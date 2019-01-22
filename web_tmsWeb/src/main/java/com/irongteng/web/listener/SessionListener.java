package com.irongteng.web.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {
    
    @Override
    public void sessionCreated(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        String id = session.getId() + session.getCreationTime();
        //System.out.println("session id:" + id);
    }
    
    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        String id = session.getId() + session.getCreationTime();
        
        UserLoggerListener.removeUserInfo(id);
    }
    
}
