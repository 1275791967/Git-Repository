package com.irongteng.comm.modem;

public class Ucs2Tool {
    
    /** 
     * UCS2解码 
     *  
     * @param src 
     *            UCS2 源串 
     * @return 解码后的UTF-16BE字符串 
     */  
    public static String DecodeUCS2(String src) {  
        byte[] bytes = new byte[src.length() / 2];  
      
        for (int i = 0; i < src.length(); i += 2) {  
            bytes[i / 2] = (byte) (Integer  
                    .parseInt(src.substring(i, i + 2), 16));  
        }  
        String reValue="";  
        try {  
            reValue = new String(bytes, "UTF-16BE");  
        } catch (Exception e) {  

        }  
        return reValue;  
    }  
    
    /** 
     * UCS2编码 
     *  
     * @param src 
     *            UTF-16BE编码的源串 
     * @return 编码后的UCS2串 
     */  
    public static String EncodeUCS2(String src) {  
          
        byte[] bytes=null;  
        try {  
            bytes = src.getBytes("UTF-16BE");  
        } catch (Exception e) {  
            
        }        
        StringBuilder reValue = new StringBuilder();  
        StringBuffer tem = new StringBuffer();  
        for (int i = 0; i < bytes.length; i++) {  
            tem.delete(0, tem.length());  
            tem.append(Integer.toHexString(bytes[i] & 0xFF));  
            if(tem.length()==1){  
                tem.insert(0, '0');  
            }  
            reValue.append(tem);  
        }  
        return reValue.toString().toUpperCase();  
    }  
    
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        String uStr=Ucs2Tool.EncodeUCS2("汉1");
        System.out.println(uStr);
        String str=Ucs2Tool.DecodeUCS2(uStr);
        System.out.println(str);
        

    }

}
