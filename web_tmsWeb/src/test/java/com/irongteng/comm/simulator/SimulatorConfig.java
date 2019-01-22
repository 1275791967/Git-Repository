package com.irongteng.comm.simulator;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class SimulatorConfig {
    
    private int deviceCount;
    private String ftpDir;
    private String logsDir;
    private String errorDir;
    private int fileTextLineCount;
    private int taskInterval;
    
    private boolean logSwitch;
    private boolean aplSwitch;
    private boolean aglSwitch;
    private boolean htpSwitch;
    private boolean pnlSwitch;
    private boolean hplSwitch;
    
    public SimulatorConfig() {
        Properties prop = new Properties();
        try {
            
            FileInputStream fis = new FileInputStream(SimulatorConfig.class.getResource("/").getPath() + SimulatorConstants.VM_CONFIG_FILE);
            
            prop.load(fis);
            deviceCount = Integer.parseInt(prop.getProperty("deviceCount", "10").trim());
            fileTextLineCount = Integer.parseInt(prop.getProperty("fileTextLineCount", "10").trim());
            ftpDir =  prop.getProperty("ftpDir", System.getProperty("user.dir") + File.separator + "files");
            logsDir =  prop.getProperty("logsDir", System.getProperty("user.dir") + File.separator + "files");
            errorDir = prop.getProperty("errorDir", System.getProperty("user.dir") + File.separator + "files");
            taskInterval = Integer.parseInt(prop.getProperty("taskInterval", "360").trim());
            
            logSwitch = prop.getProperty("logSwitch", "off").equalsIgnoreCase("on");
            aplSwitch = prop.getProperty("aplSwitch", "off").equalsIgnoreCase("on");
            aglSwitch = prop.getProperty("aglSwitch", "off").equalsIgnoreCase("on");
            htpSwitch = prop.getProperty("htpSwitch", "off").equalsIgnoreCase("on");
            pnlSwitch = prop.getProperty("pnlSwitch", "off").equalsIgnoreCase("on");
            hplSwitch = prop.getProperty("hplSwitch", "off").equalsIgnoreCase("on");
            
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public int getDeviceCount() {
        return deviceCount;
    }
    public void setDeviceCount(int deviceCount) {
        this.deviceCount = deviceCount;
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

    public int getFileTextLineCount() {
        return fileTextLineCount;
    }
    public void setFileTextLineCount(int fileTextLineCount) {
        this.fileTextLineCount = fileTextLineCount;
    }

    public int getTaskInterval() {
        return taskInterval;
    }

    public void setTaskInterval(int taskInterval) {
        this.taskInterval = taskInterval;
    }

    public boolean isLogSwitch() {
        return logSwitch;
    }

    public void setLogSwitch(boolean logSwitch) {
        this.logSwitch = logSwitch;
    }

    public boolean isAplSwitch() {
        return aplSwitch;
    }

    public void setAplSwitch(boolean aplSwitch) {
        this.aplSwitch = aplSwitch;
    }

    public boolean isAglSwitch() {
        return aglSwitch;
    }

    public void setAglSwitch(boolean aglSwitch) {
        this.aglSwitch = aglSwitch;
    }

    public boolean isHtpSwitch() {
        return htpSwitch;
    }

    public void setHtpSwitch(boolean htpSwitch) {
        this.htpSwitch = htpSwitch;
    }

    public boolean isPnlSwitch() {
        return pnlSwitch;
    }

    public void setPnlSwitch(boolean pnlSwitch) {
        this.pnlSwitch = pnlSwitch;
    }

    public boolean isHplSwitch() {
        return hplSwitch;
    }

    public void setHplSwitch(boolean hplSwitch) {
        this.hplSwitch = hplSwitch;
    }
    
}
