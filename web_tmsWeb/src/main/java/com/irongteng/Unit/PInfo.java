package com.irongteng.Unit;

import java.util.Arrays;

/**
 * Created by xing on 2017/8/7.
 */
public class PInfo {
    public Uint32 len;
    public Uint8 verP;
    public Uint32 deviceNO;
    public Uint16 serial;
    public Uint16 reserve;
    public Uint8 cmd;
    public Uint8 answer;
    public byte[] cmdBodys;
    private Buffer buffer = null;

    /**  用于组包  */
    public PInfo(int version, int deviceNo, int serial1, int reserve1, int cmd1, byte[] bodys){
        len = new Uint32(bodys.length+15);
        verP = new Uint8(version);
        deviceNO = new Uint32(deviceNo);
        serial = new Uint16(serial1);
        reserve = new Uint16(reserve1);
        cmd = new Uint8(cmd1);
        answer = new Uint8(0);
        cmdBodys = Arrays.copyOf(bodys, bodys.length);
    }

    public PInfo(int version, int cmd1, byte[] bodys){
        if(bodys == null){
            len = new Uint32(15);
            cmdBodys = null;
        }else {
            len = new Uint32(bodys.length+15);
            cmdBodys = Arrays.copyOf(bodys, bodys.length);
        }
        verP = new Uint8(version);
        deviceNO = new Uint32(0);
        serial = new Uint16(0);
        reserve = new Uint16(0);
        cmd = new Uint8(cmd1);
        answer = new Uint8(0);

    }

    public Buffer toBuffer(){
        buffer = new Buffer(len.getValue());
        try{
            buffer.push(len);
            buffer.push(verP);
            buffer.push(deviceNO);
            buffer.push(serial);
            buffer.push(reserve);
            buffer.push(cmd);
            buffer.push(answer);
            if(cmdBodys != null){
                buffer.push(new Bytes(cmdBodys));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return buffer;
    }

    /**  用于解析  */

    public PInfo(Buffer buffer){
        this.buffer = buffer;
        len = new Uint32(buffer);
        verP = new Uint8(buffer);
        deviceNO = new Uint32(buffer);
        serial = new Uint16(buffer);
        reserve = new Uint16(buffer);
        cmd = new Uint8(buffer);
        answer = new Uint8(buffer);
        cmdBodys = buffer.getCMDBodys();
    }

    public Buffer getBuffer(){
        return buffer;
    }

    @Override
    public String toString() {
        return "PInfo{" +
                "len=" + len.toString() +
                ", verP=" + verP.toString() +
                ", deviceNO=" + deviceNO.toString() +
                ", serial=" + serial.toString() +
                ", reserve=" + reserve.toString() +
                ", cmd=" + cmd.toString() +
                ", answer=" + answer.toString() +
                ", bodys=" + Arrays.toString(cmdBodys) +
                '}';
    }

    /**public int len;
    public int verP;
    public int deviceNO;
    public int serial;
    public int reserve;
    public int cmd;
    public int answer;
    public byte[] cmdBodys;

    public PInfo(Buffer buffer){
        len = buffer.getUint32();
        verP = buffer.getUint8();
        deviceNO =  buffer.getUint32();
        serial = buffer.getUint16();
        reserve = buffer.getUint16();
        cmd = buffer.getUint8();
        answer = buffer.getUint8();
        cmdBodys = buffer.getCMDBodys();
    }

    @Override
    public String toString() {
        return "PInfo{" +
                "len=" + len +
                ", verP=" + verP +
                ", deviceNO=" + deviceNO +
                ", serial=" + serial +
                ", reserve=" + reserve +
                ", cmd=" + cmd +
                ", answer=" + answer +
                ", bodys=" + Arrays.toString(cmdBodys) +
                '}';
    }
    */
}
