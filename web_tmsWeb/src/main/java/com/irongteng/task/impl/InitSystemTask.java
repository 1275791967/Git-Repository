package com.irongteng.task.impl;

import java.io.File;

import com.irongteng.conf.LogsConfig;

/**
 * 用于初始化系统信息，如生成日志目录，加载重要参数等
 * @author lvlei
 *
 */
public class InitSystemTask implements Runnable  {

    @Override
    public void run() {
        //初始化FTP目录
        this.initFtpDir(); 
    }
    
    private void initFtpDir(){
        
        LogsConfig fmb = new LogsConfig();
        
        File ftpDir = new File(fmb.getFtpDir());
        if (!ftpDir.exists()) { //ftp文件存放目录
            ftpDir.mkdirs();
        }
        
        File logsdir = new File(fmb.getLogsDir());
        if (!logsdir.exists()) { //ftp处理后日志文件存放目录
            logsdir.mkdirs();
        }
        
        File errordir = new File(fmb.getErrorDir());
        if (!errordir.exists()) { //ftp处理后日志文件存放目录
            errordir.mkdirs();
        }
    }
}
