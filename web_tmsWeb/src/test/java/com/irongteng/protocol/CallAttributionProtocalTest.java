package com.irongteng.protocol;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CallAttributionProtocalTest{
    
    public static final String tmpDevMAC = "fe2010042";
    public static final String tmpUserMAC = "e3:ab:ba:ef:";
    public static final String tmpApMAC = ":3e:ef:ab:cd:";
    public static final String tmpIMEI = "1234567";
    public static final String tmpIMSI = "7654321";
    
    public static final String tmpDevIP = "192.168.168.";
    public static final String tmpApName = "testApName_";
    
    private static Logger logger = LoggerFactory.getLogger(CallAttributionProtocalTest.class);
    
    @Test
    public void testDecode_CMCC() {
        
        try {
            CallAttributionProtocal protocol = new CallAttributionProtocal();
            System.out.println(protocol.decode("460014853500090"));
            System.out.println(protocol.decode("460029140147218"));
            System.out.println(protocol.decode("460002601844048").substring(0, protocol.decode("460002601844048").length()-4));
            System.out.println(protocol.decode("460002601844048"));
            System.out.println(protocol.decode("460000040336723"));
            System.out.println(protocol.decode("460002241269678"));
            System.out.println(protocol.decode("460004090425414"));
            System.out.println(protocol.decode("460002241269678"));
            System.out.println(protocol.decode("460002591840997"));
            System.out.println(protocol.decode("460006500743513"));
            System.out.println(protocol.decode("460003173297272"));
            System.out.println(protocol.decode("460000172999867"));
            System.out.println(protocol.decode("460002506726372"));
            System.out.println(protocol.decode("460002705388616"));
            System.out.println(protocol.decode("460001690311719"));
            System.out.println(protocol.decode("460001690311719"));
            logger.info("解析完成");
        } finally {
            
        }
    }
    
    @Test
    public void testDecode_CUCC() {
        String imsi = "460028833195203";
        //imsi = "460006191911214";
        imsi = "46003074500ABCD";
        imsi = "46003741000ABCD";
        //imsi = "46003139000ABCD";
        System.out.println(imsi.substring(3, 5));
        System.out.println(imsi.substring(5, 6));
        System.out.println(imsi.substring(6, 10));
        
        CallAttributionProtocal protocol = new CallAttributionProtocal();
        String no = protocol.decode(imsi);
        System.out.println(no + ":" + protocol.getCommOperator());
    }

    @Test
    public void testDecode_CUCC_1() {
        
        try {
            CallAttributionProtocal protocol = new CallAttributionProtocal();
            System.out.println(protocol.decode("460010529706720"));
            logger.info("解析完成");
        } finally {
            
        }
    }
    
    @Test
    public void testDecode_CTCC() {
        
        try {
            CallAttributionProtocal protocol = new CallAttributionProtocal();
            System.out.println(protocol.decode("460036990990756")); //153
            System.out.println(protocol.decode("460030902001123")); //133 -09
            System.out.println(protocol.decode("460030309991123")); //133 -03
            System.out.println(protocol.decode("460030130002314")); //180 -01
            System.out.println(protocol.decode("460030230000214")); //180 -02
            System.out.println(protocol.decode("460030370990215")); //180 -03
            System.out.println(protocol.decode("460030400001324")); //180 -04
            System.out.println(protocol.decode("460030731997894")); //180 -07
            System.out.println(protocol.decode("460030744991325")); //10649 -07
            System.out.println(protocol.decode("460030745992567")); //181 -07
            System.out.println(protocol.decode("460031201004567")); //181 -12
            System.out.println(protocol.decode("460037411991278")); //1700 -74
            System.out.println(protocol.decode("460031265001359")); //177 -12
            
            
            logger.info("电信IMSI解析完成");
        } finally {
            
        }
    }
    
    @Test
    public void testDecode() {
    	CallAttributionProtocal protocol = new CallAttributionProtocal();
    	System.out.println(protocol.decode("460021234512345")); //153
    }
}