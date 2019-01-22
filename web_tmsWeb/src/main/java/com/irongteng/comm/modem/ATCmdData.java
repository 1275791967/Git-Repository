package com.irongteng.comm.modem;

/**
 * Project: OMCServerCommunication Package: com.omcserver.communication.modem
 * Class name: ATCmdData.java 功能说明:该类是记录向串口发送AT指令的数据，并记录该指令是否返回 Copyright (c)
 * 2007-2009 winhap Systems, Inc. All rights reserved. Created by: liuqing
 * Changed by: liuqing On: 2007-4-5 16:18:16
 */
public class ATCmdData {

    public static final int ATTYPE_OK = 1;
    public static final int ATTYPE_DELSMS = 1;
    public static final int ATTYPE_READSMS = 2;
    public static final int ATTYPE_SENDSMS = 3;
    public static final int ATTYPE_CSCA = 4;
    public static final int ATTYPE_CMGSDATA = 5;
    // 标志串口是否返回响应
    private boolean isResponse = false;

    /**
     * 1：仅仅需要OK和ERROR状态的命令 2：CMGR 读短信 3：CMGS 发送短信 4：CSCA 短信中心电话号码 5：CMGSData
     */
    private int cmdType = 1; // AT指令的类型

    /**
     * 仅仅对cmdType >= 2的命令有效
     */
    private String data;

    /**
     * true： OK false：ERROR
     */
    private boolean status = false; // 标志响应是否正确

    private String doingAtCmd = ""; // 当前正在执行的At指令

    public void setDoingAtCmd(String doingAtCmd) {
        this.doingAtCmd = doingAtCmd;
    }

    public String getDoingAtCmd() {
        return this.doingAtCmd;
    }

    public int getCmdType() {
        return cmdType;
    }

    public void setCmdType(int cmdType) {
        this.cmdType = cmdType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean getResponse() {
        return isResponse;
    }

    public void setResponse(boolean isResponse) {
        this.isResponse = isResponse;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
