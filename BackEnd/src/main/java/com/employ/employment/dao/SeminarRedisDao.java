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
        jedis.select(4);

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
     * 返回要显示的页码总数
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
        return page;
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
            list.add(jedis.get(key));
        }
        long finishQueryTime = System.currentTimeMillis();
        log.info("Jedis process time:" + (finishQueryTime - startTime));
        return list;
    }


}
