package com.irongteng.tcp;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestClient {
    public static void main(String[] args) {
        new TestClient().start();
    }

    public void start() {
        try {
            Socket socket = new Socket("127.0.0.1", 7777);
            // 建立输入流
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            User user = new User();
            user.setName("梁国俏");
            user.setPassword("123456");
            // 输入对象, 一定要flush（）
            oos.writeObject(user);
            oos.flush();

            oos.close();
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}