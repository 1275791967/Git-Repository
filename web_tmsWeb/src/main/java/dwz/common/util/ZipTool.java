package dwz.common.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 压缩实现类 <br>
 * 主要实现: <br>
 * <p>
 * 压缩单个文件、
 * <p>
 * 压缩文件夹下的所有文件及子文件夹
 * 
 * <p>
 * 
 * @author lvlei
 * 
 */

public class ZipTool {
    
    private static final int BLOCK_64K = 64 * 1024;

    /**
     * 压缩单个文件
     * 
     * @param srcDir
     *            文件路径
     * @param srcName
     *            文件名字
     * @param destDir
     *            压缩文件目标文件夹
     * @param destName
     *            压缩文件名字
     * @return
     * @throws IOException
     */
    public boolean zip(String srcDir, String srcName, String destDir, String destName) throws IOException {
        
        boolean tag = false;
        
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(destDir + destName));

        FileInputStream fis = new FileInputStream(srcDir + srcName);
        byte[] buffer = new byte[BLOCK_64K];
        int i = 0;
        
        zos.putNextEntry(new ZipEntry(srcName));
        while ((i = (fis.read(buffer))) > 0) {
            zos.write(buffer, 0, i);
        }
        
        fis.close();
        zos.close();

        return tag;
    }
    
    public boolean zip(File srcFile, File destZipFile) throws IOException {
        
        boolean tag = false;
        
        FileInputStream fis = new FileInputStream(srcFile);
        
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(destZipFile));
        
        byte[] buffer = new byte[BLOCK_64K];
        int len = 0;
        
        zos.putNextEntry(new ZipEntry(srcFile.getName()));
        
        while ((len = (fis.read(buffer))) > 0) {
            zos.write(buffer, 0, len);
        }
        
        fis.close();
        zos.close();

        return tag;
    }
    /**
     * 压缩一个文件夹下的所有文件 注意中间路径连接用"/" 如：c:/tt/ttt
     * 
     * @param srcDir
     *            要压缩的文件夹路径
     * @param destZipDir
     *            目标文件夹路径
     * @param destZipName
     *            目标压缩文件名字
     * @return
     */
    public boolean zipDir(String srcDir, String destZipDir, String destZipName) {

        ZipOutputStream zos = null;

        try {
            
            File destFile = new File(destZipDir, destZipName);
            zos = new ZipOutputStream(new BufferedOutputStream( new FileOutputStream(destFile)));

            if (srcDir == null) {
                System.out.println("传入的源文件夹路径字符串不能为空！");
                throw new NullPointerException();
            }
            String dirName = "";
            File file = new File(srcDir);

            if (!file.isDirectory()) {
                throw new Exception("传入了不正确的源文件夹路径！");
            } else {
                dirName = srcDir.substring(srcDir.lastIndexOf("/") + 1);
                if (dirName == null || "".equals(dirName)) {
                    String subStr = srcDir.substring(0, srcDir.length() - 2);
                    dirName = subStr.substring(subStr.lastIndexOf("/") + 1);
                }
                System.out.println(dirName);
                ZipEntry ze = new ZipEntry(dirName + "/");
                zos.putNextEntry(ze);
                if (dirName == null || "".equals(dirName)) {
                    throw new Exception("传入了不正确的源文件夹路径！");
                }
            }

            File[] files = file.listFiles();

            for (int i = 0; i < files.length; i++) {
                zipFile(dirName + "/", files[i], zos);
            }
            return true;
        } catch (Exception e) {
            System.out.println("压缩文件时出现异常！");
            e.printStackTrace();
            return false;
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
    /**
     * 压缩一个文件夹下的所有文件 注意中间路径连接用"/" 如：c:/tt/ttt
     * 
     * @param srcDir
     *            要压缩的文件夹路径
     * @param destZipDir
     *            目标文件夹路径
     * @param destZipName
     *            目标压缩文件名字
     * @return
     */
    public boolean zipDir(File srcDir, File targetFile) {

        ZipOutputStream zos = null;

        try {
            zos = new ZipOutputStream(new BufferedOutputStream( new FileOutputStream(targetFile)));

            if (srcDir == null) {
                System.out.println("传递的要压缩的文件不能为空！");
                throw new NullPointerException("传递的要压缩的文件不能为空！");
            }
            
            if (!srcDir.isDirectory())  throw new Exception("传入了不正确的源文件夹路径！");
            
            String zipDir = srcDir.getName() + "/";
            
            ZipEntry ze = new ZipEntry(zipDir);
            zos.putNextEntry(ze);
            
            File[] files = srcDir.listFiles();
            
            for (File file : files) {
                try {
                    zipFile(zipDir, file, zos);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
            
            return true;
        } catch (Exception e) {
            System.out.println("compress file error！");
            e.printStackTrace();
            return false;
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
    
    /**
     * 用来压缩文件夹下的所有子文件 此方法为一个递归调法方法
     * 
     * @param srcDir
     *            要压缩的文件在压缩包中的相对路径
     * @param srcFile
     *            　要压缩的文件引用
     * @param zos
     *            　　 压缩文件输出流
     * @throws IOException
     */
    private void zipFile(String srcDir, File srcFile, ZipOutputStream zos)
            throws IOException {
        
        // 是文件夹的操作
        if (srcFile.isDirectory()) {
            String zipDir = srcDir + srcFile.getName() + "/";
            ZipEntry ze = new ZipEntry(zipDir);
            zos.putNextEntry(ze);
            // 递归调用
            File[] files = srcFile.listFiles();
            for (File file: files) {
                try {
                    zipFile(zipDir, file, zos);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
            // 是文件时的操作
        } else {
            FileInputStream fis = null;
            
            try {
                fis = new FileInputStream(srcFile);
                ZipEntry ze = new ZipEntry(srcDir + srcFile.getName());
                zos.putNextEntry(ze);
                byte[] b = new byte[4096];
                int i = 0;
                while ((i = (fis.read(b))) > 0) {
                    zos.write(b, 0, i);
                }
            } finally {
                if (fis != null) {
                    fis.close();
                }
            }
        }
    }
    
    public static void main(String[] args) {
        /*
        long time = System.currentTimeMillis();
        ZipTool zt = new ZipTool();
        try {
            zt.zipDir("C:/logsFile", "c:/", "test2.zip");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("runtime: " + (System.currentTimeMillis() - time)
                / 1000 + " s");
        */
        

        long time = System.currentTimeMillis();
        ZipTool zt = new ZipTool();
        try {
            //zt.zipDir("D:/app_tmp", "d:/", "app_tmp2.zip");
            zt.zipDir(new File("D:/app_tmp"), new File("d:/app_tmp3.zip"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("runtime: " + (System.currentTimeMillis() - time)
                / 1000 + " s");
    }

}