/*
 * Project: OMCServer
 * Class name: ServerInitialSetting
 * 系统初始化设置类
 * Copyright (c) 2000-2006 BroadenGate Systems, Inc. 
 * All rights reserved.
 * Created by: yewei
 * Changed by: yewei On: 2007-1-6
 */

package com.irongteng.ipms.pool;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.log4j.Logger;

import com.irongteng.comm.CommFactory;
import com.irongteng.comm.InitCommObject;
import com.irongteng.license.ReadLicense;

import dwz.framework.config.AppConfiguration;

/**
 * 
 * @author //add by leimin 2007-03-21
 * 
 */
public class ServerInitialSetting {

    private final Logger logger = Logger.getLogger(ServerInitialSetting.class);
    
    private static LicenseTask licenseTask;
    
    private static final ServerInitialSetting  serverInitialSetting = new ServerInitialSetting();
   
    //下面四个标记用于指示定时器是否正常运行
    public static boolean reqryDataSendTimerRunTag = true;        //发送定时器是否正常运行标记
    public static boolean reqryRequestProcessTimerRunTag = true;  //定时判断内存中有没有轮询任务需要执行的定时器运行是否正常标记
    public static boolean reqryTaskCheckTimerRunTag = true;   //检查数据库有没有轮询任务的定时器运行是否正常标记
    public static boolean reqryTaskIsEndTimerRunTag = true;   //检查轮询任务是否结束定时器是否正常运行标记
    
    private ServerInitialSetting() {}
    
    public static ServerInitialSetting getInstance() {
        return serverInitialSetting;
    }
    
    /**
     * 初始化系统各配置信息
     */
    public void init() {
        
        logger.debug("Init NMS Information...");

        try {
            //澳门调试用于检查操作系统的字符编码
            String encoding = System.getProperty("file.encoding");
            System.out.println("System encoding:" + encoding);
            
            try {
                if (licenseTask != null) {
                    licenseTask.close();
                    licenseTask = null;
                }
                
                licenseTask = new LicenseTask();
                licenseTask.start();
            } catch (Exception e) {
                logger.error("ConfigurationException: ", e);
            }
            
            //系统默认字符编码
        } catch(Exception e) {
            logger.error(e);
        }
        
        //初始化南向接口
        initSouthInterface();
        
        //初始化国际化配制文件
        initInternational();
        
        // 初始化事件调度定时器
        initEventTimer();
        
        // 初始化告警同步配置信息
        initAlarmSynchronization();
         
        // 初始化服务器告警配置信息
        initServerAlarm();
         
        //初始化频繁告警配置信息
        initContinualAlarm();
                
        // 初始化黑名单配置信息
        inintBlacklist();
        
        // 初始化历史数据配置信息
        initHistory();
        
        // 初始化Web接口
        initWebSocket();
        
        //初始化MCPC协议设备，把设备添加到mcpcRepeater中
        //McpTypeData.initMcpcRepeater();
        
        // 初始化轮询数据发送定时器
        initReqryDataSendTimer();
        
        //初始化检测轮询任务定时器
        initReqryTaskCheckTimer();
        
        //初始化监视轮询任务是否结束的定时器
        initReqryTaskIsEndTimer();
        
        //初始化轮询请求处理的定时器
        initReqryRequestProcessTimer();
        
        //批采任务定时器配置信息
        initCatchCollect();
        
        //定时查询批采直放站状态定时器配置信息
        initQueryCatchCollectStatu();
        
        // 读取北向接口配置信息 实例化北向接口对象
         initSNMP();
         
        //设置北向接口对象属性
        initBakupTimer();//数据备份
        
        //初始化重庆北向接口
        initChongQingSubwayService();
        
        // 实例化轮询控制器
        // 调用轮询控制器的checkNoExcute()方法，查询轮询任务表，判断是否存在未执行的轮询任务
        // 判断未执行的轮询任务是否超过执行时间
        // 如未超时，则将未执行的轮询任务放到"未执行的轮询任务"列表中
        // 如超时，则将该轮询任务从轮询任务表中删除
        
        //POI轮询
        initPOIRequryControl();
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        logger.debug("ServerInitialSetting.init() over……");
    }
    
    /**
     * 销毁所有初始化信息
     */
    public void destroy() {
        
        logger.info("service are stopping................");
        
        stopCommService();
        
        if (licenseTask != null) {
            licenseTask.close();
            licenseTask = null;
        }
        
        AppConfiguration.destroy();
        
        System.gc();
        //System.exit(0);
    }
    
    private class LicenseTask extends Thread {
        
        private boolean runStatus = true;
        
        @Override
        public void run() {

            //假如没有获取授权，将不启用ftp功能和UDP服务
            while(!ReadLicense.verify() && runStatus) {
                try {
                    System.out.println("该用户没有获取授权,请使用授权！");
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    this.close();
                    e.printStackTrace();
                }
            }
            
            startService();
            
            while(ReadLicense.verify() && runStatus) {
                
                try {
                    System.out.println("该用户已经获取授权！");
                    Thread.sleep(3600*1000); //1小时检查一次
                } catch (InterruptedException e) {
                    this.close();
                    e.printStackTrace();
                }
            }
            
            stopService();
            
            licenseTask = new LicenseTask();
            licenseTask.start();
        }
        
        public void close() {
            runStatus = false;
            this.interrupt();
        }
    }
    
    private void startService() {
        
        try {
            //启动时间定时器等任务
            AppConfiguration.init();
            //启动通信服务
            startCommService();
            
            //启动任务服务
            ThreadPoolCenter pool = ThreadPoolCenter.getInstance();
            pool.startService();
            
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }
    
    private void stopService() {

        try {
            //停止计划定时器任务
            AppConfiguration.destroy();
            //停止通信服务
            stopCommService();
            
            //停止任务服务
            ThreadPoolCenter pool = ThreadPoolCenter.getInstance();
            pool.stopService();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void startCommService() {
        
        //开启mina的通信服务
        CommFactory commFactory = CommFactory.getInstance();
        commFactory.setReceiveDataListener(ThreadPoolCenter.getInstance());
        commFactory.start();
        
        //开启短信猫通信服务
        InitCommObject modemManager = new InitCommObject();
        modemManager.addReadRptDataListener(null);
        modemManager.setLinkAlarmListener(null);
        modemManager.start();
    }
    
    private void stopCommService() {
        // 停止mina的通信服务
    	CommFactory commFactory = CommFactory.getInstance();
        commFactory.stop();
        
        // 停止短信猫服务
        InitCommObject modemManager = new InitCommObject();
        modemManager.stop();
    }
    
    /**
     *  功能说明:初始化南向接口 主要初始化通信层
     *   
     *  return void
     *  author 吕雷 2016-4-13 10:33:25
     */
    private void initSouthInterface() {
        
    }
    
    /**
     * 初始化告警同步配置信息
     * 
     * add by leimin 2007-03-21
     */
    public void initAlarmSynchronization() {
        
    }
    
    /**
     * void 初始化国际化配制文件 ,具体内容参见 \config2\Internatioal.ini文件
     */
    private void initInternational() {
        
    }

    /**
     * start add by leimin 2007-03-21
     * 
     * 初始化事件调度
     */
    private void initEventTimer() {

    }
    
    /**
     * 初始化黑名单配置信息 add by leimin 2007-03-21
     */
    private void inintBlacklist() {
        
    }

    /**
     * 初始化频繁告警配置信息 add by leimin 2007-03-23
     */
    private void initContinualAlarm() {
        
    }

    /**
     * start add by leimin 2007-03-21
     * 
     * 初始化历史数据配置信息
     */
    private void initHistory() {

    }

    /**
     * start add by leimin 2007-03-27
     * 
     * 获取服务器告警配置信息
     */
    private void initServerAlarm() {
        
    }

    /**
     * author zhudd
     * 轮询任务定时器初始化
     * 获取轮询配置信息
     */

    private void initReqryDataSendTimer() {
        
    }
    
    /**
     * 初始化轮询请求定时器
     *
     */
    private void initReqryRequestProcessTimer() {

    }
    
    /**
     * author zhudd
     *轮询监视定时器初始化
     * 获取轮询配置信息
     */
    private void initReqryTaskIsEndTimer() {
        
    }
    
    /**
     * author zhudd
     * 
     * 获取查询轮询表定时器配置信息
     */

    private void initReqryTaskCheckTimer() {
        
    }
    
    
    /**
     * author zhudongdong 2007-04-30
     * 
     * 批采定时器配置信息
     */
    private void initCatchCollect() {
        
    }
    
    private void initQueryCatchCollectStatu(){
        
    }
    
    /**
     * 初始化备份定时器
     *
     */
    private void initBakupTimer() {

    }
    
    /**
     * 初始化SNMP北向接口
     */
    private void initSNMP() {
        
    }
    
    /**
     * 初始化后台程序与web服务之间的通信
     * 
     */
    private void initWebSocket() {
        
    }
    
    /**
     * 初始化远程升级定时器
     * 
     * @param repeaterOperation
     */
    private void initRemoteUpdateSendTimer() {
        
    }
    /**
     * 初始化POI轮询控制器
     * 
     */
    private void initPOIRequryControl() {
        
    }
    
    //初始化重庆地铁接口
    private void initChongQingSubwayService() {
        
    }
}
