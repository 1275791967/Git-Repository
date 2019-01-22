package com.irongteng.conf;

/**
 * Project: OMCServerCommunication Package: com.omcserver.communication.config
 * Class name: LinkAlarmParaID.java 功能说明: Copyright (c) 2007-2009 winhap
 * Systems, Inc. All rights reserved. Created by: liuqing Changed by: liuqing
 * On: 2007-4-19 18:57:26
 */
public final class LinkAlarmConfig extends AbstractSystemConfig{
    
    private int modemFaultAlarmID;// Modem通信故障参数标识
    
    private int ipFaultAlarmID;// IP数据通信故障参数标识
    private int SMCLoginFaultAlarmID;// 短信网关登录异常故障参数标识
    private int SMCCommFaultAlarmID;// 网关链路故障参数标识

    private boolean isModemFaultAlarm;// Modem通信故障告警

    private boolean isIpFaultAlarm;// IP数据通信故障告警
    private boolean isSmcLoginFaultAlarm;// 短信网关登录异常故障告警
    private boolean isSmcCommFaultAlarm;// 网关链路故障告警

    private static LinkAlarmConfig config = null;
    
    /*
     * 私有的构造函数
     */
    private LinkAlarmConfig() {
        
        try {
            
            this.loadSecionProperties("LinkAlarmParam");
            
            try {
                setModemFaultAlarmID(Integer.parseInt(getProperty("ModemFaultAlarmID", "64004")));
                setIpFaultAlarmID(Integer.parseInt(getProperty("IPFaultAlarmID", "64005")));
                setSMCLoginFaultAlarmID(Integer.parseInt(getProperty("SMCLoginFaultAlarmID", "64007")));
                setSMCCommFaultAlarmID(Integer.parseInt(getProperty("SMCCommFaultAlarmID", "64006")));
                setModemFaultAlarm(Boolean.parseBoolean(getProperty("isModemFaultAlarm", "false")));
                setIpFaultAlarm(Boolean.parseBoolean(getProperty("isIpFaultAlarm", "false")));
                setSmcLoginFaultAlarm(Boolean.parseBoolean(getProperty("isSmcLoginFaultAlarm", "false")));
                setSmcCommFaultAlarm(Boolean.parseBoolean(getProperty("isSmcCommFaultAlarm", "false")));
            } catch (NumberFormatException e) {
                setIpFaultAlarm(false);
                setIpFaultAlarmID(64005);
                setModemFaultAlarm(false);
                setModemFaultAlarmID(64004);
                setSmcCommFaultAlarm(false);
                setSMCCommFaultAlarmID(64006);
                setSmcLoginFaultAlarm(false);
                setSMCLoginFaultAlarmID(64007);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 单例类，获取该类的唯一对象
     * 
     * @return
     */
    public static LinkAlarmConfig getInstance() {
        if (config == null) {
            config = new LinkAlarmConfig();
        }
        return config;
    }
    
    public int getIpFaultAlarmID() {
        return ipFaultAlarmID;
    }

    public void setIpFaultAlarmID(int ipFaultAlarmID) {
        setProperty("IPFaultAlarmID", ipFaultAlarmID + "");
        this.ipFaultAlarmID = ipFaultAlarmID;
    }
    
    public boolean isIpFaultAlarm() {
        return isIpFaultAlarm;
    }
    
    public void setIpFaultAlarm(boolean isIpFaultAlarm) {
        setProperty("isIpFaultAlarm", isIpFaultAlarm + "");
        this.isIpFaultAlarm = isIpFaultAlarm;
    }
    
    public boolean isModemFaultAlarm() {
        return isModemFaultAlarm;
    }

    public void setModemFaultAlarm(boolean isModemFaultAlarm) {
        setProperty("isModemFaultAlarm", isModemFaultAlarm + "");
        this.isModemFaultAlarm = isModemFaultAlarm;
    }

    public boolean isSmcCommFaultAlarm() {
        return isSmcCommFaultAlarm;
    }

    public void setSmcCommFaultAlarm(boolean isSmcCommFaultAlarm) {
        setProperty("isSmcLoginFaultAlarm", isModemFaultAlarm + "");
        this.isSmcCommFaultAlarm = isSmcCommFaultAlarm;
    }

    public boolean isSmcLoginFaultAlarm() {
        return isSmcLoginFaultAlarm;
    }

    public void setSmcLoginFaultAlarm(boolean isSmcLoginFaultAlarm) {
        setProperty("isSmcLoginFaultAlarm", isSmcLoginFaultAlarm + "");
        this.isSmcLoginFaultAlarm = isSmcLoginFaultAlarm;
    }

    public int getModemFaultAlarmID() {
        return modemFaultAlarmID;
    }

    public void setModemFaultAlarmID(int modemFaultAlarmID) {
        setProperty("ModemFaultAlarmID", modemFaultAlarmID + "");
        this.modemFaultAlarmID = modemFaultAlarmID;
    }

    public int getSMCCommFaultAlarmID() {
        return SMCCommFaultAlarmID;
    }

    public void setSMCCommFaultAlarmID(int SMCCommFaultAlarmID) {
        setProperty("SMCCommFaultAlarmID", SMCCommFaultAlarmID + "");
        this.SMCCommFaultAlarmID = SMCCommFaultAlarmID;
    }

    public int getSMCLoginFaultAlarmID() {
        return SMCLoginFaultAlarmID;
    }

    public void setSMCLoginFaultAlarmID(int SMCLoginFaultAlarmID) {
        setProperty("SMCLoginFaultAlarmID", SMCLoginFaultAlarmID + "");
        this.SMCLoginFaultAlarmID = SMCLoginFaultAlarmID;
    }

    public static void main(String[] args) {
        LinkAlarmConfig setting = LinkAlarmConfig.getInstance();
        System.out.println(setting.getIpFaultAlarmID());

    }
}
