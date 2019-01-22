package com.irongteng.persistence.mapper;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import com.irongteng.comm.CommConst;
import com.irongteng.control.CommonParameterControl;
import com.irongteng.persistence.TranstFlowVO;
import com.irongteng.persistence.beans.TranstFlow;
import com.irongteng.protocol.ProtocolConst;
import com.irongteng.protocol.ProtocolDispatch;
import com.irongteng.protocol.common.CommonParamValueObject;
import com.irongteng.service.DeviceBean;
import com.irongteng.service.DeviceService;
import com.irongteng.service.TranstFlowBean;
import com.irongteng.service.TranstFlowService;

import dwz.framework.junit.BaseJunitCase;
import dwz.framework.sys.exception.ServiceException;
@Rollback(false)
public class TranstFlowTest extends BaseJunitCase{
    
//    private static final Integer Integer = null;
    @Autowired
    private TranstFlowService transtFlowService;
    
    @Autowired
    private DeviceService deviceService;
    
    @Test
    public void testAdd() {
        TranstFlowBean bean = new TranstFlowBean();
        bean.setContent("ssss");
        bean.setImei("86754");
        bean.setImsi("4255435234");
        bean.setChannelType("1");
        bean.setLogTypeID(5);
        bean.setPhone("32535435r234");
        bean.setToDeviceID(2);
        bean.setFromDeviceID(9);
        bean.setFromDeviceName("马总测试前端");
        bean.setToDeviceName("你好有才");
        bean.setLogTypeID(1);
        bean.setRecordTime(new Date());
        bean.setRemark("nihaoama");
        try {
            System.out.println(transtFlowService.add(bean));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
    
    @Test 
    public void update() {
        TranstFlowBean beans = new TranstFlowBean();
        TranstFlow bean = beans.getTranstFlow();
        bean.setId(345);
        bean.setContent("dddd");
        bean.setImei("1243222222");
        bean.setImsi("1231222123212");
        bean.setChannelType("2");
        bean.setLogTypeID(4);
        bean.setPhone("131111223");
        bean.setToDeviceID(1);
        bean.setFromDeviceID(2);
        bean.setFromDeviceName("你好有才");
        bean.setToDeviceName("Test手机2");
        bean.setLogTypeID(2);
        bean.setRecordTime(new Date());
        bean.setRemark("nihaoama");
        
        try {
            transtFlowService.update(beans);  
        } catch (ServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    
    @Test
    public void testGet() {
        int id =343;
        TranstFlowBean db = transtFlowService.get(id);
        System.out.println(db.toString());
    }
    
    @Test
    public void testDelete() {
        int id = 343;
        transtFlowService.delete(id);
    }
    
    @Test
    public void testGetCancelTranslateList() {
        /*int deviceID = 41;
        List<TranstFlowBean> beans = transtFlowService.getCancelTranslateList(deviceID);
        System.out.println(beans.size());
        for(TranstFlowBean bean : beans){
            System.out.println(bean.toString());
        }*/
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("1_A", "A_1");
        map.put("2_A", "A_2");
        map.put("3_A", "A_3");
        map.put("4_A", "A_4");
        map.put("5_A", "A_5");
        Iterator<String> it1 = map.keySet().iterator();
        while(it1.hasNext()){
            String key = it1.next();
            System.out.println(key + map.get(key));
            if(key.startsWith("3")){
                String value = map.get(key);
                map.remove(key);
                key = key.replace("3", "6");
                map.put(key, value);
            }
        }
        
        Iterator<String> it2= map.keySet().iterator();
        while(it2.hasNext()){
            String key = it2.next();
            System.out.println(key + map.get(key));
            
            
        }
        
        System.out.println(map.get("1"));
        Date date1 = new Date();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Date date2 = new Date();
        System.out.println(date1.before(date2));
        
        
    }
    
    
    @Test
    public void testSearch() {
        TranstFlowVO vo = new TranstFlowVO();
        List<TranstFlowBean> beans = transtFlowService.search(vo);
        for (TranstFlowBean bean: beans) {
            System.out.println(bean.toString());
        }
    }
    
    
    @Test
    public void testSearchNum() {
        TranstFlowVO vo = new TranstFlowVO();        
        Integer num = transtFlowService.searchNum(vo);
        System.out.println("num:" + num);
    }
   
    
    @Test
    public void testGetDeviceByImsi() {
        ProtocolDispatch dispatch = new ProtocolDispatch();
        DeviceBean device = dispatch.getByDeviceFromImsi("460001234567897");
        if(device == null){
            System.out.println("device is null....");
        }else{
            System.out.println(device.toString());
        }
    }
    
    @Test
    public void testGetDevice() {
        int deviceID = 5;
        List<TranstFlowBean> beans = transtFlowService.getCancelTranslateList(deviceID);
        for(TranstFlowBean bean : beans){
            System.out.println(bean.toString());
        }
    }
    
    @Test
    public void testGetLastByImsi() {
        TranstFlowBean bean = transtFlowService.getLastByImsi("1231222123212");
        if(bean == null){
            System.out.println("bean is null....");
        }else{
            System.out.println(bean.toString());
        }
    }
    
    @Test 
    public void findAllTest(){
      List<TranstFlowBean> findAll = transtFlowService.findAll();
      System.out.println("customer findAll == "+findAll);

    }
    

    @Test
    public void testUpdateTranslateStatusByIMSI() {
        String imsi = "454075302657859";
        transtFlowService.updateTranslateStatusByIMSI(imsi);
    }
    
    @Test
    public void testUpdateTranslateStatusByDeviceID() {
        int deviceID = 14;
        transtFlowService.updateTranslateStatusByDeviceID(deviceID);
    }
    
    @Test
    public void testGetByToDeviceID() {
        int deviceID = 14;
        List<TranstFlowBean> beans = transtFlowService.getDevice(deviceID);
        List<TranstFlowBean> list = beans.subList(0, 200);
        System.out.println(list.size());
        
        CommonParamValueObject param = new CommonParamValueObject();
        param.setIp("192.168.1.239");
        param.setPort(60977);
        param.setCommCategory(CommConst.TCP);
        param.setAnswerID(ProtocolConst.ANSWER_FLAG);
        param.setVersionID(ProtocolConst.VERSION_V3);
        dealTranslateCancel(0, deviceService, list, param);
    }
    
    int index = 0;
    private void dealTranslateCancel(int index1, DeviceService deviceService, List<TranstFlowBean> beans, CommonParamValueObject param){
        index = index1;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
//                logger.info("index == "+index);
                if(index < beans.size()){
                    TranstFlowBean bean = beans.get(index++);
                    DeviceBean dBean = deviceService.get(bean.getToDeviceID());
                    new CommonParameterControl().packetTranslateCancelData(bean, dBean, param);
                }
            }
        }, 0, 20);
    }

}