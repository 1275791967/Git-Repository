package com.irongteng.protocol.common;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irongteng.protocol.Protocol;

public class FddProtocolTest{
    
    private static Logger logger = LoggerFactory.getLogger(FddProtocolTest.class);
    
    @Test
    public void testEncode() {
        
        try {
            
            
        } finally {
            
        }
    }
    
    @Test
    public void testDecode() {
        
        try {
            
            Protocol<FddParamValueObject> fdd = new FddProtocol();
            
            
            FddParamValueObject  param = new FddParamValueObject();
            String strVal = "0324460018087949962#0755,GSM134#";
            boolean bol = fdd.decode(strVal.getBytes(), param);
            System.out.println(bol);
            logger.info("type:" + param.getType() + " protocol:" + param.getProtocolID()
                    + " imsi:" + param.getImsi() + " cityCode:" + param.getCityCode() + " devName:" + param.getDeviceName());
        } finally {
            
        }
        
    }
}