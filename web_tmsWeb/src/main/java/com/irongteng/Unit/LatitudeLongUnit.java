package com.irongteng.Unit;


/**
 * 设置经纬度
 */
public class LatitudeLongUnit {
    public Bytes longitude = null ;//经度
    public Bytes latitude = null;//纬度
    public Bytes reserve = new Bytes(new byte[50]);  //保留字段
    
    public LatitudeLongUnit(){}

    public LatitudeLongUnit(Buffer buffer){
        this.longitude = new Bytes(buffer, 20);
        this.latitude = new Bytes(buffer, 20);
        this.reserve = new Bytes(buffer, 50);
    }
    
    public Buffer toBuffer(){
        if(longitude == null || latitude == null) return null;
        Buffer buffer = new Buffer(90);
        try {
            buffer.push(longitude);
            buffer.push(latitude);
            buffer.push(reserve);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }


}
