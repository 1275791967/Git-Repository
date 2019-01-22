package com.irongteng.conf;

public final class UdpConfig extends AbstractSystemConfig{
    
    private boolean enable;  //UDP开关
    private int serverPort; //UDP服务端端口
    private int clientPort; //UDP客户端端口
    
    public UdpConfig(){
        try {

            this.loadSecionProperties("UDP");
            
            setEnable("on".equalsIgnoreCase(getProperty("udp.switch", "off")));
            setServerPort(Integer.parseInt(getProperty("udp.server.port", "9102")));
            setClientPort(Integer.parseInt(getProperty("udp.client.port", "9101")));
        } catch (NumberFormatException e) {
            logger.info("UDP配置文件信息错误：" + e.getMessage());
        }
    }
    
    public boolean isEnable() {
        return enable;
    }
    
    public void setEnable(boolean enable) {
        setProperty("udp.switch", enable ? "on" : "off");
        this.enable = enable;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        setProperty("udp.server.port", "" + serverPort);
        this.serverPort = serverPort;
    }

    public int getClientPort() {
        return clientPort;
    }

    public void setClientPort(int clientPort) {
        setProperty("udp.client.port", "" + clientPort);
        this.clientPort = clientPort;
    }
    
    public static void main(String[] args) {
        UdpConfig fmb = new UdpConfig();
        System.out.println(fmb.isEnable());
        System.out.println(fmb.getServerPort());
        System.out.println(fmb.getClientPort());
    }
}
