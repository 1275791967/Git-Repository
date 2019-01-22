/**
 * Modem数据传输接口
 */
package com.irongteng.comm.modem;

/**
 * Modem数据传输方式收发数据的接口
 * @author liuqing
 *
 */
public interface ModemDataTransferInterface {

    /**
     * 和远程Modem建立连接
     * @param destPhone :目标电话号码
     * @return:连接成功返回ture,失败返回false
     */
    public boolean openConnection(String destPhone);

    /**
     * 断开连接
     * @return:成功返回true,失败返回false
     */
    public boolean disconnect();

    /**
     * 判断是否和远程Modem处在连接中
     * @return:true 表示连着，false表示断开
     */
    public boolean isConnected();
    
    /**
     * 设置数传连接或者断开
     * @param connected true表示连接成功，false表示连接失败
     */
    public void setConnected(boolean connected);

    /**
     * 返回连接着的远程Modem的号码
     * @return:返回号码
     */
    public String getConnectionPhone();

    /**
     * 设置数传连接的远程Modem的电话号码
     * @param phone 远程Modem的号码
     */
    public void setConnectionPhone(String phone);
    
    /**
     * 发送数据
     * @param destPhone:目标电话号码
     * @param data:发送的数据
     * @return:成功返回ture,失败返回false
     */
    public boolean sendData(String destPhone, byte[] data);
    
    /**
     * 接收数据
     * @param sourcePhone 源电话号码
     * @param data 接收到的数据
     */
    public void receiveData(String sourcePhone,String data);
}
