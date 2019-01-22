package com.irongteng.Unit;

/**
 * 四字节整数类型
 * Created by xing on 2017/8/4.
 */
public class Uint32 {
    private int value = 0;

    public Uint32(int n){
        this.value = n & 0xFFFFFFFF  ;
    }

    public Uint32(Buffer buffer){
        int intValue = 0;
        int p = buffer.getPosition();
        for (int i = 0; i < 4; i++) {
            intValue += (buffer.getByte()[p] & 0xFF) << (8 * i);
            p++;
        }
        buffer.setPosition(p);
        this.value = intValue;
    }

    public Uint32(byte[] b){
        int intValue = 0;
        for (int i = 0; i < 4; i++) {
            intValue += (b[i] & 0xFF) << (8 * i);
        }
        this.value = intValue;
    }

    public byte[] getByte(){
        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            b[i] = (byte) (value >> 8 *i & 0xFF);
        }
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
