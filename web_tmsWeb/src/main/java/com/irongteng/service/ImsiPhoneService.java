package com.irongteng.service;

import java.util.Date;
import java.util.List;

import com.irongteng.persistence.BaseConditionVO;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;

public interface ImsiPhoneService extends BusinessObjectServiceMgr {
    
    String SERVICE_NAME = "imsiPhoneService";

    /**
     * 后台添加
     * 
     * @param user
     */

    ImsiPhoneBean get(Integer id);
    
    ImsiPhoneBean queryByPhone(String phone);
    
    ImsiPhoneBean queryByImsi(String imsi);
    
    Integer add(ImsiPhoneBean bean) throws ServiceException;
    
    void addOrUpdate(ImsiPhoneBean bean);
    
    void addBatch(List<ImsiPhoneBean> beans) throws ServiceException;
    
    void updateSelective(ImsiPhoneBean bean) throws ServiceException;;
    
    void update(ImsiPhoneBean bean) throws ServiceException;;
    
    void updateBatchSelective(List<ImsiPhoneBean> beans) throws ServiceException;
    
    void delete(Integer id);

    void deleteByPhone(String phone);
    
    void deleteBatch(List<Integer> imsiCallList) throws ServiceException;
    
    void deleteBatchByPhone(List<String> phoneList) throws ServiceException;
    
    List<ImsiPhoneBean> search(BaseConditionVO vo);
    
  //Integer searchStatusNum(ImsiCallBean  bean);
    
    Integer searchNum(BaseConditionVO vo);

    List<ImsiPhoneBean> findAll();
    
    Integer countAll();
    
    List<Integer> findAllIds();
    
    void importBatch(List<ImsiPhoneBean> beans) throws ServiceException;
    
    void importExcel(List<String[]> strings) throws ServiceException;
    
    List<String> queryAllPhone();

    Date getLastRecordTime();
    
}
