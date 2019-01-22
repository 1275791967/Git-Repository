package dwz.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

import org.junit.Test;

public class FileZipUtilTest {
    
    /**
     * 按照ZIP格式压缩文件
     */
    @Test
    public void testZipFile() throws FileNotFoundException, IOException {
        File sourceFile = new File("C:\\apps\\Hotpoint_755_你去死吧_2_201604251223.hp");
        File targetFile = new File("C:\\apps\\Hotpoint_755_你去死吧_2_201604251223.hp.zip");
        assertTrue(FileZipUtils.zipFile(sourceFile, targetFile));
    }
    
    @Test
    public void testZipFile_List() throws FileNotFoundException, IOException {
    	List<File> srcFiles = new ArrayList<File>();
    	srcFiles.add(new File("C:/WebCenter/logs"));
    	srcFiles.add(new File("D:/Tests/data.xlsx"));
    	srcFiles.add(new File("D:/Tests/import"));
        File targetFile = new File("D:/app_tmp.zip");
        assertTrue(FileZipUtils.zipFiles(srcFiles, targetFile));
    }
    
    @Test
    public void testZipDir() throws FileNotFoundException, IOException {
        File sourceFile = new File("C:/WebCenter/logs");
        File targetFile = new File("D:/app_tmp.zip");
        
        FileZipUtils.zipDir(sourceFile, targetFile);
    }
    
    /**
     * 按照ZIP格式解压文件
     * @param sourceFile：解压前原文件
     * @param targetFile: 解压后的目标文件
     * @return 解压成功返回true，否则返回失败
     */
    @Test
    public void testUnzipDir_1() {
        File sourceFile = new File("D:/app_tmp.zip");
        File targetFile = new File("D:/test");
        
        FileZipUtils.unzipDir(sourceFile, targetFile);
    }
    /**
     * 按照ZIP格式解压文件
     * @param sourceFile：解压前原文件
     * @param targetFile: 解压后的目标文件
     * @return 解压成功返回true，否则返回失败
     */
    @Test
    public void testUnzipDir() {
        File sourceFile = new File("D:/app_tmp.zip");
        File targetDir = new File("D:/test");
        
        FileZipUtils.unzipDir(sourceFile, targetDir);
    }
    /**
     * 按照GZIP格式打包文件
     * @param sourceFile：解压前原文件
     * @param targetFile: 解压后的目标文件
     * @return 解压成功返回true，否则返回失败
     */
    @Test
    public void testGzipFile() {
        File sourceFile = new File("D:/Tests/data.xlsx");
        File targetDir = new File("D:/data.gz");
        
        FileZipUtils.gzipFile(sourceFile, targetDir);
    }
    /**
     * 按照GZIP格式解压文件
     * @param sourceFile：解压前原文件
     * @param targetFile: 解压后的目标文件
     * @return 解压成功返回true，否则返回失败
     */
    @Test
    public void testUngzipFile() {
        File sourceFile = new File("D:/data.gz");
        File targetFile = new File("D:/data.xlsx");
        
        assertTrue(FileZipUtils.ungzipFile(sourceFile, targetFile));
    }
    
    /**
     * 按照GZIP格式解压文件
     * @param sourceFile：解压前原文件
     * @param targetFile: 解压后的目标文件
     * @return 解压成功返回true，否则返回失败
     */
    @Test
    public void testUngzipFile_1() {
        File sourceFile = new File("D:/backupfile.gz");
        File targetFile = new File("D:/test", ".sql");
        
        assertTrue(FileZipUtils.ungzipFile(sourceFile, targetFile));
    }
}
