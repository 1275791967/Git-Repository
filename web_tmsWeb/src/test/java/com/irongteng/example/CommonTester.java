package com.irongteng.example;


import dwz.common.util.StringUtils;

public class CommonTester {
    private static int index  = 0;
    public static String bytes2BinaryString(byte[] bs) {
        StringBuffer sb = new StringBuffer();
        
        String ZERO="00000000";
        for   (int i = 0;i<bs.length;i++)   {
            String   s   =   Integer.toBinaryString(bs[i]);
            if (s.length() > 8)   {
                s   =   s.substring(s.length()   -   8);
            } else  if (s.length() < 8)   {
                s = ZERO.substring(s.length())   +   s;
            }
            sb.append(s + " ");
        }
        return sb.toString();
    }
    
    public Integer returnIndex() {
        return index++;
    }
    
    public static void main(String[] args) throws Exception{
        
        String str ="C:\\程序备份\\WebCenter";
        String hex = StringUtils.string2HexString(str);
        System.out.println(hex);
        System.out.println(StringUtils.hexString2String(hex));
        
        String hexStr = "433a5c";
        System.out.println(StringUtils.hexString2String(hexStr).replace(" ", ""));
        hexStr = "47 53 4D 31 39 36 D4 31 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00";
        System.out.println(new String(StringUtils.hexString2Bytes(hexStr), "GBK"));
        String path = "C:\\WebCenter-InnoDB\\tools\\Serv-U\\ServUDaemon.exe";
        hex = StringUtils.string2HexString(path);
        System.out.println(hex);
        
    }
    
}