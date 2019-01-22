package com.irongteng.protocol;

import java.util.List;
import com.irongteng.persistence.LoadInfoVO;
import com.irongteng.persistence.TranstFlowVO;
import com.irongteng.protocol.common.CommonParamValueObject;
import com.irongteng.service.DeviceBean;
import com.irongteng.service.DeviceService;
import com.irongteng.service.LoadInfoBean;
import com.irongteng.service.LoadInfoService;
import com.irongteng.service.TranstFlowBean;
import com.irongteng.service.TranstFlowService;

import dwz.framework.spring.SpringContextHolder;

/**
 * 翻译调度系统通讯协议与普通的通讯协议不太一样,这里特意写一个类进行数据解包和组包。
 * @author xing
 *
 */
public class ProtocolDispatch {
    
    
    
    
    public ParamValueObject packetTranslateCancel(String imsi, int channelType, CommonParamValueObject param){
        /*DeviceCache dCache = CommonParameterControl.deviceCacheMap.get(session);
        Map<Short, CommonMonitorObject> dataMap = new HashMap<>();
         unit = new IdetyverlfyUnit();
        unit.featureCode = new Bytes(dCache.featureCode, 20);
        unit.string = new Bytes(dCache.publicKeyBs);
        byte[] value = unit.toBuffer().getByte();
        short len = 90;
        CommonMonitorObject commObject = new CommonMonitorObject(ProtocolConst.IDENTITY_VERIFY, len, value);
                
        dataMap.put(ProtocolConst.IDENTITY_VERIFY, commObject);
       
        param.setDataMap(dataMap);*/
        
        //命令编号不修改，还是0x7C
        return param;
    }
    
    
    

    //取消翻译
    public void translateCancel(int deviceID){
        //查找需要取消翻译的号码(查找fromDeviceID=deviceID and toDeviceID != 0)
        //select * from t_transt_flow where fromDeviceID = 2 and toDeviceID <> 0 and logTypeID = 0 and imsi not in (select imsi from t_transt_flow where toDeviceID = 2 and logTypeID = 1);
        
    }
    
    
    public void calculateServerLoad(){
        LoadInfoService loadService = SpringContextHolder.getBean(LoadInfoService.SERVICE_NAME);
        List<LoadInfoBean> beans = loadService.search(new LoadInfoVO());
        for(LoadInfoBean bean : beans){
            
        }
    }
    
    
    public DeviceBean getByDeviceFromImsi(String imsi){
        TranstFlowService flowService = SpringContextHolder.getBean(TranstFlowService.SERVICE_NAME);
        TranstFlowVO vo = new TranstFlowVO();
        vo.setImsi(imsi);
        vo.setOrderField("recordTime");
        vo.setOrderDirection("desc");
        vo.setLogTypeID(0);
        List<TranstFlowBean> beans = flowService.search(vo);
        int deviceID = beans.get(0).getFromDeviceID();
        DeviceService deviceService = SpringContextHolder.getBean(DeviceService.SERVICE_NAME);
        DeviceBean device = deviceService.get(deviceID);
        return device;
    }
    
    
    
    
}
