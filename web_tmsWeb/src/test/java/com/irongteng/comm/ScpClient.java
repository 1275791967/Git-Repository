package com.irongteng.comm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SFTPv3Client;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class ScpClient { 
    
    public static void main(String[] args) {
        
        String user = "root";   
        String pass = "******";   
        String host = "hostip";   
           
        Connection con = new Connection(host);   
        try {   
            con.connect();   
            boolean isAuthed = con.authenticateWithPassword(user, pass);   
            System.out.println("isAuthed===="+isAuthed);   
               
            /*   
            SCPClient scpClient = con.createSCPClient();   
            scpClient.put("localFiles", "remoteDirectory"); //从本地复制文件到远程目录   
            
            scpClient.get("remoteFiles","localDirectory");  //从远程获取文件  
            
            scpClient.put("D:\\apache-tomcat-6.0.29\\webapps\\recursor\\WEB-INF\\serverfile\\192.168.2.190\\policy\\strategy-baddomain.conf", "/root/testor"); //从本地复制文件到远程目录   
             */
//               
            SFTPv3Client sftpClient = new SFTPv3Client(con);   
            sftpClient.mkdir("newRemoteDir", 6);    //远程新建目录   
            sftpClient.rmdir("");                   //远程删除目录   
            
            sftpClient.createFile("newRemoteFile"); //远程新建文件   
            sftpClient.openFileRW("remoteFile");    //远程打开文件，可进行读写   
               
            Session session = con.openSession();   
            session.execCommand("uname -a && date && uptime && who");   //远程执行命令   
               
               
               
            //显示执行命令后的信息   
            System.out.println("Here is some information about the remote host:");   
            InputStream stdout = new StreamGobbler(session.getStdout());   
  
            BufferedReader br = new BufferedReader(new InputStreamReader(stdout));   
  
            while (true)   
            {   
                String line = br.readLine();   
                if (line == null)   
                    break;   
                System.out.println(line);   
            }   
  
            /* Show exit status, if available (otherwise "null") */  
  
            System.out.println("ExitCode: " + session.getExitStatus());   
  
            session.close();   
            con.close();   
        } catch (IOException e) {   
            e.printStackTrace();   
        }   
           
    } 
}