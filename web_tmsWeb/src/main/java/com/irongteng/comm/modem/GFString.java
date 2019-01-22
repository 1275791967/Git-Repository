/**
 *-----------------------------------------------------------------------------
 * @ Copyright(c) 2004~2008  Huawei Technologies, Ltd. All Rights Reserved.
 *-----------------------------------------------------------------------------
 * FILE  NAME             : GFString.java
 * DESCRIPTION            :
 * PRINCIPAL AUTHOR       : Huawei Technologies Parlay Project Team
 * SYSTEM NAME            : SAG
 * MODULE NAME            : SAG 
 * LANGUAGE               : Java
 * DATE OF FIRST RELEASE  :
 *-----------------------------------------------------------------------------
 * @ Created on October 04, 2005
 * @ Release 1.0.0.0
 * @ Version 1.0
 * -----------------------------------------------------------------------------------
 * Date           Author          Version     Description
 * -----------------------------------------------------------------------------------
 * 2007-7-16     zhangping        1.0       Initial Create
 * -----------------------------------------------------------------------------------
 */
package com.irongteng.comm.modem;

public class GFString {
    /**
     * 把相临的两个字符对换，字符串长度为奇数时最后加“F”
     * 
     * @param src
     * @return
     */
    public static String interChange(String src) {
        String result = null;
        if (src != null) {
            if (src.length() % 2 != 0)
                src += "F";
            src += "0";// 多加1位为了后面截取字符串方便,实际上还是没有增加此0
            result = "";
            for (int i = 0; i < src.length() - 2; i += 2) {
                result += src.substring(i + 1, i + 2);
                result += src.substring(i, i + 1);
            }
        }
        return result;
    }

    public static String gb2unicode(String gbString) {
        String result = "";
        
        char[] c;
        int value;
        if (gbString == null)
            return null;
        String temp;
        c = new char[gbString.length()];
        String sb = gbString;
        sb.getChars(0, sb.length(), c, 0);
        for (int i = 0; i < c.length; i++) {
            value = c[i];
            temp = Integer.toHexString(value);
            result += fill(temp, 4);
            // -------------------------------------------------------------------------
        }
        return result.toUpperCase();
    }

    public static String fill(String temp, int totalbit) {
        String t = temp;
        while (t.length() < totalbit) {
            t = "0" + t;
        }
        return t;
    }

    /**
     * 对7-BIT编码进行解码
     * 
     * @param src
     *            十六进制的字符串，且为偶数个
     * @return 源字符串
     */
    public static String decode7bit(String src) {
        String result = null;
        int[] b;
        String temp;
        byte srcAscii;
        byte left = 0;

        if (src != null && src.length() % 2 == 0) {
            result = "";
            b = new int[src.length() / 2];
            temp = src + "0";
            int k;
            for (int i = 0, j = 0; i < temp.length() - 2; i += 2, j++) {
                b[j] = Integer.parseInt(temp.substring(i, i + 2), 16);

                k = j % 7;
                srcAscii = (byte) (((b[j] << k) & 0x7F) | left);
                result += (char) srcAscii;
                left = (byte) (b[j] >>> (7 - k));
                if (k == 6) {
                    result += (char) left;
                    left = 0;
                }
                if (j == src.length() / 2)
                    result += (char) left;
            }
        }
        return result;
    }

    public static String decode8bit(String src) {
        String temp = src;
        String result = "";
        for (int i = 0; i < src.length() - 2; i += 2)
            result += (char) Integer.parseInt(temp.substring(i, i + 2), 16);
        return result;
    }

    /**
     * 把UNICODE编码的字符串转化成汉字编码的字符串
     * 
     * @param hexString
     * @return
     */
    public static String unicode2gb(String hexString) {
        StringBuilder sb = new StringBuilder();
        if (hexString == null)
            return null;
        for (int i = 0; i + 4 <= hexString.length(); i = i + 4) {
            try {
                int j = Integer.parseInt(hexString.substring(i, i + 4), 16);
                sb.append((char) j);
            } catch (NumberFormatException e) {
                return hexString;
            }
        }
        return sb.toString();
    }

    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp;
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
            if (n < b.length - 1)
                hs = hs + ":";
        }
        return hs.toUpperCase();
    }

    public static String byte2hex(byte b) {
        String hs = "";
        String stmp = Integer.toHexString(b & 0XFF);
        if (stmp.length() == 1)
            hs = hs + "0" + stmp;
        else
            hs = hs + stmp;
        return hs.toUpperCase();
    }

    /*
     * //字节码转换成16进制字符串 public static String byte2hex(byte bytes[]){ StringBuffer
     * retString = new StringBuffer(); for (int i = 0; i < bytes.length; ++i) {
     * retString.append(Integer.toHexString(0x0100 + (bytes[i] &
     * 0x00FF)).substring(1).toUpperCase()); } return retString.toString(); }
     * //将16进制字符串转换成字节码 public static byte[] hex2byte(String hex) { byte[] bts =
     * new byte[hex.length() / 2]; for (int i = 0; i < bts.length; i++) { bts[i]
     * = (byte) Integer.parseInt(hex.substring(2*i, 2*i+2), 16); } return bts; }
     */
    
    public static String encode7bit(String src) {
        String result = null;
        String hex;
        byte value;
        if (src != null && src.length() == src.getBytes().length && src.length() == 1)
            result = GFString.byte2hex(src.getBytes());
        else if (src != null && src.length() == src.getBytes().length) {
            result = "";
            byte left = 0;
            byte[] b = src.getBytes();
            int j;
            for (int i = 0; i < b.length; i++) {
                j = i & 7;
                if (j == 0)
                    left = b[i];
                else {
                    value = (byte) ((b[i] << (8 - j)) | left);
                    left = (byte) (b[i] >> j);
                    hex = GFString.byte2hex(value);
                    result += hex;
                    if (i == b.length - 1)
                        result += GFString.byte2hex(left);
                }
            }
            result = result.toUpperCase();
        }
        return result;
    }

}
