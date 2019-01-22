/**
 * 
 */
package com.irongteng.comm.modem;

/**
 * 读串口数据
 * @author liuqing
 *
 */
public interface SerialReadListener {
    
    /**
     * 读串口数据
     * @param data  读到的数据
     */
    public void serialReadData(byte [] data);

}
