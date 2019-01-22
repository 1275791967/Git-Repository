package com.irongteng.example;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

import dwz.common.util.StringUtils;

public class BASE64New {
    // 加密  
    public static byte[] encode(String str) {
        if (str != null) {
            try {
                return new Base64().encode(str.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }  
    // 加密  
    public static byte[] encode(byte[] srcDate) {
        if (srcDate != null) {
            return new Base64().encode(srcDate);
        }
        return null;
    } 
    
    // 解密  
    public static String decode(String s) {  
        byte[] b = null;  
        String result = null;  
        if (s != null) {  
            Base64 decoder = new Base64();  
            try {  
                b = decoder.decode(s);  
                result = new String(b, "utf-8");  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return result;  
    }

    // 解密  
    public static String decode(byte[] s) {  
        byte[] b = null;  
        String result = null;  
        if (s != null) {  
            Base64 decoder = new Base64();  
            try {  
                b = decoder.decode(s);  
                result = new String(b, "utf-8");  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return result;  
    }
    
    public static void main(String[] args) {
        System.out.println(new String(BASE64New.encode("EFCCAADD")));
        System.out.println(StringUtils.bytes2BinaryString(BASE64New.encode("EFCCAADD")));
        System.out.println(BASE64New.decode(BASE64New.encode("EFCCAADD")));
    }
}
