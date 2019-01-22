/**
 *-----------------------------------------------------------------------------
 * @ Copyright(c) 2004~2008  Huawei Technologies, Ltd. All Rights Reserved.
 *-----------------------------------------------------------------------------
 * FILE  NAME             : PduPack.java
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

public class PduPack {
    // 短消息中心号长度
    private String smscLen;

    private String smscFormat;

    // 短消息中心号
    private String smsc;

    // 源地址长度
    private int addrLen;

    private String addrFormat;

    // 源地址
    private String addr;

    // 短消息内容编码方式,tp_dcs
    private String msgCoding;

    // 时间戳,tp_scts
    // private String timestamp;

    private int msgLen;

    // 短消息内容,tp_ud
    private String msgContent;

    public PduPack() {
        smscLen = "08";
        smscFormat = "91";
        addrLen = 13;
        addrFormat = "81";
    }

    public PduPack(String src) {

        if (src != null && src.length() > 44) {
            String temp = src.substring(4, 18);
            smsc = GFString.interChange(temp);
            if (smsc != null && smsc.length() > 1) {
                smsc = smsc.substring(0, smsc.length() - 1);
                if (smsc.length() == 13)
                    smsc = smsc.substring(2);
            }

            temp = src.substring(20, 22);
            addrLen = Integer.parseInt(temp, 16);
            if (addrLen % 2 == 0)
                temp = src.substring(24, 24 + addrLen);
            else
                temp = src.substring(24, 24 + addrLen + 1);
            addr = GFString.interChange(temp);
            // 去掉为补齐为偶数加上的那一位
            if (addr != null && addr.length() % 2 == 0) {
                addr = addr.substring(0, addr.length() - 1);
                if (addr.length() == 13)// 如果前面有86，去掉它
                    addr = addr.substring(2);
            }
            if (addrLen % 2 == 0) {
                msgCoding = src.substring(24 + addrLen + 2, 24 + addrLen + 4);
                temp = src.substring(24 + addrLen + 4 + 16);
            } else {
                msgCoding = src.substring(24 + addrLen + 3, 24 + addrLen + 5);
                temp = src.substring(24 + addrLen + 5 + 16);
            }
            if (msgCoding.equals("08"))
                msgContent = GFString.unicode2gb(temp);
            else
                msgContent = GFString.decode7bit(temp);

        }
    }

    public void setSmsc(String s) {
        if (s != null) {
            String centerNo;
            if (s.length() == 11 && s.substring(0, 2).equals("13")) {
                centerNo = "86" + s;
            } else if (s.length() == 13 && s.substring(0, 4).equals("8613")) {
                centerNo = s;
            } else if (s.length() == 14 && s.substring(0, 5).equals("+8613")) {
                centerNo = s.substring(1);
            } else
                return;

            this.smsc = GFString.interChange(centerNo);
        }
    }

    public void setAddr(String ad) {
        if (ad != null) {
            String centerNo;
            if (ad.length() == 11 && ad.substring(0, 2).equals("13")) {
                centerNo = "86" + ad;
            } else if (ad.length() == 13 && ad.substring(0, 4).equals("8613")) {
                centerNo = ad;
            } else if (ad.length() == 14 && ad.substring(0, 5).equals("+8613")) {
                centerNo = ad.substring(1);
            } else if (ad.length() > 0) {// 特服号
                addrFormat = "A1";
                addrLen = ad.length();
                centerNo = ad;
            } else
                return;
            addr = GFString.interChange(centerNo);
        }
    }

    /** */
    /**
     * 设置编码方式
     * 
     * @param encoding
     *            0:表示7-BIT编码 4:表示8-BIT编码 8:表示UCS2编码
     */
    public void setMsgCoding(int encoding) {
        switch (encoding) {
            case 8:
                msgCoding = "08";
                break;
            case 4:
                msgCoding = "04";
                break;
            default:
                msgCoding = "00";
                break;
        }
    }

    /** */
    /**
     * 短消息内容
     * 
     * @param content
     */
    public void setMsgContent(String content) {
        if (content != null) {
            if (content.length() == content.getBytes().length) {
                msgCoding = "00";
                msgLen = content.getBytes().length;
                msgContent = encode7bit(content);

            } else {
                msgCoding = "08";
                msgContent = GFString.gb2unicode(content);
                if (msgContent != null)
                    msgLen = msgContent.length() / 2;

            }
            
            if (msgContent != null) {
                msgContent = msgContent.toUpperCase();

            }
        }
    }

    /** */
    /**
     * 
     * @return 经过PDU编码的结果,十六进制字符串形式
     */
    public String getCodedResult() {
        //String result = null;
        final String tp_mti = "11";
        final String tp_mr = "00";
        final String tp_pid = "00";
        final String tp_vp = "FF";
        /*
        if (smsc != null && addr != null && msgContent != null) {
            result = smscLen + smscFormat + smsc + tp_mti + tp_mr + GFString.byte2hex((byte) addrLen) + addrFormat
                    + addr + tp_pid + msgCoding + tp_vp + GFString.byte2hex((byte) msgLen) + msgContent;
            result = result.toUpperCase();
        }
        */
        StringBuilder result = new StringBuilder();
        //if (smsc!=null) {
        //    result.append(smscLen);
        //    result.append(smscFormat);
        //    result.append(smsc);
        //} else {
            result.append("00");
        //}
        result.append(tp_mti);
        result.append(tp_mr);
        result.append(GFString.byte2hex((byte) addrLen));
        result.append(addrFormat);
        result.append(addr);
        result.append(tp_pid);
        result.append(msgCoding);
        result.append(tp_vp);
        result.append(GFString.byte2hex((byte) msgLen));
        result.append(msgContent);
        
        System.out.println(smscLen);
        System.out.println(smscFormat);
        System.out.println("smsc:" + smsc);
        System.out.println(addrLen);
        System.out.println(addrFormat);
        System.out.println("addr:" + addr);
        System.out.println(msgCoding);
        System.out.println(msgLen);
        System.out.println(msgContent);
        
        return result.toString().toUpperCase();
    }

    public String getAddr() {
        return addr;
    }

    public String getMsgCoding() {
        return msgCoding;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public int getMsgLen() {
        return msgLen;
    }

    public String getSmsc() {
        return smsc;
    }
    
    public static String encode7bit(String src) {
        String result = null;
        String hex;
        byte value;

        if (src != null && src.length() == src.getBytes().length) {
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

    public static void main(String agrs[]) {
        PduPack pdu = new PduPack();
        pdu.setAddr("13480760840");
        pdu.setMsgCoding(8);
        pdu.setMsgContent("王小华\n城中村8翔9栋901\n12-11 12:33:40");
        //pdu.setSmsc("+8613800755500");
        String result = pdu.getCodedResult();
        System.out.println("result:" + result);
    }
}
