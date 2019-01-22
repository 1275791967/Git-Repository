package com.irongteng.thirdinterface;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class HttpCanstants {

    public static String auth_bearer = null;
    public static int remain_count = 0;
    
    public static String userName = "testapi1";
    public static String password = "bMBgKYWlrK1UBJKy";
    public static String url = "https://45.252.63.167:1443";
    
    //1、帐号：TEST0001 密码：Test123456
    //2、帐号：PTCX001 密码：baNHjUDs7R
//    public static String userName = "PTCX001";
//    public static String password = "baNHjUDs7R";
//    public static String url = "https://45.252.63.167:38698"; //正式版端口号为38698
    public static String cmd_getAPIToken = "/getapitoken";
    public static String cmd_remain = "/remain";
    public static String cmd_query = "/query";
    public static String cmd_passwd = "/passwd";
    public static String cmd_input = "/input";
    public static String cmd_output = "/output";
    
    //请求查询列表
    public static ConcurrentLinkedQueue<String> resqList = new ConcurrentLinkedQueue<>();
    
    public static Map<String, HttpQueryInfo> resqQueueMap = new LinkedHashMap<>(); 
    
    //等待查询结果列表
    public static ConcurrentLinkedQueue<String> waitQueryResultList = new ConcurrentLinkedQueue<>();
}
