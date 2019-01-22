package com.irongteng.protocol.common;

import java.io.UnsupportedEncodingException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irongteng.protocol.MonitorObject;
import com.irongteng.protocol.common.CommonMonitorObject;

import dwz.common.util.StringUtils;

public class CommonMonitorObjectTest{
    
    private static Logger logger = LoggerFactory.getLogger(CommonMonitorObjectTest.class);
    
    @Test
    public void testGetValue_NULL() {
        
        try {
            MonitorObject<Short, Short> wifi = new CommonMonitorObject((short)0x0011, (short)60111);
            String uint4 = wifi.getValue("string");
            logger.info(uint4);
            System.out.println("uint4:" + uint4);
            
            wifi.setValue(null, "string");
            byte[] values = wifi.toBytes();
            System.out.print("" 
                    + "bytes length:" + wifi.toBytes().length
                    + "\nObj object:" + StringUtils.getStringIdentifier(wifi.getObjectID())
                    + "\nObj length:" + (wifi.getLength() & 0xffff)
                    + "\nObj value:" + wifi.getValue("string")
                    + "\nbyte value object:" + StringUtils.getStringIdentifier(((values[3] & 0xFF) << 8) + (values[2] & 0xFF)) 
                    + "\nbyte value length:" + (((values[1] & 0xFF) << 8) + (values[0] & 0xFF)) 
                    + "\nbyte value byte length:" + values.length 
                    );
        } finally {
            
        }
    }
    
    @Test
    public void testGetValue_String() {
        
        try {
            MonitorObject<Short, Short> wifi = new CommonMonitorObject((short)0x0011, (short)60111);
            String uint4 = wifi.getValue("string");
            logger.info(uint4);
            
            wifi.setValue("单独", "string");
            
            byte[] values = wifi.toBytes();
            System.out.println(StringUtils.bytes2HexString(values));
            
            System.out.print("" 
                    + "bytes length:" + wifi.toBytes().length
                    + "\nObj object:" + Integer.toHexString(wifi.getObjectID())
                    + "\nObj length:" + (wifi.getLength() & 0xffff)
                    + "\nObj value:" + wifi.getValue("string")
                    + "\nbyte value object:" + Integer.toHexString(((values[3] & 0xFF) << 8) + (values[2] & 0xFF)) 
                    + "\nbyte value length:" + (((values[1] & 0xFF) << 8) + (values[0] & 0xFF)) 
                    );
        } finally {
            
        }
    }

    @Test
    public void testGetValue_String_SMS() {
        
        try {
            MonitorObject<Short, Short> wifi = new CommonMonitorObject((short)0x005E, (short)1024);
            String sms = wifi.getValue("string");
            logger.info(sms);
            
            wifi.setValue("单独", "string");
            
            byte[] values = wifi.toBytes();
            System.out.println(StringUtils.bytes2HexString(values));
            
            System.out.print("" 
                    + "bytes length:" + wifi.toBytes().length
                    + "\nObj object:" + Integer.toHexString(wifi.getObjectID())
                    + "\nObj length:" + (wifi.getLength() & 0xffff)
                    + "\nObj value:" + wifi.getValue("string")
                    + "\nbyte value object:" + Integer.toHexString(((values[3] & 0xFF) << 8) + (values[2] & 0xFF)) 
                    + "\nbyte value length:" + (((values[1] & 0xFF) << 8) + (values[0] & 0xFF)) 
                    );
        } finally {
            
        }
    }
    @Test
    public void testGetValue_unit4() {
        
        try {
            
            CommonMonitorObject wifi = new CommonMonitorObject((short)0x0011, (short)4);
            
            wifi.setValue("11223344", "uint4");
            
            byte[] values = wifi.toBytes();
            System.out.print("" 
                    + "\nbytes size:" + wifi.size()
                    + "\nbytes length:" + wifi.toBytes().length
                    + "\nObj object:" + Integer.toHexString(wifi.getObjectID())
                    + "\nObj length:" + wifi.getLength()
                    + "\nObj value:" + wifi.getValue("uint4")
                    + "\nbyte value object:" + Integer.toHexString(((values[3] & 0xFF) << 8) + (values[2] & 0xFF)) 
                    + "\nbyte value length:" + (((values[1] & 0xFF) << 8) + (values[0] & 0xFF)) 
                    );
        } finally {
            
        }
        
    }
    

    @Test
    public void testValue() {
        
        try {
            short s = -12345;
            System.out.println(s & 0xffff);
        } finally {
            
        }
        
    }
    
    @Test
    public void testEncodeChar() {
        String hex = "b5 a5 b6 c0 00 00 00 00 00 00";
        String str;
        try {
            str = new String(StringUtils.hexString2Bytes(hex), "gbk");
            System.out.println(str);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String val = "单独";
        System.out.println(StringUtils.bytes2HexString(val.getBytes()));
    }
}