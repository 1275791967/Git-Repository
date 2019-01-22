package com.irongteng.persistence.beans;

import javax.xml.crypto.Data;

import dwz.dal.object.AbstractDO;

public class LogType extends AbstractDO{
    private static final long serialVersionUID = -2549770144648770857L;
    private Integer Id; 
    private String name;//名字
    private String status;//类型状态(使用,停用)
    private Data createTime;//创建时间
    private String remark;//备注
    
    public LogType(){
        
    }
    
    public LogType(Integer id){
        this.Id = id;
    }
    
    @Override
    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Data createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    
}
