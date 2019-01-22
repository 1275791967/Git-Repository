package com.irongteng.tcp;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServer {
    
    public void start() {
        try {
            ServerSocket ss = new ServerSocket(7777);
            System.out.println("start to accept...");
            Socket socket = ss.accept();

            // 建立输入流
            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            Object obj = ois.readObject();
            if (obj != null) {
                User user = (User) obj;// 把接收到的对象转化为user
                System.out.println("user: " + user.getName());
                System.out.println("password: " + user.getPassword());
            }
            ois.close();
            socket.close();
            ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new TestServer().start();
    }
}