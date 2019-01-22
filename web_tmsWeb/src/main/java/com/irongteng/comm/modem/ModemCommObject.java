package com.irongteng.comm.modem;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irongteng.comm.listener.LinkAlarmListener;
import com.irongteng.comm.listener.ReadRptDataListener;
import com.irongteng.comm.CommModeConstants;
import com.irongteng.comm.CommObjectIF;
import com.irongteng.conf.LinkAlarmConfig;
import com.irongteng.conf.ModemConfig;
import com.irongteng.conf.ModemInfo;

import dwz.common.util.StringUtils;

/**
 * Project: OMCServerCommunication Package: com.omcserver.communication.modem
 * Class name: ModemManager.java 功能说明:管理modem通信，短信、数传、GPRS Copyright (c)
 * 2007-2009 winhap Systems, Inc. All rights reserved. Created by: lvlei
 * Changed by: liuqing On: 2016-3-5 15:10:46
 */
public class ModemCommObject extends CommObjectIF {
    
    private static final Logger logger = LoggerFactory.getLogger(ModemCommObject.class);
    
    private ModemBase modem = null; // 负责发送数据的modem
    
    private String smsPhone = null; // 短信电话号码
    
    private String dataTransferPhone = null; // 数传电话号码
    
    private LinkAlarmListener linkAlarmListener = null;// 链路告警侦听者
    
    private int counter = 0;// 检测IP数据通信故障的计数器
    
    private static final int MAX_SEND_RESET_COUNT = 3;     //最大复位次数
    private static final int MAX_SERVICE_RESET_COUNT = 3;     //最大复位次数
    
    private volatile int serverResetFailCounter = 0;           // 服务启动统计程序复位失败次数的计数器
    
    private volatile int sendResetFailCounter = 0;           // 发送统计程序复位失败次数的计数器
    
    private boolean isLinkAlarmTag = LinkAlarmConfig.getInstance().isModemFaultAlarm();// 是否处于链路告警
    
    private String portName; // 串口名称
    private String modemType; // Modem的类型
    
    private Timer initTimer = null; // 初始化定时器
    private Timer serialTimer;  //串口定时器，定时判断串口状态
    
    public ModemCommObject() {
        
    }
    /**
     * 构造器
     * 
     * @param listener
     *            直放站数据侦听者
     * @param modemInfo
     *            modem的配置信息
     */
    public ModemCommObject(Vector<ReadRptDataListener> listener, ModemInfo modemInfo) {
        this.portName = modemInfo.getPortName();
        this.modemType = modemInfo.getModemType();
        // 根据modemTyp创建相应的modem,默认是西门子modem
        String modemType = modemInfo.getModemType();
        
        if (ModemTypeConstants.SIEMENS.equalsIgnoreCase(modemType)) { //西门子
            modem = new SiemensModem(listener, modemInfo);
            this.allowCommunicationMode.setAllowDataTransfer(true);
            this.allowCommunicationMode.setAllowSms(true);
        } else if (ModemTypeConstants.SIMPLNANO.equalsIgnoreCase(modemType)) { //简约纳电子
            modem = new SimplnanoModem(listener, modemInfo);
            this.allowCommunicationMode.setAllowDataTransfer(true);
            this.allowCommunicationMode.setAllowSms(true);
        } else {
            modem = new SiemensModem(listener, modemInfo);
            this.allowCommunicationMode.setAllowDataTransfer(true);
            this.allowCommunicationMode.setAllowSms(true);
        }
        smsPhone = modemInfo.getSmsPhone();
        dataTransferPhone = modemInfo.getDataTransferPhone();
        this.priority = modemInfo.getPriority();    
    }
    
    /**
     * 功能说明:初始化ModemManager，ModemManager开始工作
     * 
     * return void author liuqing 2007-3-5 15:11:25
     */
    @Override
    public void startService() {
        
        // 停止定时器
        if (initTimer != null) {
            initTimer.cancel();
            initTimer = null;
        }
        
        boolean result = false;
        
        // 打开Modem，如果失败则写日志记录失败原因，结束
        switch (modem.open()) {
        case SerialStatus.PORT_OPEN_OK:  //打开串口正常
            
            // 初始化Modem，写日志记录初始化结果，如果初始化失败则关闭串口并启动定时器重新startService(),失败则返回
            if (modem.init()) {
                
                logger.info("Modem 初始化成功" + "(" + portName + "," + modemType + ")");
                
                //参数初始化
                initServiceOK();
                //启动串口是否运行定时器
                //startSerialAvailableTask(true);
                //端口存在，初始化完成
                startSerialAvailableTask(true, true);
                
                result = true;
            } else {
                logger.info("Modem 初始化失败" + "(" + portName + "," + modemType + ")");
                
                //初始化参数
                initServiceError();
                //启动串口监控定时器
                //startSerialAvailableTask(false);
                //端口存在，初始化失败
                startSerialAvailableTask(true, false);
            }
            
            //启动链路告警定时器
            //startLinkAlarmTask();
            
            break;
        case SerialStatus.PORT_NO_FOUND:  //串口不存在，则定期检查串口是否存在
            logger.info("串口不存在");
            
            //标记串口不可用，且为有故障，不允许发短信。不开启重启和链路告警判断功能
            initServiceErrorNoPort();
            //启动串口监控定时器
            //startSerialAvailableTask(false);
            //端口不存在，初始化当然失败，无论最后一个参数是否设为false，都为false
            startSerialAvailableTask(false, false);
            
            break;
        case SerialStatus.PORT_USED:
            logger.info("串口正在使用");
            break;
        case SerialStatus.PORT_NOT_SUPPORTED:
            logger.info("不支持串口操作");
            break;
        case SerialStatus.PORT_IO_ERROR:
            logger.info("打开串口的输入输出流失败");
            break;
        default:
            logger.info("打开串口失败");
            break;
        }
        
        if (!result && !isLinkAlarmTag && linkAlarmListener != null) {
            //产生链路告警
            alterLinkAlarmStatus(true);
        }
    }
    
    /**
     * 启动链路告警和复位和断电定时器
     *//*
    protected void startLinkAlarmTask() {
        
        initTimer = new Timer("modemInitTimer");
        
        logger.debug("创建modem初始化定时器：modemInitTimer");
        logger.debug("init suceess!");
        
        initTimer.schedule(new TimerTask() {
            
            public void run() {
                
                if (fault) {
                    counter++;
                } else {
                    counter = 0;
                }
                
                if (counter == 0 && isLinkAlarmTag && linkAlarmListener != null) {
                    //通信链路告警恢复
                    alterLinkAlarmStatus(false);
                }
                
                if (counter > 600) { //十分钟
                    
                    if (!isLinkAlarmTag && linkAlarmListener != null) {
                        //产生通信链路告警
                        alterLinkAlarmStatus(true);
                        //次数重置
                        counter = 0;
                    }
                }
                
                if (fault && (serviceFailCounter >= MAX_SERVICE_FAIL_COUNT || serverResetFailCounter >= MAX_SERVICE_RESET_COUNT)) {
                    //如果达到一定数量，将关闭电源重启
                    if (serverResetFailCounter >= MAX_SERVICE_RESET_COUNT && serverResetFailCounter >= MAX_SERVICE_RESET_COUNT) { //断电
                        serviceFailCounter = 0;
                        serverResetFailCounter = 0;
                        //模块断电
                        shutdownModule();
                        
                    } else if (serverResetFailCounter >= MAX_SERVICE_RESET_COUNT) {  //复位
                        serviceFailCounter = 0;
                        //模块复位
                        resetModule();
                    }
                    //重启服务
                    restartService();
                }
                
                if (fault && (sendFailCounter > MAX_SEND_FAIL_COUNT || sendResetFailCounter > MAX_SEND_RESET_COUNT)) {
                    busy = true;
                    //如果达到一定数量，将关闭电源重启
                    if (sendResetFailCounter > MAX_SEND_RESET_COUNT && sendResetFailCounter > MAX_SEND_RESET_COUNT) { //断电
                        //重置参数
                        sendFailCounter = 0;
                        sendResetFailCounter = 0;
                        
                        //模块断电
                        shutdownModule();
                        
                    } else if (sendResetFailCounter > MAX_SEND_RESET_COUNT) {  //复位
                        //重置参数
                        sendFailCounter = 0;
                        //模块复位
                        resetModule();
                    }
                    busy = false;
                    //重启服务
                    restartService();
                }
                
            }
        }, 1000, 1000);
    }
    */
    
    /**
     * 启动判断串口是否存在定时器
     * 
     * @param isAvailable  标记串口是否可用
     */
    /*
    protected void startSerialAvailableTask(final boolean isAvailable) {
        //停止串口定时器
        if (serialTimer != null ) {
            serialTimer.cancel();
            serialTimer = null;
        }
        
        serialTimer = new Timer("commMonitorTimer");
        serialTimer.schedule(new TimerTask() {
            
            public void run() {
                
                boolean  isFindPort = SerialUtils.existSerialPort(portName);
                
                if (isAvailable) {
                    //如果没有发现串口或者串口状态已经关闭，则关闭服务，启动串口监控进程，同时产生通信链路告警
                    if (!isFindPort || !isOpened()) {
                        //停止服务
                        stopService();
                        
                        startSerialAvailableTask(false);
                        //产生通信链路告警
                        alterLinkAlarmStatus(true);
                    }
                } else {
                    if (isFindPort) {
                        //重启失败次数加一
                        serviceFailCounter++;
                        
                        if (serviceFailCounter > MAX_SERVICE_FAIL_COUNT) {
                            //复位次数+1
                            serverResetFailCounter++;
                        } else {
                            //重启服务
                            restartService();
                        }
                    }
                }
            }
        }, 10*1000, 10*1000);
    }
    */
    
    /**
     * 启动判断串口是否存在定时器
     * 
     * @param exist  
     * 
     * 标记串口是否存在，如果不存在，则不可用，如果存在在，则进行逻辑判断
     * 
     * @param isAvailable  
     * 标记串口是否可用
     */
    protected void startSerialAvailableTask(final boolean exist, final boolean isAvailable) {
        
        boolean tmp;
        
        if (!exist)  tmp = false; 
        else tmp = isAvailable;
        
        final boolean isUsed = tmp;
        
        //停止串口定时器
        if (serialTimer != null ) {
            serialTimer.cancel();
            serialTimer = null;
        }
        
        serialTimer = new Timer("commMonitorTimer");
        serialTimer.schedule(new TimerTask() {
            
            @Override
            public void run() {
                
                //判断串口是否存在
                boolean  isFindPort = SerialUtils.existSerialPort(portName);
                
                if (exist) { //标记接口存在
                    
                    if (isUsed) { //接口标记可以被使用
                        startLinkAlarmTask2();
                        
                        //如果没有发现串口或者串口状态已经关闭，则关闭服务，启动串口监控进程，同时产生通信链路告警
                        if (!isFindPort || !isOpened()) {
                            //停止服务
                            stopService();
                            //标记接口不存在，接口无法使用
                            startSerialAvailableTask(false, false);
                            //产生通信链路告警
                            alterLinkAlarmStatus(true);
                        }
                    } else {
                        //重启失败次数加一
                        serviceFailCounter++;
                        
                        startLinkAlarmTask2();
                        //重启服务
                        restartService();
                    }
                } else { //标记接口不存在
                    if (isFindPort) {
                        //启动服务
                        startService();
                    }
                }
            }
        }, 10*1000, 10*1000);
    }
    
    /**
     * 启动链路告警和复位和断电定时器
     */
    private void startLinkAlarmTask2() {

        if (fault) {
            counter++;
        } else {
            counter = 0;
        }
        
        if (counter == 0 && isLinkAlarmTag && linkAlarmListener != null) {
            //通信链路告警恢复
            alterLinkAlarmStatus(false);
        }
        
        if (counter > 60) { //十分钟
            
            if (!isLinkAlarmTag && linkAlarmListener != null) {
                //产生通信链路告警
                alterLinkAlarmStatus(true);
                //次数重置
                counter = 0;
            }
        }
        
        if (fault && (serviceFailCounter >= MAX_SERVICE_FAIL_COUNT || serverResetFailCounter >= MAX_SERVICE_RESET_COUNT)) {
            //如果达到一定数量，将关闭电源重启
            if (serverResetFailCounter >= MAX_SERVICE_RESET_COUNT && serviceFailCounter >= MAX_SERVICE_FAIL_COUNT) { //断电
                serviceFailCounter = 0;
                serverResetFailCounter = 0;
                //模块断电
                shutdownModule();
                
            } else if(serviceFailCounter >= MAX_SERVICE_FAIL_COUNT) {  //复位
                serviceFailCounter = 0;
                //复位次数+1
                serverResetFailCounter++;
                //模块复位
                resetModule();
            }
        }
        
        if (fault && (sendFailCounter >= MAX_SEND_FAIL_COUNT || sendResetFailCounter >= MAX_SEND_RESET_COUNT)) {
            busy = true;
            System.out.println("sendResetFailCounter:" + sendResetFailCounter);
            //如果达到一定数量，将关闭电源重启
            if (sendResetFailCounter >= MAX_SEND_RESET_COUNT && sendFailCounter >= MAX_SEND_FAIL_COUNT) { //断电
                //重置参数
                sendFailCounter = 0;
                sendResetFailCounter = 0;
                
                //模块断电
                shutdownModule();
                
            } else if(sendFailCounter >= MAX_SEND_FAIL_COUNT) {  //复位
                //重置参数
                sendFailCounter = 0;
                sendResetFailCounter++;
                //模块复位
                resetModule();
            }
            
            //重启服务
            restartService();
            busy = false;
        }
    }
    
    /**
     * 初始化正常重置为正常参数
     */
    private void initServiceOK() {
        this.busy = false;
        this.fault = false;
        
        //服务失败次数置为0
        this.serviceFailCounter = 0;
        //重置复位次数设为0
        this.serverResetFailCounter = 0;
    }
    
    /**
     * 初始化异常时重置参数
     */
    private void initServiceError() {
        this.busy = true;
        this.fault = true;
    }

    /**
     * 初始化异常时重置参数
     */
    private void initServiceErrorNoPort() {
        // 停止定时器
        if (initTimer != null) {
            initTimer.cancel();
            initTimer = null;
        }
        
        this.busy = true;
        this.fault = true;
        
        //服务失败次数置为0
        this.serviceFailCounter = 0;
        //重置复位次数设为0
        this.serverResetFailCounter = 0;
        
    }
    
    /**
     * 初始化正常重置为正常参数
     */
    private void initSendOK() {
        this.busy = false;
        this.fault = false;
        
        //发送失败次数置为0
        this.sendFailCounter = 0;
        //重置复位次数设为0
        this.sendResetFailCounter = 0;
    }
    
    /**
     * 关闭电源
     */
    private void shutdownModule() {
        try {
            //关闭电源
            modem.shutdownModule();
            
            Thread.sleep(1000*8); //默认重启时间为15秒
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
    }
    
    /**
     * 模块复位
     */
    private void resetModule() {
        
        try {
            //模块复位
            modem.resetModule();
            
            Thread.sleep(1000*4); //默认复位时间为8秒
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
    }
    
    /**
     *  修改链路告警状态，并将链路告警保存到配置文件和数据库
     *  
     * @param status  true为产生链路告警 false链路告警恢复
     */
    private void alterLinkAlarmStatus(boolean status) {
        linkAlarmListener.readLinkAlarmData(LinkAlarmConfig.getInstance().getModemFaultAlarmID(), status);
        
        if (status) {
            logger.info("Modem通信故障" + "(" + portName + "," + modemType + ")");
        } else {
            logger.info("Modem通信故障恢复" + "(" + portName + "," + modemType + ")");
        }
        isLinkAlarmTag = status;
        LinkAlarmConfig config = LinkAlarmConfig.getInstance();
        config.setModemFaultAlarm(status);
        config.save();
    }
    
    /**
     * 功能说明:ModemManager停止工作
     * 
     * return void author liuqing 2007-3-5 15:11:45
     */
    @Override
    public void stopService() {
        
        this.busy = true;
        this.fault = true;
        
        // 停止定时器
        if (initTimer != null) {
            initTimer.cancel();
            initTimer = null;
        }
        //停止串口定时器
        if (serialTimer != null ) {
            serialTimer.cancel();
            serialTimer = null;
        }
        
        // 关闭串口
        if (modem != null) modem.close();
    }
    
    /**
     * 功能说明:重启
     * 
     * return void author liuqing 2007-4-19 19:25:56
     */
    private void restartService() {
        
        stopService();

        try {
            Thread.sleep(2000);
            Thread.yield();
        } catch (Exception e) {

        }
        startService();
    }
    
    /**
     * 重载基类方法
     * @return 
     */
    @Override
    public synchronized boolean sendData(byte[] data, String phoneOrIP, int sendMode) {
        
        boolean resultTag = false;
        
        if (!this.busy) { //如果端口没有被占用，则发送信息
            //标记为正在使用
            this.busy = true;
            
            try {

                this.lastUsedTime = System.currentTimeMillis();// 更新使用时间
                
                logger.debug("加入短信:" + portName);
                
                // 判断通信方式（数传/短信）
                // 通信方式是数传方式，modem发送数传数据
                if (sendMode == CommModeConstants.DATA_MODE) {
                    
                    resultTag = modem.sendData(phoneOrIP, data);
                    // 根据发送结果记录日志
                    String str = StringUtils.bytes2HexString(data);
                    if (resultTag) {
                        logger.info("发送数据成功：" + phoneOrIP + "," + str);
                    } else {
                        logger.info("发送数据失败：" + phoneOrIP + "," + str);
                    }
                } else if (sendMode == CommModeConstants.SMS_MODE) { // 通信方式是短信方式时，modem发送短信
                    resultTag = false;
                    try {
                        boolean isChineseSMS = isIncludeChineseChar(new String(data));
                        if (isChineseSMS) {
                            resultTag = modem.sendChineseSMS(phoneOrIP, new String(data));
                        } else {
                            resultTag = modem.sendEnglishSMS(phoneOrIP, new String(data));
                        }
                    } catch (Exception e) {
                        logger.error(e.getMessage());
                    }
                    
                    // 根据发送结果记录日志
                    if (resultTag) {
                        logger.info("发送短信成功："+phoneOrIP+"," + new String(data));
                    } else {
                        logger.info("发送短信失败：" + phoneOrIP+"," + new String(data));
                    }
                }
                
                // 根据发送结果记录日志
                if (resultTag) {
                    
                    initSendOK();
                    
                } else {
                    
                    sendFailCounter++;
                    
                }
            } catch(Exception e) {
                
            } finally {
                System.out.println("sendFailCounter:" + sendFailCounter + " sendResetFailCounter:" + sendResetFailCounter);
                
                if (sendFailCounter >= MAX_SEND_FAIL_COUNT || sendResetFailCounter >= MAX_SEND_RESET_COUNT) {
                    this.fault = true;
                    this.busy = true;
                } else {
                //标记为可用
                    this.busy = false;
                }
            }
        }
        return resultTag;
    }
    
    /**
     * @return  * 重载基类方法
     */
    @Override
    public String getSmsPhone() {
        return smsPhone;
    }
    
    /**
     * 重载基类方法
     * @return 
     */
    @Override
    public String getDataTransferPhone() {
        return dataTransferPhone;
    }
    
    private static boolean isIncludeChineseChar(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isLetter(c) && Character.getType(c) == 5) {
                return true;
            }
        }
        return false;
    }
    
    public LinkAlarmListener getLinkAlarmListener() {
        return linkAlarmListener;
    }

    @Override
    public void setLinkAlarmListener(LinkAlarmListener linkAlarmListener) {
        this.linkAlarmListener = linkAlarmListener;
    }
    
    public boolean isOpened() {
        return modem.isOpened();
    }
    
    public static void main(String agrs[]) throws InterruptedException {
        
        List<ModemInfo> modems = new ModemConfig().getModems();
        
        for (ModemInfo modemInfo: modems) {
            
            if (!modemInfo.isUsed()) {
                continue;
            }
            Vector<ReadRptDataListener> repeaterDataListener = new Vector<>();
            repeaterDataListener.add((ReadRptDataListener) (byte[] data, String sourceAddress, int communicationMode) -> {
                System.out.println(StringUtils.bytes2HexString(data) + " addr:" + sourceAddress);
            });
            
            LinkAlarmListener linkAlarmListener = (int alarmID, boolean isAlarm) -> {
                System.out.println("alarmID:" + alarmID + " isAlarm:" + isAlarm);
            };
            
            CommObjectIF commObject = new ModemCommObject(repeaterDataListener, modemInfo);
            commObject.setLinkAlarmListener(linkAlarmListener);
            commObject.startService();
            
            Thread.sleep(14000);
            
            commObject.stopService();
            
            /*
            for(int i=0; i<25; i++) {
                String str = "test_number:" + i;
                //str = "王小华\n城中村8翔9栋901\n12-11 12:33:40";
                byte[] data = str.getBytes();
                String destination = "13480760840";
                
                while(commObject.isBusy()) {
                    Thread.sleep(3000);
                }
                
                commObject.sendData(data, destination, CommModeConstants.SMS_MODE);
                
                Thread.sleep(4000);
            }
            */
        }
    }
}