package com.irongteng.conf;

public final class ReportFtpConfig extends AbstractSystemConfig {

    private boolean enable;
    private String ip;
    private int port;
    private String username;
    private String password;

    public ReportFtpConfig() {
        try {
            this.loadSecionProperties("ReportFTP");

            setEnable("on".equalsIgnoreCase(getProperty("report.ftp.switch", "off")));
            setIp(getProperty("report.ftp.ip", "192.168.1.231"));
            setPort(Integer.parseInt(getProperty("report.ftp.port", "21")));
            setUsername(getProperty("report.ftp.username", "logsuser"));
            setPassword(getProperty("report.ftp.password", "123456"));
        } catch (NumberFormatException e) {
            logger.info("文件上报FTP转发配置文件信息错误：" + e.getMessage());
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
        setProperty("report.ftp.ip", ip);
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
        setProperty("report.ftp.username", username);
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        setProperty("report.ftp.password", password);
        this.password = password;
    }

    public static void main(String[] args) {
        ReportFtpConfig fmb = new ReportFtpConfig();
        System.out.println(fmb.isEnable());
        System.out.println(fmb.getIp());
        System.out.println(fmb.getPort());
        System.out.println(fmb.getUsername());
        System.out.println(fmb.getPassword());
    }
}
