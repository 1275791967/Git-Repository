package com.irongteng.service;

import java.util.List;

import com.irongteng.persistence.BaseConditionVO;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;

public interface LogTypeService extends BusinessObjectServiceMgr{
    String SERVICE_NAME = "logTypeService";
    /**
     * 后台添加用户管理日志类型
     * 
     * @param user
     */
    Integer add(LogTypeBean user) throws ServiceException;
    
    void update(LogTypeBean user) throws ServiceException;
    
    LogTypeBean get(Integer id);
    
    void delete(Integer id);
    
    List<LogTypeBean> search(BaseConditionVO vo);
    
    Integer searchNum(BaseConditionVO vo);

}
