package com.irongteng.protocol.common;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.irongteng.protocol.Protocol;

import dwz.common.util.StringUtils;

public class LTEProtocol implements Protocol<LTEParamValueObject>{
    
    private String errorMsg; // 记录组装或解析数据包的错误信息
    
    /**
     * 对操作对象进行封装     * 
     * @param paramObject 协议中规定的信息
     * @return 返回组包的字节流
     */
    @Override
    public byte[] encode(LTEParamValueObject paramObject) {

        try {
            ByteBuffer buffer = ByteBuffer.allocate(paramObject.getBodyLen()+4).order(ByteOrder.LITTLE_ENDIAN);
            buffer.put(paramObject.getCommandID());
            buffer.put((byte)0);
            buffer.putShort(paramObject.getBodyLen());
            
            paramObject.getImsiList().stream().map((imsi) -> {
                byte[] bytes = new byte[6];
                buffer.put(bytes);
                byte [] imsiBytes = StringUtils.String2FixedBytes(imsi, 16);
                return imsiBytes;
            }).forEachOrdered((imsiBytes) -> {
                buffer.put(imsiBytes);
            });
            return buffer.array();
        } catch (Exception e) {
            // 解析VP层数据包发生异常";
            errorMsg = "解析数据包发生异常";
        }
        return null;
    }
    
    /**
     * 对操作对象进行解析
     * 
     * @param dataPackage 需要解析的字节流
     * @param paramObject 存放协议中规定的信息
     * @return 返回解析结果，true表示成功，false表示失败
     * 
     */
    @Override
    public boolean decode(byte[] dataPackage, LTEParamValueObject paramObject) {
        
        try {
            
            ByteBuffer buffer = ByteBuffer.wrap(dataPackage).order(ByteOrder.BIG_ENDIAN);
            byte commandID = buffer.get();
            
            buffer.get(); //保留字段
            
            short bodyLen = buffer.getShort();
            List<String> imsis = new ArrayList<>();
            
            if (dataPackage.length - 4 != (bodyLen&0xffff))  return false;
            
            while(buffer.hasRemaining()) {
                byte[] dst = new byte[6];
                byte[] imsibs = new byte[16];
                buffer.get(dst);
                buffer.get(imsibs);
                String imsi = new String(imsibs);
                imsis.add(imsi);
            }
            paramObject.setCommandID(commandID);
            paramObject.setBodyLen(bodyLen);
            paramObject.setImsiList(imsis);    
            
            return true;
        } catch (Exception e) {
            // 解析VP层数据包发生异常";
            errorMsg = "解析数据包发生异常";
            return false;
        }
    }
    
    /**
     * 获取错误信息
     */
    @Override
    public String getParseMessage() {
        // 返回errorMsg的值
        return errorMsg;
    }
    
    @Override
    public Map<Short, byte[]> encodeMap(LTEParamValueObject paramObject) {
        return null;
    }
}
