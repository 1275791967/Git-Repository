package com.irongteng.example;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class UrlTest {
    public static void main(String[] args) {
        URL url = null;
        try {
            url = new URL("http://api.map.baidu.com");
            InputStream in = url.openStream();
            System.out.println("连接正常");
            in.close();
        } catch (IOException e) {
            System.out.println("无法连接到：" + url.toString());
        }
        long num = 2005047155;
        System.out.println("" + Long.toHexString(num));
    }
}