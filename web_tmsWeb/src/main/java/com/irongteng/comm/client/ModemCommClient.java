package com.irongteng.comm.client;

import java.net.SocketAddress;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.session.IoSessionConfig;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.serial.SerialAddress;
import org.apache.mina.transport.serial.SerialConnector;

import com.irongteng.comm.CommClient;

public class ModemCommClient extends CommClient {
    
    private SerialConnector connector;
    
    private IoSession session;
    
    /**
     * 启动状态，true开始， false为停止
         * @return 
         *  判断连接是否是有效的。
     */
    @Override
    public boolean isActive() {
            return connector.isActive() && connector!=null;
    }
    
    /**
     * 创建连接
     * 
     * @param serialAddress
     */
    @Override
    public void connect(SocketAddress serialAddress) {
        
            connector = new SerialConnector();
            connector.setHandler(new ModemCommHandler());
            connector.getFilterChain().addLast("logger", new LoggingFilter());
            //connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8")))); // 设置编码过滤器
        
            IoSessionConfig dcfg = connector.getSessionConfig();
            dcfg.setReadBufferSize(2048);
            //SerialAddress serialAddress = new SerialAddress ("COM3", 115200, DataBits.DATABITS_8, StopBits.BITS_1 , Parity.NONE, FlowControl.NONE);
        
            ConnectFuture connFuture = connector.connect(serialAddress);
            connFuture.awaitUninterruptibly();
            
            session = connFuture.getSession();
    }
    
    /**
     * 断开连接
     */
    @Override
    public void disconnect() {
        connector.dispose();
    }
    /**
     * 发送报文
     * 
     * @param data
         *  要发送的字节流
     */
    @Override
    public void send(byte[] data) {
            session.write(IoBuffer.wrap(data));// 发送消息 这里是发送字节数组的重点
    }
}