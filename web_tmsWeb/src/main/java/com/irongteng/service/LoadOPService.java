package com.irongteng.service;

import java.util.List;
import dwz.framework.sys.business.BusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;

public interface LoadOPService extends BusinessObjectServiceMgr {
    
    String SERVICE_NAME = "loadOPService";
    
    Integer add(LoadOPBean bean) throws ServiceException;
    
    void update(LoadOPBean bean) throws ServiceException;
    
    void updateRatio(LoadOPBean bean) throws ServiceException;
    
    LoadOPBean getRatio(Integer opNo);
    
    LoadOPBean getCDMARatio(Integer opNo, Integer randNo);
    
    LoadOPBean get(Integer deviceID, Integer opNo);
    
    void delete(Integer id);
    
    void deleteByDeviceID(Integer deviceID);
    
    void clear();
    
    List<LoadOPBean> queryAll();
    
    Integer getCountByOP(Integer opNo);
    
    List<LoadOPBean> queryByDevice(Integer deviceID);
    
    List<LoadOPBean> queryByOp(Integer opNo);
    
}
