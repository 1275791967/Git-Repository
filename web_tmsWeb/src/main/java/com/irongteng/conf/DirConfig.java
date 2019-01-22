package com.irongteng.conf;

import java.io.File;

public final class DirConfig extends AbstractSystemConfig{
    
    private String homeDir;
    
    public DirConfig() {
        try {
            this.loadSecionProperties("Dir");
            
            String hDir = getProperty("home.dir", ".");
            String cDir = "C:/WebCenter";
            String dDir = "D:/WebCenter";
            
            setHomeDir( !".".equals(hDir) ? hDir : new File(cDir, ConfigConstants.SYS_CONFIG_FILE).exists() 
                    ? cDir : new File(dDir, ConfigConstants.SYS_CONFIG_FILE).exists() ? dDir : this.getAppHomeDir());
        } catch(Exception e) {
            logger.error(e.getMessage());
        }
    }
    
    public String getHomeDir() {
        return homeDir;
    }
    
    public void setHomeDir(String homeDir) {
        setProperty("home.dir", homeDir);
        this.homeDir = homeDir;
    }
    
    public static void main(String[] args) {
        DirConfig dir = new DirConfig();
        System.out.println(dir.getHomeDir());
        dir.setHomeDir(dir.getHomeDir());
        dir.save();
    }
}
