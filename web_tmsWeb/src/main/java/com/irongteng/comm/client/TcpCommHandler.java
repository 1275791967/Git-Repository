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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irongteng.control.DeviceCache;

/**
 * An UDP server used for performance tests.
 * 
 * It does nothing fancy, except receiving the messages, and counting the number of
 * received messages.
 * 
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 */
public class TcpCommHandler extends IoHandlerAdapter {
    
    private Logger logger = LoggerFactory.getLogger(TcpCommHandler.class);
    
    private List<IoSession> ioSessions = new ArrayList<IoSession>();
    public static ArrayList<DeviceCache> cacheInfos = new ArrayList<>();
    public static HashMap<IoSession, String> infos = new HashMap<>();
    
    
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
        
        /*if (message instanceof IoBuffer) {
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
                
                logger.info(StringUtils.bytes2HexString(data));
                
                InetSocketAddress socketAddress = (InetSocketAddress) session.getRemoteAddress();
                String ipAddress = socketAddress.getAddress().getHostAddress();
                int port = socketAddress.getPort();
                
                param.setCommCategory(CommContants.TCP);
                param.setIp(ipAddress);
                param.setPort(port);
                
                ParameterControlService paramControl = SpringContextHolder.getBean(ParameterControlService.SERVICE_NAME);
                byte[] answerData = paramControl.receiveData(param);
                
                if (answerData != null) {
                    session.write(IoBuffer.wrap(answerData));
                }
            }
        }*/
        
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        cause.printStackTrace();
//        if(ioSessions.contains(session)) {
//            ioSessions.remove(session);
//        }
        session.closeNow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sessionClosed(IoSession session) throws Exception {
        if(ioSessions.contains(session)) {
            ioSessions.remove(session);
        }
        session.closeNow();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void sessionCreated(IoSession session) throws Exception {
//        ioSessions.add(session);
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
        infos.put(session, "");
       /* Date day = new Date();

        session.setAttribute("time", day);// 每次进来都更新session时间
        if (ioSessions.contains(session)) {

        } else {
            sessionCreated(session);// 放到sessions集合中
        }

        for (IoSession ss : ioSessions) {
            Date date = (Date) ss.getAttribute("time");
            long time = date.getTime();
            long time2 = day.getTime();
            if (time - time2 > 60 * 1000) {// 当前session一分钟没有更新,则将它断开连接
                sessionClosed(ss);
            }

        }

        System.out.println("Session Opened...");

        // 判断定时任务是否为空，如果为空则创建定时任务
        if (clearSession == null) {
            clearSession = new Timer("ClearSession= ");
            logger.info("清除超时的session：ClearSession");
            // 创建定时任务
            clearSession.schedule(new TimerTask() {
                // 定时任务需要执行的内容
                public void run() {
                    Date day = new Date();
                    for (IoSession ss : ioSessions) {
                        Date date = (Date) ss.getAttribute("time");
                        long time = date.getTime();
                        long time2 = day.getTime();
                        if (time - time2 > 60 * 1000) {// 当前session一分钟没有更新,则将它断开连接
                            ioSessions.remove(ss);
                        }
                    }
                }

            }, 60 * 1000);// 每隔一分钟执行一次
        }*/
    }
}