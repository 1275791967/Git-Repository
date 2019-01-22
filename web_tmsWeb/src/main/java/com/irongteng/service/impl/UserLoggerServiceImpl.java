package com.irongteng.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irongteng.persistence.BaseConditionVO;
import com.irongteng.persistence.beans.UserLogger;
import com.irongteng.persistence.mapper.UserLoggerMapper;
import com.irongteng.service.UserLoggerBean;
import com.irongteng.service.UserLoggerService;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;


@Transactional(rollbackFor = Exception.class)
@Service(UserLoggerService.SERVICE_NAME)
public class UserLoggerServiceImpl extends AbstractBusinessObjectServiceMgr
  implements UserLoggerService{
  
    @Autowired
    private UserLoggerMapper userLoggerMapper;
    
    @Override
    public UserLoggerBean get(Integer id){
        UserLogger user = userLoggerMapper.load(id);
        if(user == null) return null;
        
        return new UserLoggerBean(user);
    }
    
    @Override
    public Integer add(UserLoggerBean bean) throws ServiceException {
        UserLogger model =bean.getUserLogger();
        userLoggerMapper.insert(model);
        return model.getId();
    }
    
    @Override
    public void update(UserLoggerBean bean) throws ServiceException{
        UserLogger model =bean.getUserLogger();
        userLoggerMapper.updateSelective(model);
    }
    
    @Override
    public void delete(Integer id){
        userLoggerMapper.delete(id);
    }
    
    @Override
    public List<UserLoggerBean> search(BaseConditionVO vo) {
        List<UserLoggerBean> beans = new ArrayList<>();
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<UserLogger> models = userLoggerMapper.findPageBreakByCondition(vo, rb);
        models.stream().forEach((model) -> {
            beans.add(new UserLoggerBean(model));
        });
        return beans;
    }
    
    @Override
    public Integer searchNum(BaseConditionVO vo) {
        return userLoggerMapper.findNumberByCondition(vo);
    }

}
