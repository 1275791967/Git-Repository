package com.irongteng.web.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.irongteng.annotation.Permission;
import com.irongteng.service.Role;
import com.irongteng.service.UserBean;

import dwz.common.util.ServerInfo;
import dwz.framework.config.Constants;
import dwz.framework.identity.Validator;
/**
 * 用户权限验证
 * 
 * @author lvlei
 *
 */
public class PermissionInterceptor extends HandlerInterceptorAdapter {
    
    private String loginUrl = "/login";
    
    final Logger logger = LoggerFactory.getLogger(getClass());
 
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        
        if (handler instanceof HandlerMethod) {
                
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Class<?> clazz = handlerMethod.getBeanType();
            
            if (clazz.isAnnotationPresent(Controller.class)) {
                Controller controller = clazz.getAnnotation(Controller.class);
                String controllerName = controller.value().trim();
                //String methodName = invocation.getMethod().getName();
                Permission author = handlerMethod.getMethodAnnotation(Permission.class);
                
                if(null == author) { //没有声明权限,放行
                    return true;
                }
                
                HttpSession session = req.getSession();
                Validator validator = Validator.getInstance();
                validator.init(req.getSession());
                
                if (validator.validate()) {
                    UserBean user = (UserBean) session.getAttribute(Constants.AUTHENTICATION_KEY);
                    String userRole = user.getRole().toUpperCase();  //登录人角色
                    
                    logger.debug("user role:" + userRole);
                    
                    boolean permissionFlag = false;
                    
                    Role[] roles = author.value();
                    for(Role role: roles) {
                        String roleString = role.name();
                        if (userRole.equalsIgnoreCase(roleString)) {
                            permissionFlag = true;  //有权访问
                            break;
                        }
                    }
                    
                    if ( !permissionFlag ) {
                        StringBuffer sb = req.getRequestURL();
                        String query = req.getQueryString();
                        if (query != null && !"".equals(query)) {
                            sb.append('?').append(query);
                        }
                        
                        String backToUrl = sb.toString();
                        
                        if (ServerInfo.isAjax(req) || req.getParameter("ajax") != null) {
                            res.setContentType("text/html;charset=utf-8");
                            PrintWriter out = res.getWriter();
                            out.println("{\"statusCode\":\"301\", \"message\":\"你没有权限访问!\"}");
                        } else {
                            res.sendRedirect(res.encodeRedirectURL(req.getContextPath() + this.loginUrl));
                        }
                        validator.confirm();
                        validator.clear();
                        return false;
                    }
                } else {
                    validator.cancel();
                }
            }
        }
        return true;
    }
 
}