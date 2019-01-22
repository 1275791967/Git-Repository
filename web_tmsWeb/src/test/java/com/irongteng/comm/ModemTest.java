package com.irongteng.comm;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dwz.framework.junit.BaseJunitCase;

public class ModemTest extends BaseJunitCase {
    
    private static Logger logger = LoggerFactory.getLogger(ModemTest.class);
    
    @Test
    public void testQueryByID() {
        InitCommObject commPool = new InitCommObject();
        commPool.addReadRptDataListener(null);
        commPool.setLinkAlarmListener(null);
        commPool.start();
        
        String str = "hello,world!";
        str = "王小华\n城中村8翔9栋901\n12-11 12:33:40";
        byte[] data = str.getBytes();
        String destination = "13480760840";
        logger.info(str);
        commPool.sendData(data, destination, CommModeConstants.SMS_MODE, null);
    }
}