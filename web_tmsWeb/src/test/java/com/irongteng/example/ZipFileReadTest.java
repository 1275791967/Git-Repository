package com.irongteng.example;
import java.io.BufferedReader;  
import java.io.InputStreamReader;  
import java.util.Enumeration;
import java.util.zip.ZipEntry;  
import java.util.zip.ZipFile;  


public class ZipFileReadTest {

    @SuppressWarnings("unchecked")
    public static void readZipFile(String file) throws Exception {
        
        ZipFile zipFile = new ZipFile(file);
        
        Enumeration<ZipEntry> enu = (Enumeration<ZipEntry>) zipFile.entries();
        while (enu.hasMoreElements()) {
            ZipEntry zipElement = enu.nextElement();
            
            String fileName = zipElement.getName();
            System.out.println(fileName);

            if (!zipElement.isDirectory() && fileName.lastIndexOf(".sql") != -1) {// 是否为文件
                if (fileName.lastIndexOf(".sql") != -1) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(zipFile.getInputStream(zipElement), "utf-8"));

                    String line = null;
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                        /*
                         * if(!line.trim().equals("")){
                         * if(checkDuplicateRow(line.trim()))
                         * tempList.add(line.trim()); }
                         */
                    }
                    br.close();
                }
            }
        }
        
        zipFile.close();
    }
    
    public static void main(String[] args) throws Exception {
        /*
        try {  
            readZipFile("c:/Hotpoint_371_GSM139_2_201510231930.hp.zip");  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        */
        try {
            throw new Exception("test1:");
        } catch(Exception e) {
            throw e;
        } finally {
            System.out.println("test2");
        }
    }
    
}