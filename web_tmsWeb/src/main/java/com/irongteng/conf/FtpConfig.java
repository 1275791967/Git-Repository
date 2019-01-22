package com.irongteng.conf;

public final class FtpConfig extends AbstractSystemConfig{
    
    private boolean enable;  //UDP开关
    private int port; //UDP服务端端口
    
    public FtpConfig() {
        try {
            
            this.loadSecionProperties("FTP");
            
            setEnable("on".equalsIgnoreCase(getProperty("ftp.switch", "off")));
            setPort(Integer.parseInt(getProperty("ftp.port", "8021")));
        } catch(NumberFormatException e) {
            logger.error(e.getMessage());
        }
    }
    
    public boolean isEnable() {
        return enable;
    }
    
    public void setEnable(boolean enable) {
        setProperty("ftp.switch", enable ? "on" : "off");
        this.enable = enable;
    }
    
    public int getPort() {
        return port;
    }
    
    public void setPort(int port) {
        setProperty("ftp.port", "" + port);
        this.port = port;
    }
    
    public static void main(String[] args) {
        FtpConfig fmb = new FtpConfig();
        System.out.println(fmb.isEnable());
        System.out.println(fmb.getPort());
    }
}
