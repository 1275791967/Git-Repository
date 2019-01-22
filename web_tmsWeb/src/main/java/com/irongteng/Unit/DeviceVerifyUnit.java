package com.irongteng.Unit;

/**
 * 设备验证
 */
public class DeviceVerifyUnit {
        public Uint8 firsLogin = new Uint8(0); // 开机初次登录
        public Uint8 deviceType = new Uint8(0); // 设备类型
        public Uint16 op1 = new Uint16(0); // 运营商1
        public Uint16 op2 = new Uint16(0); // 运营商2
        public Uint16 op3 = new Uint16(0); // 运营商3
        public Uint16 op4 = new Uint16(0); // 运营商4
        public Uint16 op5 = new Uint16(0); // 运营商5
        public Bytes featureCode = null ; // 特征码/IMEI
        public Uint32 rand = new Uint32(0); // rand，CDMA服务器使用，其他的暂时为0。
        public Uint16 areaCode = new Uint16(0); //区域码
        public Bytes reserve = new Bytes(new byte[50]);  //保留字段
    
        public DeviceVerifyUnit(){}
    
        public DeviceVerifyUnit(Buffer buffer){
            this.firsLogin = new Uint8(buffer);
            this.deviceType = new Uint8(buffer);
            this.op1 = new Uint16(buffer);
            this.op2 = new Uint16(buffer);
            this.op3 = new Uint16(buffer);
            this.op4 = new Uint16(buffer);
            this.op5 = new Uint16(buffer);
            this.featureCode = new Bytes(buffer, 20);
            this.rand = new Uint32(buffer);
            this.areaCode = new Uint16(buffer);
            this.reserve = new Bytes(buffer, 50);
        }
    
        public Buffer toBuffer(){
            if(featureCode == null) return null;
            Buffer buffer = new Buffer(88);
            try {
                buffer.push(firsLogin);
                buffer.push(deviceType);
                buffer.push(op1);
                buffer.push(op2);
                buffer.push(op3);
                buffer.push(op4);
                buffer.push(op5);
                buffer.push(featureCode);
                buffer.push(rand);
                buffer.push(areaCode);
                buffer.push(reserve);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return buffer;
        }

        @Override
        public String toString() {
            return "DeviceVerifyUnit [firsLogin=" + firsLogin.getValue() + 
                    ", deviceType=" + deviceType.getValue() + 
                    ", op1=" + op1.getValue() + 
                    ", op2=" + op2.getValue() + 
                    ", op3=" + op3.getValue() + 
                    ", op4=" + op4.getValue() + 
                    ", op5=" + op5.getValue() + 
                    ", featureCode=" + featureCode.toArrysString() + 
                    ", rand=" + rand.getValue() + 
                    ", areaCode=" + areaCode.getValue() + 
                    ", reserve=" + reserve.toArrysString() + 
                    "]";
        }

         
        
    }
