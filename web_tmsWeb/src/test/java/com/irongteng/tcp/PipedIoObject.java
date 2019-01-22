package com.irongteng.tcp;

import java.io.*;
import java.util.Arrays;

import dwz.common.util.FileUtils;

class PipedIoObject {
    public static void main(String[] args) throws Exception {
        PipedInputStream pin = new PipedInputStream();
        PipedOutputStream pout = new PipedOutputStream();
        pin.connect(pout); // 输入流与输出流连接

        ReadIoThread readTh = new ReadIoThread(pin);
        WriteIoThread writeTh = new WriteIoThread(pout);
        new Thread(readTh).start();
        new Thread(writeTh).start();
    }

    public static void sop(Object obj) { // 打印
        System.out.println(obj);
    }
}

class ReadIoThread implements Runnable {
    private PipedInputStream pin;

    ReadIoThread(PipedInputStream pin)  {
        this.pin = pin;
    }

    @Override
    public void run() { // 由于必须要覆盖run方法,所以这里不能抛,只能try
        try {
            sop("R:读取前没有数据,阻塞中...等待数据传过来再输出到控制台...");
            
            sop("R:读取数据成功,阻塞解除...");
            while(true) {
                byte[] buf = new byte[1024*120];
                
                int len = pin.read(buf); // read阻塞
                
                if(len>0) {
                    Object obj = FileUtils.bytes2Object(Arrays.copyOf(buf, len));
                    if (obj instanceof User) {
                        User user = (User)obj;
                        System.out.println("name:" + user.getName() + " password:" + user.getPassword());
                    }
                }
            }
            
            //pin.close();
        } catch (Exception e) {
            throw new RuntimeException("R:管道读取流失败!");
        }
    }
    
    public static void sop(Object obj) { // 打印
        System.out.println(obj);
    }
}

class WriteIoThread implements Runnable {
    private PipedOutputStream pout;
    
    WriteIoThread(PipedOutputStream pout) {
        this.pout = pout;
    }

    @Override
    public void run() {
        try {
            sop("W:开始将数据写入:但等个5秒让我们观察...");
            Thread.sleep(5000); // 释放cpu执行权5秒
            int index = 0;
            while(true) {
                User user = new User();
                user.setName("admin_" + index);
                user.setPassword("pwd_" + index);
                
                pout.write(FileUtils.object2Bytes(user)); // 管道输出流
                
                Thread.sleep(500);
                index++;
            }
            //pout.close();
        } catch (Exception e) {
            throw new RuntimeException("W:WriteThread写入失败...");
        }
    }

    public static void sop(Object obj) {// 打印
        System.out.println(obj);
    }
}