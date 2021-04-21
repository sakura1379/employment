package com.employ.employment.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

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
}
