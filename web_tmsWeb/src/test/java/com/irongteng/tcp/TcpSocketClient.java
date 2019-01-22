package com.irongteng.tcp;
import java.io.BufferedInputStream;  
import java.io.IOException;  
import java.io.ObjectInputStream;  
import java.io.ObjectOutputStream;  
import java.net.Socket;  
import java.util.logging.Level;  
import java.util.logging.Logger;  
  
public class TcpSocketClient {  
      
    private final static Logger logger = Logger.getLogger(TcpSocketClient.class.getName());  
      
    public static void main(String[] args) throws Exception {  
        for (int i = 0; i < 1000; i++) {  
            
            new ThreadClient(i).start();;
            
            /*
            Socket socket = null;  
            ObjectOutputStream os = null;  
            ObjectInputStream is = null;  
              
            try {  
                socket = new Socket("localhost", 10000);  
                
                os = new ObjectOutputStream(socket.getOutputStream());  
                User user = new User("user_" + i, "password_" + i); 
                
                os.writeObject(user);  
                os.flush();  
                  
                is = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));  
                Object obj = is.readObject();  
                if (obj != null) {  
                    user = (User)obj;  
                    System.out.println("user: " + user.getName() + "/" + user.getPassword());  
                }  
            } catch(IOException ex) {  
                logger.log(Level.SEVERE, null, ex);  
            } finally {  
                try {  
                    is.close();  
                } catch(Exception ex) {}  
                try {  
                    os.close();  
                } catch(Exception ex) {}  
                try {  
                    socket.close();  
                } catch(Exception ex) {}  
            } 
            */ 
        }  
    }
    
    public static class ThreadClient extends Thread {
        
        private int index;
        
        public ThreadClient(int index) {
            this.index = index;
            this.setName("user_thread_" + index);
        }
        
        @Override
        public void run() {
            
            Socket socket = null;  
            ObjectOutputStream os = null;  
            ObjectInputStream is = null;  
              
            try {  
                socket = new Socket("localhost", 10000);  
                
                os = new ObjectOutputStream(socket.getOutputStream());  
                User user = new User("user_" + index, "password_" + index); 
                
                os.writeObject(user);  
                os.flush();  
                  
                is = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));  
                Object obj = is.readObject();  
                if (obj != null) {  
                    user = (User)obj;  
                    System.out.println("user: " + user.getName() + "/" + user.getPassword());  
                }  
            } catch(IOException ex) {  
                logger.log(Level.SEVERE, null, ex);  
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {  
                try {  
                    if (is != null) is.close();  
                } catch(Exception ex) {}  
                try {  
                    if (os != null) os.close();  
                } catch(Exception ex) {}  
                try {  
                    if (socket != null) socket.close();  
                } catch(Exception ex) {}  
            } 
        }
    }
}  