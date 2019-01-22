package com.irongteng.Unit;

/**
 * 射频关闭通知
 */
public class ClosingUnit {
    public Bytes reserve = new Bytes(new byte[50]);  //保留字段
 
    public ClosingUnit(){}
    
    public ClosingUnit(Buffer buffer){
        this.reserve = new Bytes(buffer, 50);
    }
   
    public Buffer toBuffer(){
        Buffer buffer = new Buffer(50);
        try {
            buffer.push(reserve);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }
}
