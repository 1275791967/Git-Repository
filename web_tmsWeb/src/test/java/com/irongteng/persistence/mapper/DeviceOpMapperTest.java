package com.irongteng.persistence.mapper;


import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.irongteng.persistence.beans.Device;
import com.irongteng.service.DeviceOpBean;
import com.irongteng.service.DeviceOpService;
import com.irongteng.service.DeviceService;

import dwz.framework.junit.BaseJunitCase;
import dwz.framework.sys.exception.ServiceException;
@Rollback(false)
public class DeviceOpMapperTest extends BaseJunitCase{
    @Autowired  
    private DeviceOpService deviceOpService;  
    @Autowired  
    private DeviceService deviceService;  
    
    @Test 
    public void Testinsert() {
        DeviceOpBean bean = new DeviceOpBean();
        Device device = deviceService.get(9).getDevice();
        bean.getDeviceOp().setDevice(device);
        bean.setOp1(2);
        bean.setOp2(0);
        bean.setOp3(0);
        bean.setOp4(0);
        bean.setOp5(0);
        try {
            int id = deviceOpService.add(bean);
            System.out.println("customer iD == "+id);
        } catch (ServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @Test 
    public void TestQueryAll() {
        List<DeviceOpBean> beans = deviceOpService.queryAll();
        for(DeviceOpBean bean : beans){
            System.out.println(bean.toString());
        }
    }
    
    @Test 
    public void TestQueryByOp() {
        /*List<DeviceOpBean> beans = deviceOpService.queryByOp(2);
        for(DeviceOpBean bean : beans){
            System.out.println(bean.toString());
        }*/
        /*int op = 2;
        HashMap<Integer, Integer> opMap = new HashMap<>();
        opMap.put(1, 1);
        opMap.put(2, 2);
        opMap.put(3, 0);
        opMap.put(4, 0);
        opMap.put(5, 0);
        Iterator<Integer> it = opMap.keySet().iterator();
        while(it.hasNext()){
            int index = it.next();
            if(opMap.get(index) == op){
                System.out.println(index);
            }
        }*/
        
    }
    
    @Test 
    public void TestQueryByDeviceID() {
        int deviceID = 0;
        DeviceOpBean bean = deviceOpService.queryByDeviceID(deviceID);
        System.out.println(bean == null ? "bean is null":bean.toString());
        
    }
    
    @Test 
    public void TestQuery() {
        int deviceID = 17;
        DeviceOpBean bean = deviceOpService.queryByDeviceID(deviceID);
        if(bean != null){
            bean.setOp1(3);
            bean.setOp2(0);
            bean.setOp3(0);
            bean.setOp4(0);
            bean.setOp5(0);
            try {
                deviceOpService.update(bean);
            } catch (ServiceException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
    }
    
    
    
   
}
