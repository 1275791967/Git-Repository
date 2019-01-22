package com.irongteng.conf;

public final class CleanDataConfig extends AbstractSystemConfig{
    
    private int holdTime;
    
    private boolean logSwitch;
    private boolean aplSwitch;
    private boolean aglSwitch;
    private boolean htpSwitch;
    private boolean pnlSwitch;
    private boolean hplSwitch;
    
    private boolean globalSwitch;
    
    public CleanDataConfig(){
        
        try {
            
            this.loadSecionProperties("DBClean");
            
            setGlobalSwitch("on".equalsIgnoreCase(getProperty("db.global.switch","off")));
            
            if(isGlobalSwitch()) {
                setLogSwitch(true);
                setAplSwitch(true);
                setAglSwitch(true);
                setHtpSwitch(true);
                setPnlSwitch(true);
                setHplSwitch(true);
            } else {
                setLogSwitch("on".equalsIgnoreCase(getProperty("db.logSwitch", "off")));
                setAplSwitch("on".equalsIgnoreCase(getProperty("db.aplSwitch", "off")));
                setAglSwitch("on".equalsIgnoreCase(getProperty("db.aglSwitch", "off")));
                setHtpSwitch("on".equalsIgnoreCase(getProperty("db.htpSwitch", "off")));
                setPnlSwitch("on".equalsIgnoreCase(getProperty("db.pnlSwitch", "off")));
                setHplSwitch("on".equalsIgnoreCase(getProperty("db.hplSwitch", "off")));
            }
            holdTime = Integer.parseInt(getProperty("db.holdTime").trim());
        } catch (NumberFormatException e) {
            logger.error(e.getMessage());
        }
    }
    
    public int getHoldTime() {
        return holdTime;
    }
    
    public void setHoldTime(int holdTime) {
        setProperty("db.holdTime", "" + holdTime);
        this.holdTime = holdTime;
    }
    
    public boolean isLogSwitch() {
        return logSwitch;
    }
    
    public void setLogSwitch(boolean logSwitch) {
        setProperty("db.logSwitch", logSwitch ? "on" : "off");
        this.logSwitch = logSwitch;
    }
    
    public boolean isAplSwitch() {
        return aplSwitch;
    }
    
    public void setAplSwitch(boolean aplSwitch) {
        setProperty("db.aplSwitch", aplSwitch ? "on" : "off");
        this.aplSwitch = aplSwitch;
    }
    
    public boolean isAglSwitch() {
        return aglSwitch;
    }
    
    public void setAglSwitch(boolean aglSwitch) {
        setProperty("db.aglSwitch", aglSwitch ? "on" : "off");
        this.aglSwitch = aglSwitch;
    }

    public boolean isHtpSwitch() {
        return htpSwitch;
    }

    public void setHtpSwitch(boolean htpSwitch) {
        setProperty("db.htpSwitch", htpSwitch ? "on" : "off");
        this.htpSwitch = htpSwitch;
    }

    public boolean isPnlSwitch() {
        return pnlSwitch;
    }

    public void setPnlSwitch(boolean pnlSwitch) {
        setProperty("db.pnlSwitch", pnlSwitch ? "on" : "off");
        this.pnlSwitch = pnlSwitch;
    }

    public boolean isHplSwitch() {
        return hplSwitch;
    }

    public void setHplSwitch(boolean hplSwitch) {
        setProperty("db.hplSwitch", hplSwitch ? "on" : "off");
        this.hplSwitch = hplSwitch;
    }
    
    public boolean isGlobalSwitch() {
        return globalSwitch;
    }

    public void setGlobalSwitch(boolean globalSwitch) {
        setProperty("db.global.switch", globalSwitch ? "on" : "off");
        this.globalSwitch = globalSwitch;
    }
    
    public static void main(String[] args) {
        CleanDataConfig logs = new CleanDataConfig();
        System.out.println(logs.getHoldTime());
        System.out.println(logs.isAplSwitch());
        System.out.println(logs.isAglSwitch());
        System.out.println(logs.isAplSwitch());
        System.out.println(logs.isPnlSwitch());
        System.out.println(logs.isHtpSwitch());
        System.out.println(logs.isHplSwitch());
    }
}
