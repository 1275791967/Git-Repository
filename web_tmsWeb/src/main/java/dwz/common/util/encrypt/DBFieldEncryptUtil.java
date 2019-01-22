package dwz.common.util.encrypt;

import java.util.Random;

import dwz.common.util.StringUtils;

public class DBFieldEncryptUtil {
    
    /*// 加密
    public String getEncrptDBPhone(String phone){
        String randStr = getRandStr();
        int randInt = new Random().nextInt(255)+1;
        return encryptDBPhone(randStr, randInt, phone);
    }
    // 解密
    public String getDecrptDBPhone(String etDBPhone){
        return decryptDBPhone(etDBPhone);
    }*/
   
    /**
     * 
     * @return
     */
    private static String getRandStr(){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<15;i++){
            sb.append(EncryptCanstans.randStrSrc.charAt(new Random().nextInt(62)));
        }
        return sb.toString();
    }
    
    /**
     * 根据数据库的加密电话号码得到明文 的电话号码
     * @param etDBPhone
     * @return
     */
    private static String decryptDBPhone(String etDBPhone){ 
        String randStr = etDBPhone.substring(0, 15);
        int randInt = Integer.parseInt(etDBPhone.substring(15, 17), 16);
        byte[] keys = getDBEncryptKey(randStr, randInt);
        int index = 0;
        String ss = etDBPhone.substring(17);
//        System.out.println(ss);
        byte[] datas = StringUtils.hexString2Bytes(ss);
        for(int i=0;i<datas.length;i++){
            datas[i] = (byte)((datas[i])^keys[index]);
            index = ++index % keys.length;
        }
        return new String(datas);
    }

    /**
     * 得到加密电话号码
     * @param randStr
     * @param randInt
     * @param phone
     * @return
     */
    private static String encryptDBPhone(String randStr, int randInt, String phone){
        String head = randStr+StringUtils.getStandarHexStr((byte)randInt);
//      System.out.println(head);
        byte[] keys = getDBEncryptKey(randStr, randInt);
        int index = 0;
        byte[] phones = phone.getBytes();
        for(int i=0;i<phones.length;i++){
            phones[i] = (byte)((phones[i])^keys[index]);
            index = ++index % keys.length;
        }
        return head+StringUtils.bytes2HexStringNS(phones);
    }
   
    /**
     * 根据随机字符串和随机数，获得数据库字段的加密密钥
     * @param randStr
     * @param randInt
     * @return
     */
    private static byte[] getDBEncryptKey(String randStr, int randInt){
        int quot = randInt / randStr.length();
        int remainder = randInt % randStr.length();
        byte[] randSS = randStr.getBytes();
        byte[] keys = new byte[randSS.length+3];
        keys[0] = (byte) remainder;
        keys[1] = (byte) quot;
        keys[2] = (byte) randInt;
        for(int i=1;i<=randSS.length;i++){
            keys[i+2] = randSS[randSS.length-i];
        }
        return keys;
    }
    
    public static void main(String[] args) {
        String phone = "1524897377169,13418490770,134135695704,861524897479680,+861524898181771";
        String[] ss = phone.split(",");
        for(int i=0;i<ss.length;i++){
            String randStr = getRandStr();
            int randInt = new Random().nextInt(255)+1;
            String etDBPhone = encryptDBPhone(randStr, randInt, ss[i]);
            String tel = decryptDBPhone(etDBPhone);
            System.out.println(String.format("< %s >< %s >< %s >", ss[i], etDBPhone, tel));
        }
    }

}
