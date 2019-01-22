package com.irongteng.ipms.pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irongteng.thirdinterface.HttpCanstants;
import com.irongteng.thirdinterface.ThirdRequest;

public class HttpThreadPool extends Thread{
    private final Logger logger = LoggerFactory.getLogger(HttpThreadPool.class);
    private ThirdRequest third = new ThirdRequest();
    
    @Override
    public void run() {
        // TODO Auto-generated method stub
        request();
        super.run();
    }
    
    private void request(){
        try{
            while(true){
                if(HttpCanstants.remain_count > 0){
                    if(!HttpCanstants.resqList.isEmpty()){
                        if(third.query() > 0){
                            Thread.sleep(5*1000);
                        }
                        third.input();
                    }
                }else{
                    logger.info("查询剩余次数不足,需要进行充值....");
                    Thread.sleep(60*1000);
                }
                
            }
        }catch(Exception e){
            e.printStackTrace();
            request();
        }
        
    }
    
}
