package com.irongteng.service;

import java.util.List;

import com.irongteng.persistence.BaseConditionVO;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;

public interface UserLoggerService extends BusinessObjectServiceMgr {
    String SERVICE_NAME = "userLoggerService";
    
    
    /**
     * 后台添加用户管理日志
     * 
     * @param user
     */
    Integer add(UserLoggerBean user) throws ServiceException;
    
    void update(UserLoggerBean user) throws ServiceException;
    
    UserLoggerBean get(Integer id);
    
    void delete(Integer id);
    
    List<UserLoggerBean> search(BaseConditionVO vo);
    
    Integer searchNum(BaseConditionVO vo);
}
