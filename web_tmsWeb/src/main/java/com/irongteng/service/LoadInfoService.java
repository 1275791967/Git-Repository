package com.irongteng.service;

import java.util.List;

import com.irongteng.persistence.LoadInfoVO;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;

public interface LoadInfoService extends BusinessObjectServiceMgr {
    
    String SERVICE_NAME = "loadInfoService";
    
    
    /**
     * 后台添加
     * 
     * @param user
     */
    Integer add(LoadInfoBean user) throws ServiceException;
    
    void update(LoadInfoBean user) throws ServiceException;
    
    LoadInfoBean get(Integer id);
    
    void delete(Integer id);
    
    List<LoadInfoBean> search(LoadInfoVO vo);
    
    Integer searchNum(LoadInfoVO vo);
    
    List<LoadInfoBean> searchBy(LoadInfoVO vo);

//    List<LoadInfoBean> searchByDevice(LoadInfoVO vo);
    
    LoadInfoBean queryByDeviceID(Integer deviceID);
    
    LoadInfoBean channelOneTranslateRatio();
    
    LoadInfoBean channelTwoTranslateRatio();
    
    LoadInfoBean channeThreeTranslateRatio();
    
    LoadInfoBean channeFourTranslateRatio();
    
    LoadInfoBean channeFiveTranslateRatio();

    List<LoadInfoBean> findAll();


}
