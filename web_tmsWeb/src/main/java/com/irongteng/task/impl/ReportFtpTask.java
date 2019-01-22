package com.irongteng.task.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.irongteng.comm.ftp.FTPStatus;
import com.irongteng.comm.ftp.FtpClient;
import com.irongteng.conf.ReportFtpConfig;

@Service("reportFtpTask")
public class ReportFtpTask extends Thread {
	
	private static List<File> filesPool = new CopyOnWriteArrayList<>();
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public void addFiles(List<File> files) {
		
		filesPool.addAll(files);
		
		if (filesPool.size() > 500) {
			List<File> oldFiles = new ArrayList<>();
			oldFiles.addAll(filesPool);
			//写入文件
			new HplFileWriterProcess(oldFiles).start();
			filesPool.clear();
		}
	}
	
    @Override
    public void run() {
    	List<File> oldFiles = new ArrayList<>();
    	oldFiles.addAll(filesPool);
        //获取log类型日志文件列表
        new HplFileWriterProcess(oldFiles).start();
        filesPool.clear();
    }
    
    private class HplFileWriterProcess extends Thread {  
        
    	public List<File> files;
    	
    	public HplFileWriterProcess(List<File> files) {
    		this.files = files;
    	}
    	
		@Override
		public void run() {
			ReportFtpConfig reportConfig = new ReportFtpConfig();
	    	//FTP上报如果开关为关，则不进行
	    	if (!reportConfig.isEnable()) return;
    		//ftp client
        	FtpClient client = new FtpClient();
        	try {
        		//连接FTP服务端
				client.connect(reportConfig.getIp(), reportConfig.getPort(), 
						reportConfig.getUsername(), reportConfig.getPassword());
				
				files.stream().filter(file -> file.exists() && file.length() > 0).forEach(tmpFile -> {
					
    				String name = tmpFile.getName();
	            	String remote = "/" + name + ".tmp";
	            	FTPStatus status;
					try {
						//上传文件
						status = client.upload(tmpFile.getAbsolutePath(), remote);
						if (FTPStatus.Upload_New_File_Success == status 
								|| FTPStatus.Upload_From_Break_Success == status) {
							//文件重命名
		            		status = client.rename(remote, name);
		            		if (FTPStatus.Remote_Rename_Success == status) {
		            			logger.info("文件" + name + "通过FTP上传成功");
		            		}
		            	}
					} catch (IOException e) {
						e.printStackTrace();
					}
	    		});
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					client.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
    }
    
}
