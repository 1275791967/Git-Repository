package com.irongteng.service;

import java.util.List;

import com.irongteng.persistence.OpInfoVO;

import dwz.framework.sys.business.BusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;

public interface OpInfoService  extends BusinessObjectServiceMgr{
    String SERVICE_NAME = "opInfoService";
    
    OpInfoBean queryByName(String opName);
    
    OpInfoBean queryByNo(Integer opNo);
    
    OpInfoBean get(Integer id);
    
    List<OpInfoBean> search(OpInfoVO vo);
    
    Integer searchNum(OpInfoVO vo);

    Integer add(OpInfoBean bean) throws ServiceException;

    void update(OpInfoBean bean)throws ServiceException;

    void delete(Integer id);

}
