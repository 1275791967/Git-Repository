package com.irongteng.license;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

class License implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private byte[] bs1;
    private byte[] bs5;  
    private byte[] bs11;
    private byte[] bs14;
    private byte[] bs16;
    private byte[] bs17;
    private byte[] bs18;
    private byte[] bs19;
    private byte[] bs20;
    private byte[] bs22;
    private byte[] bs24;
    private byte[] bs27;   
    
    public License(){
        bs1=randomBytes();
        bs5=randomBytes();
        bs11=randomBytes();
        bs14=randomBytes();
        bs16=randomBytes();
        bs17=randomBytes();
        bs18=randomBytes();
        bs19=randomBytes();
        bs20=randomBytes();
        bs22=randomBytes();
        bs24=randomBytes();
        bs27=randomBytes();
    }
    
    public boolean isSwitch(){
        String str=new String(reverse(bs16))+new String(reverse(bs17))+new String(reverse(bs18))+new String(reverse(bs19))+new String(reverse(bs20));
        return !"SWITCH IS OFF".equals(str);
    }
    
    public void setSwitch(boolean open) {
        if (!open) {
            bs16 = reverse("SWITCH".getBytes());
            bs17 = reverse(" ".getBytes());
            bs18 = reverse("IS".getBytes());
            bs19 = reverse(" ".getBytes());
            bs20 = reverse("OFF".getBytes());
        } else {
            bs16 = randomBytes();
            bs17 = randomBytes();
            bs18 = randomBytes();
            bs19 = randomBytes();
            bs20 = randomBytes();
        }
    }
    
    public int getNumber(){
       String str= new String(reverse(bs14));
       try{
           return Integer.parseInt(str);
       }catch(Exception e){
           return 5;
       }
    }
    
    public void setNumber(int number) {
        this.bs14 = reverse(Integer.toString(number).getBytes());
    }

    public String getDiskSerial() {
        return new String(reverse(bs5));
    }

    public void setDiskSerial(String diskSerial) {
        this.bs5 = reverse(diskSerial.getBytes());
    }

    public String getMacAddress() {
        return new String(reverse(bs1));
    }

    public void setMacAddress(String macAddress) {
        this.bs1 = reverse(macAddress.getBytes());
    }

    public String getProductID() {
        return new String(reverse(bs11));
    }

    public void setProductID(String productID) {
        this.bs11 = reverse(productID.getBytes());
    }
    
    public long getDate(){
        String sTemp=new String(reverse(bs22)); 
        try{
           return Long.parseLong(sTemp);    
        }catch(Exception e){
            return 0;           
        }
    }
    
    public void setDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        String s=sdf.format(date);
        setDate(Long.parseLong(s));
    }
    
    public void setDate(long dt){
        String sTemp=Long.toString(dt); 
        this.bs22= reverse(sTemp.getBytes());
    }
    
    public boolean isExpriySwitch(){
        String sTemp=new String(reverse(bs24));
        return !"OFF".equals(sTemp.toUpperCase());
    }
    
    //on表示验证日期，off表示不验证日期，永远有效
    public void setExpriySwitch(boolean on_off){
        String sTemp= on_off ? "on" :"off";
        this.bs24=reverse(sTemp.getBytes()); 
    }
    
    //返回True表示过期失效
    public boolean isOverTime() {
        if (!isExpriySwitch()) {
            return false;
        } else {
            if (getOvertimeTag())
                return true;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
            long curTime = Long.parseLong(sdf.format(new Date()));
            return curTime > getDate();
        }
    }
    
    public void setOvertimeTag(boolean on_off){
        String sTemp=on_off ? "true" : "false";
        this.bs27=reverse(sTemp.getBytes());
    }
    
    public boolean getOvertimeTag(){
        String sTemp=new String(reverse(bs27));
        return sTemp.equals("true");
    }
    
    private byte[] reverse(byte[] bb){
       if(bb!=null & bb.length>0){
          byte bs[]=bb.clone();
          for(int i=0;i<bs.length;i++){
             bs[i]=(byte)(0xFF^bs[i]); 
          }
          return bs;
       }
       else
           return bb;
    }
    private byte[] randomBytes(){
        int length=(int)(System.currentTimeMillis() & 0x0F) + 10;
        byte[]  bs=new byte[length];
        for(int i=0;i<length;i++){
          bs[i]=(byte)Math.round(Math.random()*1000);
        }
        return bs;
        
    }
    
    @Override
    public String toString(){
        String tempStr = "Switch = " +this.isSwitch()+ "\n"+    
                       "Disk serial = "+this.getDiskSerial()+"\n"+
                       "MAC address = "+this.getMacAddress()+"\n"+
                       "Product ID  = "+this.getProductID()+"\n"+
                       "Number = " +this.getNumber()+"\n"+
                       "ExpriySwitch = " + this.isExpriySwitch()+ "\n"+
                       "EffectiveDate = "+ this.getDate()+ "\n"+
                       "OverTimeTag = "+ this.getOvertimeTag();
        return tempStr; 
    }
    
    public boolean equals(License license){
        return this.getDiskSerial().equals(license.getDiskSerial()) 
                && (!"".equals(license.getMacAddress().trim()))
                && (this.getMacAddress().contains(license.getMacAddress()) || license.getMacAddress().contains(this.getMacAddress()))
                &&  this.getProductID().equals(license.getProductID());
    }
    
}
