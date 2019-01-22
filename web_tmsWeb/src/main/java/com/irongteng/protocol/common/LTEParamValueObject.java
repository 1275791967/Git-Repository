package com.irongteng.protocol.common;

import java.util.List;

import com.irongteng.protocol.ParamValueObject;

public class LTEParamValueObject extends ParamValueObject {
    
    private byte commandID;
    private short bodyLen;
    private List<String> imsiList;
    
    public byte getCommandID() {
        return commandID;
    }
    public void setCommandID(byte commandID) {
        this.commandID = commandID;
    }
    public short getBodyLen() {
        return bodyLen;
    }
    public void setBodyLen(short bodyLen) {
        this.bodyLen = bodyLen;
    }
    public List<String> getImsiList() {
        return imsiList;
    }
    public void setImsiList(List<String> imsiList) {
        this.imsiList = imsiList;
    }
    
    @Override
    public String toString() {
        return "LTEParamValueObject [commandID=" + commandID 
                + ", bodyLen=" + bodyLen + ", imsis=" + imsiList 
                + ",DeviceName:" + this.getDeviceName() 
                + ",CityCode:" + this.getCityCode() 
                + "]";
    }
}
