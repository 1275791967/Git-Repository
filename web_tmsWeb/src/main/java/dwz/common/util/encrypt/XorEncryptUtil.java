package dwz.common.util.encrypt;

import java.util.Random;

import dwz.common.util.StringUtils;

public class XorEncryptUtil {
    public AuthentInfo info = null;
    
    public XorEncryptUtil(AuthentInfo info){
        this.info = info;
    }
    
    /**
     * 生成鉴权加密信息
     */
    public void createAuthentInfo(){
        createRandData();
        createAllEncryptCode();
    }
    
    /**
     * 生成随机字符串和随机数
     */
    private void createRandData(){
        if(info == null) return;
        int n = new Random().nextInt(5)+14;
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<n;i++){
            sb.append(EncryptCanstans.randStrSrc.charAt(new Random().nextInt(62)));
        }
        info.randStr = sb.toString();
        info.randInt = new Random().nextInt(255)+1;
    }
    
    /**
     * 得到加密的密码
     * @param password
     * @return
     */
    public String getEncryptPSW(String password){
        if (info == null) return "";
        String appendStr = String.format("%s%s%d%d", password, info.randStr, info.quot, info.remainder);
        byte[] bs = MD5.getMD5Bytes(appendStr);
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<8;i++){
            sb.append(StringUtils.getStandarHexStr(bs[i]));
        }
        return sb.toString();
    }
    
    /**
     * 生成所需的全部加密代码
     * @param password
     */
    private void createAllEncryptCode(){
        if(info == null) return;
        int len = info.randStr.length();
        info.quot = info.randInt / len;
        info.remainder = info.randInt % len;
        String appendStr = String.format("%s%d%d", info.randStr, info.quot, info.remainder);
        byte[] bs = MD5.getMD5Bytes(appendStr);
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<8;i++){
            sb.append(StringUtils.getStandarHexStr(bs[i]));
        }
        info.md5Code = sb.toString();
        int n = info.remainder > (len-info.remainder) ? info.remainder : (len-info.remainder);
        info.imsiETC = info.md5Code.substring(info.md5Code.length()-n);
        info.imeiETC = info.md5Code.substring(info.md5Code.length()-n+1);
        info.phoneETC = info.md5Code.substring(info.md5Code.length()-n+2);
    }
    
    /**
     * 得到加密后的IMSI数据
     * @param imsi
     * @return byte数组
     */
    public byte[] getEncryptIMSI(String imsi){
        if(info == null) return null;
        if("".equals(info.imsiETC)) return null;
        byte[] codes = imsi.getBytes();
        return xorEncrypt(codes, info.imsiETC);
    }
    
    /**
     * 得到加密后的IMEI数据
     * @param imei
     * @return byte数组
     */
    public byte[] getEncryptIMEI(String imei){
        if(info == null) return null;
        if("".equals(info.imeiETC)) return null;
        byte[] codes = imei.getBytes();
        return xorEncrypt(codes, info.imeiETC);
    }
    
    /**
     * 得到加密后的电话号码数据
     * @param phone
     * @return byte数组
     */
    public byte[] getEncryptPhone(String phone){
        if(info == null) return null;
        if("".equals(info.phoneETC)) return null;
        byte[] codes = new byte[20];
        System.arraycopy(phone.getBytes(), 0, codes, 0, phone.getBytes().length);
        return xorEncrypt(codes, info.phoneETC);
    }
    
    /**
     * 将数据codes和密钥key进行异或加密
     * @param codes
     * @param key
     * @return
     */
    private byte[] xorEncrypt(byte[] codes, String key){
        byte[] keys = key.getBytes();
        int index = 0;
        for(int i=0;i<codes.length;i++){
            codes[i] = (byte)((codes[i]+EncryptCanstans.defaultKey)^keys[index]);
            index = ++index % keys.length;
        }
        return codes;
    }
    
    /**
     * 解密得到IMSI
     * @param codes
     * @return 
     */
    public String decrypt2Imsi(byte[] codes){
        if(info == null) return "";
        if("".equals(info.imsiETC)) return "";
        byte[] imsis = subBytes(codes, 15);
        System.out.println("加密imsi数据流为 : "+StringUtils.bytes2HexString(imsis));
        return xorDecrypt(imsis, info.imsiETC);
    }
    
    /**
     * 解密得到IMEI
     * @param codes
     * @return
     */
    public String decrypt2Imei(byte[] codes){
        if(info == null) return "";
        if("".equals(info.imeiETC)) return "";
        return xorDecrypt(subBytes(codes, 15), info.imeiETC);
    }
    
    /**
     * 解密得到电话号码
     * @param codes
     * @return
     */
    public String decrypt2Phone(byte[] codes){
        if(info == null) return "";
        if("".equals(info.phoneETC)) return "";
        return xorDecrypt(codes, info.phoneETC);
    }
    
    /**
     * 根据密钥key解密数据codes
     * @param codes
     * @param key 密钥
     * @return 
     */
    private String xorDecrypt(byte[] codes, String key){
        byte[] keys = key.getBytes();
        int index = 0;
        for(int i=0;i<codes.length;i++){
            codes[i] = (byte)((codes[i]^keys[index])-EncryptCanstans.defaultKey);
            index = ++index % keys.length;
        }
        System.out.println("解密后的数据为 : "+StringUtils.bytes2HexString(codes));
        return new String(codes).trim();
    }
    
    private byte[] subBytes(byte[] bs, int len){
        if(bs.length < len) len = bs.length;
        byte[] datas = new byte[len];
        System.arraycopy(bs, 0, datas, 0, len);
        return datas;
    }
    
    public static void main(String[] args) {
        XorEncryptUtil xorUtil = new XorEncryptUtil(new AuthentInfo());
        xorUtil.createAuthentInfo();
        System.out.println(xorUtil.info.toString());
        System.out.println(StringUtils.bytes2HexString(xorUtil.getEncryptIMSI("460001234589743")));
    }

}
