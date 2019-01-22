package com.irongteng.comm.modem;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import com.irongteng.comm.listener.ReadRptDataListener;
import com.irongteng.conf.AreaConfig;
import com.irongteng.conf.ModemInfo;

/**
 * Project: OMCServerCommunication Package: com.omcserver.communication.modem
 * Class name: SiemensModem.java 功能说明:SiemensModem类，继承了ModemBase类，实现短信、数传的收发
 * Copyright (c) 2007-2009 winhap Systems, Inc. All rights reserved. Created by:
 * liuqing Changed by: liuqing On: 2007-4-5 16:04:05
 */
public class SimplnanoModem extends ModemBase {
    
    private final static String AT_CMGF = "+CMGF";   //设置短信模式（0-PDU 1-Text文本格式）

    private final static String AT_CNMI = "+CNMI";   //新短信提示方式

    private final static String AT_CSMP = "+CSMP";   //设置文本格式参数

    private final static String AT_CSCA = "+CSCA";   //获取或设置短信中新号码

    private final static String AT_CMGR = "+CMGR";   //读短信

    private final static String AT_CMGS = "+CMGS";   //发送短信

    private final static String AT_CMGD = "+CMGD";   //删除短信

    private final static String AT_CMTI = "+CMTI";   //新消息

    private final static String AT_CIMI = "+CIMI";   //判断SIM卡是否存在
    
    private final static String AT_RST = "+RST";     //复位控制
    
    private final static String AT_CPOF = "+CPOF";   //关机控制

    private final static int MaxCount_ShortMsg = 50; // 最大短信数

    private String tempStr = ""; // 用于临时存放读取短信的附属信息

    private boolean isReceiveFull = false; // 是否接收到完整的短信

    private int unreadSmsCounter = 0; // 未读短信数量计数器
    
    private final Object signalLight = "true"; // 线程同步信号灯，设置或unreadSmsCount必须先获取信号
    
    private ReadSmsThread readSmsThread = null;

    private boolean sendingSmsFlag = false; // 正在发送短信流程

    // private Command command = new Command();

    /**
     * 构造器
     * 
     * @param listener
     *            直放站数据侦听者
     * @param modemInfo
     *            modem配置信息
     */
    public SimplnanoModem(Vector<ReadRptDataListener> listener, ModemInfo modemInfo) {
        super(listener, modemInfo);
    }

    /**
     * 构造器
     * 
     * @param listener
     *            直放站数据侦听者
     * @param modemInfo
     *            modem配置信息
     * @param flowControlIn
     *            输入控制流
     * @param flowControlOut
     *            输出控制流
     * @param databits
     *            数据位
     * @param stopbits
     *            停止位
     * @param parity
     *            基偶校验
     */
    public SimplnanoModem(Vector<ReadRptDataListener> listener, ModemInfo modemInfo, int flowControlIn, int flowControlOut, int databits,
            int stopbits, int parity) {
        super(listener, modemInfo, flowControlIn, flowControlOut, databits, stopbits, parity);
    }
    
    /**
     * 功能说明:SimplnanoModem初始化
     * 
     * @param readMsg
     *            是否读取短信 return boolean 初始化是否成功 author liuqing 2007-4-5 15:50:18
     * @return 
     */
    @Override
    public boolean init(boolean readMsg) {
        boolean result = super.init(readMsg);
        initing = true;
        
        if (result) {
            // 设置选择短信的模式(TEXT模式)
            result = setCMGF(1);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (result) {
                // 设置短信提示方式
                result = setCNMI();
                if (result) {
                    // 设置文本格式参数
                    result = setCSMP();// 如果设置该指令，短信将不能保存在手机上
                    if (result) {
                        initing = false;
                        // 读取或者删除Modem中存放的短信
                        for (int i = 1; i < MaxCount_ShortMsg; i++) {
                            if (readMsg) {
                                if (!readShortMsg(i)) {
                                    break;
                                }
                            } else {
                                deleteShortMsg(i); // 删除短信
                            }
                        }
                    }
                }
            }
        }
        initing = false;
        return result;
    }
    
    @Override
    public int open() {
        int result = super.open();
        if (result == 0) {
            if (readSmsThread != null) {
                readSmsThread.stopFlag = true;
                readSmsThread.interrupt();
                readSmsThread = null;
            }
            readSmsThread = new ReadSmsThread();
            readSmsThread.start();
        }
        return result;
    }

    @Override
    public void close() {
        if (readSmsThread != null) readSmsThread.terminate();
        super.close();
    }
    
    // 先读Modem中的短信 ，然后删除
    protected void readAndDeleteSms(int index) {
        for (int i = 1; i < index; i++) {
            readShortMsg(i); // 读短信
            // deleteShortMsg(i); // 删除短信
        }
    }

    /**
     * 功能说明:接收基类传来的信息
     * 
     * @param data
     *            串口数据 return void author liuqing 2007-4-5 15:50:18
     */
    @Override
    protected void informReadData(String data) {
        
        logger.debug("simens modem 收到数据:" + data);
        
        if (isReceiveFull) {
            isReceiveFull = false;
            analyzeCMGR(tempStr + data);
            return;
        }
        // 如果以“OK”开始，调用analyzeResponse(true)通知响应正常
        if (data.startsWith(C_OK) || data.startsWith(C_GT)) {
            analyzeResponse(true);
        } else if (data.startsWith(C_ERROR)) { // 如果以“ERROR”开始，调用analyzeResponse(false)通知响应异常
            analyzeResponse(false);
        } else if (data.length() > 5 && data.substring(0, 5).equals(AT_CMGS)) { // 如果包含“+CMGS”，调用analyzeCMGS(s)通知收到发送短信应答
            analyzeCMGS(data);
        } else if (data.length() > 5 && data.substring(0, 5).equals(AT_CSCA)) {// 如果包含“+CSCA”，调用analyzeCSCA(s)通知收到短信中心电话号码应答
            analyzeCSCA(data);
        } else if (data.contains("\r\n>")) { // 如果是“>”，调用analyzeCMGSSendData(s)通知传输需要发送的数据
            analyzeCMGSSendData(data);
        }
        
        // 如果包含“+CMGR”，调用analyzeCMGR(s)通知收到读短信应答
        if (data.length() > 5 && data.contains(AT_CMGR)) {
            if (data.indexOf("REC UNREAD") > 0 && !data.contains("\r\n")) {
                isReceiveFull = true;
                tempStr = tempStr + data;
            } else {
                logger.info("收到的短信内容为：" + data);
                String data1 = data.substring(data.indexOf("\r\n"));
                String newData = data1.substring(1, data1.trim().indexOf("OK"));
                String phoneNumber = data.substring((data.indexOf("+86") + "+86".length()), (data.indexOf("+86") + 14));
                
                this.sendEnglishSMS(phoneNumber, newData.trim());
                
                analyzeCMGR(data);
            }
        }
        // 如果包含 "+CMTI"，调用analyzeNewShortMsg(s)通知收到短信
        if (data.length() > 5 && data.contains(AT_CMTI)) {
            analyzeNewShortMsg(data);
        }
    }

    /**
     * 功能说明:接收短信
     * 
     * @param data
     *            短信 return void author lvlei 2007-4-5 15:55:18
     */
    public void analyzeNewShortMsg(String data) {
        String[] splitData = data.split("\r\n");
        for (String sData : splitData) {
            // +CMTI: "SM",1
            sData = sData.trim();
            int i = sData.indexOf(",");
            if (i >= 0) {
                try {
                    final int newSmsIndex = Integer.parseInt(sData.substring(i + 1, sData.length()));
                    setUnreadSmsCounter(newSmsIndex);
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                }
            }
        }
    }

    /**
     * 功能说明:读短信时，modem的应答数据
     * 
     * @param data
     *            应答数据 return void author liuqing 2007-4-5 16:00:44
     */
    public void analyzeCMGR(String data) {
        tempStr = "";
        // +CMGR: "REC
        // UNREAD","+8613590344304",,"04/06/06,11:47:14+00"[0D][0A]#SOK#
        if (atCmd != null && atCmd.getCmdType() == 2) {
            atCmd.setData(data);
            atCmd.setStatus(true);
            atCmd.setResponse(true);
        }
    }

    /**
     * 功能说明:送短信时，modem的应答数据
     * 
     * @param data
     *            应答数据 return void author liuqing 2007-4-5 16:01:17
     */
    public void analyzeCMGS(String data) {
        // +CMGS: 236[0D][0A][0D][0A]OK[0D][0A]
        if (atCmd != null && atCmd.getCmdType() == 3) {
            atCmd.setData(data);
            atCmd.setStatus(true);
            atCmd.setResponse(true);
        }
    }

    /**
     * 功能说明:读取短消息中心号码时，modem的应答数据
     * 
     * @param data
     *            应答数据 return void author liuqing 2007-4-5 16:01:37
     */
    public void analyzeCSCA(String data) {
        // +CSCA: "+8613010888500",145[0D][0A][0D][0A]OK[0D][0A]
        if (atCmd != null && atCmd.getCmdType() == 4) {
            atCmd.setData(data);
            atCmd.setStatus(true);
            atCmd.setResponse(true);
        }
    }

    /**
     * 功能说明:收到">",提示发送数据
     * 
     * @param data
     *            return void author liuqing 2007-4-5 16:02:51
     */
    public void analyzeCMGSSendData(String data) {
        
        if (atCmd != null && atCmd.getCmdType() == 5) {
            atCmd.setData(data);
            atCmd.setStatus(true);
            atCmd.setResponse(true);
        } else {
            atCmd = sendATData("" + (char) 0x1A, 3, 20);
        }
    }

    /**
     * 清除Modem中的短信
     * @return 
     */
    @Override
    public boolean clearShortMsg() {
        // 逐条删除Modem中的短信
        for (int i = 1; i <= 40; i++) {
            if (!deleteShortMsg(i)) {
                break;
            }
        }
        return true;
    }

    /**
     * 删除Modem中的某条短信
     * @param index
     * @return 
     */
    @Override
    public boolean deleteShortMsg(int index) {
        // 正在发送短信，等待中
        while (this.sendingSmsFlag) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        // 向串口发送删除短信的AT指令
        atCmd = sendATData(AT_AT + AT_CMGD + "=" + index + C_Return, 1, 3);
        return !(atCmd == null || !atCmd.getResponse() || !atCmd.getStatus());
    }
    
    /**
     * 关机：控制模块的关机
     * @return 
     */
    @Override
    public boolean shutdownModule() {
        atCmd = sendATData(AT_AT + AT_CPOF + C_Return, 1, 30);
        return !(atCmd == null || !atCmd.getResponse() || !atCmd.getStatus());
    }
    
    /**
     * 复位：控制模块的热复位
     * @return
     */
    @Override
    public boolean resetModule() {
        atCmd = sendATData(AT_AT + AT_RST + C_Return, 1, 3);
        return !(atCmd == null || !atCmd.getResponse() || !atCmd.getStatus());
    }
    /**
     * 读取Modem中的某条短信
     * @return 
     */
    @Override
    public boolean readShortMsg(int index) {
        // 正在发送短信，等待中
        while (this.sendingSmsFlag) {

            try {
                Thread.sleep(100);
            } catch (Exception e) {

            }
        }
        // 向串口发送读取短信的AT指令
        atCmd = sendATData(AT_AT + AT_CMGR + "=" + index + C_Return, 2, 40);
        if (atCmd == null || !atCmd.getResponse() || !atCmd.getStatus()) {
            return false;
        }
        try {
            String data = atCmd.getData();
            if (data.trim().startsWith("+CMGR: 0,,0")) {
                return false;
            }
            String[] datas = data.split(",");
            if (!datas[0].contains("REC UNREAD")) {
                Thread.sleep(1000);
                deleteShortMsg(index);
                return true;
            }
            String phone = datas[1].substring(1, datas[1].length() - 1);
            AreaConfig config = AreaConfig.getInstance();
            String area = config.getArea().trim();
            if (phone.startsWith(area)) {
                phone = phone.substring(area.length(), phone.length());
            } else if (phone.substring(1).startsWith(area)) {
                phone = phone.substring(area.length() - 1, phone.length());
            } else if (phone.startsWith("+")) {
                phone = phone.substring(1, phone.length());
            }
            String sSentDate = datas[3].substring(1, datas[3].length());
            String sSentTime = datas[4].substring(0, 8);
            int i = datas[datas.length - 1].indexOf("\"");
            String content = datas[datas.length - 1].substring(i + 1, datas[datas.length - 1].length());
            Calendar c = new GregorianCalendar();
            c.set(Integer.parseInt(sSentDate.substring(0, 2) + 2000), Integer.parseInt(sSentDate.substring(3, 5)),
                    Integer.parseInt(sSentDate.substring(6, 8)), Integer.parseInt(sSentTime.substring(0, 2)),
                    Integer.parseInt(sSentTime.substring(3, 5)), Integer.parseInt(sSentTime.substring(6, 8)));
            Date sentTime = c.getTime();
            //删除短信
            deleteShortMsg(index);
            
            if (index >= 15) {
                for (int k = 1; k < 15; k++) {
                    Thread.sleep(10);
                    deleteShortMsg(k);
                }
            }
            receiveShortMsg(phone, content, sentTime);
            return true;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return false;
        }
    }
    
    /**
     * 获取短信中心电话号码
     * @return 
     */
    @Override
    public String getShortMsgPhone() {
        // 向串口发送获取电话号码的指令
        atCmd = sendATData(AT_AT + AT_CSCA + "?" + C_Return, 4, 3);
        if (atCmd == null || !atCmd.getResponse() || !atCmd.getStatus()) {
            return "";
        }
        String data = atCmd.getData();
        try {
            int i = data.indexOf("\"");
            data = data.substring(i + 1, data.length());
            i = data.indexOf("\"");
            String phone = data.substring(0, i);
            return phone;
        } catch (Exception ex) {

        }
        return null;
    }

    /**
     * 检查电话卡是否存在
     * @return 
     */
    @Override
    public boolean isSIMExist() {
        // 向串口发送检查电话卡是否存在的AT指令
        atCmd = sendATData(AT_AT + AT_CIMI + "=?" + C_Return, 1, 3);
        return !(atCmd == null || !atCmd.getResponse() || !atCmd.getStatus());
    }

    /**
     * 发送英文短信
     * @param destPhone
     * @param content
     * @return 
     */
    @Override
    synchronized public boolean sendEnglishSMS(String destPhone, String content) {
        this.sendingSmsFlag = true;
        try {
            // 向串口发送短信发送的AT指令
            atCmd = sendATData(AT_AT + AT_CMGS + "=\"" + destPhone + "\"" + C_Return, 5, 6);
            
            if (atCmd == null || !atCmd.getResponse() || !atCmd.getStatus()) 
                return false;
            
            atCmd = sendATData(content + (char) 0x1A, 3, 28);
            return !(atCmd == null || !atCmd.getResponse() || !atCmd.getStatus());
        } finally {
            this.sendingSmsFlag = false;
        }
    }

    /**
     * 发送中文短信
     * @param destPhone
     * @param content
     * @return 
     */
    @Override
    public synchronized boolean sendChineseSMS(String destPhone, String content) {
        this.sendingSmsFlag = true;
        
        try {
            String smsc = modemInfo.getSMSCenterPhone();
            PduPackBig5 pdu = new PduPackBig5();
            pdu.setAddr(destPhone);
            pdu.setMsgCoding(8);// 0:表示7-BIT编码 4:表示8-BIT编码 8:表示UCS2编码
            pdu.setMsgContent(content);
            pdu.setSmsc(smsc);
            
            setCMGF(0);// 设置短信模式为pdu模式
            // 向串口发送短信发送的AT指令
            atCmd = sendATData(AT_AT + AT_CMGS + "=" + pdu.size() + C_Return, 5, 6);
            if (atCmd == null || !atCmd.getResponse() || !atCmd.getStatus()) {
                return false;
            }
            
            //获取发送的PDU内容
            String codedResult = pdu.getCodedResult();
            atCmd = sendATData(codedResult + (char) 0x1A, 3, 28);
            return !(atCmd == null || !atCmd.getResponse() || !atCmd.getStatus());
        } finally {
            setCMGF(1);
            
            this.sendingSmsFlag = false;
        }
    }

    /**
     * 设置选择短信的模式（提示新消息）
     * 
     * @return 返回AT指令是否执行成功
     */
    private boolean setCNMI() {
        atCmd = sendATData(AT_AT + AT_CNMI + "=3,1" + C_Return, 1, 3);
        // atCmd = sendATData(AT_AT + AT_CNMI + "=3,1,,1" + C_Return, 1, 3);
        return !(atCmd == null || !atCmd.getResponse() || !atCmd.getStatus());
    }

    /**
     * 设置短信提示方式
     * 
     * @return 返回AT指令是否执行成功
     */
    private boolean setCMGF(int smsMode) {
        atCmd = sendATData(AT_AT + AT_CMGF + "=" + smsMode + C_Return, 1, 3);
        return !(atCmd == null || !atCmd.getResponse() || !atCmd.getStatus());
    }


    /**
     * 设置文本格式参数
     * 
     * @return 返回AT指令是否执行成功
     */
    private boolean setCSMP() {
        atCmd = sendATData(AT_AT + AT_CSMP + "=17,167,0,0" + C_Return, 1, 3);// 老款modem设置
        return !(atCmd == null || !atCmd.getResponse() || !atCmd.getStatus());
    }


    // 设置未读短信计数器
    private void setUnreadSmsCounter(int unreadSmsCounter) {
        try {
            synchronized (signalLight) {
                if (unreadSmsCounter > this.unreadSmsCounter) {
                    this.unreadSmsCounter = unreadSmsCounter;
                    signalLight.notifyAll();
                }
            }
        } catch (Exception e) {
            
        }
    }

    // 读取未读短信线程
    class ReadSmsThread extends Thread {
        private boolean stopFlag = false;

        public void terminate() {
            stopFlag = true;
        }

        @Override
        public void run() {
            int smsIndex = 0;
            while (!stopFlag) {
                try {
                    synchronized (signalLight) {
                        smsIndex = unreadSmsCounter;
                        if (unreadSmsCounter > 0) {
                            unreadSmsCounter--;
                            signalLight.wait(500);
                        } else {
                            signalLight.wait();
                        }
                    }
                    if (smsIndex > 0) {
                        readShortMsg(smsIndex);
                    }
                } catch (Exception e) {
                    
                }
            }
        }
    }
    
}
