package com.irongteng.control;

import java.util.Arrays;
import java.util.Date;

import org.apache.mina.core.session.IoSession;

import com.irongteng.persistence.beans.Device;

public class DeviceCache {
    
    public IoSession session;
    public String featureCode = "";
    public byte[] encryptData = null;
    public String deviceIP;
    public boolean verifySuccess = false;  //验证成功标志
    
    public byte[] publicKeyBs = null;
    
    public Device device = null;
    
    public Date verifyDate = null;
    
    //手机客户端使用
    public boolean accountLoginSuccess = false; //手机账户是否登录成标志
    public String accountName = "";             //手机账户名称
    public int thridBalance = 0;                //第三方查询剩余数量
    
    @Override
    public String toString() {
        return "DeviceCache [session=" + session + ", featureCode=" + featureCode + ", encryptData="
                + Arrays.toString(encryptData) + ", deviceIP=" + deviceIP + ", verifySuccess=" + verifySuccess
                + ", publicKeyBs=" + Arrays.toString(publicKeyBs) + ", device=" + device + "]";
    }
    
    

}
