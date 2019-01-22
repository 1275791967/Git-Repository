/**
 * 
 * Modem基本类，提供modem通用的方法
 * 
 */
package com.irongteng.comm.modem;

import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irongteng.comm.CommModeConstants;
import com.irongteng.comm.listener.ReadRptDataListener;
import com.irongteng.conf.ModemInfo;

import dwz.common.util.StringUtils;

/**
 * 所有Modem类的基类，实现了数传、gprs及短信功能
 * 
 * @author 吕雷
 * 
 */
public class ModemBase implements ModemBaseInterface, SerialReadListener, ModemSmsInterface, ModemDataTransferInterface {
    
    public Logger logger = LoggerFactory.getLogger(this.getClass());
    
    public final static String C_Return = "\r\n";// 回车符
    
    protected final static String ATZ = "ATZ";   //恢复用户缺省值
    
    protected final static String ATZ0 = "ATZ0"; //恢复出厂缺省值
    
    protected final static String ATE0 = "ATE0"; // 初始化指令（这里设置为不显示回显）
    
    protected final static String ATE1 = "ATE1"; // 设置回显
    
    protected final static String AT_W = "AT&W";   //保存用户配置
    
    protected final static String AT_AT = "AT";    // AT指令头
    
    protected final static String AT_ATD = "ATD";  // 拨号指令
    
    protected final static String AT_ATH = "ATH";  // 挂断数传
    
    protected final static String AT_ATA = "ATA";  //接听电话
    
    protected final static String AT_CGDCONT = "AT+CGDCONT"; // 定义PDP（Packet Data Protocol）上下文
    
    protected final static String AT_CGATT = "AT+CGATT";   // 连接与分离GPRS（1：连接；0：分离）
    
    protected final static String AT_CGACT = "AT+CGACT";   // 激活或失效PDP上下文（1：激活；0：未激活）
    
    protected final static String AT_CGDATA = "AT+CGDATA"; // 进入数据状态
    
    protected final static String AT_CGEREP = "AT+CGEREP"; // 事件报告

    protected final static String C_OK = "OK";
    
    protected final static String C_GT = ">";  //输入框，表示需要有内容输入的情况下
    
    protected final static String C_ERROR = "ERROR";

    private final static String C_NoDialtone = "NO DIALTONE";

    private final static String C_Busy = "BUSY";

    private final static String C_NoCarrier = "NO CARRIER";

    private final static String C_Connect = "CONNECT";

    private final static String C_NoAnswer = "NO ANSWER";

    private final long MAXIDLETIME = 55 * 1000; // 数传链路最大空闲时间

    private final long SWITHIDLETIME = 10 * 1000; // 允许挂断当前数传的时间（空闲时间）

    protected ModemInfo modemInfo; // modem的配置参数

    protected CommSerialPort serialPort = null; // 通信串口对象,用于向串口发送数据和接收串口收到的数据

    protected boolean initing = false; // 正在初始化标志

    private StringBuffer buffer = new StringBuffer(); // 暂存从串口收到的数据

    private boolean isBusy = false; // 串口忙闲标志

    // 数传属性
    private long lastUseTime; // 数传链路的最后使用时间

    private boolean connectedTag = false; // 数传连接标志(断开/连接)

    private boolean hangingUpTag = false; // 数传正在挂断标志

    private boolean connectingTag = false; // 数传正在连接标志

    private String connectionPhone; // 数传连接的电话（直放站电话）

    protected ATCmdData atCmd = new ATCmdData(); // AT命令的对象，记录当前发送的at指令及回应情况

    protected AutoDisconnectDataTransfer ad = null; // 申请一个AtuoDisconnectDataTransfer对象,负责数传的自动挂断

    protected Vector<ReadRptDataListener> listener; // 收发数据的通知接口

    /**
     * 构造器
     * 
     * @param listener
     *            直放站数据侦听者
     * @param modemInfo
     *            modem配置信息
     */
    public ModemBase(Vector<ReadRptDataListener> listener, ModemInfo modemInfo) {
        this.listener = listener;
        this.modemInfo = modemInfo;
        // 初始化CommSerialPort对象
        serialPort = new CommSerialPort(modemInfo.getPortName(), modemInfo.getBaudRate());
        // 设置读数据侦听者
        serialPort.setListener(this);
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
    public ModemBase(Vector<ReadRptDataListener> listener, ModemInfo modemInfo, int flowControlIn, int flowControlOut, int databits,
            int stopbits, int parity) {
        this.listener = listener;
        this.modemInfo = modemInfo;
        // 初始化CommSerialPort对象
        serialPort = new CommSerialPort(modemInfo.getPortName(), modemInfo.getBaudRate(), flowControlIn, flowControlOut,
                databits, stopbits, parity);
        // 设置读数据侦听者
        serialPort.setListener(this);
    }

    /**
     * 实现ModemBaseInterface接口，初始化modem
     * @return 
     */
    public boolean init() {
        return init(false);
    }
    
    /**
     * 实现ModemBaseInterface接口，初始化modem
     */
    @Override
    public boolean init(boolean readMsg) {
        // 设置正在初始化标志为true
        initing = true;
        
        if(!setDefaultConfig()) { initing = false; return false; }
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 向串口发送ATE0指令（关闭回显方式，打开回显为ATE1
            atCmd = sendATData(ATE0 + C_Return, 1, 15);
            // 如果AT指令返回false则返回false，表示Modem初始化失败
            if (atCmd == null || !atCmd.getResponse() || !atCmd.getStatus()) {
                initing = false;
                return false;
            }
            initing = false;
            // Modem初始化成功返回true
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        return true;
    }
    
    /**
     * 实现ModemBaseInterface接口，打开串口
     */
    @Override
    public int open() {
        // 调用CommSerialPort对象的open方法打开串口
        return serialPort.openSerialPort();
    }

    /**
     * 实现ModemBaseInterface接口,关闭串口
     */
    @Override
    public void close() {
        // 调用CommSerialPort对象的close方法关闭串口
        if (serialPort !=null ) serialPort.close();
    }
    
    /**
     * 实现ModemBaseInterface接口，通过串口发送数据
     * @return 
     */
    @Override
    public boolean write(byte[] data) {
        // 调用CommSerialPort对象的write方法发送数据
        return serialPort.write(data);
        // 写modem日志
    }
    
    /**
     * 恢复出厂默认设置
     * 
     * @return
     */
    public boolean setDefaultConfig() {
        atCmd = sendATData(ATZ0 + C_Return, 1, 6);
        return !(atCmd == null || !atCmd.getResponse() || !atCmd.getStatus());
    }
    
    /**
     * 关机：控制模块的关机
     * @return 
     */
    public boolean shutdownModule() {
        return true;
    }
    
    /**
     * 复位：控制模块的热复位
     * @return
     */
    public boolean resetModule() {
        return true;
    }
    /**
     * 恢复用户默认默认设置
     * 
     * @return
     */
    public boolean resetUserDefaultConfig() {
        atCmd = sendATData(ATZ + C_Return, 1, 3);
        return !(atCmd == null || !atCmd.getResponse() || !atCmd.getStatus());
    }
    

    /**
     * 保存用户默认配置
     * 
     * @return
     */
    public boolean saveUserDefaultConfig() {
        atCmd = sendATData(AT_W + C_Return, 1, 3);
        return !(atCmd == null || !atCmd.getResponse() || !atCmd.getStatus());
    }
    
    /**
     * 实现SerialReadInterface接口，读取串口数据 接收CommSerialPort类的数据
     */
    @Override
    public void serialReadData(byte[] data) {
        try {
            buffer.append(new String(data, "ISO-8859-1"));
            if (connectedTag && !hangingUpTag) {
                if (!buffer.toString().endsWith("~")) {
                    return;
                }
            }
            if (buffer.equals("\r") || buffer.equals("\n") || buffer.equals("\r\n")) {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 写modem日志
        // 检查接收的数据是否完整结束，即检查其最后一个字符是否回车符，如果不是则返回
        if (!buffer.toString().endsWith(C_Return) && !buffer.toString().startsWith("\r\n>") && !connectedTag) {
            if (!buffer.toString().endsWith("OK") || buffer.toString().length() <= 2) {
                // log.debug("继续连接："+buffer.toString());
                return;
            }
        }
        // 针对西门子Modem,收到短信后，分包发送，判定结束后，再次收到OK，导致指令出错
        if (buffer.indexOf("UNREAD") > 0) {
            if (!buffer.toString().trim().endsWith("OK")) {
                return;
            }
        }
        
        String str = buffer.toString();
        buffer = new StringBuffer();
        // Modem正在初始化标志为true时，设置正在初始化标志为false，数据为“OK”则analyzeResponse(true),不为“OK”则analyzeResponse(false)，返回
        if (initing) { //表示串口正在初始化
            // initing = false;
            if (str.contains("OK")) { //如果显示为OK或者以输入>结尾，则表示获得响应信息
                analyzeResponse(true);
            } else {
                analyzeResponse(false);
            }
            return;
        }
        
        // 数传正在挂断标志为true时，设置数传正在挂断标志为false，数据为“OK”则analyzeResponse(true),不为“OK”则analyzeResponse(false)，返回
        if (hangingUpTag) {
            // hangingUpTag = false;
            if (str.contains("OK")) {
                analyzeResponse(true);
            } else {
                analyzeResponse(false);
            }
            return;
        }
        
        // 如果收到的数据为“ring”，设置数传正在连接标志为true
        if (str.indexOf("RING") > 0) {
            connectingTag = true;
            return;
        }
        
        // 如果正在连接状态为true,设置正在连接状态为false，检查响应结果做操作，返回
        if (connectingTag) {
            
            connectingTag = false;
            
            if (str.contains(C_OK)) {
                // if(recollection){
                // connectingTag = true;
                // recollection=false;
                // return;
                // }
                analyzeResponse(true);
            } else if (str.contains(C_ERROR)) {
                // 返回应答状态ERROR
                analyzeResponse(false);
            } else if (str.contains(C_NoDialtone)) {
                // 返回应答状态没有拨号音
                analyzeResponse(false);
            } else if (str.contains(C_Busy)) {
                // 返回应答状态忙
                analyzeResponse(false);
            } else if (str.contains(C_NoCarrier)) {
                // 返回应答状态没有载波
                analyzeResponse(false);
            } else if (str.contains(C_NoAnswer)) {
                analyzeResponse(false);
            } else if (str.contains(C_Connect)) {
                // 返回应答状态连接成功
                analyzeResponse(true);
                this.setConnected(true);
            } else if (str.contains("NMBR")) {
                connectingTag = true;
                this.setConnectionPhone(str.substring(33, str.length()));
                write(AT_ATA.getBytes()); //接听电话
            }
            return;
        }
        // 如果数传处于连接状态
        if (connectedTag) {
            // 若收到的数据包含"NO CARRIER"，则disconnect()断开连接，设置连接状态为false
            if (str.contains(C_NoCarrier)) {
                //断开数据传输连接
                disconnect();
                
                this.setConnected(false);
            } else {  // 若收到的数据包不含"NO CARRIER"，则调用receiveData方法通知数传数据侦听者
                receiveData(this.getConnectionPhone(), str);
            }
            return;
        }
        // 如果GPRS处于正在连接状态时，数据为“OK”则analyzeResponse(true),不为“OK”则analyzeResponse(false)，返回
        /*
        if (GPRSConnectingTag) { 
            if (str.indexOf(C_OK) > 0) {
                analyzeResponse(true); 
            } else { 
                analyzeResponse(false); 
            } 
        }
        */
        // 调用子类informReadData方法
        if (str.trim().equals("")){// 因为发送短信时，有时收到\r\n后，再收到>，这时在上面229行的代码将返回，程序就无法送短信内容了，所以把str加入buffer并返回
            buffer.append(str);
            return;
        }
        str = str.trim();
        informReadData(str);
    }

    /**
     * 子类实现的方法，用于基类收到信息后，serialReadData方法将信息交给子类处理
     * 
     * @param data
     *            信息
     */
    protected void informReadData(String data) {
        
    }

    /**
     * 实现ModemSMSInterface接口，清除短信
     */
    @Override
    public boolean clearShortMsg() {
        return false;
    }

    /**
     * 实现ModemSMSInterface接口，删除短信
     */
    @Override
    public boolean deleteShortMsg(int index) {
        return false;
    }

    /**
     * 读取Modem中的某条短信
     * 
     * @param index
     *            要读取短信在Modem中的存放位置
     * @return 返回是否成功
     */
    @Override
    public boolean readShortMsg(int index) {
        return false;

    }

    /**
     * 实现ModemSMSInterface接口
     */
    @Override
    public String getShortMsgPhone() {
        return null;
    }

    /**
     * 实现ModemSMSInterface接口
     */
    @Override
    public boolean isSIMExist() {
        return false;
    }

    /**
     * 实现ModemSMSInterface接口
     * 
     * 收到短信，通知侦听者
     * @param devicePhone
     */
    @Override
    public void receiveShortMsg(String devicePhone, String content, Date date) {
        // 写通信日志
        logger.info("接收短信："+devicePhone+","+content);
        
        Iterator<ReadRptDataListener> it = listener.iterator();
        
        while (it.hasNext()) {
            ReadRptDataListener l = it.next();
            if (l == null)
                return;
            l.readRepeaterData(content.getBytes(), devicePhone, CommModeConstants.SMS_MODE);
        }
    }

    /**
     * 实现ModemSMSInterface接口
     * @param destPhone
     * @param content
     * @return 
     */
    @Override
    public boolean sendEnglishSMS(String destPhone, String content) {
        return false;
    }

    /**
     * 实现ModemSMSInterface接口
     * @param destPhone
     * @param content
     * @return 
     */
    @Override
    public boolean sendChineseSMS(String destPhone, String content) {
        return false;
    }

    /**
     * ***********************数传通信所使用的专有方法**********开始**************************
     * **********
     */
    
    /**
     * 实现ModemDataTransferInterface接口
     * @param destPhone
     * @return 
     */
    @Override
    public boolean openConnection(String destPhone) {
        if (isConnected()) {
            if (getConnectionPhone().equals(destPhone)) {
                return true;
            }
            if (isAllowSwitch()) {
                // 得到AtuoDisconnectDataTransfer对象
                ad = this.getAutoDisconnectDataTransfer();
                // 设置断开标志setDisconnectTag(true)
                ad.setDisconnectTag();
            } else {
                return false;
            }
        }
        // 设置数传正在连接的状态为true
        this.connectingTag = true;
        
        // 向串口发送“ATD”指令，拨打号码，进入数据传输模式
        atCmd = sendATData(AT_ATD + destPhone + ";" + C_Return, 1, 35);
        // 设置数传正在连接的状态为false
        this.connectingTag = false;
        // 如果指令的响应正常，设置连接电话，设置数传为连接状态，返回true
        if (atCmd != null && atCmd.getStatus()) {
            this.setConnectionPhone(destPhone);
            this.setConnected(true);
            return true;
        }
        // 返回false
        return false;
    }
    
    /**
     * 实现ModemDataTransferInterface接口，断开数传连接
     * @return 
     */
    @Override
    public boolean disconnect() {
        // 判断数传连接是否已经断开，断开则返回true
        if (!this.isConnected()) {
            return true;
        }
        // 设置数传正在挂断标志为true
        hangingUpTag = true;
        // 向串口发送“+++”指令，由数据传输模式切换到命令模式
        atCmd = sendATData("+++", 1, 15);
        // 如果atCmd记录的status为false，返回false
        if (atCmd == null || !atCmd.getStatus()) {
            // hangingUpTag = false;
            // return false;
        }
        // 向串口发送“ATH”指令
        atCmd = sendATData(AT_ATH + C_Return, 1, 15);
        /**
         * // 如果atCmd记录的status为false，返回false if (atCmd == null ||
         * !atCmd.getStatus()) { hangingUpTag = false; return false; }
         */
        // 设置数传连接为断开状态
        this.setConnected(false);
        // 设置数传连接电话为空
        this.setConnectionPhone("");
        // 设置数传正在挂断标志为false
        hangingUpTag = false;
        // 如果atCmd记录的status为false，返回false
        // 返回true
        return !(atCmd == null || !atCmd.getStatus());
    }
    
    /**
     * 实现ModemDataTransferInterface接口
     * @return 
     */
    @Override
    public String getConnectionPhone() {
        // 返回数传连接的电话
        return this.connectionPhone;
    }
    
    /**
     * 实现ModemDataTransferInterface接口
     */
    @Override
    public void setConnectionPhone(String phone) {
        connectionPhone = phone;

    }

    /**
     * 实现ModemDataTransferInterface接口 判断数传连接还是断开
     * @return 
     */
    @Override
    public boolean isConnected() {
        // 返回数传连接标志
        return connectedTag;
    }

    /**
     * 实现ModemDataTransferInterface接口
     */
    @Override
    public void setConnected(boolean connected) {
        connectedTag = connected;
        // 如果connected为false，设置AtuoDisconnectDataTransfer对象setAutoDisconnectDataTransfer(null)
        if (!isConnected()) {
            setAutoDisconnectDataTransfer(null);
            return;
        }
        try {
            if (ad != null) {
                ad.setDisconnectTag();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ad = null;
        // 得到AtuoDisconnectDataTransfer对象
        ad = getAutoDisconnectDataTransfer();
        ad.start();

    }
    
    /**
     * 实现ModemDataTransferInterface接口
     * 
     * 收到数传数据，通知侦听者
     * @param devicePhone
     */
    @Override
    public void receiveData(String devicePhone, String data) {
        Iterator<ReadRptDataListener> it = listener.iterator();
        while (it.hasNext()) {
            ReadRptDataListener listener = it.next();
            try {
                listener.readRepeaterData(data.getBytes("ISO-8859-1"), devicePhone, CommModeConstants.DATA_MODE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        // 写通信日志
        String str = StringUtils.bytes2HexString(data.getBytes());
        try {
            str = StringUtils.bytes2HexString(data.getBytes("ISO-8859-1"));
        } catch (Exception e) {
            
        }
        logger.info("接收数据："+str);
    }

    /**
     * 实现ModemDataTransferInterface接口
     * @param destPhone
     * @param data
     * @return 
     */
    @Override
    public boolean sendData(String destPhone, byte[] data) {
        // 打开数传连接，如果失败再次尝试连接，3次之后仍然失败，返回false
        int count = 1;
        while (true) {
            if (openConnection(destPhone)) {
                break;
            }
            if (count >= 3) {
                return false;
            }
            count++;
            try {
                Thread.sleep(1000);
                Thread.yield();
            } catch (Exception e) {

            }
        }
        // 调用write发送数据
        boolean result = write(data);
        if (result) {
            // 重新设置数传链路的最后使用时间
            resetLastUseTime();
        }
        // 返回true
        return result;
    }

    /**
     * 重新设置数传链路的最后使用时间
     * 
     */
    public void resetLastUseTime() {
        lastUseTime = System.currentTimeMillis();
    }

    /**
     * 判断是否允许挂断当前数传并进行其他数传连接
     * 
     * @return true表示允许，false表示不允许
     */
    public boolean isAllowSwitch() {
        // 判断数传链路的空闲时间lastUseTime是否超过了允许切换的时间
        return System.currentTimeMillis() - lastUseTime >= SWITHIDLETIME;
    }

    /**
     * ***********************数传通信所使用的专有方法**********结束**************************
     * **********
     */
    /**
     * 发送AT命令
     * 
     * @param cmd 
     *         发送的命令
     * @param cmdType 
     *         at指令类型
     * @param timeOut  
     *         命令超时时间
     * @return
     */
    protected synchronized ATCmdData sendATData(String cmd, int cmdType, int timeOut) {
        // 检查串口忙闲标志isBusy，忙则等待，等待超过检测的指定次数返回
        int count = 0;
        while (isBusy) {
            
            try {
                Thread.sleep(100);
                Thread.yield();
            } catch (Exception e) {

            }
            count++;
            if (count >= 10 * timeOut) {
                return null;
            }
        }
        // 设置串口忙闲标志isBusy为true
        isBusy = true;
        // 得到ATCmdData对象，根据参数设置其属性
        if (atCmd == null) {
            atCmd = new ATCmdData();
        }
        atCmd.setCmdType(cmdType);
        atCmd.setResponse(false);
        atCmd.setStatus(false);
        // 调用write方法将发送cmd
        try {
            // System.out.println("cmd="+cmd);
            write(cmd.getBytes());
        } catch (Exception e) {

        }
        // 循环判断是否收到应答数据，超过检测的指定次数结束循环
        count = 0;
        while (atCmd != null && !atCmd.getResponse()) {
            try {
                Thread.sleep(100);
                Thread.yield();
            } catch (Exception e) {

            }
            count++;
            if (count >= 10 * timeOut) {
                isBusy = false;
                logger.debug("等待响应超时" + cmd);
                return null;
            }
        }
        // 设置串口忙闲标志isBusy为false
        isBusy = false;
        // 返回ModemData对象
        logger.debug("返回atCmd" + cmd);
        return atCmd;
    }

    /**
     * 发送AT命令(Unicode编码)
     * @param data
     * @param cmdType
     * @param timeOut
     * @return 
     */
    protected synchronized ATCmdData sendATData(byte[] data, int cmdType, int timeOut) {
        // 检查串口忙闲标志isBusy，忙则等待，等待超过检测的指定次数返回
        String cmdStr = "";
        try {

            cmdStr = new String(data, "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        int count = 0;
        while (isBusy) {
            try {
                Thread.sleep(100);
                Thread.yield();
            } catch (Exception e) {

            }
            count++;
            if (count >= 10 * timeOut) {
                return null;
            }
        }
        // 设置串口忙闲标志isBusy为true
        isBusy = true;
        // 得到ATCmdData对象，根据参数设置其属性
        if (atCmd == null) {
            atCmd = new ATCmdData();
        }
        atCmd.setCmdType(cmdType);
        atCmd.setResponse(false);
        atCmd.setStatus(false);
        // 调用write方法将发送cmd
        try {
            // System.out.println("cmd="+cmd);
            write(data);
        } catch (Exception e) {

        }
        // 循环判断是否收到应答数据，超过检测的指定次数结束循环
        count = 0;
        while (atCmd != null && !atCmd.getResponse()) {
            try {
                Thread.sleep(100);
                Thread.yield();
            } catch (Exception e) {

            }
            count++;
            if (count >= 10 * timeOut) {
                isBusy = false;
                logger.debug("等待响应超时" + cmdStr);
                return null;
            }
        }
        // 设置串口忙闲标志isBusy为false
        isBusy = false;
        // 返回ModemData对象
        logger.debug("返回atCmd" + cmdStr);
        return atCmd;
    }

    /**
     * 分析响应
     * 
     * @param status
     *            AT指令返回是否正常
     */
    protected void analyzeResponse(boolean status) {
        try {
            atCmd.setData("");
            // 设置AT指令已经返回
            atCmd.setResponse(true);
            // 设置AT指令返回状态,true表示正常
            atCmd.setStatus(status);
        } catch (Exception e) {
        }
    }

    /**
     * 分析响应
     * 
     * @param status
     *            AT指令返回是否正常
     * @param data
     */
    protected void analyzeResponse(boolean status, String data) {
        try {
            atCmd.setData(data);
            // 设置AT指令已经返回
            atCmd.setResponse(true);
            // 设置AT指令返回状态,true表示正常
            atCmd.setStatus(status);
        } catch (Exception e) {
        }
    }

    /**
     * 获取AtuoDisconnectDataTransfer对象
     * 
     * @return AtuoDisconnectDataTransfer对象
     */
    AutoDisconnectDataTransfer getAutoDisconnectDataTransfer() {
        if (ad == null) {
            ad = new AutoDisconnectDataTransfer();
        }
        return ad;
    }

    /**
     * 设置AutoDisconnectDataTransfer对象
     * 
     * @param ad
     *            AutoDisconnectDataTransfer对象
     */
    void setAutoDisconnectDataTransfer(AutoDisconnectDataTransfer ad) {
        try {
            Thread.yield();
        } catch (Exception e) {
        }
        this.ad = ad;
    }
    
    /**
     * 该内部类是完成数传自动断开的功能
     * 
     * @author liuqing
     * 
     */
    class AutoDisconnectDataTransfer extends Thread {
        private boolean disconnectTag = false; // 数传断开标志

        @Override
        public void run() {
            // 设置数传链路的最后使用时间
            lastUseTime = System.currentTimeMillis();
            // 循环判断数传断开标志
            // 如果数传链路未使用时间达到指定时间，设置数传断开标志为true
            // 结束循环
            while (!disconnectTag) {
                try {
                    Thread.sleep(100);
                    Thread.yield();
                } catch (Exception e) {

                }
                if (System.currentTimeMillis() - lastUseTime >= MAXIDLETIME) {
                    disconnectTag = true;
                }
            }
            // 如果数传链路处于连接状态，断开连接
            if (isConnected()) {
                disconnect();
            }
        }

        /**
         * 设置数传连接标志
         * 
         */
        public void setDisconnectTag() {
            // 设置数传断开标志
            disconnectTag = true;
            // 等待线程死亡
            try {
                join();
            } catch (Exception e) {

            }
        }
    }
    
    protected synchronized ATCmdData sendDailData(String cmd, int timeOut) {
        // 检查串口忙闲标志isBusy，忙则等待，等待超过检测的指定次数返回
        int count = 0;
        while (isBusy) {
            try {
                Thread.sleep(100);
                Thread.yield();
            } catch (Exception e) {

            }
            count++;
            if (count >= 10 * timeOut) {
                return null;
            }
        }
        // 设置串口忙闲标志isBusy为true
        isBusy = true;
        // 得到ATCmdData对象，根据参数设置其属性
        if (atCmd == null) {
            atCmd = new ATCmdData();
        }
        atCmd.setResponse(false);
        atCmd.setStatus(false);
        // 调用write方法将发送cmd
        try {
            // System.out.println("cmd="+cmd);
            write(cmd.getBytes("gb2312"));
        } catch (Exception e) {

        }
        // 循环判断是否收到应答数据，超过检测的指定次数结束循环
        count = 0;
        while (atCmd != null && !atCmd.getResponse()) {
            try {
                Thread.sleep(100);
                Thread.yield();
            } catch (Exception e) {

            }
            count++;
            if (count >= 10 * timeOut) {
                isBusy = false;
                logger.debug("等待响应超时" + cmd);
                return null;
            }
        }
        // 设置串口忙闲标志isBusy为false
        isBusy = false;
        // 返回ModemData对象
        logger.debug("返回atCmd" + cmd);
        return atCmd;
    }
    
    public boolean isOpened() {
        return serialPort.isOpened();
    }
}
