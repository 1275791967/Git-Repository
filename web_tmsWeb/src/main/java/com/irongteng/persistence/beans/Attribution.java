package com.irongteng.persistence.beans;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import dwz.dal.object.AbstractDO;

public class Attribution extends AbstractDO{
    
    private static final long serialVersionUID = -2549770144649770857L;
    
    private Integer Id;            //监控设备流水号  
    private Integer PhoneNO;        //电话号码段
    private String Province;        //省份
    private String City;            //城市
    private Integer CellNO;         //地区号
    private String Remark;          //备注
    
    public Attribution(){
        
    }
    
    public Attribution(Integer id){
        this.Id = id;
    }

  
    
    @Override
    public Integer getId() {
        return this.Id;
    }
    public void setId(Integer id) {
        this.Id = id;
    }
    
    public Integer getPhoneNO() {
        return PhoneNO;
    }

    public void setPhoneNO(Integer phoneNO) {
        this.PhoneNO = phoneNO;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        this.Province = province;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        this.City = city;
    }

    public Integer getCellNO() {
        return CellNO;
    }

    public void setCellNO(Integer cellNO) {
        this.CellNO = cellNO;
    }
    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        this.Remark = remark;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("phoneNO",getPhoneNO())
            .append("province",getProvince())
            .append("city",getCity())
            .append("cellNO",getCellNO())
            .append("remark",getRemark())
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
        if(obj instanceof Attribution == false) return false;
        if(this == obj) return true;
        Attribution other = (Attribution)obj;
        return new EqualsBuilder()
            .append(getId(),other.getId())
            .isEquals();
    }
}

