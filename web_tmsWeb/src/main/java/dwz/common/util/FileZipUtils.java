package dwz.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileZipUtils {
    public static Log log = LogFactory.getLog(FileZipUtils.class);
    /**
     * 按照ZIP格式压缩文件
     * @param sourceFile：压缩前原文件
     * @param targetFile: 压缩后目标文件
     * @return 压缩成功返回true，否则返回失败
     */
    public static boolean zipFile(File srcFile, File targetFile) {
        List<File> files = new ArrayList<>();
        files.add(srcFile);
        
        return zipFiles(files,targetFile);
    }
    
    /**
     * 按照ZIP格式压缩文件
     * @param sourceFile：压缩前原文件
     * @param outputStream: 压缩后目标文件的输出流
     * @return 压缩成功返回true，否则返回失败
     */
    public static boolean zipFile(File srcFile, OutputStream outputStream) {
        List<File> files = new ArrayList<>();
        files.add(srcFile);
        
        return zipFiles(files,outputStream);
    }
    
    /**
     * 按照ZIP格式压缩文件
     * @param sourceFiles：要压缩的文件列表
     * @param targetFile: 压缩后目标文件
     * @return 压缩成功返回true，否则返回失败
     */
    public static boolean zipFiles(List<File> srcFiles, File targetFile) {
        
        try {
            if (srcFiles == null) throw new NullPointerException("传递的要压缩的文件不能为空！");
            if (targetFile == null)  throw new NullPointerException("指定要生成的研所文件不能为空！");
            
            File parentDir = targetFile.getParentFile();
            
            if (!parentDir.exists()) {
            	if (!parentDir.mkdirs()) throw new SecurityException("你没有权限创建文件夹");
            	
            	return zipFiles(srcFiles, new FileOutputStream(targetFile));
            }
        } catch(NullPointerException | FileNotFoundException | SecurityException e) {
            log.error(e.getMessage());
        }
        return false;
    }
    
    /**
     * 按照ZIP格式压缩文件
     * @param sourceFiles：要压缩的文件列表
     * @param outputStream: 压缩后的文件输出流
     * @return 压缩成功返回true，否则返回失败
     */
    public static boolean zipFiles(List<File> srcFiles, OutputStream outputStream) {
        
        try {
        	
            if (srcFiles == null)  throw new NullPointerException("传递的要压缩的文件不能为空！");
            if (outputStream == null) throw new NullPointerException("输出流不能为空不能为空！");
            
            try (ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(outputStream))) {
            	
            	srcFiles.stream().filter((srcFile) -> !(!srcFile.exists())).forEach((File srcFile) -> {
            		
            		if (srcFile.isDirectory()) {
                        try {
                        	String zipDir = srcFile.getName() + "/";
                            ZipEntry ze = new ZipEntry(zipDir);
                            ze.setTime(srcFile.lastModified());
							zos.putNextEntry(ze);
							// 递归调用
	                        File[] files = srcFile.listFiles();
	                        for (File file: files) {
	                            try {
	                                zipFile(zipDir, file, zos);
	                            } catch(Exception e) {
	                                log.error(e.getMessage());
	                            }
	                        }
						} catch (IOException e1) {
							e1.printStackTrace();
						}
                    } else { // 是文件时的操作
                		try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcFile), 1024);) {
                			ZipEntry entry = new ZipEntry(srcFile.getName());
                			entry.setTime(srcFile.lastModified());
                			zos.putNextEntry(entry);
                            
                            int read;
                            byte[] buffer = new byte[1024];
                            while((read = bis.read(buffer, 0, buffer.length)) != -1){
                                zos.write(buffer, 0, read);
                                zos.flush();
                            }
                        } catch(Exception e) {
                            log.error(e.getMessage());
                        }
                	}
                });
            }
            
            return true;
        } catch(Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }
    
    /**
     * 用来压缩文件夹下的所有子文件 此方法为一个递归调法方法
     * 
     * @param parenZipDir
     *            要压缩的文件在压缩包中的相对路径
     * @param srcFile
     *            　要压缩的文件引用
     * @param zos
     *            　　 压缩文件输出流
     * @throws IOException
     */
    private static void zipFile(String parenZipDir, File srcFile, ZipOutputStream zos)
            throws IOException {
        
        // 是文件夹的操作
        if (srcFile.isDirectory()) {
            String zipDir = parenZipDir + srcFile.getName() + "/";
            
            ZipEntry ze = new ZipEntry(zipDir);
            ze.setTime(srcFile.lastModified());
            zos.putNextEntry(ze);
            // 递归调用
            File[] files = srcFile.listFiles();
            for (File file: files) {
                try {
                    zipFile(zipDir, file, zos);
                } catch(Exception e) {
                    log.error(e.getMessage());
                }
            }
            // 是文件时的操作
        } else {
            try (FileInputStream fis = new FileInputStream(srcFile)) {
                ZipEntry ze = new ZipEntry(parenZipDir + srcFile.getName());
                ze.setTime(srcFile.lastModified());
                zos.putNextEntry(ze);
                byte[] b = new byte[4096];
                int i;
                while ((i = (fis.read(b))) > 0) {
                    zos.write(b, 0, i);
                }
            }
        }
    }
    
    /**
     * 压缩一个文件夹下的所有文件 注意中间路径连接用"/" 如：c:/tt/ttt
     * 
     * @param srcDir
     *            要压缩的文件夹路径
     * @param targetFile
     *            目标压缩文件
     * @return
     */
    public static boolean zipDir(File srcDir, File targetFile){
        
        try {
            if (srcDir == null) throw new NullPointerException("传递的要压缩的文件目录不能为空！");
            if (targetFile == null) throw new NullPointerException("指定要生成的压缩文件不能为空！");
            if (!srcDir.isDirectory())  throw new IOException("传入了不正确的源文件夹路径！");
            
            File parentDir = targetFile.getParentFile();
            
            if (!parentDir.exists()) {
            	if (!parentDir.mkdirs()) throw new SecurityException("你没有权限创建文件夹");
            	
            	return zipDir(srcDir, new FileOutputStream(targetFile));
            }
        } catch (Exception e) {
            log.info("compress file error！");
        }
        return false;
    }
    
     /**
     * 压缩一个文件夹下的所有文件 注意中间路径连接用"/" 如：c:/tt/ttt
     * 
     * @param srcDir
     *            要压缩的文件夹路径
     * @param outputStream
     *            目标压缩文件输出流
     * @return
     */
    private static boolean zipDir(File srcDir, OutputStream outputStream){
        
        try {
            if (srcDir == null) throw new NullPointerException("传递的要压缩的文件目录不能为空！");
            if (outputStream == null) throw new NullPointerException("压缩文件的文件输出流不能为空！");
            if (!srcDir.isDirectory())  throw new IOException("传入了不正确的源文件夹路径！");
            
            try (ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(outputStream))) {
                String zipDir = srcDir.getName() + "/";
                
                ZipEntry ze = new ZipEntry(zipDir);
                ze.setTime(srcDir.lastModified());
                zos.putNextEntry(ze);
                
                File[] files = srcDir.listFiles();

                for (File file : files) {
                    try {
                        zipFile(zipDir, file, zos);
                    } catch(Exception e) {
                        log.error(e.getMessage());
                    }
                }
                return true;
            }
        } catch (NullPointerException | IOException e) {
            System.out.println("compress file error！");
            log.error(e.getMessage());
            return false;
        }
    }
        
    /**
     * 按照ZIP格式解压文件
     * @param sourceFile：解压前原文件
     * @param targetFile: 解压后的目标文件
     * @return 解压成功返回true，否则返回失败
     */
    public static boolean unzipFile(File sourceFile, File targetFile) {
    	
        try {
            if (sourceFile == null) throw new NullPointerException("传递的要压缩的文件不能为空！");
            if (targetFile == null) throw new NullPointerException("指定要生成的研所文件不能为空！");
            if (sourceFile.isDirectory())  throw new IOException("源文件不能是文件夹！");
            
            File parentDir = targetFile.getParentFile();
            if (!parentDir.exists()) {
            	// 创建所有父文件夹
            	if (!parentDir.mkdirs()) throw new SecurityException("你没有权限创建文件夹");
            	
            	try (ZipFile zipFile = new ZipFile(sourceFile, Charset.forName("GBK"))) {
                    
                    @SuppressWarnings("unchecked")
                    Enumeration<ZipEntry> enu =  (Enumeration<ZipEntry>) zipFile.entries();
                    
                    while (enu.hasMoreElements()) {
                        ZipEntry zipEle = enu.nextElement();
                        long time = 0;
                        if (!zipEle.isDirectory()) {

                            time = zipEle.getTime();
                            
                        	try (InputStream is = zipFile.getInputStream(zipEle);
                                FileOutputStream fos = new FileOutputStream(targetFile);) {
                        		
                                int BUFFER = 1024;
                                int count; 
                                byte data[] = new byte[BUFFER];  
                                while ((count = is.read(data, 0, BUFFER)) != -1) {  
                                    fos.write(data, 0, count);  
                                } 
                                return true;
                            } catch (UnsupportedEncodingException e) {
                                log.error(e.getMessage());
                            } catch (IOException e) {
                                log.error(e.getMessage());
                            } finally {
                            	if (time > 0) targetFile.setLastModified(time);
                            }
                        }
                    }
                }
            	
            }
        } catch (NullPointerException | IOException e) {
            log.error(e.getMessage());
        }
        return false;
    }
    
    /**
     * 按照ZIP格式解压文件
     * @param sourceFile：解压前原文件
     * @param targetDir: 解压后的目标目录
     */
    public static void unzipDir(File srcFile,File targetDir) {
        
        try {
            if (srcFile == null) throw new NullPointerException("传递的要压缩的文件不能为空！");
            if (targetDir == null) throw new NullPointerException("指定要生成的研所文件不能为空！");
            if (srcFile.isDirectory())  throw new IOException("源文件不能是文件夹！");
            if (!targetDir.exists())  {
            	if (!targetDir.mkdirs()) throw new SecurityException("你没有权限创建文件夹");;
            }
            if (!targetDir.isDirectory()) throw new IOException("目标必须是是文件夹！");
            
            try (ZipFile zipFile = new ZipFile(srcFile, Charset.forName("GBK"));) {
                
                @SuppressWarnings("unchecked")
                Enumeration<ZipEntry> enu =  (Enumeration<ZipEntry>) zipFile.entries();

                while (enu.hasMoreElements()) {
                    ZipEntry zipEle = enu.nextElement();
                    //System.out.println(zipEle);
                    File tmpFile = new File(targetDir.getAbsolutePath() + "/" +  zipEle.getName());
                    
                    if (! zipEle.isDirectory()) {
                    	try (InputStream is = zipFile.getInputStream(zipEle);
                                FileOutputStream fos = new FileOutputStream(tmpFile);){
                    		int BUFFER = 1024;
                            int count; 
                            byte data[] = new byte[BUFFER];  
                            while ((count = is.read(data, 0, BUFFER)) != -1) {  
                                fos.write(data, 0, count);  
                            }
                    	} catch (UnsupportedEncodingException e) {
                            log.error(e.getMessage());
                        } catch (IOException e) {
                            log.error(e.getMessage());
                        }
                    } else {
                    	if (tmpFile.exists() && tmpFile.isFile()) tmpFile.delete();
                    	
                        tmpFile.mkdirs();
                    }
                    tmpFile.setLastModified(zipEle.getTime());
                }
            } catch (NullPointerException | IOException e) {
                log.error(e.getMessage());
            }
        } catch (NullPointerException | IOException e) {
            log.error(e.getMessage());
        }
    }
    
    /**
     * 按照GZIP格式压缩文件
     * @param sourceFile：压缩前原文件
     * @param targetFile: 压缩后目标文件
     * @return 压缩成功返回true，否则返回失败
     */
    public static boolean gzipFile(File sourceFile,File targetFile) {
        boolean status = false;
        
        try (FileInputStream fis = new FileInputStream(sourceFile.getAbsolutePath());
            GZIPOutputStream gos = new GZIPOutputStream(new FileOutputStream(targetFile.getAbsolutePath()));) {
            
            int count;  
            int BUFFER = 1024;
            byte data[] = new byte[BUFFER];  
            while ((count = fis.read(data, 0, BUFFER)) != -1) {  
                gos.write(data, 0, count);  
            }
            status = true;
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return status;
    }
    
    /**
     * 按照GZIP格式解压文件
     * @param sourceFile：解压前原文件
     * @param targetFile: 解压后目标文件
     * @return 解压成功返回true，否则返回失败
     */
    public static boolean ungzipFile(File sourceFile,File targetFile) {
        File targetDir = targetFile.getParentFile();
        String fileType = targetFile.getName().substring(targetFile.getName().indexOf("."));
        
        return ungzipFile(sourceFile,targetDir, fileType);
    }
    
    /**
     * 按照GZIP格式解压文件
     * @param sourceFile：解压前原文件
     * @param targetDir: 解压后目标文件夹
     * @param fileType  文件类型
     * @return 解压成功返回true，否则返回失败
     */
    public static boolean ungzipFile(File sourceFile, File targetDir, String fileType) {
        
        boolean status = false;
        
        try {
            if (sourceFile == null) {
                throw new NullPointerException("传递的要压缩的文件不能为空！");
            }
            if (targetDir == null) {
                throw new NullPointerException("指定要生成的研所文件不能为空！");
            }
            if (sourceFile.isDirectory())  throw new IOException("源文件不能是文件夹！");
            if (!targetDir.exists())  targetDir.mkdirs();
            if (!targetDir.isDirectory())  throw new IOException("目标必须是是文件夹！");
            
            fileType = fileType.startsWith(".") ? fileType : "." + fileType;
            String fileName = sourceFile.getName();
            File outputFile = new File(targetDir, fileName.substring(0, fileName.toLowerCase().indexOf(".")) + fileType);
            
            try (GZIPInputStream gis = new GZIPInputStream(new FileInputStream(sourceFile));
                OutputStream fos = new FileOutputStream(outputFile);) {
            	
                int count;  
                int BUFFER = 1024;
                byte[] data = new byte[BUFFER];  
                while ((count = gis.read(data, 0 , BUFFER)) != -1) {  
                    fos.write(data, 0, count);  
                }
                return true;
            } finally {
            	outputFile.setLastModified(sourceFile.lastModified());
            }
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return status;
    }
}
