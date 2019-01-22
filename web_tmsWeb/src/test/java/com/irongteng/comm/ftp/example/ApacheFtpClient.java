package com.irongteng.comm.ftp.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.junit.Test;

public class ApacheFtpClient {

    public  String uploadFile(String hostname, int port,String username, String password, String path, String filename, InputStream input) {
        String error_age="test";
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(hostname, port);//连接FTP服务器
            //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            boolean login = ftp.login(username, password);//登录
            System.out.println(login);
            
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            ftp.enterLocalPassiveMode();
            ftp.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return "error1";
            }
            
            if(!ftp.changeWorkingDirectory(path)) {
                File newFile = new File(path + filename);
                String dir = newFile.getParentFile().getPath();
                if (!ftp.changeWorkingDirectory(dir)) {
                    if (!ftp.makeDirectory(newFile.getParentFile().getPath())) {
                        //logger.debug("创建文件目录【" + dir + "】 失败！");
                    }
                }
            }
            //logger.info(path+"/"+filename);
            System.out.println(path+"/"+filename);
            ftp.storeFile(filename, input);            
            input.close();
            ftp.logout();
            //error_age=StatusCodes.UploadCompleted;
        } catch (IOException e) {
           // error_age=StatusCodes.ErrorOnUpload;
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                    //error_age=StatusCodes.ErrorOnUpload;
                }
            }
        }

        return error_age;

    }
    
    public  String downloadFile(String hostname, int port,String username, String password, String dir, String filename, OutputStream output) {
        String error_age="test";
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(hostname, port);//连接FTP服务器
            //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            boolean login = ftp.login(username, password);//登录
            System.out.println(login);
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            ftp.enterLocalPassiveMode();
            ftp.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return "error1";
            }
            
            if(!ftp.changeWorkingDirectory(dir)) {
                File newFile = new File(dir + filename);
                
                if (!ftp.changeWorkingDirectory(dir)) {
                    if (!ftp.makeDirectory(newFile.getParentFile().getPath())) {
                        //logger.debug("创建文件目录【" + dir + "】 失败！");
                    }
                }
            }
            
            System.out.println(dir+"/"+filename);
            ftp.retrieveFile(filename, output);
            output.close();
            ftp.logout();
            //error_age=StatusCodes.UploadCompleted;
        } catch (IOException e) {
           // error_age=StatusCodes.ErrorOnUpload;
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                    //error_age=StatusCodes.ErrorOnUpload;
                }
            }
        }

        return error_age;

    }
    
    @Test
    public void testUploadFile(){
            
            ApacheFtpClient test = new ApacheFtpClient();
            File file = new File("d:/Hotpoint_755_GSM139_2_201603081223.hp");
            InputStream input;
            try {
                input = new FileInputStream(file);
                String error = test.uploadFile("127.0.0.1", 21, "ftpuser", "123456", "/", file.getName(), input);
                System.out.println(error);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
    }
    
    @Test
    public void testDownloadFile(){
            
            ApacheFtpClient test = new ApacheFtpClient();
            File file = new File("d:/Hotpoint_755_GSM139_2_201603081223.hp");
            OutputStream output;
            try {
                output = new FileOutputStream(file);
                String error = test.downloadFile("127.0.0.1", 21, "ftpuser", "123456", "/", file.getName(), output);
                System.out.println(error);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
    }
    
    public static void main(String[] args) {
        /*
        FtpClient ftpClient = FtpClient.create();
        SocketAddress address = new InetSocketAddress("127.0.0.1",21);
        
        try {
            ftpClient.connect(address);
            Thread.sleep(1000);
            ftpClient.login("ftpuser", null, "123456");
            Thread.sleep(1000);
            ftpClient.changeDirectory("/");
            ftpClient.deleteFile("test.hp");
        } catch (FtpProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
    }
}
