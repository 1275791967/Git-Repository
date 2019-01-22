package com.irongteng.service;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.irongteng.persistence.beans.User;
import com.irongteng.persistence.beans.UserLogger;


public class UserLoggerBean{
 
    private final UserLogger userLogger;
    private final User user;
    
    public UserLoggerBean(){
        this.userLogger = new UserLogger();
        this.user = new User();
    }
    public UserLoggerBean(UserLogger userLogger){
         this.userLogger = userLogger!=null ? userLogger : new UserLogger();
         this.user = this.userLogger.getUser() != null ? this.userLogger.getUser() : new User();
    }
    
    public UserLogger getUserLogger() {
        return this.userLogger;
    }
    

    public Integer getId(){
        return this.userLogger.getId();
    }
    
    public void setId(Integer id){
        this.userLogger.setId(id);
    }
    
    public Integer getUserID() {
        return this.user.getId();
    }

    public void setUserID(Integer userId) {
        this.user.setId(userId);
        this.userLogger.setUser(user); 
    }
    public String getUsername() {
        return this.user.getUsername();
    }

    public void setUsername(String username) {
        this.user.setUsername(username);
        this.userLogger.setUser(user); 
    }
    
    public String getLoginIPorPort(){
        return this.userLogger.getLoginIPorPort();
    }
    
    public void setLoginIPorPort(String loginIPorPort){
        this.userLogger.setLoginIPorPort(loginIPorPort);
    }
    
    public String getLoginHostName(){
        return this.userLogger.getLoginHostName();
    }
    
    public void setLoginHostName(String loginHostName){
        this.userLogger.setLoginHostName(loginHostName);
    }
    
    public Date getLoginTime(){
        return this.userLogger.getLoginTime();
    }
    

    public void setLoginTime(Date loginTime){
       this.userLogger.setLoginTime(loginTime);
    }
    
    public Date getExitTime(){
        return this.userLogger.getExitTime();
    }
    
    public void setExitTime(Date exitTime){
        this.userLogger.setExitTime(exitTime);
    }
    
    public Integer getLoginStatus(){
        return this.userLogger.getLoginStatus();
    }
    public void setLoginStatus(int loginStatus){
         this.userLogger.setLoginStatus(loginStatus);
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("userID",getUserID())
            .append("username",getUsername())
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
        if(obj instanceof UserLoggerBean == false) return false;
        if(this == obj) return true;
        UserLoggerBean other = (UserLoggerBean)obj;
        return new EqualsBuilder()
            .append(getId(),other.getId())
            .isEquals();
    }
}
