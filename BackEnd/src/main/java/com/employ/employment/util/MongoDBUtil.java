package com.employ.employment.util;

import com.mongodb.MongoClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Zenglr
 * @program: employment
 * @packagename: com.employ.employment.util
 * @Description MongoDB连接
 * @create 2021-06-16-7:45 下午
 */
@Slf4j
@Component
public class MongoDBUtil {
    /**
     * @return Mongodb的连接
     */
    public static MongoClient getConn(){
        MongoClient mongoClient = null;
        try{
            mongoClient = new MongoClient("localhost", 27017);
        }catch (Exception e){
            log.error(e.getClass().getName()+": "+e.getMessage());
        }
        return mongoClient;

    }
}
