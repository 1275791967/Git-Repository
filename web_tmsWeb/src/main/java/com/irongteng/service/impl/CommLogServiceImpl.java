package com.irongteng.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irongteng.persistence.CommLogVO;
import com.irongteng.persistence.beans.CommLog;
import com.irongteng.persistence.beans.Device;
import com.irongteng.persistence.mapper.CommLogMapper;
import com.irongteng.persistence.mapper.DeviceMapper;
import com.irongteng.service.CommLogBean;
import com.irongteng.service.CommLogService;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;

@Transactional(rollbackFor = Exception.class)
@Service(CommLogService.SERVICE_NAME)
public class CommLogServiceImpl extends AbstractBusinessObjectServiceMgr
implements CommLogService{
    
    @Autowired
    private CommLogMapper commLogMapper;
    
    @Autowired
    private DeviceMapper deviceMapper;
    
    @Override
    public CommLogBean get(Integer id){
        CommLog user = commLogMapper.load(id);
        if(user == null) return null;
        
        return new CommLogBean(user);
    }
    
    @Override
    public Integer add(CommLogBean bean) throws ServiceException {
        CommLog model =bean.getCommLog();

        Device queryByName = deviceMapper.load(model.getDeviceID());
        bean.setDeviceID(queryByName.getId());

//        Device queryByName = deviceMapper.queryByName(model.getDeviceName());
//        bean.setDeviceID(queryByName.getId());

        commLogMapper.insert(model);
        return model.getId();
    }
    
    @Override
    public void update(CommLogBean bean) throws ServiceException{
        CommLog model =bean.getCommLog();
        commLogMapper.updateSelective(model);
    }
    
    @Override
    public void delete(Integer id){
        commLogMapper.delete(id);
    }
    
    @Override
    public List<CommLogBean> search(CommLogVO vo) {
        List<CommLogBean> beans = new ArrayList<>();
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<CommLog> models = commLogMapper.findPageBreakByCondition(vo, rb);
        models.stream().forEach((model) -> {
            beans.add(new CommLogBean(model));
        });
        return beans;
    }
    
    @Override
    public Integer searchNum(CommLogVO vo) {
        Integer count = commLogMapper.findNumberByCondition(vo);
        return count;
    }

    @Override
    public List<CommLogBean> findAll() {
        List<CommLogBean> beans = new ArrayList<>();
        List<CommLog> models = commLogMapper.findAll();
        models.stream().forEach((model) -> {
            beans.add(new CommLogBean(model));
        });
        return beans;
    }  
}
