package com.irongteng.comm.client;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.DatagramSessionConfig;
import org.apache.mina.transport.socket.nio.NioDatagramConnector;

import com.irongteng.comm.CommClient;

public class UdpCommClient extends CommClient {
    
    private NioDatagramConnector connector;
    
    private IoSession session;
    /**
     * 服务启动状态
     * @return true开始， false为停止
     */
    @Override
    public boolean isActive() {
        return connector!=null && connector.isActive();
    }
    
    /**
     * 创建连接
     * 
     * @param socketAddress 地址信息
     */
    @Override
    public void connect(SocketAddress socketAddress) {
        
        connector = new NioDatagramConnector();
        connector.setHandler(new UdpCommHandler());
        connector.getFilterChain().addLast("logger", new LoggingFilter());
        //connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8")))); // 设置编码过滤器
        
        DatagramSessionConfig dcfg = connector.getSessionConfig();
        dcfg.setReuseAddress(true);
        ConnectFuture connFuture = connector.connect(socketAddress);
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
     */
    @Override
    public void send(byte[] data) {
        session.write(IoBuffer.wrap(data));// 发送消息 这里是发送字节数组的重点
        //session.getCloseFuture().awaitUninterruptibly();// 等待连接断开
    }
    
}