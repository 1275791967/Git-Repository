package com.irongteng.conf;

import dwz.common.util.AbstractConfig;
import java.io.File;
import java.net.URISyntaxException;

public abstract class AbstractSystemConfig extends AbstractConfig{
    
    public AbstractSystemConfig() {
        //加载配置信息
        load(this.getAppHomeDir());
    }
    
    protected final String getAppHomeDir() {
        File sysFile = new File(new File(AbstractConfig.class.getResource("/").getPath()).getParentFile().getParentFile().getParentFile().getParentFile().getParentFile().toURI().getPath(), ConfigConstants.SYS_CONFIG_FILE);
        
        if (!sysFile.exists()) {
            try {
                //以类的形式读取的配置文件
                sysFile = new File(AbstractConfig.class.getResource("/").toURI().getPath(), ConfigConstants.SYS_CONFIG_FILE);
            } catch (URISyntaxException e) {
                logger.error(e.getMessage());
            }
        }
        return sysFile.toURI().getPath();
    }
    
}
