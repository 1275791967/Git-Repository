package com.irongteng.persistence.mapper;


import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;


import com.irongteng.persistence.beans.Device;
import com.irongteng.service.DeviceService;
import com.irongteng.service.LoadOPBean;
import com.irongteng.service.LoadOPService;

import dwz.framework.junit.BaseJunitCase;
import dwz.framework.sys.exception.ServiceException;
@Rollback(false)
public class LoadOPMapperTest extends BaseJunitCase{
    @Autowired
    private  LoadOPService loadOPService;
    @Autowired
    private  DeviceService deviceService;
    
    @Test
    public void testAdd() {
        int deviceID = 9;
        Device device = deviceService.get(deviceID).getDevice();
        LoadOPBean bean = new LoadOPBean();
        bean.getLoadOP().setDevice(device);
        bean.setOpNo(5);
        bean.setRatio(15.0);
        try {
            System.out.println(loadOPService.add(bean));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
    
    @Test 
    public void testUpdate() {
        int id = 1;
        Device device = deviceService.get(2).getDevice();
        LoadOPBean bean = new LoadOPBean();
        bean.getLoadOP().setDevice(device);
        bean.setOpNo(2);
        bean.setRatio(12.5);
        bean.setId(id);
        try {
            loadOPService.update(bean);  
        } catch (ServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @Test 
    public void testUpdateRatio() {
        int deviceID = 2;
        Device device = deviceService.get(deviceID).getDevice();
        LoadOPBean bean = new LoadOPBean();
        bean.getLoadOP().setDevice(device);
        bean.setOpNo(2);
        bean.setRatio(11.5);
        try {
            loadOPService.updateRatio(bean);  
        } catch (ServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @Test
    public void testGet() {
        int deviceID = 9;
        int opNo = 3;
        LoadOPBean bean = loadOPService.get(deviceID, opNo);
        System.out.println(bean == null ? "bean is null...." : bean.toString());
    }
    
    
    @Test
    public void testGetRatio() {   
        int opNo = 2;
        LoadOPBean bean = loadOPService.getRatio(opNo);
        System.out.println(bean == null ? "bean is null...." : bean.toString());
    }
    
    @Test
    public void testGetByOpNo() {   
        int opNo = 1;
        List<LoadOPBean> beans = loadOPService.queryByOp(opNo);
        for(LoadOPBean bean : beans){
            System.out.println(bean.toString());
        }
    }
    
    @Test
    public void testGetCDMARatio() {   
        int opNo = 3;
        int rand = 110;
        LoadOPBean bean = loadOPService.getCDMARatio(opNo, rand);
        System.out.println(bean == null ? "bean is null...." : bean.toString());
    }
    
    @Test
    public void testDelete() {
        int id = 8;
        loadOPService.delete(id);
    }
    
    @Test
    public void testClear() {
        loadOPService.clear();
    }
    
    @Test
    public void testDeleteByDeviceID() {
        int deviceID = 17;
        loadOPService.deleteByDeviceID(deviceID);
    }
    
    @Test
    public void testQueryByDevice() {
        int deviceID = 5;
        List<LoadOPBean> beans = loadOPService.queryByDevice(deviceID);
        if(beans == null)
        for(LoadOPBean bean : beans){
            System.out.println(bean.toString());
        }
    }
    
    @Test
    public void testQueryAll() {
        List<LoadOPBean> beans = loadOPService.queryAll();
        for(LoadOPBean bean : beans){
            System.out.println(bean.toString());
        }
    }
    
}
