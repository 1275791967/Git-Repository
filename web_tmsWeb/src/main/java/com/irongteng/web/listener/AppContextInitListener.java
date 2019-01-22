package com.irongteng.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.irongteng.ipms.pool.ServerInitialSetting;

import dwz.framework.spring.SpringContextHolder;

public class AppContextInitListener implements ServletContextListener {
    
    private static SpringContextHolder springContextHolder = new SpringContextHolder();  

    private static ServerInitialSetting serverInitialSetting;
    
    @Override
    public void contextInitialized(ServletContextEvent event) {

        ServletContext context = event.getServletContext();
        //初始化Spring baean
        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(context);
        //初始化springContext
        springContextHolder.setApplicationContext(applicationContext);
        //初始化服务
        if (serverInitialSetting != null) {
            serverInitialSetting.destroy();
            serverInitialSetting = null;
        }
        serverInitialSetting = ServerInitialSetting.getInstance();
        serverInitialSetting.init();
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        //停止初始化的服务
        serverInitialSetting = ServerInitialSetting.getInstance();
        serverInitialSetting.destroy();
        //System.exit(0);
    }
    
}
