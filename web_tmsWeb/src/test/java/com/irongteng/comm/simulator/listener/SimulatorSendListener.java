package com.irongteng.comm.simulator.listener;

import org.apache.mina.core.session.IoSession;

/**Project: OMCServerCommunication
 * Package: com.omcserver.communication
 * Class name: ReadDataInterface.java
 * 功能说明:
 * Copyright (c) 2007-2009 winhap Systems, Inc. 
 * All rights reserved.
 * Created by: lvlei
 * Changed by: lvlei On: 2016-12-15  9:47:50
 */
public interface SimulatorSendListener {
    /**
     *  功能说明:读取数据的接口
     *  @param session
     *  @param data
     *  @return boolean
     *  author lviei 2016-11-16 19:51:53
     */
    public boolean sendData(IoSession session, byte[] data);

}
