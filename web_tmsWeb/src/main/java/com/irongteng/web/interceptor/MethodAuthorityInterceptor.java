package com.irongteng.web.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

public class MethodAuthorityInterceptor implements MethodInterceptor {
    
    private static final Logger logger = LoggerFactory.getLogger(SpringLoginInterceptor.class);
    
    private String LOGIN_URL = "/login";
    
    private String LOGOUT_URL = "/passport/logout";
    
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Class<?> clazz = invocation.getThis().getClass();

        if (clazz.isAnnotationPresent(Controller.class)) {
            Controller controller = clazz.getAnnotation(Controller.class);
            String controllerName = controller.value().trim();
            String methodName = invocation.getMethod().getName();

            logger.debug("controllerName:"+controllerName + "methodName:"+methodName);
            /*
            Object[] args = invocation.getArguments();  
            HttpServletRequest request = null;
            HttpServletResponse response = null;
            ActionMapping  mapping = null;
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof HttpServletRequest) request = (HttpServletRequest) args[i];
                if (args[i] instanceof HttpServletResponse) response = (HttpServletResponse) args[i];
                if (args[i] instanceof ActionMapping) mapping = (ActionMapping) args[i];
            }
            
            if (request != null && mapping != null) {
                UserBean user = (UserBean) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
                String userRole = user.getRole().toUpperCase();  //登录人角色
                
                List<String> roleList = new ArrayList<String>();
                Method method = clazz.getDeclaredMethod(methodName, clazz);
                if (method.isAnnotationPresent(Permission.class)) {
                    Permission methodAnno = method.getAnnotation(Permission.class);  
                    
                    Role[] roles = methodAnno.value();
                    for(Role role: roles) {
                        String roleString = role.name();
                        System.out.println(roleString);
                    }
                }
                
                //如果方法上存在授权注释且不包含该用户角色，则不允许执行该方法
                if (roleList.size() > 0 && !roleList.contains(userRole)) {
                    response.sendRedirect(LOGIN_URL);
                    return null;
                }
            }
            */
        }

//        if (noAuthorith) {
//            return null;
//        }
//        SpringContextHolder.getApplicationContext().findAnnotationOnBean(arg0, arg1)
//        HttpServletRequest request = null;  
//        ActionMapping mapping = null;  
//        Object[] args = invocation.getArguments(); 
//        for (int i = 0 ; i < args.length ; i++ ) {  
//            if (args[i] instanceof HttpServletRequest) request = (HttpServletRequest)args[i];  
//            if (args[i] instanceof ActionMapping) mapping = (ActionMapping)args[i];  
//        }
        
        return invocation.proceed();
    }

}
