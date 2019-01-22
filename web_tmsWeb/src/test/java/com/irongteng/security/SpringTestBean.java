package com.irongteng.security;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class SpringTestBean {
    
    private String driver    = null;
    private String url       = null;
    private String username  = null;
    private String password  = null;
    private String maxActive = null;
    private String maxIdle   = null;
    
    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(String maxActive) {
        this.maxActive = maxActive;
    }

    public String getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(String maxIdle) {
        this.maxIdle = maxIdle;
    }
    //  get set 方法略

    /**
     * 重写toString方法 观察测试结果用
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("driver", driver)
            .append("url", url).append("username", username).append("password", password)
            .append("maxActive",maxActive).append("maxIdle",maxIdle)
            .toString();
    }

}