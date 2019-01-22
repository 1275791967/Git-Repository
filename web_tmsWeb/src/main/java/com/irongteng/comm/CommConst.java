package com.irongteng.comm;

import java.util.HashMap;
import java.util.Map;

import com.irongteng.conf.TcpConfig;
import com.irongteng.conf.UdpConfig;


public class CommConst {
    
    public static final int TCP = 0x01;
    public static final int UDP = 0x02;
    public static final int MODEM = 0x03;
    
    public static final int HP_GSM = 0x01;    //GSM
    public static final int HP_WCDMA = 0x02;  //WCDMA
    public static final int HP_CDMA = 0x03;   //CDMA
    public static final int HP_TDLTE = 0x04;  //TD-LTE
    public static final int HP_FDDLTE = 0x05; //FDD-LTD
    public static final int WIFI = 0x14; //WIFI监控
    
    public static final int MAC_TYPE = 1;
    public static final int IMSI_TYPE = 2;
    public static final int IMEI_TYPE = 3;

    public static Map<Integer, String> getCommCategoryList() {
        Map<Integer,String> map = new HashMap<>();
        
        if (new TcpConfig().isEnable()) {
            map.put(TCP, "TCP通讯");
        }
        if (new UdpConfig().isEnable()) {
            map.put(UDP, "UDP通讯");
        }
        return map;
    }
    
    public static Map<Integer, String> getCommMode() {
    	Map<Integer,String> map = new HashMap<>();
    	map.put(1, "中国移动GSM");
        map.put(2, "中国联通GSM");
        map.put(3, "中国电信CDMA");
        map.put(4, "中国联通WCDMA");
        map.put(5, "中国移动TD LTE");
        map.put(6, "中国联通FDD LTE");
        map.put(7, "中国电信FDD LTE");
        map.put(8, "中国移动TD-SCDMA");
        map.put(9, "中国联通TD LTE");
        map.put(10, "中国电信TD LTE");
        
        return map;
    }
}
