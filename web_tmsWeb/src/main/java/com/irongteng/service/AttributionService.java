package com.irongteng.service;

import java.util.List;

import com.irongteng.persistence.BaseConditionVO;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;

public interface AttributionService extends BusinessObjectServiceMgr {
    
    String SERVICE_NAME = "attributionService";

    /**
     * 后台添加
     * 
     * @param user
     */

    AttributionBean get(Integer id);
    
    AttributionBean queryByPhoneNO(Integer phoneNO);
    
    String queryAreaByCellNo(Integer cellNo);
    
    Integer add(AttributionBean callAttr) throws ServiceException;
    
    void addBatch(List<AttributionBean> callAttrList) throws ServiceException;
    
    void updateSelective(AttributionBean callAttr) throws ServiceException;;
    
    void update(AttributionBean callAttr) throws ServiceException;;
    
    void updateBatchSelective(List<AttributionBean> callAttributionBeanList) throws ServiceException;
    
    void delete(Integer id);

    void deleteByPhoneNO(Integer phoneNO);
    
    void deleteBatch(List<Integer> callAttrList) throws ServiceException;
    
    void deleteBatchByPhoneNO(List<Integer> phoneNoList) throws ServiceException;
    
    List<AttributionBean> search(BaseConditionVO vo);
    
    Integer searchNum(BaseConditionVO vo);

    List<AttributionBean> findAll();
    
    Integer countAll();
    
    List<Integer> findAllIds();
    
    void importBatch(List<AttributionBean> callAttrBeanList) throws ServiceException;
    
    void importExcel(List<String[]> strings) throws ServiceException;
    
    List<Integer> queryAllPhoneNO();
    
    List<String> searchProvinces();
    
    List<String> searchCitys(String province);

    List<AttributionBean> searchCondition(AttributionBean bean);
}
