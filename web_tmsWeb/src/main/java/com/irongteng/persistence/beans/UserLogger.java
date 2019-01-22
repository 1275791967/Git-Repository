package com.irongteng.persistence.beans;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import dwz.dal.object.AbstractDO;

public class UserLogger extends AbstractDO{
    
    private static final long serialVersionUID = 1024L;
    private Integer id;
    private String loginIPorPort;//登录主机端口
    private String loginHostName;//登录主机名
    private Date   loginTime;
    private Date   exitTime;
    private Integer loginStatus;//1-登录 2-退出
    private User user; 
    
    public UserLogger(){
        
    }
    public UserLogger(Integer id){
         this.id = id;
    }
    
    @Override
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getLoginIPorPort() {
        return this.loginIPorPort;
    }

    public void setLoginIPorPort(String loginIPorPort) {
        this.loginIPorPort = loginIPorPort;
    }

    public String getLoginHostName() {
        return this.loginHostName;
    }

    public void setLoginHostName(String loginHostName) {
        this.loginHostName = loginHostName;
    }

    public Date getLoginTime() {
        return this.loginTime;
    }

    public void setLoginTime(Date value) {
        this.loginTime = value;
    }
    
    public Date getExitTime() {
        return exitTime;
    }
    public void setExitTime(Date exitTime) {
        this.exitTime = exitTime;
    }
    public Integer getLoginStatus() {
        return this.loginStatus;
    }

    public void setLoginStatus(Integer value) {
        this.loginStatus = value;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("user",getUser())
            .append("LoginIPorPort",getLoginIPorPort())
            .append("LoginHostName",getLoginHostName())
            .append("LoginTime",getLoginTime())
            .append("ExitTime",getExitTime())
            .append("LoginStatus",getLoginStatus())
            .toString();
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }
    
    @Override
    public boolean equals(Object obj){
        if(obj instanceof UserLogger == false) return false;
        if(this == obj) return true;
        UserLogger other = (UserLogger)obj;
        return new EqualsBuilder()
            .append(getId(),other.getId())
            .isEquals();
    }

}
