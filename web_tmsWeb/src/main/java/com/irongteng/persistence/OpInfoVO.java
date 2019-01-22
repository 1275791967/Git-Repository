package com.irongteng.persistence;

import java.util.Date;

public class OpInfoVO  extends BaseConditionVO{
    
    private Integer Id;  
    private String opNo;  //运营商编号
    private String opName; //运营商名称
    private Date createTime; //创建时间
    private Date updateTime; //修改时间
    private String remark; //备注
    public Integer getId() {
        return Id;
    }
    public void setId(Integer id) {
        Id = id;
    }
    public String getOpNo() {
        return opNo;
    }
    public void setOpNo(String opNo) {
        this.opNo = opNo;
    }
    public String getOpName() {
        return opName;
    }
    public void setOpName(String opName) {
        this.opName = opName;
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
