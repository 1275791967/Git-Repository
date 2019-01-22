package dwz.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class FileUtilsTest {
    
    public Log logger = LogFactory.getLog(FileUtilsTest.class);
    
    /***
     * 复制单个文件
     */
    @Test
    public void testCopyFile_File() {
        File srcFile = new File("d:/app_tmp/out.csv");
        File destFile = new File("d:/app_tmp2/out.csv");
        FileUtils.copyFile(srcFile, destFile);
    }
    @Test
    public void testCopyFile_File_1() {
        long time = System.currentTimeMillis();
        File srcFile = new File("d:/王PT工：特工学院.mp4");
        File destFile = new File("d:/迅雷下载/王PT工：特工学院.mp4");
        FileUtils.copyFile(srcFile, destFile);
        System.out.println(((System.currentTimeMillis() - time)/1000) + "s");
    }
    
    /***
     * 复制单个文件
     */
    @Test
    public void testCopyFile_String() {
        String srcFile = "d:/app_tmp/out.csv";
        String destFile = "d:/app_tmp2/out.csv";
        FileUtils.copyFile(srcFile, destFile);
    }
    
    /**
     * 剪切单个文件
     */
    @Test
    public void testCutFile_File() {
        File srcFile = new File("d:/app_tmp2/out.csv");
        File destFile = new File("d:/app_tmp2/out2.csv");
        
        FileUtils.cutFile(srcFile, destFile);
    }
    
    /**
     * 剪切单个文件
     */
    @Test
    public void testCutFile_String() {
        String srcFile = "d:/app_tmp2/out2.csv";
        String destFile = "d:/app_tmp2/out3.csv";
        
        FileUtils.cutFile(srcFile, destFile);
    }
    /**
     * 剪切单个文件到指定目录
     */
    @Test
    public void testCutFile2Dir_File() {
        File srcFile = new File("d:/app_tmp2/out3.csv");
        File destDir = new File("d:/app_tmp/");
        FileUtils.cutFile2Dir(srcFile,destDir);
    }
    
    /**
     * 剪切单个文件到指定目录
     */
    @Test
    public void testCutFile2Dir_String() {
        String srcFile = "d:/app_tmp/out3.csv";
        String destDir = "d:/app_tmp2";
        
        FileUtils.cutFile2Dir(srcFile,destDir);
    }
    
    /**
     * 剪切单个文件到指定目录
     */
    @Test
    public void testCutFiles2Dir_String() {
        
        FileUtils.cutFile2Dir("d:/app_tmp2/out3.csv", "d:/app_tmp");
        
        String srcFile1 = "d:/app_tmp/out.csv";
        String srcFile2 = "d:/app_tmp/out3.csv";
        String[] files = new String[2];
        files[0] = srcFile1;
        files[1] = srcFile2;
        String destDir = "d:/app_tmp2";
        
        FileUtils.cutFiles2Folder(files,destDir);
    }
    /**
     * 剪切单个文件到指定目录
     */
    @Test
    public void testCutFiles2Dir_File() {
        
        File srcFile1 = new File("d:/app_tmp2/out.csv");
        File srcFile2 = new File("d:/app_tmp2/out3.csv");
        File[] files = new File[2];
        files[0] = srcFile1;
        files[1] = srcFile2;
        File destDir = new File("d:/app_tmp");
        
        FileUtils.cutFiles2Folder(files,destDir);
    }
    
    /**
     * 剪切文件列表到指定目录
     */
    @Test
    public void testCutFiles2Folder_List_String() {
        File srcFile1 = new File("d:/app_tmp/out.csv");
        File srcFile2 = new File("d:/app_tmp/out3.csv");
        List<File> files = new ArrayList<File>();
        files.add(srcFile1);
        files.add(srcFile2);
        
        FileUtils.cutFiles2Folder(files, "d:/app_tmp2");
    }
    
    /**
     * 复制整个文件夹内容
     */
    @Test
    public void testCopyFolder_File() {
        File srcDir = new File("d:/app_tmp");
        File destDir = new File("d:/app_tmp2");
        
        FileUtils.copyFolder(srcDir, destDir);
    }
    
    /**
     * 复制整个文件夹内容
     */
    @Test
    public void testCopyFolder_String() {
        String srcDir = new String("d:/app_tmp");
        String destDir = new String("d:/app_tmp2");
        
        FileUtils.copyFolder(srcDir, destDir);
    }
    
    /**
     * 剪切整个文件夹内容
     */
    @Test
    public void testCutFolder_File() {
        File srcDir = new File("d:/app_tmp");
        File destDir = new File("d:/app_tmp2");
        
        FileUtils.cutFolder(srcDir, destDir);
    }
    
    /**
     * 剪切整个文件夹内容
     */
    @Test
    public void testCutFolder_String() {
        String srcDir = new String("d:/app_tmp2");
        String destDir = new String("d:/app_tmp");
        
        FileUtils.cutFolder(srcDir, destDir);
    }
    
    /**
     * 剪切整个文件夹内容
     */
    @Test
    public void testCopyDirStructure() {
        String srcDir = new String("d:/app_tmp");
        String destDir = new String("d:/app_tmp2");
        
        FileUtils.copyDirStructure(srcDir, destDir);
    }
    /**
     * 剪切整个文件夹内容
     * @throws FileNotFoundException 
     */
    @Test
    public void testDeleteFiles_List() throws FileNotFoundException {
        List<File> files = new ArrayList<File>();
        File file1 = new File("d:/IMEI统计分析_0.csv");
        File file2 = new File("d:/IMEI统计分析_2.csv");
        File file3 = new File("d:/IMEI统计分析_4.csv");
        File file4 = new File("d:/IMEI统计分析_6.csv");
        
        files.add(file1);
        files.add(file2);
        files.add(file3);
        files.add(file4);
        
        FileZipUtils.zipFiles(files, new File("d:/imei_info.zip"));
        FileUtils.deleteFiles(files);
    }
    
    @Test
    public void testFile2Base64String() {
        System.out.println(FileUtils.file2Base64String("C:/apps/license/license.dat"));

    }

    @Test 
    public void testDefaultEncoding() {
        System.out.println(Charset.defaultCharset().name());
    }
}
