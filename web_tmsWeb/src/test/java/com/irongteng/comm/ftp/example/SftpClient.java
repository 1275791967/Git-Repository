package com.irongteng.comm.ftp.example;

import java.io.IOException;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SFTPv3Client;
import ch.ethz.ssh2.Session;

public class SftpClient { 
    
    public static void main(String[] args) {
        
        String user = "ftpuser";   
        String pass = "123456";   
        String host = "127.0.0.1";   
           
        Connection con = new Connection(host, 21);   
        try {   
            con.connect();
            //boolean isAuthed = con.authenticateWithPassword(user, pass);   
            //System.out.println("isAuthed===="+isAuthed);   
            
            
            SFTPv3Client sftpClient = new SFTPv3Client(con);   
            sftpClient.mkdir("/newRemoteDir/", 6);    //远程新建目录   
            //sftpClient.rmdir("");                   //远程删除目录   
            
            sftpClient.createFile("/newRemoteFile"); //远程新建文件   
            sftpClient.openFileRW("/remoteFile");    //远程打开文件，可进行读写   
            
            Session session = con.openSession();   
            session.execCommand("uname -a && date && uptime && who");   //远程执行命令   
               
            //显示执行命令后的信息   
            System.out.println("Here is some information about the remote host:");   
            /*
            InputStream stdout = new StreamGobbler(session.getStdout());   
  
            BufferedReader br = new BufferedReader(new InputStreamReader(stdout));   
            
            while (true) {   
                String line = br.readLine();   
                if (line == null)   
                    break;   
                System.out.println(line);   
            }   
           */
           // Show exit status, if available (otherwise "null")  
  
            System.out.println("ExitCode: " + session.getExitStatus());   
      
            session.close();
            
            con.close();   
        } catch (IOException e) {   
            e.printStackTrace();   
        }   
           
    } 
}