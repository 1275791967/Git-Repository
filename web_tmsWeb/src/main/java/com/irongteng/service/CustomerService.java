package com.irongteng.service;

import java.util.List;

import com.irongteng.persistence.CustomerVO;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;

public interface CustomerService extends BusinessObjectServiceMgr{
    String SERVICE_NAME = "customerServices";
    /**
     * 后台添加
     * 
     * @param user
     */
    CustomerBean queryByName(String customerName);
    
    CustomerBean get(Integer id);
    
    Integer add(CustomerBean bean) throws ServiceException;
    
    void addOrUpdate(CustomerBean bean);
   
    void addBatch(List<CustomerBean> beans)throws ServiceException;
    
    void updateSelective(CustomerBean bean) throws ServiceException;
    
    void update(CustomerBean bean) throws ServiceException;
    
    void updateBatchSelective(List<CustomerBean> beans) throws ServiceException;
    
    void delete(Integer id);
    void deleteByPhone(String contact);

    public void deleteBatchByPhone(List<String> contactList) throws ServiceException;
    List<CustomerBean> findAll();
    CustomerBean queryByPhone(String contact);
    Integer countAll();
    
    List<CustomerBean> search(CustomerVO vo);
    Integer searchNum(CustomerVO vo);
    List<CustomerBean> parameter(CustomerVO vo);

    List<Integer> findAllIds();

}
