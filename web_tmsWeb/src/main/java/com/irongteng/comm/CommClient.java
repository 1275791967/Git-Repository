package com.irongteng.comm;

import java.net.SocketAddress;

public abstract class CommClient {
    
    public static final String UDP_CLIENT_NAME = "udpClient"; //UDP
    
    public static final String UDP_FDD_CLIENT_NAME = "udpFddClient"; //UDP
    
    public static final String UDP_LTE_CLIENT_NAME = "udpLTEClient"; //UDP
    
    public static final String TCP_CLIENT_NAME = "tcpClient"; //TCP
    
    public static final String FTP_CLIENT_NAME = "ftpClient"; //FTP
    
    public static final String SERIAL_CLIENT_NAME = "serialClient"; //串口
    
    public static final String MODEM_CLIENT_NAME = "modemClient"; //串口
    
    public abstract void connect(SocketAddress socketAddrss);
    
    public abstract void disconnect();
    
    public abstract void send(byte[] data);

    public abstract boolean isActive();
}
