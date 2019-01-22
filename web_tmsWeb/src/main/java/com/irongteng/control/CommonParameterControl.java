package com.irongteng.control;


import java.math.BigDecimal;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.irongteng.Unit.Buffer;
import com.irongteng.Unit.Bytes;
import com.irongteng.Unit.CancelUnit;
import com.irongteng.Unit.DeviceVerifyUnit;
import com.irongteng.Unit.GetPhoneResultUnit;
import com.irongteng.Unit.GetPhoneUnit;
import com.irongteng.Unit.GsmAuthenResultsUnit;
import com.irongteng.Unit.GsmAuthenticationUnit;
import com.irongteng.Unit.AuthenticationResultsUnit;
import com.irongteng.Unit.AuthenticationUnit;
import com.irongteng.Unit.LatitudeLongUnit;
import com.irongteng.Unit.MobileloginUnit;
import com.irongteng.Unit.MobileloginresultsUnit;
import com.irongteng.Unit.TranslateUnit;
import com.irongteng.Unit.Uint16;
import com.irongteng.Unit.Uint32;
import com.irongteng.Unit.Uint8;
import com.irongteng.comm.server.CommHandler;
import com.irongteng.ipms.pool.ThreadPoolCenter;
import com.irongteng.persistence.beans.Device;
import com.irongteng.protocol.Canstants;
import com.irongteng.protocol.ParamValueObject;
import com.irongteng.protocol.ProtocolConst;
import com.irongteng.protocol.ProtocolFactory;
import com.irongteng.protocol.common.CommonMonitorObject;
import com.irongteng.protocol.common.CommonParamValueObject;
import com.irongteng.protocol.common.CommonProtocol;
import com.irongteng.service.AttributionBean;
import com.irongteng.service.AttributionService;
import com.irongteng.service.CustomerAccountBean;
import com.irongteng.service.CustomerAccountService;
import com.irongteng.service.DeviceBean;
import com.irongteng.service.DeviceOpBean;
import com.irongteng.service.DeviceOpService;
import com.irongteng.service.DeviceRandBean;
import com.irongteng.service.DeviceRandService;
import com.irongteng.service.DeviceService;
import com.irongteng.service.ImsiPhoneBean;
import com.irongteng.service.ImsiPhoneService;
import com.irongteng.service.LoadInfoBean;
import com.irongteng.service.LoadInfoService;
import com.irongteng.service.LoadOPBean;
import com.irongteng.service.LoadOPService;
import com.irongteng.service.OpInfoBean;
import com.irongteng.service.OpInfoService;
import com.irongteng.service.TranstFlowBean;
import com.irongteng.service.TranstFlowService;
import com.irongteng.thirdinterface.AccountBilling;
import com.irongteng.thirdinterface.HttpCanstants;
import com.irongteng.thirdinterface.HttpQueryInfo;

import dwz.common.util.encrypt.DataEncrypt;
import dwz.framework.spring.SpringContextHolder;
import dwz.framework.sys.exception.ServiceException;


public class CommonParameterControl extends ParameterControl{
    
    private final Logger logger = LoggerFactory.getLogger(CommonParameterControl.class);
    
    public static HashMap<String, DeviceCache> deviceCacheHashMap = new HashMap<>();
    
    private final ThreadPoolCenter poolCenter = ThreadPoolCenter.getInstance();
    
    public DeviceService dService = SpringContextHolder.getBean(DeviceService.SERVICE_NAME); 
    
    public static ConcurrentHashMap<String, String> translateFlowMap = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, IoSession> deviceSessionMap = new ConcurrentHashMap<>();
    //存储服务器上一次分配调度的时间    <设备特征码_运营商编号，时间>
    public static ConcurrentHashMap<String, Date> lastDeployMap = new ConcurrentHashMap<>();
    
    @Autowired
    public ParamValueObject specialParamValueObjectReceiver(IoSession session, CommonParamValueObject param) {
        ParamValueObject answerData = null;
        Map<Short, CommonMonitorObject> dataMap = param.getDataMap();
        Iterator<Short> it =dataMap.keySet().iterator();
//        InetSocketAddress socketAddress = (InetSocketAddress) session.getRemoteAddress(); 
        while(it.hasNext()){
            Short paramID = it.next();
//            DeviceService deviceService = SpringContextHolder.getBean(DeviceService.SERVICE_NAME); 
//            DeviceBean deviceBean = deviceService.getByIP(socketAddress.getAddress().getHostAddress());
            CommonMonitorObject mObject = null;
            switch(paramID) {
                case ProtocolConst.DEVICE_VERIFY:                   //设备验证；
                    mObject = dataMap.get(paramID);
                    answerData = paramDeviceVerifyProcessor(session, param, mObject);
                    break;
                case ProtocolConst.GET_PHONE:                       //获取电话号码；
                    mObject = dataMap.get(paramID);
                    DeviceCache dCache = getDeviceCacheByIoSession(session);
                    answerData = paramGetPhoneProcessor(dCache, mObject, param);
                    break;
                case ProtocolConst.ACCOUNT_LOGIN:                   //客户账户登录结果
                    mObject = dataMap.get(paramID);
                    answerData = paramAccountLoginProcessor(session, param, mObject);
                    break;
                case ProtocolConst.TRANSLATE_CANCEL:                //取消翻译
                    //处理服务器回应的 取消翻译数据包
                    mObject = dataMap.get(paramID);
                    byte[] values = mObject.getValue();
                    CancelUnit unit = new CancelUnit(new Buffer(values));
                    int packageID = param.getCommPackageID();
                    DeviceCache deviceCache = getDeviceCacheByIoSession(session);
                    byte[] dataXorKeys = DataEncrypt.getInstance().getDataXorKeys(deviceCache.encryptData);
                    String imsi = DataEncrypt.getInstance().getIMSIDecrypt(unit.imsi.getBytes(), packageID, dataXorKeys).trim();
                    logger.info("更新翻译流程翻译状态的imsi是： "+imsi);
                    TranstFlowService service = SpringContextHolder.getBean(TranstFlowService.SERVICE_NAME);
                    TranstFlowBean rfBean = service.getLastByImsi(imsi);
                    rfBean.setRecordTime(new Date());
                    service.updateTranslateStatusByIMSI(imsi);
                    break;
                default:
                    break;
            }
        }
        return answerData;
    }
    
    
   /**
     * 通用参数值对象接收器管理
     * 
     * @param param  参数值对象父类
     * @return 
     */
    public void commonParamValueObjectReceiver(IoSession session, CommonParamValueObject param) {
        Map<Short, CommonMonitorObject> dataMap = param.getDataMap();
        Iterator<Short> it =dataMap.keySet().iterator();
        InetSocketAddress socketAddress = (InetSocketAddress) session.getRemoteAddress(); 
        while(it.hasNext()){
            Short paramID = it.next();
            /*DeviceService deviceService = SpringContextHolder.getBean(DeviceService.SERVICE_NAME); 
            DeviceBean deviceBean = deviceService.getByIP(socketAddress.getAddress().getHostAddress());*/
            DeviceCache deviceCache = getDeviceCacheByIoSession(session);
            if(deviceCache == null) return ;
            CommonMonitorObject mObject = dataMap.get(paramID);
            switch(paramID) {
                /*case ProtocolConst.GET_PHONE:                       //获取电话号码
                    paramGetPhoneProcessor(deviceCache, mObject, param);
                    break;*/
                case ProtocolConst.GET_PHONE_RESULT:                //电话号码结果
                    paramGetPhoneResultProcessor(deviceCache, param, mObject);
                    break;
                case ProtocolConst.SET_LATITUDE_LONGITUDE:          //设置经纬度
                    paramSetLatitudAndLongitudeProcessor(deviceCache, mObject);
                    break;
                case ProtocolConst.IDENTITY_VERIFY_RESULT:          //身份验证结果
                    paramIndentityVerifyResultProcessor(deviceCache, mObject);
                    break;    
                case ProtocolConst.GSM_AUTHENT:                    //GSM鉴权
                    paramGSMAuthenProcessor(deviceCache, param, mObject);
                    break;
                case ProtocolConst.GSM_AUTHENT_RESULT:            //GSM鉴权结果
                    paramGSMAuthenResultProcessor(deviceCache, param, mObject);
                    break;
                case ProtocolConst.TRANSLATE_LOAD_REPORT:         //翻译负载信息上报
                    paramLoadInfoReportProcessor(deviceCache, mObject, param);
                    
                    break;
                case ProtocolConst.RF_OFF_NOTIFY:                 //射频关闭通知
                    paramRFoffNotifyProcessor(deviceCache, param);
                    
                    //关闭射频，主动断开连接
                    /*session.closeNow();
                    deviceCacheHashMap.remove(deviceCache.featureCode);*/
                    break;
                default:
                    break;
            }
        }
    }
    
    
    /**
     * 获取电话号码
     */
    private ParamValueObject paramGetPhoneProcessor(DeviceCache dCache, CommonMonitorObject mObject, 
            CommonParamValueObject param) {
        try {
            byte[] values = mObject.getValue();
            GetPhoneUnit unit = new GetPhoneUnit(new Buffer(values));
            
            int translateType = unit.translateType.getValue();
            //解析加密的IMSI,IMEI
            short packageID = param.getCommPackageID();
            byte[] dataXorKeys = DataEncrypt.getInstance().getDataXorKeys(dCache.encryptData);
//            logger.info("异或加密运算的密钥是："+Arrays.toString(dataXorKeys));
            String imsi = DataEncrypt.getInstance().getIMSIDecrypt(unit.imsi.getBytes(), packageID, dataXorKeys).trim();
            String imei = DataEncrypt.getInstance().getIMSIDecrypt(unit.imei.getBytes(), packageID, dataXorKeys).trim();
            String phone = DataEncrypt.getInstance().getIMSIDecrypt(unit.phone.getBytes(), packageID, dataXorKeys).trim();
            logger.info(String.format("获取电话号码请求：IMSI(%s) >>>> IMEI(%s)", imsi, imei));
            
            int deviceType = dCache.device.getDeviceType();
            //翻译前端获取电话号码的请求imsi的长度为15字节
            if((deviceType != 2) && (imsi.length() != 15)){
                logger.info(String.format("解析得到的imsi(%s) 长度不等于15....", imsi));
                return null;
            }
            //46004开头的不需要调配翻译,直接给前端回应包
            if((deviceType == 0) && (dCache != null) && imsi.startsWith("46004")){
                param.setCommandID(ProtocolConst.REQUEST_ANSWER);
                byte[] data = ProtocolFactory.getIntance().encode(param);
                if(data != null){
                    poolCenter.sendDataUseThreads(dCache.session, data);
                }
                return null;
            }
            
            TranstFlowService flowService = SpringContextHolder.getBean(TranstFlowService.SERVICE_NAME);
            TranstFlowBean flowBean = new TranstFlowBean();
            flowBean.setImsi(imsi);
            flowBean.setImei(imei);
            flowBean.setPhone(phone);
            flowBean.setFromDeviceID(dCache.device.getId());
            flowBean.setRecordTime(new Date());
            
            ImsiPhoneService phoneService = SpringContextHolder.getBean(ImsiPhoneService.SERVICE_NAME);
            ImsiPhoneBean phoneBean = phoneService.queryByImsi(flowBean.getImsi());

            //查询手机客户端直接向调度系统发起的获取电话号码请求/手动翻译/反查imsi
            if(deviceType == 2){
                logger.info("手机请求查询的imsi == "+flowBean.getImsi()+" >> 电话号码"+flowBean.getPhone());
                if(!"".equals(flowBean.getImsi())){
                    phoneBean = phoneService.queryByImsi(flowBean.getImsi());
                }else{
                    if(!"".equals(flowBean.getPhone())){
                        phoneBean = phoneService.queryByPhone(flowBean.getPhone());
                    } 
                }
                if(phoneBean == null){
                    //只有手机客户端可以使用第三方电话号码查询
                    if(unit.thridSwitch.getValue() == 1){
                        //判断该手机的账户是否登录成功
                        CustomerAccountService caService = SpringContextHolder.getBean(CustomerAccountService.SERVICE_NAME);
                        CustomerAccountBean accountBean = caService.queryByDeviceIDAndName(dCache.device.getId(), dCache.accountName);
                        if("".equals(dCache.accountName)){
                            logger.info("手机账户未登录，无法使用第三方查询功能....");
                            return null;
                        }else{
                            //判断该账户是否有第三方查询剩余数量
                            if(accountBean.getThirdBalance() <= 0){
                                logger.info(String.format("帐号 < %s >第三方查询剩余次数不足，不能进行第三方查询....", dCache.accountName));
                                return null;
                            }
                        }
                        flowBean.setLogTypeID(Canstants.TRANS_STATUS_THIRD);
                        flowBean.setToDeviceID(2);
                        flowService.add(flowBean);
                        
                        HttpQueryInfo info = new HttpQueryInfo();
                        info.param = param;
                        info.session = dCache.session;
                        if(!"".equals(imsi)){
                            info.queryStr = imsi;
                            HttpCanstants.resqList.offer("101"+imsi);
                            HttpCanstants.resqQueueMap.put(imsi, info);
                        }else{
                            if(!"".equals(phone)){
                                if(phone.length() == 14 && phone.startsWith("+86")){
                                    phone = phone.substring(1, phone.length());
                                }
                                if(phone.startsWith("1") && (phone.length() == 11)){
                                    phone = "86"+phone;
                                }
                                
                                if(phone.length() == 13 && phone.startsWith("86")){
                                    info.queryStr = phone;
                                    HttpCanstants.resqList.offer("000"+phone);
                                    HttpCanstants.resqQueueMap.put(phone, info);
                                }
                            }else{
                                logger.info("无效的获取电话号码请求，imsi和电话号码都是空值");
                                return null;
                            }
                        }
                    }
                    return null;
                }else{
                    //调度系统存在该imsi对应的电话号码/该电话号码对应的imsi
                    logger.info("调度系统存在该imsi对应的电话号码/该电话号码对应的imsi，直接将结果返回手机客户端....");
                    flowBean.setLogTypeID(Canstants.TRANS_STATUS_SYSTEM);
                    flowBean.setChannelType(getChannelType(translateType));
                    flowBean.setToDeviceID(1);
                    flowBean.setImsi(phoneBean.getImsi());
                    flowBean.setPhone(phoneBean.getPhone());
                    flowService.add(flowBean);
                    
                    //系统存在电话号码，直接在获取电话号码的应答包中将电话号码返回给前端。
                    byte[] phones = DataEncrypt.getInstance().getIMSIEncryptData(phoneBean.getPhone(), packageID, dataXorKeys);
                    byte[] imsis = DataEncrypt.getInstance().getIMSIEncryptData(phoneBean.getImsi(), packageID, dataXorKeys);
                    byte[] imeis = DataEncrypt.getInstance().getIMSIEncryptData(imei, packageID, dataXorKeys);
                    unit.imsi = new Bytes(imsis);
                    unit.imei = new Bytes(imeis);
                    unit.phone = new Bytes(phones);
                    byte[] value = unit.toBuffer().getByte();
                    Map<Short, CommonMonitorObject> dataMap = param.getDataMap();
                    short len = (short) unit.toBuffer().getByte().length;
                    CommonMonitorObject commObject = new CommonMonitorObject(ProtocolConst.GET_PHONE, len, value);
                    dataMap.replace(ProtocolConst.GET_PHONE, commObject);
                    param.setDataMap(dataMap);
                    param.setCommandID(ProtocolConst.REQUEST_ANSWER);
                    
                    return param;
                }
            }
            
            if(phoneBean == null){
                //前端获取电话号码请求的应答
                if((deviceType == 0) && (dCache != null)){
                    param.setCommandID(ProtocolConst.REQUEST_ANSWER);
                    byte[] data = ProtocolFactory.getIntance().encode(param);
                    if(data != null){
                        poolCenter.sendDataUseThreads(dCache.session, data);
                    }
                }
                
                String fromInfo = String.format("%d_%s", dCache.session.getId(), imsi);
                //当该设备这一次连接系统，已经分配过该imsi, 就不要再分配翻译了
                if(translateFlowMap.containsKey(fromInfo)){
                    logger.info(String.format("imsi <%s> 已分配了翻译....", imsi));
                    return null;
                }
                
                //根据负载分配服务器进行号码翻译
                logger.info("正在分配 服务器翻译");
                //优先根据Imsi计算出运营商进行分配服务器翻译,如果运营商无法分配服务器，再根据翻译类型进行分配服务器翻译
                int op = getOperatorByImsi(imsi);
                
                IoSession sendSession = null;
                if(op == 3){
                    //CDMA前端请求翻译调度分配的逻辑不一样
                    sendSession = getCDMATranslateService(dCache, translateType, op);
                }else{
                    sendSession = getTranslateRatioSession(translateType, op);
                }
                flowBean.setChannelType(getChannelType(op));
                logger.info("运营商："+flowBean.getChannelType());
                
                DeviceCache deviceCache = null;
                if(sendSession != null){
                    deviceCache = getDeviceCacheByIoSession(sendSession);
                    int deciceID = deviceCache.device.getId();
                    flowBean.setLogTypeID(Canstants.TRANS_STATUS_ING);
                    flowBean.setToDeviceID(deciceID);
                    flowService.add(flowBean);
                }else{
                    logger.info("无法获取调配的服务器连接....");
                    return null;
                }
                
                if(deviceCache != null){
                    //分配给服务器翻译时，因为每个设备生成的加密密钥不一样，所以要把imsi、imei、电话号码等加密数据先解析再用相应的密钥加密。
                    byte[] xorEncryptKeys = DataEncrypt.getInstance().getDataXorKeys(deviceCache.encryptData);
                    byte[] imsis = DataEncrypt.getInstance().getIMSIEncryptData(imsi, packageID, xorEncryptKeys);
                    byte[] imeis = DataEncrypt.getInstance().getIMSIEncryptData(imei, packageID, xorEncryptKeys);
                    byte[] phones = DataEncrypt.getInstance().getIMSIEncryptData(phone, packageID, xorEncryptKeys);
                    unit.imsi = new Bytes(imsis);
                    unit.imei = new Bytes(imeis);
                    unit.phone = new Bytes(phones);
                    byte[] value = unit.toBuffer().getByte();
                    Map<Short, CommonMonitorObject> dataMap = new HashMap<>();
                    short len = (short) unit.toBuffer().getByte().length;
                    CommonMonitorObject commObject = new CommonMonitorObject(ProtocolConst.GET_PHONE, len, value);
                    dataMap.put(ProtocolConst.GET_PHONE, commObject);
                    CommonParamValueObject answerParam = new CommonParamValueObject();
                    answerParam = param;
//                    CommonParamValueObject answerParam = param;
                    answerParam.setDataMap(dataMap);
                    answerParam.setCommandID(ProtocolConst.REQUEST);
                    if(sendSession != null){
                        byte[] data = ProtocolFactory.getIntance().encode(answerParam);
                        if(data != null){
                            logger.info("正在分配翻译的服务器"+sendSession.toString()+" >>> imsi == "+imsi);
                            poolCenter.sendDataUseThreads(sendSession, data);
                            
                            //将翻译流程记录加入translateFlowMap
                            String toInfo = String.format("%d_%s", sendSession.getId(), imsi);
                            translateFlowMap.put(fromInfo, toInfo);
                            /*logger.info(String.format("put <%s><%s> into translateFlowMap", fromInfo, toInfo));
                            Iterator<String> it1 = translateFlowMap.keySet().iterator();
                            while(it1.hasNext()){
                                String key = it1.next();
                                logger.info(String.format("translateFlowMap fromInfo == %s and toInfo == %s", key, translateFlowMap.get(key)));
                            }*/
                            //记录这一次分配给该服务器的时间
                            DeviceCache servicesCache = getDeviceCacheByIoSession(sendSession);
                            if(lastDeployMap.containsKey(String.format("%s_%d", servicesCache.featureCode, op))){
                                lastDeployMap.replace(String.format("%s_%d", servicesCache.featureCode, op), new Date());
                            }else{
                                lastDeployMap.put(String.format("%s_%d", servicesCache.featureCode, op), new Date()); 
                            }
                            Iterator<String> it = lastDeployMap.keySet().iterator();
                            while(it.hasNext()){
                                String key = it.next();
                                logger.info(String.format("lastDeployMap key == %s and value == %s", key, lastDeployMap.get(key).toGMTString()));
                            }
                        }
                    }
                }else{
                    logger.info("找不到对应服务器连接分配....");
                }
                
                return null;
            }else{
                logger.info("系统存在该imsi对应的电话号码，准备返回电话号码结果到前端....");
                flowBean.setLogTypeID(Canstants.TRANS_STATUS_SYSTEM);
                flowBean.setChannelType(getChannelType(translateType));
                flowBean.setToDeviceID(1);
                flowBean.setPhone(phoneBean.getPhone());
                flowService.add(flowBean);
                
                //系统存在电话号码，直接在获取电话号码的应答包中将电话号码返回给前端。
                byte[] phones = DataEncrypt.getInstance().getIMSIEncryptData(phoneBean.getPhone(), packageID, dataXorKeys);
                byte[] imsis = DataEncrypt.getInstance().getIMSIEncryptData(imsi, packageID, dataXorKeys);
                byte[] imeis = DataEncrypt.getInstance().getIMSIEncryptData(imei, packageID, dataXorKeys);
                unit.imsi = new Bytes(imsis);
                unit.imei = new Bytes(imeis);
                unit.phone = new Bytes(phones);
                byte[] value = unit.toBuffer().getByte();
                Map<Short, CommonMonitorObject> dataMap = param.getDataMap();
                short len = (short) unit.toBuffer().getByte().length;
                CommonMonitorObject commObject = new CommonMonitorObject(ProtocolConst.GET_PHONE, len, value);
                dataMap.replace(ProtocolConst.GET_PHONE, commObject);
                param.setDataMap(dataMap);
                param.setCommandID(ProtocolConst.REQUEST_ANSWER);
                
                return param;
            }
            //
            /*else{
                sendSession = dCache.session;
                answerParam = packetGetPhoneResultData(deviceCache, phoneBean, param);
            }
            if(sendSession != null){
                byte[] data = ProtocolFactory.getIntance().encode(answerParam);
                if(data != null){
//                    ThreadPoolCenter.getInstance().sendDataHaveReSend(sendSession, answerParam, data);
                    logger.info("正在分配翻译的服务器"+sendSession.toString()+" >>> imsi == "+imsi);
                    if(param.getCommandID() == ProtocolConst.REQUEST){
                        ThreadPoolCenter.getInstance().sendDataHaveReSend(sendSession, answerParam, data);
                    }else if(param.getCommandID() == ProtocolConst.REQUEST_ANSWER){
                        ThreadPoolCenter.getInstance().sendData(sendSession, data);
                    }
                    poolCenter.sendDataUseThreads(sendSession, data);
                }
            }*/
        } catch (ServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
    
    private IoSession getCDMATranslateService(DeviceCache dCache, int opNo, int translateType){
        int deviceID = dCache.device.getId();
        DeviceRandService randService = SpringContextHolder.getBean(DeviceRandService.SERVICE_NAME);
        DeviceRandBean randBean = randService.queryByDeviceID(deviceID);
        if(randBean != null){
            LoadOPService service = SpringContextHolder.getBean(LoadOPService.SERVICE_NAME);
            
            LoadOPBean lopBean = service.getCDMARatio(opNo, randBean.getRand());
            if(lopBean == null){
                logger.info(String.format("查找不到运营商编号(%d)的负载情况....", opNo));
                //如果根据运营商编号无法获取且翻译类型和运营商编号不一致时，则根据翻译类型获取翻译效率信息
                if(opNo != translateType){
                    lopBean = service.getCDMARatio(translateType, randBean.getRand());
                    if(lopBean == null){
                        logger.info(String.format("查找不到翻译类型(%d)的负载情况....", translateType));
                    }
                    opNo = translateType;
                }
            }
            
            if(lopBean != null){
                String featureCode = lopBean.getLoadOP().getDevice().getFeatureCode();
                DeviceCache deviceCache = deviceCacheHashMap.get(featureCode);
                if(deviceCache != null){
                    return deviceCache.session;
                }
            }
            return null;
        }
        return null;
    }
    
    
    private void dealPhoneClientGetPhoneProcessor(TranstFlowBean flowBean) {
        // TODO Auto-generated method stub
        ImsiPhoneService phoneService = SpringContextHolder.getBean(ImsiPhoneService.SERVICE_NAME);
        ImsiPhoneBean phoneBean = null;
    }


    public IoSession getTranslateRatioSession(int translateType, int opNo){
        LoadOPService service = SpringContextHolder.getBean(LoadOPService.SERVICE_NAME);
        //获取翻译负载最少的服务器
        List<LoadOPBean> beans = service.queryByOp(opNo);
        if(beans.size() == 0){
            logger.info(String.format("查找不到运营商编号(%d)的负载情况....", opNo));
            //如果根据运营商编号无法获取且翻译类型和运营商编号不一致时，则根据翻译类型获取翻译效率信息
            if(opNo != translateType){
                beans = service.queryByOp(translateType);
                if(beans.size() == 0){
                    logger.info(String.format("查找不到翻译类型(%d)的负载情况....", translateType));
                }
                opNo = translateType;
            }
        }
        if(beans != null && beans.size() > 0){
            logger.info("service.queryByOp(opNo)。size() == "+beans.size());
            String featureCode = beans.get(0).getLoadOP().getDevice().getFeatureCode();
            if(beans.size() > 1){
                if(lastDeployMap.containsKey(String.format("%s_%d", featureCode, opNo))){
                    featureCode = getDeployService(featureCode, beans, opNo);
                }else{
                    logger.info("lastDeployMap not contains %s "+beans.get(0).getLoadOP().getDevice().getDeviceName());
                }
            }
            
//            lastDeployMap.put(featureCode, new Date());
            DeviceCache dCache = deviceCacheHashMap.get(featureCode);
            if(dCache != null){
                return dCache.session;
            }
        }
        return null;
        
        //根据运营商编号获取翻译效率的信息
        /*LoadOPBean lopBean =  null;
        List<LoadOPBean> beans = service.queryByDevice(opNo);
        if(beans == null){
            logger.info(String.format("查找不到运营商编号(%d)的负载情况....", opNo));
            if(opNo != translateType){
                beans = service.queryByDevice(opNo);
                if(beans == null){
                    logger.info(String.format("查找不到翻译类型(%d)的负载情况....", translateType));
                }else{
                    lopBean = dealTranslateRatio(beans);
                }
                opNo = translateType;
            }
        }else{
            lopBean = dealTranslateRatio(beans);
        }*/
        
       /* LoadOPBean lopBean = service.getRatio(opNo);
        if(lopBean == null){
            logger.info(String.format("查找不到运营商编号(%d)的负载情况....", opNo));
            //如果根据运营商编号无法获取且翻译类型和运营商编号不一致时，则根据翻译类型获取翻译效率信息
            if(opNo != translateType){
                lopBean = service.getRatio(translateType);
                if(lopBean == null){
                    logger.info(String.format("查找不到翻译类型(%d)的负载情况....", translateType));
                }
                opNo = translateType;
            }
        }
        
        if(lopBean != null){
            String featureCode = lopBean.getLoadOP().getDevice().getFeatureCode();
            DeviceCache dCache = deviceCacheHashMap.get(featureCode);
            if(dCache != null){
                return dCache.session;
            }
        }
        return null;*/
    }
    
    private String getDeployService(String featureCode, List<LoadOPBean> beans, int opNo){
        for(int i=1;i<beans.size();i++){
            String featureCode1 = beans.get(i).getLoadOP().getDevice().getFeatureCode();
            logger.info("featureCode1 == "+featureCode1+" >>> featureCode == "+featureCode);
            if(lastDeployMap.containsKey(String.format("%s_%d", featureCode1, opNo))){
                Date lastDate1 = lastDeployMap.get(String.format("%s_%d", featureCode1, opNo));
                Date lastDate = lastDeployMap.get(String.format("%s_%d", featureCode, opNo));
                //featureCode在featureCode1之后有分配过，所以应该分配给featureCode1
                logger.info("lastDate1 == "+lastDate1.toGMTString()+" >>> lastDate == "+lastDate.toGMTString());
                if(lastDate1.before(lastDate)){
                    featureCode = featureCode1;
                }
            }else{
                return featureCode1;
            }
        }
        return featureCode;
    }
    
    private LoadOPBean dealTranslateRatio(List<LoadOPBean> beans){
        LoadOPBean bean = beans.get(0);
        if(bean.getRatio() != 0) return bean;
        HashMap<Integer, LoadOPBean> map = new HashMap<>();
        return null;
    }
    
    
    public DeviceCache getDeviceCacheByIoSession(IoSession session){
        Iterator<String> it = deviceCacheHashMap.keySet().iterator();
        while(it.hasNext()){
            String featureCode = it.next();
            DeviceCache dCache = deviceCacheHashMap.get(featureCode);
            if(dCache.session == session) return dCache;
        }
        return null;
    }
    
    
    /**
     * 电话号码结果
     */
    private void paramGetPhoneResultProcessor(DeviceCache deviceCache, CommonParamValueObject param, CommonMonitorObject mObject) {
        try {
            byte[] values = mObject.getValue();
            GetPhoneResultUnit unit = new GetPhoneResultUnit(new Buffer(values));
            byte[] xorEncryptKeys = DataEncrypt.getInstance().getDataXorKeys(deviceCache.encryptData);
            int packageID = param.getCommPackageID();
            String imsi = DataEncrypt.getInstance().getIMSIDecrypt(unit.imsi.getBytes(), packageID, xorEncryptKeys).trim();
            String imei = DataEncrypt.getInstance().getIMSIDecrypt(unit.imei.getBytes(), packageID, xorEncryptKeys).trim();
            String phone = DataEncrypt.getInstance().getIMSIDecrypt(unit.phone.getBytes(), packageID, xorEncryptKeys).trim();
            logger.info(String.format("电话号码结果：IMSI(%s) >>>> IMEI(%s)  >>>>> Phone(%s)", imsi, imei, phone));
            if(!(phone.equals("") || phone.startsWith("E"))){
                phone = getStandardPhone(phone);
                ImsiPhoneBean bean = new ImsiPhoneBean();
                bean.setImsi(imsi);
                bean.setImei(imei);
                bean.setPhone(phone);
                /*bean.setRecordTime(new Date());
                AttributionBean attrBean = getPhoneAttribution(phone);
                if(attrBean != null){
                    bean.setCity(attrBean.getCity());
                    bean.setProvince(attrBean.getProvince());
                }
                ImsiPhoneService phoneService = SpringContextHolder.getBean(ImsiPhoneService.SERVICE_NAME);
                phoneService.add(bean);*/
                addImsiPhone(bean);
            }
            
            String toInfo = String.format("%d_%s", deviceCache.session.getId(), imsi);
            if(!translateFlowMap.containsValue(toInfo)){
                //如果translateFlowMap不存在，则前端已断开连接或关闭射频，结果不用送给前端
                logger.info("电话号码结果：发起翻译请求的前端已经断开连接或关闭射频....");
                return ;
            }
            
            TranstFlowService flowService = SpringContextHolder.getBean(TranstFlowService.SERVICE_NAME); 
            TranstFlowBean flowBean = flowService.getLastByImsi(imsi);
            if(flowBean == null){
                // 2018-9-10
                String.format("Error : 没有imsi(%s)正在翻译的流程(该imsi已取消翻译)。。。。", imsi);
                logger.info("");
                return;
            }
            DeviceService deviceService = SpringContextHolder.getBean(DeviceService.SERVICE_NAME); 
            DeviceBean devieBean = deviceService.get(flowBean.getFromDeviceID());
            DeviceCache dCache = deviceCacheHashMap.get(devieBean.getFeatureCode());
            
            if(dCache != null && dCache.session != null){
                //将服务器翻译的电话号码写到翻译流程里
                TranstFlowBean insertFlowBean = new TranstFlowBean();
                insertFlowBean.setImsi(imsi);
                insertFlowBean.setImei(imei);
                insertFlowBean.setPhone(phone);
                insertFlowBean.setFromDeviceID(deviceCache.device.getId());
                insertFlowBean.setToDeviceID(dCache.device.getId());
                insertFlowBean.setRecordTime(new Date());
                if(!(phone.equals("") || phone.startsWith("E"))){
                    insertFlowBean.setLogTypeID(Canstants.TRANS_STATUS_SUCCESS);
                }else{
                    insertFlowBean.setLogTypeID(Canstants.TRANS_STATUS_FAIL);
                }
                insertFlowBean.setChannelType(flowBean.getChannelType());
                flowService.add(insertFlowBean);
                
                //将服务器的翻译号码转发给对应的前端设备
                Map<Short, CommonMonitorObject> dataMap = new HashMap<>();
                byte[] dataXorKeys = DataEncrypt.getInstance().getDataXorKeys(dCache.encryptData);
                byte[] imsis = DataEncrypt.getInstance().getIMSIEncryptData(imsi, packageID, dataXorKeys);
                byte[] imeis = DataEncrypt.getInstance().getIMSIEncryptData(imei, packageID, dataXorKeys);
                byte[] phones = DataEncrypt.getInstance().getIMSIEncryptData(phone, packageID, dataXorKeys);
                unit.imsi = new Bytes(imsis);
                unit.imei = new Bytes(imeis);
                unit.phone = new Bytes(phones);
                short len = (short) unit.toBuffer().getByte().length;
                CommonMonitorObject commObject = new CommonMonitorObject(ProtocolConst.GET_PHONE_RESULT, len, unit.toBuffer().getByte());
                dataMap.put(ProtocolConst.GET_PHONE_RESULT, commObject);
                param.setDataMap(dataMap);
                //命令编号不修改，还是0x7C
                param.setCommandID(ProtocolConst.REQUEST);
                
                byte[] sendData = ProtocolFactory.getIntance().encode(param);
                if(sendData != null){
                    logger.info("向前端"+dCache.session.toString()+" >>>> imsi == "+imsi);
//                    logger.info("返回电话号码结果："+Arrays.toString(sendData));
                    /*if(param.getCommandID() == ProtocolConst.REQUEST){
                        ThreadPoolCenter.getInstance().sendDataHaveReSend(dCache.session, param, sendData);
                    }else if(param.getCommandID() == ProtocolConst.REQUEST_ANSWER){
                        ThreadPoolCenter.getInstance().sendData(dCache.session, sendData);
                    }*/
//                    ThreadPoolCenter.getInstance().sendDataHaveReSend(dCache.session, param, sendData);
                    poolCenter.sendDataUseThreads(dCache.session, sendData);
                }
            }else{
                logger.info("Error : 没有对应的翻译机前端已经断开与系统的连接。。。。");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * 设置经纬度
     */
    private void paramSetLatitudAndLongitudeProcessor(DeviceCache deviceCache, CommonMonitorObject mObject) {
        try {
            byte[] values = mObject.getValue();
            LatitudeLongUnit unit = new LatitudeLongUnit(new Buffer(values));
            DeviceBean deviceBean = new DeviceBean(deviceCache.device);
            deviceBean.setLatitude(unit.latitude.toString());
            deviceBean.setLongitude(unit.longitude.toString());
            DeviceService deviceService = SpringContextHolder.getBean(DeviceService.SERVICE_NAME);
            logger.info("设备信息："+deviceBean.toString());
            deviceService.update(deviceBean);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * 设备验证
     * 
     */
    private ParamValueObject paramDeviceVerifyProcessor(IoSession session, CommonParamValueObject param, CommonMonitorObject mObject) {
        ParamValueObject answerParam = null;
        try {
            byte[] values = mObject.getValue();
            DeviceVerifyUnit unit = new DeviceVerifyUnit(new Buffer(values));
            String featureCode = unit.featureCode.toString();
            logger.info("设备特征码/手机IMEI："+featureCode);
            
            if(featureCode.equals("")){
                session.closeNow();
                return null;
            }
            
            DeviceBean deviceBean = dService.queryByFeatureCode(featureCode);
            //判断是否是我们的设备
            if(deviceBean != null){
//            if(deviceBean.getFeatureCode() != null && deviceBean.getFeatureCode().equals(featureCode)){
                //如果设备验证的IP和设备表的IP不一致，则写日志，并更新设备信息
                InetSocketAddress socketAddress = (InetSocketAddress) session.getRemoteAddress();
                String addr = socketAddress.getAddress().getHostAddress();
                if(!addr.equals(deviceBean.getDeviceIP())){
                    logger.info("更新设备IP地址信息");
                    //设备验证是我们的设备后，写日志数据库
//                    CommLogControl.getInstance().writeLog(deviceBean.getId(), param);
                    //更新设备信息（设备IP地址）
                    deviceBean.setDeviceIP(socketAddress.getAddress().getHostAddress());
                    //如果位置区码不是0，则更新位置区码信息
                    int areaCode = unit.areaCode.getValue();
                    if(areaCode != 0){
                        AttributionService attrService = SpringContextHolder.getBean(AttributionService.SERVICE_NAME); 
                        //根据区域编码获取地区名
                        String areaLocation = attrService.queryAreaByCellNo(areaCode);
                        if(areaLocation != null){
                            deviceBean.setAreaLocation(areaLocation);
                        }
                    }
                    dService.update(deviceBean);
                }
                //判断是否初次登录，如果是，保存session信息，如果不是，则更新设备缓存的session信息;
                if(unit.firsLogin.getValue() == 1){
                    //将设备的相关信息加入缓存。
                    DeviceCache dCache = new DeviceCache();
                    dCache.deviceIP = ((InetSocketAddress) session.getRemoteAddress()).getAddress().getHostAddress();
                    dCache.session = session;
                    dCache.featureCode = featureCode;
                    dCache.publicKeyBs = DataEncrypt.getInstance().createRandomBytes();
                    dCache.encryptData = DataEncrypt.getInstance().getEncryptData(featureCode, dCache.publicKeyBs);
                    dCache.device = deviceBean.getDevice();
                    dCache.verifySuccess = false; 
                    deviceCacheHashMap.put(featureCode, dCache);
                    CommHandler.ioSessions.add(session);
                    
                    //将设备的IoSession信息保存在内存
                    IoSession oldSession = null;
                    if(deviceSessionMap.containsKey(featureCode)){
                        oldSession = deviceSessionMap.get(featureCode);
                        deviceSessionMap.replace(featureCode, session);
                    }else{
                        deviceSessionMap.put(featureCode, session);
                    }
                    
                    //如果是前端第一次登录，清除服务器中该设备请求翻译的imsi(设备直接断电重启的情况)
                    if(deviceBean.getDevice().getDeviceType() == 0){ 
                        //前端设备首次登录时，删除translateFlowMap中该前端上次连接的翻译记录
                        if(oldSession != null){
                            removeTranslateFlowMapBySession(oldSession);
                        }
                        if(translateFlowMap.size() > 0){
                            Iterator<String> it1 = translateFlowMap.keySet().iterator();
                            while(it1.hasNext()){
                                String key = it1.next();
                                logger.info(String.format("设备验证 ：translateFlowMap fromInfo == %s and toInfo == %s", key, translateFlowMap.get(key)));
                            }
                        }else{
                            logger.info("设备验证 ： translateFlowMap is cleared....");
                        }
                        
                        logger.info(String.format("翻译前端设备( %s<%s> )首次登录调度系统 ....", deviceBean.getDeviceName(), featureCode));
                        dealTranslateCancel(featureCode, param);
                    }else if(deviceBean.getDevice().getDeviceType() == 1){
                        //服务器重新登录，将分配给该服务器翻译的翻译流程的状态改为取消翻译
                        logger.info(String.format("翻译服务器( %s<%s> )首次登录调度系统 ....", deviceBean.getDeviceName(), featureCode));
                        TranstFlowService flowService = SpringContextHolder.getBean(TranstFlowService.SERVICE_NAME);
                        int toDeviceID = deviceBean.getDevice().getId();
                        List<TranstFlowBean> tfBeans = flowService.getCancelTranslateList1(toDeviceID);
                        if(tfBeans.size() > 0){
                            flowService.updateTranslateStatusByDeviceID(toDeviceID);
                        }
                    }
                }else if(unit.firsLogin.getValue() == 0){
                    //将设备的IoSession信息保存在内存
                    IoSession oldSession = null;
                    if(deviceSessionMap.containsKey(featureCode)){
                        oldSession = deviceSessionMap.get(featureCode);
                        deviceSessionMap.replace(featureCode, session);
                    }else{
                        deviceSessionMap.put(featureCode, session);
                    }
                    if((deviceBean.getDevice().getDeviceType() == 0) && (oldSession != null)){
                        updateMapWhenReConnect(oldSession, session);
                    }
                    
                    DeviceCache dCache = deviceCacheHashMap.get(featureCode);
                    if(dCache == null){
                        //将设备的相关信息加入缓存。
                        dCache = new DeviceCache();
                        dCache.deviceIP = ((InetSocketAddress) session.getRemoteAddress()).getAddress().getHostAddress();
                        dCache.session = session;
                        dCache.featureCode = featureCode;
                        dCache.publicKeyBs = DataEncrypt.getInstance().createRandomBytes();
                        dCache.encryptData = DataEncrypt.getInstance().getEncryptData(featureCode, dCache.publicKeyBs);
                        dCache.device = deviceBean.getDevice();
                        dCache.verifySuccess = false; 
                        dCache.verifyDate = new Date();
                        deviceCacheHashMap.put(featureCode, dCache);
                        CommHandler.ioSessions.add(session);
                    }else{
                        //移除ioSessions被替代的session
                        CommHandler.ioSessions.remove(dCache.session);
                        //更新缓存的设备缓存信息。
                        dCache.session = session;
                        dCache.verifySuccess = false; 
                        dCache.verifyDate = new Date();
                        dCache.publicKeyBs = DataEncrypt.getInstance().createRandomBytes();
                        dCache.encryptData = DataEncrypt.getInstance().getEncryptData(featureCode, dCache.publicKeyBs);
                        CommHandler.ioSessions.add(session);
                    }
                }
                
                //添加或更新服务器设备与运营商通道的对应关系
                if(deviceBean.getDeviceType() == 1){ //只有服务器有这个操作
                    dealDeviceOPProcessor(deviceBean, unit);
                }else if(deviceBean.getDeviceType() == 0){ //
                    if(unit.op1.getValue() == 3 || unit.op2.getValue() == 3 || unit.op3.getValue() == 3 || 
                            unit.op4.getValue() == 3 || unit.op5.getValue() == 3){  //带CDMA的前端才需要分配Rand值
                        if(!dealCDMAFrontRandProcessor(session, deviceBean)){
                            return null;
                        }
                    }
                }
                
                logger.info("设备验证成功，准备发起身份验证请求。。。。");  
                //设备验证没有回应包，设备验证通过后发起身份验证
                answerParam = packetIdentityVerify(featureCode, param);
                if(answerParam == null){
                    session.closeNow();
                    return null;
                }
            }else{
                //判断不是我们的设备，断开与设备的连接
                session.closeNow();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.info("Exception :"+e.toString()); 
            session.closeNow();
            return null;
        }
        return answerParam;
    }
   
    
    //处理CDMA前端的rand值分配。
    private boolean dealCDMAFrontRandProcessor(IoSession session, DeviceBean deviceBean){
        DeviceRandService randService = SpringContextHolder.getBean(DeviceRandService.SERVICE_NAME); 
        DeviceRandBean rBean = randService.getRandToDevice();
//        if(rBean == null || deviceCacheHashMap.get(rBean.getDevice().getFeatureCode()) == null){
        if(rBean == null){
            logger.info("没有Rand值分配给CDMA前端，先断开CDMA前端的连接....");
            session.closeNow();
            return false;
        }
        try{
            int deviceID = deviceBean.getId();
            DeviceRandBean randBean = randService.queryByDeviceID(deviceID);
            if(randBean == null){
                randBean = new DeviceRandBean();
                randBean.setDevice(deviceBean.getDevice());
                randBean.setRand(rBean.getRand());
                randService.add(randBean);
                
                DeviceRandBean serviceRand = randService.queryServiceRand(rBean.getRand()).get(0);
                if(serviceRand != null){
                    int dCount = serviceRand.getdCount()+1;
                    serviceRand.setdCount(dCount);
                    randService.update(serviceRand);
                }
            }else{
                if(rBean.getRand() != randBean.getRand()){
                    randBean.setRand(rBean.getRand());
                    randService.update(randBean);
                    
                    DeviceRandBean serviceRand1 = randService.queryServiceRand(randBean.getRand()).get(0);
                    if(serviceRand1 != null){
                        int dCount = serviceRand1.getdCount()-1;
                        serviceRand1.setdCount(dCount);
                        randService.update(serviceRand1);
                    }
                    
                    DeviceRandBean serviceRand = randService.queryServiceRand(rBean.getRand()).get(0);
                    if(serviceRand != null){
                        int dCount = serviceRand.getdCount()+1;
                        serviceRand.setdCount(dCount);
                        randService.update(serviceRand);
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    //处理服务器设备与营运商通道的对应关系
    private void dealDeviceOPProcessor(DeviceBean deviceBean, DeviceVerifyUnit unit){
        DeviceOpService service = SpringContextHolder.getBean(DeviceOpService.SERVICE_NAME); 
        try {
            DeviceOpBean bean = service.queryByDeviceID(deviceBean.getId());
            System.out.println(bean == null ? "bean is null..." : bean.toString());
            if(bean == null){
                bean = new DeviceOpBean();
                bean.getDeviceOp().setDevice(deviceBean.getDevice());
                bean.setOp1(unit.op1.getValue());
                bean.setOp2(unit.op2.getValue());
                bean.setOp3(unit.op3.getValue());
                bean.setOp4(unit.op4.getValue());
                bean.setOp5(unit.op5.getValue());
                service.add(bean);
            }else{
                bean.setOp1(unit.op1.getValue());
                bean.setOp2(unit.op2.getValue());
                bean.setOp3(unit.op3.getValue());
                bean.setOp4(unit.op4.getValue());
                bean.setOp5(unit.op5.getValue());
                service.update(bean);
            }
            
            //处理CDMA服务器
            if(bean.getOp1() == 3 || bean.getOp2() == 3 || bean.getOp3() == 3 || bean.getOp4() == 3 || bean.getOp5() == 3){
                DeviceRandService randService = SpringContextHolder.getBean(DeviceRandService.SERVICE_NAME); 
                if(deviceBean.getDeviceType() == 1){
                    int randValue = unit.rand.getValue();
                    int deviceID = deviceBean.getId();
                    logger.info("queryByDeviceID deviceID is "+deviceID);
                    DeviceRandBean randBean = randService.queryByDeviceID(deviceID);
//                    logger.info("randService.queryByDeviceID == "+randBean == null ? "null" : randBean.toString());
                    if(randBean == null){
                        logger.info("randBean is null....");
                        randBean = new DeviceRandBean();
                        randBean.setDevice(deviceBean.getDevice());
                        randBean.setRand(randValue);
                        logger.info("add : "+randBean.toString());
                        randService.add(randBean);
                    }else{
                        if(randValue != randBean.getRand()){
                            randBean.setRand(randValue);
                            logger.info("update : "+randBean.toString());
                            randService.update(randBean);
                        }
                    }
                }
            }
        } catch (ServiceException e) {
            e.printStackTrace();
            logger.info("Exception :"+e.toString()); 
        }
    }
   
       
    /**
     * 身份验证结果
     */
    private void paramIndentityVerifyResultProcessor(DeviceCache dCache, CommonMonitorObject mObject) {
        try {
            byte[] values = mObject.getValue();
            AuthenticationResultsUnit unit = new AuthenticationResultsUnit(new Buffer(values));
            byte[] dBS = unit.authentication.getBytes();
            //校验鉴权码是否一致。
            if(!Arrays.equals(dBS, dCache.encryptData)){
                //校验不通过，从缓存中去掉。
                dCache.verifySuccess = false; 
                deviceCacheHashMap.remove(dCache.featureCode);
                dCache.session.closeNow();
            }else{
                dCache.verifySuccess = true;
                logger.info("身份验证成功。。。。");
//                logger.info("加密密钥数据是："+ dCache);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.info("Exception :"+e.toString()); 
        }
        
    }
    
    /**
     * 客户帐号登录
     */
    private ParamValueObject paramAccountLoginProcessor(IoSession session, CommonParamValueObject param, CommonMonitorObject mObject) {
        try {
            CustomerAccountService caService = SpringContextHolder.getBean(CustomerAccountService.SERVICE_NAME);
            DeviceCache dCache = getDeviceCacheByIoSession(session);
            if(dCache == null) return null;
            if(!dCache.verifySuccess) return null;
            byte[] values = mObject.getValue();
            MobileloginUnit unit = new MobileloginUnit(new Buffer(values));
            String accountName = unit.name.toString();
            logger.info("账户登录名 == "+accountName);
            CustomerAccountBean caBean = caService.queryByDeviceIDAndName(dCache.device.getId(), accountName);
            MobileloginresultsUnit resultUnit = new MobileloginresultsUnit();
            if(caBean == null){
                //用户不存在
                logger.info("用户名不存在  "+accountName);
                resultUnit.loginResult = new Uint8(Canstants.MISS_ACCOUNT_NAME);
            }else{
//                byte[] pswBytes = MD5.getMD5Bytes(caBean.getName()+caBean.getPsw());
//                if(Arrays.equals(pswBytes, unit.psw.getBytes())){
                if(caBean.getPsw().equals(unit.psw.toString())){
                    resultUnit.loginResult = new Uint8(Canstants.ACCOUNT_LOGIN_SUCCESS);
                    resultUnit.localBalance = new Uint32(caBean.getLocalBalance());
                    resultUnit.thirdBalance = new Uint32(caBean.getThirdBalance());
                    
                    dCache.thridBalance = caBean.getThirdBalance();
                    dCache.accountName = accountName;
                }else{
                    logger.info("密码错误： 登录密码 ==  "+unit.psw.toString() + "实际密码是  == "+caBean.getPsw());
                    resultUnit.loginResult = new Uint8(Canstants.PASSWORD_ERROR);
                }
            }
            
            Map<Short, CommonMonitorObject> dataMap = new HashMap<>();
            byte[] value = resultUnit.toBuffer().getByte();
            short len = (short) resultUnit.toBuffer().getByte().length;
            CommonMonitorObject commObject = new CommonMonitorObject(ProtocolConst.ACCOUNT_LOGIN_RESULT, len, value);
            dataMap.put(ProtocolConst.ACCOUNT_LOGIN_RESULT, commObject);
            param.setDataMap(dataMap);
            //作为账户登录的应答包，使用0x6C命令编号应答
            param.setCommandID(ProtocolConst.REQUEST_ANSWER);
            return param;
        }catch(Exception e){
            e.printStackTrace();
            logger.info("Exception :"+e.toString()); 
        }
        return null;
    }
    
    
    /**
     * GSM鉴权
     */
    private void paramGSMAuthenProcessor(DeviceCache deviceCache, CommonParamValueObject param, CommonMonitorObject mObject) {
        byte[] values = mObject.getValue();
        GsmAuthenticationUnit unit = new GsmAuthenticationUnit(new Buffer(values));
        //根据密钥解密得到imsi
        byte[] xorEncryptKeys = DataEncrypt.getInstance().getDataXorKeys(deviceCache.encryptData);
        byte[] imsis = unit.imsi.getBytes();
        int packageID = param.getCommPackageID();
        String imsi = DataEncrypt.getInstance().getIMSIDecrypt(imsis, packageID, xorEncryptKeys).trim();
        String toInfo = String.format("%d_%s", deviceCache.session.getId(), imsi);
        if(!translateFlowMap.containsValue(toInfo)){
            //如果translateFlowMap不存在，则前端已断开连接或关闭射频，不需要向前端请求鉴权
            logger.info("GSM鉴权请求：发起翻译请求的前端已经断开连接或关闭射频....");
            return ;
        }
        
        logger.info("向前端发起鉴权请求的imsi是："+imsi);
        TranstFlowService flowService = SpringContextHolder.getBean(TranstFlowService.SERVICE_NAME); 
        TranstFlowBean flowBean = flowService.getLastByImsi(imsi);
        if(flowBean == null){
            logger.info("翻译流程表找不到imsi( "+imsi+" )");
            return;
        }
        DeviceService deviceService = SpringContextHolder.getBean(DeviceService.SERVICE_NAME); 
        DeviceBean devieBean = deviceService.get(flowBean.getFromDeviceID());
        DeviceCache dCache = deviceCacheHashMap.get(devieBean.getFeatureCode());
        
        if(dCache != null && dCache.session != null){
            //根据对应前端的密钥加密imsi
            byte[] xorEncryptKeys1 = DataEncrypt.getInstance().getDataXorKeys(dCache.encryptData);
            byte[] data = DataEncrypt.getInstance().getIMSIEncryptData(imsi, packageID, xorEncryptKeys1);
            unit.imsi = new Bytes(data);
            Map<Short, CommonMonitorObject> dataMap = new HashMap<>();
            short len = (short) unit.toBuffer().getByte().length;
            byte[] value = unit.toBuffer().getByte();
            CommonMonitorObject commObject = new CommonMonitorObject(ProtocolConst.GSM_AUTHENT, len, value);
            dataMap.put(ProtocolConst.GSM_AUTHENT, commObject);
            param.setDataMap(dataMap);
            param.setCommandID(ProtocolConst.REQUEST);
            
            byte[] sendData = ProtocolFactory.getIntance().encode(param);
            if(sendData != null){
                logger.info("正在向前端发起鉴权请求："+dCache.session.toString());
//                ThreadPoolCenter.getInstance().sendDataHaveReSend(dCache.session, param, sendData);
                poolCenter.sendDataUseThreads(dCache.session, sendData);
            }
        }else{
            logger.info("Error : 没有对应的翻译机前端，前端已经断开与系统的连接。。。。");
        }
    }
    
    
    
    /**
     * GSM鉴权结果
     */
    private void paramGSMAuthenResultProcessor(DeviceCache deviceCache, CommonParamValueObject param, CommonMonitorObject mObject) {
        byte[] values = mObject.getValue();
        GsmAuthenResultsUnit unit = new GsmAuthenResultsUnit(new Buffer(values));
        //根据密钥解密得到IMSI
        byte[] xorEncryptKeys = DataEncrypt.getInstance().getDataXorKeys(deviceCache.encryptData);
        byte[] imsis = unit.imsi.getBytes();
        int packageID = param.getCommPackageID();
        String imsi = DataEncrypt.getInstance().getIMSIDecrypt(imsis, packageID, xorEncryptKeys).trim();
        logger.info("GSM鉴权结果的imsi是："+imsi+" >>> Sres == "+unit.sres.getValue());
        TranstFlowService flowService = SpringContextHolder.getBean(TranstFlowService.SERVICE_NAME); 
        TranstFlowBean flowBean = flowService.getLastByImsi(imsi);
        DeviceService deviceService = SpringContextHolder.getBean(DeviceService.SERVICE_NAME); 
        DeviceBean devieBean = deviceService.get(flowBean.getToDeviceID());
        DeviceCache dCache = deviceCacheHashMap.get(devieBean.getFeatureCode());
       
        if(dCache != null && dCache.session != null){
           //根据对应服务器的密钥加密imsi
            byte[] xorEncryptKeys1 = DataEncrypt.getInstance().getDataXorKeys(dCache.encryptData);
            byte[] data = DataEncrypt.getInstance().getIMSIEncryptData(imsi, packageID, xorEncryptKeys1);
            unit.imsi = new Bytes(data);
            Map<Short, CommonMonitorObject> dataMap = new HashMap<>();
            short len = (short) unit.toBuffer().getByte().length;
            byte[] value = unit.toBuffer().getByte();
            CommonMonitorObject commObject = new CommonMonitorObject(ProtocolConst.GSM_AUTHENT_RESULT, len, value);
            dataMap.put(ProtocolConst.GSM_AUTHENT_RESULT, commObject);
            param.setDataMap(dataMap);
            param.setCommandID(ProtocolConst.REQUEST);
            
            byte[] sendData = ProtocolFactory.getIntance().encode(param);
            if(sendData != null){
                logger.info("将鉴权结果发给服务器："+dCache.session.toString());
//                ThreadPoolCenter.getInstance().sendDataHaveReSend(dCache.session, param, sendData);
                poolCenter.sendDataUseThreads(dCache.session, sendData);
            }
            
        }else{
            logger.info("Error : 没有对应的翻译服务器已经断开与系统的连接。。。。");
        }
    }
    
    /**
     * 服务器翻译负载上报
     */
    private void paramLoadInfoReportProcessor(DeviceCache dCache, CommonMonitorObject mObject, CommonParamValueObject param) {
        LoadInfoService service = SpringContextHolder.getBean(LoadInfoService.SERVICE_NAME); 
        //获取设备信息
        DeviceService deviceService = SpringContextHolder.getBean(DeviceService.SERVICE_NAME); 
        DeviceBean deviceBean = deviceService.queryByFeatureCode(dCache.featureCode);
        if(deviceBean == null){
            logger.info("deviceBean is null , 设备特征码是: "+dCache.featureCode);
            return;
        }
        
        if(deviceBean.getDeviceType() != 1){ //服务器设备才有负载上报命令
            logger.info("该设备不是服务器，不需要负载上报。。。。");
            return;
        }
        
        byte[] values = mObject.getValue();
        TranslateUnit unit = new TranslateUnit(new Buffer(values));
        //查看负载信息 是否存在，存在则更新，不存在则添加
        LoadInfoBean bean = service.queryByDeviceID(deviceBean.getId());
        logger.info(String.format("服务器( %s )负载上报。。。。", deviceBean.getDeviceName()));
        logger.info(unit.toString());
        try{
            if(bean == null){
                bean = new LoadInfoBean();
                bean.setLoadInfo(unit);
                bean.setDeviceID(deviceBean.getId());
                bean.setDeviceName(deviceBean.getDeviceName());
                bean.setRecordTime(new Date());
//                logger.info("add: "+bean.toString());
                service.add(bean);
            }else{
                bean.setLoadInfo(unit);
                bean.setRecordTime(new Date());
//                logger.info("update: "+bean.toString());
                service.update(bean);
            }
            
            //计算翻译效率，根据运营商和设备写数据库（设备运营商翻译效率表t_load_op）
            DeviceOpService opService = SpringContextHolder.getBean(DeviceOpService.SERVICE_NAME); 
            //获取运营商编号与通道对应关系
            DeviceOpBean opBean = opService.queryByDeviceID(deviceBean.getId());
            if(opBean != null){
                dealTranslateRatio(opBean, bean);
            }
       
            
            
            //临时测试
            /*if(deviceBean.getDeviceIP().equals("192.168.1.239")){
                TranstFlowService flowService = SpringContextHolder.getBean(TranstFlowService.SERVICE_NAME); 
                List<TranstFlowBean> beans = flowService.getDevice(deviceBean.getDevice().getId());
                List<TranstFlowBean> list = beans.subList(0, 20);
                logger.info("模拟测试取消翻译流程：准备进行取消翻译流程....");
                
                dealTranslateCancel(0, deviceService, list, param);
                
                logger.info("模拟测试取消翻译流程：取消翻译流程已完成....");
            }*/
            
        }catch(Exception e){
            e.printStackTrace();
            logger.info("Exception :"+e.toString()); 
        }
    }
    
    
    private void dealTranslateRatio(DeviceOpBean opBean, LoadInfoBean bean){
        LoadOPService service = SpringContextHolder.getBean(LoadOPService.SERVICE_NAME); 
        Device device = opBean.getDeviceOp().getDevice();
        int normalCH = 0;
        try{
            if(bean.getCh1Counts() != 0){
                int opNo = opBean.getOp1();
                if((opNo != 0) && (bean.getCh1NormalCounts() != 0)){
                    //使用BigDecimal进行小数的除法
                    double ratio = new BigDecimal(bean.getCh1WaitTranslates()).divide(new BigDecimal(bean.getCh1NormalCounts()),10,BigDecimal.ROUND_HALF_UP).doubleValue();
                    LoadOPBean loadOPBean = service.get(device.getId(), opNo);
                    if(loadOPBean == null){
                        loadOPBean = new LoadOPBean();
                        loadOPBean.getLoadOP().setDevice(device);
                        loadOPBean.setOpNo(opNo);
                        loadOPBean.setRatio(ratio);
                        service.add(loadOPBean);
                    }else{
                        loadOPBean.setRatio(ratio);
                        service.updateRatio(loadOPBean);
                    }
                }
                
                if(opNo == 3 && bean.getCh1NormalCounts() != 0){
                    normalCH = bean.getCh1NormalCounts();
                }
            }
            if(bean.getCh2Counts() != 0){
                int opNo = opBean.getOp2();
                if((opNo != 0) && (bean.getCh2NormalCounts() != 0)){
                    //使用BigDecimal进行小数的除法
                    double ratio = new BigDecimal(bean.getCh2WaitTranslates()).divide(new BigDecimal(bean.getCh2NormalCounts()),10,BigDecimal.ROUND_HALF_UP).doubleValue();
                    LoadOPBean loadOPBean = service.get(device.getId(), opNo);
                    if(loadOPBean == null){
                        loadOPBean = new LoadOPBean();
                        loadOPBean.getLoadOP().setDevice(device);
                        loadOPBean.setOpNo(opNo);
                        loadOPBean.setRatio(ratio);
                        service.add(loadOPBean);
                    }else{
                        loadOPBean.setRatio(ratio);
                        service.updateRatio(loadOPBean);
                    }
                }
                
                if(opNo == 3 && bean.getCh2NormalCounts() != 0){
                    normalCH = bean.getCh2NormalCounts();
                }
            }
            if(bean.getCh3Counts() != 0){
                int opNo = opBean.getOp3();
                if((opNo != 0) && (bean.getCh3NormalCounts() != 0)){
                    //使用BigDecimal进行小数的除法
                    double ratio = new BigDecimal(bean.getCh3WaitTranslates()).divide(new BigDecimal(bean.getCh3NormalCounts()),10,BigDecimal.ROUND_HALF_UP).doubleValue();
                    LoadOPBean loadOPBean = service.get(device.getId(), opNo);
                    if(loadOPBean == null){
                        loadOPBean = new LoadOPBean();
                        loadOPBean.getLoadOP().setDevice(device);
                        loadOPBean.setOpNo(opNo);
                        loadOPBean.setRatio(ratio);
                        service.add(loadOPBean);
                    }else{
                        loadOPBean.setRatio(ratio);
                        service.updateRatio(loadOPBean);
                    }
                }
                
                if(opNo == 3 && bean.getCh3NormalCounts() != 0){
                    normalCH = bean.getCh3NormalCounts();
                }
            }
            if(bean.getCh4Counts() != 0){
                int opNo = opBean.getOp4();
                if((opNo != 0) && (bean.getCh4NormalCounts() != 0)){
                    //使用BigDecimal进行小数的除法
                    double ratio = new BigDecimal(bean.getCh4WaitTranslates()).divide(new BigDecimal(bean.getCh4NormalCounts()),10,BigDecimal.ROUND_HALF_UP).doubleValue();
                    LoadOPBean loadOPBean = service.get(device.getId(), opNo);
                    if(loadOPBean == null){
                        loadOPBean = new LoadOPBean();
                        loadOPBean.getLoadOP().setDevice(device);
                        loadOPBean.setOpNo(opNo);
                        loadOPBean.setRatio(ratio);
                        service.add(loadOPBean);
                    }else{
                        loadOPBean.setRatio(ratio);
                        service.updateRatio(loadOPBean);
                    }
                }
                
                if(opNo == 3 && bean.getCh4NormalCounts() != 0){
                    normalCH = bean.getCh4NormalCounts();
                }
            }
            if(bean.getCh5Counts() != 0){
                int opNo = opBean.getOp5();
                if((opNo != 0) && (bean.getCh5NormalCounts() != 0)){
                    //使用BigDecimal进行小数的除法
                    double ratio = new BigDecimal(bean.getCh5WaitTranslates()).divide(new BigDecimal(bean.getCh5NormalCounts()),10,BigDecimal.ROUND_HALF_UP).doubleValue();
                    LoadOPBean loadOPBean = service.get(device.getId(), opNo);
                    if(loadOPBean == null){
                        loadOPBean = new LoadOPBean();
                        loadOPBean.getLoadOP().setDevice(device);
                        loadOPBean.setOpNo(opNo);
                        loadOPBean.setRatio(ratio);
                        service.add(loadOPBean);
                    }else{
                        loadOPBean.setRatio(ratio);
                        service.updateRatio(loadOPBean);
                    }
                }
                
                if(opNo == 3 && bean.getCh5NormalCounts() != 0){
                    normalCH = bean.getCh5NormalCounts();
                }
            }
            
            if(normalCH != 0){
                DeviceRandService randService = SpringContextHolder.getBean(DeviceRandService.SERVICE_NAME); 
                DeviceRandBean randBean = randService.queryByDeviceID(device.getId());
                if(randBean == null){
                    logger.info("randService queryByDeviceID result is null....");
                }else{
                    randBean.setNormalCh(normalCH);
                    logger.info("randBean == "+randBean.toString());
                    randService.update(randBean);
                }
            }
        }catch (Exception e) {
            logger.info("Exception : "+e.toString());
            e.printStackTrace();
        }
    }
    
    /**
     * 射频关闭通知
     */
    private void paramRFoffNotifyProcessor(DeviceCache deviceCache, CommonParamValueObject param) {
        if(deviceCache.device.getDeviceType() == 0){       //如果关闭射频的设备是前端设备，取消该设备请求的号码翻译任务。
            String featureCode = deviceCache.featureCode;
            //前端设备关闭射频时，删除translateFlowMap中该前端的翻译记录
            if(deviceCache.session != null){
                removeTranslateFlowMapBySession(deviceCache.session);
            }
            if(translateFlowMap.size() > 0){
                Iterator<String> it1 = translateFlowMap.keySet().iterator();
                while(it1.hasNext()){
                    String key = it1.next();
                    logger.info(String.format("射频关闭：translateFlowMap fromInfo == %s and toInfo == %s", key, translateFlowMap.get(key)));
                }
            }else{
                logger.info("射频关闭：translateFlowMap is cleared....");
            }
            
            dealTranslateCancel(featureCode, param);
            
            /*DeviceOpService service = SpringContextHolder.getBean(DeviceOpService.SERVICE_NAME);
            DeviceOpBean bean = service.queryByDeviceID(deviceCache.device.getId());
            logger.info(bean == null ? "DeviceOpBean is null":bean.toString());
            if(bean != null && containsCDMACh(bean)){
                logger.info("纯CDMA的翻译前端关闭设备，不向服务器发起取消翻译命令....");
            }else{
                String featureCode = deviceCache.featureCode;
                dealTranslateCancel(featureCode, param);
            }*/
            
            /*LoadOPService service = SpringContextHolder.getBean(LoadOPService.SERVICE_NAME);
            List<LoadOPBean> beans = service.queryByDevice(deviceCache.device.getId());
            if(beans != null){
                if(beans.size() == 1){
                    LoadOPBean bean = beans.get(0);
                    logger.info("LoadOPBean的运营商编号是："+bean.getOpNo());
                    if(bean.getOpNo() == 3){
                        logger.info("纯CDMA的翻译前端关闭设备，不向服务器发起取消翻译命令....");
                    }else{
                        String featureCode = deviceCache.featureCode;
                        dealTranslateCancel(featureCode, param);
                    }
                }else{
                    logger.info("List<LoadOPBean> size == "+beans.size());
                    String featureCode = deviceCache.featureCode;
                    dealTranslateCancel(featureCode, param);
                }
            }else{
                logger.info("List<LoadOPBean> is null...."); 
            }*/
        }
        
        //查找需要取消翻译的号码(查找fromDeviceID=deviceID and toDeviceID != 0)
        //select * from t_transt_flow where fromDeviceID = deviceID and toDeviceID <> 0 and logTypeID = 0 and imsi not in (select imsi from t_transt_flow where toDeviceID = deviceID and logTypeID = 1);
        
    }
    
    private boolean containsCDMACh(DeviceOpBean bean){
        if(bean.getOp1() == 3){
            return true;
        }
        if(bean.getOp2() == 3){
            return true;
        }
        if(bean.getOp3() == 3){
            return true;
        }
        if(bean.getOp4() == 3){
            return true;
        }
        if(bean.getOp5() == 3){
            return true;
        }
        return false;
    }
    
//    public static Map<String, DeviceCache> map = new HashMap<>();
    int index = 0;
    
    public void dealTranslateCancel(String featureCode, CommonParamValueObject param){
        
        DeviceService deviceService = SpringContextHolder.getBean(DeviceService.SERVICE_NAME); 
        DeviceBean deviceBean = deviceService.queryByFeatureCode(featureCode);
//        System.out.println(deviceBean.toString());
        TranstFlowService flowService = SpringContextHolder.getBean(TranstFlowService.SERVICE_NAME); 
        List<TranstFlowBean> beans = flowService.getCancelTranslateList(deviceBean.getId());
        /*for(TranstFlowBean bean : beans){
            logger.info("需求取消翻译的流程是 ："+bean.toString());
        }*/
        
        logger.info("准备进行取消翻译流程....");
        
//        dealTranslateCancel(0, deviceService, beans, param);
        poolCenter.dealTranslateCancel(deviceService, beans, param);
        
        logger.info("取消翻译流程已完成....");
    }
    
    private void dealTranslateCancel(int index1, DeviceService deviceService, List<TranstFlowBean> beans, CommonParamValueObject param){
        index = index1;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
//                logger.info("index == "+index);
                if(index < beans.size()){
                    TranstFlowBean bean = beans.get(index++);
                    //CDMA不需要取消翻译
                    if(getOperatorByImsi(bean.getImsi()) != 3){
                        DeviceBean dBean = deviceService.get(bean.getToDeviceID());
                        packetTranslateCancelData(bean, dBean, param);
                    }
                }
            }
        }, 0, 20);
    }
    
    public void packetTranslateCancelData(TranstFlowBean bean, DeviceBean dBean, CommonParamValueObject param){
        DeviceCache dCache = deviceCacheHashMap.get(dBean.getFeatureCode());
        if(dCache != null){
            IoSession sendSession = dCache.session;
            Map<Short, CommonMonitorObject> dataMap = new HashMap<>();
            //根据imsi获取运营商编号
            int opNo = getOperatorByImsi(bean.getImsi());
            CancelUnit unit = new CancelUnit();
            unit.translateType = new Uint16(opNo);
            /*String channelType = bean.getChannelType();
            //根据运营商名称获取运营商编号
            OpInfoService service = SpringContextHolder.getBean(OpInfoService.SERVICE_NAME);
            OpInfoBean opInfoBean = service.queryByName(channelType);
            if(opInfoBean == null) return ;
            CancelUnit unit = new CancelUnit();
            unit.translateType = new Uint16(opInfoBean.getOpNo());*/
            
            //获取自增的包序号
            short packageID = CommonProtocol.raiseCommPackageID();
            param.setCommPackageID(packageID);
            
            byte[] dataXorKeys = DataEncrypt.getInstance().getDataXorKeys(dCache.encryptData);
            if(dataXorKeys == null) return;
            byte[] imsis = DataEncrypt.getInstance().getIMSIEncryptData(bean.getImsi(), packageID, dataXorKeys);
            unit.imsi = new Bytes(imsis);
            
            byte[] value = unit.toBuffer().getByte();
            short len = (short) unit.toBuffer().getByte().length;
            CommonMonitorObject commObject = new CommonMonitorObject(ProtocolConst.TRANSLATE_CANCEL, len, value);
            dataMap.put(ProtocolConst.TRANSLATE_CANCEL, commObject);
            param.setDataMap(dataMap);
            
            param.setCommandID(ProtocolConst.REQUEST);
            byte[] data = ProtocolFactory.getIntance().encode(param);
            if(data != null){
//                ThreadPoolCenter.getInstance().sendDataHaveReSend(sendSession, param, data);
                /*if(param.getCommandID() == ProtocolConst.REQUEST){
                    ThreadPoolCenter.getInstance().sendDataHaveReSend(sendSession, param, data);
                }else if(param.getCommandID() == ProtocolConst.REQUEST_ANSWER){
                    ThreadPoolCenter.getInstance().sendData(sendSession, data);
                }*/
                logger.info("取消翻译的imsi是："+bean.getImsi());
                poolCenter.sendDataUseThreads(sendSession, data);
            }
        }else{
            logger.info(String.format("取消翻译的服务器<%s>已断开连接....", dBean.getFeatureCode()));
        }
    }
   
    
    /*public void pollCenterSendData(){
        byte[] value = {52, 54, 48, 48, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 49, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        short len = 71;
        CommonMonitorObject commObject = new CommonMonitorObject(ProtocolConst.TRANSLATE_CANCEL, len, value);
        Map<Short, CommonMonitorObject> dataMap = new HashMap<>();
        dataMap.put(ProtocolConst.TRANSLATE_CANCEL, commObject);
        
        CommonParamValueObject param = new CommonParamValueObject();
        param.setCommandID(ProtocolConst.REQUEST);
        param.setAnswerID(ProtocolConst.ANSWER_FLAG);
        param.setCommPackageID((short) 123);
        param.setDataMap(dataMap);
        param.setVersionID(ProtocolConst.VERSION_V3);
        
        byte[] data = ProtocolFactory.getIntance().encode(param);
        System.out.println(Arrays.toString(data));
    }*/
    
    
    private ParamValueObject packetIdentityVerify(String featureCode, CommonParamValueObject param){
        DeviceCache dCache = deviceCacheHashMap.get(featureCode);
        if(dCache == null) return null;
        logger.info("缓存的设备信息是 == "+dCache.toString());
        Map<Short, CommonMonitorObject> dataMap = new HashMap<>();
        AuthenticationUnit unit = new AuthenticationUnit();
        byte[] authenKeys = DataEncrypt.getInstance().getTzmMD5(featureCode);
        unit.authentication = new Bytes(authenKeys);
        unit.publickey = new Bytes(dCache.publicKeyBs);
        
        //CDMA前端身份验证时，需要把分配的rand送给前端。
        if(dCache.device.getDeviceType() == 0){
            DeviceRandService randService = SpringContextHolder.getBean(DeviceRandService.SERVICE_NAME);
            DeviceRandBean randBean = randService.queryByDeviceID(dCache.device.getId());
            if(randBean != null) unit.rand = new Uint32(randBean.getRand());
        }
        
        byte[] value = unit.toBuffer().getByte();
        logger.info("身份验证数据体为 "+Arrays.toString(value));
        short len = (short) unit.toBuffer().getByte().length;
        CommonMonitorObject commObject = new CommonMonitorObject(ProtocolConst.IDENTITY_VERIFY, len, value);
                
        dataMap.put(ProtocolConst.IDENTITY_VERIFY, commObject);
        param.setDataMap(dataMap);
        //作为设备验证的应答包，使用0x6C命令编号应答
        param.setCommandID(ProtocolConst.REQUEST_ANSWER);
        return param;
    }
    
    private String getChannelType(int channelType){
        OpInfoService service = SpringContextHolder.getBean(OpInfoService.SERVICE_NAME);
        OpInfoBean bean = service.queryByNo(channelType);
        if(bean != null) return bean.getOpName();
        return "";
    }
    
   /* private IoSession getLoadInfoByTranslateType(int translateType){
        LoadInfoService loadService = SpringContextHolder.getBean(LoadInfoService.SERVICE_NAME);
        DeviceService deviceService = SpringContextHolder.getBean(DeviceService.SERVICE_NAME);
        LoadInfoBean bean = null;
        switch(translateType){
            case 0:
                bean = loadService.queryCmccTranslateRatio();
                break;
            case 1:
                bean = loadService.queryUnicomTranslateRatio();
                break;
            case 2:
                bean = loadService.queryCdmaTranslateRatio();
                break;
            default:
                break;
        }
        if(bean != null){
            DeviceBean deviceBean = deviceService.get(bean.getDeviceID());
//            logger.info("应该分配的服务器信息是 ： "+deviceBean.toString());
            DeviceCache dCache = deviceCacheHashMap.get(deviceBean.getFeatureCode());
            if(dCache != null){
                try {
                    switch(translateType){
                    case 0:
                        bean.setCmccWaitTranslates(bean.getCmccWaitTranslates()+1);
                        break;
                    case 1:
                        bean.setUnicomWaitTranslates(bean.getUnicomWaitTranslates()+1);
                        break;
                    case 2:
                        bean.setCdmaWaitTranslates(bean.getCdmaWaitTranslates()+1);
                        break;
                    default:
                        break;
                    }
                    loadService.update(bean);
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
                return dCache.session;
            }
        }else{
           return null; 
        }
        return null;
    }*/
    
    
    public ParamValueObject packetGetPhoneResultData(DeviceCache deviceCache, ImsiPhoneBean bean, CommonParamValueObject param){
        Map<Short, CommonMonitorObject> dataMap = new HashMap<>();
        GetPhoneResultUnit unit = new GetPhoneResultUnit();
        //获取自增的包序号
        short packageID = CommonProtocol.raiseCommPackageID();
        param.setCommPackageID(packageID);
        byte[] dataXorKeys = DataEncrypt.getInstance().getDataXorKeys(deviceCache.encryptData);
        byte[] imsis = DataEncrypt.getInstance().getIMSIEncryptData(bean.getImsi(), packageID, dataXorKeys);
        byte[] imeis = DataEncrypt.getInstance().getIMSIEncryptData(bean.getImei(), packageID, dataXorKeys);
        byte[] phones = DataEncrypt.getInstance().getIMSIEncryptData(bean.getPhone(), packageID, dataXorKeys);
        unit.imsi = new Bytes(imsis);
        unit.imei = new Bytes(imeis);
        unit.phone = new Bytes(phones);
        byte[] value = unit.toBuffer().getByte();
        short len = (short) unit.toBuffer().getByte().length;
        CommonMonitorObject commObject = new CommonMonitorObject(ProtocolConst.GET_PHONE_RESULT, len, value);
//        logger.info("GetPhoneResult's commObject values is "+Arrays.toString(commObject.getValue()));
        dataMap.put(ProtocolConst.GET_PHONE_RESULT, commObject);
        param.setDataMap(dataMap);
        //命令编号不修改，还是0x7C
        param.setCommandID(ProtocolConst.REQUEST);
        return param;
    }
    
    /**
     * 获得标准的电话号码
     * @param phone
     * @return
     */
    public static String getStandardPhone(String phone){
        if(phone.startsWith("+86")){
            phone = phone.substring(3);
        }
        if(phone.startsWith("86")){
            phone = phone.substring(2);
        }
        if(phone.endsWith("S")){
            phone = phone.substring(0, phone.length()-1);
        }
        return phone;
    }
    
    public static AttributionBean getPhoneAttribution(String phone){
        AttributionService attrService = SpringContextHolder.getBean(AttributionService.SERVICE_NAME);
        String str = phone.substring(0, 7);
        AttributionBean bean = attrService.queryByPhoneNO(Integer.decode(str));
        if(bean != null) return bean;
        str = phone.substring(0, 6);
        bean = attrService.queryByPhoneNO(Integer.decode(str));
        if(bean != null) return bean;
        str = phone.substring(0, 8);
        bean = attrService.queryByPhoneNO(Integer.decode(str));
        if(bean != null) return bean;
        return null;
    }
    
    
    public int getOperatorByImsi(String imsi){
        String subStr = imsi.substring(0, 5);
        switch(subStr){
            case "46000" :
            case "46002" :
            case "46007" :
            case "46020" :
                return 1;
            case "46001" :
            case "46006" :
            case "46009" :
                return 2;
            case "46003" :
            case "46011" :
                return 3;     
            case "45400" :
            case "45401" :
            case "45402" :
            case "45410" :
            case "45418" :
                return 4;
            case "45403" :
            case "45404" :
            case "45405" :
            case "45414" :    
                return 5;
            case "45406" :
            case "45415" :
            case "45417" :
                return 6;  
            case "45407" :
                return 7;
            case "45412" :
            case "45416" :
                return 8;
            case "45500" :
            case "45506" :
                return 9;     
            case "45501" :
            case "45504" :
                return 10;
            case "45503" :
            case "45505" :
                return 11;
            case "45502" :
            case "45507" :
                return 12; 
            default:
                return 0;
        }
    }
    
    
    public void dealThirdQueryAnswer(HashMap<String, String> resultMap){
        Iterator<String> it = resultMap.keySet().iterator();
        while(it.hasNext()){
            String resqText = it.next();
            //数据库第三方查询次数-1
            AccountBilling.deduckFromDB(resqText);
            HttpQueryInfo queryInfo = HttpCanstants.resqQueueMap.get(resqText);
            HttpCanstants.resqQueueMap.remove(resqText);
            HttpCanstants.waitQueryResultList.remove();
            if(queryInfo == null){
                logger.info("queryInfo is null...."); 
                continue;
            }
            
            if(queryInfo.session != null){
                CommonParamValueObject answerParam = queryInfo.param;
                CommonMonitorObject mObject = answerParam.getDataMap().get(ProtocolConst.GET_PHONE);
                GetPhoneUnit getUnit = new GetPhoneUnit(new Buffer(mObject.getValue()));
                short packageID = answerParam.getCommPackageID();
                DeviceCache dCache = getDeviceCacheByIoSession(queryInfo.session);
                if(dCache == null){
                    logger.info("请求第三方查询的手机客户端已断开连接....");
                    continue;
                }
                
                byte[] xorEncryptKeys = DataEncrypt.getInstance().getDataXorKeys(dCache.encryptData);
                ImsiPhoneBean phoneBean = new ImsiPhoneBean();
                if(resqText.startsWith("460")){   //imsi查询电话号码
                    String phone = resultMap.get(resqText);
                    byte[] phones = DataEncrypt.getInstance().getIMSIEncryptData(phone, packageID, xorEncryptKeys);
                    getUnit.phone = new Bytes(phones);
                    phoneBean.setImsi(resqText);
                    phoneBean.setPhone(phone.substring(2));
                }else{
                    if(resqText.startsWith("86")){//电话号码反查imsi
                        String imsi = resultMap.get(resqText);
                        byte[] imsis = DataEncrypt.getInstance().getIMSIEncryptData(imsi, packageID, xorEncryptKeys);
                        getUnit.imsi = new Bytes(imsis);
                        phoneBean.setImsi(imsi);
                        phoneBean.setPhone(resqText.substring(2));
                    }else{
                        logger.info("Error : 不是正常的IMSI或电话号码， 第三方查询返回的结果是："+resqText);
                        continue;
                    }
                }
                
                //将查询回来的结果写数据库，壮大数据库
                addImsiPhone(phoneBean);
                
                //写翻译流程
                byte[] values = getUnit.toBuffer().getByte();
                mObject = new CommonMonitorObject(ProtocolConst.GET_PHONE, (short) values.length, values);
                Map<Short, CommonMonitorObject> dataMap = new HashMap<>();
                dataMap.put(ProtocolConst.GET_PHONE, mObject);
                answerParam.setDataMap(dataMap);
                answerParam.setCommandID(ProtocolConst.REQUEST_ANSWER);
                byte[] data = ProtocolFactory.getIntance().encode(answerParam);
                if(data != null){
                    logger.info("正在将imsi查询电话 号码的结果返回给手机客户端："+queryInfo.session.toString()+" >>> imsi/电话号码 == "+resqText);
                    poolCenter.sendDataUseThreads(queryInfo.session, data);
                }
            }
        }
    }
    
    private void addImsiPhone(ImsiPhoneBean bean){
        bean.setRecordTime(new Date());
        AttributionBean attrBean = getPhoneAttribution(bean.getPhone());
        if(attrBean != null){
            bean.setCity(attrBean.getCity());
            bean.setProvince(attrBean.getProvince());
        }
        ImsiPhoneService phoneService = SpringContextHolder.getBean(ImsiPhoneService.SERVICE_NAME);
        try {
            phoneService.add(bean);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 删除翻译流程记录(前端首次登录和前端关闭射频的时候)
     * @param session
     */
    private void removeTranslateFlowMapBySession(IoSession session){
        Iterator<String> it = translateFlowMap.keySet().iterator();
        while(it.hasNext()){
            String key = it.next();
            if(key.startsWith(String.valueOf(session.getId()))){
                translateFlowMap.remove(key);
            }
        }
    }
    
    /**
     * 更新翻译流程记录的sessionID(前端因为网络不好，重新连接系统的时候)
     * @param oldSession
     * @param newSession
     */
    private void updateMapWhenReConnect(IoSession oldSession, IoSession newSession){
        Iterator<String> it = translateFlowMap.keySet().iterator();
        while(it.hasNext()){
            String key = it.next();
            String sessionID = String.valueOf(oldSession.getId());
            if(key.startsWith(sessionID)){
                String value = translateFlowMap.get(key);
                translateFlowMap.remove(key);
                key = key.replace(sessionID, String.valueOf(newSession.getId()));
                translateFlowMap.put(key, value);
            }
        }
    }
    
    
    private boolean isTranslatingNow(DeviceCache dCache, String imsi){
        if(dCache.session == null){
            return false;
        }
        String key = String.format("%d_%s", dCache.session.getId(), imsi);
        if(translateFlowMap.containsKey(key)){
            return true;
        }
        return false;
    }
    
    private void newTranslateFlowControll(){
        
        
    }
    
}
