package com.irongteng.comm;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irongteng.comm.listener.LinkAlarmListener;
import com.irongteng.comm.listener.ReceiveDataListener;
import com.irongteng.comm.listener.SendDataListener;
import com.irongteng.comm.server.TcpCommServer;
import com.irongteng.comm.server.UdpCommServer;
import com.irongteng.comm.server.UdpFddCommServer;
import com.irongteng.comm.server.UdpLteCommServer;
import com.irongteng.conf.ModemConfig;
import com.irongteng.conf.ModemInfo;
import com.irongteng.conf.TcpConfig;
import com.irongteng.conf.UdpConfig;
import com.irongteng.conf.UdpFddConfig;
import com.irongteng.conf.UdpLTEConfig;

/**Project: OMCServerCommunication
 * Package: com.omcserver.communication
 * Class name: InitCommObject.java
 * 功能说明:初始化通信对象
 * Copyright (c) 2007-2009 winhap Systems, Inc. 
 * All rights reserved.
 * Created by: lvlei
 * Changed by: lviei On: 2007-4-10  17:19:15
 */
public class CommFactory {
    
    private static final Logger logger = LoggerFactory.getLogger(CommFactory.class);
    
    private ReceiveDataListener receiveDataListener; //接收数据侦听者
    
    private SendDataListener sendDataListener;       //接收数据侦听者
    
    private LinkAlarmListener linkAlarmListener;     //链路告警侦听者
    
    private  final static List<CommServer> commObjects = new ArrayList<>(); // 存放通信对象的列表数组(数组按优先级分类,共十个等级)
    
    private static final CommFactory INSTANCE = new CommFactory();
    
    private CommFactory () { }
    
    public static CommFactory getInstance() {
    	return INSTANCE;
    }
    
    public void start() {

        if (commObjects.size() > 0) {
            this.stop();
        }
        
        //TCP
        TcpConfig tcpConfig = new TcpConfig();
        if(tcpConfig.isEnable()) {
            // 启动UDP服务
            CommServer tcpManager = new TcpCommServer(tcpConfig.getServerPort(), CommModeStatus.TCP);
            
            if(!tcpManager.isActive()) {
                tcpManager.setReceiveDataListener(receiveDataListener);
                tcpManager.start();
                
                commObjects.add(tcpManager);
            }
        }
        //UDP
        UdpConfig udpConfig = new UdpConfig();
        if(udpConfig.isEnable()) {
            // 启动UDP服务
            CommServer udpManager = new UdpCommServer(udpConfig.getServerPort(), CommModeStatus.UDP);
            if(!udpManager.isActive()) {
                udpManager.setReceiveDataListener(receiveDataListener);
                udpManager.start();
                
                commObjects.add(udpManager);
            }
        }
        //UDP FDD
        UdpFddConfig udpFddConfig = new UdpFddConfig();
        if(udpFddConfig.isEnable()) {
            // 启动FDD UDP服务
            CommServer udpFddManager = new UdpFddCommServer(udpFddConfig.getServerPort());
            
            if(!udpFddManager.isActive()) {
                udpFddManager.setReceiveDataListener(receiveDataListener);
                udpFddManager.start();
                
                commObjects.add(udpFddManager);
            }
        }
        //UDP LTE
        UdpLTEConfig udpLTEConfig = new UdpLTEConfig();
        if(udpLTEConfig.isEnable()) {
            
            // 启动TD LTE UDP服务
            CommServer udpLTEManager = new UdpLteCommServer(udpLTEConfig.getServerPort());
            
            if(!udpLTEManager.isActive()) {
                udpLTEManager.setReceiveDataListener(receiveDataListener);
                udpLTEManager.start();
                
                commObjects.add(udpLTEManager);
            }
        }
        
        List<ModemInfo> modems = new ModemConfig().getModems();
        // 生成ModemManager
        if(modems.size() <= 0) {
            logger.info("检测到该电脑没有串口或者缺少串口包");
        }
        
        /*
        for (ModemInfo modemInfo: modems) {
            if (!modemInfo.isUsed()) {
                continue;
            }
            CommObjectIF commObject = new ModemCommObject(readDataListener, modemInfo);
            commObject.setLinkAlarmListener(linkAlarmListener);
            commObject.startService();
            commObjects.add(commObject);
        }
        */
    }
    
    /**
     * 停止服务
     */
    public void stop(){
        
        commObjects.stream().map((commObject) -> {
            commObject.stop();
            return commObject;
        }).forEach((commObject) -> {
            commObject = null;
        });
        commObjects.clear();
    }
    
    public ReceiveDataListener getReceiveDataListener() {
        return this.receiveDataListener;
    }
    
    public void setReceiveDataListener(ReceiveDataListener receiveDataListener) {
        this.receiveDataListener = receiveDataListener;
    }
    
    public SendDataListener getSendDataListener() {
        return sendDataListener;
    }

    public void setSendDataListener(SendDataListener sendDataListener) {
        this.sendDataListener = sendDataListener;
    }
    
    public LinkAlarmListener getLinkAlarmListener() {
        return linkAlarmListener;
    }
    
    public void setLinkAlarmListener(LinkAlarmListener linkAlarmListener) {
        this.linkAlarmListener = linkAlarmListener;
    }
}
