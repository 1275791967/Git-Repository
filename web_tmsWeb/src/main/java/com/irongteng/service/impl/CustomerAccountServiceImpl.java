package com.irongteng.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irongteng.persistence.CustomerAccountVO;
import com.irongteng.persistence.beans.CustomerAccount;
import com.irongteng.persistence.beans.Device;
import com.irongteng.persistence.mapper.CustomerAccountMapper;
import com.irongteng.persistence.mapper.DeviceMapper;
import com.irongteng.service.CustomerAccountBean;
import com.irongteng.service.CustomerAccountService;
import com.irongteng.service.CustomerAccountStatus;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;

@Transactional(rollbackFor = Exception.class)
@Service(CustomerAccountService.SERVICE_NAME)
@Scope("prototype")
public class CustomerAccountServiceImpl extends AbstractBusinessObjectServiceMgr implements CustomerAccountService{
    @Autowired
    private CustomerAccountMapper customer;
    @Autowired
    private DeviceMapper deviceMapper;
    
    
    /**
     * 添加
     * @param bean
     * @return 
     * @throws dwz.framework.sys.exception.ServiceException 
     */
    @Override
    public Integer add(CustomerAccountBean bean) throws ServiceException{
        if (!isUniqueName(bean)) {
            throw new ServiceException(getMessage("客户名字已存在，请核对后提交"));
        }
        bean.setCreateDate(new Date());
        bean.setStatus(CustomerAccountStatus.ACTIVE.toString());
        CustomerAccount model = bean.getCustomerAccount();
        List<Device> devices = deviceMapper.queryByName(model.getDeviceName());
        if(devices != null && devices.size() > 0){
            Device device = devices.get(0);
            bean.setDeviceID(device.getId());
            customer.insert(model);
        }
        
        return model.getId();
    }
    
    
    private boolean isUniqueName(CustomerAccountBean bean) {
        Integer id = bean.getId() != null ? bean.getId() : 0;
        return customer.isUniqueName(id, bean.getName()) < 1;
    }
    
    @Override
    public void addOrUpdate(CustomerAccountBean bean){
        bean.setCreateDate(new Date());
        customer.addOrUpdate(bean.getCustomerAccount());
    }
    
    @Override
    public void addBatch(List<CustomerAccountBean> beans) throws ServiceException {
        
        List<CustomerAccount> models = new ArrayList<>();   //保存不存在的号码归属地信息，用于添加列表
        Date date = new Date();
        beans.stream().map((bean) -> {
            bean.setCreateDate(date);
            return bean;
        }).forEach((bean) -> {
            models.add(bean.getCustomerAccount());
        });
        
        int index = 0;       //保存每次的索引
        int nextIndex;       //保存最终索引的下一次索引
        int batchCount = 50; //每批commit的个数
        
        while(index < models.size()) {
            nextIndex = index + batchCount;
            if (nextIndex >= models.size()) {
                customer.addBatch(models.subList(index, models.size()));
            } else {
                customer.addBatch(models.subList(index, index + batchCount));
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
    public void update(CustomerAccountBean bean) throws ServiceException{
        if (!isUniqueName(bean)) {
            throw new ServiceException(getMessage("客户名字已存在，请核对后提交"));
        }
        bean.setCreateDate(new Date());  
        CustomerAccount cust = bean.getCustomerAccount();
        customer.updateSelective(cust);
    }
    
    /**
     * 条件更新
     * @param bean
     * @throws dwz.framework.sys.exception.ServiceException
     */
    @Override
    public void updateSelective(CustomerAccountBean bean)  throws ServiceException{
        if (!isUniqueName(bean)) {
            throw new ServiceException(getMessage("客户名字已存在，请核对后提交"));
        }
        bean.setCreateDate(new Date());
        CustomerAccount model = bean.getCustomerAccount();
        customer.updateSelective(model);
        }
        
    /**
     * 按条件批量更新
     * @param beans
     * @throws dwz.framework.sys.exception.ServiceException
     */
    @Override
    public void updateBatchSelective(List<CustomerAccountBean> beans) throws ServiceException {
        if (beans==null || beans.size()<=0) {
            throw new ServiceException("msg.list.notnull");
        }
        List<CustomerAccount> imsiCallList = new ArrayList<>();   
        Date date = new Date();
        beans.stream().map((bean) -> {
            bean.setCreateDate(date);
            return bean;
        }).forEach((bean) -> {
            imsiCallList.add(bean.getCustomerAccount());
        });
        
        int index = 0;       //保存每次的索引
        int nextIndex;       //保存最终索引的下一次索引
        int batchCount = 500;//每批commit的个数
        
        while(index < imsiCallList.size()) {
            nextIndex = index + batchCount;
            if (nextIndex >= imsiCallList.size()) {
                customer.updateBatchSelective(imsiCallList.subList(index, imsiCallList.size()));
            } else {
                customer.updateBatchSelective(imsiCallList.subList(index, index + batchCount));
            }
            index += batchCount;
        }
        
    }
        
    @Override
    public void delete(Integer id) {
        customer.delete(id);
    }
        

    @Override
    public void deleteByPhone(String phone) {
        customer.deleteByPhone(phone);
        
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
                customer.deleteBatchByPhone(contactList.subList(index, contactList.size()));
            } else {
                customer.deleteBatchByPhone(contactList.subList(index, index + batchCount));
            }
            index += batchCount;
        }
    }
    

    @Override
    public List<CustomerAccountBean> search(CustomerAccountVO vo) {
        List<CustomerAccountBean> beans = new ArrayList<>();
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<CustomerAccount> al = customer.findPageBreakByCondition(vo, rb);
        al.stream().forEach((imsiCall) -> {
            beans.add(new CustomerAccountBean(imsiCall));
        });
        return beans;
    }
    
    @Override
    public List<CustomerAccountBean> parameter(CustomerAccountVO vo) {
        List<CustomerAccountBean> beans = new ArrayList<>();
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<CustomerAccount> al = customer.parameter(vo, rb);
        al.stream().forEach((imsiCall) -> {
            beans.add(new CustomerAccountBean(imsiCall));
        });
        return beans;
    }
    
   
    @Override
    public Integer searchNum(CustomerAccountVO vo) {
        Integer count = customer.findNumberByCondition(vo);
        
        return count;
    }
   
    @Override
    public CustomerAccountBean get(Integer id) {
        CustomerAccount model = customer.load(id);
        if (model == null) return null;
        
        return new CustomerAccountBean(model);
    }
    

    @Override
    public List<CustomerAccountBean> findAll() {
        List<CustomerAccountBean> beans = new ArrayList<>();
        List<CustomerAccount> models = customer.findAll();
        models.stream().forEach((model) -> {
            beans.add(new CustomerAccountBean(model));
        });
        return beans;
    }
    
    /**
     * 通过手机号码查询对象
     * @param phone
     * @return 
     */
    @Override
    public CustomerAccountBean queryByPhone(String Phone) {
        CustomerAccount model = customer.queryByPhone(Phone);
        if (model!=null) {
            return new CustomerAccountBean(customer.queryByPhone(Phone));
        }
        return null;
    }
    
    /**
     * 通过名字查询对象
     * @param phone
     * @return 
     */
    @Override
    public CustomerAccountBean queryByName(String name){
        CustomerAccount model = customer.queryByName(name);
        if (model!=null) {
            return new CustomerAccountBean(customer.queryByName(name));
        }
        return null;
    }
    
    /**
     * 通过名字查询对象
     * @param phone
     * @return 
     */
    @Override
    public CustomerAccountBean queryByDeviceIDAndName(int deviceID, String name){
        CustomerAccount model = customer.queryByDeviceIDAndName(deviceID, name);
        if (model!=null) {
            return new CustomerAccountBean(customer.queryByName(name));
        }
        return null;
    }
    
    /**
     * 通过设备查询对象
     * @param phone
     * @return 
     */
    /*@Override
    public CustomerAccountBean queryByDeviceID(int deviceID){
        CustomerAccount model = customer.queryByDeviceID(deviceID);
        if (model!=null) {
            return new CustomerAccountBean(model);
        }
        return null;
    }*/
   
    @Override
    public Integer countAll() {
        return customer.countAll();
    }
    @Override
    public List<Integer> findAllIds() {
        return customer.findAllIds();
    }
    
    @Override
    public void active(int id) {
        customer.updateStatus(id, CustomerAccountStatus.ACTIVE.toString());
    }

    @Override
    public void inActive(int id) {
        customer.updateStatus(id, CustomerAccountStatus.INACTIVE.toString());
    }
}

