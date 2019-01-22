package com.irongteng.service;

import java.util.List;

import com.irongteng.persistence.CustomerAccountVO;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;

public interface CustomerAccountService extends BusinessObjectServiceMgr{
    String SERVICE_NAME = "customerAccountService";
    /**
     * 后台添加
     * 
     * @param user
     */
    CustomerAccountBean queryByName(String name);
    
    CustomerAccountBean get(Integer id);
    
    Integer add(CustomerAccountBean bean) throws ServiceException;
    
    void addOrUpdate(CustomerAccountBean bean);
   
    void addBatch(List<CustomerAccountBean> beans)throws ServiceException;
    
    void updateSelective(CustomerAccountBean bean) throws ServiceException;
    
    void update(CustomerAccountBean bean) throws ServiceException;
    
    void updateBatchSelective(List<CustomerAccountBean> beans) throws ServiceException;
    
    void delete(Integer id);
    void deleteByPhone(String phone);

    public void deleteBatchByPhone(List<String> contactList) throws ServiceException;
    List<CustomerAccountBean> findAll();
    CustomerAccountBean queryByPhone(String phone);
    Integer countAll();
    
    List<CustomerAccountBean> search(CustomerAccountVO vo);
    Integer searchNum(CustomerAccountVO vo);
    List<CustomerAccountBean> parameter(CustomerAccountVO vo);

    List<Integer> findAllIds();
    
//    CustomerAccountBean queryByDeviceID(int deviceID);

    CustomerAccountBean queryByDeviceIDAndName(int deviceID, String name);

    void inActive(int id) throws ServiceException;
    
    void active(int id) throws ServiceException;
}
