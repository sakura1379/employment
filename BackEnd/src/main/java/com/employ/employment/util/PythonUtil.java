package com.employ.employment.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.employ.employment.entity.CompanyInfo;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Zenglr
 * @program: employment
 * @packagename: com.employ.employment.util
 * @Description 调用python
 * @create 2021-06-19-7:37 下午
 */
@Slf4j
@Component
public class PythonUtil {

//    public void verify(CompanyInfo c){
//        try{
//            Process process;
//            process = Runtime.getRuntime().exec("python /Users/sskura/Documents/MIS/comp_validate");
//            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            String line = null;
//            while((line = in.readLine()) != null){
//                log.info("receive:{}", line);
//            }
//            in.close();
//            process.waitFor();
//        }catch (IOException | InterruptedException e){
//            e.printStackTrace();
//        }
//    }

    // 向第三方接口发送一个post 请求的参数的看具体的要求，该接口想要的数据是什么类型，如果是json，那就把参数转换为json类型，其他的转换为其它类型，如阿里的接口参数就有的不是json类型
    public String httpPost(Integer type, String[] args) throws Exception {
        String result = "";
        HttpPost post = new HttpPost("http://localhost:7788/validate");
        // json格式的参数，我们可以用map来封装参数，然后将参数转换为json格式
        Map<String ,Object> params = new HashMap<>();
        if(type == 1){
            log.info("type is company");
            params.put("type","company");
            params.put("compName",args[0]);
            params.put("creditcode",args[1]);
        }else if(type == 2){
            log.info("type is student");
            params.put("type","student");
            params.put("email",args[0]);
        }else {
            log.info("type wrong");
        }
        String paramsJson = JSON.toJSONString(params); // 将参数转换为json字符串
        System.out.println(paramsJson);

        try{
            CloseableHttpClient httpClient = HttpClients.createDefault();

            post.setHeader("Content-Type","application/json;charset=utf-8");
            post.addHeader("Authorization", "Basic YWRtaW46");
            StringEntity postingString = new StringEntity(paramsJson.toString(),"utf-8");
            post.setEntity(postingString);
            HttpResponse response = httpClient.execute(post);

            InputStream in = response.getEntity().getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "utf-8"));
            StringBuilder strber= new StringBuilder();
            String line = null;
            while((line = br.readLine())!=null){
                strber.append(line+'\n');
            }
            br.close();
            in.close();
            String tempResult = strber.toString();
            JSONObject jsonObject = JSONObject.parseObject(tempResult);
            result = jsonObject.getString("status");
            if(response.getStatusLine().getStatusCode()!= HttpStatus.SC_OK){
                result = "服务器异常";
            }
        }catch (Exception e){
            System.out.println("请求异常");
            throw new RuntimeException(e);
        } finally{
            post.abort();
        }
        return result;
    }
//
//    public static void main(String[] args) throws Exception {
//        String[] strings = new String[2];
//        strings[0] = "18023893551@123.com";
//        System.out.println(httpPost(2,strings));
//    }

}
