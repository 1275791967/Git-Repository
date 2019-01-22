package com.irongteng.ipms.pool;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.serial.SerialAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irongteng.comm.CommClient;
import com.irongteng.comm.CommConst;
import com.irongteng.comm.CommModeStatus;
import com.irongteng.comm.client.TcpCommClient;
import com.irongteng.comm.client.UdpCommClient;
import com.irongteng.comm.listener.ReceiveDataListener;
import com.irongteng.comm.listener.SendDataListener;
import com.irongteng.control.CommonParameterControl;
import com.irongteng.control.DeviceCache;
import com.irongteng.protocol.ParamValueObject;
import com.irongteng.protocol.ProtocolConst;
import com.irongteng.protocol.ProtocolFactory;
import com.irongteng.protocol.common.CommonMonitorObject;
import com.irongteng.protocol.common.CommonParamValueObject;
import com.irongteng.protocol.common.FddParamValueObject;
import com.irongteng.protocol.common.LTEParamValueObject;
import com.irongteng.service.DeviceBean;
import com.irongteng.service.DeviceService;
import com.irongteng.service.LoadOPService;
import com.irongteng.service.TranstFlowBean;
import com.irongteng.thirdinterface.HttpCanstants;
import com.irongteng.thirdinterface.ThirdRequest;

import dwz.common.util.StringUtils;
import dwz.common.util.time.DateUtils;
import dwz.framework.spring.SpringContextHolder;
import dwz.framework.sys.exception.ServiceException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class ThreadPoolCenter implements ReceiveDataListener, SendDataListener{
    
    private final Logger logger = LoggerFactory.getLogger(ThreadPoolCenter.class);
    
    public static final Map<Long, IoSession> SESSIONS = new HashMap<>();
    
    private final Map<Short, ParamValueObject> paramValueObjectQueue = new LinkedHashMap<>(); 
    
    private ThreadPoolExecutor executor;

    private static final ThreadPoolCenter INSTANCE = new ThreadPoolCenter();
    
    private Date lastRecv = null;
    
    private ConcurrentLinkedQueue<SendBuffer> sendList = new ConcurrentLinkedQueue<>();
    
    private ConcurrentHashMap<String, Integer> queryCountMap = new ConcurrentHashMap<>();
    
    private Timer sendTimer = null;
    private TimerTask sendTask = null;
    
    private ThreadPoolCenter(){}
    
    public static ThreadPoolCenter getInstance() {
        return INSTANCE;
    }
    
    public void startService() {
        if (executor != null) {
            executor.shutdown();
            executor = null;
        }
        executor = new ThreadPoolExecutor(30, 600, 1, TimeUnit.DAYS, new LinkedBlockingQueue<>(30));
        
//        startSendTimer();
        clearLoadOP();
//        new Thread(new SendDataThread()).start();
        initHttpResquest();
        
    }
    
    /**
     * 清空服务站负载效率
     */
    private void clearLoadOP(){
        LoadOPService service = SpringContextHolder.getBean(LoadOPService.SERVICE_NAME);
        service.clear();
    }
    
    /*private void startSendTimer(){
        if(sendTimer == null){
            sendTimer = new Timer();
        }
        if(sendTask == null){
            sendTask = new TimerTask() {
                @Override
                public void run() {
                    if(!sendList.isEmpty()){
                        SendBuffer sendBuffer = sendList.poll();
                        sendData(sendBuffer.session, sendBuffer.data);
                    }
                }
            };
        }
        sendTimer.schedule(sendTask, 0, 10);
    }
    
    private void stopSendTimer(){
        if(sendTask != null){
            sendTask.cancel();
            sendTask = null;
        }
        if(sendTimer != null){
            sendTimer.cancel();
            sendTimer = null;
        }
    }
    
    public void stopService() {
        executor.shutdown();
        stopSendTimer();
    }*/
    
    public void stopService() {
        executor.shutdown();
    }
    
    /**
     * 获取参数值对象
     * 
     * @param pkgID
     *         包序号
     * @return
     */
    public ParamValueObject getParamObject(Short pkgID) {
        return paramValueObjectQueue.get(pkgID);
    }
    
    /**
     * 查询参数值对象是否存在
     * 
     * @param pkgID
     *         包序号
     * @return
     */
    public boolean containsParamObject(Short pkgID) {
        return paramValueObjectQueue.containsKey(pkgID);
    }
    
    /**
     * 删除参数值对象
     * 
     * @param pkgID
     *         包序号
     */
    public void removeParamObject(Short pkgID) {
        paramValueObjectQueue.remove(pkgID);
    }
    
    /**
     * 添加参数值对象
     * 
     * @param param 监控设备参数对象
     * @throws dwz.framework.sys.exception.ServiceException
     */
    public void addParamObject(ParamValueObject param)  throws ServiceException {
    	
    	if (param == null) throw new ServiceException("null param object");
    	
       /* DeviceService deviceService = SpringContextHolder.getBean(DeviceService.SERVICE_NAME);
        DeviceBean device = null;
        Integer deviceID = param.getDeviceID();
        Integer deviceNumber = param.getDeviceNumber();
        String deviceName = param.getDeviceName();
        String cityCode = param.getCityCode();
        
        if (deviceID != null && deviceID > 0) {
            device = deviceService.get(deviceID);
        } else if (deviceNumber!=null && deviceNumber > 0 ) {
            device = deviceService.getBySiteNumber(Long.toString(deviceNumber&0xFFFFFFFFL));
        } else if (deviceName != null && cityCode != null) {
            DeviceBean bean = new DeviceBean();
            bean.setDeviceNumber(cityCode);
            bean.setAttachNumber(deviceName);
            device = deviceService.get(device);
        }
        
        if (device != null) {
            //设置通信信息
            param.setCommCategory(device.getCommCategory());
            param.setIp(device.getIpAddress());
            param.setPort(device.getPort());
            //设置基本信息
            param.setDeviceNumber((int)Long.parseLong(device.getSiteNumber())); //设备编号
            param.setCityCode(device.getDeviceNumber());  //城市代码
            param.setDeviceName(device.getAttachNumber());  //设备名称
            //对参数对象按照类型选择协议进行编码
        }*/
        //获取session
        IoSession session = getIoSession(param);
        //向程序执行池发送数据
        addParamObject(session, param);
    }
    
    /**
     * 添加参数值对象
     * 
     * @param session
     * @param param 监控设备参数对象
     * @throws dwz.framework.sys.exception.ServiceException
     */
    public void addParamObject(IoSession session, ParamValueObject param)  throws ServiceException {
    	
    	if (param == null) throw new ServiceException("null param object");
    	if (session == null && param.getCommCategory() == CommConst.TCP) throw new ServiceException("null IoSession");
    	//调用session中保存的协议版本信息
    	if (session != null && session.containsAttribute("versionID") && param instanceof CommonParamValueObject) {
            byte versionID = (byte) session.getAttribute("versionID");
            CommonParamValueObject commParam = (CommonParamValueObject) param;
            commParam.setVersionID(versionID);
            param = commParam;
    	}
    	
    	ProtocolFactory factory = ProtocolFactory.getIntance();
        //协议编码程序
        byte[] data = factory.encode(param);
        String parseMsg = factory.getParseMessage();
        if (parseMsg != null) {
            logger.error(parseMsg);
            throw new ServiceException(parseMsg);
        }
        //将合法对象加入程序执行队列
        /*ParameterExecutor parameterExecutor = new ParameterExecutor(session, param, data);
        executor.execute(parameterExecutor);*/
    }
    
    /**
     * 发送数据
     * 
     * @param session
     * @return 
     */
    @Override
    public boolean sendData(IoSession session, byte[] data) {
        
        if (session!=null && session.isConnected()) {
            
            WriteFuture writeFuture = session.write(IoBuffer.wrap(data));
            try {
                writeFuture.awaitUninterruptibly(500);
                if(writeFuture.isWritten()) {
                    /*String addr = ((InetSocketAddress)session.getRemoteAddress()).getAddress().getHostAddress();
                    String.format("send to (%s) %s", addr, StringUtils.bytes2HexString(data));*/
                    logger.info("SENT TO :" + session.toString() +" >>> "+ StringUtils.bytes2HexString(data));
                    return true;
                }
            } catch (Exception ex) {
                logger.info(ex.getMessage());
            }
            
            logger.info("SENT FAIL:" + StringUtils.bytes2HexString(data));
            return false;
        }
        
        if(session == null){
            logger.info("sesion is null....");
        }else{
            if(!session.isConnected()) logger.info(session.toString()+" disconnected....");
        }
        return false;
    }
    
    /**
     * 客户端模式发送数据
     * @param param
     * @param data
     * @return 
     */
    public boolean sendDataClient(ParamValueObject param, byte[] data) {
        CommClient client = null;
        SocketAddress sokcet = null;
        if (param.getCommCategory() == CommConst.TCP) {
            client = new TcpCommClient();
            sokcet = new InetSocketAddress(param.getIp(), param.getPort());
        } else if (param.getCommCategory() == CommConst.UDP) {
            client = new UdpCommClient();
            sokcet = new InetSocketAddress(param.getIp(), param.getPort());
        }

        if (client != null && sokcet != null) {
            client.connect(sokcet);
            client.send(data);
            return true;
        }
        return false;
    }
    
    /**
     * 接收数据
     * 
     * @param session 通信会话
     * @param message  要发送消息对象
     * @param commModeStatus  通信类型
     */
    @Override
    public void receiveData(IoSession session, Object message, CommModeStatus commModeStatus) {
        
        if (message instanceof IoBuffer) {
            IoBuffer buffer = (IoBuffer) message;
            byte[] data = new byte[buffer.limit()];
            buffer.get(data);
            buffer.flip();
            
            logger.info("RECEIVED:" + StringUtils.bytes2HexString(data));
            
            String ipAddress = null; 
            Integer port = null; 
            ParamValueObject answerParam = null;
            SocketAddress sa = session.getRemoteAddress();
            
            if (sa instanceof InetSocketAddress) {
                InetSocketAddress socketAddress = (InetSocketAddress) sa;
                
                ipAddress = socketAddress.getAddress().getHostAddress();
                port = socketAddress.getPort();
//                System.out.println(ipAddress + ":" + port);
            } else if (sa instanceof SerialAddress) {
                SerialAddress socketAddress = (SerialAddress) session.getRemoteAddress();
                
                String serialName = socketAddress.getName();
                int bauds = socketAddress.getBauds();
//                System.out.println(serialName + ":" + bauds);
            }
            
            ParamValueObject  param = null;
           
            if (null != commModeStatus) switch (commModeStatus) {
                case TCP:
                case UDP:
                case MODEM:{
                    param = new CommonParamValueObject();
                    param.setIp(ipAddress);
                    param.setPort(port);
                    switch (commModeStatus) {
                        case TCP:
                            param.setCommCategory(CommConst.TCP);
                            break;
                        case UDP:
                            param.setCommCategory(CommConst.UDP);
                            break;
                        default:
                            param.setCommCategory(CommConst.MODEM);
                            break;
                    }
                    logger.info(param == null ? "param is null" : "RECEIVED: IP地址"+param.getIp()+"，端口号"+param.getPort());
                    break;
                    }
                case UDP_FDD:{
                    param = new FddParamValueObject();
                    param.setCategoryID(CommConst.HP_FDDLTE);
                    param.setCommCategory(CommConst.UDP);
                    param.setIp(ipAddress);
                    param.setPort(port);
                    param.setCityCode("0");
                    param.setDeviceName("FDD上报设备_" + param.getIp());
                    break;
                    }
                case UDP_LTE:{
                    param = new LTEParamValueObject();
                    param.setCategoryID(CommConst.HP_TDLTE);
                    param.setCommCategory(CommConst.UDP);
                    param.setIp(ipAddress);
                    param.setPort(port);
                    param.setCityCode("0");
                    param.setDeviceName("LTE上报设备_" + param.getIp());
                    break;
                    }
                default:
                    break;
            }
            
            try {
                if (param != null) {
                    ProtocolFactory factory = ProtocolFactory.getIntance();
                    
                    boolean parseResult = factory.decode(data, param);
//                    logger.info("数据解析结果为："+parseResult);
                    if (!parseResult) {
                        logger.error("协议解析错误:" + factory.getParseMessage());
                        //删除session
//                        if (this.containsIoSession(session)) this.removeIoSession(session);
                        session.closeNow();
                    } else {
                        //协议解析成功，给设备应答
                        if (param instanceof CommonParamValueObject) {
                            //写日志
                           /* DeviceService deviceService = SpringContextHolder.getBean(DeviceService.SERVICE_NAME); 
                            String deviceIP = ((InetSocketAddress)sa).getAddress().getHostAddress();
                            DeviceBean deviceBean = deviceService.getByIP(deviceIP);
                            
                            if(deviceBean != null){
                                CommLogControl.getInstance().writeLog(deviceBean.getId(), (CommonParamValueObject)param);
                            }else{
                                //设备验验证可能为空，因为设备出厂时不会录入设备IP
                                logger.info("deviceBean is null ");
                            }*/
                            
                            int commandID = ((CommonParamValueObject) param).getCommandID();
                            
                            //处理多个数据包同时上来
                           /* Date now = new Date();
                            if(lastRecv != null && (lastRecv.getTime() == now.getTime())) Thread.sleep(20);
                            lastRecv = now;*/
                            //处理接收的数据
                            answerParam = paramValueObjectReceiver(session, param);
                            
                            if (answerParam != null) {
                                byte[] answerData = factory.encode(answerParam);
                                //添加到数据发送池
                                sendDataUseThreads(session,  answerData);
//                                sendDataHaveReSend(session, answerParam, answerData);
                            }
                            
                            if(commandID == ProtocolConst.REQUEST){
                                new CommonParameterControl().commonParamValueObjectReceiver(session, (CommonParamValueObject) param);
                            }
                        }else{
                            logger.info("param not instanceof CommonParamValueObject");
                        }
                        /*//如果协议解析成功，则添加session到ioSessionMaps.put(session.getId(), session);
                        if (!this.containsIoSession(session)) this.addIoSession(session);
                        
                        //将数据发送到接收器
                        if (param instanceof CommonParamValueObject) {
                            //如果是上报数据类型，则要查询城市代码和设备名称信息
                            CommonParamValueObject common = (CommonParamValueObject)param;
                            byte commandID = common.getCommandID();
                            //如果不是下面几个命令时，才保存设备编号、城市代码、设备名称等属性值
                            this.setSessionAttributes(session, param);
                            
                            answerParam = paramValueObjectReceiver(session, param);
                            
                        } else {
                            //保存session属性值
                            this.setSessionAttributes(session, param);
                            
                            answerParam = paramValueObjectReceiver(session, param);                   
                        }
                        
                        if (answerParam != null) {
                            byte[] answerData = factory.encode(answerParam);
                            //添加到数据发送池
                            sendData(session,  answerData);
                        }*/
                    }
                }
            }catch(Exception e){
                logger.info(e.getMessage());
                e.printStackTrace();
            } finally {
                //保存session属性值
//                if (param != null) this.setSessionAttributes(session, param);
                
            }
        }
    }
    
    
    
    /**
     * 通用参数值对象接收器管理
     * 
     * @param param  参数值对象父类
     * @return 
     */
    /*public ParamValueObject paramValueObjectReceiver(IoSession session, ParamValueObject param) {
        ParamValueObject answerData = null;
        if (param instanceof CommonParamValueObject) {
            return new CommonParameterControl().commonParamValueObjectReceiver(session, (CommonParamValueObject)param);
        }
        return answerData;
    }*/
    
    public ParamValueObject paramValueObjectReceiver(IoSession session, ParamValueObject param) {
        CommonParamValueObject commParam = (CommonParamValueObject) param;
        //命令编号
        byte commandID = commParam.getCommandID();
        ParamValueObject answerParam = null;
        Map<Short, CommonMonitorObject> dataMap = ((CommonParamValueObject)param).getDataMap();
        Iterator<Short> it =dataMap.keySet().iterator();
        while(it.hasNext()){
            Short paramID = it.next();
            //取消翻译的响应要做响应的处理
            if(commandID == ProtocolConst.REQUEST_ANSWER && paramID != ProtocolConst.TRANSLATE_CANCEL){
                return null;
            }
            
            if((paramID == ProtocolConst.GET_PHONE) || (paramID == ProtocolConst.GET_PHONE_RESULT) || 
                    (paramID == ProtocolConst.SET_LATITUDE_LONGITUDE) || (paramID == ProtocolConst.GSM_AUTHENT) || 
                    (paramID == ProtocolConst.GSM_AUTHENT_RESULT) || (paramID == ProtocolConst.TRANSLATE_LOAD_REPORT) ||
                    (paramID == ProtocolConst.RF_OFF_NOTIFY) ){
                DeviceCache dCache = new CommonParameterControl().getDeviceCacheByIoSession(session);
                if(dCache == null){
                    logger.info("设备未进行设备、身份验证 。。。。");
                    return null;
                }
                if(!dCache.verifySuccess){
                    logger.info("设备身份未验证成功，不能进行相关操作!!!!");
                    return null;
                }
            }
            
            CommonMonitorObject mObject = dataMap.get(paramID);
            switch(paramID) {
                case ProtocolConst.GET_PHONE_RESULT:                //电话号码结果
                    if(mObject.getLength() != 114) return null;
                    break;
                case ProtocolConst.SET_LATITUDE_LONGITUDE:          //设置经纬度
                    if(mObject.getLength() != 90) return null;
                    break;
                case ProtocolConst.IDENTITY_VERIFY_RESULT:         //身份验证结果
                    //身份验证不需要回数据
                    return null;
                case ProtocolConst.GSM_AUTHENT:                    //GSM鉴权
                    if(mObject.getLength() != 88) return null;
                    break;
                case ProtocolConst.GSM_AUTHENT_RESULT:            //GSM鉴权结果
                    if(mObject.getLength() != 74) return null;
                    break;
                case ProtocolConst.TRANSLATE_LOAD_REPORT:         //翻译负载信息上报
                    if(mObject.getLength() != 74) return null;
                    break;
                case ProtocolConst.RF_OFF_NOTIFY:                 //射频关闭通知
                    if(mObject.getLength() != 50) return null;
                    break;
                case ProtocolConst.TRANSLATE_CANCEL:              //取消翻译响应
                    if(mObject.getLength() != 72){
                        logger.info("数据长度有误：取消翻译正确的长度为72，但解析数据体长度为："+mObject.getLength());
                    }
                    new CommonParameterControl().specialParamValueObjectReceiver(session, (CommonParamValueObject)param);
                    return null;
                case ProtocolConst.GET_PHONE:                     //获取电话号码
                    if(mObject.getLength() != 161) return null;
                    return new CommonParameterControl().specialParamValueObjectReceiver(session, (CommonParamValueObject)param);
                case ProtocolConst.DEVICE_VERIFY:                 //设备验证
//                    logger.info("设备验证数据体为："+Arrays.toString(mObject.getValue()));
                    if(mObject.getLength() != 88){ 
                        logger.info("设备验证数据体长度为："+mObject.getLength());
                        return null;
                    }
                    return new CommonParameterControl().specialParamValueObjectReceiver(session, (CommonParamValueObject)param);
                case ProtocolConst.ACCOUNT_LOGIN:                 //账户登录
                    if(mObject.getLength() != 90) return null;
                    return new CommonParameterControl().specialParamValueObjectReceiver(session, (CommonParamValueObject)param);
                default:
                    break;
            }
        }
        
        commParam.setCommandID(ProtocolConst.REQUEST_ANSWER);
        answerParam = commParam;
        return answerParam;
    }
    
    /*public void sendDataHaveReSend(IoSession session, ParatfmValueObject param, byte[] data){
        param.setEndDate(null);
        ParameterExecutor parameterExecutor = new ParameterExecutor(session, param, data);
        executor.execute(parameterExecutor);
    }*/
    
    /**
     * 使用多线程发送数据
     * @param session
     * @param data
     */
    public void sendDataUseThreads(IoSession session, byte[] data){
        /*SendDataExecutor sendDataExecutor = new SendDataExecutor(session, data);
        executor.execute(sendDataExecutor);*/
        
       /* SendBuffer sendBuffer = new SendBuffer();
        sendBuffer.session = session;
        sendBuffer.data = data;
        sendList.offer(sendBuffer);*/
        
        sendData(session, data);
    }
    
    
    
    /**
     * 判断上报信息中是否有非0的设备编号，如果是0则查询设备名称和城市代码
     * 
     * @param session
     * @param param
     * @return 
     */
    private boolean isContainAttributes(IoSession session, CommonParamValueObject  param) {
        
        Integer deviceNumber = param.getDeviceNumber();
        
        if (deviceNumber!=null && deviceNumber !=0) return true;
        
        if (!session.containsAttribute("cityCode") && !session.containsAttribute("deviceName")) {
            if (session.containsAttribute("cityCode") && session.containsAttribute("deviceName")) {
                param.setCityCode((String) session.getAttribute("cityCode"));
                param.setDeviceName((String) session.getAttribute("deviceName"));
                return true;
            }
        }
        return false;
    }
    
    public void setSessionAttributes(IoSession session, ParamValueObject param) {
        try {
            Integer siteNumber = param.getDeviceNumber(); //设备编号
            String deviceName = param.getDeviceName();    //设备名称
            String cityCode = param.getCityCode();        //城市代码
            
            Integer oldSiteNumber = null; String oldDeviceName = null; String oldCityCode = null;
            //设备编号判断
            if (siteNumber != null) {
                if (!siteNumber.equals(session.getAttribute("siteNumber"))) {
                    if (session.containsAttribute("siteNumber")) oldSiteNumber = (Integer) session.getAttribute("siteNumber");
                    session.setAttribute("siteNumber", siteNumber);
                }
            }
            //设备名称属性判断
            if (deviceName != null) {
                if (!deviceName.equals(session.getAttribute("deviceName"))) {
                    if (session.containsAttribute("deviceName")) oldDeviceName = (String) session.getAttribute("deviceName");
                    session.setAttribute("deviceName", deviceName);
                }
            } else {
                if (session.containsAttribute("deviceName")) param.setDeviceName((String) session.getAttribute("deviceName"));
            }
            //城市代码属性判断
            if (cityCode != null) {
                if (!cityCode.equals(session.getAttribute("cityCode"))) {
                    if (session.containsAttribute("cityCode")) oldCityCode = (String) session.getAttribute("cityCode");
                    session.setAttribute("cityCode", cityCode);
                }
            } else {
                if (session.containsAttribute("cityCode")) param.setCityCode((String) session.getAttribute("cityCode"));
            }
            //如果设备编号、设备名称或城市代码状态发生改变，则对设备信息进行更新
            
            //判断通用协议版本信息
            if (param instanceof CommonParamValueObject) {
            	CommonParamValueObject commonParam = (CommonParamValueObject) param;
            	byte versionID = commonParam.getVersionID();
                session.setAttribute("versionID", versionID);
            }
            //如果设备编号为0，设备名称和城市代码为空，且session中不包含deviceName和cityCode字段，那么则发送城市代码和设备名称参数查询请求
            if ((siteNumber==null || siteNumber == 0) && deviceName == null && cityCode == null 
                    && !session.containsAttribute("deviceName") && !session.containsAttribute("cityCode")) {
                
                if (session.containsAttribute("cityCode") && session.containsAttribute("deviceName")) {
                    param.setCityCode((String) session.getAttribute("cityCode"));
                    param.setDeviceName((String) session.getAttribute("deviceName"));
                }
            }
        } catch(Exception e) {
            logger.error(e.getMessage());
        }
    }
    
    /**
     * 开启 session
     * 
     * @param session
     */
    @Override
    public void openIoSession(IoSession session) {
        //当session开启后，2秒后启动设备名称、城市代码、设备编号查询
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    
                    CommonParamValueObject param = new CommonParamValueObject();
                    //session.write(IoBuffer.wrap(pkgNameAndCityNumber(param)));
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                }
            }
        }.start();
    }
    
    /**
     * 添加 session
     * 
     * @param session
     * @return 
     */
    @Override
    public boolean containsIoSession(IoSession session) {
        if (session==null) return false;
        return SESSIONS.containsKey(session.getId());
    }
    
    /**
     * 添加 session
     * 
     * @param session
     */
    @Override
    public synchronized void addIoSession(IoSession session) {
        SESSIONS.put(session.getId(), session);
    }
    /**
     * 删除 session
     * 
     * @param session
     */
    @Override
    public synchronized void removeIoSession(IoSession session) {
        SESSIONS.remove(session.getId());
        //删除deviceCacheHashMap中session的相关缓存
        DeviceCache dCache = new CommonParameterControl().getDeviceCacheByIoSession(session);
        logger.info("删除 的session是："+session.toString());
        if(dCache != null){
            logger.info("删除连接的设备的特征码是："+dCache.featureCode); 
            CommonParameterControl.deviceCacheHashMap.remove(dCache.featureCode);
            //删除服务器负载效率
            if(dCache.device.getDeviceType() == 1){
                LoadOPService service = SpringContextHolder.getBean(LoadOPService.SERVICE_NAME);
                service.deleteByDeviceID(dCache.device.getId());
            }
        }
    }
    
    /**
     * 根据查询信息，获取IoSession
     * 
     * @param param
     * @return
     */
    public IoSession getIoSession(ParamValueObject param) {
        if (SESSIONS.size()>0) {
            Collection<IoSession> sessions = SESSIONS.values();
            Integer siteNumber = param.getDeviceNumber();
            String deviceName = param.getDeviceName();
            String cityCode = param.getCityCode();
            
            if (siteNumber != null && siteNumber > 0) {
                for (IoSession session: sessions) {
                    if (siteNumber.equals(session.getAttribute("siteNumber"))) return session;
                }
            } else if (deviceName != null && cityCode != null ) {
                for (IoSession session: sessions) {
                    if (deviceName.equals(session.getAttribute("deviceName")) && cityCode.equals(session.getAttribute("cityCode"))) return session;
                }
            }
        }
        return null;
    }
    
    
    /**
     * 中心与网管交互任务
     * 
     * @author lvlei
     *
     */
    private class ParameterExecutor implements Runnable, Serializable {

        private static final long serialVersionUID = 134433L;
        
        private final Logger logger = LoggerFactory.getLogger(ParameterExecutor.class);
        
        private final IoSession session;
        private final ParamValueObject param;
        private final byte[] data;
        
        public ParameterExecutor(IoSession session, ParamValueObject param, byte[] data) {
            this.session = session;
            this.param = param;
            this.data = data;
        }
        
        @Override
        public void run() {
            //将参数对象加入队列
            paramValueObjectQueue.put(param.getCommPackageID(), param);
            
            try {
            	param.setStartDate(new Date());
                while (!param.getEndTag()) {
                    //发送数据
                    if (param.getEndDate()==null || (DateUtils.delaySecond(-30).compareTo(param.getEndDate()) >= 0 
                            && param.getSendCount() < param.getMaxSendCount()
                            )) { //延迟时间超过30秒后，再次发送
                        if (session!=null && session.isConnected()) { //建立连接被动发送
                            //向设备端发送数据
                            sendData(session, data);
                        }
                        param.setEndDate(new Date());
                        param.setSendCount(param.getSendCount()+1);
                    } else if (DateUtils.delaySecond(-150).compareTo(param.getStartDate()) >= 0 || param.getSendCount() >= param.getMaxSendCount()) {
                        param.setEndTag(true);
                    }
                }
            } catch (NumberFormatException e) {
                logger.error(e.getMessage());
            } finally {
                Short pkgID = param.getCommPackageID();
                //删除该交互数据对象
                if (pkgID!=null) removeParamObject(pkgID);
            }
        }
    }
    
    private class SendDataExecutor implements Runnable, Serializable {

        private static final long serialVersionUID = 134432L;
        
        private final Logger logger = LoggerFactory.getLogger(ParameterExecutor.class);
        
        private final IoSession session;
        private final byte[] data;
        
        public SendDataExecutor(IoSession session, byte[] data) {
            this.session = session;
            this.data = data;
        }
        
        @Override
        public void run() {
            try {
                if (session!=null && session.isConnected()) {
                    //向设备端发送数据
                    sendData(session, data);
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            } 
        }
    }
    
    private class SendDataThread implements Runnable, Serializable {
        @Override
        public void run() {
            while(true){
                sendTCPData();
            }
        }
    }
    
    private void sendTCPData(){
        if(!sendList.isEmpty()){
            SendBuffer sendBuffer = sendList.poll();
            if(sendData(sendBuffer.session, sendBuffer.data)){
                sendTCPData();
            }
        }
    }
    
    
    private void timingCheckVerifySuccess(){
        Iterator<String> it = CommonParameterControl.deviceCacheHashMap.keySet().iterator();
        while(it.hasNext()){
            String featureCode = it.next();
            DeviceCache dCache = CommonParameterControl.deviceCacheHashMap.get(featureCode);
            if(!dCache.verifySuccess){
                if(DateUtils.delayMinute(1).compareTo(dCache.verifyDate) >= 0){
                    dCache.session.closeNow();
                }
            }
        }
    }
    
    private void initHttpResquest(){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    getAPIToken();
                    HttpCanstants.remain_count = new ThirdRequest().remain();
                    if(HttpCanstants.remain_count > 0){
                        requestThird();
                        getThirdQueryResult();
                    }else{
                        logger.info("查询剩余次数不足,需要进行充值....");
                        Thread.sleep(60*1000);
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    getAPIToken();
                    HttpCanstants.remain_count = new ThirdRequest().remain();
                    if(HttpCanstants.remain_count > 0){
                        new Thread(new HttpThreadPool()).start();
                        new Thread(new GetQueryResultThreadPool()).start();
                    }else{
                        logger.info("查询剩余次数不足,需要进行充值....");
                        Thread.sleep(60*1000);
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();*/
    }
    
    private void requestThird(){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true){
                        //为了防止一直占用CPU资源，需要sleep一会
                        Thread.sleep(200);
                        if(HttpCanstants.remain_count > 0){
                            if(!HttpCanstants.resqList.isEmpty()){
                                if(new ThirdRequest().query() > 0){
                                    Thread.sleep(5*1000);
                                }
                                new ThirdRequest().input();
                            }
                        }else{
                            logger.info("查询剩余次数不足,需要进行充值....");
                            Thread.sleep(60*1000);
                        }
                    }
                    /*if(HttpCanstants.remain_count > 0){
                        if(new ThirdRequest().query() > 0){
                            Thread.sleep(5*1000);
                        }
                        new ThirdRequest().inputOne(resqText);
                    }else{
                        logger.info("查询剩余次数不足,需要进行充值....");
                        Thread.sleep(60*1000);
                    }*/
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }
    
    private void getThirdQueryResult(){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true){
                        //为了防止一直占用CPU资源，需要sleep一会
                        Thread.sleep(200);
                        if(HttpCanstants.remain_count > 0){
                            if(HttpCanstants.waitQueryResultList.size() > 0){
                                String resq = HttpCanstants.waitQueryResultList.peek();
                                int count = 0;
                                if(queryCountMap.containsKey(resq)){
                                    count = queryCountMap.get(resq);
                                    queryCountMap.remove(resq);
                                }
                                queryCountMap.put(resq, ++count);
                                //查询30次都没有返回结果，认为第三方库不存在该imsi/电话号码，从等待查询列表中移除
                                if(count >= 30){   
                                    HttpCanstants.waitQueryResultList.remove(resq);
                                    queryCountMap.remove(resq);
                                }
                                
                                Thread.sleep(2*1000);
                                int waitCount = new ThirdRequest().query();
                                if(waitCount != 0 ){
                                    Thread.sleep(10*1000);
                                }
                                new ThirdRequest().output();
                            }
                        }else{
                            logger.info("查询剩余次数不足,需要进行充值....");
                            Thread.sleep(60*1000);
                        }
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }
    
    private void getAPIToken(){
        HttpCanstants.auth_bearer = new ThirdRequest().getapitoken();
        logger.info("获取的令牌为 "+HttpCanstants.auth_bearer);
        if(HttpCanstants.auth_bearer != null){
            //获取令牌后，等待15秒后，才可以进行其他操作
            try {
                Thread.sleep(15*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else{
            
        }
    }
    
    public void dealTranslateCancel(DeviceService deviceService, List<TranstFlowBean> beans, CommonParamValueObject param){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    for(TranstFlowBean bean : beans){
                        //CDMA不需要取消翻译
                        if(new CommonParameterControl().getOperatorByImsi(bean.getImsi()) != 3){
                            DeviceBean dBean = deviceService.get(bean.getToDeviceID());
                            new CommonParameterControl().packetTranslateCancelData(bean, dBean, param);
                            Thread.sleep(30);
                        }
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }
    
}

