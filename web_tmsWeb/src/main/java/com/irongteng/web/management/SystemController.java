package com.irongteng.web.management;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.irongteng.annotation.Permission;
import com.irongteng.persistence.BaseConditionVO;
import com.irongteng.service.Role;
import com.irongteng.web.BaseController;


/**
 * 系统管理
 * 
 * @author tdx
 */
@Controller("management.systemController")
@RequestMapping(value="/management/system")
public class SystemController extends BaseController{
    
    @RequestMapping()
    @Permission({Role.ADMIN_ROLE,Role.MANAGER_ROLE})
    public String list(BaseConditionVO vo,HttpServletRequest request,HttpServletResponse response, Model model) {
        
        return "/management/system";
    }
    
   
}
