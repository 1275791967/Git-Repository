package com.irongteng.service;

import java.util.List;

import com.irongteng.persistence.BaseConditionVO;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;

public interface UserService extends BusinessObjectServiceMgr {
    String SERVICE_NAME = "userServiceMgr";

    /**
     * 后台添加用户
     * 
     * @param user
     */
    Integer add(UserBean user) throws ServiceException;

    void update(UserBean user) throws ServiceException;
     
    UserBean get(int id);

    UserBean getByUsername(String username);

    void delete(int id);

    List<UserBean> search(BaseConditionVO vo);

    Integer searchNum(BaseConditionVO vo);

    /**
     * 激活一个用户
     * 
     * @param id
     */
    void active(int id);

    /**
     * 禁用一个用户
     * 
     * @param id
     */
    void inActive(int id);

}
