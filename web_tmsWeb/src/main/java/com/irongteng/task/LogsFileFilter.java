package com.irongteng.task;

import java.io.File;
import java.io.FilenameFilter;

import dwz.common.util.FileUtils;
import dwz.common.util.LogFileSuffix;

//实现FilenameFilter接口，可用于过滤器文件名 //本方法实现的是筛选指定格式结尾的文件 
public class LogsFileFilter implements FilenameFilter {
    private String[] prefixs;
    private LogFileSuffix suffix;
    
    public LogsFileFilter(LogFileSuffix suffix) {
        this.suffix = suffix;
    }
    
    public LogsFileFilter(String[] prefixs, LogFileSuffix suffix) {
    	this.prefixs = prefixs;
        this.suffix = suffix;
    }
    /**
     * 
     * @param args
     *            实现功能； 实现FilenameFilter接口，定义出指定的文件筛选器
     *            重写accept方法，测试指定文件是否应该包含在某一文件列表中
     */
    @Override
    public boolean accept(File dir, String name) {
        
        try {
            File file = new File(dir, name);
            String upName = name.toUpperCase();
            if (upName.endsWith(suffix.name().toUpperCase())) {
                // 判断文件是否被锁定，如果没有锁定，则返回真
                if(!FileUtils.isLocked(file)) {
                	if (prefixs != null && prefixs.length > 0) {
                		for (String prefix: prefixs) {
                    		if (upName.startsWith(prefix.toUpperCase())) return true;
                    	}
                        return false;
                	}
                	return true;
                } else {
                    System.out.println(name + "文件没有发现，或者正在被锁定或被其他程序调用");
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
