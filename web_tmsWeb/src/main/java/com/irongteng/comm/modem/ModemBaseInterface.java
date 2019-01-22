/**
 * 
 */
package com.irongteng.comm.modem;

/**
 * Modem基本接口，包括打开、关闭、初始化、写数据
 * @author liuqing
 *
 */
public interface ModemBaseInterface {

    /**
     * 打开串口
     * @return 返回串口打开结果，0表示成功
     */
    int open();

    /**
     * 初始化Modem
     * @param readMsg 初始化时是否读取短信
     * @return 返回初始化结果，true表示成功，false表示失败
     */
    boolean init(boolean readMsg);

    /**
     * 关闭串口
     *
     */
    void close();

    /**
     * 发送数据，比如对Modem初始化的数据
     * @param data 需要发送的数据
     */
    boolean write(byte[] data);
}
