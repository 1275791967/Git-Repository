package com.irongteng.comm.server;

import com.irongteng.comm.coder.CommonProtocolCodecFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.Executors;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irongteng.comm.CommModeStatus;
import com.irongteng.comm.CommServer;
import org.apache.mina.filter.executor.ExecutorFilter;

public class TcpCommServer extends CommServer {
    
    private SocketConnector connector;
    
    private NioSocketAcceptor acceptor;
    
    private IoSession session;
    
    private final int port;
    
    private final CommModeStatus commModeStatus;
    
    public TcpCommServer(int port, CommModeStatus commModeStatus)  {
        this.port = port;
        this.commModeStatus = commModeStatus;
    }
    
    /**
     * 启动服务
     */
    @Override
    public void start() {
        
        acceptor = new NioSocketAcceptor();
        
        try {
            //acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8")))); // 设置编码过滤器
            acceptor.getFilterChain().addLast("codec",new ProtocolCodecFilter(new CommonProtocolCodecFactory(Charset.forName("UTF-8"))));
            //acceptor.getFilterChain().addLast("logger", new LoggingFilter());
            //acceptor.getFilterChain().addLast("threadPool", new MdcInjectionFilter());//设置线程池，以支持多线程
            acceptor.getFilterChain().addLast("exceutor", new ExecutorFilter());  
            //10分钟没有读取操作，进入空闲状态(时间可配)
            acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 60*10);
            
            CommHandler commHandler = new CommHandler(commModeStatus);
            commHandler.setReceiveDataListener(this.getReceiveDataListener());
            commHandler.setSendDataListener(this.getSendDataListener());
            
            acceptor.setHandler(commHandler);
            //用于配置资源管理的方法
            SocketSessionConfig dcfg = acceptor.getSessionConfig();
            dcfg.setReuseAddress(true);
            dcfg.setReadBufferSize(2048);
            
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
     *      boolean 返回状态，查看是否处于可用状态
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
        
        connector = new NioSocketConnector();
        
        CommHandler commHandler = new CommHandler(commModeStatus);
        commHandler.setReceiveDataListener(this.getReceiveDataListener());
        commHandler.setSendDataListener(this.getSendDataListener());
        
        connector.setHandler(commHandler);
        
        connector.getFilterChain().addLast("logger", new LoggingFilter());
        //connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8")))); // 设置编码过滤器
        connector.getFilterChain().addLast("codec",new ProtocolCodecFilter(new CommonProtocolCodecFactory(Charset.forName("UTF-8"))));
        connector.getFilterChain().addLast("exceutor", new ExecutorFilter(Executors.newFixedThreadPool(1000)));  
        
        SocketSessionConfig dcfg = connector.getSessionConfig();
        dcfg.setReuseAddress(true);
        dcfg.setReadBufferSize(2048);
        
        ConnectFuture connFuture = connector.connect(new InetSocketAddress(address, port));
        connFuture.awaitUninterruptibly();
        
        while(!connFuture.isConnected()) {
            session = connFuture.getSession();
        }
        
    }
    
    /**
     * 断开连接
     */
    public void disconnect() {
        connector.dispose();
    }
    
    /**
     * 发送报文
     * 
     * @param data
     *      byte[] 传递的数据
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
    
    /** 30秒后超时 */
    private static final int IDELTIMEOUT = 30;
    /** 15秒发送一次心跳包 */
    private static final int HEARTBEATRATE = 15;
    /** 心跳包内容 */
    private static final String HEARTBEATREQUEST = "HEARTBEATREQUEST";
    private static final String HEARTBEATRESPONSE = "HEARTBEATRESPONSE";
    
    /***
     * @ClassName: KeepAliveMessageFactoryImpl
     * @Description: 内部类，实现心跳工厂
     * @author Minsc Wang ys2b7_hotmail_com
     * @date 2011-3-7 下午04:09:02
     * 
     */
    private static class KeepAliveMessageFactoryImpl implements
            KeepAliveMessageFactory {
        
        private final Logger logger = LoggerFactory.getLogger(KeepAliveMessageFactoryImpl.class);
        /*
         * (non-Javadoc)
         * 
         * @see
         * org.apache.mina.filter.keepalive.KeepAliveMessageFactory#getRequest
         * (org.apache.mina.core.session.IoSession)
         */
        @Override
        public Object getRequest(IoSession session) {
            return null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.apache.mina.filter.keepalive.KeepAliveMessageFactory#getResponse
         * (org.apache.mina.core.session.IoSession, java.lang.Object)
         */
        @Override
        public Object getResponse(IoSession session, Object request) {
            logger.info("返回预设语句" + HEARTBEATRESPONSE);
            /** 返回预设语句 */
            return HEARTBEATRESPONSE;
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.apache.mina.filter.keepalive.KeepAliveMessageFactory#isRequest
         * (org.apache.mina.core.session.IoSession, java.lang.Object)
         */
        @Override
        public boolean isRequest(IoSession session, Object message) {
            logger.info("是否是心跳包: " + message);
            return message.equals(HEARTBEATREQUEST);
        }

        /*
         * (non-Javadoc)
         * 
         * @see
         * org.apache.mina.filter.keepalive.KeepAliveMessageFactory#isResponse
         * (org.apache.mina.core.session.IoSession, java.lang.Object)
         */
        @Override
        public boolean isResponse(IoSession session, Object message) {
            logger.info("是否是心跳包: " + message);
            return message.equals(HEARTBEATRESPONSE);
        }

    }

    /***
     * @ClassName: KeepAliveRequestTimeoutHandlerImpl
     * @Description: 当心跳超时时的处理，也可以用默认处理 这里like
     *               KeepAliveRequestTimeoutHandler.LOG的处理
     * @author Minsc Wang ys2b7_hotmail_com
     * @date 2011-3-7 下午04:15:39
     * 
     */
    private static class KeepAliveRequestTimeoutHandlerImpl implements
            KeepAliveRequestTimeoutHandler {
        
        private final Logger logger = LoggerFactory.getLogger(KeepAliveRequestTimeoutHandlerImpl.class);
        /*
         * (non-Javadoc)
         * 
         * @seeorg.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler#
         * keepAliveRequestTimedOut
         * (org.apache.mina.filter.keepalive.KeepAliveFilter,
         * org.apache.mina.core.session.IoSession)
         */
        @Override
        public void keepAliveRequestTimedOut(KeepAliveFilter filter,
                IoSession session) throws Exception {
            logger.info("心跳超时！");
        }
    }
    
}