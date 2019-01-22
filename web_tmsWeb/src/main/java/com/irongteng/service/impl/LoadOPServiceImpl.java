package com.irongteng.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.irongteng.persistence.beans.LoadOP;
import com.irongteng.persistence.mapper.LoadOPMapper;
import com.irongteng.service.LoadOPBean;
import com.irongteng.service.LoadOPService;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;

@Transactional(rollbackFor = Exception.class)
@Service(LoadOPService.SERVICE_NAME)
public class LoadOPServiceImpl extends AbstractBusinessObjectServiceMgr implements LoadOPService{
    @Autowired
    private LoadOPMapper loadOPMapper;
    
//    @Autowired
//    private DeviceMapper deviceMapper;

    @Override
    public Integer add(LoadOPBean bean) throws ServiceException {
        // TODO Auto-generated method stub
        LoadOP loadOP = bean.getLoadOP();
        loadOP.setUpdateTime(new Date());
        loadOPMapper.insert(loadOP);
        return loadOP.getId();
    }

    @Override
    public void update(LoadOPBean bean) throws ServiceException {
        // TODO Auto-generated method stub
        LoadOP model =bean.getLoadOP();
        model.setUpdateTime(new Date());
        loadOPMapper.update(model);
    }

    @Override
    public LoadOPBean getRatio(Integer opNo) {
        List<LoadOP> list = loadOPMapper.getRatio(opNo);
        if(list != null && list.size() > 0){
            return new LoadOPBean(list.get(0));
        }
        return null;
    }
    
    @Override
    public LoadOPBean getCDMARatio(Integer opNo, Integer randNo) {
        List<LoadOP> list = loadOPMapper.getCDMARatio(opNo, randNo);
        if(list != null && list.size() > 0){
            return new LoadOPBean(list.get(0));
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        // TODO Auto-generated method stub
        loadOPMapper.delete(id);
    }

    @Override
    public List<LoadOPBean> queryAll() {
        List<LoadOPBean> beans = new ArrayList<>();
        List<LoadOP> list = loadOPMapper.queryAll();
        if(list != null && list.size() >0){
            for(LoadOP loadOP : list){
                beans.add(new LoadOPBean(loadOP));
            }
        }
        return beans;
    }

    @Override
    public Integer getCountByOP(Integer opNo) {
        return loadOPMapper.getCountByOP(opNo);
    }

    @Override
    public List<LoadOPBean> queryByDevice(Integer deviceID) {
        List<LoadOPBean> beans = new ArrayList<>();
        List<LoadOP> list = loadOPMapper.queryByDevice(deviceID);
        if(list != null && list.size() >0){
            for(LoadOP loadOP : list){
                beans.add(new LoadOPBean(loadOP));
            }
        }
        return beans;
    }

    @Override
    public LoadOPBean get(Integer deviceID, Integer opNo) {
        LoadOP loadOP = loadOPMapper.get(deviceID, opNo);
        if(loadOP != null){
            return new LoadOPBean(loadOP);
        }
        return null;
    }

    @Override
    public void updateRatio(LoadOPBean bean) throws ServiceException {
        // TODO Auto-generated method stub
        LoadOP model =bean.getLoadOP();
        model.setUpdateTime(new Date());
        loadOPMapper.updateRitio(model);
    }

    @Override
    public void clear() {
        loadOPMapper.clear();
    }

    @Override
    public void deleteByDeviceID(Integer deviceID) {
        loadOPMapper.deleteByDeviceID(deviceID);
    }

    @Override
    public List<LoadOPBean> queryByOp(Integer opNo) {
        List<LoadOPBean> beans = new ArrayList<>();
        List<LoadOP> list = loadOPMapper.queryByOp(opNo);
        if(list != null && list.size() >0){
            LoadOP lop = list.get(0);
            for(LoadOP loadOP : list){
                if(lop.getRatio().doubleValue() == loadOP.getRatio().doubleValue()){
                    beans.add(new LoadOPBean(loadOP));
                }
            }
        }
        return beans;
    }
    

}

