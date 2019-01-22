package com.irongteng.Unit;

/**
 * GSM鉴权请求
 */
public class GsmAuthenticationUnit {
    public Bytes imsi = null ;//IMSI
    public Uint16 translateType = new Uint16(0); //翻译类型
    public Bytes gsmRand = null ;//GSM Rand
    public Bytes reserve = new Bytes(new byte[50]);  //保留字段
    
    public GsmAuthenticationUnit(){}

    public GsmAuthenticationUnit(Buffer buffer){
        this.imsi = new Bytes(buffer, 20);
        this.translateType = new Uint16(buffer);
        this.gsmRand = new Bytes(buffer, 16);
        this.reserve = new Bytes(buffer, 50);
    }
    
    public Buffer toBuffer(){
        if(imsi == null ||gsmRand == null) return null;
        Buffer buffer = new Buffer(88);
        try {
            buffer.push(imsi);
            buffer.push(translateType);
            buffer.push(gsmRand);
            buffer.push(reserve);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }

    @Override
    public String toString() {
        return "GsmAuthenticationUnit [imsi=" + imsi.toArrysString() + 
                ", translateType=" + translateType.getValue() +
                ", gsmRand=" + gsmRand.toArrysString() + 
                ", reserve=" + reserve.toArrysString() + "]";
    }
    
    

}
