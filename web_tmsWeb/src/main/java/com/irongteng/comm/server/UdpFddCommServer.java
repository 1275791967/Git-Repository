package com.irongteng.comm.server;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.DatagramSessionConfig;
import org.apache.mina.transport.socket.nio.NioDatagramAcceptor;
import org.apache.mina.transport.socket.nio.NioDatagramConnector;

import com.irongteng.comm.CommModeStatus;
import com.irongteng.comm.CommServer;
import org.apache.mina.filter.executor.ExecutorFilter;

public class UdpFddCommServer extends CommServer {
    
    private NioDatagramConnector connector;
    
    private NioDatagramAcceptor acceptor;
    
    private IoSession session;
    
    private final int port;
    
    public UdpFddCommServer(int port)  {
        this.port = port;
    }
    
    /**
     * 启动服务
     * 
     */
    @Override
    public void start() {
        
        acceptor = new NioDatagramAcceptor();
        
        try {
            //acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8")))); // 设置编码过滤器
            //acceptor.getFilterChain().addLast("codec",new ProtocolCodecFilter(new CommonProtocolCodecFactory(Charset.forName("UTF-8"))));
            //acceptor.getFilterChain().addLast("logger", new LoggingFilter());
            acceptor.getFilterChain().addLast("exceutor", new ExecutorFilter());  
            
            CommHandler commHandler = new CommHandler(CommModeStatus.UDP_FDD);
            commHandler.setReceiveDataListener(this.getReceiveDataListener());
            commHandler.setSendDataListener(this.getSendDataListener());
            
            acceptor.setHandler(commHandler);
            
            //用于配置资源管理的方法
            DatagramSessionConfig dcfg = acceptor.getSessionConfig();
            dcfg.setReuseAddress(true);
            
            acceptor.bind(new InetSocketAddress(port));
            
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
    
    /**
     * 停止服务
     */
    @Override
    public void stop() {
        if (acceptor!=null) {
            acceptor.unbind();
        }
    }
    
    /**
     * 重启服务
     */
    @Override
    public void restart() {
        if (acceptor!=null) {
            acceptor.unbind();
        }
        
        start();
    }
    
    /**
     * 启动状态
     * @return 
     *     true开始， false为停止
     */
    @Override
    public boolean isActive() {
        return acceptor!=null && acceptor.isActive();
    }
    /**
     * 创建连接
     * 
     * @param address
     * @param port
     */
    public void connect(String address, int port) {
        
        connector = new NioDatagramConnector();

        CommHandler commHandler = new CommHandler(CommModeStatus.UDP_FDD);
        commHandler.setReceiveDataListener(this.getReceiveDataListener());
        commHandler.setSendDataListener(this.getSendDataListener());
        
        connector.setHandler(commHandler);
        
        //connector.getFilterChain().addLast("logger", new LoggingFilter());
        //connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8")))); // 设置编码过滤器
        //connector.getFilterChain().addLast("codec",new ProtocolCodecFilter(new CommonProtocolCodecFactory(Charset.forName("UTF-8"))));
        
        DatagramSessionConfig dcfg = connector.getSessionConfig();
        dcfg.setReadBufferSize(1024);
        //dcfg.setReuseAddress(true);
        ConnectFuture connFuture = connector.connect(new InetSocketAddress(address, port));
        connFuture.awaitUninterruptibly();
        session = connFuture.getSession();
    }
    
    /**
     * 发送报文
     * 
     * @param data
     */
    public void send(byte[] data) {
        session.write(IoBuffer.wrap(data));// 发送消息 这里是发送字节数组的重点
        //session.getCloseFuture().awaitUninterruptibly();// 等待连接断开
    }
    
    /**
     * 发送报文
     * 
     * @param session
     * @param data
     */
    public void send(IoSession session, byte[] data) {
        session.write(IoBuffer.wrap(data));// 发送消息 这里是发送字节数组的重点
        //session.getCloseFuture().awaitUninterruptibly();// 等待连接断开
    }
    /**
     * 断开连接
     */
    public void disconnect() {
        connector.dispose();
    }

}