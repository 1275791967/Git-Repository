package com.irongteng.Unit;    
/**
 * 电话号码结果
 */
public class GetPhoneResultUnit {
        public Bytes imsi = null;//IMSI
        public Bytes imei = null;//IMEI
        public Uint32 tmsi = new Uint32(0);//TMSI
        public Bytes phone = null;//电话号码
        public Bytes reserve = new Bytes(new byte[50]);  //保留字段
        
        public GetPhoneResultUnit(){}
        
        public GetPhoneResultUnit(Buffer buffer){
            this.imsi = new Bytes(buffer, 20);
            this.imei = new Bytes(buffer, 20);
            this.tmsi = new Uint32(buffer);
            this.phone = new Bytes(buffer, 20);
            this.reserve = new Bytes(buffer, 50);
        }
        
        public Buffer toBuffer(){
            if(imsi == null || imei == null || phone == null) return null;
            Buffer buffer = new Buffer(114);
            try {
                buffer.push(imsi);
                buffer.push(imei);
                buffer.push(tmsi);
                buffer.push(phone);
                buffer.push(reserve);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return buffer;
        }
        
    
    }
