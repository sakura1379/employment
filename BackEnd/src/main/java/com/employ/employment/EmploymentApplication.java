package com.employ.employment;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@EnableCaching // 启用缓存
//@EnableScheduling // 启动定时任务
@SpringBootApplication // springboot本尊
@MapperScan("com.employ.employment.mapper")
//@EnableTransactionManagement // 启动注解事务管理
public class EmploymentApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmploymentApplication.class, args);
    }

}
