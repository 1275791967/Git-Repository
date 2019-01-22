package com.irongteng.comm.listener;


/**Project: OMCServerCommunication
 * Package: com.omcserver.communication
 * Class name: ReadDataInterface.java
 * 功能说明:
 * Copyright (c) 2007-2009 winhap Systems, Inc. 
 * All rights reserved.
 * Created by: liuqing
 * Changed by: liuqing On: 2007-4-5  9:47:50
 */
public interface ReadRptDataListener {
    /**
     *  功能说明:读取数据的接口
     *  @param data
     *  @param sourceAddress
     *  @param communicationMode 
     *  return void
     *  author liuqing 2007-4-5 9:51:53
     */
    public void readRepeaterData(byte[] data, String sourceAddress, int communicationMode);
}
