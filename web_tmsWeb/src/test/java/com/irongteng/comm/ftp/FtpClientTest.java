package com.irongteng.comm.ftp;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Assert;

import com.irongteng.comm.ftp.FTPStatus;
import com.irongteng.comm.ftp.FtpClient;

public class FtpClientTest {
    
	private FtpClient client;
	private String hostname = "60.208.83.72";
	private int port = 21;
	private String username = "yantai"; 
	private String password = "1qaz2wsx3edc";

    @Before
    public void before() throws IOException {
    	client = new FtpClient();
    	boolean result = client.connect(hostname, port, username, password);
    	Assert.isTrue(result);
    }
    
	@Test
    public void testConnect() throws IOException{
    	
        boolean result = client.connect(hostname, port, username, password);
        Assert.isTrue(result);
    }

    /**
     * 
     * 上传文件到FTP服务器，支持断点续传
     * 
     * @throws IOException
     */
    @Test
    public void testUpload() throws IOException {
    	String local = "D:/3.txt";
    	String remote = "/3.txt"; 
    	
    	FTPStatus status = client.upload(local, remote);
    	if (status == FTPStatus.Upload_New_File_Success) {
        	System.out.println("上传新的文件下载成功");
        } else {
        	System.out.println("上传新的文件失败");
        }
    }
    
    @Test
    public void testRename() throws IOException {
        
    	FTPStatus status = client.rename("/3.txt", "4.txt");
    	if (status == FTPStatus.Delete_Remote_Success) {
    		System.out.println("重命名文件成功");
    	}
    }
    
    @Test
    public void testDownload() throws IOException {
    	String remote = "/4.txt"; 
    	String local = "D:/4.txt";
    	FTPStatus status = client.download(remote, local);
        if (status == FTPStatus.Download_From_Break_Success) {
        	System.out.println("文件下载成功");
        } else {
        	System.out.println("文件下载失败");
        }
    }
    
    @Test
    public void testDelete() throws IOException {
    	FTPStatus status = client.delete("/4.txt");
    	if (status == FTPStatus.Delete_Remote_Success) {
    		System.out.println("删除文件成功");
    	}
    }
    
    
    /**
     * 
     * 断开与远程服务器的连接
     * 
     * @throws IOException
     */
    @Test
    public void testDisconnect() throws IOException {
        
    }
}