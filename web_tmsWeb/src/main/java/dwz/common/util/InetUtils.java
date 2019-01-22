package dwz.common.util;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class InetUtils {
    
    private static Logger logger = LoggerFactory.getLogger(InetUtils.class);
    
    public static boolean isConnectedByBaiduMap() {
        try {
            URL url = new URL("http://api.map.baidu.com/api?v=2.0&ak=p07djqeOM6zKUIgOZF8sGUSy");
            return isConnectedByURL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static boolean isConnectedByURL(URL url) {
        try {
            url.openConnection().connect();;
            logger.debug("连接正常");
            return true;
        } catch (IOException e) {
            logger.debug("无法连接到：" + url.toString());
        }
        return false;
    }
    
    /**
     * 
     * @param urlName
     * @return
     */
    public static boolean isConnectedByUrlName(String urlName) {
        URL url = null;
        try {
            url = new URL(urlName);
            return isConnectedByURL(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static boolean isAvailableMapUrl() {
        return isAvailableUrl("http://api.map.baidu.com/api?v=2.0&ak=p07djqeOM6zKUIgOZF8sGUSy");
    }
    
    public static boolean isAvailableUrl(String url) {
        try {
            new URL(url).openConnection().connect();
            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * 域名
     * @param domain
     * @return
     */
    public static boolean isOnline(String domain) {
        try {
            InetAddress address = InetAddress.getByName(domain);// ping this IP
            if (address instanceof Inet4Address || address instanceof Inet6Address) return true;
        } catch (Exception e) {
            logger.debug("network offline...");
        }
        return false;
    }
    
    public static boolean isOnline() {
        return isOnline("www.baidu.com");
    }
}
