package dwz.common.util.encrypt;

import java.security.MessageDigest;

public class MD5 {
    // 全局数组
    private final static String[] strDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
    
    public MD5() {
    }

   // 返回形式为数字跟字符串
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    // 返回形式只为数字
    private static String byteToNum(byte bByte) {
        int iRet = bByte;
        System.out.println("iRet1=" + iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        return String.valueOf(iRet);
    }

    // 转换字节数组为16进制字串
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    public static String GetMD5Code(String strObj) {
        String resultString = null;
        try {
            resultString = new String(strObj);
            MessageDigest md = MessageDigest.getInstance("MD5");
            // md.digest() 该函数返回值为存放哈希值结果的byte数组
            resultString = byteToString(md.digest(strObj.getBytes()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return resultString;
    }
    
    public static byte[] getMD5Bytes(String strObj){
    	byte[] data = new byte[20];
    	try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            // md.digest() 该函数返回值为存放哈希值结果的byte数组
            data = md.digest(strObj.getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		return data;
    }
    
    public static byte[] getMD5Bytes(byte[] byteList){
        byte[] data = new byte[20];
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            // md.digest() 该函数返回值为存放哈希值结果的byte数组
            data = md.digest(byteList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return data;
    }
    
    public static boolean isEqual(String digesta, String digestb){
    	try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(digesta.getBytes(), digestb.getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    	return false;
    }

}
