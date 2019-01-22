package dwz.framework.context;

import com.irongteng.service.UserBean;

import dwz.business.website.Website;

public class DefaultAppContext implements AppContext {

    private UserBean user = null;

    private Website website = null;

    public DefaultAppContext() {
    }

    @Override
    public UserBean getUser() {
        return this.user;
    }

    @Override
    public void setUser(UserBean user) {
        this.user = user;
    }

    @Override
    public Website getWebsite() {
        return website;
    }

    @Override
    public void setWebsite(Website website) {
        this.website = website;
    }

}
