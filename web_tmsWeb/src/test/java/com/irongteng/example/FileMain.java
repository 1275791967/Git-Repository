package com.irongteng.example;

import java.io.File;

public class FileMain {
    public class FileManagerTask implements Runnable {
        @Override
        public void run() {
            File file = new File("c:/Test.txt");
            
            while (true) {
                try {
                    if (file.renameTo(file)) {
                        System.out.println("有权限");
                    } else {
                        System.out.println("无权限");
                    }
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
            
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        
        new Thread(new FileMain().new FileManagerTask()).start();
        Thread.sleep(1000);
        new Thread(new FileMain().new FileManagerTask()).start();
    }

}
