package com.irongteng.service;

import java.util.List;

import com.irongteng.persistence.DeviceVO;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;

public interface DeviceService extends BusinessObjectServiceMgr{
    String SERVICE_NAME = "deviceService";
    
    /**
     * 后台添加
     * 
     * @param bean
     * @return 
     * @throws dwz.framework.sys.exception.ServiceException
     */
    Integer add(DeviceBean bean) throws ServiceException;

    void update(DeviceBean bean) throws ServiceException;
    
    void updateSelective(DeviceBean bean) throws ServiceException;
   
    DeviceBean queryByName(String deviceName);
    
//    DeviceBean getBySiteNumber(String siteNumber);
//    
//    DeviceBean getByCityName(String cityCode, String deviceName);
             
    List<DeviceBean> queryAll();
//    //查询所有WIFI
//    List<DeviceBean> queryWiFi();
//    //查询所有电子围栏
//    List<DeviceBean> queryWLAN();
//    
    List<DeviceBean> searchAll();
    
    Integer searchAllNum();
    
    void addOrUpdate(DeviceBean bean);

    DeviceBean get(Integer id);
    
    DeviceBean getByIP(String address);
    
    List<DeviceBean> search(DeviceVO vo);

    Integer searchNum(DeviceVO vo);

    //List<DeviceBean> searchList(DeviceBean deviceBean);
    void updateBatch(List<DeviceBean> beans) throws ServiceException;

    void importExcel(List<String[]> strings) throws ServiceException;
 
    void updateBatchSelective(List<DeviceBean> beans) throws ServiceException;

    void addBatch(List<DeviceBean> beans) throws ServiceException;

    void delete(Integer id);

    void deleteBatch(List<Integer> imsiCallList) throws ServiceException;

    List<Integer> findAllIds();

    Integer countAll();

    List<DeviceBean> findAll();
    
    List<DeviceBean> parameter(DeviceVO vo);

    DeviceBean get(DeviceBean deviceBean);
    
    DeviceBean findByName(String customerName);
    
    DeviceBean queryByFeatureCode(String featureCode);

}
