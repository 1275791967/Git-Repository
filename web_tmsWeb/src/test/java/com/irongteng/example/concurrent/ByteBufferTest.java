package com.irongteng.example.concurrent;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import dwz.common.util.StringUtils;

public class ByteBufferTest {
    
    public static void main(String args[]) throws Exception {
        int deviceNum = 0x12345678;
        int deviceChannel = 0xffcceedd;
        String deviceName = "设备名称"; 
        String date = "20050411122356";
        
        ByteBuffer buffer = ByteBuffer.allocate(35).order(ByteOrder.LITTLE_ENDIAN);
        buffer.putInt(deviceNum);
        buffer.putInt(deviceChannel);
        
        System.out.println("deviceName hex:" + StringUtils.bytes2HexString(deviceName.getBytes()));
        buffer.put(StringUtils.String2FixedBytes(deviceName, 20));
        buffer.put(StringUtils.bytes2FixedBytes(StringUtils.hexString2Bytes(date), 7));
        buffer.flip();
        
        System.out.println(StringUtils.bytes2HexString(buffer.array()));
        System.out.println(Integer.toHexString(buffer.getInt()));
        System.out.println(Integer.toHexString(buffer.getInt()));
        byte[] dst = new byte[20];
        //System.out.println(buffer.position());
        buffer.get(dst);
        System.out.println(new String(dst).trim());
        
        byte[] sdate= new byte[7];
        buffer.get(sdate);
        
        System.out.println(new String(sdate).trim());
        
        System.out.println(StringUtils.bytes2HexStringNS(sdate).trim().substring(0, 14));
        
        String devName = "设备名称";
        StringBuffer sb = new StringBuffer(20);
        sb.append(devName);
        System.out.println("dev size:" + sb.toString());
    }
    
}