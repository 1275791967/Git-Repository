package dwz.common.util.encrypt;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

public class DesEncryptUtil {
    
    private static final String DEFAULT_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCVRiDkEKXy/KBTe+UmkA+feq1zGWIgBxkgbz7aBJGb5+eMKKoiDRoEHzlGndwFKm4mQWNftuMOfNcogzYpGKSEfC7sqfBPDHsGPZixMWzL3J10zkMTWo6MDIXKKqMG1Pgeq1wENfJjcYSU/enYSZkg3rFTOaBSFId+rrPjPo7Y4wIDAQAB8a!2e4b4%1b6e2&ba5.-011b?720f-=+";
    
    private static final String DES_ALGORITHM= "DESede";
    /** 
     * DES加密 
     * @param plainData 
     * @param secretKey 
     * @return 
     * @throws Exception 
     */  
    public static String encrypt(String text, String key){  
  
        Cipher cipher = null;  
        try {  
            // 进行3-DES加密后的内容的字节
            DESedeKeySpec dks = new DESedeKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            SecretKey skey = keyFactory.generateSecret(dks);
            cipher = Cipher.getInstance(DES_ALGORITHM);  
            cipher.init(Cipher.ENCRYPT_MODE, skey);
            byte[] buf = cipher.doFinal(text.getBytes());  
            
            return Base64.getEncoder().encodeToString(buf);  
        }catch (Exception e) {
            e.printStackTrace();
            return text;
        }  
    }
  
    /** 
     * DES解密 
     * @param secretData 
     * @param secretKey 
     * @return 
     * @throws Exception 
     */  
    public static String decrypt(String text, String key){  
          
        Cipher cipher = null;  
        try { 
            DESedeKeySpec dks = new DESedeKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            SecretKey skey = keyFactory.generateSecret(dks);
            cipher = Cipher.getInstance(DES_ALGORITHM);  
            
            cipher = Cipher.getInstance(DES_ALGORITHM);  
            cipher.init(Cipher.DECRYPT_MODE, skey);  
            
            byte[] buf = cipher.doFinal(Base64.getDecoder().decode(text.getBytes()));    
            return new String(buf);  
             
        } catch (Exception e) {
            e.printStackTrace();
            return text;
        }  
    } 
    
    /**
     * 加密
     * @param password
     * @return
     */
    public static String encrypt(String password) {
        return encrypt(password, DEFAULT_KEY);
    }
    /**
     * 解密
     * @param password
     * @return
     */
    public static String decrypt(String password) {
        return decrypt(password, DEFAULT_KEY);
    }
    
    public static void main(String[] a) throws Exception{  
        String input = "test";    
          
        String result = encrypt(input);  
        System.out.println(result);  
          
        System.out.println( decrypt(result));  
            //H+Ac2AysRv6CWn3ncH1HIw==  
    }  
}  
