package com.irongteng.web.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.portlet.bind.annotation.ActionMapping;

import com.irongteng.annotation.Permission;
import com.irongteng.service.Role;
import com.irongteng.service.UserBean;

import dwz.framework.config.Constants;

public class SpringLoginInterceptor implements MethodInterceptor {
    
    private static final Logger logger = LoggerFactory.getLogger(SpringLoginInterceptor.class);

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        
        logger.info("拦截开始！");
        
        Class<?> clazz = invocation.getThis().getClass();

        if (clazz.isAnnotationPresent(Controller.class)) {
            Controller controller = clazz.getAnnotation(Controller.class);
            String controllerName = controller.value().trim();
            String methodName = invocation.getMethod().getName();

            logger.debug("controllerName:"+controllerName + "methodName:"+methodName);
            
            Object[] args = invocation.getArguments();  
            HttpServletRequest request = null;
            HttpServletResponse response = null;
            ActionMapping  mapping = null;
            for (Object arg : args) {
                if (arg instanceof HttpServletRequest) {
                    request = (HttpServletRequest) arg;
                }
                if (arg instanceof HttpServletResponse) {
                    response = (HttpServletResponse) arg;
                }
                if (arg instanceof ActionMapping) {
                    mapping = (ActionMapping) arg;
                }
            }
            if (request != null && mapping != null) {
                UserBean user = (UserBean) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
                String userRole = user.getRole().toUpperCase();  //登录人角色
                String url = request.getRequestURI();   
                HttpSession session = request.getSession(true);     
                String usercode = request.getRemoteUser();// 登录人
                String user_role = (String)session.getAttribute("user_role");//登录人角色
                
                if (usercode == null || usercode.equals("")) {
                    if ( !url.contains("Login") && !url.contains("login") ) {
                        //return ((Object) mapping).findForward("loginInterceptor");
                    }
                }
                
                Method[] oMethods = clazz.getDeclaredMethods();
                for (Method method: oMethods) {
                    if (method.isAnnotationPresent(Permission.class)) {
                        Permission methodAnno = method.getAnnotation(Permission.class );  
                        logger.debug("method name:" + method.getName() +" Method's value: "  + methodAnno.value()); 
                        Role[] roles = methodAnno.value();
                        for(Role role: roles) {
                            String roleString = role.name();
                            System.out.println(roleString);
                        }
                    }
                }
            }
        }
        return invocation.proceed();
    }
}