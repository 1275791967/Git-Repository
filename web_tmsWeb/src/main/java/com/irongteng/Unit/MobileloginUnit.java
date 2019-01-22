package com.irongteng.Unit;

/**
 *手机客户登录
 */
public class MobileloginUnit {
    public Bytes name = null ;//用户名
    public Bytes psw = null ;// 密码
    public Bytes reserve = new Bytes(new byte[50]);  //保留字段
    
    public MobileloginUnit(){}

    public MobileloginUnit(Buffer buffer){
        this.name = new Bytes(buffer, 20);
        this.psw = new Bytes(buffer, 20);
        this.reserve = new Bytes(buffer, 50);
    }
    
    public Buffer toBuffer(){
        if(name == null ||psw == null) return null;
        Buffer buffer = new Buffer(90);
        try {
            buffer.push(name);
            buffer.push(psw);
            buffer.push(reserve);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }  

}
