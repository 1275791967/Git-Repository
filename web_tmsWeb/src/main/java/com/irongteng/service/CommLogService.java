package com.irongteng.service;

import java.util.List;

import com.irongteng.persistence.CommLogVO;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;

public interface CommLogService extends BusinessObjectServiceMgr{
    
    String SERVICE_NAME = "commLogService";
    
    
    /**
     * 后台添加用户管理日志
     * 
     * @param user
     */
    Integer add(CommLogBean user) throws ServiceException;
    
    void update(CommLogBean user) throws ServiceException;
    
    CommLogBean get(Integer id);
    
    void delete(Integer id);
    
    List<CommLogBean> search(CommLogVO vo);
    
    Integer searchNum(CommLogVO vo);

    List<CommLogBean> findAll();

}
