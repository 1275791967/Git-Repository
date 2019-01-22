package com.irongteng.conf;

import java.io.FileInputStream;
import java.util.Properties;

public class MacAttrUtils {
    
    private static final Properties PROPS = new Properties();
    
    static {
        try (FileInputStream fis = new FileInputStream(MacAttrUtils.class.getResource("/").getPath() + ConfigConstants.MAC_CONFIG_FILE)) {
            PROPS.load(fis);
        } catch (Exception e) {
            
        }  
    }
    
    public static String getValue(String mac) {
        String key;
        if (mac.contains(":") ) {
            key = mac.replaceAll(":", "-").substring(0, 8).toUpperCase();
        } else if (mac.contains("-")) {
            key = mac.substring(0, 8).toUpperCase();
        } else {
            key = mac.replaceAll(mac.substring(2,3), "-").substring(0, 8).toUpperCase();
        }
        String value = PROPS.getProperty(key);
        if (value==null) {
            return "Unknown";
        }
        return value; 
    }
    
    public static void main(String[] args) {
        System.out.println(MacAttrUtils.getValue("38:BC:1A:C7:F2:6D"));
    }
}
