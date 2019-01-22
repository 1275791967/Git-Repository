package com.irongteng.service;

import java.util.List;

import com.irongteng.persistence.TranstFlowVO;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;

public interface TranstFlowService extends BusinessObjectServiceMgr {
    String SERVICE_NAME = "transtFlowService";
    /**
     * 后台添加
     * 
     * @param user
     */
    Integer add(TranstFlowBean user) throws ServiceException;
    
    void update(TranstFlowBean user) throws ServiceException;
    
    TranstFlowBean get(Integer id);
    
    void delete(Integer id);
    
    List<TranstFlowBean> search(TranstFlowVO vo);
    
    Integer searchNum(TranstFlowVO vo);
    
    TranstFlowBean analyzeTranslateResult(Integer deviceID);
    
    List<TranstFlowBean> getDevice(Integer deviceID);

    List<TranstFlowBean> getCancelTranslateList(Integer deviceID);
    
    TranstFlowBean getLastByImsi(String imsi);

    void deleteBatch(List<Integer> imsiCallList) throws ServiceException;

    
    void updateTranslateStatusByDeviceID(Integer deviceID);
    
    void updateTranslateStatusByIMSI(String imsi);
    
    List<TranstFlowBean> getCancelTranslateList1(Integer deviceID);
    
    //目前仅手机客户端第三方查询使用
    TranstFlowBean queryLastByImsiOrPhone(String text);
    
    List<TranstFlowBean> findAll();
}
