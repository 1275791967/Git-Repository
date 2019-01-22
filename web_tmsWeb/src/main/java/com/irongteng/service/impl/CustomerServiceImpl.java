package com.irongteng.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irongteng.persistence.CustomerVO;
import com.irongteng.persistence.beans.Customer;
import com.irongteng.persistence.mapper.CustomerMapper;
import com.irongteng.service.CustomerBean;
import com.irongteng.service.CustomerService;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;

@Transactional(rollbackFor = Exception.class)
@Service(CustomerService.SERVICE_NAME)
@Scope("prototype")
public class CustomerServiceImpl extends AbstractBusinessObjectServiceMgr implements CustomerService{
    
    @Autowired
    private CustomerMapper customerMapper;
    /**
     * 添加
     * @param bean
     * @return 
     * @throws dwz.framework.sys.exception.ServiceException 
     */
    @Override
    public Integer add(CustomerBean bean) throws ServiceException{
       
        bean.setCreateTime(new Date());
        bean.setUpdateTime(new Date());
        Customer model = bean.getCustomer();
        customerMapper.insert(model);
        return model.getId();
    }
    @Override
    public void addOrUpdate(CustomerBean bean){
        bean.setCreateTime(new Date());
        bean.setUpdateTime(new Date());

        customerMapper.addOrUpdate(bean.getCustomer());
    }
    @Override
    public void addBatch(List<CustomerBean> beans) throws ServiceException {
        
        List<Customer> models = new ArrayList<>();   //保存不存在的号码归属地信息，用于添加列表
        Date date = new Date();
        
        beans.stream().map((bean) -> {
            bean.setCreateTime(date);
            bean.setUpdateTime(date);
            return bean;
        }).forEach((bean) -> {
            models.add(bean.getCustomer());
        });
        
        int index = 0;       //保存每次的索引
        int nextIndex;       //保存最终索引的下一次索引
        int batchCount = 50; //每批commit的个数
        
        while(index < models.size()) {
            nextIndex = index + batchCount;
            if (nextIndex >= models.size()) {
                customerMapper.addBatch(models.subList(index, models.size()));
            } else {
                customerMapper.addBatch(models.subList(index, index + batchCount));
            }
            index += batchCount;
        }
    }
   
    /**
     * 更新
     * @param bean
     * @throws dwz.framework.sys.exception.ServiceException
     */
    @Override
    public void update(CustomerBean bean) throws ServiceException{
        bean.setCreateTime(new Date());
        bean.setUpdateTime(new Date());    
        Customer model = bean.getCustomer();  
        customerMapper.update(model);
    }
    
    /**
     * 条件更新
     * @param bean
     * @throws dwz.framework.sys.exception.ServiceException
     */
    @Override
    public void updateSelective(CustomerBean bean)  throws ServiceException{
        bean.setCreateTime(new Date());
        bean.setUpdateTime(new Date());
        
        Customer model = bean.getCustomer();
        customerMapper.updateSelective(model);
        }
        
    /**
     * 按条件批量更新
     * @param beans
     * @throws dwz.framework.sys.exception.ServiceException
     */
    @Override
    public void updateBatchSelective(List<CustomerBean> beans) throws ServiceException {
        if (beans==null || beans.size()<=0) {
            throw new ServiceException("msg.list.notnull");
        }
        List<Customer> imsiCallList = new ArrayList<>();   
        Date date = new Date();
        beans.stream().map((bean) -> {
            bean.setCreateTime(date);
            bean.setUpdateTime(date);
            return bean;
        }).forEach((bean) -> {
            imsiCallList.add(bean.getCustomer());
        });
        
        int index = 0;       //保存每次的索引
        int nextIndex;       //保存最终索引的下一次索引
        int batchCount = 500;//每批commit的个数
        
        while(index < imsiCallList.size()) {
            nextIndex = index + batchCount;
            if (nextIndex >= imsiCallList.size()) {
                customerMapper.updateBatchSelective(imsiCallList.subList(index, imsiCallList.size()));
            } else {
                customerMapper.updateBatchSelective(imsiCallList.subList(index, index + batchCount));
            }
            index += batchCount;
        }
        
    }
        
    @Override
    public void delete(Integer id) {
        customerMapper.delete(id);
    }
        

    @Override
    public void deleteByPhone(String phone) {
        customerMapper.deleteByPhone(phone);
        
    }
        
    
//    @Override
//    public void deleteBatch(List<Integer> imsiCallList) throws ServiceException{
//        if (imsiCallList==null || imsiCallList.size()<=0) {
//            throw new ServiceException("msg.list.notnull");
//        }
//        int index = 0;   //保存每次的索引
//        int nextIndex;   //保存最终索引的下一次索引
//        int batchCount = 5000;//每批commit的个数
//        
//        while(index < imsiCallList.size()) {
//            nextIndex = index + batchCount;
//            if (nextIndex >= imsiCallList.size()) {
//                imsiCallMapper.deleteBatch(imsiCallList.subList(index, imsiCallList.size()));
//            } else {
//                imsiCallMapper.deleteBatch(imsiCallList.subList(index, index + batchCount));
//            }
//            index += batchCount;
//        }
//    }
//    
    @Override
    public void deleteBatchByPhone(List<String> contactList) throws ServiceException {
        if (contactList==null || contactList.size()<=0) {
            throw new ServiceException("msg.list.notnull");
        }
        int index = 0;       //保存每次的索引
        int nextIndex;   //保存最终索引的下一次索引
        int batchCount = 5000;//每批commit的个数
        
        while(index < contactList.size()) {
            nextIndex = index + batchCount;
            if (nextIndex >= contactList.size()) {
                customerMapper.deleteBatchByPhone(contactList.subList(index, contactList.size()));
            } else {
                customerMapper.deleteBatchByPhone(contactList.subList(index, index + batchCount));
            }
            index += batchCount;
        }
    }
    
//    
    @Override
    public List<CustomerBean> search(CustomerVO vo) {
        List<CustomerBean> beans = new ArrayList<>();
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<Customer> al = customerMapper.findPageBreakByCondition(vo, rb);
        al.stream().forEach((imsiCall) -> {
            beans.add(new CustomerBean(imsiCall));
        });
        return beans;
    }
    
    @Override
    public List<CustomerBean> parameter(CustomerVO vo) {
        List<CustomerBean> beans = new ArrayList<>();
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<Customer> al = customerMapper.parameter(vo, rb);
        al.stream().forEach((imsiCall) -> {
            beans.add(new CustomerBean(imsiCall));
        });
        return beans;
    }
    
   
    @Override
    public Integer searchNum(CustomerVO vo) {
        Integer count = customerMapper.findNumberByCondition(vo);
        
        return count;
    }
   
    @Override
    public CustomerBean get(Integer id) {
        Customer model = customerMapper.load(id);
        if (model == null) return null;
        
        return new CustomerBean(model);
    }
    

    @Override
    public List<CustomerBean> findAll() {
        List<CustomerBean> beans = new ArrayList<>();
        List<Customer> models = customerMapper.findAll();
        models.stream().forEach((model) -> {
            beans.add(new CustomerBean(model));
        });
        return beans;
    }
    
    /**
     * 通过手机号码查询对象
     * @param phone
     * @return 
     */
    @Override
    public CustomerBean queryByPhone(String contact) {
        Customer model = customerMapper.queryByPhone(contact);
        if (model!=null) {
            return new CustomerBean(customerMapper.queryByPhone(contact));
        }
        return null;
    }
    
    /**
     * 通过名字查询对象
     * @param phone
     * @return 
     */
    @Override
    public CustomerBean queryByName(String customerName){
        Customer model = customerMapper.queryByName(customerName);
        if (model!=null) {
            return new CustomerBean(customerMapper.queryByName(customerName));
        }
        return null;
    }
   
    @Override
    public Integer countAll() {
        return customerMapper.countAll();
    }
    @Override
    public List<Integer> findAllIds() {
        return customerMapper.findAllIds();
    }
    
}
