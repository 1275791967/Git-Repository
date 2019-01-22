package com.irongteng.comm.server;

import com.irongteng.comm.coder.CommonProtocolCodecFactory;
import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.session.IoSessionConfig;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.serial.SerialAddress;
import org.apache.mina.transport.serial.SerialAddress.DataBits;
import org.apache.mina.transport.serial.SerialAddress.FlowControl;
import org.apache.mina.transport.serial.SerialAddress.Parity;
import org.apache.mina.transport.serial.SerialAddress.StopBits;
import org.apache.mina.transport.serial.SerialConnector;

import com.irongteng.comm.CommModeStatus;
import com.irongteng.comm.CommServer;
import com.irongteng.conf.ModemInfo;
import org.apache.mina.filter.executor.ExecutorFilter;

public class ModemCommServer extends CommServer {
    
    private SerialConnector connector;
    
    private IoSession session;
    
    private final ModemInfo modemInfo;
    
    private final CommModeStatus commModeStatus;
    
    public ModemCommServer(ModemInfo modemInfo, CommModeStatus commModeStatus) {
        this.modemInfo = modemInfo;
        this.commModeStatus = commModeStatus;
    }
    
    /**
     * 启动服务
     * 
     */
    @Override
    public void start() {

        try {
            connector = new SerialConnector();
            //connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8")))); // 设置编码过滤器
            connector.getFilterChain().addLast("codec",new ProtocolCodecFilter(new CommonProtocolCodecFactory(Charset.forName("UTF-8"))));
            //connector.getFilterChain().addLast("logger", new LoggingFilter());
            connector.getFilterChain().addLast("exceutor", new ExecutorFilter());  
            
            CommHandler commHandler = new CommHandler(commModeStatus);
            commHandler.setReceiveDataListener(this.getReceiveDataListener());
            commHandler.setSendDataListener(this.getSendDataListener());
            
            connector.setHandler(commHandler);
               
            SerialAddress serialAddress = new SerialAddress(modemInfo.getPortName(), modemInfo.getBaudRate(), DataBits.DATABITS_8, StopBits.BITS_1, Parity.NONE, FlowControl.NONE);
            
            ConnectFuture future = connector.connect(serialAddress);
            session = future.getSession();
            
            try {
                future.await();
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
            }
            
            // 用于配置资源管理的方法
            IoSessionConfig dcfg = connector.getSessionConfig();
            dcfg.setReadBufferSize(2048);
            
            logger.info("Modem server start running...");
            
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
    
    /**
     * 停止服务
     */
    @Override
    public void stop() {
        if (isActive()) {
            connector.dispose();
        }
    }
    
    /**
     * 重启服务
     */
    @Override
    public void restart() {
        
        stop();
        
        start();
    }
    
    /**
     * 启动状态
     * @return 
     *      返回激活状态，true开始， false为停止
     */
    @Override
    public boolean isActive() {
        
        return connector!=null && connector.isActive();
    }
    
    /**
     * 发送报文
     * 
     * @param data
     * 
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
}