package com.irongteng.control;

import java.util.Date;
import java.util.Iterator;

import com.irongteng.protocol.ProtocolConst;
import com.irongteng.protocol.ProtocolFactory;
import com.irongteng.protocol.common.CommonParamValueObject;
import com.irongteng.service.CommLogBean;
import com.irongteng.service.CommLogService;

import dwz.common.util.StringUtils;
import dwz.framework.spring.SpringContextHolder;

public class CommLogControl {
    private CommLogService logService = SpringContextHolder.getBean(CommLogService.SERVICE_NAME);
    
    private static final CommLogControl INSTANCE = new CommLogControl();
    
    public static CommLogControl getInstance() {
        return INSTANCE;
    }
    
    public void wrietLog(int deviceID, int logType, byte[] data){
       CommLogBean bean = new CommLogBean();
       bean.setContent(StringUtils.bytes2HexString(data, ","));
       bean.setLogTypeID(logType);
       bean.setRecordTime(new Date());
       bean.setDeviceID(deviceID);
       try {
           logService.add(bean);
       } catch (Exception e) {
           e.printStackTrace();
       }
    }
    
    public void writeLog(int deviceID, CommonParamValueObject param){
        Iterator<Short> it = param.getDataMap().keySet().iterator();
        CommLogBean bean = new CommLogBean();
        while(it.hasNext()){
            Short paramID = it.next();
            switch(paramID) {
                case ProtocolConst.GET_PHONE:
                    bean.setLogTypeID(ProtocolConst.TYPE_GET_PHONE);
                    break;
                case ProtocolConst.GET_PHONE_RESULT:
                    bean.setLogTypeID(ProtocolConst.TYPE_GET_PHONE_RESULT);
                    break;
                case ProtocolConst.DEVICE_VERIFY:
                    bean.setLogTypeID(ProtocolConst.TYPE_DEVICE_VERIFY);
                    break;
                case ProtocolConst.IDENTITY_VERIFY:
                    bean.setLogTypeID(ProtocolConst.TYPE_IDENTITY_VERIFY);
                    break;
                case ProtocolConst.IDENTITY_VERIFY_RESULT:
                    bean.setLogTypeID(ProtocolConst.TYPE_IDENTITY_VERIFY_RESULT);
                    break;
                case ProtocolConst.ACCOUNT_LOGIN:
                    bean.setLogTypeID(ProtocolConst.TYPE_ACCOUNT_LOGIN);
                    break;
                case ProtocolConst.ACCOUNT_LOGIN_RESULT:
                    bean.setLogTypeID(ProtocolConst.TYPE_ACCOUNT_LOGIN_RESULT);
                    break;    
                case ProtocolConst.GSM_AUTHENT:
                    bean.setLogTypeID(ProtocolConst.TYPE_GSM_AUTHENT);
                    break;
                case ProtocolConst.GSM_AUTHENT_RESULT:
                    bean.setLogTypeID(ProtocolConst.TYPE_GSM_AUTHENT_RESULT);
                    break;
                case ProtocolConst.TRANSLATE_LOAD_REPORT:
                    bean.setLogTypeID(ProtocolConst.TYPE_TRANSLATE_LOAD_REPORT);
                    break;
                case ProtocolConst.RF_OFF_NOTIFY:
                    bean.setLogTypeID(ProtocolConst.TYPE_RF_OFF_NOTIFY);
                    break;
                case ProtocolConst.TRANSLATE_CANCEL:
                    bean.setLogTypeID(ProtocolConst.TYPE_TRANSLATE_CANCEL);
                    break;
                default:
                    break;
            }
        }
        byte[] data = ProtocolFactory.getIntance().encode(param);
        bean.setContent(StringUtils.bytes2HexString(data, ","));
        bean.setRecordTime(new Date());
        bean.setDeviceID(deviceID);
        try {
            logService.add(bean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
