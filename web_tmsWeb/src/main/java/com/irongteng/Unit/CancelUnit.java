package com.irongteng.Unit;


/**
 * 取消翻译
 */
public class CancelUnit {
    public Bytes imsi = null ;//Imsi
    public Uint16 translateType = new Uint16(0); //翻译类型
    public Bytes reserve = new Bytes(new byte[50]);  //保留字段
    
    public CancelUnit(){}

    public CancelUnit(Buffer buffer){
        this.imsi = new Bytes(buffer, 20);
        this.translateType = new Uint16(buffer);
        this.reserve = new Bytes(buffer, 50);
    }
    
    
    public Buffer toBuffer(){
        if(imsi == null) return null;
        Buffer buffer = new Buffer(72);
        try {
            buffer.push(imsi);
            buffer.push(translateType);
            buffer.push(reserve);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }

    @Override
    public String toString() {
        return "CancelUnit [imsi=" + imsi.toArrysString() + 
                ", translateType=" + translateType.getValue() + 
                ", reserve=" + reserve.toArrysString() + 
                "]";
    }

    

}
