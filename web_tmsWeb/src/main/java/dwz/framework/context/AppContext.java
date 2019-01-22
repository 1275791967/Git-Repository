package dwz.framework.context;

import com.irongteng.service.UserBean;

import dwz.business.website.Website;

public interface AppContext {

    UserBean getUser();

    void setUser(UserBean user);

    Website getWebsite();

    void setWebsite(Website website);

}
