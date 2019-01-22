package com.irongteng.persistence.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.irongteng.persistence.DeviceVO;
import com.irongteng.persistence.beans.Device;
import com.irongteng.service.DeviceBean;
import com.irongteng.service.DeviceService;

import dwz.framework.junit.BaseJunitCase;
import dwz.framework.sys.exception.ServiceException;
@Rollback(false)
public class DeviceMapperTest extends BaseJunitCase{
    @Autowired  
    private DeviceService deviceService;  
    
    @Test 
    public void Testinsert() {
        DeviceBean customerBean = new DeviceBean();
        Device customer = customerBean.getDevice();
        customer.setAddress("今天天气晴朗");
        customer.setCreateTime(new Date());
        customer.setCustomerID(7);
        customer.setCustomerName("惊天动地");
        customer.setAreaLocation("有太阳");
        customer.setDeviceIP("11");
        customer.setDeviceName("试试");                                                                                                                           
        customer.setDeviceType(45);
        customer.setLatitude("36");
        customer.setLongitude("13");
        customer.setFeatureCode("84");
        customer.setRemark("测试");
        try {
            int id = deviceService.add(customerBean);
            System.out.println("customer iD == "+id);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
    
    @Test 
    public void queryByNameTest(){
        DeviceBean queryByName = deviceService.queryByName("Test手机2");
        System.out.println("customer iD == "+ queryByName);
    }
    
    
    @Test
    public void testGet() {
        int id = 14;
        DeviceBean db = deviceService.get(id);
        System.out.println(db.toString());
    }
    
    
    @Test 
    public void updateBatchSelectiveTest(){
        DeviceBean customerBean = new DeviceBean();
        Device customer = customerBean.getDevice();
        customer.setAddress("asddssd");
        customer.setCreateTime(new Date());
        customer.setCustomerID(4);
        customer.setDeviceName("nifssfo");
        customer.setDeviceType(45);
        customer.setLatitude("36");
        customer.setLongitude("13");
        customer.setFeatureCode("84");
        customer.setRemark("121111");
        DeviceBean customerBean3 = new DeviceBean();
        Device customer3 = customerBean3.getDevice();
        customer3.setAddress("asddssd");
        customer3.setCreateTime(new Date());
        customer3.setCustomerID(4);
        customer3.setDeviceName("nifssfo");
        customer3.setDeviceType(45);
        customer3.setLatitude("36");
        customer3.setLongitude("13");
        customer3.setFeatureCode("84");
        customer3.setRemark("121111");
        ArrayList<DeviceBean> arrayList = new ArrayList<>();
        arrayList.add(customerBean);
        arrayList.add(customerBean3);
       try {
           deviceService.updateBatchSelective(arrayList);
    } catch (ServiceException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
  }
    
    @Test 
    public void deleteTest(){
        deviceService.delete(1);
    }
    
    @Test 
    public void getTest(){
        String imsi = "46001000020230";
        System.out.println(imsi.substring(0, 5));
        DeviceBean get = deviceService.get(2);
        System.out.println("device info == "+get.getDevice().toString());
        
    }
    
    @Test
    public void testDelete() {
        int id = 1;
        deviceService.delete(id);
    }

    
    @Test
    public void testSearchNum() {
        DeviceVO vo = new DeviceVO();        
        Integer num = deviceService.searchNum(vo);
        System.out.println("num:" + num);
    }
    
    
    @Test
    public void testSearch() {
        DeviceVO vo = new DeviceVO();
        List<DeviceBean> beans = deviceService.search(vo);
        System.out.println("size:" + beans.size());
        beans.forEach(bean->System.out.println(bean));
    }
    
    @Test 
    public void search() {
        DeviceVO vo = new DeviceVO();       
        vo.setKeywords("前");
        vo.getKeywords();
        List<DeviceBean> search = deviceService.search(vo);
        System.out.println("customer search == "+search);
    }
    
    @Test
    public void testDeleteBatch() {
        long crt = System.currentTimeMillis();
        try {
            List<Integer> list = new ArrayList<Integer>();
            for (int i = 100; i < 200; i++) {
                list.add(2+i);
            }
            deviceService.deleteBatch(list);
                
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        System.out.println("callAttr运行时间：" + (System.currentTimeMillis() - crt));
    }
    
    @Test
    public void testQueryByName() {
        String deviceName = "197.178.11";
        DeviceBean bean = deviceService.getByIP(deviceName);
        System.out.println(bean.toString());

    }
    
    @Test 
    public void findAllTest(){
      List<DeviceBean> findAll = deviceService.findAll();
      System.out.println("customer findAll == "+findAll);

    }
}
