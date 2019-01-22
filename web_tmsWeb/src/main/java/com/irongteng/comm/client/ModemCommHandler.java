/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */
package com.irongteng.comm.client;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An UDP server used for performance tests.
 * 
 * It does nothing fancy, except receiving the messages, and counting the number of
 * received messages.
 * 
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class ModemCommHandler extends IoHandlerAdapter {
    
    private Logger logger = LoggerFactory.getLogger(ModemCommHandler.class);
    /**
     * 发送数据
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
        /*
        if (message instanceof IoBuffer) {
            IoBuffer buffer = (IoBuffer)message;
            byte[] data = new byte[buffer.limit()];
            buffer.get(data);
            buffer.flip();
            
            CommonParamValueObject  param = new CommonParamValueObject();
            Protocol<CommonParamValueObject> protocol = new CommonProtocol();
            boolean parseResult = protocol.decode(data, param);
            
            if (!parseResult) {
                logger.error(protocol.getParseMessage());
                
                //throw new Exception(protocol.getParseMessage());
            } else {
                ParameterControlService paramCtrlService = SpringContextHolder.getBean(ParameterControlService.SERVICE_NAME);
                
                logger.info(StringUtils.bytes2HexString(data));
                
                byte[] answerData = paramCtrlService.receiveData(param);
                
                if (answerData != null) {
                    session.write(IoBuffer.wrap(answerData));
                }
            }
        }
        */
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        cause.printStackTrace();
        session.closeNow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sessionClosed(IoSession session) throws Exception {
        System.out.println("Session closed...");
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void sessionCreated(IoSession session) throws Exception {
        System.out.println("Session created...");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        System.out.println("Session idle...");
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void sessionOpened(IoSession session) throws Exception {
        System.out.println("Session Opened...");
    }
}
