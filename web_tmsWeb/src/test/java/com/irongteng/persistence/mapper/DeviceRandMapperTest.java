package com.irongteng.persistence.mapper;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.irongteng.persistence.beans.Device;
import com.irongteng.service.DeviceRandBean;
import com.irongteng.service.DeviceRandService;
import com.irongteng.service.DeviceService;

import dwz.framework.junit.BaseJunitCase;
import dwz.framework.sys.exception.ServiceException;
@Rollback(false)
public class DeviceRandMapperTest extends BaseJunitCase{
    @Autowired  
    private DeviceRandService deviceRandService;  
    @Autowired  
    private DeviceService deviceService;  
    
    @Test 
    public void Testinsert() {
        DeviceRandBean bean = new DeviceRandBean();
        Device device = deviceService.get(9).getDevice();
        
        try {
            int id = deviceRandService.add(bean);
            System.out.println("customer iD == "+id);
        } catch (ServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @Test 
    public void TestQueryAll() {
        List<DeviceRandBean> beans = deviceRandService.queryAll();
        for(DeviceRandBean bean : beans){
            System.out.println(bean.toString());
        }
    }
    
    @Test 
    public void TestGetRandToDevice() {
       
        DeviceRandBean bean = deviceRandService.getRandToDevice();
        System.out.println(bean.toString());
    }
    
    @Test 
    public void TestQueryByDevice() {
        int deviceID = 30;
        DeviceRandBean bean = deviceRandService.queryByDeviceID(deviceID);
        
        System.out.println(bean == null ? "queryByDeviceID result is null.....":bean.toString());
    }
    
    
    
    
    
   
}
