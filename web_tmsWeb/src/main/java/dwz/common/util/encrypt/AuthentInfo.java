package dwz.common.util.encrypt;

/**
 * 鉴权加密信息
 * @author xing
 *
 */
public class AuthentInfo {
    public String randStr = "";    //随机字符串
    public int randInt = 0;         //随机整数
    public int quot = 0;            
    public int remainder = 0;
    public String md5Code = "";     //randStr+quot+remainder生成的MD5码
    public String imsiETC = "";     //imsi加密码
    public String imeiETC = "";     //imei加密码
    public String phoneETC = "";    //电话号码加密码
    
    @Override
    public String toString() {
        return "AuthentInfo [randStr=" + randStr + ", randInt=" + randInt
                + ", quot=" + quot + ", remainder=" + remainder + ", md5Code="
                + md5Code + ", imsiETC=" + imsiETC
                + ", imeiETC=" + imeiETC + ", phoneETC=" + phoneETC + "]";
    }
	
}
