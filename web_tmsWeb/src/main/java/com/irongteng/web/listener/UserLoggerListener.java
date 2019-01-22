package com.irongteng.web.listener;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.irongteng.service.UserBean;
import com.irongteng.service.UserLoggerBean;
import com.irongteng.service.UserLoggerService;
import com.irongteng.service.UserLoggerStatus;

import dwz.framework.config.Constants;
import dwz.framework.spring.SpringContextHolder;
import dwz.framework.sys.exception.ServiceException;

public class UserLoggerListener implements HttpSessionAttributeListener {
    
    private static Map<String, UserInfo> userLoggerMap = new ConcurrentHashMap<String, UserInfo>();
    
    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        
        if(Constants.AUTHENTICATION_KEY.equals(event.getName())) {
            
            HttpSession ses = event.getSession();
            UserBean user = (UserBean) ses.getAttribute(Constants.AUTHENTICATION_KEY);
            
            if (user != null) {
                
                try {
                    final String id = ses.getId() + ses.getCreationTime();
                    
                    if (userLoggerMap.containsKey(id)) return;
                    
                    UserInfo info = new UserInfo();
                    info.setUserBean(user);
                    
                    UserLoggerBean bean = new UserLoggerBean();
                    bean.setLoginTime(new Date());
                    bean.setLoginStatus(UserLoggerStatus.ONLINE);
                    bean.setUserID(user.getId());

                    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                    
                    String host = request.getRemoteHost();
                    String addr = request.getRemoteAddr();
                    Integer port = request.getRemotePort();
                    bean.setLoginHostName(host);
                    bean.setLoginIPorPort(addr + ":" + port);
                    
                    UserLoggerService service = SpringContextHolder.getBean(UserLoggerService.SERVICE_NAME);
                    // 添加用户登录日志
                    int logID = service.add(bean);
                    info.setId(logID);

                    userLoggerMap.put(id, info); // 添加用户
                    
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        
        if (Constants.AUTHENTICATION_KEY.equals(event.getName())) {
            
            HttpSession ses = event.getSession();
            
            String id = ses.getId() + ses.getCreationTime();
            
            synchronized (this) {
                removeUserInfo(id);
            }
            ses.invalidate();
        }
        
    }
    
    @Override
    public void attributeReplaced(HttpSessionBindingEvent se) {
        
    }
    
    public synchronized static void removeUserInfo(String id) {
        
        if (userLoggerMap.containsKey(id)) {
            
            UserInfo info = userLoggerMap.get(id);
            UserLoggerBean bean = new UserLoggerBean();
            bean.setId(info.getId());
            bean.setExitTime(new Date());
            bean.setLoginStatus(UserLoggerStatus.OFFLINE);
            UserLoggerService service = SpringContextHolder.getBean(UserLoggerService.SERVICE_NAME);
            
            try {
                service.update(bean);
                
                userLoggerMap.remove(id); // 从用户组中移除掉，用户组为一个map
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }
    }
    
    class UserInfo {
        
        private UserBean user;
        private Integer id;

        public UserBean getUser() {
            return user;
        }
        
        public void setUserBean(UserBean user) {
            this.user = user;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

    }
}
