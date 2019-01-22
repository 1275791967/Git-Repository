/*
 * Powered By [dwz4j-framework]
 * Web Site: http://j-ui.com
 * Google Code: http://code.google.com/p/dwz4j/
 * Generated 2012-09-10 08:51:33 by code generator
 */
package com.irongteng.service;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.irongteng.persistence.beans.Attribution;

import dwz.framework.sys.business.AbstractBusinessObject;

public class AttributionBean extends AbstractBusinessObject {
    
    private static final long serialVersionUID = 1L;
    private final Attribution attribution;
    private Integer operator;
    
    /* generateConstructor */
    public AttributionBean() {
        this.attribution = new Attribution();
    }

    public AttributionBean(Attribution callAttribution) {
        this.attribution = callAttribution!=null ? callAttribution : new Attribution();
    }
    public Attribution getCallAttribution() {
        return this.attribution;
    }
    @Override
    public Integer getId() {
        return this.attribution.getId();
    }
    
    public void setId(Integer id) {
        this.attribution.setId(id);
    }

    public Integer getPhoneNO() {
        return this.attribution.getPhoneNO();
    }

    public void setPhoneNO(Integer phoneNO) {
        this.attribution.setPhoneNO(phoneNO);
    }

    public String getProvince() {
        return this.attribution.getProvince();
    }
    
    public void setProvince(String province) {
        this.attribution.setProvince(province);
    }

    public String getCity() {
        return this.attribution.getCity();
    }

    public void setCity(String city) {
        this.attribution.setCity(city);
    }

    public Integer getCellNO() {
        return this.attribution.getCellNO();
    }

    public void setCellNO(Integer cellNO) {
        this.attribution.setCellNO(cellNO);
    }
    public String getRemark() {
        return this.attribution.getRemark();
    }

    public void setRemark(String remark) {
        this.attribution.setRemark(remark);
    }

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
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
            .append("operator",getOperator())
            .toString();
    }
    
}
