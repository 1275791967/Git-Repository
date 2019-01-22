package com.irongteng.comm;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.irongteng.comm.CommObjectIF;
import com.irongteng.comm.modem.ModemCommObject;

/**Project: OMCServerCommunication
 * Package: com.omcserver.communication
 * Class name: InitCommObject.java
 * 功能说明:初始化通信对象
 * Copyright (c) 2007-2009 winhap Systems, Inc. 
 * All rights reserved.
 * Created by: liuqing
 * Changed by: liuqing On: 2007-4-10  17:19:15
 */
public class InitCommObjectTest {
    
    InitCommObject commPool = null;
    
    @Test
    public void testStartService() {
        InitCommObject commPool = new InitCommObject();
        commPool.addReadRptDataListener(null);
        commPool.setLinkAlarmListener(null);
        commPool.start();
    }
    
    @Test
    public void testSortFromLastUsedTime() throws InterruptedException{
        
         List<CommObjectIF> list = new ArrayList<CommObjectIF>();
         CommObjectIF commObject = new ModemCommObject();
         commObject.lastUsedTime = 10;
         list.add(commObject);
         CommObjectIF commObject2 = new ModemCommObject();
         commObject2.lastUsedTime = 3;
         list.add(commObject2);
         CommObjectIF commObject3 = new ModemCommObject();
         commObject3.lastUsedTime = 7;
         list.add(commObject3);
         CommObjectIF commObject4 = new ModemCommObject();
         commObject4.lastUsedTime = 4;
         list.add(commObject4);
         CommObjectIF commObject5 = new ModemCommObject();
         commObject5.lastUsedTime = 6;
         list.add(commObject5);
         CommObjectIF commObject6 = new ModemCommObject();
         commObject6.lastUsedTime = 1;
         list.add(commObject6);
         CommObjectIF commObject7 = new ModemCommObject();
         commObject7.lastUsedTime = 2;
         list.add(commObject7);
         CommObjectIF commObject8 = new ModemCommObject();
         commObject8.lastUsedTime = 5;
         list.add(commObject8);
         CommObjectIF commObject9 = new ModemCommObject();
         commObject9.lastUsedTime = 8;
         list.add(commObject9);
         InitCommObject communicationPool = new InitCommObject();
        
         list = communicationPool.sortFromLastUsedTime(list);
         
         for(int i=0;i<list.size();i++) {
             System.out.print(list.get(i).lastUsedTime+",");
         }
    }
    
    @Test
    public void testSendMsg() throws InterruptedException {
        InitCommObject commPool = new InitCommObject();
        commPool.addReadRptDataListener(null);
        commPool.setLinkAlarmListener(null);
        commPool.start();
        
        Thread.sleep(4000);
        String str = "hello,world!";
        str = "王小华\n城中村8翔9栋901\n12-11 12:33:40";
        byte[] data = str.getBytes();
        String destination = "13480760840";
        commPool.sendData(data, destination, CommModeConstants.SMS_MODE, null);
    }
    
    public static void main(String[] args)throws InterruptedException {
        InitCommObject commPool = new InitCommObject();
        commPool.addReadRptDataListener(null);
        commPool.setLinkAlarmListener(null);
        commPool.start();
        
        Thread.sleep(4000);
        String str = "hello,world!";
        str = "王小华\n城中村8翔9栋901\n12-11 12:33:40";
        byte[] data = str.getBytes();
        String destination = "13480760840";
        commPool.sendData(data, destination, CommModeConstants.SMS_MODE, null);
    }
}
