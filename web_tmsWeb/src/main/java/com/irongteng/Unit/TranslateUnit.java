package com.irongteng.Unit;

/**
 *翻译负载信息
 */
public class TranslateUnit {
    
    public Uint8 ch1Counts = new Uint8(0); // 通道1总通道数
    public Uint8 ch1NormalCounts = new Uint8(0); // 通道1正常通道数
    public Uint16 ch1WaitTranslates = new Uint16(0); // 通道1待翻译数
    
    public Uint8 ch2Counts = new Uint8(0); // 通道2总通道数
    public Uint8 ch2NormalCounts = new Uint8(0); // 通道2正常通道数
    public Uint16 ch2WaitTranslates = new Uint16(0); // 通道2待翻译数
    
    public Uint8 ch3Counts = new Uint8(0); // 通道3总通道数
    public Uint8 ch3NormalCounts = new Uint8(0); // 通道3正常通道数
    public Uint16 ch3WaitTranslates = new Uint16(0); // 通道3待翻译数
    
    public Uint8 ch4Counts = new Uint8(0); // 通道4总通道数
    public Uint8 ch4NormalCounts = new Uint8(0); // 通道4正常通道数
    public Uint16 ch4WaitTranslates = new Uint16(0); // 通道4待翻译数
    
    public Uint8 ch5Counts = new Uint8(0); // 通道5总通道数
    public Uint8 ch5NormalCounts = new Uint8(0); // 通道5正常通道数
    public Uint16 ch5WaitTranslates = new Uint16(0); // 通道5待翻译数
    
    /*public Uint8 unicomChannels = new Uint8(0); //联通GSM总通道数
    public Uint8 unicomNormalChannels = new Uint8(0); // 联通GSM正常通道数
    public Uint16 unicomWaitTranslates = new Uint16(0); // 联通GSM待翻译数
    
    public Uint8 cdmaChannels = new Uint8(0); // CDMA总通道数
    public Uint8 cdmaNormalChannels = new Uint8(0); // CDMA正常通道数
    public Uint16 cdmaWaitTranslates = new Uint16(0); // CDMA待翻译数 */    
    
    public Uint32 alarmState = new Uint32(0); // 告警状态
    public Bytes reserve = new Bytes(new byte[50]);  //保留字段
    
    public TranslateUnit(){}
    
    public TranslateUnit(Buffer buffer){
        this.ch1Counts = new Uint8(buffer);
        this.ch1NormalCounts = new Uint8(buffer);
        this.ch1WaitTranslates = new Uint16(buffer);
        this.ch2Counts = new Uint8(buffer);
        this.ch2NormalCounts = new Uint8(buffer);
        this.ch2WaitTranslates = new Uint16(buffer);
        this.ch3Counts = new Uint8(buffer);
        this.ch3NormalCounts = new Uint8(buffer);
        this.ch3WaitTranslates = new Uint16(buffer);
        this.ch4Counts = new Uint8(buffer);
        this.ch4NormalCounts = new Uint8(buffer);
        this.ch4WaitTranslates = new Uint16(buffer);
        this.ch5Counts = new Uint8(buffer);
        this.ch5NormalCounts = new Uint8(buffer);
        this.ch5WaitTranslates = new Uint16(buffer);
        this.alarmState = new Uint32(buffer);
        this.reserve = new Bytes(buffer, 50);
    }
    
    public Buffer toBuffer(){
        Buffer buffer = new Buffer(66);
        try {
            buffer.push(ch1Counts);
            buffer.push(ch1NormalCounts);
            buffer.push(ch1WaitTranslates);
            buffer.push(ch2Counts);
            buffer.push(ch2NormalCounts);
            buffer.push(ch2WaitTranslates);
            buffer.push(ch3Counts);
            buffer.push(ch3NormalCounts);
            buffer.push(ch3WaitTranslates);
            buffer.push(ch4Counts);
            buffer.push(ch4NormalCounts);
            buffer.push(ch4WaitTranslates);
            buffer.push(ch5Counts);
            buffer.push(ch5NormalCounts);
            buffer.push(ch5WaitTranslates);
            buffer.push(alarmState);
            buffer.push(reserve);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }

    @Override
    public String toString() {
        return "TranslateUnit [ch1Counts=" + ch1Counts.getValue() + 
                ", ch1NormalCounts=" + ch1NormalCounts.getValue() + 
                ", ch1WaitTranslates=" + ch1WaitTranslates.getValue() + 
                ", ch2Counts=" + ch2Counts.getValue() + 
                ", ch2NormalCounts=" + ch2NormalCounts.getValue() + 
                ", ch2WaitTranslates=" + ch2WaitTranslates.getValue() + 
                ", ch3Counts=" + ch3Counts.getValue() + 
                ", ch3NormalCounts=" + ch3NormalCounts.getValue() + 
                ", ch3WaitTranslates=" + ch3WaitTranslates.getValue() + 
                ", ch4Counts=" + ch4Counts.getValue() + 
                ", ch4NormalCounts=" + ch4NormalCounts.getValue() + 
                ", ch4WaitTranslates=" + ch4WaitTranslates.getValue() + 
                ", ch5Counts=" + ch5Counts.getValue() + 
                ", ch5NormalCounts=" + ch5NormalCounts.getValue() + 
                ", ch5WaitTranslates=" + ch5WaitTranslates.getValue() + 
                ", alarmState=" + alarmState.getValue() + 
                ", reserve=" + reserve.toArrysString() + 
                "]";
    }

       
    

}
