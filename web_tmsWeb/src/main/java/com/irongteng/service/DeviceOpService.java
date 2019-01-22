package com.irongteng.service;

import java.util.List;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;

public interface DeviceOpService extends BusinessObjectServiceMgr{
    String SERVICE_NAME = "deviceOpService";
    
    /**
     * 后台添加
     * 
     * @param bean
     * @return 
     * @throws dwz.framework.sys.exception.ServiceException
     */
    Integer add(DeviceOpBean bean) throws ServiceException;

    void update(DeviceOpBean bean) throws ServiceException;
             
    List<DeviceOpBean> queryAll();
    
    DeviceOpBean queryByDeviceID(int deviceID);
    
    List<DeviceOpBean> queryByOp(int op);
    
    void delete(Integer id);
    
    void deleteByDeviceID(Integer deviceID);
}
