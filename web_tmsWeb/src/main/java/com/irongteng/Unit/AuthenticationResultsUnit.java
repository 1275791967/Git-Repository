package com.irongteng.Unit;


/**
 * 身份验证结果
 */
public class AuthenticationResultsUnit {
    
    public Bytes authentication = null ;//鉴权码
    public Bytes reserve = new Bytes(new byte[50]);  //保留字段
    
    public AuthenticationResultsUnit(){}

    public AuthenticationResultsUnit(Buffer buffer){
        this.authentication = new Bytes(buffer, 20);
        this.reserve = new Bytes(buffer, 50);
    }
    
    
    public Buffer toBuffer(){
        if(authentication == null) return null;
        Buffer buffer = new Buffer(70);   
        try {
            buffer.push(authentication);
            buffer.push(reserve);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }

}
