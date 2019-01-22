package com.irongteng.thirdinterface;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.mina.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.irongteng.control.CommonParameterControl;

public class ThirdRequest {
    private final Logger logger = LoggerFactory.getLogger(ThirdRequest.class);
    private static int noQueryResultCount = 0;
    private static boolean queryResultFlag = true;
    private Timer timer = null;
    private TimerTask task = null;
    
    public void startTimming(){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                int waitCount = query();
                if(waitCount == 0){
                    output();
                }
            }
        }, 2000, 2000);
    }
    
    /**
     * 构造Basic Auth认证头信息
     * @return
     */
    public String getAuthHeader() {
        String auth = String.format("%s:%s", HttpCanstants.userName, HttpCanstants.password);
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        return authHeader;
    }
    
    /**
     * 构造OAuth2 Bearer Auth认证头信息
     * @return
     */
    private static String getOAuth2BearerHeader() {
//        auth_bearer = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiIsImp0aSI6IjFGT1JRWVJ2ZUI3RGtwNDkifQ.eyJ1aWQiOiI1OWE2YjBmMTA2ZDA3MDg2NjczNjY4IiwiaXNzIjoiaHR0cDpcL1wvZXhhbXBsZS5jb20iLCJhdWQiOiJodHRwOlwvXC9leGFtcGxlLm9yZyIsImp0aSI6IjFGT1JRWVJ2ZUI3RGtwNDkiLCJpYXQiOjE1MzY5MDkxNzMsIm5iZiI6MTUzNjkwOTE4OCwiZXhwIjoxNTM2OTEyNzczfQ.k4e2YLXoxfuVFASVUNWvT5HMC0xl40HfvMGwIRkkCC4";
        if(HttpCanstants.auth_bearer != null){
//            System.out.println("auth_bearer == "+HttpCanstants.auth_bearer);
            String authHeader = "OAuth2 Bearer " + HttpCanstants.auth_bearer;
            return authHeader;
        }
        return null;
    }
    
    /**
     * 获取令牌，所有的操作的前提是先获取令牌，一次获取后失效时间为60分钟，令牌获取成功15秒后生效
     * @return
     */
    public String getapitoken(){
        String host = HttpCanstants.url+HttpCanstants.cmd_getAPIToken;
        Map<String, String> headers = new HashMap<>();
        String auth = getAuthHeader();
        if(auth == null){
            logger.info("获取令牌的Basic Auth认证头信息为null....");
            return null;
        }
        headers.put("Authorization", auth);
        try{
            HttpResponse response = HttpUtils.doPut(host, "", headers, null, "");
            if(response != null){  
                int statusCode = response.getStatusLine().getStatusCode();
                logger.info("获取令牌请求返回的结果： "+statusCode);
                if(statusCode == 200){
                    JSONObject jsonObj = HttpUtils.getJson(response);
                    String authBearer = (String) jsonObj.get("Authorization");
                    String result =authBearer.substring(authBearer.indexOf("Bearer ")+"Bearer ".length());
//                    HttpCanstants.auth_bearer = authBearer.substring(authBearer.indexOf("Bearer "));
                    logger.info("获取令牌请求返回的authBearer == "+ result);
                    return result;
                }
            }else{
                logger.info("获取令牌的HttpResponse响应为null....");
                return null;
            }
        }catch(Exception e){
            e.printStackTrace();
            logger.info("Exception : "+e.toString());
            return null;
        }
        return null;
    } 
   
   /**
    * 获取剩余次数
    * @return
    */
   public Integer remain(){
       String host = HttpCanstants.url+HttpCanstants.cmd_remain;
       Map<String, String> headers = new HashMap<>();
       String auth = getOAuth2BearerHeader();
       if(auth == null){
           logger.info("获取剩余次数的OAuth2 Bearer认证头信息为null....");
           return -1;
       }
       try{
           headers.put("Authorization", auth);
           HttpResponse response = HttpUtils.doGet(host, "", headers, null);
           if(response != null){  
               int statusCode = response.getStatusLine().getStatusCode();
               logger.info("获取剩余次数请求返回的结果： "+statusCode);
               if(statusCode == 200){
                   JSONObject jsonObj = HttpUtils.getJson(response);
//                   System.out.println(jsonObj.toJSONString());
                   String remaintimes = (String) jsonObj.get("remaintimes");
                   logger.info("获取剩余次数  == "+remaintimes);
                   return Integer.parseInt(remaintimes);
               }
           }else{
               logger.info("获取剩余次数的HttpResponse响应为null....");
               return -1;
           }
       }catch(Exception e){
           e.printStackTrace();
           logger.info("Exception : "+e.toString());
           return -1;
       }
       return -1;
   }
   
   /**
    * 获取当前等待查询的请求个数
    * @return
    */
   public Integer query(){
       String host = HttpCanstants.url+HttpCanstants.cmd_query;
       Map<String, String> headers = new HashMap<>();
       String auth = getOAuth2BearerHeader();
       if(auth == null){
           logger.info("获取当前等待查询的请求个数的OAuth2 Bearer认证头信息为null....");
           return null;
       }
       try{
           headers.put("Authorization", auth);
           HttpResponse response = HttpUtils.doGet(host, "", headers, null);
           if(response != null){  
               int statusCode = response.getStatusLine().getStatusCode();
               logger.info("获取当前等待查询的请求个数请求返回的结果： "+statusCode);
               if(statusCode == 200){
                   JSONObject jsonObj = HttpUtils.getJson(response);
                   String str = (String) jsonObj.get("waitforcount");
                   logger.info("等待查询数量 == "+str);
                   return Integer.parseInt(str) ;
               }
           }else{
               logger.info("获取当前等待查询的请求个数的HttpResponse响应为null....");
               return -1;
           }
       }catch(Exception e){
           e.printStackTrace();
           logger.info("Exception : "+e.toString());
           return -1;
       }
       return -1;
   }
   
   /**
    * 提交查询申请
    */
   public void inputOne(String queryStr){
       String host = HttpCanstants.url+HttpCanstants.cmd_input;
       Map<String, String> headers = new HashMap<>();
       String auth = getOAuth2BearerHeader();
       if(auth == null){
           logger.info("获取当前等待查询的请求个数的OAuth2 Bearer认证头信息为null....");
           return ;
       }
       headers.put("Authorization", auth);
       try{
           String imsiBase64 = new String(Base64.encodeBase64(queryStr.getBytes(Charset.forName("US-ASCII"))));
           headers.put("dtag", imsiBase64);
           HttpResponse response = HttpUtils.doPost(host, "", headers, null, "");
           if(response != null){  
               int statusCode = response.getStatusLine().getStatusCode();
               logger.info(String.format("<%s> 提交查询申请请求返回的结果 : %d", queryStr, statusCode));
               if(statusCode == 200){
                   //缓存中的第三方查询次数-1
                   AccountBilling.deduckFromCache(queryStr);
                   HttpCanstants.resqList.remove();
                   logger.info("success: 提交查询申请请求成功....");
                   //加入等待查询结果列表中
                   HttpCanstants.waitQueryResultList.offer(queryStr);
               }else if(statusCode == 429){  //待查询数超过5条，延时继续尝试
                   Thread.sleep(100);
               }else if(statusCode == 401){  //令牌已过期需获取，重新获取令牌
                   reGetAPIToken();
                   input();
               }else if(statusCode == 403){  //账号被禁用需充值，联系公司充值
                   logger.info("帐号剩余查询次数不足，需充值....");
                   HttpCanstants.remain_count = remain();
                   logger.info("查询帐号剩余查询次数为："+HttpCanstants.remain_count);
               }else if(statusCode == 422){
                   String resqText = queryStr.substring(3);
                   logger.info(String.format("数据格式错误，提交的imsi或电话号码不规范。。。。<%s>", resqText));
                   HttpCanstants.resqList.remove();
                   HttpCanstants.resqQueueMap.remove(resqText);
               }
           }else{
               logger.info("提交查询申请请求的HttpResponse响应为null....");
           }
       }catch(Exception e){
           e.printStackTrace();
           logger.info("Exception : "+e.toString());
       }
   }
   
   /**
    * 提交查询申请
    */
   public void input(){
       String host = HttpCanstants.url+HttpCanstants.cmd_input;
       Map<String, String> headers = new HashMap<>();
       String auth = getOAuth2BearerHeader();
       if(auth == null){
           logger.info("获取当前等待查询的请求个数的OAuth2 Bearer认证头信息为null....");
           return ;
       }
       /*String imsi = "101460010123456789";
       String imsiBase64 = new String(Base64.encodeBase64(imsi.getBytes(Charset.forName("US-ASCII"))));
       System.out.println(imsiBase64);*/
       
       headers.put("Authorization", auth);
       int index = 0;
       int count = HttpCanstants.resqList.size() > 5 ? 5 : HttpCanstants.resqList.size();
       try{
           if(index < count){
               String queryStr = HttpCanstants.resqList.peek();
               /*if(queryText.startsWith("460")){  //imsi查电话号码
                   queryText = "101"+queryText;
               }else{ //电话号码反查imsi
                   queryText = "000"+queryText;
               }*/
               String imsiBase64 = new String(Base64.encodeBase64(queryStr.getBytes(Charset.forName("US-ASCII"))));
               headers.put("dtag", imsiBase64);
               HttpResponse response = HttpUtils.doPost(host, "", headers, null, "");
               if(response != null){  
                   int statusCode = response.getStatusLine().getStatusCode();
                   logger.info(String.format("<%s> 提交查询申请请求返回的结果 : %d", queryStr, statusCode));
                   if(statusCode == 200){
                       //缓存中的第三方查询次数-1
                       AccountBilling.deduckFromCache(queryStr);
                       HttpCanstants.resqList.remove();
                       logger.info("success: 提交查询申请请求成功....");
                       //加入等待查询结果列表中
                       HttpCanstants.waitQueryResultList.offer(queryStr);
                   }else if(statusCode == 429){  //待查询数超过5条，延时继续尝试
                       Thread.sleep(100);
                   }else if(statusCode == 401){  //令牌已过期需获取，重新获取令牌
                       reGetAPIToken();
                       input();
                   }else if(statusCode == 403){  //账号被禁用需充值，联系公司充值
                       logger.info("帐号剩余查询次数不足，需充值....");
                       HttpCanstants.remain_count = remain();
                       logger.info("查询帐号剩余查询次数为："+HttpCanstants.remain_count);
                   }else if(statusCode == 422){
                       String resqText = queryStr.substring(3);
                       logger.info(String.format("数据格式错误，提交的imsi或电话号码不规范。。。。<%s>", resqText));
                       HttpCanstants.resqList.remove();
                       HttpCanstants.resqQueueMap.remove(resqText);
                   }
               }else{
                   logger.info("提交查询申请请求的HttpResponse响应为null....");
               }
           } 
       }catch(Exception e){
           e.printStackTrace();
           logger.info("Exception : "+e.toString());
       }
   }
   
   /**
    * 获得查询结果
    */
   public HashMap<String, String> output(){
       HashMap<String, String> resultMap = new HashMap<>();
       String host = HttpCanstants.url+HttpCanstants.cmd_output;
       Map<String, String> headers = new HashMap<>();
       String auth = getOAuth2BearerHeader();
       if(auth == null){
           logger.info("获得查询结果请求的OAuth2 Bearer认证头信息为null....");
           return null;
       }
       headers.put("Authorization", auth);
       try{
           HttpResponse response = HttpUtils.doDelete(host, "", headers, null);
           if(response != null){  
               int statusCode = response.getStatusLine().getStatusCode();
               logger.info("获得查询结果请求返回的结果： "+statusCode);
               if(statusCode == 200){
                   HttpEntity entity = response.getEntity();
                   String resq = EntityUtils.toString(entity, "UTF-8");
                   resultMap = decodeRcontent(resq);
                   new CommonParameterControl().dealThirdQueryAnswer(resultMap);
                   //查询回来结果后，再延时2秒查询，看是否还存在其他的结果未查询回来
                   /*noQueryResultCount = 0;
                   Thread.sleep(2*1000);
                   output();*/
               }else if(statusCode == 401){  //令牌已过期需获取，重新获取令牌
                   reGetAPIToken();
                   output();
               }else if(statusCode == 404){  //当前没有任何结果，延时继续尝试
                   logger.info("当前没有任何结果，延时继续尝试");
               }else if(statusCode == 403){  //账号被禁用需充值，联系公司充值
                   logger.info("帐号剩余查询次数不足，需充值....");
                   HttpCanstants.remain_count = remain();
                   logger.info("查询帐号剩余查询次数为："+HttpCanstants.remain_count);
               }
           }else{
               logger.info("获得查询结果请求的HttpResponse响应为null....");
               return null;
           }
       }catch(Exception e){
           e.printStackTrace();
           logger.info("Exception : "+e.toString());
           return resultMap;
       }
       return resultMap;
   }
   
   
   
   /**
    * 解析查询结果
    * @return
    */
   private HashMap<String, String> decodeRcontent(String resq){
       logger.info("第三方服务器返回的查询结果是  : "+resq);
       //去掉头尾的[]中括号
       resq = resq.substring(1, resq.length()-1);
       logger.info("去掉头尾的[]中括号后的结果是  : "+resq);
       HashMap<String, String> resultMap = new HashMap<>();
       try {
           String[] resultArray = resq.split("},");
           for(String resultOne : resultArray){
               resultOne = resultOne.replace("{", "");
               resultOne = String.format("{%s}",resultOne.replace("}", ""));
               logger.info("获得查询结果： "+resultOne);
               JSONObject jsonObj = JSON.parseObject(resultOne);
               String rcontent = (String) jsonObj.get("rcontent");
               byte[] data = Base64.decodeBase64(rcontent.getBytes("utf-8"));
               String result = new String(data);
               logger.info("解析的结果是："+result);
               String[] ss = result.split("\t");
               logger.info("请求的数据是："+ss[0].substring(3)+ " >>>  结果是： "+ss[1].substring(1));
               resultMap.put(ss[0].substring(3), ss[1].substring(1));
           }
           return resultMap;
       } catch (UnsupportedEncodingException e) {
           e.printStackTrace();
           return resultMap;
       }
       
   }
   
   private void reGetAPIToken(){
       try {
           HttpCanstants.auth_bearer = getapitoken();
           Thread.sleep(15*1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
   }
   
   public static void main(String[] args) {
       String resp = "{\"rid\":1,\"rcontent\":\"MDAwODYxMzkxMjM0NTY3OAkxNDYwMDI2NzU5NjkxODU0\",\"rtime\":\"2017-10-19 17:04:08\"},"
               + "{\"rid\":2,\"rcontent\":\"MDAwODYxMzkxMjM0NTY3OAkxNDYwMDM5NzMwMjg0Mzkx\",\"rtime\":\"2017-10-19 17:04:09\"},"
                       + "{\"rid\":3,\"rcontent\":\"MDAwODYxMzkxMjM0NTY3OAkxNDYwMDM1NDk4NjM4NDQz\",\"rtime\":\"2017-10-19 17:04:21\"}";
       ThirdRequest third = new ThirdRequest();
       HashMap<String, String> resMap = third.decodeRcontent(resp);
       Iterator<String> it = resMap.keySet().iterator();
       while(it.hasNext()){
           String key = it.next();
           String value = resMap.get(key);
           System.out.println("请求的数据是："+key+" >>> 请求的结果是："+value);
       }
       /*String[] resultArray = resp.split("},");
       for(String resultOne : resultArray){
           resultOne = resultOne.replace("{", "");
           resultOne = resultOne.replace("}", "");
           System.out.println(resultOne);
           JSONObject jsonObj = JSON.parseObject(String.format("{%s}", resultOne));
           String str1 = (String) jsonObj.get("rcontent");
           System.out.println(str1);
       }*/
       
       /*HttpCanstants.resqList.offer("460001234567890");
       HttpCanstants.resqList.offer("460011234567890");
       HttpCanstants.resqList.offer("460021234567890");
       HttpCanstants.resqList.offer("460031234567890");
       for(String imsi : HttpCanstants.resqList){
           System.out.println("start: "+imsi);
       }
       String imsi = HttpCanstants.resqList.peek();
       System.out.println("peek: "+imsi);
       String imsi1 = HttpCanstants.resqList.peek();
       System.out.println("peek: "+imsi1);
       for(String str : HttpCanstants.resqList){
           System.out.println("end: "+str);
       }*/
   }

}
