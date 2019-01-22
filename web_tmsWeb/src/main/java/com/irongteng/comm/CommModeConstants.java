package com.irongteng.comm;

public class CommModeConstants {
    // 直放站通信方式，对应于数据库通信方式表
    public static final int GPRS_MODE = 1; // GPRS通信方式
    
    public static final int SMS_MODE = 2; // CDMA 短消息

    public static final int DATA_MODE = 3; // CDMA 数传

    public static final int TCP_MODE = 4; // socket通信方式
    
    public static final int UDP_MODE = 5; // UDP通信方式
    
    public static final int PDU_MODE = 20; // 电源管理通信方式
}
