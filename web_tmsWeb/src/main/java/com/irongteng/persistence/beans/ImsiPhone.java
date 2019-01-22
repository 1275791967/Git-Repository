package com.irongteng.persistence.beans;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import dwz.dal.object.AbstractDO;

public class ImsiPhone extends AbstractDO{
    
    private static final long serialVersionUID = -2549770144648770857L;
    
    private Integer ID;            //监控设备流水号  
    private String Imsi;            //imsi号码
    private String Phone;           //电话号码
    private Date RecordTime;        //记录时间
    private String Province;        //省
    private String OwnID;            //身份证号码
    private String Address;         //地址
    private String Name;            //名字
    private String City;            //市
    private String Remark;          //备注
    private String imei;        //imei号码
    private String mac;         //mac地址
    private String imsi_MSISDN;  //imsi加密码
   
    public ImsiPhone toClone(){
        ImsiPhone imsiCall = new ImsiPhone();
        imsiCall.ID = this.ID;
        imsiCall.Imsi = this.Imsi;
        imsiCall.Phone = this.Phone;
        imsiCall.RecordTime = this.RecordTime;
        imsiCall.Province = this.Province;
        imsiCall.City = this.City;
        imsiCall.Remark = this.Remark;
        imsiCall.Name = this.Name;
        imsiCall.imei = this.imei;
        imsiCall.mac = this.mac;
        imsiCall.OwnID = this.OwnID;
        imsiCall.Address = this.Address;
        imsiCall.imsi_MSISDN = this.imsi_MSISDN;
        return imsiCall;
    }
    
    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }


    public String getImsi_MSISDN() {
        return imsi_MSISDN;
    }

    public void setImsi_MSISDN(String imsi_MSISDN) {
        this.imsi_MSISDN = imsi_MSISDN;
    }

    public ImsiPhone(){
        
    }
    
    public ImsiPhone(Integer id){
        this.ID = id;
    }

    public void setId(Integer id) {
        this.ID = id;
    }
    
    @Override
    public Integer getId() {
        return this.ID;
    }
    
    public String getImsi() {
        return Imsi;
    }

    public void setImsi(String imsi) {
        this.Imsi = imsi;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        this.Phone = phone;
    }

    public Date getRecordTime() {
        return RecordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.RecordTime = recordTime;
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
    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        this.Remark = remark;
    }

    public String getOwnID() {
        return OwnID;
    }

    public void setOwnID(String ownID) {
        this.OwnID = ownID;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("Id",getId())
            .append("imsi",getImsi())
            .append("phone",getPhone())
            .append("recordTime",getRecordTime())
            .append("province",getProvince())
            .append("city",getCity())
            .append("remark",getRemark())
            .append("imei",getImei())
            .append("mac",getMac())
            .append("imsi_MSISDN",getImsi_MSISDN())
            .append("ownID",getOwnID())
            .append("address",getAddress())
            .append("name",getName())
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
        if(obj instanceof ImsiPhone == false) return false;
        if(this == obj) return true;
        ImsiPhone other = (ImsiPhone)obj;
        return new EqualsBuilder()
            .append(getId(),other.getId())
            .isEquals();
    }
}

