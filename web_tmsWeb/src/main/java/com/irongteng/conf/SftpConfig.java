package com.irongteng.conf;

public final class SftpConfig  extends AbstractSystemConfig{
    

    private boolean enable;  //UDP开关
    private int port; //UDP服务端端口
    
    public SftpConfig(){
        try {
            this.loadSecionProperties("SFTP");
            
            setEnable("on".equalsIgnoreCase(getProperty("sftp.switch", "off")));
            setPort(Integer.parseInt(getProperty("sftp.port", "8022")));
        } catch(NumberFormatException e) {
            logger.info("SFTP配置文件信息错误：" + e.getMessage());
        }
        
    }
    
    public boolean isEnable() {
        return enable;
    }
    
    public void setEnable(boolean enable) {
        setProperty("sftp.switch", enable ? "on" : "off");
        this.enable = enable;
    }
    
    public int getPort() {
        return port;
    }
    
    public void setPort(int port) {
        setProperty("sftp.port", "" + port);
        this.port = port;
    }
    
    public static void main(String[] args) {
        SftpConfig fmb = new SftpConfig();
        System.out.println(fmb.isEnable());
        System.out.println(fmb.getPort());
    }
}
