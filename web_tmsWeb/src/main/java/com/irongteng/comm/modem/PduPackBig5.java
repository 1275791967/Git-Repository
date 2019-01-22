package com.irongteng.comm.modem;

public class PduPackBig5 {
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

    // 国际区号
    private final String[] areas = { "86", "886" };
    
    private String src;
    
    public String getAreaByPhone(String phone) {
        for (String str : areas) {
            if (phone.startsWith("+" + str)) {
                return str;
            }
            if (phone.startsWith(str) && phone.length() >= 12) {
                return str;
            } else if (phone.length() >= 11 && phone.startsWith("1")) {
                return "86";
            }
        }
        return "886";
    }
    
    public PduPackBig5() {
        smscLen = "08";
        smscFormat = "91";
        addrLen = 11;
        addrFormat = "81";
    }
    
    public PduPackBig5(String src) {
        
        this.src = src;
        
        if (src != null && src.length() > 44) {
            
            if (!src.startsWith("00")) { //有短信网关
                String temp = src.substring(4, 18);
                
                smsc = temp;
                
                temp = src.substring(22, 24);
                addrLen = Integer.parseInt(temp, 16);
                if (addrLen % 2 == 0)
                    temp = src.substring(26, 26 + addrLen);
                else
                    temp = src.substring(26, 26 + addrLen + 1);
                
                addr = temp;
                
                if (addrLen % 2 == 0) {
                    msgCoding = src.substring(26 + addrLen + 2, 26 + addrLen + 4);
                    msgLen = Integer.parseInt(src.substring(26 + addrLen + 6, 26 + addrLen + 8), 16);
                    temp = src.substring(26 + addrLen + 4 + 16);
                } else {
                    msgCoding = src.substring(26 + addrLen + 3, 26 + addrLen + 5);
                    msgLen = Integer.parseInt(src.substring(26 + addrLen + 7, 26 + addrLen + 9), 16);
                    temp = src.substring(26 + addrLen + 5 + 4);
                }
                
                msgContent = temp;
                
            } else {
                
                String temp = src.substring(6, 8);
                addrLen = Integer.parseInt(temp, 16);
                if (addrLen % 2 == 0)
                    temp = src.substring(10, 10 + addrLen);
                else
                    temp = src.substring(10, 10 + addrLen + 1);
                
                addr = temp;
                
                if (addrLen % 2 == 0) {
                    msgCoding = src.substring(10 + addrLen + 2, 10 + addrLen + 4);
                    msgLen = Integer.parseInt(src.substring(10 + addrLen + 6, 10 + addrLen + 8), 16);
                    temp = src.substring(10 + addrLen + 4 + 16);
                } else {
                    msgCoding = src.substring(10 + addrLen + 3, 10 + addrLen + 5);
                    msgLen = Integer.parseInt(src.substring(10 + addrLen + 7, 10 + addrLen + 9), 16);
                    temp = src.substring(10 + addrLen + 5 + 4);
                }
                
                msgContent = temp;
            }
        }
    }
    
    public void setSmsc(String s) {
        if (s != null) {
            String centerNo;
            if (s.length() == 9 && s.substring(0, 2).equals("93")) {
                centerNo = "886" + s;
            } else if (s.length() == 11) {
                centerNo = s;
            } else if (s.length() == 12 && s.substring(0, 5).equals("88693")) {
                centerNo = s;
            } else if (s.length() == 13 && s.substring(0, 6).equals("+88693")) {
                centerNo = s.substring(1);
            } else if (s.length() == 14 && s.substring(0, 3).equals("+86")) {
                centerNo = s.substring(1);
            } else
                return;
            
            this.smsc = GFString.interChange(centerNo);
        }
    }
    
    public String getSmsc() {
        String smscContent = GFString.interChange(smsc);
        if (smscContent != null && smscContent.length() > 1) {
            smscContent = smscContent.substring(0, smscContent.length() - 1);
            if (smscContent.length() == 13)
                smscContent = smscContent.substring(2);
        }
        return smscContent;
    }
    
    public void setAddr(String ad) {
        if (ad != null) {
            String centerNo;
            if (ad.length() == 9) {
                centerNo = "886" + ad;
            } else if (ad.length() == 11) {
                centerNo = ad;
            } else if (ad.length() == 12 && ad.substring(0, 3).equals("886")) {
                centerNo = ad;
            } else if (ad.length() == 13 && ad.substring(0, 4).equals("+886")) {
                centerNo = ad.substring(1);
            } else if (ad.length() == 14 && ad.substring(0, 3).equals("+86")) {
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
    
    public String getAddr() {
        
        String addrContent;
        
        addrContent = GFString.interChange(addr);
        // 去掉为补齐为偶数加上的那一位
        if (addrContent != null && addrContent.length() % 2 == 0) {
            addrContent = addrContent.substring(0, addrContent.length() - 1);
            if (addrContent.length() == 13)// 如果前面有86，去掉它
                addrContent = addrContent.substring(2);
        }
        return addrContent;
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
    
    public int getMsgCoding() {
        int encoding = 0;
        
        if("08".equals(msgCoding)) encoding = 8;
        else if("04".equals(msgCoding)) encoding = 4;
        
        return encoding;
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
    
    public String getMsgContent() {
        String content;
        if (msgCoding.equals("08"))
            content = GFString.unicode2gb(msgContent);
        else
            content = GFString.decode7bit(msgContent);
        return content;
    }
    
    /** */
    /**
     * 
     * @return 经过PDU编码的结果,十六进制字符串形式
     */
    public String getCodedResult() {
        
        if (src!=null) return src;
        
        final String tp_mti = "11";
        final String tp_mr = "00";
        final String tp_pid = "00";
        final String tp_vp = "FF";
        
        StringBuilder result = new StringBuilder();
        if (smsc!=null) {
            result.append(smscLen);
            result.append(smscFormat);
            result.append(smsc);
        } else {
            result.append("00");
        }
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
        /*
        System.out.println("smscLen:" + smscLen);
        System.out.println("smscFormat:" + smscFormat);
        System.out.println("smsc:" + smsc);
        System.out.println("addrLen:" + addrLen);
        System.out.println("addrFormat:" + addrFormat);
        System.out.println("addr:" + addr);
        System.out.println("msgCoding:" + msgCoding);
        System.out.println("msgLen:" + msgLen);
        System.out.println("msgContent:" + msgContent);
        */
        return result.toString().toUpperCase();
    }

    public int getMsgLen() {
        return msgLen;
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
    
    public int size() {
        int len;
        
        String content = getCodedResult();
        
        if (smsc != null)
            len = (content.length() - 18) / 2;
        else 
            len = content.length()/2 - 1;
        
        return len;
    }
    
    public static void main(String agrs[]) {
        
        PduPackBig5 pdu = new PduPackBig5();
        pdu.setAddr("13480760840");
        pdu.setMsgCoding(8);
        pdu.setMsgContent("王小华\n城中村8巷9栋901\n12-11 12:33:40");
        pdu.setSmsc("+8613010888500");
        String result = pdu.getCodedResult();
        System.out.println("smsc:" + pdu.getSmsc());
        System.out.println("addr:" + pdu.getAddr());
        System.out.println("msgCoding:" + pdu.getMsgCoding());
        System.out.println("msgLen:" + pdu.getMsgLen());
        System.out.println("msgContent:" + pdu.getMsgContent());
        System.out.println("CodeResult:" + result);
        System.out.println("size:" + pdu.size());
        
        PduPackBig5 pduDecode = new PduPackBig5(result);
        
        System.out.println("CodeResult:" + pduDecode.getCodedResult());
        
        System.out.println("smsc:" + pduDecode.getSmsc());
        System.out.println("addr:" + pduDecode.getAddr());
        System.out.println("msgCoding:" + pduDecode.getMsgCoding());
        System.out.println("msgLen:" + pduDecode.getMsgLen());
        System.out.println("msgContent:" + pduDecode.getMsgContent());
        System.out.println("size:" + pduDecode.size());
        String str = "133123456789";
        System.out.println("str=" + str + ";area=" + pdu.getAreaByPhone(str));
        /*
        str = "86133123456789";
        System.out.println("str=" + str + ";area=" + pdu.getAreaByPhone(str));
        str = "+86133123456789";
        System.out.println("str=" + str + ";area=" + pdu.getAreaByPhone(str));
        str = "911514540";
        System.out.println("str=" + str + ";area=" + pdu.getAreaByPhone(str));
        str = "886911514540";
        System.out.println("str=" + str + ";area=" + pdu.getAreaByPhone(str));
        str = "+886911514540";
        System.out.println("str=" + str + ";area=" + pdu.getAreaByPhone(str));
        str = "14540";
        System.out.println("str=" + str + ";area=" + pdu.getAreaByPhone(str));
        */
    }
}
