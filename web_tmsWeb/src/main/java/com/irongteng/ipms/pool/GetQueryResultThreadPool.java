package com.irongteng.ipms.pool;

import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irongteng.thirdinterface.HttpCanstants;
import com.irongteng.thirdinterface.ThirdRequest;

public class GetQueryResultThreadPool extends Thread{
    private final Logger logger = LoggerFactory.getLogger(GetQueryResultThreadPool.class);
    private ThirdRequest third = new ThirdRequest();
    
    private ConcurrentHashMap<String, Integer> queryCountMap = new ConcurrentHashMap<>();
    
    @Override
    public void run() {
        // TODO Auto-generated method stub
        getQueryResult();
        super.run();
    }
    
    private void getQueryResult(){
        try{
            while(true){
                if(HttpCanstants.remain_count > 0){
                    if(HttpCanstants.waitQueryResultList.size() > 0){
                        String resq = HttpCanstants.waitQueryResultList.peek();
                        int count = 0;
                        if(queryCountMap.containsKey(resq)){
                            count = queryCountMap.get(resq);
                            queryCountMap.remove(resq);
                        }
                        queryCountMap.put(resq, ++count);
                        //查询30次都没有返回结果，认为第三方库不存在该imsi/电话号码，从等待查询列表中移除
                        if(count >= 30){   
                            HttpCanstants.waitQueryResultList.remove(resq);
                            queryCountMap.remove(resq);
                        }
                        
                        Thread.sleep(2*1000);
                        int waitCount = third.query();
                        if(waitCount != 0 ){
                            Thread.sleep(10*1000);
                        }
                        third.output();
                    }
                }else{
                    logger.info("查询剩余次数不足,需要进行充值....");
                    Thread.sleep(60*1000);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            getQueryResult();
        }
        
    }
    
}
