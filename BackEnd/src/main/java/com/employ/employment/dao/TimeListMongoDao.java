package com.employ.employment.dao;

import com.employ.employment.entity.AjaxJson;
import com.employ.employment.util.MongoDBUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import java.util.regex.Pattern;

/**
 * @author Zenglr
 * @program: employment
 * @packagename: com.employ.employment.dao
 * @Description 时间表数据库操作
 * @create 2021-06-16-7:46 下午
 */
@Slf4j
@Repository
public class TimeListMongoDao {
    private static MongoCollection<Document> collection;
    private static StringBuilder sb;

    public AjaxJson getTimeList(String compName){
        MongoClient mongoClient=null;
        try{
            mongoClient = MongoDBUtil.getConn();
            collection = mongoClient.getDatabase("employment").getCollection("timeList");
            sb= new StringBuilder();
//            Pattern pattern = Pattern.compile("*"+compName+"*", Pattern.CASE_INSENSITIVE);
            BasicDBObject compName1 = new BasicDBObject("compName",compName);
            Document originDoc = collection.find(compName1).first();
            Document extractedDoc = new Document();
            if (originDoc!=null&&!originDoc.isEmpty()) {
//                extractedDoc.put("timeList",originDoc);
//                sb.append(extractedDoc.toJson());
                sb.append(originDoc.toJson());
                log.info(sb.toString());
            }else{
                sb.append("{}");
                log.info("not found");
            }
//            sb.deleteCharAt(0);
            log.info("has already timeList from MongoDB");
            log.info(sb.toString());
        }finally {
            assert mongoClient != null;
            mongoClient.close();
        }
        return AjaxJson.getSuccessData(sb.toString());
    }
}
