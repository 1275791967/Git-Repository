package com.irongteng.protocol;

import org.junit.Test;

import com.irongteng.control.CommonParameterControl;
import com.irongteng.protocol.common.CommonParamValueObject;
import com.irongteng.service.ImsiPhoneBean;


public class CommonParameterTest {
	
    @Test
    public void testParamRFoffNotifyProcessor() {
        /*for(int i = 0;i<20;i++){
            DeviceCache cache = new DeviceCache();
            if(String.valueOf(i).length() == 1){
                cache.featureCode = "8726213612730"+i;
            }else if(String.valueOf(i).length() == 2){
                cache.featureCode = "872621361273"+i;
            }
            CommonParameterControl.map.put(""+i, cache);
        }
        
        CommonParameterControl controller = new CommonParameterControl();
        controller.dealTranslateCancel("87262136127312");*/
        
        /*CommonParameterControl controller = new CommonParameterControl();
        controller.pollCenterSendData();*/
        
    }
    
    @Test
    public void testPacketGetPhoneResultData() {
        CommonParamValueObject param = new CommonParamValueObject();
        param.setAnswerID(ProtocolConst.ANSWER_FLAG);
        param.setCommPackageID((short) 123);
        param.setVersionID(ProtocolConst.VERSION_V3);
        param.setCommandID(ProtocolConst.REQUEST);
        
        ImsiPhoneBean bean = new ImsiPhoneBean();
        bean.setImei("863231234567890");
        bean.setImsi("460001234567890");
        bean.setPhone("13412345678");
        CommonParameterControl controller = new CommonParameterControl();
//        ParamValueObject param1 = controller.packetGetPhoneResultData(bean, param);
//        byte[] data = ProtocolFactory.getIntance().encode(param1);
//        System.out.println(Arrays.toString(data));
    }
	
}
