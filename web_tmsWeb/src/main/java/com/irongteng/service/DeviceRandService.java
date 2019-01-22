package com.irongteng.service;

import java.util.List;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;

public interface DeviceRandService extends BusinessObjectServiceMgr{
    String SERVICE_NAME = "deviceRandService";
    
    /**
     * 后台添加
     * 
     * @param bean
     * @return 
     * @throws dwz.framework.sys.exception.ServiceException
     */
    Integer add(DeviceRandBean bean) throws ServiceException;

    void update(DeviceRandBean bean) throws ServiceException;
             
    List<DeviceRandBean> queryAll();
    
    DeviceRandBean queryByDeviceID(int deviceID);
    
//    List<DeviceRandBean> queryByRand(int rand);
    
    /*DeviceRandBean queryByRandDeviceID(int deviceID, int rand);*/
    
    void delete(Integer id);
    
    void deleteByDeviceID(Integer deviceID);
    
    DeviceRandBean getRandToDevice();
    
    List<DeviceRandBean> queryServiceRand(Integer randV);
    
}
