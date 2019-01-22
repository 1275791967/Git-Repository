package com.irongteng.service;

import java.util.Date;

import com.irongteng.persistence.beans.Customer;

import dwz.framework.sys.business.AbstractBusinessObject;

public class CustomerBean extends AbstractBusinessObject{
    private static final long serialVersionUID = 1L;
    private  Customer customer;

    @Override
    public Integer getId() {
        return this.customer.getId();
    }

    public void setId(Integer id) {
        this.getCustomer().setId(id);
    }
    
    public CustomerBean() {
        this.customer = new Customer();
    }
    
    public CustomerBean(Customer customer){
         this.customer=customer;
    }
    public Customer getCustomer(){
        return  this.customer;
    }
    
    public String getCustomerName() {
        return this.getCustomer().getCustomerName();
    }

    public void setCustomerName(String customerName) {
        this.getCustomer().setCustomerName(customerName);
    }
    
    public String getContact() {
        return this.getCustomer().getContact();
    }
    
    public void setContact(String contact) {
        this.getCustomer().setContact(contact);
    }
    
    public String getAddress() {
        return this.getCustomer().getAddress();
    }
    
    public void setAddress(String address) {
        this.getCustomer().setAddress(address);
    }
    
    public Date getCreateTime() {
        return this.getCustomer().getCreateTime();
    }
    
    public void setCreateTime(Date createTime) {
        this.getCustomer().setCreateTime(createTime);
    }
    
    public Date getUpdateTime() {
        return this.getCustomer().getUpdateTime();
    }
    
    public void setUpdateTime(Date updateTime) {
        this.getCustomer().setUpdateTime(updateTime);
    }
    
    public String getRemark() {
        return this.getCustomer().getRemark();
    }

    public void setRemark(String remark) {
        this.getCustomer().setRemark(remark);
    }
}
