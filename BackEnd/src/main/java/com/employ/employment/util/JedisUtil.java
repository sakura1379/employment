package com.employ.employment.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zenglr
 * @ClassName JedisUtil
 * @Description JedisPool配置以及连接
 * @create 2020-10-16-7:09 下午
 */

@Slf4j
@Component
public class JedisUtil {

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.jedis.pool.max-idle}")
    private int MaxIdle;
    @Value("${spring.redis.jedis.pool.min-idle}")
    private int MinIdle;

    public JedisUtil() {
    }

    @Bean
    public JedisPool initPoll() {
        log.info("调用初始化jedisPool方法");
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(1000);
        jedisPoolConfig.setMaxIdle(MaxIdle);
        jedisPoolConfig.setMinIdle(MinIdle);
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setTestOnCreate(true);
        jedisPoolConfig.setTestOnReturn(true);
        jedisPoolConfig.setTestWhileIdle(true);

        jedisPoolConfig.setMaxWaitMillis(10 * 1000);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, 3000);
        log.info("检测jedisPool：" + jedisPool.getResource());
        return jedisPool;
    }

    @Autowired
    JedisPool jedisPool;
    public Jedis getClient() {
        log.info("调用getClient");
        return jedisPool.getResource();
    }


    /**
     * 根据query进行模糊匹配找相应的检索词
     *
     * @param query
     * @return
     */
    public List<String> fuzzySearchKeys(String query, int database) {
        log.info("fuzzySearch:{},database:{}", query, database);
        String pattern = query.trim().replaceAll("\\s+", "*");
        pattern = "*" + pattern + "*";
        long startTime = System.currentTimeMillis();
        List<String> res = jedisScan(pattern, database);
        long finishTime = System.currentTimeMillis();
        log.info("jediskeys process time:" + (finishTime - startTime));
        log.info("fuzzySearch:{},size:{}", pattern, res.size());
        return res;
    }

    /**
     * 根据模糊的query找到所有的检索词
     *
     * @param pattern,database
     * @return
     */
    public List<String> jedisScan(String pattern, int database) {
        long startTime = System.currentTimeMillis();
        Jedis jedis = getClient();
        jedis.select(database);
        String cursor = ScanParams.SCAN_POINTER_START;
        ScanParams scanParams = new ScanParams();
        scanParams.match(pattern);
        scanParams.count(Integer.MAX_VALUE);
        List<String> keys = new ArrayList<>();
        do {
            //使用scan命令获取数据，使用cursor游标记录位置，下次循环使用
            ScanResult<String> scanResult = jedis.scan(cursor, scanParams);
            /* 返回0 说明遍历完成 */
            cursor = scanResult.getCursor();
            keys = scanResult.getResult();
        } while (!"0".equals(cursor));
        long finishTime = System.currentTimeMillis();
        log.info("jedisScan process time:" + (finishTime - startTime));
        jedis.close();
        return keys;
    }

    /**
     * 返回要显示的页码总数
     *
     * @param length listLength
     * @return pagenum
     */
    public Integer getPageNumber(int length, int pageRecord) {
        return length / pageRecord + (length % pageRecord == 0 ? 0 : 1);
    }
}
