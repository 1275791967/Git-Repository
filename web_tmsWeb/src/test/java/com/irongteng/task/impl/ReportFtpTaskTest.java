package com.irongteng.task.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
public class ReportFtpTaskTest{
	
	@Test
    public void testFiles(){  
    	try {
			List<File> logsFiles = new ArrayList<>();
			logsFiles.add(new File("C:/WebCenter/logs/data.xlsx"));
	    	logsFiles.add(new File("C:/WebCenter/logs/NullFile.txt"));
	    	logsFiles.add(new File("C:/WebCenter/logs/NullFile2.txt"));
            ReportFtpTask center = new ReportFtpTask();
            center.addFiles(logsFiles);
            center.start();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            
        }
    } 
    
    public static void main(String [] args) {
    	try {
    		File logDir = new File("C:/WebCenter/logs/2017-01-12");
    		File[] files = logDir.listFiles(); 
    		
			List<File> logsFiles = new ArrayList<>();
			logsFiles.add(new File("C:/WebCenter/logs/data.xlsx"));
	    	logsFiles.add(new File("C:/WebCenter/logs/NullFile.txt"));
	    	logsFiles.add(new File("C:/WebCenter/logs/NullFile2.txt"));
            ReportFtpTask center = new ReportFtpTask();
            
            //center.addFiles(logsFiles);
            center.addFiles(Arrays.asList(files));
            center.start();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            
        }
    	
    }
}
