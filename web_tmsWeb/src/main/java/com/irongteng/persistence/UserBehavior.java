package com.irongteng.persistence;

import java.io.Serializable;
import java.util.Date;

import dwz.dal.object.AbstractDO;

public class UserBehavior extends AbstractDO {
    /**
     * 用户行为数据查询
     */
    private static final long serialVersionUID = 1L;
    private String customerName;
    private String behaviorName;
    private Integer custId;
    private Integer behaviorId;
    private String psw;
    private String phone;
    private Integer localBalance;
    private Integer thirdBalance;
    private Date createDate;
    private String custRemark;
    private String status;
    private String Description;
    private String behaviorRemark;
    private Integer customerId;
    private Integer balancedetailId;
    private Integer cutBehaviorId;
    private String balanceType;
    private Date recordDate;
    private String balancedetailRemark;
    
    private String detail;
    
    
    public String getBalancedetailRemark() {
        return balancedetailRemark;
    }
    public void setBalancedetailRemark(String balancedetailRemark) {
        this.balancedetailRemark = balancedetailRemark;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getBehaviorName() {
        return behaviorName;
    }
    public void setBehaviorName(String behaviorName) {
        this.behaviorName = behaviorName;
    }
    public Integer getCustId() {
        return custId;
    }
    public void setCustId(Integer custId) {
        this.custId = custId;
    }
    public Integer getBehaviorId() {
        return behaviorId;
    }
    public void setBehaviorId(Integer behaviorId) {
        this.behaviorId = behaviorId;
    }
    public String getPsw() {
        return psw;
    }
    public void setPsw(String psw) {
        this.psw = psw;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public Integer getLocalBalance() {
        return localBalance;
    }
    public void setLocalBalance(Integer localBalance) {
        this.localBalance = localBalance;
    }
    public Integer getThirdBalance() {
        return thirdBalance;
    }
    public void setThirdBalance(Integer thirdBalance) {
        this.thirdBalance = thirdBalance;
    }
  
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public String getCustRemark() {
        return custRemark;
    }
    public void setCustRemark(String custRemark) {
        this.custRemark = custRemark;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getDescription() {
        return Description;
    }
    public void setDescription(String description) {
        Description = description;
    }
    public String getBehaviorRemark() {
        return behaviorRemark;
    }
    public void setBehaviorRemark(String behaviorRemark) {
        this.behaviorRemark = behaviorRemark;
    }
    public Integer getCustomerId() {
        return customerId;
    }
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
    public Integer getBalancedetailId() {
        return balancedetailId;
    }
    public void setBalancedetailId(Integer balancedetailId) {
        this.balancedetailId = balancedetailId;
    }
    public Integer getCutBehaviorId() {
        return cutBehaviorId;
    }
    public void setCutBehaviorId(Integer cutBehaviorId) {
        this.cutBehaviorId = cutBehaviorId;
    }
    public String getBalanceType() {
        return balanceType;
    }
    public void setBalanceType(String balanceType) {
        this.balanceType = balanceType;
    }
   
    public Date getRecordDate() {
        return recordDate;
    }
    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }
    public String getDetail() {
        return detail;
    }
    public void setDetail(String detail) {
        this.detail = detail;

    }
    @Override
    public Serializable getId() {
        // TODO Auto-generated method stub
        return getBalancedetailId();
    }
    public Integer setId(Integer id) {
        // TODO Auto-generated method stub
        return this.balancedetailId=id;
    }
}
