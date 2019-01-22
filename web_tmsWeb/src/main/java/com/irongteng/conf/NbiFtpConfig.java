package com.irongteng.conf;

public final class NbiFtpConfig  extends AbstractSystemConfig{
    

    private boolean enable;
    private String ip;
    private int port;
    private String username;
    private String password;
    
    public NbiFtpConfig(){
        try {
            this.loadSecionProperties("NbiFTP");
            
            setEnable("on".equalsIgnoreCase(getProperty("nbi.ftp.switch", "off")));
            setIp(getProperty("nbi.ftp.ip", "192.168.1.223"));
            setPort(Integer.parseInt(getProperty("nbi.ftp.port", "21")));
            setUsername(getProperty("nbi.ftp.username", "ftpuser"));
            setPassword(getProperty("nbi.ftp.password", "123456"));
        } catch(NumberFormatException e) {
            logger.error(e.getMessage());
        }
    }
    
    public boolean isEnable() {
        return enable;
    }
    
    public void setEnable(boolean enable) {
        setProperty("sftp.switch", enable ? "on" : "off");
        this.enable = enable;
    }
    
    public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		setProperty("nbi.ftp.ip", ip);
		this.ip = ip;
	}

	public int getPort() {
        return port;
    }
    
    public void setPort(int port) {
        setProperty("sftp.port", "" + port);
        this.port = port;
    }
    
    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		setProperty("nbi.ftp.username", username);
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		setProperty("nbi.ftp.password", password);
		this.password = password;
	}
	
	public static void main(String[] args) {
        NbiFtpConfig fmb = new NbiFtpConfig();
        System.out.println(fmb.isEnable());
        System.out.println(fmb.getIp());
        System.out.println(fmb.getPort());
        System.out.println(fmb.getUsername());
        System.out.println(fmb.getPassword());
    }
}
