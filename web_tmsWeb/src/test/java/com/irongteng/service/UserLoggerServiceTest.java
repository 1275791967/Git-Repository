package com.irongteng.service;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.irongteng.persistence.BaseConditionVO;
import com.irongteng.service.UserLoggerBean;

import dwz.framework.junit.BaseJunitCase;
import dwz.framework.sys.exception.ServiceException;

public class UserLoggerServiceTest extends BaseJunitCase {
    
    @Autowired
    private UserLoggerService service;
    
    @Test
    public void testAdd() {
        UserLoggerBean bean = new UserLoggerBean();
        bean.setUserID(1);
        bean.setLoginTime(new Date());
        bean.setLoginIPorPort("192.168.1.44");
        bean.setLoginHostName("lvlei-pc:192.168.1.44");
        bean.setLoginStatus(1);;
        try {
            System.out.println(service.add(bean));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testUpdate() {
        UserLoggerBean bean = new UserLoggerBean();
        bean.setId(1);
        bean.setExitTime(new Date());
        
        try {
            service.update(bean);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testUpdate_ON_UerExist() {
        UserLoggerBean bean = new UserLoggerBean();
        bean.setId(1);
        bean.setExitTime(new Date());
        
        try {
            service.update(bean);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testGet() {
        int id = 1;
        UserLoggerBean db = service.get(id);
        System.out.println(db.toString());
    }
    
    @Test
    public void testDelete() {
        int id = 115;
        service.delete(id);
    }

    @Test
    public void testSearch() {
        
        BaseConditionVO vo = new BaseConditionVO();
        vo.setUserID(1);
        List<UserLoggerBean> beans = service.search(vo);
        for (UserLoggerBean bean: beans) {
            System.out.println(bean.toString());
        }
    }
    
    @Test
    public void testSearchNum() {
        BaseConditionVO vo = new BaseConditionVO();
        vo.setUserID(1);
        
        Integer num = service.searchNum(vo);
        System.out.println("num:" + num);
    }
}
