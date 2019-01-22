package com.irongteng.comm.modem;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.TooManyListenersException;

import org.apache.log4j.Logger;

import dwz.common.util.StringUtils;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

/**
 * 通信接口类，负责串口的通信
 * 
 * @author lvlei
 * 
 */
public class CommSerialPort implements SerialPortEventListener {
    
    private static Logger logger = Logger.getLogger(CommSerialPort.class);
    
    static private byte APA_START_END_TAG = 0x7D;

    private String portName; // 串口名

    private int baudRate; // 波特率

    private int flowControlIn; // 输入流控

    private int flowControlOut; // 输出流控

    private int databits; // 数据位

    private int stopbits; // 停止位

    private int parity; // 奇偶校验

    private SerialReadListener listener = null; // 串口数据的侦听者

    private SerialPort serialPort = null;

    private InputStream in = null; // 串口输入流

    private OutputStream out = null; // 串口输出流
    
    private boolean opened = false; // 标志串口是否打开
    

    /**
     * 构造器
     * 
     * @param portName
     *            串口名
     * @param baudRate
     *            波特率
     */
    public CommSerialPort(String portName, int baudRate) {
        // 对属性进行赋值
        this.portName = portName;
        this.baudRate = baudRate;
        this.flowControlIn = SerialPort.FLOWCONTROL_NONE;
        this.flowControlOut = SerialPort.FLOWCONTROL_NONE;
        this.databits = SerialPort.DATABITS_8;
        this.stopbits = SerialPort.STOPBITS_1;
        this.parity = SerialPort.PARITY_NONE;
    }

    /**
     * 构造器
     * 
     * @param portName
     *            串口名
     * @param baudRate
     *            波特率
     * @param flowControlIn
     *            输入流控
     * @param flowControlOut
     *            输出流控
     * @param databits
     *            数据位
     * @param stopbits
     *            停止位
     * @param parity
     *            奇偶检验
     */
    public CommSerialPort(String portName, int baudRate, int flowControlIn, int flowControlOut, int databits,
            int stopbits, int parity) {
        // 对属性进行赋值
        this.portName = portName;
        this.baudRate = baudRate;
        this.flowControlIn = flowControlIn;
        this.flowControlOut = flowControlOut;
        this.databits = databits;
        this.stopbits = stopbits;
        this.parity = parity;
    }

    /**
     * 功能说明:打开串口
     * 
     * @return 返回打开串口的结果，0表示成功 return int author liuqing 2007-4-5 16:10:22
     */
    public int openSerialPort() {
        if (opened) {
            return SerialStatus.PORT_OPEN_OK; // 返回成功打开串口
        }
        @SuppressWarnings("unchecked")
        Enumeration<CommPortIdentifier> enu= CommPortIdentifier.getPortIdentifiers();
        boolean  isFindPort = false;
        while(enu.hasMoreElements()) {
            if(enu.nextElement().getName().equalsIgnoreCase(portName)) {
                isFindPort = true;
            }
        }
        if (!isFindPort) {
            return SerialStatus.PORT_NO_FOUND;
        }
        
        // 打开串口并扑捉异常
        try {
            CommPortIdentifier portID = CommPortIdentifier.getPortIdentifier(portName);
            serialPort = (SerialPort) portID.open("winscad", 30000);
            serialPort.setSerialPortParams(baudRate, databits, stopbits, parity);
            serialPort.setFlowControlMode(flowControlIn | flowControlOut);
            in = serialPort.getInputStream();
            out = serialPort.getOutputStream();
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
            opened = true;
            return SerialStatus.PORT_OPEN_OK;
        } catch (NoSuchPortException e) {
            return SerialStatus.PORT_NO_FOUND;
        } catch (PortInUseException e) {
            return SerialStatus.PORT_USED;
        } catch (UnsupportedCommOperationException e) {
            serialPort.close();
            return SerialStatus.PORT_NOT_SUPPORTED;
        } catch (IOException e) {
            serialPort.close();
            return SerialStatus.PORT_IO_ERROR;
        } catch (TooManyListenersException e) {
            serialPort.close();
            return SerialStatus.TOO_MANY_LISTENER_ERR;
        }
    }

    /**
     * 功能说明:关闭串口
     * 
     * return void author liuqing 2007-4-5 16:11:39
     */
    public void close() {
        
        try {
            if (!opened) {
                return; // 串口已经关闭则返回
            }
            // 关闭串口的输入输出流
            if (in!=null) in.close();
            if (out!=null) out.close();
            // 关闭串口
            if(serialPort != null) serialPort.close();
        } catch (IOException e) {

        } finally {
            in = null;
            out = null;
            serialPort = null;
            opened = false;
        }
    }
    
    /**
     * 实现SerialPortEventListener接口的方法
     */
    @Override
    public void serialEvent(SerialPortEvent event) {
        
        // 如果串口事件是表示串口数据到达，则接收数据
        switch (event.getEventType()) {
        case SerialPortEvent.DATA_AVAILABLE:
            byte[] buffer = new byte[1024];
            try {
                int count = in.read(buffer);
                if (count > 0) {
                    byte[] data = new byte[count];
                    for (int i = 0; i < count; i++) {
                        data[i] = buffer[i];
                    }
                    
                    String log = "";
                    if (data[0] == APA_START_END_TAG) {
                        log =   StringUtils.bytes2HexString(data);
                    } else {
                        log =   new String(data);
                    }
                    
                    logger.debug("接收到串口[" + portName + "]数据:" + log.trim());
                    // 调用serialReadData方法通知Modem类收到数据
                    serialReadData(data);
                }
            } catch (IOException ee) {
                ee.printStackTrace();
                
                boolean  isFindPort = SerialUtils.existSerialPort(portName);
                
                if (!isFindPort) {
                    this.close();
                }
                
            }
        }
    }
    
    /**
     * 向串口写数据
     * 
     * @param data
     *            需要写入串口的数据
     */
    public boolean write(byte[] data) {
        if (!opened) {
            return false;
        }
        // 通过串口的输出流将数据发送到直放站
        try {
            out.write(data);
            
            String log = "";
            if (data[0] == APA_START_END_TAG) {
                log =   StringUtils.bytes2HexString(data);
            } else {
                log = new String(data, "GBK");
            }
            
            logger.info("发送数据到串口[" + portName + "]:" + log.trim());
            
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 通知串口数据侦听者
     * 
     * @param data
     *            串口数据
     */
    public void serialReadData(byte[] data) {
        // 通知串口数据侦听者SerialReadInterface
        if (listener != null) {
            listener.serialReadData(data);
        }
    }

    public SerialReadListener getListener() {
        return listener;
    }

    public void setListener(SerialReadListener listener) {
        this.listener = listener;
    }

    public boolean isOpened() {
        return opened;
    }
    
    public static String gb2unicode(String gbString) {
        String result = "";
        // GB2Uni nn = new GB2Uni();
        char[] c;
        int value;
        if (gbString == null)
            return null;
        // if (gbString.getBytes().length == gbString.length())
        // return gbString;
        String temp = null;
        c = new char[gbString.length()];
        StringBuffer sb = new StringBuffer(gbString);
        sb.getChars(0, sb.length(), c, 0);
        for (int i = 0; i < c.length; i++) {
            value = c[i];
            temp = Integer.toHexString(value);
            result += fill(temp, 4);
            // -------------------------------------------------------------------------
        }
        return result.toUpperCase();
    }
    
    public static String fill(String temp, int totalbit) {
        String t = temp;
        while (t.length() < totalbit) {
            t = "0" + t;
        }
        return t;
    }
    
    public static void main(String agrs[]) {
        try {
            String ch = "leimin，收到我发的短信没有？";
            
            System.out.println(gb2unicode(ch));
            System.out.println(StringUtils.bytes2HexString(ch.getBytes("unicode")));
            System.out.println(StringUtils.bytes2HexString(ch.getBytes()));
            
            CommSerialPort comm = new CommSerialPort("COM3", 115200);
            comm.openSerialPort();
            comm.write("ATE0\r\n".getBytes());
            Thread.sleep(3000);
            comm.write("AT+CMGF=0\r\n".getBytes());
            Thread.sleep(3000);
            comm.write("AT+CNMI=2,3\r\n".getBytes());
            Thread.sleep(3000);
            comm.write("At+csmp=17,167,0,240\r\n".getBytes());
            Thread.sleep(3000);
            // comm.write("at+cmgs=\"13824396986\"\r\n".getBytes());

            PduPack pdu = new PduPack();
            pdu.setAddr("13480760840");// 13316506835//13510419796
            pdu.setMsgCoding(8);
            pdu.setMsgContent("leimin，收到我发的短信没有？");
            pdu.setSmsc("+8613800755500");
            String result = pdu.getCodedResult();
            System.out.println(result);
            int len = (result.length() - 18) / 2;
            comm.write(("at+cmgs=" + len + "\r\n").getBytes());
            Thread.sleep(3000);
            comm.write((result + (char) 0x1A).getBytes());

            // String toPhone = "13510419796";
            // Sms sms1 = new Sms();
            //// if (args.length>=1)
            //// {
            //// toPhone = args[0];
            //// }
            // boolean sendsucc = sms1.sendmsg("你好",toPhone);
            // if(sendsucc){
            // System.out.println("send successful..............");
            // }else{
            // System.out.println("send failure........");
            // }

            byte[] bytes = new byte[40];
            bytes[0] = APA_START_END_TAG;
            bytes[1] = 0x1;
            bytes[2] = 0x1;
            bytes[3] = 0x55;
            bytes[4] = 0x0;
            bytes[5] = 0x0;
            bytes[6] = 0x0;
            bytes[7] = 0x16;
            bytes[8] = 0x3;
            bytes[9] = 0x0;
            bytes[10] = 0x54;
            bytes[11] = 0x1;
            bytes[12] = 0x2;
            bytes[13] = (byte) 0xFF;
            bytes[14] = 0x23;
            bytes[15] = 0x6;
            bytes[16] = 0x0;

            bytes[17] = 0x0;
            bytes[18] = 0x0;
            bytes[19] = 0x0;
            bytes[20] = 0x0;

            bytes[21] = 0x0;
            bytes[22] = 0x0;
            bytes[23] = 0x0;
            bytes[24] = 0x0;

            bytes[25] = 0x0;
            bytes[26] = 0x0;
            bytes[27] = 0x0;
            bytes[28] = 0x0;

            bytes[29] = 0x0;
            bytes[30] = 0x0;
            bytes[31] = 0x0;
            bytes[32] = 0x0;

            bytes[33] = 0x0;
            bytes[34] = 0x0;
            bytes[35] = 0x0;
            bytes[36] = 0x0;

            bytes[37] = 0x58;
            bytes[38] = 0x67;
            bytes[39] = APA_START_END_TAG;
            // comm.write("ATE0\r\n".getBytes());
            // comm.write("at+cmgs=\"13824396986\"\r\n".getBytes());
            byte[] bs = new byte[4];
            bs[0] = 0x2D;
            bs[1] = 0x4E;
            bs[2] = 0;
            bs[3] = 0;
            Thread.sleep(5000);
            byte[] bsy = "中色".getBytes("GB18030");
            byte[] bsy2 = new byte[bsy.length + 1];
            for (int i = 0; i < bsy.length; i++) {
                bsy2[i] = bsy[i];
            }
            bsy2[bsy.length - 1] = 0x1A;
            String ttt = "耗能" + (char) 0x1A;
            byte[] bbb = ttt.getBytes("GBK");
            char[] c = new char[bbb.length];
            for (int i = 0; i < bbb.length; i++) {
                c[i] = (char) bbb[i];
            }
            String UTFstr = "";
            try {
                UTFstr = new String(ttt.toString().getBytes("ISO-8859-1"), "GB18030");
                System.out.println(UTFstr);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            // comm.writer.write(ttt);
            // comm.write(UTFstr.getBytes());
            // byte[]
            // bs="7E010137000000100300800102FF17060000000000000000000000000000000000000000003A437E".getBytes();
            // println(bs);
            Thread.sleep(3 * 60 * 1000);
            // comm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // private static void println(byte[] bs) {
    // if (bs == null)
    // System.out.println("null");
    // else {
    // for (int i = 0; i < bs.length; i++) {
    // String str = Integer.toHexString(bs[i]);
    // if (str.length() == 1) {
    // str = "0" + str;
    // } else if (str.length() > 2) {
    // str = str.substring(str.length() - 2);
    // }
    // System.out.print(str.toUpperCase() + " ");
    // }
    // System.out.println();
    // }
    // }
    // public static String BytesToHexStr(byte[] data) {
    // StringBuffer sb = new StringBuffer();
    // for (int i = 0; i < data.length; i++) {
    // String str = Integer.toHexString(data[i] & 0xFF);
    // if (str.length() <= 1)
    // str = "0" + str;
    // sb.append(str + " ");
    // }
    // return sb.toString();
    // }
}