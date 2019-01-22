/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.irongteng.protocol;

import com.irongteng.protocol.common.CommonParamValueObject;
import com.irongteng.protocol.common.CommonProtocol;
import com.irongteng.protocol.common.FddParamValueObject;
import com.irongteng.protocol.common.FddProtocol;
import com.irongteng.protocol.common.LTEParamValueObject;
import com.irongteng.protocol.common.LTEProtocol;
import java.util.Map;

/**
 *
 * @author lvlei
 */
public class ProtocolFactory {
    
    private String errorMessage;
    
    private static final ProtocolFactory INSTANCE = new ProtocolFactory();
    
    private ProtocolFactory () { }
    
    public static ProtocolFactory getIntance() {
        return INSTANCE;
    }
    
    /**
     * 编码
     * @param param
     * @return 
     */
    public byte[] encode(ParamValueObject param) {
        byte[] data = null;
        
        if (param instanceof CommonParamValueObject) {
            CommonParamValueObject common = (CommonParamValueObject) param;
            //协议打包
            Protocol<CommonParamValueObject> proto = new CommonProtocol();
            data = proto.encode(common);
            errorMessage = proto.getParseMessage();
        } else if (param instanceof FddParamValueObject) {
            FddParamValueObject common = (FddParamValueObject) param;
            //协议打包
            Protocol<FddParamValueObject> proto = new FddProtocol();
            data = proto.encode(common);
            errorMessage = proto.getParseMessage();
        } else if (param instanceof LTEParamValueObject) {
            LTEParamValueObject common = (LTEParamValueObject) param;
            //协议打包
            Protocol<LTEParamValueObject> proto = new LTEProtocol();
            data = proto.encode(common);
            errorMessage = proto.getParseMessage();
        }
        return data;
    }
    
    /**
     * 分包编码
     * 
     * @param param
     * @return 
     */
    public Map<Short, byte[]> encodeMap(ParamValueObject param) {
        Map<Short, byte[]> dataMap = null;
        
        if (param instanceof CommonParamValueObject) {
            CommonParamValueObject common = (CommonParamValueObject) param;
            //协议打包
            Protocol<CommonParamValueObject> proto = new CommonProtocol();
            dataMap = proto.encodeMap(common);
            errorMessage = proto.getParseMessage();
        } else if (param instanceof FddParamValueObject) {
            FddParamValueObject common = (FddParamValueObject) param;
            //协议打包
            Protocol<FddParamValueObject> proto = new FddProtocol();
            dataMap = proto.encodeMap(common);
            errorMessage = proto.getParseMessage();
        } else if (param instanceof LTEParamValueObject) {
            LTEParamValueObject common = (LTEParamValueObject) param;
            //协议打包
            Protocol<LTEParamValueObject> proto = new LTEProtocol();
            dataMap = proto.encodeMap(common);
            errorMessage = proto.getParseMessage();
        }
        return dataMap;
    }
    
    /**
     * 解码
     * @param data
     * @param param
     * @return 
     */
    public boolean decode(byte[] data, ParamValueObject param) {
        boolean success = false;
        if (param instanceof CommonParamValueObject) {
            CommonParamValueObject common = (CommonParamValueObject) param;
            //协议打包
            Protocol<CommonParamValueObject> proto = new CommonProtocol();
            success = proto.decode(data, common);
            errorMessage = proto.getParseMessage();
        } else if (param instanceof FddParamValueObject) {
            FddParamValueObject common = (FddParamValueObject) param;
            //协议打包
            Protocol<FddParamValueObject> proto = new FddProtocol();
            success = proto.decode(data, common);
            errorMessage = proto.getParseMessage();
        } else if (param instanceof LTEParamValueObject) {
            LTEParamValueObject common = (LTEParamValueObject) param;
            //协议打包
            Protocol<LTEParamValueObject> proto = new LTEProtocol();
            success = proto.decode(data, common);
            errorMessage = proto.getParseMessage();
        }
        return success;
    }
    
    /**
     * 查询是否有错误信息
     * 
     * @return 
     */
    public String getParseMessage() {
        return errorMessage;
    }
}
