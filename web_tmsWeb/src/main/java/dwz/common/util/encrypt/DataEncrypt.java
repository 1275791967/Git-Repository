package dwz.common.util.encrypt;


import java.util.Random;

import com.irongteng.Unit.Buffer;
import com.irongteng.Unit.Bytes;

public class DataEncrypt {
    private final String privateKey = "39eda38294edf8948a9282";
    private static DataEncrypt instance = new DataEncrypt();
    
    public static DataEncrypt getInstance(){
        return instance;
    }
    
    //获取参数编号0x0A18的鉴权码
    public byte[] getTzmMD5(String tzm){
        try{
            int len = privateKey.length()+20;
            Buffer buffer = new Buffer(len);
            buffer.push(privateKey);
            buffer.push(new Bytes(tzm, 20));
            byte[] bs = MD5.getMD5Bytes(buffer.getByte());
//            System.out.println("私钥串行特征码的数据是："+Arrays.toString(buffer.getByte()));
//            System.out.println("私钥串行特征码计算得到的MD5码是：："+Arrays.toString(bs));
//            byte[] bs = MD5.getMD5Bytes(privateKey+tzm);
            byte[] dest = new byte[20];
            System.arraycopy(bs, 0, dest, 0, bs.length);
            return dest;  
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        
    }
    
    //获取参数编号0x0A19的鉴权码
    public byte[] getEncryptData(String tzm, byte[] publicKeyBs){
        int len = privateKey.length()+publicKeyBs.length+20;
        
        try{
           Buffer buffer = new Buffer(len);
           buffer.push(privateKey);
           buffer.push(new Bytes(publicKeyBs));
           buffer.push(new Bytes(tzm, 20));
//           System.out.println("私钥 == "+Arrays.toString(privateKey.getBytes()));
//           System.out.println("公钥 == "+Arrays.toString(publicKeyBs));
//           System.out.println("特征码 == "+Arrays.toString(new Bytes(tzm, 20).getBytes()));
           byte[] md5Bs = MD5.getMD5Bytes(buffer.getByte());
//           System.out.println("私钥、公钥、特征码串行的数据是："+Arrays.toString(buffer.getByte()));
//           System.out.println("私钥、公钥、特征码串行后计算得到的MD5码是：："+Arrays.toString(md5Bs));
           byte[] dest = new byte[20];
           System.arraycopy(md5Bs, 0, dest, 0, md5Bs.length);
           return dest;
        }catch(Exception e){
           e.printStackTrace(); 
           return null;
        }
        /*byte[] keyBs = privateKey.getBytes();
        byte[] tzmBs = tzm.getBytes();
        int len = keyBs.length+publicKeyBs.length+tzmBs.length;
        byte[] bs = new byte[len];
        System.arraycopy(keyBs, 0, bs, 0, keyBs.length);
        System.arraycopy(publicKeyBs, 0, bs, keyBs.length, publicKeyBs.length);
        System.arraycopy(tzmBs, 0, bs, keyBs.length+publicKeyBs.length, tzmBs.length);
        byte[] md5Bs = MD5.getMD5Bytes(bs);
        byte[] dest = new byte[20];
        System.arraycopy(md5Bs, 0, dest, 0, md5Bs.length);
        return dest;*/
    }
    
    
    /**
     * 获取IMSI,IMEI,电话号码的加密数据
     * @param imsi
     * @param packageID
     * @param encryptStr
     * @return
     */
    public byte[] getIMSIEncryptData(String imsi, int packageID, byte[] dataXorKeys){
        if(dataXorKeys == null) return null;
        if(imsi == null) imsi = "";
        byte[] imsiBytes = new Bytes(imsi, 20).getBytes();
        int len = dataXorKeys.length;
        for(int i=0;i<imsiBytes.length;i++){
            byte bValue = imsiBytes[i];
            bValue = (byte) (bValue+(byte)(packageID++));
            imsiBytes[i] = (byte)(bValue ^ dataXorKeys[i % len]);
        }
        return imsiBytes;
    }
    
    /**
     * 获取与IMSI,IMEI,Phone进行异或运算的异或数组
     * @param encryptBs
     * @return
     */
    public byte[] getDataXorKeys(byte[] authenCodes){
        if(authenCodes == null) return null;
        int len = privateKey.length()+authenCodes.length;
        Buffer buffer = new Buffer(len);
        try {
            buffer.push(privateKey);
            buffer.push(new Bytes(authenCodes));
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println("异或运算密钥MD5计算前的数据："+Arrays.toString(buffer.getByte()));
        byte[] dataXorKeys = MD5.getMD5Bytes(buffer.getByte());
        return dataXorKeys;
    }
    
    
    public String getIMSIDecrypt(byte[] data, int packageID, byte[] dataXorKeys){
        int len = dataXorKeys.length;
        for(int i=0;i<data.length;i++){
            byte bValue = (byte)(data[i] ^ dataXorKeys[i % len]);
            data[i] = (byte) (bValue-(byte)(packageID++));
            
        }
//        System.out.println("字段解密后的数据是："+Arrays.toString(data));
        String rs = new String(data);
        return rs;
    }
    
    public byte[] createRandomBytes(){
        Random random = new Random();
        byte[] randBytes = new byte[20];
        for(int i=0;i<16;i++){
            randBytes[i] = (byte) random.nextInt(255);
        }
        return randBytes;
    }
    
    
    public static void main(String[] args) {
        int packageID = 2561;
        DataEncrypt encrypt = new DataEncrypt();
        String tzm = "862341234332630";
        byte[] tzmBs = encrypt.getTzmMD5(tzm);
//        System.out.println("计算特征码的鉴权码 : "+Arrays.toString(tzmBs));
        byte[] publicKeyBs = new byte[20];
        Random random = new Random();
        for(int i=0;i<20;i++){
            int intValue = random.nextInt(255);
            System.out.print(intValue+",");
            publicKeyBs[i] = (byte) intValue;
        }
//        System.out.println("公钥是 : "+Arrays.toString(publicKeyBs));
        byte[] encryptBs = encrypt.getEncryptData(tzm, publicKeyBs);
//        System.out.println("加密鉴权码 : "+Arrays.toString(encryptBs));
        //得到加密后的Imsi数据
        byte[] data = encrypt.getIMSIEncryptData("460013489922330", packageID, encryptBs);
//        System.out.println(Arrays.toString(data));
        //解密IMSI的加密数据
        String str = encrypt.getIMSIDecrypt(data, packageID, encryptBs);
//        System.out.println(str);
        /*int packageID = 2561;
        DataEncrypt encrypt = new DataEncrypt();
        String tzmStr = encrypt.getTzmMD5("862341234332630");
        String encryptStr = encrypt.getEncryptStr(tzmStr, "ue12eyh2ew23wsfj0xxz");
        System.out.println(encryptStr);
        byte[] data = encrypt.getIMSIEncryptData("460013489922330", packageID, encryptStr);
        System.out.println(Arrays.toString(data));
//        byte[] data = encrypt.getIMSIEncryptData("460011234567890", packageID, encryptStr);
        String str = encrypt.getIMSIDecrypt(data, packageID, encryptStr);
        System.out.println(str);*/
    }

}
