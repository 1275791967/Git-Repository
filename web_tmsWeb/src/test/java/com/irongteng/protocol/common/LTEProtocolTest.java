package com.irongteng.protocol.common;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irongteng.protocol.Protocol;

import dwz.common.util.StringUtils;

public class LTEProtocolTest {
  
private static Logger logger = LoggerFactory.getLogger(LTEProtocolTest.class);
    
    @Test
    public void testEncode() {
        
        try {
            LTEParamValueObject  p = new LTEParamValueObject();
            p.setCommandID((byte)0xD5);
            p.setBodyLen((short)44);
            List<String> imsis = new ArrayList<String>();
            imsis.add("4644398788589918");
            imsis.add("4643398788589916");
            p.setImsiList(imsis);
            
            Protocol<LTEParamValueObject> fdd = new LTEProtocol();
            byte[] data = fdd.encode(p);
            logger.info("解析数据：" + StringUtils.bytes2HexString(data));
        } finally {
            
        }
    }
    
    @Test
    public void testDecode() {
        
        try {
            LTEParamValueObject  p = new LTEParamValueObject();
            p.setCommandID((byte)0xD5);
            p.setBodyLen((short)44);
            List<String> imsis = new ArrayList<String>();
            imsis.add("4644398788589918");
            imsis.add("4643398788589916");
            p.setImsiList(imsis);
            
            Protocol<LTEParamValueObject> protcol = new LTEProtocol();
            
            LTEParamValueObject  param = new LTEParamValueObject();
            
            boolean bol = protcol.decode(protcol.encode(p), param);
            
            System.out.println(bol);
            
            logger.info("commandID:" + param.getCommandID() + " bodyLen:" + param.getBodyLen() + " cityCode:" + param.getCityCode() + " devName:" + param.getDeviceName());
            for(String imsi:param.getImsiList()) {
                logger.info("imsi:" + imsi);
            }
        } finally {
            
        }
    }
}
