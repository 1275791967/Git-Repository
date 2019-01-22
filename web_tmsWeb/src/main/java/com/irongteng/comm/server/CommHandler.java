
package com.irongteng.comm.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irongteng.comm.CommModeStatus;
import com.irongteng.comm.listener.ReceiveDataListener;
import com.irongteng.comm.listener.SendDataListener;
import dwz.common.util.StringUtils;

/**
 * An UDP server used for performance tests.
 * 
 * It does nothing fancy, except receiving the messages, and counting the number of
 * received messages.
 * 
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class CommHandler extends IoHandlerAdapter {
    
    private final Logger logger = LoggerFactory.getLogger(CommHandler.class);
    
    private SendDataListener sendDataListener;
    private ReceiveDataListener receiveDataListener;
    private final CommModeStatus commModeStatus;
    
    
    public static List<IoSession> ioSessions = new ArrayList<IoSession>();
    
    public CommHandler(CommModeStatus commModeStatus) {
        this.commModeStatus = commModeStatus;
    }
    
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
    
    /**
     * 发送数据
     * @param session
     * @param message
     * @throws java.lang.Exception
     */
    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        super.messageSent(session, message);
    }
    
    /**
     * 接收数据
     * {@inheritDoc}
     */
    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        try {
            if (receiveDataListener != null) {
                receiveDataListener.receiveData(session, message, commModeStatus);
            } else {
                if (message instanceof IoBuffer) {
                    IoBuffer buffer = (IoBuffer)message;
                    byte[] data = new byte[buffer.limit()];
                    buffer.get(data);
                    buffer.flip();
                    logger.info(StringUtils.bytes2HexString(data));
                }
            }
        } catch(Exception e) {
            logger.error(e.getMessage());
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        logger.info("Session exception...");
        logger.error(cause.getMessage());
        session.closeNow();
        //this.sessionClosed(session);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void sessionClosed(IoSession session) throws Exception {
        logger.info(String.format("Session(%s) closed...", session.toString()));
        if (receiveDataListener != null) receiveDataListener.removeIoSession(session);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void sessionCreated(IoSession session) throws Exception {
        logger.info("Session created...");
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        logger.info("Session idle...");
        logger.info(session.getRemoteAddress().toString());
        session.closeNow();
        try{
            if(ioSessions.contains(session)){
                ioSessions.remove(session);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void sessionOpened(IoSession session) throws Exception {
        logger.info("Session Opened...");
        logger.info(session.getRemoteAddress().toString());
    }
}
