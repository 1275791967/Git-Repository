package com.irongteng.comm.modem;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

/**
 * Project: OMCServerCommunication Package: com.omcserver.communication.modem
 * Class name: ModemPoolRoute.java 功能说明:modem池路由 Copyright (c) 2007-2009 winhap
 * Systems, Inc. All rights reserved. Created by: liuqing Changed by: liuqing
 * On: 2007-4-6 14:28:26
 */
public class ModemPoolRoute {
    private Map<String, String> smsRouteList = null; // 短信路由表

    private Map<String, String> dataTransferList = null; // 数传路由表

    private static ModemPoolRoute route = null;

    /**
     * 单例类
     * 
     */
    private ModemPoolRoute() {
        smsRouteList = new Hashtable<String, String>();
        dataTransferList = new Hashtable<String, String>();
    }

    /**
     * 功能说明:获取实例
     * 
     * @return return ModemPoolRoute author liuqing 2007-4-6 14:39:21
     */
    public static ModemPoolRoute getInstance() {
        if (route == null) {
            route = new ModemPoolRoute();
        }
        return route;
    }

    /**
     * 功能说明: 检查电话号码是否在短信路由列表中存在
     * 
     * @param devicePhone
     *            设备电话号码
     * @param modemPhone
     *            modem电话号码
     * @return return boolean author liuqing 2007-4-6 14:56:08
     */
    public boolean isExistInSmsRouteList(String devicePhone, String modemPhone) {
        try {
            Iterator<String> it = smsRouteList.keySet().iterator();
            while (it.hasNext()) {
                Object key = it.next();
                if ((String) key == devicePhone) {
                    String value = smsRouteList.get(key);
                    String phone[] = value.split(",");
                    for (int i = 0; i < phone.length; i++) {
                        if (phone[i] == modemPhone) {
                            return true;
                        }
                    }
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 功能说明:检查电话号码是否在数传路由列表中存在
     * 
     * @param devicePhone
     *            设备电话号码
     * @param modemPhone
     *            modem电话号码
     * @return return boolean author liuqing 2007-4-6 15:14:29
     */
    public boolean isExistInDataTransferRouteList(String devicePhone, String modemPhone) {
        try {
            Iterator<String> it = dataTransferList.keySet().iterator();
            while (it.hasNext()) {
                Object key = it.next();
                if ((String) key == devicePhone) {
                    String value = dataTransferList.get(key);
                    String phone[] =  value.split(",");
                    for (int i = 0; i < phone.length; i++) {
                        if (phone[i] == modemPhone) {
                            return true;
                        }
                    }
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 功能说明:向短信路由列表中加入路由信息
     * 
     * @param devicePhone
     *            设备电话号码
     * @param modemPhone
     *            modem电话号码 return void author liuqing 2007-4-6 15:21:46
     */
    public void addSmsRouteList(String devicePhone, String modemPhone) {
        Object object = smsRouteList.get(devicePhone);
        if (object == null) {
            smsRouteList.put(devicePhone, modemPhone);
            return;
        }
        if (!isExistInSmsRouteList(devicePhone, modemPhone)) {
            smsRouteList.put(devicePhone, modemPhone + "," + ((String) object));
        }
    }

    /**
     * 功能说明:向数传路由列表中加入路由信息
     * 
     * @param devicePhone
     *            设备电话号码
     * @param modemPhone
     *            modem电话号码 return void author liuqing 2007-4-6 15:23:28
     */
    public void addDataTransferRouteList(String devicePhone, String modemPhone) {
        Object object = dataTransferList.get(devicePhone);
        if (object == null) {
            dataTransferList.put(devicePhone, modemPhone);
            return;
        }
        if (!isExistInDataTransferRouteList(devicePhone, modemPhone)) {
            dataTransferList.put(devicePhone, modemPhone + "," + ((String) object));
        }
    }
}
