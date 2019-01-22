package com.irongteng.persistence.beans;

import java.util.Date;

import dwz.dal.object.AbstractDO;

public class Customer extends AbstractDO{
    private static final long serialVersionUID = -2549770144648770857L;
    private Integer ID; 
    private String customerName; //客户名称
    private String contact; //联系方式
    private String address; //地址
    private Date createTime;//创建时间
    private Date updateTime;//修改时间
    private String remark; //备注
    
    public Customer(){
        
    }
    public Customer(Integer id){
        this.ID = id;
    }
    @Override
    public Integer getId() {
        return ID;
    }

    public void setId(Integer id) {
        ID = id;
    }
    

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

  
    

}
