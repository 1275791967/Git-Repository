package com.irongteng.comm.simulator;

import java.io.File;

/**Project: OMCServerCommunication
 * Package: com.omcserver.communication.constants
 * Class name: CommunicationConstants.java
 * 功能说明:
 * Copyright (c) 2007-2009 winhap Systems, Inc. 
 * All rights reserved.
 * Created by: liuqing
 * Changed by: liuqing On: 2007-4-4  19:35:08
 */
public class SimulatorConstants
{
    public static final String COMMUNICATION_CONFIG_FILE = "conf/communicationConfig.ini"; // 通信配置文件

    public static final String CMPP_CONFIG_FILE = "conf/CmppConfig.ini";// cmpp配置文件

    public static final String SGIP_CONFIG_FILE = "conf/SgipConfig.ini";// sgip配置文件

    public static final String TCP_CONFIG_FILE = "conf/Tcpconfig.ini";// tcp配置文件
    
    public static final String FTP_CONFIG_FILE = "conf/FtpConfig.ini";// ftp配置文件
    
    public static final String VM_CONFIG_FILE = "conf" + File.separator + "Simulator.ini";// 监控设备模拟器配置文件
    
    public static final String COMMON_CONFIG_FILE = "conf" + File.separator + "Common.ini";// 监控设备模拟器配置文件
    
    public static final String DB_CLEAN_TASK_FILE = "conf" + File.separator + "DbCleanTask.ini";// 监控设备模拟器配置文件
    
    public static final String MAC_CONFIG_FILE = "conf/oui.txt";    // MAC地址所属厂商配置文件
    
    public static final String SMGP_CONFIG_FILE = "conf/smgpconfig.ini";// smgp配置文件
    
    public static final String UDP_CONFIG_FILE = "conf/UdpConfig.ini";// udp配置文件

    public static final String LINK_ALARM_PARA_CONFIG_FILE = "conf/linkAlarmParaID.ini";// 链路告警参数配置文件
    
    public static final String AREA_CONFIG_FILE = "conf/areaConfig.ini";// 国际区号配置文件
}
