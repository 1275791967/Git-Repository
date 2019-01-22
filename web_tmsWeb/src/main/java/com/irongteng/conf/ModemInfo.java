package com.irongteng.conf;

/**
 * Project: OMCServerCommunication Package: com.omcserver.communication Class
 * name: ModemConfig.java 功能说明: Copyright (c) 2007-2009 winhap Systems, Inc. All
 * rights reserved. Created by: liuqing Changed by: liuqing On: 2007-4-4
 * 17:25:49
 */
public final class ModemInfo {
    
    private String portName; // 串口名称

    private String modemType; // Modem的类型

    private int baudRate; // 波特率

    private String smsPhone; // modem上的电话号码

    private String dataTransferPhone; // 数传电话号码

    private String SMSCenterPhone; // 短消息中心号码

    private boolean isUsed = false; // 是否使用

    private int priority = 0;// 优先级

    private boolean isSupportGPRS; // 是否支持GPRS

    private int GPRSInterceptionPort; // GPRS的侦听端口
    
    private String portInfo;  //串口信息，包含所有信息的字符串
    
    public ModemInfo(String portName, String portInfo) {
        
        this.portInfo = portInfo;
        this.setPortName(portName);
        
        String[] str = portInfo.split(",");
        this.setModemType(str[0].trim());
        try {
            this.setBaudRate(Integer.parseInt(str[1].trim()));
        } catch (NumberFormatException e) {
            this.setBaudRate(9600);
        }

        this.setSmsPhone(str[2].trim());
        this.setDataTransferPhone(str[3].trim());
        String smsc = str[4].trim();
        this.setSMSCenterPhone(!smsc.equals("") ? smsc : null);
        try {
            this.setUsed(Boolean.parseBoolean(str[5].trim()));
        } catch (Exception e) {
            this.setUsed(false);
        }
        try {
            this.setPriority(Integer.parseInt(str[6].trim()));
        } catch (NumberFormatException e) {
            this.setPriority(1);
        }
        try {
            this.setSupportGPRS(Boolean.parseBoolean(str[7].trim()));
        } catch (Exception e) {
            this.setSupportGPRS(false);
        }
        try {
            this.setGPRSInterceptionPort(Integer.parseInt(str[8].trim()));
        } catch (NumberFormatException e) {
            this.setSupportGPRS(false);
        }
    }

    public int getBaudRate() {
        return baudRate;
    }

    public void setBaudRate(int baudRate) {
        this.baudRate = baudRate;
    }

    public int getGPRSInterceptionPort() {
        return GPRSInterceptionPort;
    }

    public void setGPRSInterceptionPort(int interceptionPort) {
        GPRSInterceptionPort = interceptionPort;
    }

    public boolean isSupportGPRS() {
        return isSupportGPRS;
    }

    public void setSupportGPRS(boolean isSupportGPRS) {
        this.isSupportGPRS = isSupportGPRS;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }

    public String getModemType() {
        return modemType;
    }

    public void setModemType(String modemType) {
        this.modemType = modemType;
    }

    public String getDataTransferPhone() {
        return dataTransferPhone;
    }

    public void setDataTransferPhone(String dataTransferPhone) {
        this.dataTransferPhone = dataTransferPhone;
    }

    public String getSmsPhone() {
        return smsPhone;
    }

    public void setSmsPhone(String smsPhone) {
        this.smsPhone = smsPhone;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getSMSCenterPhone() {
        return SMSCenterPhone;
    }

    public void setSMSCenterPhone(String centerPhone) {
        SMSCenterPhone = centerPhone;
    }
    
    public String getPortInfo() {
        return portInfo;
    }
    /**
     * 功能说明:
     * 
     * @param args
     *            return void author liuqing 2007-4-4 17:25:49
     */
    public static void main(String[] args) {
        ModemInfo info = new ModemInfo("COM3", "siemens,115200,16385132,324235423,,true,10,false,7890");
        System.out.println(info.getSMSCenterPhone() == null);
    }
}
