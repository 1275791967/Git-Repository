package com.irongteng.Unit;


/**
 * 身份验证
 */
public class AuthenticationUnit {    
    public Bytes publickey = null ;//公钥
    public Bytes authentication = null ;// 鉴权码
    public Uint32 rand = new Uint32(0); // rand，CDMA前端使用，其他的暂时为0。
    public Bytes reserve = new Bytes(new byte[50]);  //保留字段
    
    public AuthenticationUnit(){}

    public AuthenticationUnit(Buffer buffer){
        this.publickey = new Bytes(buffer, 20);
        this.authentication = new Bytes(buffer, 20);
        this.rand = new Uint32(buffer);
        this.reserve = new Bytes(buffer, 50);
    }
    
    public Buffer toBuffer(){
        if(publickey == null ||authentication == null) return null;
        Buffer buffer = new Buffer(94);
        try {
            buffer.push(publickey);
            buffer.push(authentication);
            buffer.push(rand);
            buffer.push(reserve);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }

    @Override
    public String toString() {
        return "AuthenticationUnit [publickey=" + publickey.toArrysString() + 
                ", authentication=" + authentication.toArrysString() + 
                ", rand=" + rand.getValue() + 
                ", reserve=" + reserve.toArrysString() + 
                "]";
    }
    
   

}
