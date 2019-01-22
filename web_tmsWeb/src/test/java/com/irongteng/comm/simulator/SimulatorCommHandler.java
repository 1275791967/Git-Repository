
package com.irongteng.comm.simulator;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irongteng.comm.simulator.listener.SimulatorReceiveListener;
import com.irongteng.comm.simulator.listener.SimulatorSendListener;

import dwz.common.util.StringUtils;

/**
 * An UDP server used for performance tests.
 * 
 * It does nothing fancy, except receiving the messages, and counting the number of
 * received messages.
 * 
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class SimulatorCommHandler extends IoHandlerAdapter {
    
    private final Logger logger = LoggerFactory.getLogger(SimulatorCommHandler.class);
    
    private SimulatorSendListener sendDataListener;
    private SimulatorReceiveListener receiveDataListener;
    
    public SimulatorSendListener getSendDataListener() {
        return sendDataListener;
    }

    public void setSendDataListener(SimulatorSendListener sendDataListener) {
        this.sendDataListener = sendDataListener;
    }
    
    public SimulatorReceiveListener getReceiveDataListener() {
        return receiveDataListener;
    }

    public void setReceiveDataListener(SimulatorReceiveListener receiveDataListener) {
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
    public void messageReceived(IoSession session, Object message) {
        try {
            if (message instanceof IoBuffer) {
                IoBuffer buffer = (IoBuffer)message;
                byte[] data = new byte[buffer.limit()];
                buffer.get(data);
                buffer.flip();
                if (receiveDataListener != null) {
                    receiveDataListener.receiveData(session, data);
                } else {
                    logger.info(StringUtils.bytes2HexString(data));
                }
            }
        } catch(Exception e) {
            logger.error(e.getMessage());
        }
    }
    
    @Override
    public void sessionCreated(IoSession iosession) throws Exception {
        System.out.println("客户端会话创建");
        super.sessionCreated(iosession);
    }
    
    @Override
    public void sessionOpened(IoSession iosession) throws Exception {
        System.out.println("客户端会话打开");
        super.sessionOpened(iosession);
    }
    
    @Override
    public void sessionClosed(IoSession session) throws Exception {
        session.closeNow();
        System.out.println("客户端会话关闭");
    }
    
    @Override
    public void sessionIdle(IoSession iosession, IdleStatus idlestatus)
            throws Exception {
        System.out.println("客户端会话休眠");
        super.sessionIdle(iosession, idlestatus);
    }
    
    @Override
    public void exceptionCaught(IoSession session, Throwable cause)
            throws Exception {
        System.out.println("客户端异常");
    }
    
}
