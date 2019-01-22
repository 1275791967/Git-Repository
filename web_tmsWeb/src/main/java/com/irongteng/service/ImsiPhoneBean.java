
package com.irongteng.service;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.irongteng.persistence.beans.ImsiPhone;

import dwz.framework.sys.business.AbstractBusinessObject;

public class ImsiPhoneBean extends AbstractBusinessObject {
    
    private static final long serialVersionUID = 1L;
    private final ImsiPhone imsiCall;
    private Integer operator;
    
    public ImsiPhoneBean() {
        this.imsiCall = new ImsiPhone();
    }

    public ImsiPhoneBean(ImsiPhone imsiCall) {
        this.imsiCall = imsiCall;
    }
    public ImsiPhone getImsiPhone() {
        return this.imsiCall;
    }
    @Override
    public Integer getId() {
        return this.imsiCall.getId();
    }
    
    public void setId(Integer id) {
        this.imsiCall.setId(id);
    }

    public String getImsi() {
        return this.imsiCall.getImsi();
    }

    public void setImsi(String imsi) {
        this.imsiCall.setImsi(imsi);
    }

    public String getPhone() {
        return this.imsiCall.getPhone();
    }

    public void setPhone(String phone) {
        this.imsiCall.setPhone(phone);
    }

    public Date getRecordTime() {
        return this.imsiCall.getRecordTime();
    }
    
    public void setRecordTime(Date recordTime) {
        this.imsiCall.setRecordTime(recordTime);
    }
    public String getProvince() {
        return this.imsiCall.getProvince();
    }
    
    public void setProvince(String province) {
        this.imsiCall.setProvince(province);
    }

    public String getCity() {
        return this.imsiCall.getCity();
    }

    public void setCity(String city) {
        this.imsiCall.setCity(city);
    }

    public void setRemark(String remark) {
        this.imsiCall.setRemark(remark);
    }

    public String getRemark() {
        return this.imsiCall.getRemark();
    }
    
    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }
    
    public String getImei() {
        return this.imsiCall.getImei();
    }

    public void setImei(String imei) {
        this.imsiCall.setImei(imei);
    }

    public String getMac() {
        return this.imsiCall.getMac();
    }

    public void setMac(String mac) {
        this.imsiCall.setMac(mac);
    }

    public String getImsi_MSISDN() {
        return this.imsiCall.getImsi_MSISDN();
    }

    public void setImsi_MSISDN(String imsi_MSISDN) {
        this.imsiCall.setImsi_MSISDN(imsi_MSISDN);
    }
    
    public String getOwnID() {
        return this.imsiCall.getOwnID();
    }

    public void setOwnID(String ownID) {
        this.imsiCall.setOwnID(ownID);
    }

    public String getAddress() {
        return this.imsiCall.getAddress();
    }

    public void setAddress(String address) {
        this.imsiCall.setAddress(address);
    }

    public String getName() {
        return this.imsiCall.getName();
    }

    public void setName(String name) {
        this.imsiCall.setName(name);
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
            .append("operator",getOperator())
            .append("imei",getImei())
            .append("mac",getMac())
            .append("imsi_MSISDN",getImsi_MSISDN())
            .append("ownID",getOwnID())
            .append("address",getAddress())
            .append("name",getName())
            .toString();
    }
}
