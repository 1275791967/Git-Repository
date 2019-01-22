package com.irongteng.comm;

import com.irongteng.comm.listener.LinkAlarmListener;

/**
 * Project: OMCServerCommunication Package: com.omcserver.communication Class
 * name: CommunicationManager.java 功能说明: 通信管理器接口 Copyright (c) 2007-2009 winhap
 * Systems, Inc. All rights reserved. Created by: liuqing Changed by: liuqing
 * On: 2007-4-4 13:51:21
 */
public class CommObjectIF {

    protected volatile boolean busy = true; // 标志modem是否处于忙状态

    protected volatile boolean fault = true; // 标志modem是否处于故障状态

    protected int priority = 0; // 发送数据的优先级

    protected AllowCommunicationMode allowCommunicationMode = new AllowCommunicationMode(); // 支持的通信方式
    
    public static final int MAX_SEND_FAIL_COUNT = 3;      //最大发送失败次数
    
    public static final int MAX_SERVICE_FAIL_COUNT = 3; //最大服务重启次数
    
    protected volatile int sendFailCounter = 0;     // 失败次数计数器
    
    protected volatile int serviceFailCounter = 0; // 通信服务统计失败次数计数器
    
    protected long lastUsedTime = System.currentTimeMillis();// 最后使用时间
    
    /**
     * 功能说明: modem是否处于忙状态
     * 
     * @return modem是否忙 return boolean author liuqing 2007-4-5 16:12:48
     */
    public boolean isBusy() {
        return busy;
    }

    /**
     * 功能说明:modem是否处于故障状态
     * 
     * @return modem是否故障 return boolean author liuqing 2007-4-5 16:13:50
     */
    public boolean isFault() {
        return fault;
    }

    /**
     * 功能说明:获取优先级
     * 
     * @return return int author liuqing 2007-4-5 16:14:25
     */
    public int getPriority() {
        return priority;
    }

    /**
     * 功能说明:获取所支持的通信方式
     * 
     * @return return AllowCommunicationMode author liuqing 2007-4-5 16:14:59
     */
    public AllowCommunicationMode getAllowCommunicationMode() {
        return allowCommunicationMode;
    }

    /**
     * 功能说明:获取modem的短信号码,此方法仅用于modem通信时有意义,其他方式都返回null
     * 
     * @return return String author liuqing 2007-4-6 14:10:00
     */
    public String getSmsPhone() {
        return null;
    }

    /**
     * 功能说明:获取modem的数传号码,此方法仅用于modem通信时有意义,其他方式都返回null
     * 
     * @return return String author liuqing 2007-4-6 14:10:37
     */
    public String getDataTransferPhone() {
        return null;
    }

    /**
     * 功能说明:向外部提供发送数据的接口
     * 
     * @param data
     *            需要向直放站发送的数据
     * @param phoneOrIP
     *            电话号码或者IP
     * @param sendMode
     *            发送方式
     * @return return boolean author liuqing 2007-4-5 16:16:24
     */
    public synchronized boolean sendData(byte[] data, String phoneOrIP, int sendMode) {
        return false;
    }

    public long getLastUsedTime() {
        return lastUsedTime;
    }

    public void setLastUsedTime(long lastUsedTime) {
        this.lastUsedTime = lastUsedTime;
    }

    /**
     * 功能说明：初始化，启动服务
     *
     */
    public void startService() {
        
    }

    /**
     * 功能说明：停止服务
     *
     */
    public void stopService() {
        
    }

    /**
     * 链路告警侦听
     * 
     * @param linkAlarmListener
     *            链路告警侦听者
     */
    public void setLinkAlarmListener(LinkAlarmListener linkAlarmListener) {
        
    }

    /**
     * Project: OMCServerCommunication Package: com.omcserver.communication
     * Class name: CommunicationMode.java 功能说明: 各个通信管理器支持的通信方式 Copyright (c)
     * 2007-2009 winhap Systems, Inc. All rights reserved. Created by: liuqing
     * Changed by: liuqing On: 2007-4-5 17:25:41
     */
    public class AllowCommunicationMode {
        
        private boolean allowSms = false; // 是否支持短信方式

        private boolean allowDataTransfer = false; // 是否支持数传方式

        private boolean allowGPRS = false; // 是否支持GPRS方式

        private boolean allowTcp = false; // 是否支持socket通信方式

        private boolean allowUdp = false;// 是否支持UDP通信方式

        public boolean isAllowDataTransfer() {
            return allowDataTransfer;
        }

        public void setAllowDataTransfer(boolean allowDataTransfer) {
            this.allowDataTransfer = allowDataTransfer;
        }

        public boolean isAllowGPRS() {
            return allowGPRS;
        }

        public void setAllowGPRS(boolean allowGPRS) {
            this.allowGPRS = allowGPRS;
        }

        public boolean isAllowSms() {
            return allowSms;
        }

        public void setAllowSms(boolean allowSms) {
            this.allowSms = allowSms;
        }

        public boolean isAllowTcp() {
            return allowTcp;
        }

        public void setAllowTcp(boolean allowTcp) {
            this.allowTcp = allowTcp;
        }

        public boolean isAllowUdp() {
            return allowUdp;
        }

        public void setAllowUdp(boolean allowUdp) {
            this.allowUdp = allowUdp;
        }
    }
}