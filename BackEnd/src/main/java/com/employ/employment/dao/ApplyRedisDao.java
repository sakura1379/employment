package com.employ.employment.dao;

import com.employ.employment.util.JedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 *
 * @ProjectName: employment
 * @Package: com.employ.employment.dao
 * @ClassName: ApplyRedisDao
 * @Description: [Redis for apply_info]
 * @Author: [clh]
 * @Date: 2021/6/11 13:52
 **/
@Slf4j
@Repository
public class ApplyRedisDao {
    //分页的每一页的结果数
    static int pageRecord = 10;

    private final JedisUtil jedisUtil;

    @Autowired
    public ApplyRedisDao(JedisUtil jedisUtil) {
        this.jedisUtil = jedisUtil;
    }

    /**
     * 查询职位申请信息的id
     *
     * @param query 检索词
     * @return keys
     */
    public List<String> fuzzySearchApplyIdList(String query) {
        log.info("====start fuzzySearch ApplyIdList=====");
        log.info("receive query:{}",query);
        List<String> list = new ArrayList<>();
        for (int i = 0, length = query.split(" ").length; i < length; i++) {
            list.add(query.split(" ")[i]);
        }
//        log.info("list:" + list.toString());
        List<String> res = new ArrayList<>(fuzzySearchApplyList(query));
        for (String key : list) {
            res.addAll(fuzzySearchApplyList(key));
        }
        //去重（顺序不变）
        List<String> result = new ArrayList<>(new LinkedHashSet<>(res));
        log.debug("redis fuzzy search:" + query + ",return" + result.toString());
        log.info("redis fuzzy search:{},return {} lines", query, result.size());
        return result;
    }

    /**
     * 根据compId获得申请的jobId列表
     * @param compId
     * @return
     */
    public List<String> getApplyByCompId(long compId, int page){
        log.info("====start getApplyByCompId from redis====");
        log.info("redis receive compId:{}",compId);
        List<String> applyIdList = new ArrayList<>();
        String cid = String.valueOf(compId);
        Jedis jedis = jedisUtil.getClient();
        jedis.select(0);

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
                applyIdList.addAll(jedis.lrange(cid, start, end));
                log.info("redis found applyIdList:{}", applyIdList.toString());
            } else {
                log.info("redis didn't find applyIdList, compId:{}",cid);
            }
        } finally {
            jedis.close();
        }

        return applyIdList;
    }

    /**
     * 返回要显示的总数
     *
     * @param compId
     * @return
     */
    public Long getApplyPageNumberByCompId(long compId) {
        log.info("====start getApplyPageNumberByCompId from redis=====");
        log.info("redis receive compId:{}",compId);
        Jedis jedis = jedisUtil.getClient();
        jedis.select(0);
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
     * 根据stuNum获得申请的jobId列表
     * @param stuNum
     * @return
     */
    public List<String> getApplyByStuNum(long stuNum, int page){
        log.info("====start getApplyByStuNum from redis====");
        log.info("redis receive stuNum:{}",stuNum);
        List<String> applyIdList = new ArrayList<>();
        String cid = String.valueOf(stuNum);
        Jedis jedis = jedisUtil.getClient();
        jedis.select(1);

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
                applyIdList.addAll(jedis.lrange(cid, start, end));
                log.info("redis found applyIdList:{}", applyIdList.toString());
            } else {
                log.info("redis didn't find applyIdList, stuNum:{}",cid);
            }
        } finally {
            jedis.close();
        }

        return applyIdList;
    }

    /**
     * 返回要显示的页码总数
     *
     * @param stuNum
     * @return
     */
    public Long getApplyPageNumberByStuNum(long stuNum) {
        log.info("====start getApplyPageNumberByStuNum from redis=====");
        log.info("redis receive stuNum:{}",stuNum);
        Jedis jedis = jedisUtil.getClient();
        jedis.select(1);
        String cid = String.valueOf(stuNum);
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
     * 根据query找相应的JobId
     *
     * @param query
     * @return
     */
    public List<String> fuzzySearchApplyList(String query) {
        log.info("====start fuzzySearchApplyList from redis======");
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
     * 插入申请职位信息compId索引
     * @param key
     * @param value
     * @return
     */
    public int insertCompIndex(String key, String value){
        log.info("start insertCompIndex into redis======");
        log.info("receive key:{}, value；{}", key, value);
        Jedis jedis = jedisUtil.getClient();
        jedis.select(0);

        long line = jedis.lpush(key,value);
        log.info("line:{}",line);

        jedis.close();

        return (int) line;
    }

    /**
     * 插入申请职位信息stuNum索引
     * @param key
     * @param value
     * @return
     */
    public int insertStuIndex(String key, String value){
        log.info("start insertStuIndex into redis======");
        log.info("receive key:{}, value；{}", key, value);
        Jedis jedis = jedisUtil.getClient();
        jedis.select(1);

        long line = jedis.lpush(key,value);
        log.info("line:{}",line);

        jedis.close();

        return (int) line;
    }

    /**
     * 删除宣讲会信息标题索引和compId索引
     * @param compId
     * @param stuNum
     * @param jobId
     * @return
     */
    public int deleteIndex(String compId, String stuNum,String jobId){
        log.info("start delete Index========");
        log.info("receive old company id:{}, old stuNum:{}, old jobId:{}", compId, stuNum, jobId);
        Jedis jedis = jedisUtil.getClient();
        int line = 0;
        //删除学生-工作索引
        jedis.select(1);
//        line += jedis.del(stuNum, jobId);
        line += jedis.lrem(stuNum,0,jobId);

        //删除compId索引
        jedis.select(0);
        jedis.lrem(compId,0,jobId);

        jedis.close();
        return line;
    }
}
