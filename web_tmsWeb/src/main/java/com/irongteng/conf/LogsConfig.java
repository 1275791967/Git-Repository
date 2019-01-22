package com.irongteng.conf;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public final class LogsConfig extends AbstractSystemConfig{
    
    private String ftpDir;
    private String logsDir;
    private String errorDir;
    
    public LogsConfig() {
        
        try {
            DirConfig dir = new DirConfig();
            File tmpDir = new File(dir.getHomeDir() + "/tmp");
            if (!tmpDir.exists()) tmpDir.mkdirs();
            
            File logDir = new File(dir.getHomeDir() + "/logs");
            if (!logDir.exists()) logDir.mkdirs();
            
            File errDir = new File(dir.getHomeDir() + "/logs/error");
            if (!errDir.exists()) errDir.mkdirs();
            
            setFtpDir(dir.getHomeDir() + "/tmp");
            setLogsDir(dir.getHomeDir() + "/logs");
            setErrorDir(dir.getHomeDir() + "/logs/error");
            
        } catch (Exception e) {
        }
    }
    
    public String getFtpDir() {
        return ftpDir;
    }
    public void setFtpDir(String ftpDir) {
        this.ftpDir = ftpDir;
    }
    public String getLogsDir() {
        return logsDir;
    }

    public void setLogsDir(String logsDir) {
        this.logsDir = logsDir;
    }

    public String getErrorDir() {
        return errorDir;
    }

    public void setErrorDir(String errorDir) {
        this.errorDir = errorDir;
    }
    
    public static void main(String[] args) {
        LogsConfig logs = new LogsConfig();
        System.out.println(logs.getLogsDir());
        System.out.println(logs.getFtpDir());
        System.out.println(logs.getErrorDir());
        
        try {
            System.out.println(URLDecoder.decode((new DirConfig().getHomeDir() + "/license/license.dat"), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            
        }
    }
}
