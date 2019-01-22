package com.irongteng.persistence.beans;

import java.util.Date;

import dwz.dal.object.AbstractDO;

public class OpInfo extends AbstractDO{
    private static final long serialVersionUID = -2549770144648770857L;
    
    private Integer Id;  
    private String opNo;  //运营商编号
    private String opName; //运营商名称
    private Date createTime; //创建时间
    private Date updateTime; //修改时间
    private String remark; //备注
    
    
   public OpInfo(){
        
    }
    
    public OpInfo(Integer id){
        this.Id = id;
    }
    
    @Override
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
