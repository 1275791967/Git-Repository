/**
 *
 */
package com.irongteng.protocol;

/**
 * 协议承载的监控对象接口
 *
 * @author lvlei
 *
 * @param <T> 定义传递的参数id类型
 * @param <PK> 定义传递的参数长度类型
 */
public interface MonitorObject<T extends Number, PK extends Number> {

    /**
     * 获取监控对象总的字节长度
     *
     * @return 返回监控对象的长度
     */
    int size();

    /**
     * 得到参数对象的参数ID
     *
     * @return
     */
    T getObjectID();

    /**
     * 设置对象的参数ID
     *
     * @param objectID
     */
    void setObjectID(T objectID);

    /**
     * 得到监控参数对象长度
     *
     * @return
     */
    PK getLength();

    /**
     * 设置监控对象长度
     *
     * @param length
     */
    void setLength(PK length);

    /**
     * 得到对象的字节流形式的参数值
     *
     * @return
     */
    byte[] getValue();

    /**
     * 获取参数值的字符串形式的值
     *
     * @return
     */
    String getStringValue();

    /**
     * 设置参数对象的值
     *
     * @param value 要设置的值
     * @param dataType 数据类型
     */
    void setValue(String value, String dataType);

    /**
     * 传递参数类型，获取参数的参数值
     *
     * @param dataType 参数类型
     * @return 参数值的字符串形式
     */
    String getValue(String dataType);

    /**
     * 获取系统类型
     *
     * @return
     */
    String getDataType();

    /**
     * 设置系统类型
     *
     * @param dataType
     */
    void setDataType(String dataType);

    /**
     * 将监控对象转换成字节流
     *
     * @return
     */
    byte[] toBytes();
}
