package com.irongteng.example;

import dwz.common.util.StringUtils;
import java.io.IOException;

public class JavaMain {
    public static void test(int num) {
        switch(num) {
            case 1:
            case 2:
            case 3:
            case 4: 
                System.out.println(num);
                break;
            default:
                break;
        }
    }
    public static void main(String[] args) throws IOException {
        test(1);
        test(2);
        test(3);
        test(4);

        System.out.println(StringUtils.string2HexString("AT+CCED=0,1\r\n"));
        String line = "tcp=true \\# hello\\#，test#world";
        int i = line.indexOf('=');
        String key = line.substring(0, i).trim();
        String value = line.substring(i + 1).trim();
        if (value.contains("#") && value.indexOf("#") > value.indexOf("=")) {
            if (!value.contains("\\")) {
                value = value.substring(0, value.indexOf("#")).trim();
            } else {
                int index = 0;
                while (value.indexOf("#", index) != -1) {
                    System.out.println((value.indexOf("\\",index-2) + 1) +" : " + value.indexOf("#", index));
                    if (value.indexOf("\\",index-1) + 1 == value.indexOf("#", index)) {
                        index = value.indexOf("#", index + 1);
                    } else {
                        value = value.substring(0, index).trim();
                        break;
                    }
                    
                }
            }
        }
        
        System.out.println(key + ":" + value);
    }
}
