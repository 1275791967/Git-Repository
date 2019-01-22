package com.irongteng.conf;

public final class CleanLogsConfig extends AbstractSystemConfig{
    
    private int holdTime;
    
    private boolean switchEnable;
    
    public CleanLogsConfig(){
        
        try {
            
            this.loadSecionProperties("LogsClean");
            
            setHoldTime(Integer.parseInt(getProperty("logs.holdTime","360").trim()));
            setSwitchEnable("on".equalsIgnoreCase(getProperty("logs.cleanSwitch", "on")));
            
        } catch (NumberFormatException e) {
            logger.info("清理日志配置文件信息错误：" + e.getMessage());
        }
    }
    
    public int getHoldTime() {
        return holdTime;
    }
    
    public void setHoldTime(int holdTime) {
        setProperty("db.holdTime", "" + holdTime);
        this.holdTime = holdTime;
    }
    
    public boolean isSwitchEnable() {
        return switchEnable;
    }
    
    public void setSwitchEnable(boolean switchEnable) {
        setProperty("logs.cleanSwitch", switchEnable ? "on" : "off");
        this.switchEnable = switchEnable;
    }
    
    public static void main(String[] args) {
        CleanLogsConfig logs = new CleanLogsConfig();
        System.out.println(logs.getHoldTime());
        System.out.println(logs.isSwitchEnable());
    }
}
