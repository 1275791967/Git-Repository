package com.irongteng.Unit;

/**
 * 获取电话号码
 */
public class GetPhoneUnit {
    public Uint8 thridSwitch = new Uint8(0); //第三方开关
    public Uint16 translateType  = new Uint16(0) ;//翻译类型
    public Bytes imsi = null ;//IMSI
    public Bytes imei = null ;//IMEI
    public Uint32 tmsi = new Uint32(0);//TMSI
    public Uint16 lac = new Uint16(0);//LAC
    public Uint8  rssi  = new Uint8(0) ;//RSSI
    public Uint8  first  = new Uint8(0) ;//优先翻译
    public Bytes phone = null;//电话号码
    public Bytes longitude = null ;//经度
    public Bytes latitude = null;//纬度
    public Bytes reserve = new Bytes(new byte[50]);  //保留字段

    public GetPhoneUnit(){}

    public GetPhoneUnit(Buffer buffer){
        this.thridSwitch = new Uint8(buffer);
        this.translateType = new Uint16(buffer);
        this.imsi = new Bytes(buffer, 20);
        this.imei = new Bytes(buffer, 20);
        this.tmsi = new Uint32(buffer);
        this.lac = new Uint16(buffer);
        this.rssi = new Uint8(buffer);
        this.first = new Uint8(buffer);
        this.phone = new Bytes(buffer, 20);
        this.longitude = new Bytes(buffer, 20);
        this.latitude = new Bytes(buffer, 20);
        this.reserve = new Bytes(buffer, 50);
    }

    public Buffer toBuffer(){
        if(imsi == null || imei == null ||  phone == null || longitude == null || latitude == null) return null;
        Buffer buffer = new Buffer(161);
        try {
            buffer.push(thridSwitch);
            buffer.push(translateType);
            buffer.push(imsi);
            buffer.push(imei);
            buffer.push(tmsi);
            buffer.push(lac);
            buffer.push(rssi);
            buffer.push(first);
            buffer.push(phone);
            buffer.push(longitude);
            buffer.push(latitude);
            buffer.push(reserve);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }

    @Override
    public String toString() {
        return "GetPhoneUnit{" +
                "thridSwitch = " + thridSwitch.getValue() +
                ", translateType = " + translateType.getValue() +
                ", imsi = " + imsi.toString() +
                ", imei = " + imei.toString() +
                ", tmsi = " + tmsi.getValue() +
                ", lac = " + lac.getValue() +
                ", rssi = " + rssi.getValue() +
                ", first = " + first.getValue() +
                ", phone = " + phone.toString() +
                ", longitude = " + longitude.toString() +
                ", latitude = " + latitude.toString() +
                ", reserve = " + reserve.toString() +
                '}';
    }
}
