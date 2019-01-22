package com.irongteng.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 将官方厂家MAC提取出来，用于生成属性文件
 * @author lvlei
 *
 */
public class MacFileUtils {
    private static String hexFile = "d:/hex.properties";
    
    private static String base16File = "d:/base.properties";
    
    private static Properties hexProps = new Properties();
    private static Properties baseProps = new Properties();
    
    static {
        try {
            File hex = new File(hexFile);
            File base = new File(base16File);
            
            if (!hex.exists()) {
                hex.createNewFile();
            }
            
            if (!base.exists()) {
                base.createNewFile();
            }
            
            hexProps.load(new FileInputStream(hexFile));
            baseProps.load(new FileInputStream(base16File));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (IOException e) {       
            System.exit(-1);
        }
    }
    
    public static void main(String[] args) {
        
        try {
            
            String encoding="UTF-8";
            System.out.println(MacFileUtils.class.getResource("/").getPath() + "oui.txt");
            File file=new File(MacFileUtils.class.getResource("/").getPath() + "oui.txt");
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader( new FileInputStream(file), encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                
                while((lineTxt = bufferedReader.readLine()) != null){
                    //System.out.println(lineTxt);
                    if (lineTxt.contains("(hex)")) {
                        String[] strs = lineTxt.split("\\(hex\\)");
                        String key = strs[0].trim();
                        String value = strs[1].trim();
                        hexProps.setProperty(key, value);
                    } else if (lineTxt.contains("(base 16)")) {
                        String[] strs = lineTxt.split("\\(base 16\\)");
                        System.out.println(strs[0].trim() + "=" + strs[1].trim());
                        String key = strs[0].trim();
                        String value = strs[1].trim();
                        baseProps.setProperty(key, value);
                    } else {
                        
                    }
                }
                hexProps.store(new FileOutputStream(hexFile), "this is hex 16 string");
                baseProps.store(new FileOutputStream(base16File), "this is hex 16 string");
                read.close();
                
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
    }

}
