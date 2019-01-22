package com.irongteng.persistence.mapper;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.irongteng.persistence.beans.User;
import com.irongteng.service.Role;

import dwz.framework.junit.DaoJUnitBase;

/** 
 * dao层测试例子 
 * @author 吕雷 
 * 
 */  
public class UserMapperTest extends DaoJUnitBase {  
    
    @Autowired  
    private UserMapper userMapper;  
    
    @Test  
    public void testLoad() {   
        User user = userMapper.load(1);  
        System.out.println(user);
    }
    

    @Test  
    public void testAdd() {
        User model = new User();
        model.setUsername("user1");
        model.setPassword("123456");
        model.setRole(Role.USER_ROLE.name());
        model.setInsertDate(new Date());
        model.setUpdateDate(new Date());
        model.setStatus("ACTIVE");
        
        
        userMapper.insert(model);  
        System.out.println(model.getId());
    }
} 