package com.irongteng.comm;

import com.irongteng.comm.listener.ReceiveDataListener;
import com.irongteng.comm.listener.SendDataListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CommServer {
    
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    public static final String UDP_SERVER_NAME = "udpService"; //UDP
    
    public static final String UDP_FDD_SERVER_NAME = "udpFddService"; //UDP
    
    public static final String UDP_LTE_SERVER_NAME = "udpLTEService"; //UDP
    
    public static final String TCP_SERVER_NAME = "tcpService"; //TCP
    
    public static final String FTP_SERVER_NAME = "ftpService"; //FTP
    
    public static final String SERIAL_SERVER_NAME = "serialService"; //串口
    
    public static final String MODEM_SERVER_NAME = "modemService"; //串口
    
    public abstract void start();
    
    public abstract void stop();
    
    public abstract void restart();
    
    public abstract boolean isActive();
    
    private SendDataListener sendDataListener;
    
    private ReceiveDataListener receiveDataListener;
    
    public SendDataListener getSendDataListener() {
        return sendDataListener;
    }

    public void setSendDataListener(SendDataListener sendDataListener) {
        this.sendDataListener = sendDataListener;
    }

    public ReceiveDataListener getReceiveDataListener() {
        return receiveDataListener;
    }

    public void setReceiveDataListener(ReceiveDataListener receiveDataListener) {
        this.receiveDataListener = receiveDataListener;
    }
}
