package com.irongteng.conf;

import java.util.Date;

import dwz.common.util.time.DateUtils;

public final class VersionConfig extends AbstractSystemConfig{
    
    private String version;
    private Date updateDate;
    
    public VersionConfig() {
        try {
            this.loadSecionProperties("Version");
            
            setVersion(getProperty("app.version"));
            setUpdateDate(DateUtils.parseStandardDate(getProperty("app.update.time", DateUtils.formatStandardDate(new Date()))));
        } catch(Exception e) {
            logger.info("版本信息配置文件错误：" + e.getMessage());
        }
        
    }
    
    public String getVersion() {
        return version;
    }
    
    public void setVersion(String version) {
        setProperty("app.version",version);
        this.version = version;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        setProperty("app.update.time", DateUtils.formatStandardDate(updateDate));
        this.updateDate = updateDate;
    }
    
    public static void main(String[] args) {
        VersionConfig logs = new VersionConfig();
        logs.setVersion("1.2.3");
        logs.setUpdateDate(new Date());
        
        logs.save();
        
        System.out.println(logs.getVersion());
        System.out.println(logs.getUpdateDate());
    }
}
