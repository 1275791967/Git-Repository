package com.irongteng.Unit;


/**
 *手机登录结果
 */
public class MobileloginresultsUnit {
    public Uint8 loginResult = new Uint8(0); // 登录结果
    public Uint32 localBalance = new Uint32(0);//本地余额
    public Uint32 thirdBalance = new Uint32(0);//第三方余额
    public Bytes reserve = new Bytes(new byte[50]);  //保留字段
    
    public MobileloginresultsUnit(){}

    public MobileloginresultsUnit(Buffer buffer){
        this.loginResult = new Uint8(buffer);
        this.localBalance = new Uint32(buffer);
        this.thirdBalance = new Uint32(buffer);
        this.reserve = new Bytes(buffer, 50);
    }
    
    public Buffer toBuffer(){
        Buffer buffer = new Buffer(59);
        try {
            buffer.push(loginResult);
            buffer.push(localBalance);
            buffer.push(thirdBalance);
            buffer.push(reserve);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }
    

}
