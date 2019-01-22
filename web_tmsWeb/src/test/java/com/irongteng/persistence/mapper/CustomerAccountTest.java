package com.irongteng.persistence.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.irongteng.persistence.CustomerAccountVO;
import com.irongteng.persistence.beans.CustomerAccount;
import com.irongteng.service.CustomerAccountBean;
import com.irongteng.service.CustomerAccountService;

import dwz.framework.junit.BaseJunitCase;
import dwz.framework.sys.exception.ServiceException;
@Rollback(false)
public class CustomerAccountTest extends BaseJunitCase{
    @Autowired  
    private  CustomerAccountService customerService;  
    
    @Test 
    public void Testinsert() {
        CustomerAccountBean customerBean = new CustomerAccountBean();
        CustomerAccount customer = customerBean.getCustomerAccount();
        customer.setCreateDate(new Date());
        customer.setDeviceName("服务器1");
        customer.setLocalBalance(1);
        customer.setName("sss");
        customer.setPhone("134325532");
        customer.setPsw("123546");
        customer.setStatus("1");
        customer.setThirdBalance(123);
        customer.setRemark("天气晴");
      
        try {
            int id = customerService.add(customerBean);
            System.out.println("customer iD == "+id);
        } catch (ServiceException e) {
            
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @Test 
    public void queryByNameTest(){
        CustomerAccountBean queryByName = customerService.queryByName("Admin");
        System.out.println("customer iD == "+ queryByName);
    }
    
    @Test 
    public void queryByDeviceIDAndNameTest(){
        CustomerAccountBean queryByName = customerService.queryByDeviceIDAndName(5, "dexing");
        System.out.println("customer iD == "+ queryByName);
    }
    
    @Test 
    public void countAllTest(){
        CustomerAccountBean customerBean = new CustomerAccountBean();
        CustomerAccount customer = customerBean.getCustomerAccount();
        
        customer.setRemark("测试测试");
        Integer countAll = customerService.countAll();
        System.out.println("customer countAll == "+countAll);
    }
    
//    @Test 
//    public void queryByPhoneTest(){
//        CustomerAccountBean customerBean = new CustomerAccountBean();
//        CustomerAccount customer = customerBean.getCustomerAccount();
//
//        customer.setRemark("测试");
//        CustomerAccountBean queryByPhone = customerService.queryByPhone("13243654567");
//        System.out.println("customer queryByPhone == "+queryByPhone);
//    }
    
    
    @Test 
    public void addBatchTest(){
        CustomerAccountBean customerBean = new CustomerAccountBean();
        CustomerAccount customer = customerBean.getCustomerAccount();

        customer.setRemark("测试");
        customerService.addOrUpdate(customerBean);
    }
    
    @Test 
    public void addOrUpdateTest(){
        CustomerAccountBean customerBean = new CustomerAccountBean();
        CustomerAccount customer = customerBean.getCustomerAccount();
        customer.setCreateDate(new Date());
        customer.setLocalBalance(21133);
        customer.setName("nnddn");
        customer.setPhone("133514632");
        customer.setPsw("1231");
        customer.setStatus("123");
        customer.setThirdBalance(2314);
        customer.setRemark("12131");
        customerService.addOrUpdate(customerBean);
    }
    
    @Test 
    public void deleteTest(){
        customerService.delete(17);
    }
    
    @Test 
    public void getTest(){
        CustomerAccountBean get = customerService.get(20);
        System.out.println("customer get == "+get);
    }
    
    @Test 
    public void updateSelectiveTest(){
        CustomerAccountBean customerBean = new CustomerAccountBean();
        CustomerAccount customer = customerBean.getCustomerAccount();
        customer.setId(3);
        customer.setCreateDate(new Date());
        customer.setLocalBalance(233);
        customer.setName("nnwwn");
        customer.setPhone("133354632");
        customer.setPsw("1223");
        customer.setStatus("1");
        customer.setThirdBalance(214);
        customer.setRemark("122213");
      try {
        customerService.updateSelective(customerBean);
    } catch (ServiceException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    }
    
    @Test 
    public void updateBatchSelectiveTest(){
        CustomerAccountBean customerBean = new CustomerAccountBean();
        CustomerAccount customer = customerBean.getCustomerAccount();
        
        customer.setRemark("1213");
        CustomerAccountBean customerBean1 = new CustomerAccountBean();
        CustomerAccount customer3 = customerBean1.getCustomerAccount();
       
        customer3.setRemark("1213");

         ArrayList<CustomerAccountBean> arrayList = new ArrayList<>();
         arrayList.add(customerBean1);
       try {
        customerService.updateBatchSelective(arrayList);
    } catch (ServiceException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    }
    
    @Test 
    public void deleteByPhoneTest(){  
        customerService.deleteByPhone("788");
    }
    
    @Test 
    public void deleteBatchByPhone(){
    ArrayList<String> arrayList = new ArrayList<>(); 
    arrayList.add("7898");
    arrayList.add("3534");
        try {
            customerService.deleteBatchByPhone(arrayList);
        } catch (ServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @Test 
    public void findAllTest(){
      List<CustomerAccountBean> findAll = customerService.findAll();
      System.out.println("customer findAll == "+findAll);

    }
    
    @Test 
    public void findAllIdsTest(){
        List<Integer> findAllIds = customerService.findAllIds();
        System.out.println("customer findAllIds == "+findAllIds);
    }
    
    @Test 
    public void search() {
        CustomerAccountVO customerVO = new CustomerAccountVO();
        customerVO.setKeywords("你");
        customerVO.getKeywords();
        List<CustomerAccountBean> search = customerService.search(customerVO);
        System.out.println("customer search == "+search);
    }

    @Test
    public void testSearch() {   
        CustomerAccountVO vo = new CustomerAccountVO();
        List<CustomerAccountBean> beans = customerService.search(vo);
        System.out.println("size:" + beans.size());
        beans.forEach(bean->System.out.println(bean));
    }
    
    
    @Test 
    public void update() {
        CustomerAccountBean bean = new CustomerAccountBean();  
        CustomerAccount s = bean.getCustomerAccount();
        s.setId(25);
        s.setLocalBalance(234);
        s.setThirdBalance(234);
        
        try {
            customerService.update(bean);  
        } catch (ServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}



