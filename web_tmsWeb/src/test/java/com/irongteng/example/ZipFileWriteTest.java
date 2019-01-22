package com.irongteng.example;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFileWriteTest {
    
    public static void main(String[] args) {
        String srcFilePath = "c:/hpl_log.sql";
        String zipFilePath = "c:/outfile.zip";
        
        zipFile(srcFilePath, zipFilePath);
    }

    /** 
     * 压缩文件 
     * @param srcFilePath 需要压缩的文件的完整路径
     * @param zipFilePath 压缩生成的文件的路径
     * */
    private static void zipFile(String srcFilePath, String zipFilePath) {
        if(srcFilePath == null){
            throw new RuntimeException("需要压缩的文件的完整路径 不能为空!");
        }
        if(zipFilePath == null){
            throw new RuntimeException("压缩生成的文件的路径 不能为空!");
        }
        
        ZipOutputStream zout = null;
        FileInputStream fin = null;
        
        try{
            File txtFile = new File(srcFilePath);
            fin = new FileInputStream(txtFile);
        }catch (FileNotFoundException e) {
            throw new RuntimeException("压缩失败!找不到文件" + srcFilePath);
        }finally {
            try {
                fin.close();
            } catch (Exception e) {
                
            }
        }
        
        try {
            zout = new ZipOutputStream(new FileOutputStream(new File(zipFilePath)));
            
            File srcFile = new File(srcFilePath);
            fin = new FileInputStream(srcFile);
            
            byte[] bb = new byte[4096];
            int i = 0;
            zout.putNextEntry(new ZipEntry(srcFile.getName()));
            while ((i = fin.read(bb)) != -1) {
                zout.setLevel(9);
                zout.write(bb, 0, i);
            }
        }  catch (IOException e) {
            throw new RuntimeException("压缩失败!", e);
        } finally {
            try {
                zout.close();
                fin.close();
            } catch (Exception e) {
                
            }
        }
    }

}