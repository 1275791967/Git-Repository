/*
 * Powered By [dwz4j-framework]
 * Web Site: http://j-ui.com
 * Google Code: http://code.google.com/p/dwz4j/
 * Generated 2012-09-10 08:51:33 by code generator
 */
package com.irongteng.service;

import java.util.Date;

import com.irongteng.persistence.beans.User;

import dwz.common.util.EnumUtils;
import dwz.framework.sys.business.AbstractBusinessObject;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class UserBean extends AbstractBusinessObject {
    private static final long serialVersionUID = 1L;
    private final User user;
    private String newPassword;

    /* generateConstructor */
    public UserBean() {
        this.user = new User();
        this.user.setStatus(UserStatus.ACTIVE.toString());
    }
    
    public UserBean(User user) {
        this.user = user!=null ? user : new User();
    }

    public User getUser() {
        return this.user;
    }

    @Override
    public Integer getId() {
        return this.user.getId();
    }

    public void setId(Integer id) {
        this.user.setId(id);
    }
    public String getUsername() {
        return this.user.getUsername();
    }

    public void setUsername(String username) {
        this.user.setUsername(username);
    }

    public String getPassword() {
        return this.user.getPassword();
    }

    public void setPassword(String password) {
        this.user.setPassword(password);
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRole() {
        return this.user.getRole();
    }

    public void setRole(String role) {
        this.user.setRole(role);
    }
    
    public String getEmail() {
        return this.user.getEmail();
    }

    public void setEmail(String email) {
        this.user.setEmail(email);
    }

    public String getPhone() {
        return this.user.getPhone();
    }

    public void setPhone(String phone) {
        this.user.setPhone(phone);
    }
    
    public String getAddress() {
        return this.user.getAddress();
    }
    
    public void setAddress(String address) {
        this.user.setAddress(address);
    }
    public UserStatus getStatus() {
        if (EnumUtils.isDefined(UserStatus.values(), user.getStatus()))
            return UserStatus.valueOf(user.getStatus());

        return UserStatus.INACTIVE;
    }
    
    public Date getInsertDate() {
        return this.user.getInsertDate();
    }

    public Date getUpdateDate() {
        return this.user.getUpdateDate();
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("Username",getUsername())
            .append("Password",getPassword())
            .append("Role",getRole())
            .append("Email",getEmail())
            .append("Phone",getPhone())
            .append("Status",getStatus())
            .append("InsertDate",getInsertDate())
            .append("UpdateDate",getUpdateDate())
            .toString();
    }
}
