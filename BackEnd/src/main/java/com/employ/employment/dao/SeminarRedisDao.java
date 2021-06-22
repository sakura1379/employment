package com.employ.employment.dao;

import com.employ.employment.service.EpAdminPasswordService;
import com.employ.employment.util.JedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Zenglr
 * @program: employment
 * @packagename: com.employ.employment.dao
 * @Description redis
 * @create 2021-05-16-3:50 下午
 */
@Slf4j
@Repository
public class SeminarRedisDao {

    //分页的每一页的结果数
    static int pageRecord = 10;

    private final JedisUtil jedisUtil;

    @Autowired
    public SeminarRedisDao(JedisUtil jedisUtil) {
        this.jedisUtil = jedisUtil;
    }

    /**
     * 查询宣讲会信息的id
     *
     * @param query 检索词
     * @return keys
     */
    public List<String> fuzzySearchSeminarIdList(String query) {
        log.info("start fuzzySearchSeminarIdList======");
        log.info("receive query:{}",query);
        List<String> list = new ArrayList<>();
        for (int i = 0, length = query.split(" ").length; i < length; i++) {
            list.add(query.split(" ")[i]);
        }
//        log.info("list:" + list.toString());
        List<String> res = new ArrayList<>(fuzzySearchSeminarList(query));
        for (String key : list) {
            res.addAll(fuzzySearchSeminarList(key));
        }
        //去重（顺序不变）
        List<String> result = new ArrayList<>(new LinkedHashSet<>(res));
        log.debug("redis fuzzy search:" + query + ",return" + result.toString());
        log.info("redis fuzzy search:{},return {} lines", query, result.size());
        return result;
    }

    /**
     * 根据compId获得SeminarId列表
     * @param compId
     * @return
     */
    public List<String> getSeminarByCompId(long compId, int page){
        log.info("start getSeminarByCompId from redis======");
        log.info("redis receive compId:{}",compId);
        List<String> seminarIdList = new ArrayList<>();
        String cid = String.valueOf(compId);
        Jedis jedis = jedisUtil.getClient();
        jedis.select(3);

        // 根据页码获取对应index
        int start = (page - 1) * pageRecord;
        log.debug("start: " + start);
        int end = start + pageRecord - 1;
        long length = jedis.llen(cid);
        if (end > length) {
            end = (int) length;
        }
        log.debug("end: " + end);
        try {
            if (jedis.exists(cid)) {
                seminarIdList.addAll(jedis.lrange(cid, start, end));
                log.info("redis found seminarIdList:{}", seminarIdList.toString());
            } else {
                log.info("redis didn't find seminarIdList, compId:{}",cid);
            }
        } finally {
            jedis.close();
        }

        return seminarIdList;
    }

    /**
     * 返回要显示的总数
     *
     * @param compId
     * @return
     */
    public Long getPageNumberByCompId(long compId) {
        log.info("start getSeminarPageNumberByCompId from redis======");
        log.info("redis receive compId:{}",compId);
        Jedis jedis = jedisUtil.getClient();
        jedis.select(4);
        String cid = String.valueOf(compId);
        long num;
        try {
            num = jedis.llen(cid);
        } finally {
            jedis.close();
        }
        long page = num / pageRecord + 1;
        log.info("pageCount: " + page);
        return num;
    }

    /**
     * 根据query找相应的SeminarId
     *
     * @param query
     * @return
     */
    public List<String> fuzzySearchSeminarList(String query) {
        log.info("start fuzzySearchSeminarList from redis=======");
        long startTime = System.currentTimeMillis();
        List<String> keys = jedisUtil.fuzzySearchKeys(query,4);
        Jedis jedis = jedisUtil.getClient();
        jedis.select(4);
        log.debug("模糊匹配到keys：" + keys.toString());
        log.info("模糊匹配到{}条keys",keys.size());
        List<String> list = new ArrayList<>();
        for (String key : keys) {
            list.addAll(jedis.smembers(key));
        }
        long finishQueryTime = System.currentTimeMillis();
        jedis.close();
        log.info("Jedis process time:" + (finishQueryTime - startTime));
        return list;
    }


    /**
     * 插入标题索引
     * @param key
     * @param value
     * @return
     */

    public Long insertIndex(String key, String value){
        log.info("start insert Index into redis======");
        log.info("receive key:{}, value:{}", key, value);
        Jedis jedis = jedisUtil.getClient();
        jedis.select(4);

        long line = jedis.sadd(key, value);
        log.info("line:{}",line);

        jedis.close();

        return line;
    }
    /**
     * 插入宣讲会信息compId索引
     * @param key
     * @param value
     * @return
     */
    public int insertCompIndex(String key, String value){
        log.info("start insertCompIndex into redis======");
        log.info("receive key:{}, value；{}", key, value);
        Jedis jedis = jedisUtil.getClient();
        jedis.select(3);

        long line = jedis.lpush(key,value);
        log.info("line:{}",line);

        jedis.close();

        return (int) line;
    }


    /**
     * 更新宣讲会信息标题的索引
     * @param oldKey
     * @param newKey
     * @param value
     * @return
     */
    public int updateTitleIndex(String oldKey, String newKey, String value){
        log.info("start updateTitleIndex========");
        log.info("receive oldKey:{}, newKey:{}, value:{}", oldKey, newKey, value);
        Jedis jedis = jedisUtil.getClient();
        jedis.select(4);
        int line = 0;

        //删除旧的
        line += jedis.srem(oldKey, value);
        //插入新的
        line += jedis.sadd(newKey, value);

        jedis.close();
        return line;
    }

    /**
     * 删除宣讲会信息标题索引和compId索引
     * @param oldKey
     * @param compId
     * @param oldValue
     * @return
     */
    public int deleteIndex(String oldKey, String compId,String oldValue){
        log.info("start deleteTitleIndex========");
        log.info("receive oldKey:{}, oldValue:{}", oldKey, oldValue);
        Jedis jedis = jedisUtil.getClient();
        int line = 0;
        //删除标题索引
        jedis.select(4);
        line += jedis.srem(oldKey, oldValue);

        //删除compId索引
        jedis.select(3);
        jedis.lrem(compId,0,oldValue);

        jedis.close();
        return line;
    }
}
