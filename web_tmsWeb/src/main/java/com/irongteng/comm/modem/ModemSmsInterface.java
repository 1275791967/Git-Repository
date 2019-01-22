/**
 * 
 */
package com.irongteng.comm.modem;

import java.util.Date;
/**
 * Modem处理短信的接口
 * @author liuqing
 *
 */
public interface ModemSmsInterface {

    /**
     * 清除Modem中的短信
     * @return 
     */
    public boolean clearShortMsg();
    
    /**
       * 删除Modem中的某条短信
       * @param index 要删除短信在Modem中的存放位置
       * @return 是否删除成功
       */
    public boolean deleteShortMsg(int index);
    
    /**
     * 读取Modem中的某条短信
     * @param index 要读取短信在Modem中的存放位置
     * @return 返回是否成功
     */
    public boolean readShortMsg(int index);
    /**
      * 发送英文短信
      * @param destPhone 接收方电话号码
      * @param content 短信内容
      * @return 是否发送成功
      */
    public boolean sendEnglishSMS(String destPhone, String content);
    
    /**
      * 发送中文短信
      * @param destPhone 接收方电话号码
      * @param content 短信内容
      * @return 是否发送成功
      */
    public boolean sendChineseSMS(String destPhone, String content);
    /**
      * 收到新的短信通知函数
      * @param sourcePhone 发送方电话号码
      * @param content 短信内容
      * @param date TODO
      */
    public void receiveShortMsg(String sourcePhone, String content, Date date);
    /**
     * 获取发送短信的电话号码
     * @return 发送短信的电话号码,如果获取不成功，返回null
     */
    String getShortMsgPhone();
    /**
     * 判断SIM卡是否存在
     * @return 存在返回true，不存在返回false
     */
    public boolean isSIMExist();
}
