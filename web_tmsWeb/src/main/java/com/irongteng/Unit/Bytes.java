package com.irongteng.Unit;

import java.util.Arrays;

/**
 * Created by xing on 2017/8/11.
 */
public class Bytes {
    private byte[] data = null;

    public Bytes(Buffer buffer, int len){
        int p = buffer.getPosition();
        data = new byte[len];
        System.arraycopy(buffer.getByte(), p, data, 0, len);
        buffer.setPosition(p + len);
    }

    public Bytes(String value, int len){
        data = new byte[len];
        System.arraycopy(value.getBytes(), 0, data, 0, value.length());
    }

    public Bytes(byte[] b){
        data = new byte[b.length];
        data = Arrays.copyOf(b, b.length);
    }

    public Bytes(byte[] bs1, byte[] bs2){
        if (bs1 == null){
            data = new byte[bs2.length];
            System.arraycopy(bs2, 0, data, 0, bs2.length);
        }else{
            int len = bs1.length+bs2.length;
            data = new byte[len];
            System.arraycopy(bs1, 0, data, 0, bs1.length);
            System.arraycopy(bs2, 0, data, bs1.length, bs2.length);
        }
    }

    public byte[] getBytes(){
        return data;
    }

    @Override
    public String toString() {
        String result = "";
        try {
            result = new String(data, "UTF-8").trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public String toArrysString(){
        return Arrays.toString(data);
    }
}
