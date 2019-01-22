package com.irongteng.conf;
public final class FtpsConfig extends AbstractSystemConfig{
    
    private boolean enable;  //UDP开关
    private int port; //UDP服务端端口
    
    public FtpsConfig() {
        
        loadSecionProperties("FTPS");
        
        setEnable("on".equalsIgnoreCase(getProperty("ftps.switch", "off")));
        setPort(Integer.parseInt(getProperty("ftps.port", "8990")));
    }
    
    public boolean isEnable() {
        return enable;
    }
    
    public void setEnable(boolean enable) {
        setProperty("ftps.switch", enable ? "on" : "off");
        this.enable = enable;
    }
    
    public int getPort() {
        return port;
    }
    
    public void setPort(int port) {
        setProperty("ftps.port", "" + port);
        this.port = port;
    }
    
    public static void main(String[] args) {
        FtpsConfig fmb = new FtpsConfig();
        System.out.println(fmb.isEnable());
        System.out.println(fmb.getPort());
        
        fmb.setEnable(false);
        fmb.setPort(7990);
        System.out.println(fmb.isEnable());
        System.out.println(fmb.getPort());
        fmb.save();
    }
}
