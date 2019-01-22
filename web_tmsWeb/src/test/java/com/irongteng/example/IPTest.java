package com.irongteng.example;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class IPTest {
    public static void main(String[] args) throws IOException {
        try {
            String[] ips = "180.97.33.90".split("\\.");
            byte[] ipBytes = new byte[ips.length];
            System.out.println(ips.length);
            for (int i=0; i<ips.length; i++) {
                ipBytes[i] = (byte)Short.parseShort(ips[i]);
            };
            //InetAddress ad = InetAddress.getByName("180.97.33.90");
            InetAddress ad = InetAddress.getByAddress(ipBytes);
            boolean state = ad.isReachable(1000);// 测试是否可以达到该地址
            if (state)
                System.out.println("连接成功" + ad.getHostAddress());
            else
                System.err.println("连接失败");
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.err.println("连接失败");
        }
    }
}