package com.irongteng.Unit;


/**
 * 一字节无符号整数类型
 * Created by xing on 2017/8/4.
 */
public class Uint8 {
    private int value = 0;

    public Uint8(int n){
        this.value = n & 0xFF;
    }

    public Uint8(Buffer buffer){
        int p = buffer.getPosition();
        int intValue = (buffer.getByte()[p] & 0xFF) ;
        p++;
        buffer.setPosition(p);
        this.value = intValue;
    }

    public Uint8(byte[] b){
        this.value = b[0] & 0xFF;
    }

    public byte[] getByte(){
        byte[] b = new byte[1];
        b[0] = (byte)value;
        return b;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public Integer getValue(){
        return value;
    }
}
