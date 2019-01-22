package com.irongteng.example;

import java.io.File;

public class FileStatusCheck {
    public static void main(String[]args ) {
        /*
        File srcFile=new File("d:/test1/test.hp");
        File destFile=new File("d:/test2/test.hp");  
        if(srcFile.renameTo(destFile)){  
          System.out.println("文件未被操作");  
        }else{  
          System.out.println("文件正在被操作");  
        }
        */
        File file=new File("C:/ftproot/tmp/test.hp");
        
        if(file.renameTo(file)){  
          System.out.println("文件未被操作");  
        }else{  
          System.out.println("文件正在被操作");  
        }  
    }
}

