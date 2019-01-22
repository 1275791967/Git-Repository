/**
 * 
 */
package com.irongteng.protocol;

import java.util.Map;

/**
 * @author lvlei
 * @param <T>
 * 
 */
public interface Protocol<T> {
    
    /**
     * 根据南向接口协议对数据进行组包
     * @param paramObject
     * @return  返回组包的字节流
     */
    public byte[] encode(T paramObject);
    
    /**
     * 协议打包。
     * 用于解决数据过长，发送失败的问题，解决方法是将发送的数据打包成多个包发送
     * @param paramObject  协议中承载的有效数据
     * @return 返回多个包，其中以包标识号做区分
     */
    public Map<Short, byte[]> encodeMap(T paramObject);

    /**
     * 根据南向接口协议对收到的数据进行解析
     * @param data  需要解析的字节流
     * @param paramObject  存放协议中规定的基本信息
     * @return  返回解析结果，true表示成功，false表示失败
     */
    public boolean decode(byte[] data, T paramObject);

    /**
     * 获取解析是否成功及失败原因
     * @return 返回解析信息
     */
    public String getParseMessage();

}
