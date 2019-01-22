package com.irongteng.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.irongteng.persistence.BaseConditionVO;

import dwz.framework.junit.BaseJunitCase;
import dwz.framework.sys.exception.ServiceException;

public class UserServiceTest extends BaseJunitCase{
    
    @Autowired
    private UserService service;
    
    @Test
    public void testAdd() {
        UserBean bean = new UserBean();
        bean.setUsername("user");
        bean.setPassword("device2005_1");
        bean.setRole(Role.USER_ROLE.toString());
        bean.setEmail("user@163.com");
        bean.setPhone("12345678");
        try {
            service.add(bean);
            //System.out.println("deviceID:" + userMgr.add(device));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testUpdate() {
        UserBean bean = new UserBean();
        bean.setId(1);
        bean.setUsername("user123");
        bean.setPassword("device2005_1");
        bean.setRole(Role.MANAGER_ROLE.toString());
        bean.setEmail("user123@163.com");;
        bean.setPhone("1234567813");
        try {
            service.update(bean);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testGet() {
        int id = 1;
        UserBean bean = service.get(id);
        System.out.println(bean);
    }
    @Test
    public void testGetByUsername() {
        String username = "admin";
        UserBean bean =  service.getByUsername(username);
        System.out.println(bean);
    }
    @Test
    public void testDelete() {
        int id = 1;
        service.delete(id);
    }
    
    @Test
    public void testSearch() {
        BaseConditionVO vo = new BaseConditionVO();
        List<UserBean> beans = service.search(vo);
        System.out.println("size:" + beans.size());
        beans.forEach(bean->System.out.println(bean));
    }
    
    @Test
    public void testSearchNum() {
        BaseConditionVO vo = new BaseConditionVO();
        
        Integer num = service.searchNum(vo);
        System.out.println("num:" + num);
    }
}
