package com.irongteng.web.interceptor;

import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import dwz.common.util.ServerInfo;
import dwz.framework.identity.Validator;

public class SessionValidateInterceptor extends HandlerInterceptorAdapter {
    
    private String loginUrl = "/login";
    
    final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        if (handler instanceof HandlerMethod) {
                
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Class<?> clazz = handlerMethod.getBeanType();
            
            if (clazz.isAnnotationPresent(Controller.class)) {
                Controller controller = clazz.getAnnotation(Controller.class);
                String controllerName = controller.value().trim();
                //String methodName = invocation.getMethod().getName();
                
                Validator validator = Validator.getInstance();
                validator.init(request.getSession());
                
                if (!validator.validate()) {
                    validator.cancel();
                    
                    StringBuffer sb = request.getRequestURL();
                    String query = request.getQueryString();
                    if (query != null && !"".equals(query)) {
                        sb.append('?').append(query);
                    }

                    String backToUrl = sb.toString();
                    logger.debug(("backToUrl = " + backToUrl));
                    if (ServerInfo.isAjax(request) || request.getParameter("ajax") != null) {
                        response.setContentType("text/html;charset=utf-8");
                        PrintWriter out = response.getWriter();
                        out.println("{\"statusCode\":\"301\", \"message\":\"Session Timeout! Please re-sign in!\"}");
                    } else {
                        response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + this.loginUrl + URLEncoder.encode(backToUrl, "UTF-8")));
                    }
                    return false;
                }
                
                validator.confirm();

                logger.debug("validate authentication finished, the authentication has permission to enter this uri.");
                
                validator.clear();
            }
        }
        return true;
    }
}