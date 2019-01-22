package com.irongteng.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irongteng.persistence.beans.DeviceRand;
import com.irongteng.persistence.mapper.DeviceRandMapper;
import com.irongteng.service.DeviceRandBean;
import com.irongteng.service.DeviceRandService;
import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;

@Transactional(rollbackFor = Exception.class)
@Service(DeviceRandService.SERVICE_NAME)
@Scope("prototype")
public class DeviceRandServiceImpl extends AbstractBusinessObjectServiceMgr implements DeviceRandService{
    @Autowired
    private DeviceRandMapper randMapper;
    /**
     * 添加
     * @param bean
     * @return 
     * @throws dwz.framework.sys.exception.ServiceException 
     */
    @Override
    public Integer add(DeviceRandBean bean) throws ServiceException{
        bean.setUpdateTime(new Date());
        DeviceRand deviceRand = bean.getDeviceRand();
        randMapper.insert(deviceRand);
        return deviceRand.getId();
    }
    
    /**
     * 更新
     * @param bean
     * @throws dwz.framework.sys.exception.ServiceException
     */
    @Override
    public void update(DeviceRandBean bean) throws ServiceException{
        DeviceRand deviceRand = bean.getDeviceRand();
        bean.setUpdateTime(new Date());      
        randMapper.update(deviceRand);
    }
        
    @Override
    public void delete(Integer id) {
        randMapper.delete(id);
    }

    @Override
    public List<DeviceRandBean> queryAll() {
        List<DeviceRand> list = randMapper.queryAll();
        List<DeviceRandBean> beans = new ArrayList<>();
        if(list != null && list.size() > 0){
            for(DeviceRand deviceRand : list){
                beans.add(new DeviceRandBean(deviceRand));
            }
            return beans;
        }
        return null;
    }

    @Override
    public DeviceRandBean queryByDeviceID(int deviceID) {
        DeviceRand deviceRand = randMapper.queryByDeviceID(deviceID);
        if(deviceRand != null){
            return new DeviceRandBean(deviceRand);
        }
        return null;
    }

   /* @Override
    public List<DeviceRandBean> queryByRand(int rand) {
        List<DeviceRand> list = randMapper.queryByRand(rand);
        List<DeviceRandBean> beans = new ArrayList<>();
        if(list != null && list.size() > 0){
            for(DeviceRand deviceRand : list){
                beans.add(new DeviceRandBean(deviceRand));
            }
            return beans;
        }
        return null;
    }*/

    /*@Override
    public DeviceRandBean queryByRandDeviceID(int deviceID, int rand) {
        // TODO Auto-generated method stub
        List<DeviceRand> list = randMapper.queryByRandDeviceID(deviceID, rand);
        if(list != null && list.size() > 0){
            return new DeviceRandBean(list.get(0));
        }
        return null;
    }*/

    @Override
    public void deleteByDeviceID(Integer deviceID) {
        // TODO Auto-generated method stub
        randMapper.deleteByDeviceID(deviceID);
    }

    @Override
    public DeviceRandBean getRandToDevice() {
        // TODO Auto-generated method stub
        List<DeviceRand> list = randMapper.getRandToDevice();
        if(list != null && list.size() > 0){
            return new DeviceRandBean(list.get(0));
        }
        return null;
    }

    @Override
    public List<DeviceRandBean> queryServiceRand(Integer randV) {
        // TODO Auto-generated method stub
        List<DeviceRand> list = randMapper.queryServiceRand(randV);
        List<DeviceRandBean> beans = new ArrayList<>();
        if(list != null && list.size() > 0){
            for(DeviceRand deviceRand : list){
                beans.add(new DeviceRandBean(deviceRand));
            }
            return beans;
        }
        return null;
    }
    
}

