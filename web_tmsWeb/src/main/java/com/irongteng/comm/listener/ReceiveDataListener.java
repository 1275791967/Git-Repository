package com.irongteng.comm.listener;

import org.apache.mina.core.session.IoSession;

import com.irongteng.comm.CommModeStatus;

/**Project: OMCServerCommunication
 * Package: com.omcserver.communication
 * Class name: ReadDataInterface.java
 * 功能说明:
 * Copyright (c) 2007-2009 irongteng Systems, Inc. 
 * All rights reserved.
 * Created by: lvlei
 * Changed by: lviei 2016-11-16 19:51:53
 */
public interface ReceiveDataListener {
    /**
     *  功能说明:读取数据的接口
     * @param session
     * @param message
     * @param commModeStatus
     *  return void
     *  
     *  author lviei 2016-11-16 19:51:53
     */
    void receiveData(IoSession session, Object message, CommModeStatus commModeStatus);
    /**
     * 包含 session
     * 
     * @param session
     * @return 
     */
    boolean containsIoSession(IoSession session);
     /**
     * 开启 session
     * 
     * @param session
     */
    void openIoSession(IoSession session);
    /**
     * 添加 session
     * 
     * @param session
     */
    void addIoSession(IoSession session);
    /**
     * 删除 session
     * 
     * @param session
     */
    void removeIoSession(IoSession session);
}
