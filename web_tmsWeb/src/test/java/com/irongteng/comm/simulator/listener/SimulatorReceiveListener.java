package com.irongteng.comm.simulator.listener;

import org.apache.mina.core.session.IoSession;

/**Project: OMCServerCommunication
 * Package: com.omcserver.communication
 * Class name: ReadDataInterface.java
 * 功能说明:
 * Copyright (c) 2007-2009 irongteng Systems, Inc. 
 * All rights reserved.
 * Created by: lvlei
 * Changed by: lviei 2016-11-16 19:51:53
 */
public interface SimulatorReceiveListener {
    /**
     *  功能说明:读取数据的接口
     * @param session
     * @param message
     *  return void
     *  
     *  author lviei 2016-11-16 19:51:53
     */
    void receiveData(IoSession session, byte[] message);
    
}
