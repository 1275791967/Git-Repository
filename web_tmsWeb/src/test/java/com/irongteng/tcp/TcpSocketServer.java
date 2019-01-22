package com.irongteng.tcp;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;  
  
public class TcpSocketServer {  
    
    private final static Logger logger = Logger.getLogger(TcpSocketServer.class.getName());  
    
    private ServerSocket server;
    private ThreadPoolCenter center;
    
    public void start(){  
        
        try {
            //启动TCP服务
            server = new ServerSocket(10000);
            
            //启动程序缓存池
            center = ThreadPoolCenter.getInstance();
            
            while (true) {  
                Socket socket = server.accept();
                
                center.add(new ThreadProcess(socket));
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        
    } 
    
    public void stop(){  
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        center.stop();
    }
     
    private class ThreadProcess implements Runnable {
        
        private Socket socket;
        
        public ThreadProcess(Socket socket) {
            this.socket = socket;
        }
        
        @Override
        public void run() {
            ObjectInputStream is = null;
            ObjectOutputStream os = null;
            
            try {  
                is = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));  
                os = new ObjectOutputStream(socket.getOutputStream());  

                Object obj = is.readObject();
                
                if (obj instanceof User) {
                    User user = (User)obj;  
                    System.out.println("user: " + user.getName() + "/" + user.getPassword());  

                    user.setName(user.getName() + "_new");  
                    user.setPassword(user.getPassword() + "_new");  
                    
                    os.writeObject(user);
                }
                os.flush();
                
            } catch (IOException ex) {  
                logger.error(ex);  
            } catch(ClassNotFoundException ex) {  
                logger.error(ex);  
            } finally {  
                try {  
                    if(is!=null) is.close();  
                } catch(Exception ex) {}  
                try {  
                    if(os!=null)  os.close();  
                } catch(Exception ex) {}  
                try {  
                    socket.close();  
                } catch(Exception ex) {}  
            }
        }
        
    }
    
    public static void main(String[] args) throws IOException {  
        TcpSocketServer server = new TcpSocketServer();
        server.start();
    } 
} 