package com.irongteng.Unit;


/**
 * GSM鉴权结果
 */
public class GsmAuthenResultsUnit {
        public Bytes imsi = null ;//IMSI 
        public Uint32 sres = new Uint32(0);//SRES
        public Bytes reserve = new Bytes(new byte[50]);  //保留字段
        
        public GsmAuthenResultsUnit(){}
    
        public GsmAuthenResultsUnit(Buffer buffer){
            this.imsi = new Bytes(buffer, 20);
            this.sres = new Uint32(buffer);
            this.reserve = new Bytes(buffer, 50);
        }
        
        public Buffer toBuffer(){
        if(imsi == null ||sres == null) return null;
        Buffer buffer = new Buffer(74);
        try {
            buffer.push(imsi);
            buffer.push(sres);
            buffer.push(reserve);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }
    

}
