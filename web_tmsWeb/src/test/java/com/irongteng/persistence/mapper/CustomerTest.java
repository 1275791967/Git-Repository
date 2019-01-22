package com.irongteng.persistence.mapper;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.irongteng.persistence.CustomerVO;
import com.irongteng.persistence.beans.Customer;
import com.irongteng.service.CustomerBean;
import com.irongteng.service.CustomerService;

import dwz.framework.junit.BaseJunitCase;
import dwz.framework.sys.exception.ServiceException;
@Rollback(false)
public class CustomerTest extends BaseJunitCase{
   
    @Autowired  
    private CustomerService customerService;  

    
    @Test 
    public void Testinsert() {
        CustomerBean customerBean = new CustomerBean();
        Customer customer = customerBean.getCustomer();
        customer.getId();
        customer.setCustomerName("dddss");
        customer.setContact("12132411123");
        customer.setAddress("sfsggsssfs");
        customer.setCreateTime(new Date());
        customer.setUpdateTime(new Date());
        customer.setRemark("121311");
      
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
        CustomerBean queryByName = customerService.queryByName("dsaaa");
        System.out.println("customer iD == "+ queryByName);
    }
    
    @Test 
    public void countAllTest(){
        CustomerBean customerBean = new CustomerBean();
        Customer customer = customerBean.getCustomer();
        customer.setCustomerName("肉肉肉");
        customer.setContact("132423");
        customer.setAddress("发顺丰");
        customer.setCreateTime(new Date());
        customer.setUpdateTime(new Date());
        customer.setRemark("121333");
        Integer countAll = customerService.countAll();
        System.out.println("customer countAll == "+countAll);
    }
    
    @Test 
    public void queryByPhoneTest(){
        CustomerBean customerBean = new CustomerBean();
        Customer customer = customerBean.getCustomer();
        customer.setCustomerName("ddd");
        customer.setContact("121324123");
        customer.setAddress("sfsggsfs");
        customer.setCreateTime(new Date());
        customer.setUpdateTime(new Date());
        customer.setRemark("1213");
        CustomerBean queryByPhone = customerService.queryByPhone("45678");
        System.out.println("customer queryByPhone == "+queryByPhone);
    }
    
    @Test 
    public void addBatchTest(){
        CustomerBean customerBean = new CustomerBean();
        Customer customer = customerBean.getCustomer();
        customer.setCustomerName("ddd");
        customer.setContact("121324123");
        customer.setAddress("sfsggsfs");
        customer.setCreateTime(new Date());
        customer.setUpdateTime(new Date());
        customer.setRemark("1213");
        customerService.addOrUpdate(customerBean);
    }
    
    @Test 
    public void addOrUpdateTest(){
        CustomerBean customerBean = new CustomerBean();
        Customer customer = customerBean.getCustomer();
        customer.setCustomerName("ddd");
        customer.setContact("121324123");
        customer.setAddress("sfsggsfs");
        customer.setCreateTime(new Date());
        customer.setUpdateTime(new Date());
        customer.setRemark("1213");
        customerService.addOrUpdate(customerBean);
    }
    
    @Test 
    public void deleteTest(){
        customerService.delete(1);
    }
    
    @Test 
    public void getTest(){
        CustomerBean get = customerService.get(2);
        System.out.println("customer get == "+get);
    }
    
    @Test 
    public void updateSelectiveTest(){
        CustomerBean customerBean = new CustomerBean();
        Customer customer = customerBean.getCustomer();
        customer.setId(13);
        customer.setCustomerName("ddd");
        customer.setContact("121324123");
        customer.setAddress("sfsggsfs");
        customer.setCreateTime(new Date());
        customer.setUpdateTime(new Date());
        customer.setRemark("1213");
      try {
        customerService.updateSelective(customerBean);
    } catch (ServiceException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    }
    
    @Test 
    public void updateBatchSelectiveTest(){
        CustomerBean customerBean = new CustomerBean();
        Customer customer = customerBean.getCustomer();
        customer.setCustomerName("ddd");
        customer.setContact("121324123");
        customer.setAddress("sfsggsfs");
        customer.setCreateTime(new Date());
        customer.setUpdateTime(new Date());
        customer.setRemark("1213");
        CustomerBean customerBean3 = new CustomerBean();
        Customer customer3 = customerBean3.getCustomer();
        customer3.setCustomerName("ddd");
        customer3.setContact("121324123");
        customer3.setAddress("sfsggsfs");
        customer3.setCreateTime(new Date());
        customer3.setUpdateTime(new Date());
        customer3.setRemark("1213");

         ArrayList<CustomerBean> arrayList = new ArrayList<>();
         arrayList.add(customerBean);
         arrayList.add(customerBean3);
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
      List<CustomerBean> findAll = customerService.findAll();
      System.out.println("customer findAll == "+findAll);

    }
    
    @Test 
    public void findAllIdsTest(){
  
        List<Integer> findAllIds = customerService.findAllIds();
        System.out.println("customer findAllIds == "+findAllIds);
    }
    
    @Test 
    public void search() {
        CustomerVO customerVO = new CustomerVO();
//        baseConditionVO.setPageNum(1);
//        baseConditionVO.setPageSize(10);
//        baseConditionVO.setPhone("45678");
//        baseConditionVO.setName("ddd");
        customerVO.setKeywords("d");
        customerVO.getKeywords();
        List<CustomerBean> search = customerService.search(customerVO);
        System.out.println("customer search == "+search);
    }

    @Test
    public void testSearch() {
        CustomerVO vo = new CustomerVO();
        List<CustomerBean> beans = customerService.search(vo);
        System.out.println("size:" + beans.size());
        beans.forEach(bean->System.out.println(bean));
    }
}
