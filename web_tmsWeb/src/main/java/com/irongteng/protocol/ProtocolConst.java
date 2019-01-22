package com.irongteng.protocol;

/**Project: OMCServerCommunication
 * Package: com.omcserver.communication.constants
 * Class name: CmppConstants.java
 * 功能说明:通信协议常量类
 * Copyright (c) 2007-2009 winhap Systems, Inc. 
 * All rights reserved.
 * Created by: lvlei
 * Changed by: 吕雷 On: 2015-4-9  19:41:15
 */
public class ProtocolConst {
    
    public static final byte VERSION_V2 = 0x02;          //版本号2
    public static final byte VERSION_V3 = 0x03;          //版本号3
    public static final byte ANSWER_FLAG = 0x00;         //应答标志
    
    //目前调度系统只有两种命令
    public static final byte REQUEST = 0x7C;             //请求
    public static final byte REQUEST_ANSWER = 0x6C;      //请求应答
    
    //目前调度系统通过参数编号来区分不同请求
    public static final short GET_PHONE = 0x0A14;                   //获取电话号码
    public static final short GET_PHONE_RESULT = 0x0A15;            //电话号码结果
    public static final short SET_LATITUDE_LONGITUDE = 0x0A16;      //设置经纬度
    public static final short DEVICE_VERIFY = 0x0A17;               //设备验证
    public static final short IDENTITY_VERIFY = 0x0A18;             //身份验证
    public static final short IDENTITY_VERIFY_RESULT = 0x0A19;      //身份验证结果
    public static final short ACCOUNT_LOGIN = 0x0A1A;               //手机客户登录
    public static final short ACCOUNT_LOGIN_RESULT = 0x0A1B;        //客户登录结果
    public static final short GSM_AUTHENT = 0x0A1C;                 //GSM鉴权
    public static final short GSM_AUTHENT_RESULT = 0x0A1D;          //GSM鉴权结果
    public static final short TRANSLATE_LOAD_REPORT = 0x0A1E;       //翻译负载信息上报
    public static final short RF_OFF_NOTIFY = 0x0A1F;               //射频关闭通知
    public static final short TRANSLATE_CANCEL = 0x0A20;            //取消翻译
    
    public static final String BASE_DATA_SIZE = "data_size";         //数据长度 4个字节
    public static final String BASE_VERSION_CODE = "version_code";   //版本号 1个字节
    public static final String BASE_DEV_NUM = "device_number";       //设备编号 4个字节
    public static final String COMM_PACKAGE_ID = "comm_package_id";  //包的编号标识符
    public static final String BASE_RESERVED_FIELD = "reserved_field"; //保留字段 4个字节
    public static final String BASE_COMMAND_ID = "command_id";       //命令编号  1字节  WIFI协议默认是0
    public static final String BASE_ANSWER_FLAG = "answer_id";       //应答标识  1字节  WIFI协议默认是0
    public static final String CARRY_DATA = "carry_date";            //承载数据
    public static final int PROTOCOL_WIFI = 1;
    public static final int READONLY = 1;
    public static final int WRITEABLE = 2;
    
    
    public static final int TYPE_GET_PHONE = 1;                   //获取电话号码
    public static final int TYPE_GET_PHONE_RESULT = 2;            //电话号码结果
    public static final int TYPE_SET_LATITUDE_LONGITUDE = 3;      //设置经纬度
    public static final int TYPE_DEVICE_VERIFY = 4;               //设备验证
    public static final int TYPE_IDENTITY_VERIFY = 5;             //身份验证
    public static final int TYPE_IDENTITY_VERIFY_RESULT = 6;      //身份验证结果
    public static final int TYPE_ACCOUNT_LOGIN = 7;               //手机客户登录
    public static final int TYPE_ACCOUNT_LOGIN_RESULT = 8;        //客户登录结果
    public static final int TYPE_GSM_AUTHENT = 9;                 //GSM鉴权
    public static final int TYPE_GSM_AUTHENT_RESULT = 10;          //GSM鉴权结果
    public static final int TYPE_TRANSLATE_LOAD_REPORT = 11;       //翻译负载信息上报
    public static final int TYPE_RF_OFF_NOTIFY = 12;               //射频关闭通知
    public static final int TYPE_TRANSLATE_CANCEL = 13;            //取消翻译
    
}
