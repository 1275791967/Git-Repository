/*
 * Powered By [dwz4j-framework]
 * Web Site: http://j-ui.com
 * Google Code: http://code.google.com/p/dwz4j/
 * Generated 2012-09-10 08:51:33 by code generator
 */
package com.irongteng.protocol.common;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.irongteng.protocol.ParamValueObject;

public class FddParamValueObject  extends ParamValueObject{
    
    private String type;
    private String protocolID;
    private String imsi;
    
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getProtocolID() {
        return protocolID;
    }
    public void setProtocolID(String protocolID) {
        this.protocolID = protocolID;
    }
    public String getImsi() {
        return imsi;
    }
    public void setImsi(String imsi) {
        this.imsi = imsi;
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
        .append("type",getType())
        .append("protocol",getProtocolID())
        .append("imsi",this.getImsi())
        .append("cityCode",this.getCityCode())
        .append("deviceName",this.getDeviceName())
        .toString();
    }
}
