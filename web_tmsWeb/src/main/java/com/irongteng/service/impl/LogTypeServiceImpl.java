package com.irongteng.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irongteng.persistence.BaseConditionVO;
import com.irongteng.persistence.beans.LogType;
import com.irongteng.persistence.mapper.LogTypeMapper;
import com.irongteng.service.LogTypeBean;
import com.irongteng.service.LogTypeService;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;

@Transactional(rollbackFor = Exception.class)
@Service(LogTypeService.SERVICE_NAME)
@Scope("prototype")
public class LogTypeServiceImpl extends AbstractBusinessObjectServiceMgr
    implements LogTypeService{
    
    @Autowired
    private LogTypeMapper logTypeMapper;
    
    @Override
    public LogTypeBean get(Integer id){
        LogType user = logTypeMapper.load(id);
        if(user == null) return null;
        
        return new LogTypeBean(user);
    }
   
    @Override
    public Integer add(LogTypeBean bean) throws ServiceException {
        LogType model =bean.getLogType();
        logTypeMapper.insert(model);
        return model.getId();
    }
    
    @Override
    public void update(LogTypeBean bean) throws ServiceException{
        LogType model =bean.getLogType();
        logTypeMapper.updateSelective(model);
    }
    
    
    @Override
    public void delete(Integer id){
        logTypeMapper.delete(id);
    }
    
    
    @Override
    public List<LogTypeBean> search(BaseConditionVO vo) {
        List<LogTypeBean> beans = new ArrayList<>();
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<LogType> models = logTypeMapper.findPageBreakByCondition(vo, rb);
        models.stream().forEach((model) -> {
            beans.add(new LogTypeBean(model));
        });
        return beans;
    }
    
    @Override
    public Integer searchNum(BaseConditionVO vo) {
        return logTypeMapper.findNumberByCondition(vo);
    }

    

}
