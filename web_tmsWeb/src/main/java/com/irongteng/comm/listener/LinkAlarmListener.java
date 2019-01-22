package com.irongteng.comm.listener;

/**Project: OMCServerCommunication
 * Package: com.omcserver.communication
 * Class name: LinkAlarmInterface.java
 * 功能说明:链路告警侦听接口
 * Copyright (c) 2007-2009 winhap Systems, Inc. 
 * All rights reserved.
 * Created by: liuqing
 * Changed by: liuqing On: 2007-4-19  18:42:03
 */
public interface LinkAlarmListener {
    /**
     *  功能说明:读取告警数据
     *  @param alarmID 告警参数标识
     *  @param isAlarm 标识告警还是恢复
     *  return void
     *  author liuqing 2007-4-19 18:44:58
     */
    public void readLinkAlarmData(int alarmID, boolean isAlarm);
}
