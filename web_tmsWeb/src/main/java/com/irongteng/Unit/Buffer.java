package com.irongteng.Unit;

import java.util.Arrays;

/**o
 * Created by yuli on 2017/4/2.
 */

public class Buffer {
    private int DEFAULT_BUFFER_SIZE = 1024;
    private int bufferSize;
    private byte[] data;
    private int position;

    public Buffer(int bufferSize) {
        this.bufferSize = bufferSize;
        data = new byte[bufferSize];
        position = 0;
     }

    public Buffer(byte[] buf) {
        this.bufferSize = buf.length;
        data = new byte[bufferSize];
        for(int i=0; i< bufferSize; i++){
            data[i] = buf[i];
        }
        position = 0;
    }

    public Buffer(byte[] buf,int len){
        this.bufferSize = len;
        data = new byte[bufferSize];
        for(int i=0; i< bufferSize; i++){
            data[i] = buf[i];
        }
        position = 0;
    }

    public Buffer(Buffer buffer){
        data = buffer.getByte();
        position = 0;
    }

    public Buffer addBytes(byte[] bs){
        byte[] buff = data;
        data = new byte[buff.length+bs.length];
        System.arraycopy(buff, 0, data, 0, buff.length);
        System.arraycopy(bs, 0, data, buff.length, bs.length);
        return this;
    }

    public Buffer removeBytes(byte[] bs){
        byte[] buff = new byte[data.length-bs.length];
        System.arraycopy(data, bs.length, buff, 0, data.length-bs.length);
        data = new byte[buff.length];
        System.arraycopy(buff, 0, data, 0, buff.length);
        return this;
    }


     public byte[] getByte(){
         return data;
     }

    public Buffer push(Object object) throws Exception {
        if (object == null){
            throw new Exception("push a null object");
        }
        byte[] array =  null;
        if(object instanceof Uint8){
            array  =  ((Uint8) object).getByte();
        }else if(object instanceof Uint16){
            array  =  ((Uint16) object).getByte();
        }else if(object instanceof Uint32){
            array  =  ((Uint32) object).getByte();
        }else if(object instanceof Bytes){
            array  =  ((Bytes) object).getBytes();
        }else if(object instanceof Buffer){
            array  =  ((Buffer) object).getByte();
        }else if(object.getClass().equals(Byte.class)){
            array =  new byte[1];
            array[0] = (byte)object;
        }else if(object.getClass().equals(Integer.class)){
            array =  int2byte((int)object);
         }else if(object.getClass().equals(String.class)){
            byte[] temp = object.toString().getBytes();
            array = new byte[temp.length];
            for(int  i=0;i<temp.length;i++){
                array[i] = temp[i];
            }
        }
        if (!CheckBufferBoundaries(array)){
            throw new Exception("Failed to add primitive to buffer. There is no additional room for it.");
        }

        for (byte b : array)
        {
            data[position] = b;
            position += 1;

        }
        return this;
    }

    public int getUint8(){
        int intValue = 0;
        for (int i = 0; i < 1; i++) {
            intValue += (data[position] & 0xFF) ;
            position++;
        }
        return intValue;
    }

    public int getUint16(){
        int intValue = 0;
        for (int i = 0; i < 2; i++) {
            intValue += (data[position] & 0xFF) << (8 * i);
            position++;
        }
        return intValue;
    }

    public int getUint32(){
        int intValue = 0;
        int n = 0;
        for (int i = 0; i < 4; i++) {
            n = (data[position] & 0xFF) << (8 * i);
            intValue += n;
            position++;
        }
        return intValue;
    }

    public String getString(int length) throws  Exception{
        byte[]  t = new byte[length];
        int i=0;
        if(position+length>bufferSize){
            throw new Exception("out of buffer bound");
        }
        for(;i<length;i++){
            if(data[position]!='\0'){
                t[i] = data[position];
            }
            position++;
        }
        byte[] buf =Arrays.copyOf(t,i);
        return new String(buf);
    }

    public byte[] int2byte(int value){
        byte[] b = new byte[4];
        for (int i = 0; i <4; i++) {
            b[i] = (byte) (value >> 8 * (3 - i) & 0xFF);
        }
        return b;
    }

    public byte[] getCMDBodys(){
        int len = data.length - position;
        byte[] b = new byte[len];
        for(int i=0;i<len;i++){
            b[i] = data[position+i];
        }
        return b;
    }

    public int getPosition(){
        return position;
    }

    public void setPosition(int p){
        this.position = p;
    }
    private boolean CheckBufferBoundaries(byte[] bytesToCheck)
    {
        int roomLeft = bufferSize - position;
        return roomLeft >= bytesToCheck.length;
    }

    public boolean verify(){
        if(data.length < 15) return false;
        PInfo info = new PInfo(this);
        if(info.len.getValue() != data.length) return false;
        if(info.answer.getValue() != 0) return false;
        if((info.verP.getValue()<=0) || (info.verP.getValue()>3)) return false;
        return true;
    }
}
