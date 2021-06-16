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
 * @ClassName: JobRedisDao
 * @Description: [redis for jobinfo]
 * @Author: [clh]
 * @Date: 2021/6/11 10:59
 **/
@Slf4j
@Repository
public class JobRedisDao {
    //分页的每一页的结果数
    static int pageRecord = 10;

    private final JedisUtil jedisUtil;

    @Autowired
    public JobRedisDao(JedisUtil jedisUtil) {
        this.jedisUtil = jedisUtil;
    }

    /**
     * 查询职位信息的id
     *
     * @param query 检索词
     * @return keys
     */
    public List<String> fuzzySearchJobIdList(String query) {
        log.info("====start fuzzySearch JobIdList=====");
        log.info("receive query:{}",query);
        List<String> list = new ArrayList<>();
        for (int i = 0, length = query.split(" ").length; i < length; i++) {
            list.add(query.split(" ")[i]);
        }
//        log.info("list:" + list.toString());
        List<String> res = new ArrayList<>(fuzzySearchJobList(query));
        for (String key : list) {
            res.addAll(fuzzySearchJobList(key));
        }
        //去重（顺序不变）
        List<String> result = new ArrayList<>(new LinkedHashSet<>(res));
        log.debug("redis fuzzy search:" + query.toString() + ",return" + result.toString());
        log.info("redis fuzzy search:{},return {} lines", query, result.size());
        return result;
    }

    /**
     * 根据compId获得jobId列表
     * @param compId
     * @return
     */
    public List<String> getJobByCompId(long compId, int page){
        log.info("====start getJobByCompId from redis====");
        log.info("redis receive compId:{}",compId);
        List<String> jobIdList = new ArrayList<>();
        String cid = String.valueOf(compId);
        Jedis jedis = jedisUtil.getClient();
        jedis.select(2);

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
                jobIdList.addAll(jedis.lrange(cid, start, end));
                log.info("redis found jobIdList:{}", jobIdList.toString());
            } else {
                log.info("redis didn't find jobIdList, compId:{}",cid);
            }
        } finally {
            jedis.close();
        }

        return jobIdList;
    }

    /**
     * 返回要显示的页码总数
     *
     * @param compId
     * @return
     */
    public Long getJobPageNumberByCompId(long compId) {
        log.info("====start getJobPageNumberByCompId from redis=====");
        log.info("redis receive compId:{}",compId);
        Jedis jedis = jedisUtil.getClient();
        jedis.select(2);
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
     * 根据query找相应的JobId
     *
     * @param query
     * @return
     */
    public List<String> fuzzySearchJobList(String query) {
        log.info("====start fuzzySearchJobList from redis======");
        long startTime = System.currentTimeMillis();
        List<String> keys = jedisUtil.fuzzySearchKeys(query,5);
        Jedis jedis = jedisUtil.getClient();
        jedis.select(5);
        log.debug("模糊匹配到keys：" + keys.toString());
        log.info("模糊匹配到keys：" + keys.toString());
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
     * 插入职位信息compId索引
     * @param key
     * @param value
     * @return
     */
    public int insertCompIndex(String key, String value){
        log.info("start insertCompIndex into redis======");
        log.info("receive key:{}, value；{}", key, value);
        Jedis jedis = jedisUtil.getClient();
        jedis.select(2);

        long line = jedis.lpush(key,value);
        log.info("line:{}",line);

        jedis.close();

        return (int) line;
    }

    /**
     * 删除宣讲会信息标题索引和compId索引
     * @param compId
     * @param jobId
     * @return
     */
    public int deleteIndex(String compId,String jobId){
        log.info("start deleteTitleIndex========");
        log.info("receive oldKey:{}, oldValue:{}", compId, jobId);
        Jedis jedis = jedisUtil.getClient();
        int line = 0;

        //删除compId索引
        jedis.select(2);
        //jedis.del(compId,jobId);
        jedis.lrem(compId,0,jobId);

        jedis.close();
        return line;
    }
}
