package com.irongteng.comm.simulator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import dwz.common.util.FileUtils;
import dwz.common.util.StringUtils;
import dwz.common.util.time.DateUtils;

public class Simulator extends TimerTask  {
    
    private final SimulatorConfig mgrb;
    
    public static final String TMP_DEV_MAC = "fe2010042";
    public static final String TMP_USER_MAC = "e3:ab:ba:ef:";
    public static final String TNP_AP_MAC = ":3e:ef:ab:cd:";
    public static final String TMP_IMEI = "1234567";
    public static final String TMP_IMSI = "7654321";
    
    public static final String TMP_DEV_IP = "192.168.168.";
    public static final String TMP_AP_NAME = "testApName_";
    
    public Simulator(SimulatorConfig mgrb) {
        this.mgrb = mgrb;
    }
    
    /*
     * *
     * 复制单个文件
     * 
     * @param srcFile String 原文件路径 如：c:/fqf.txt
     * 
     * @param destFile String 复制后路径 如：f:/fqf.txt
     * 
     * @return boolean
     */
    public void copyFile(String srcFile, String destFile) {
        try {
            int byteread = 0;
            
            if (new File(srcFile).exists()) { // 文件存在时
                InputStream inStream = new FileInputStream(srcFile); // 读入原文件
                FileOutputStream fs = new FileOutputStream(destFile);
                byte[] buffer = new byte[1444];
                
                while ((byteread = inStream.read(buffer)) != -1) {                    
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
                fs.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
        }
    }
    
    /**
     * 复制整个文件夹内容
     * 
     * @param srcDir
     *            String 原文件路径 如：c:/fqf
     * @param destDir
     *            String 复制后路径 如：f:/fqf/ff
     */
    public void copyFolder(String srcDir, String destDir) {

        try {
            (new File(destDir)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
            File a = new File(srcDir);
            String[] file = a.list();
            File temp;
            for (String file1 : file) {
                if (srcDir.endsWith(File.separator)) {
                    temp = new File(srcDir + file1);
                } else {
                    temp = new File(srcDir + File.separator + file1);
                }
                if (temp.isFile()) {
                    try (FileInputStream input = new FileInputStream(temp); 
                            FileOutputStream output = new FileOutputStream(destDir  + "/" + (temp.getName()))) {
                        byte[] b = new byte[1024 * 5];
                        int len;
                        while ((len = input.read(b)) != -1) {
                            output.write(b, 0, len);
                        }
                        output.flush();
                    }
                }
                if (temp.isDirectory()) {
                    // 如果是子文件夹
                    copyFolder(srcDir + "/" + file1, destDir + "/" + file1);
                }
            }
        } catch (Exception e) {
            System.out.println("复制整个文件夹内容操作出错");
        }
    }
    
    private String getAglContentFile() {
        StringBuilder fileContent = new StringBuilder();
        StringBuffer sb = new StringBuffer();
        int delayMin = 0;
        for(int i=0;i <mgrb.getDeviceCount(); i++)  {
            fileContent.delete(0, fileContent.length());
            
            for(int j=0; j<mgrb.getFileTextLineCount(); j++) {
                sb.delete(0, sb.length());
                
                sb.append(TMP_DEV_MAC).append(int2FixedHex(i, 5)).append("][");       //设备MAC
                sb.append(TMP_USER_MAC).append(int2FixedHex(j/256, 2)).append(":").append(int2FixedHex(j%256, 2)).append("]["); //用户MAC
                sb.append("4").append(j).append("][");           //连接次数
                sb.append(DateUtils.formatStandardDate(delayMinute(delayMin))).append("][");  //记录时间
                switch (i%3) {
                    case 0:
                        sb.append(" User-Agent: Dalvik/1.6.0 (Linux; U; Android 4.4.2; HUAWEI MT2-C00 Build/HuaweiMT2-C00)][ ][ "); //监控的程序信息
                        break;
                    case 1:
                        sb.append(" ][793788").append(i).append("][ ");  //qq的程序信息
                        break;
                    case 2:
                        sb.append(" ][ ][ weixinhao").append(i);  //监控的程序信息
                        break;
                    default:
                        break;
                }
                sb.append("\n");
                fileContent.append(sb);
            }
            delayMin ++;
            String deviceName = "test" + formatString(String.valueOf(i), String.valueOf(200).length(),'0') + "[" + TMP_DEV_IP + (i+1) + "]";
            
            this.writeFile(fileContent.toString(), deviceName, "agl");
        }
        return sb.toString();
    }
    
    private void getAplContentFile() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();//日历对象
        cal.setTime(date);//设置当前日期
        cal.add(Calendar.MINUTE, 2);//延后2分钟
        
        StringBuilder fileContent = new StringBuilder();
        StringBuffer sb = new StringBuffer();
        
        for(int i=0;i <mgrb.getDeviceCount(); i++)  {
            
            fileContent.delete(0, fileContent.length());
            
            for(int j=0; j<mgrb.getFileTextLineCount(); j++) {
                sb.delete(0, sb.length());
                
                sb.append(TMP_DEV_MAC).append(int2FixedHex(i, 5)).append(",");        //监控设备MAC
                sb.append("   ").append(int2FixedHex(i, 2)).append(TNP_AP_MAC).append(int2FixedHex(i, 2)).append(",");      //AP MAC
                sb.append("    ").append(TMP_AP_NAME).append(i).append(",");   //AP Name
                sb.append("    ").append(j).append(",");               //信道
                sb.append("    -").append(j).append(",");              //信号强度
                sb.append("    2").append(formatString(i+"", 3, '0')).append(",");  //连接次数
                sb.append("    ").append(DateUtils.formatStandardDate(new Date())).append(",");  //开始时间
                sb.append("    ").append(DateUtils.formatStandardDate(cal.getTime())).append(","); //结束时间
                sb.append("    WEP|WPA|WPA2|WPS");  //加密方式
                sb.append("\n");
                
                fileContent.append(sb);
            }
            
            String deviceName = "test" + formatString(String.valueOf(i), String.valueOf(200).length(),'0') + "[" + TMP_DEV_IP + (i+1) + "]";
            
            this.writeFile(fileContent.toString(), deviceName, "apl");
        }
    }

    private void getHplContentFile() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();//日历对象
        cal.setTime(date);//设置当前日期
        cal.add(Calendar.MINUTE, 2);//延后2分钟
        
        StringBuilder fileContent = new StringBuilder();
        StringBuffer sb = new StringBuffer();
        
        int delayMin = 0;
        for(int i=0;i <mgrb.getDeviceCount(); i++)  {
            
            fileContent.delete(0, fileContent.length());
            
            for(int j=0; j<mgrb.getFileTextLineCount(); j++) {
                sb.delete(0, sb.length());
                
                sb.append("1011").append(StringUtils.int2FixedDec(i, 5)).append(",");        //监控设备MAC
                sb.append("   ").append("HplName").append(StringUtils.int2FixedDec(i, 5)).append(",");  //监控设备MAC
                sb.append("   ").append(TMP_IMEI).append(StringUtils.int2FixedDec(j, 5)).append(",");    //用户IMEI
                sb.append("   ").append(TMP_IMSI).append(StringUtils.int2FixedHex(j, 5)).append(",");    //用户IMSI
                sb.append("    ").append(DateUtils.formatHpDate(delayMinute(delayMin)));  //开始时间
                sb.append("\n");
                
                fileContent.append(sb);
            }
            delayMin++;
            String deviceName = "WHotpoint_" + "1011" + StringUtils.int2FixedDec(i, 5) + "_" + "HplName" + StringUtils.int2FixedDec(i, 5) + "_2_" + DateUtils.formatHpDate(new Date());
            
            this.writeHplFile(fileContent.toString(), deviceName, "hp");
        }
    }
    
    private void getPnlContentFile() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();//日历对象
        cal.setTime(date);//设置当前日期
        cal.add(Calendar.MINUTE, 2);//月份减一
        
        StringBuilder fileContent = new StringBuilder();
        StringBuffer sb = new StringBuffer();
        
        for(int i=0;i <mgrb.getDeviceCount(); i++)  {
            
            fileContent.delete(0, fileContent.length());
            
            for(int j=0; j<mgrb.getFileTextLineCount(); j++) {
                sb.delete(0, sb.length());
                
                sb.append(TMP_DEV_MAC).append(int2FixedHex(i, 5)).append(",");        //监控设备MAC
                
                sb.append("   ").append(TMP_USER_MAC).append(int2FixedHex(j/256, 2)).append(":").append(int2FixedHex(j%256, 2)).append(","); //用户MAC
                sb.append("   ").append(int2FixedHex(i, 2)).append(TNP_AP_MAC).append(int2FixedHex(i, 2)).append(",");   //AP MAC
                sb.append("    ").append(TMP_AP_NAME).append(i).append(",");    //AP Name
                sb.append("    ").append(j+8).append(",");           //连接次数
                sb.append("    ").append(DateUtils.formatStandardDate(new Date()));  //上报时间
                sb.append("\n");
                //System.out.print(sb.toString());
                fileContent.append(sb);
            }
            
            String deviceName = "test" + formatString(String.valueOf(i), String.valueOf(200).length(),'0') + "[" + TMP_DEV_IP + (i+1) + "]";
            
            this.writeFile(fileContent.toString(), deviceName, "pnl");
        }
    }
    
    private void getLogContentFile() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();//日历对象
        cal.setTime(date);//设置当前日期
        cal.add(Calendar.MINUTE, 60);//延后60分钟
        
        StringBuilder fileContent = new StringBuilder();
        StringBuffer sb = new StringBuffer();
        
        for(int i=0;i <mgrb.getDeviceCount(); i++)  {
            
            fileContent.delete(0, fileContent.length());
            
            for(int j=0; j<mgrb.getFileTextLineCount(); j++) {
                sb.delete(0, sb.length());
                
                sb.append(TMP_DEV_MAC).append(int2FixedHex(i, 5)).append(",");          //监控设备MAC
                sb.append("   ").append(TMP_USER_MAC).append(int2FixedHex(j/256, 2)).append(":").append(int2FixedHex(j%256, 2)).append(","); //用户MAC
                sb.append("   ").append(int2FixedHex(i, 2)).append(TNP_AP_MAC).append(int2FixedHex(i, 2)).append(",");   //AP MAC
                sb.append("    ").append(TMP_AP_NAME).append(i).append(",");      //AP name
                sb.append("    -").append(j).append(",");               //信号强度
                sb.append("    0048,");                      //PK类型
                sb.append("    ").append(j).append(",");                  //连接次数
                sb.append("    ").append(DateUtils.formatStandardDate(new Date())).append(",");     //开始时间
                sb.append("    ").append(DateUtils.formatStandardDate(cal.getTime())).append(",");  //结束时间
                sb.append("    ").append(0.95).append(",");                                    //持续时长（分钟）
                sb.append("    192.168.168.").append((i+1)).append(",");                      //源地址
                sb.append("    202.96.133.").append((i+1)).append(",");                       //目标地址
                sb.append("    ").append((i + 1000)).append(",");                             //源端口
                sb.append("    ").append((i + 1100));                                   //目标端口
                sb.append("\n");
                //System.out.print(sb.toString());
                fileContent.append(sb);
            }
            
            String deviceName = "test" + formatString(String.valueOf(i), String.valueOf(200).length(),'0') + "[" + TMP_DEV_IP + (i+1) + "]";
            
            this.writeFile(fileContent.toString(), deviceName, "log");
        }
    }

    private void getHtpContentFile() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();//日历对象
        cal.setTime(date);//设置当前日期
        cal.add(Calendar.MINUTE, 60);//延后50分钟
        
        StringBuilder fileContent = new StringBuilder();
        StringBuffer sb = new StringBuffer();
        
        for(int i=0;i <mgrb.getDeviceCount(); i++)  {
            
            fileContent.delete(0, fileContent.length());
            
            for(int j=0; j<mgrb.getFileTextLineCount(); j++) {
                sb.delete(0, sb.length());
                
                sb.append(DateUtils.formatStandardDate(new Date())).append(",");                          //记录时间
                sb.append("   ").append(int2FixedHex(i, 3)).append("3").append(int2FixedHex(i, 3));             //含义不知
                sb.append("   ").append(int2FixedHex(i, 2)).append(TMP_USER_MAC).append(int2FixedHex(j, 2)).append(","); //用户MAC
                sb.append("   c0:a0:bb:27:6a:").append( int2FixedHex(i, 2) ).append(",");                     //路由MAC
                sb.append("   192.168.168.").append(i).append(",");   //源IP
                sb.append("   121.15.253.").append(i).append(",");    //目标IP
                sb.append("    http://www.baidu.com/books?page=").append(i);  //访问网页地址
                sb.append("\n");
                //System.out.print(sb.toString());
                fileContent.append(sb);
            }
            
            String deviceName = "test" + formatString(String.valueOf(i), String.valueOf(200).length(),'0') + "[" + TMP_DEV_IP + (i+1) + "]";
            
            this.writeFile(fileContent.toString(), deviceName, "htp");
        }
    }
    
    public static Date delayMinute(int min) {
        Calendar cal = Calendar.getInstance();//日历对象
        cal.add(Calendar.MINUTE, min);//延后50分钟
        return cal.getTime();
    }

    public static Date delaySecond(int sec) {
        Calendar cal = Calendar.getInstance();//日历对象
        cal.add(Calendar.SECOND, sec);//延后sec秒
        return cal.getTime();
    }
    
    public static String formatString(String str, int len, char ch) {
        return StringUtils.formatFixedString(str, len, ch);
    }
    
    private static String int2FixedHex(int num, int len) {
        return StringUtils.formatFixedString(Integer.toHexString(num), len, '0');
    }
    
    public void writeFile(String fileContent, String fileNameNoSuffix, String suffix) {
        String fileAbsolutePath = System.getProperty("user.dir") + File.separator + "logs" + File.separator + fileNameNoSuffix + "_" + DateUtils.formatFileDate(new Date()) + "." + suffix;
        File file = new File(fileAbsolutePath); // 找到File类的实例
        
        try {
            // 创建文件
            file.createNewFile();
                    // 写入数据
                    try ( // 通过子类实例化，表示可以追加
                        Writer out = new FileWriter(file, true)) {
                        // 写入数据
                        out.write(fileContent);
                    }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeHplFile(String fileContent, String fileNameNoSuffix, String suffix) {
        String fileAbsolutePath = System.getProperty("user.dir") + File.separator + "logs" + File.separator + fileNameNoSuffix + "." + suffix;
        File file = new File(fileAbsolutePath); // 找到File类的实例
        
        try {
            // 创建文件
            file.createNewFile();
            // 声明字符输出流
            Writer out = new FileWriter(file, true);;
            // 通过子类实例化，表示可以追加
            
            // 写入数据
            out.write(fileContent);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void cutFile2FtpDir() {
        File srcDir = new File(System.getProperty("user.dir") + File.separator + "logs");
        
        File[] files = srcDir.listFiles();
        
        for (File file1 : files) {
            if (file1.isFile()) {
                File file = file1;
                FileUtils.copyFile(file.getAbsolutePath(), mgrb.getFtpDir() + File.separator + file.getName());
                file.delete();
            }
        }
    }
    
    public void copyFile2FtpDir() {
        File srcDir = new File(System.getProperty("user.dir") + File.separator + "logs");
        
        File[] files = srcDir.listFiles();
        
        for (File file1 : files) {
            if (!file1.isDirectory()) {
                File file = file1;
                FileUtils.copyFile(file.getAbsolutePath(), mgrb.getFtpDir() + File.separator + file.getName());
            }
        }
    }
    
    public void copyDir2FtpDir() {
        File srcDir = new File(System.getProperty("user.dir") + File.separator + "logs");
        this.copyFolder(srcDir.getAbsolutePath(), mgrb.getFtpDir());
    }
    
    @Override
    public void run() {
        if (mgrb.isAplSwitch()) {
            this.getAplContentFile();
        }
        if(mgrb.isAglSwitch()) {
            this.getAglContentFile();
        }
        if (mgrb.isLogSwitch()) {
            this.getLogContentFile();
        }
        if (mgrb.isHtpSwitch()) {
            this.getHtpContentFile();
        }
        if (mgrb.isPnlSwitch()) {
            this.getPnlContentFile();
        }
        if (mgrb.isHplSwitch()) {
            this.getHplContentFile();
        }
        
        this.cutFile2FtpDir();
    }
    
    public static void main(String []args) {
        SimulatorConfig mgrb = new SimulatorConfig();
        Simulator mgr = new Simulator(mgrb);
        new Timer().schedule(mgr, 1000, 1000 * mgrb.getTaskInterval());
        //mgr.getHplContentFile();
        System.out.println("文件生成模拟程序正在运行。。。");
        //System.out.println(StringUtils.standardDate(FileMgr.delayMinute(5)));
    }
    
}
