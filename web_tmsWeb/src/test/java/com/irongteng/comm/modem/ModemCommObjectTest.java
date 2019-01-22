package com.irongteng.comm.modem;

import java.util.List;
import java.util.Vector;

import com.irongteng.comm.CommModeConstants;
import com.irongteng.comm.CommObjectIF;
import com.irongteng.comm.listener.LinkAlarmListener;
import com.irongteng.comm.listener.ReadRptDataListener;
import com.irongteng.conf.ModemConfig;
import com.irongteng.conf.ModemInfo;

import dwz.common.util.StringUtils;

/**
 * Project: OMCServerCommunication Package: com.omcserver.communication.modem
 * Class name: ModemManager.java 功能说明:管理modem通信，短信、数传、GPRS Copyright (c)
 * 2007-2009 winhap Systems, Inc. All rights reserved. Created by: lvlei
 * Changed by: liuqing On: 2016-3-5 15:10:46
 */
public class ModemCommObjectTest{
    
    public static void main(String agrs[]) throws InterruptedException {
        
        List<ModemInfo> modems = new ModemConfig().getModems();
        
        for (ModemInfo modemInfo: modems) {
            
            if (!modemInfo.isUsed()) {
                continue;
            }
            Vector<ReadRptDataListener> repeaterDataListener = new Vector<ReadRptDataListener>();
            repeaterDataListener.add(new ReadRptDataListener(){

                @Override
                public void readRepeaterData(byte[] data, String sourceAddress, int communicationMode) {
                    System.out.println(StringUtils.bytes2HexString(data) + " addr:" + sourceAddress);
                }
                
            });
            
            LinkAlarmListener linkAlarmListener = new LinkAlarmListener() {

                @Override
                public void readLinkAlarmData(int alarmID, boolean isAlarm) {
                    System.out.println("alarmID:" + alarmID + " isAlarm:" + isAlarm);
                    
                }
            };
            
            CommObjectIF commObject = new ModemCommObject(repeaterDataListener, modemInfo);
            commObject.setLinkAlarmListener(linkAlarmListener);
            commObject.startService();
            
            Thread.sleep(4000);
            for(int i=0; i<20; i++) {
                String str = "test_number:" + i;
                //str = "王小华\n城中村8翔9栋901\n12-11 12:33:40";
                byte[] data = str.getBytes();
                String destination = "13480760840";
                
                while(commObject.isBusy()) {
                    Thread.sleep(3000);
                }
                
                commObject.sendData(data, destination, CommModeConstants.SMS_MODE);
                
                Thread.sleep(4000);
            }
            
        }
    }
}