package dwz.common.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javax.imageio.stream.FileImageInputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class FileUtils {
    
    public static Log log = LogFactory.getLog(FileUtils.class);
    
    /***
     * 复制单个文件
     * 
     * @param srcFile File 原文件路径 如：c:/fqf.txt
     * 
     * @param destFile File 复制后路径 如：f:/fqf.txt
     * 
     * @return boolean
     */
    public static boolean copyFile(File srcFile, File destFile) {
        boolean result = false;
        try {
            File parent = destFile.getParentFile();
            
            if (!parent.exists()) parent.mkdirs();
            
            //多线程加快复制文件速度
            if (srcFile.exists()) {                 
                try ( // 文件存在时
                        FileChannel in = new FileInputStream(srcFile).getChannel();//得到对应的文件通道
                        FileChannel out = new FileOutputStream(destFile).getChannel()//得到对应的文件通道
                ) {
                    in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入
                    result = true;
                } catch (Exception e) {
                    System.out.println("复制单个文件操作出错");
                    result = false;
                }
            }
        } catch (Exception e) {
            System.out.println("创建目标文件路径出错");
        }
        return result;
    }
    /**
     * 复制单个文件
     * 
     * @param srcFilePath String 原文件路径 如：c:/fqf.txt
     * 
     * @param destFilePath String 复制后路径 如：f:/fqf.txt
     * 
     * @return boolean
     */
    public static boolean copyFile(String srcFilePath, String destFilePath) {
        return copyFile(new File(srcFilePath), new File(destFilePath));
    }
    
    /**
     * 剪切单个文件
     * @param srcFile
     * @param destFile
     * @return 
     */
    public static boolean cutFile(File srcFile, File destFile) {
        boolean result = false;
        try {
            if (!srcFile.exists()) {
                throw new FileNotFoundException(srcFile.getName() + "源文件不存在");
            }
            if (destFile.exists()) { //假如目标文件存在，则将原目标文件添加bak后缀
                //dest.renameTo(new File(destFile + ".bak"));
                destFile.delete();
            } else {
                if (!destFile.getParentFile().exists()) { //假如目标文件不存在，再判断父目录是否存在，如果不存在创建父目录
                    destFile.getParentFile().mkdirs();
                }
            }
            
            srcFile.renameTo(destFile);
            result = true;
            
        } catch (Exception e) {
            System.out.println("剪切单个文件操作出错" + e.getMessage());
        }
        return result;
    }
    

    /**
     * 剪切文件到指定文件，相当于重命名后然后剪切
     * 
     * @param srcFilePath 原文件路径 如：c:/fqf.txt
     * 
     * @param destFilePath 复制后路径 如：f:/fqf.txt
     * @return 
     * 
     */
    public static boolean cutFile(String srcFilePath, String destFilePath) {
        return cutFile(new File(srcFilePath), new File(destFilePath));
    }
    
    /**
     * 剪切单个文件到指定目录
     * 
     * @param srcFile String 原文件路径 如：c:/fqf.txt
     * 
     * @param destDir 复制后目标文件目录 如：f:/fqf.txt
     * 
     * @return boolean
     */
    public static boolean cutFile2Dir(File srcFile, File destDir) {
        boolean result = false;
        try {
            if (!srcFile.exists()) {
                throw new Exception(srcFile.getName() + "源文件不存在");
            }
            if (srcFile.isDirectory()) {
                throw new Exception(srcFile.getPath() + "是个目录，而不是文件");
            }
            if (!destDir.exists()) {
                destDir.mkdirs();
            } else {
                if (!destDir.isDirectory()) {
                    destDir.delete();
                    destDir.mkdir();
                }
            }
            
            File destFile = new File(destDir.getAbsolutePath() + File.separator + srcFile.getName());
            if (destFile.exists()) {
                destFile.delete();
            }
            srcFile.renameTo(destFile);
            
            result = true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return result;
    }
    /**
     * 
     * @param srcFilePath 
     *      源文件路径
     * @param destDir     
     *      目标文件目录路径
     * @return 
     */
    public static boolean cutFile2Dir(String srcFilePath, String destDir) {
        return cutFile2Dir(new File(srcFilePath), new File(destDir));
    }
    /**
     * 
     * @param srcFile 
     *      源文件路径
     * @param destDir     
     *      目标文件目录路径
     * @return 
     */
    public static boolean cutFile2Dir(File srcFile, String destDir) {
        return cutFile2Dir(srcFile, new File(destDir));
    }
    /**
     * 复制单个文件到指定路径
     * 
     * @param srcFile String 原文件 如：c:/fqf.txt
     * 
     * @param destDir String 复制后路径 如：f:/fqf.txt
     * 
     * @return boolean
     */
    public static boolean copyFile2Dir(File srcFile, File destDir) {
        
        if (!destDir.exists()) destDir.mkdirs();
        
        if(!destDir.isDirectory()) destDir.delete();
        
        File destFile = new File(destDir.getPath() + File.separator + srcFile.getName());
        
        return copyFile(srcFile, destFile);
    }
    
    /**
     * 
     * @param srcFilePath 源文件路径
     * @param destDir     目标文件目录路径
     * @return 
     */
    public static boolean copyFile2Dir(String srcFilePath, String destDir) {
        return copyFile2Dir(new File(srcFilePath), new File(destDir));
    }
    
    /**
     * 剪切文件列表到指定目录
     * @param srcFiles
     * @param destDir
     */
    public static void cutFiles2Folder(String[] srcFiles, String destDir) {
        for( String srcFile: srcFiles) {
            cutFile2Dir(srcFile, destDir);
        }
    }
    
    /**
     * 剪切文件列表到指定目录
     * @param srcFiles
     * @param destDir
     */
    public static void cutFiles2Folder(File[] srcFiles, String destDir) {
        cutFiles2Folder(srcFiles, new File(destDir));
    }
    
    /**
     * 剪切文件列表到指定目录
     * @param srcFiles
     * @param destDir
     */
    public static void cutFiles2Folder(File[] srcFiles, File destDir) {
        for (File srcFile: srcFiles) {
            cutFile2Dir(srcFile, destDir);
        }
    }
    /**
     * 剪切文件列表到指定目录
     * @param srcFiles
     * @param destDir
     */
    public static void cutFiles2Folder(List<File> srcFiles, File destDir) {
        srcFiles.stream().forEach((srcFile) -> {
            cutFile2Dir(srcFile, destDir);
        });
    }
    
    /**
     * 剪切文件列表到指定目录
     * @param srcFiles
     * @param destDir
     */
    public static void cutFiles2Folder(List<File> srcFiles, String destDir) {
        cutFiles2Folder(srcFiles, new File(destDir));
    }
    
    /**
     * 剪切文件列表到指定目录
     * @param srcFiles
     * @param destDir
     */
    public static void copyFiles2Folder(String[] srcFiles, String destDir) {
        for( String srcFile: srcFiles) {
            copyFile2Dir(srcFile, destDir);
        }
        
    }
    
    /**
     * 剪切文件列表到指定目录
     * @param srcFiles
     * @param destDir
     */
    public static void copyFiles2Folder(File[] srcFiles, String destDir) {
        copyFiles2Folder(srcFiles, new File(destDir));
    }
    
    /**
     * 剪切文件列表到指定目录
     * @param srcFiles
     * @param destDir
     */
    public static void copyFiles2Folder(File[] srcFiles, File destDir) {
        for (File srcFile: srcFiles) {
            copyFile2Dir(srcFile, destDir);
        }
    }
    /**
     * 剪切文件列表到指定目录
     * @param srcFiles
     * @param destDir
     */
    public static void copyFiles2Folder(List<File> srcFiles, File destDir) {
        srcFiles.stream().forEach((srcFile) -> {
            copyFile2Dir(srcFile, destDir);
        });
    }
    
    /**
     * 剪切文件列表到指定目录
     * @param srcFiles
     * @param destDir
     */
    public static void copyFiles2Folder(List<File> srcFiles, String destDir) {
        copyFiles2Folder(srcFiles, new File(destDir));
    }
    
    /**
     * 复制整个文件夹内容到目标文件夹
     * 
     * @param srcDir
     *            String 原文件路径 如：c:/fqf
     * @param destDir
     *            String 复制后路径 如：f:/fqf/ff
     */
    public static void copyFolder(File srcDir, File destDir) {
        try {
            if (!destDir.exists()) destDir.mkdirs(); // 如果文件夹不存在 则建立新文件夹
            
            File[] files = srcDir.listFiles();
            
            for (File file: files) {
                
                File destFile = new File(destDir.getAbsolutePath() + File.separator + file.getName());
                
                if (file.isFile()) {
                    copyFile(file, destFile);
                }
                
                if (file.isDirectory()) {// 如果是子文件夹
                    copyFolder(file, destFile);
                }
            }
        } catch (Exception e) {
            System.out.println("复制整个文件夹内容操作出错");
            log.error(e.getMessage());
        }
    }
    
    /**
     * 复制整个文件夹内容
     * 
     * @param srcDir
     *            File 原文件路径 如：c:/fqf
     * @param destDir
     *            File 复制后路径 如：f:/fqf/ff
     */
    public static void copyFolder(String srcDir, String destDir) {
        copyFolder(new File(srcDir), new File(destDir));
    }
    
    /**
     * 剪切的整个文件夹内容
     * 
     * @param srcDir
     *            File 原文件路径 如：c:/fqf
     * @param destDir
     *            File 复制后路径 如：f:/fqf/ff
     */
    public static void cutFolder(File srcDir, File destDir) {
        
        try {
            if (!srcDir.exists() ||  !srcDir.isDirectory()) throw new Exception("源文件目录不存在，或者源文件不是目录而是文件");
            //如果原路径和目标路径相同，则不进行任何操作
            if (srcDir.equals(destDir)) return;
            
            if (!destDir.exists()) destDir.mkdirs(); // 如果文件夹不存在 则建立新文件夹
            
            File[] files = srcDir.listFiles();
            
            for (File file: files) {
                
                if (file.isFile()) {
                    cutFile2Dir(file, destDir);
                }
                if (file.isDirectory()) {// 如果是子文件夹
                    cutFolder(file, new File(destDir + File.separator + file.getName()));
                    
                    file.delete();
                }
            }
            srcDir.delete();
        } catch (Exception e) {
            System.out.println("复制整个文件夹内容操作出错");
        }
    }
    /**
     * 复制整个文件夹目录结构
     * 
     * @param srcDir
     *            File 原文件路径 如：c:/fqf
     * @param destDir
     *            File 复制后路径 如：f:/fqf/ff
     */
    public static void copyDirStructure(File srcDir, File destDir) {
        
        try {
            if (!srcDir.exists() ||  !srcDir.isDirectory()) throw new Exception("源文件目录不存在，或者源文件不是目录而是文件");
            //如果原路径和目标路径相同，则不进行任何操作
            if (srcDir.equals(destDir)) return;
            
            if (!destDir.exists()) destDir.mkdirs(); // 如果文件夹不存在 则建立新文件夹
            
            File[] files = srcDir.listFiles();
            
            for (File file: files) {
                if (file.isDirectory()) {// 如果是子文件夹
                    copyDirStructure(file, new File(destDir + File.separator + file.getName()));
                }
            }
        } catch (Exception e) {
            System.out.println("复制整个文件夹内容操作出错");
        }
    }
    
    /**
     * 复制整个文件夹目录结构
     * 
     * @param srcDir
     *            File 原文件路径 如：c:/fqf
     * @param destDir
     *            File 复制后路径 如：f:/fqf/ff
     */
    public static void copyDirStructure(String srcDir, String destDir) {
        copyDirStructure(new File(srcDir), new File(destDir));
    }
    
    /**
     * 剪切的整个文件夹内容
     * 
     * @param srcDir
     *            String 原文件路径 如：c:/fqf
     * @param destDir
     *            String 复制后路径 如：f:/fqf/ff
     */
    public static void cutFolder(String srcDir, String destDir) {
        cutFolder(new File(srcDir), new File(destDir)); 
    }
    /** 
     * 删除目录（文件夹）以及目录下的文件 
     * @param   dirFile 被删除目录的文件路径 
     * @return  目录删除成功返回true，否则返回false 
     * @throws FileNotFoundException 
     */  
    public static List<File> deleteDir(File dirFile) throws FileNotFoundException {
        
        //如果dir对应的文件不存在，或者不是一个目录，则退出  
        if (dirFile==null || !dirFile.exists() || !dirFile.isDirectory()) {  
            throw new FileNotFoundException("file not exist or the file not Folder");
        }
        //存放被锁定的文件
        List<File> lockedFiles = new ArrayList<>();
        
        //删除文件夹下的所有文件(包括子目录)  
        File[] files = dirFile.listFiles();
        for (File childFile : files) {  
            //删除子文件  
            if (childFile.isFile()) {
                //判断文件是否被锁定
                if (FileUtils.isLocked(childFile)) {
                    lockedFiles.add(childFile);
                    continue; //结束本次循环，进行下一个循环
                }
                //删除文件
                childFile.delete();
            } else {  //删除子目录  
                List<File> childLockedFiles = deleteDir(childFile);
                lockedFiles.addAll(childLockedFiles);
            }  
        }
        //删除当前目录  
        if (lockedFiles.size() <= 0) {
            dirFile.delete();
        }
        return lockedFiles;
    }
    
    /**
     * 删除文件列表
     * 
     * @param files
     * @return
     * @throws FileNotFoundException
     */
    public static List<File> deleteFiles(List<File> files) throws FileNotFoundException {
        //存放被锁定的文件
        List<File> lockedFiles = new ArrayList<>();
        
        for (File file: files) {
            if (file.isFile()) {
                //判断文件是否被锁定
                if (isLocked(file)) {
                    lockedFiles.add(file);
                    continue; //结束本次循环，进行下一个循环
                }
                //删除文件
                file.delete();
            }
            if (file.isDirectory()) {
                List<File> lockeds = deleteFiles(file.listFiles());
                if (lockeds.size()>0) 
                //如果没有文件被锁定删除当前目录  
                if (lockeds.size() <= 0) {
                    file.delete();
                } else {
                    lockedFiles.addAll(lockeds);
                }
            }
        }
        return lockedFiles;
    }
    
    /**
     * 删除文件列表
     * 
     * @param files
     * @return
     * @throws FileNotFoundException
     */
    public static List<File> deleteFiles(File[] files) throws FileNotFoundException {
        //存放被锁定的文件
        List<File> lockedFiles = new ArrayList<>();
        if (files!=null && files.length>0) {
            lockedFiles = deleteFiles(Arrays.asList(files));
        }
        return lockedFiles;
    }
    
    /**
     * 判断文件是否被占用或者被锁定
     * @param file
     * @return
     */
    public static boolean isLocked(File file) {
        
        boolean isLocked = false;
        // 获取文件锁  
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            // 获取文件锁  
            FileChannel fc = raf.getChannel();
            FileLock fl = fc.tryLock();
            
            if (fl != null && fl.isValid()) {  
                // 释放文件锁  
                fl.release();
                return false;
            }
        } catch (FileNotFoundException e) {  
            System.out.println(e);
            isLocked = true;
        } catch (IOException | OverlappingFileLockException e) {
            isLocked = true;
        }
        return isLocked;
    }
    
    /**
     * 上传文件 
     * @param is 
     *      输入文件流
     * @param filePath
     *      输出文件路径
     * @return
     * @throws Exception
     */
    public static boolean uploadFile(InputStream is, String filePath) throws Exception {
        
        boolean retCode = false;
        byte[] buffer = new byte[1024];
        File parentDir = new File(filePath).getParentFile();
        
        if (!parentDir.exists()) parentDir.mkdirs();
        
        if (new File(filePath).exists() && !new File(filePath).isFile()) throw new Exception("file type is directory");
        
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            int n;
            while ((n = is.read(buffer, 0, buffer.length)) != -1) {
                fos.write(buffer, 0, n);
            }
            System.out.println("upload file success...");
            
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("upload file not found,upload fail:" + e);
        } catch (IOException e) {
            System.out.println("upload file not found,upload fail:" + e);
        }
        return retCode;
    }
    
    public static String getXmlContent(File xmlFile) {
        try {
            Document document = new SAXReader().read(xmlFile);
            return document.asXML();
        } catch (DocumentException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public static String getFileContent(String fileName) {
        
        StringBuilder fileContent = new StringBuilder();
        File f = new File(fileName);
        try (BufferedReader reader = new BufferedReader(new FileReader(f))){
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line).append("\n"); 
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return fileContent.toString();
    }
    
    public static String getFileContent(InputStream is) {
        StringBuilder fileContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line).append("\n"); 
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return fileContent.toString();
    }
    
    public static boolean setFileContent(String path, String content) {
        boolean flag = false;
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(path))) {
            if (content != null && content.length() >= 0) {
                byte abyte[] = content.getBytes();
                dos.write(abyte, 0, abyte.length);
                dos.flush();
                
                flag = true;
            }
        } catch (FileNotFoundException e) {
            log.error("fnfe:" + e);
        } catch (IOException e) {
            log.error("ioe:" + e);
        }
        return flag;
    }
    
    public static String file2Base64String(String filePath) {
        
        File file = new File(filePath);
        try (
            InputStream in = new FileInputStream(file);
            ByteArrayOutputStream out = new ByteArrayOutputStream(4096)
        ) {
            if (!file.exists() || !file.isFile()) return null;
            
            if (file.length() > (long)1024*1024*1024*2) throw new RuntimeException("文件太大，不能提供转换");
            
            byte[] b = new byte[4096];
            int n;
            while ((n = in.read(b)) != -1) {  
                out.write(b, 0, n);
            }
            return Base64.getEncoder().encodeToString(out.toByteArray());
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }
    /**
     * 指定文件路径的文件转换为字节流
     * @param path
     * @return
     */
    public static byte[] file2Bytes(String path) {
        return file2Bytes(new File(path));
    }
    
    /**
     * 对象转换为字节数组
     * @param obj
     * @return
     */
    public static byte[] object2Bytes(Object obj) {  
        byte[] bytes = null;
        
        try (
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);  
        ) {  
            oo.writeObject(obj);
            bytes = bo.toByteArray();
        } catch (Exception e) {  
            log.error("translation" + e.getMessage());
        }
        return bytes;
    }
    
    /**
     * 字节数组转换为对象
     * @param bytes 要转换的字节数组
     * @return
     */
    public static Object bytes2Object(byte[] bytes) {
        Object obj = null;
        try (
            ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
            ObjectInputStream oi = new ObjectInputStream(bi);
        ) {  
            obj = oi.readObject();
        } catch (IOException | ClassNotFoundException e) {
            log.error("translation" + e.getMessage());
        }
        return obj;
    }  
    /**
     * 文件转换为字节流
     * @param file
     * @return
     */
    public static byte[] file2Bytes(File file) {
        byte[] data = null;
        
        if (file.exists() && file.isFile()) {
            try (FileImageInputStream input = new FileImageInputStream(file);
                ByteArrayOutputStream output = new ByteArrayOutputStream();) {
                
                byte[] buf = new byte[1024];
                int numBytesRead;
                while ((numBytesRead = input.read(buf)) != -1) {
                    output.write(buf, 0, numBytesRead);
                }
                data = output.toByteArray();
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return data;
    }
    
    /**
     * 文件转换为字节流
     * @param file
     * @return
     */
    public static byte[] file2BytesX(File file) {
        byte[] data = null;
        
        if (file.exists() && file.isFile()) {
            try (FileChannel fc = new FileInputStream(file).getChannel()) {
                ByteBuffer buf = ByteBuffer.allocate((int) fc.size());
                fc.read(buf);
                return buf.array();
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return data;
    }
}
