package com.irongteng.comm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irongteng.comm.listener.LinkAlarmListener;
import com.irongteng.comm.listener.ReadRptDataListener;
import com.irongteng.comm.modem.ModemCommObject;
import com.irongteng.conf.ModemConfig;
import com.irongteng.conf.ModemInfo;

import dwz.common.util.StringUtils;

/**Project: OMCServerCommunication
 * Package: com.omcserver.communication
 * Class name: InitCommObject.java
 * 功能说明:初始化通信对象
 * Copyright (c) 2007-2009 winhap Systems, Inc. 
 * All rights reserved.
 * Created by: liuqing
 * Changed by: liuqing On: 2007-4-10  17:19:15
 */
public class InitCommObject {
    
    private static final Logger logger = LoggerFactory.getLogger(InitCommObject.class);
    
    private Vector<ReadRptDataListener> repeaterDataListener = new Vector<>();;//直放站数据侦听者
    
    private LinkAlarmListener linkAlarmListener=null;//链路告警侦听者
    
    private static final List<CommObjectIF> commObjects = new ArrayList<>(); // 存放通信对象的列表数组(数组按优先级分类,共十个等级)
    
    public List<CommObjectIF> start() {
        
        if (repeaterDataListener !=null && repeaterDataListener.size()>0) 
            repeaterDataListener.clear();
        
        List<ModemInfo> modems = new ModemConfig().getModems();
        // 生成ModemManager
        if(modems.size() <= 0) {
            logger.info("检测到该电脑没有串口或者缺少串口包");
        }
        
        if (commObjects.size() > 0) {
            commObjects.stream().forEach((commObject) -> {
                commObject.stopService();
                commObject = null;
            });
            commObjects.clear();
        }
        
        modems.stream().filter((modemInfo) -> !(!modemInfo.isUsed())).map((modemInfo) -> new ModemCommObject(repeaterDataListener, modemInfo)).map((commObject) -> {
            commObject.setLinkAlarmListener(linkAlarmListener);
            return commObject;
        }).map((commObject) -> {
            commObject.startService();
            return commObject;
        }).forEach((commObject) -> {
            commObjects.add(commObject);
        });
        
        return sortFromPriority(commObjects);
    }
    
    /**
     * 停止服务
     */
    public void stop(){
        
        if (repeaterDataListener !=null && repeaterDataListener.size()>0) 
            repeaterDataListener.clear();
        
        commObjects.stream().forEach((commObject) -> {
            commObject.stopService();
        });
    }
    
    /**
     *  功能说明: 对通信对象列表按优先级排序
     *  @param originalList
     *  @return 
     *  return List
     *  author 吕雷 2007-4-6 16:54:58
     */
    public List<CommObjectIF> sortFromPriority(List<CommObjectIF> originalList) {
        
        if (originalList.size() <= 1) return originalList;
        
        Collections.sort(originalList, (CommObjectIF o1, CommObjectIF o2) -> {
            if (o1==null || o2==null) return 0;
            
            if(o1.getPriority() < o2.getPriority()) {
                return 1;
            } else if(o1.getPriority() > o2.getPriority()) {
                return -1;
            } else {
                return 0;
            }
            });
        return originalList;
    }
    
    public void setRepeaterDataListener(Vector<ReadRptDataListener> repeaterDataListener) {
        this.repeaterDataListener = repeaterDataListener;
    }

    /**
     * 功能说明:添加直放站数据侦听者
     * 
     * @param listener
     *            return void author liuqing 2007-4-10 18:47:13
     */
    public void addReadRptDataListener(ReadRptDataListener listener) {
        repeaterDataListener.add(listener);
    }
    
    public void setLinkAlarmListener(LinkAlarmListener linkAlarmListener) {
        this.linkAlarmListener = linkAlarmListener;
    }

    /**
     * 功能说明:通信模块向外部提供发送数据方法
     * 
     * @param data
     *            向直放站发送的数据
     * @param dest
     *            目的地
     * @param sendMode
     *            发送方式
     * @param routeList
     *            路由表
     * @return return boolean author liuqing 2007-4-6 10:07:03
     */
    public boolean sendData(byte[] data, String dest, int sendMode, String[] routeList) {

        if (dest.contains(":") && sendMode == CommModeConstants.SMS_MODE) {
            sendMode = CommModeConstants.TCP_MODE;
        }
        
        List<CommObjectIF> commObjectList = selectCommunicationManager(dest, sendMode, routeList);
        for (int i = 0; i < commObjectList.size(); i++) {
            CommObjectIF manager = commObjectList.get(i);
            if (manager instanceof ModemCommObject) {
                logger.info("使用modem发送短信等待2秒钟");
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
            if (send(data, dest, sendMode, manager)) {
                return true;
            }
        }
        
        if (commObjectList.size() <= 0) {
            
            logger.debug("没有可用的通信管理器");
            
            if (sendMode == CommModeConstants.SMS_MODE) {
                logger.info("发送短信失败：" + dest + "," + new String(data));
            } else {
                String str = StringUtils.bytes2HexString(data);
                logger.info("发送数据失败：" + dest + "," + str);
            }
        }
        return false;
    }
    
    /**
     * 功能说明:内部方法,选择适合的通信管理器,供sendData调用发送数据
     * 
     * @param dest 目的地
     * @param sendMode发送方式
     * @param routeList路由表
     * @return return CommunicationManager author liuqing 2007-4-6 11:07:03
     */
    private List<CommObjectIF> selectCommunicationManager(String dest, int sendMode, String[] routeList) {
        List<CommObjectIF> commObjectList = new ArrayList<>();// 存放可用的通信管理器
        
        if (commObjects == null || commObjects.size()<=0) {
            return commObjectList;
        }
        
        // 同等优先级时,选择可用且最久没有使用的通信管理器发送数据
        boolean isSort = false;
        for (CommObjectIF temp: commObjects) {
            switch (sendMode) {
            case CommModeConstants.SMS_MODE: // 短信方式
                if (temp.isBusy() || temp.isFault() || !temp.getAllowCommunicationMode().isAllowSms()) {
                    continue;
                }
                
                commObjectList.add(temp);
                if (temp instanceof ModemCommObject) {
                    isSort = true;
                }

                break;
            case CommModeConstants.DATA_MODE: // 数传方式
                if (temp.isBusy() || temp.isFault() || !temp.getAllowCommunicationMode().isAllowDataTransfer()) {
                    continue;
                }
                
                commObjectList.add(temp);
                if (temp instanceof ModemCommObject) {
                    isSort = true;
                }
                
                break;
            case CommModeConstants.GPRS_MODE: // GPRS方式
                if (!temp.isBusy() && !temp.isFault() && temp.getAllowCommunicationMode().isAllowGPRS()) {
                    commObjectList.add(temp);
                }
                break;
            case CommModeConstants.TCP_MODE: // Tcp方式
                if (!temp.isBusy() && !temp.isFault() && temp.getAllowCommunicationMode().isAllowTcp()) {
                    commObjectList.add(temp);
                }
                break;
            case CommModeConstants.UDP_MODE: // Udp方式
                if (!temp.isBusy() && !temp.isFault() && temp.getAllowCommunicationMode().isAllowUdp()) {
                    commObjectList.add(temp);
                }
                break;
            }
        }
        if (isSort) {
            commObjectList = sortFromLastUsedTime(commObjectList);
        }
        return commObjectList;
    }
    

    /**
     * 功能说明: 对通信对象列表按最近使用时间排序
     * 
     * @param originalList
     * @return return List author lvlei 2016-5-16 16:54:58
     */
    public List<CommObjectIF> sortFromLastUsedTime(List<CommObjectIF> originalList) {
        
        if (originalList.size() <= 1) return originalList;
        
        //按时间排序
        Collections.sort(originalList, (CommObjectIF o1, CommObjectIF o2) -> {
            if (o1==null || o2==null) return 0;
            return o1.getLastUsedTime() > o2.getLastUsedTime() ? 1
                    : o1.getLastUsedTime() == o2.getLastUsedTime() ? 0 : -1 ;
        });
        return originalList;
    }

    /**
     * 功能说明: 检查modemPhone是否在列表routeList中
     * 
     * @param modemPhone
     * @param routeList
     * @return return boolean author liuqing 2007-4-6 16:54:12
     */
    protected boolean isExistInRoute(String modemPhone, String[] routeList) {
        boolean isAllFull = true;
        for (String route : routeList) {
            if (route.equalsIgnoreCase(modemPhone)) {
                return true;
            }
            if (!route.trim().equals("")) {
                isAllFull = false;
            }
        }
        return isAllFull;
    }

    /**
     * 功能说明:利用通信管理器发送数据
     * 
     * @param data
     *             向直放站发送的数据
     * @param dest
     *             目的地
     * @param sendMode
     *             发送方式
     * @param manager
     *            通信管理器
     * @return return boolean 
     * 
     * @author 吕雷 2016-4-9 16:05:29
     */
    private boolean send(byte[] data, String dest, int sendMode, CommObjectIF manager) {
        
        boolean result = manager.sendData(data, dest, sendMode);
        
        try {
            if (sendMode == CommModeConstants.UDP_MODE || sendMode == CommModeConstants.TCP_MODE) {
                logger.debug("发送数据:" + StringUtils.bytes2HexString(data) + "-----" + result);
            } else if(sendMode == CommModeConstants.SMS_MODE) {
                String str = new String(data);
                if (StringUtils.isIncludeChineseChar(str)) {
                    str = new String(data, "Unicode");
                }
                logger.debug("发送短信:" + str + "-----" + result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public static void main(String[] args) throws InterruptedException {
        
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
