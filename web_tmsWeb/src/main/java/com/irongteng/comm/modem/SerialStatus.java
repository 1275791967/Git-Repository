package com.irongteng.comm.modem;

public class SerialStatus
{
    //直放站通信方式，程序使用

    public static final int PORT_OPEN_OK = 0; // 打开串口成功

    public static final int PORT_NO_FOUND = 1; // 没有此串口

    public static final int PORT_USED = 2; // 串口正在使用

    public static final int PORT_NOT_SUPPORTED = 3; //不允许串口操作
    
    public static final int PORT_IO_ERROR = 4; //串口输出流失败
    
    public static final int TOO_MANY_LISTENER_ERR = 5; //socket通信方式
    
    //直放站通信方式，程序使用对应于数据库通信方式表
    public static final int MODE1_DATABASE = 1; //移动表示短消息 ,联通表示CDMA短消息

    public static final int MODE2_DATABASE = 2; // 移动表示GPRS , 联通表示GSM短信
    
    public static final int MODE3_DATABASE = 3; // 移动表示数传 , 联通电路数传

    public static final int MODE4_DATABASE = 4; // 移动表示socket通信方式 , 联通表示分组数传
    
    
    public static final int GPRSCommunicationProtected = 60;
    
    public static final int SMSCommunicationProtected =180;
    
    public static final int DATAPASSCommunicationProtected =120; //暂无,我们自定义不知是否可行
    
    public static final int TCPIPCommunicationProtected = 120;   //暂无,我们自定义不知是否可行
    
    public static final int SMS_SEND_STATUS = 1;
    
    public static final int SMS_RECEIVE_STATUS = 2;

    /********************通信方式延迟****************/
//    public static final int  SMSDelayTime = 60; //GSM 短消息
//    
//    public static final int  NotSMSDelayTime = 250; //GSM 短消息
//    
//    public static final int  GPRSDelayTime = 150; //GSM数传
//    
//    public static final int  DATAPASSDelayTime = 20;// 数传延迟
//    
//    public static final int  TCPIPDelayTime =  40; //socket通信方式延迟
    
}
