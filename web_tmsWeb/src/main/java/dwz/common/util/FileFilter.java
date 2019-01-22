package dwz.common.util;

import java.io.File;
import java.io.FilenameFilter;

//实现FilenameFilter接口，可用于过滤器文件名 //本方法实现的是筛选指定格式结尾的文件 
public class FileFilter implements FilenameFilter {
    
    private final String suffix;
    
    public FileFilter(String suffix) {
        this.suffix = suffix.startsWith(".") ? suffix.toUpperCase() : "." + suffix.toUpperCase();
    }
    /**
     * 
     *      实现功能； 实现FilenameFilter接口，定义出指定的文件筛选器
     *      重写accept方法，测试指定文件是否应该包含在某一文件列表中
     */
    @Override
    public boolean accept(File dir, String name) {
        
        try {
            File file = new File(dir, name);
            
            if (name.toUpperCase().endsWith(suffix)) {
                // 判断文件是否被锁定，如果没有锁定，则返回真
                if(!FileUtils.isLocked(file)) {  
                    return true;
                } else {
                    System.out.println(name + "文件被锁定或者正在被其他程序调用");
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
