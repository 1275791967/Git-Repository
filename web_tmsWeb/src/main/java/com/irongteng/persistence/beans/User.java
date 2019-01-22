package com.irongteng.persistence.beans;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import dwz.dal.object.AbstractDO;

public class User extends AbstractDO{
    /**
     * 
     */
    private static final long serialVersionUID = -2549770144649770858L;
    private Integer id;
    private String username;
    private String password;
    private String role;
    private String email;
    private String phone;
    private String status; //状态，包括激活和锁定状态
    private String address;
    private Date insertDate;
    private Date updateDate;
    private String remark;
    public User(){
    }

    public User(Integer id){
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    @Override
    public Integer getId() {
        return this.id;
    }
    
    public void setUsername(String value) {
        this.username = value;
    }
    
    public String getUsername() {
        return this.username;
    }
    public void setPassword(String value) {
        this.password = value;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setEmail(String value) {
        this.email = value;
    }
    
    public String getEmail() {
        return this.email;
    }
    public void setPhone(String value) {
        this.phone = value;
    }
    
    public String getPhone() {
        return this.phone;
    }
    public void setStatus(String value) {
        this.status = value;
    }
    
    public String getStatus() {
        return this.status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setInsertDate(Date value) {
        this.insertDate = value;
    }
    
    public Date getInsertDate() {
        return this.insertDate;
    }

    public void setUpdateDate(Date value) {
        this.updateDate = value;
    }
    
    public Date getUpdateDate() {
        return this.updateDate;
    }
    

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof User == false) return false;
        if(this == obj) return true;
        User other = (User)obj;
        return new EqualsBuilder()
            .append(getId(),other.getId())
            .isEquals();
    }
}

