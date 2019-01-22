package com.irongteng.comm.client;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.irongteng.comm.CommClient;

public class TcpCommClient extends CommClient {
    
    private NioSocketConnector connector;
    
    private IoSession session;
    
    /**
     * 启动状态，true开始， false为停止
    * @return 
     */
        @Override
    public boolean isActive() {
        return connector.isActive() && connector!=null;
    }
    
    /**
     * 创建连接
     * 
     * @param socketAddress
     */
        @Override
    public void connect(SocketAddress socketAddress) {
        
        connector = new NioSocketConnector();
        connector.setHandler(new TcpCommHandler());
        connector.getFilterChain().addLast("logger", new LoggingFilter());
        //connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8")))); // 设置编码过滤器
        
        SocketSessionConfig dcfg = connector.getSessionConfig();
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