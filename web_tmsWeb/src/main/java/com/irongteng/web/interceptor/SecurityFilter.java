package com.irongteng.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SecurityFilter extends HandlerInterceptorAdapter  {

    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
        System.out.println("==>>Begin to Filter session====");
        HttpSession session = request.getSession();
        String user = (String) session.getAttribute("user");
        System.out.println("===??Current User=="+user);
        String curPath=request.getRequestURL().toString();
        System.out.println("===>> curpath:"+curPath);
        if (curPath.indexOf("GPS/User/Index")>=0){
            return true;
        }
        if(null==user || "".equals(user)){
            return true;
            /**
             * handle session and security if you want.
             */
            //request.getRequestDispatcher("/index.jsp").forward(request, response);
        }        
        return super.preHandle(request, response, handler);
    }
    
    

}