package com.irongteng.conf;

public final class TcpConfig extends AbstractSystemConfig{
    
    private boolean enable;  //TCP开关
    private int serverPort;  //TCP服务端端口
    private int clientPort;  //TCP客户端端口
    
    public TcpConfig(){
        
        try {
            this.loadSecionProperties("TCP");
            setEnable("on".equalsIgnoreCase(getProperty("tcp.switch", "off")));
            setServerPort(Integer.parseInt(getProperty("tcp.server.port", "9118")));
            setClientPort(Integer.parseInt(getProperty("tcp.client.port", "9117")));
        } catch (NumberFormatException e) {
            logger.info("TCP配置文件信息错误：" + e.getMessage());
        }
    }
    
    public boolean isEnable() {
        return enable;
    }
    
    public void setEnable(boolean enable) {
        setProperty("tcp.switch", enable ? "on" : "off");
        this.enable = enable;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        setProperty("tcp.server.port", "" + serverPort);
        this.serverPort = serverPort;
    }

    public int getClientPort() {
        return clientPort;
    }

    public void setClientPort(int clientPort) {
        setProperty("tcp.client.port", "" + clientPort);
        this.clientPort = clientPort;
    }
    
    public static void main(String[] args) {
        TcpConfig fmb = new TcpConfig();
        System.out.println(fmb.isEnable());
        System.out.println(fmb.getServerPort());
        System.out.println(fmb.getClientPort());
    }
}
