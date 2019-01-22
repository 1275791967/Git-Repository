package com.irongteng.conf;

public final class GlobalConfig  extends AbstractSystemConfig{
    
    private int updownSecend;        //时间波动范围
    private boolean wifiSwitch;      //是否开启WIFI监控设备开关
    private boolean parameterSwitch; //是否开启参数查询设置
    private boolean callerlocSwitch; //号码归属地开关
    
    public GlobalConfig() {
        try {
            this.loadSecionProperties("Global");
            
            String updownSec = getProperty("updown.secend");
            setUpdownSecend(Integer.parseInt(updownSec!=null ? updownSec : "30"));
            setWifiSwitch("on".equalsIgnoreCase(getProperty("wifi.switch", "on")));
            setParameterSwitch("on".equalsIgnoreCase(getProperty("parameter.switch", "on")));
            setCallerlocSwitch("on".equalsIgnoreCase(getProperty("callerloc.switch", "on"))); //号码归属地管理开关
        } catch(NumberFormatException e) {
            logger.error(e.getMessage());
        }
        
    }
    
    public int getUpdownSecend() {
        return updownSecend;
    }

    public void setUpdownSecend(int updownSecend) {
        setProperty("updown.secend", "" + updownSecend);
        this.updownSecend = updownSecend;
    }
    
    public boolean isWifiSwitch() {
        return wifiSwitch;
    }

    public void setWifiSwitch(boolean wifiSwitch) {
        setProperty("wifi.switch", wifiSwitch ? "on" : "off");
        this.wifiSwitch = wifiSwitch;
    }
    
    public boolean isParameterSwitch() {
        return parameterSwitch;
    }

    public void setParameterSwitch(boolean parameterSwitch) {
        setProperty("parameter.switch", parameterSwitch ? "on" : "off");
        this.parameterSwitch = parameterSwitch;
    }
    
    public boolean isCallerlocSwitch() {
        return callerlocSwitch;
    }
    
    public void setCallerlocSwitch(boolean callerlocSwitch) {
        setProperty("callerloc.switch", callerlocSwitch ? "on" : "off");
        this.callerlocSwitch = callerlocSwitch;
    }

    public static void main(String[] args) {
        GlobalConfig global = new GlobalConfig();
        System.out.println(global.getUpdownSecend());
        System.out.println(global.isWifiSwitch());
        System.out.println(global.isParameterSwitch());
        System.out.println(global.isCallerlocSwitch());
    }
}
