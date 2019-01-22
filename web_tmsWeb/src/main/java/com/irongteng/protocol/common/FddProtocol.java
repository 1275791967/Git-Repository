package com.irongteng.protocol.common;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irongteng.protocol.Protocol;

/**
 * @author lvlei
 * Fdd协议
 * 
 */
public class FddProtocol implements Protocol<FddParamValueObject> {
    
    private final Logger logger = LoggerFactory.getLogger(FddProtocol.class);

    private String errorMsg; // 记录组装或解析数据包的错误信息
    
    /**
     * 实现Protocol接口的方法     * 
     * @param paramObject
     *            协议中规定的基本信息
     * @return 返回组包的字节流
     */
    @Override
    public byte[] encode(FddParamValueObject paramObject) {
        try {
            return (paramObject.getType() 
                    + paramObject.getProtocolID() 
                    + paramObject.getImsi() + "#"
                    + paramObject.getCityCode() + "," + paramObject.getDeviceName() + "#").getBytes("gbk");
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
        return null;
    }
    
    /**
     * 解析数据内容
     * 
     * @param dataPackage
     *            需要解析的字节流
     * @param paramObject
     *            存放协议中规定的基本信息
     * @return 返回解析结果，true表示成功，false表示失败
     * 
     */
    @Override
    public boolean decode(byte[] dataPackage, FddParamValueObject paramObject) {
        
        try {
            String data = new String(dataPackage, "GBK");
            
            String type = data.substring(0, 2);
            String protocol = data.substring(2,4);
            
            String content = data.substring(4);
            String [] baseInfo = content.split("#");
            String imsi = baseInfo[0];
            
            paramObject.setType(type);
            paramObject.setProtocolID(protocol);
            paramObject.setImsi(imsi);
            
            return true;
        } catch (Exception e) {
            errorMsg = "解析数据包发生异常";
            logger.info(errorMsg);
            return false;
        }
    }

    /**
     * 实现Protocol接口的方法
     */
    @Override
    public String getParseMessage() {
        // 返回errorMsg的值
        return errorMsg;

    }
    
    @Override
    public Map<Short, byte[]> encodeMap(FddParamValueObject paramObject) {
        return null;
    }
}
