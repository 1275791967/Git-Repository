
package com.irongteng.protocol.common;

import java.util.Map;

import com.irongteng.protocol.ParamValueObject;

public class CommonParamValueObject extends ParamValueObject {
    private int deviceID;
    
    private Byte versionID; 
    private Byte commandID;
    private Byte answerID;
    private Map<Short, CommonMonitorObject> dataMap;
    private Integer dataSize;  //数据尺寸
    
    public Byte getVersionID() {
        return versionID;
    }
    public void setVersionID(Byte versionID) {
        this.versionID = versionID;
    }
    public Byte getCommandID() {
        return commandID;
    }
    public void setCommandID(Byte commandID) {
        this.commandID = commandID;
    }
    public Byte getAnswerID() {
        return answerID;
    }
    public void setAnswerID(Byte answerID) {
        this.answerID = answerID;
    }
    public Map<Short, CommonMonitorObject> getDataMap() {
        return dataMap;
    }
    public void setDataMap(Map<Short, CommonMonitorObject> dataMap) {
        this.dataMap = dataMap;
    }
    public Integer getDataSize() {
        return dataSize;
    }
    public void setDataSize(Integer dataSize) {
        this.dataSize = dataSize;
    }
}
