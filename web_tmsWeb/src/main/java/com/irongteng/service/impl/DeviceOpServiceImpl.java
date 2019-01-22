package com.irongteng.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irongteng.persistence.beans.DeviceOp;
import com.irongteng.persistence.mapper.DeviceOpMapper;
import com.irongteng.service.DeviceOpBean;
import com.irongteng.service.DeviceOpService;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;

@Transactional(rollbackFor = Exception.class)
@Service(DeviceOpService.SERVICE_NAME)
@Scope("prototype")
public class DeviceOpServiceImpl extends AbstractBusinessObjectServiceMgr implements DeviceOpService{
    @Autowired
    private DeviceOpMapper opMapper;
    /**
     * 添加
     * @param bean
     * @return 
     * @throws dwz.framework.sys.exception.ServiceException 
     */
    @Override
    public Integer add(DeviceOpBean bean) throws ServiceException{
        bean.setUpdateTime(new Date());
        DeviceOp deviceOp = bean.getDeviceOp();
        opMapper.insert(deviceOp);
        return deviceOp.getId();
    }
    
    /**
     * 更新
     * @param bean
     * @throws dwz.framework.sys.exception.ServiceException
     */
    @Override
    public void update(DeviceOpBean bean) throws ServiceException{
        DeviceOp deviceOP = bean.getDeviceOp();
        bean.setUpdateTime(new Date());      
        opMapper.update(deviceOP);
    }
        
    @Override
    public void delete(Integer id) {
        opMapper.delete(id);
    }

    @Override
    public List<DeviceOpBean> queryAll() {
        List<DeviceOp> list = opMapper.queryAll();
        List<DeviceOpBean> beans = new ArrayList<>();
        if(list != null && list.size() > 0){
            for(DeviceOp deviceOp : list){
                beans.add(new DeviceOpBean(deviceOp));
            }
            return beans;
        }
        return null;
    }

    @Override
    public DeviceOpBean queryByDeviceID(int deviceID) {
        DeviceOp deviceOP = opMapper.queryByDeviceID(deviceID);
        if(deviceOP != null){
            return new DeviceOpBean(deviceOP);
        }
        return null;
    }

    @Override
    public List<DeviceOpBean> queryByOp(int op) {
        List<DeviceOp> list = opMapper.queryByOp(op);
        List<DeviceOpBean> beans = new ArrayList<>();
        if(list != null && list.size() > 0){
            for(DeviceOp deviceOp : list){
                beans.add(new DeviceOpBean(deviceOp));
            }
            return beans;
        }
        return null;
    }
    
    
    @Override
    public void deleteByDeviceID(Integer deviceID) {
        opMapper.deleteByDeviceID(deviceID);
        
    }

}

