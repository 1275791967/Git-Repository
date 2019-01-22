package com.irongteng.service;

import java.util.Date;
import com.irongteng.persistence.beans.OpInfo;

import dwz.framework.sys.business.AbstractBusinessObject;

public class OpInfoBean extends AbstractBusinessObject{
    private static final long serialVersionUID = 1L;
    private  OpInfo opInfo;
    
    @Override
    public Integer getId() {
        return this.opInfo.getId();
    }

    public void setId(Integer id) {
        this.getOpInfo().setId(id);
    }
    
    public OpInfoBean() {
        this.opInfo = new OpInfo();
    }
    
    public OpInfoBean(OpInfo opInfo){
         this.opInfo=opInfo;
    }
    public OpInfo getOpInfo(){
        return  this.opInfo;
    }
    
    public String getOpNo() {
        return this.getOpInfo().getOpNo();
    }

    public void setOpNo(String opNo) {
        this.getOpInfo().setOpNo(opNo);
    }
    
    public String getOpName() {
        return this.getOpInfo().getOpName();
    }

    public void setOpName(String opName) {
        this.getOpInfo().setOpName(opName);
    }
    
    public Date getCreateTime() {
        return this.getOpInfo().getCreateTime();
    }
    
    public void setCreateTime(Date createTime) {
        this.getOpInfo().setCreateTime(createTime);
    }
    
    public Date getUpdateTime() {
        return this.getOpInfo().getUpdateTime();
    }
    
    public void setUpdateTime(Date updateTime) {
        this.getOpInfo().setUpdateTime(updateTime);
    }
    
    public String getRemark() {
        return this.getOpInfo().getRemark();
    }

    public void setRemark(String remark) {
        this.getOpInfo().setRemark(remark);
    }
}
