package dwz.common.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**Project: OMCServerCommunication
 * Package: com.omcserver.communication
 * Class name: CommLog.java
 * 功能说明: 通信日志类
 * Copyright (c) 2007-2009 winhap Systems, Inc. 
 * All rights reserved.
 * Created by: liuqing
 * Changed by: liuqing On: 2007-5-9  19:49:26
 */
public class CommUtils
{
    private static Timer deleteLogTimer = null;
    /**
     * 
     */
    public CommUtils() {
        
    }

    /**
     *  功能说明:记录通信日志
     *  @param log 
     *  return void
     *  author liuqing 2007-5-10 10:28:05
     */
    public static void writeCommLog(String log) {
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyyMMdd");
        String fileName = "log\\communication_" + format.format(new Date()) + ".log";
        File testFile = new File(fileName);
        if(testFile!=null&&testFile.length() > 500*1024*1024) { //异常日志达到500M则不再记录
            return;
        }
        writeLog(fileName, log);
    }

    /**
     * 记录上报北向的告警
     * @param log
     */
    public static void writeAlarmLog(String log) {
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyyMMdd");
        String fileName = "log\\alarm_" + format.format(new Date()) + ".log";
        File testFile = new File(fileName);
        if(testFile!=null&&testFile.length() > 500*1024*1024) { //异常日志达到500M则不再记录
            return;
        }
        writeLog(fileName, log);
    }
    
    /**
     * 将日志记录于文件中
     * @param fileName 文件名
     * @param log 要记录的日志
     */
    private static void writeLog(String fileName, String log) {
        if (fileName == null || fileName == "") {
            return;
        }
        try {
            int i = fileName.lastIndexOf("\\");
            if (i >= 0) {
                String dir = fileName.substring(0, i);
                File file = new File(dir);
                if (!file.exists()) {
                    file.mkdirs();
                }
            }
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
            out.println(format.format(new Date()) + "," + log);
            out.close();
        } catch (IOException ex) {

        }
    }
    
    /**
     *  功能说明:记录收发数据的日志
     *  @param log 
     *  return void
     *  author liuqing 2007-5-10 10:28:05
     */
    public static void writeDataLog(String log) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String fileName = "log\\data_" + format.format(new Date()) + ".log";
        File testFile = new File(fileName);
        if(testFile!=null&&testFile.length() > 1024*1024*1024) { //异常日志达到500M则不再记录
            return;
        }
        writeDataLog(fileName, log);
    }
    
    /**
     *  功能说明:记录测试的日志
     *  @param log 
     *  return void
     *  author liuqing 2007-5-10 10:28:05
     */
    public static void writeTestLog(String log) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String fileName = "log\\test_" + format.format(new Date()) + ".log";
        File testFile = new File(fileName);
        if(testFile!=null&&testFile.length() > 500*1024*1024) { //异常日志达到500M则不再记录
            return;
        }
        writeDataLog(fileName, log);
    }
    
    /**
     * 将日志记录于文件中
     * @param fileName 文件名
     * @param log 要记录的日志
     */
    private static void writeDataLog(String fileName, String log) {
        if (fileName == null || fileName == "") {
            return;
        }
        try {
            int i = fileName.lastIndexOf("\\");
            if (i >= 0) {
                String dir = fileName.substring(0, i);
                File file = new File(dir);
                if (!file.exists()) {
                    file.mkdirs();
                }
            }
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
            out.println(format.format(new Date()) + "," + log);
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void writeSerialPortLog(String log) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String fileName = "log\\modem_" + format.format(new Date()) + ".log";
        File testFile = new File(fileName);
        if(testFile!=null&&testFile.length() > 500*1024*1024)  { //异常日志达到500M则不再记录
            return;
        }
        writeUnicode(fileName, log);
    }

    /**
     * 将字符中的控制码（0x00－0x1F)用16进制码显示，其它字符不变
     * 
     * @param fileName
     *            文件名
     * @param log
     *            要记录的日志
     */
    private static void writeUnicode(String fileName, String log) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < log.length(); i++) {
            if (log.charAt(i) < (char) 0x20 && log.charAt(i) >= (char) 0x00) {
                sb.append("["+ Integer.toHexString(log.charAt(i)).toUpperCase() + "]");
            } else {
                sb.append(log.charAt(i));
            }
        }
        if (fileName == null || fileName == "") {
            return;
        }
        try {
            int i = fileName.lastIndexOf("\\");
            if (i >= 0) {
                String dir = fileName.substring(0, i);
                File file = new File(dir);
                if (!file.exists()) {
                    file.mkdirs();
                }
            }
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
            out.println(format.format(new Date()) + "," + sb.toString());
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     *  功能说明: 将字节数组转换成十六进制字符串
     *  @param data
     *  @return 
     *  return String
     *  author liuqing 2007-5-10 11:34:22
     */
    public static String BytesToHexStr(byte[] data) {
        if (data == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            String str = Integer.toHexString(data[i] & 0xFF);
            if (str.length() <= 1)
                str = "0" + str;
            sb.append(str + " ");
        }
        return sb.toString().toUpperCase();
    }

    public static byte[] HexStringTobytes(String hexStr) {
        byte[] bytes = new byte[hexStr.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            int temp = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 2), 16);
            bytes[i] = (byte) temp;
        }
        return bytes;
    }

    public static String BytesToHexStr2(byte[] data) {
        if (data == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            String str = Integer.toHexString(data[i] & 0xFF);
            if (str.length() <= 1)
                str = "0" + str;
            sb.append(" " + str);
        }
        return sb.toString().toUpperCase();
    }

    public static String BytesToUnicodeHexStr(byte[] data) {
        if (data == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            try {
                String str1 = Integer.toHexString(data[i] & 0xFF);
                if (str1.length() <= 1)
                    str1 = "0" + str1;
                sb.append(" " + str1);
                String str2 = Integer.toHexString(data[i + 1] & 0xFF);
                if (str2.length() <= 1)
                    str2 = "0" + str2;
                sb.append(str2);
                i++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sb.toString().toUpperCase();
    }

    public static String bytes2UnicodeSMSEncodeHexStr(byte[] data) {
        if (data == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            try {
                if (i < 71 || i >= data.length - 1) {
                    String str = Integer.toHexString(data[i] & 0xFF);
                    if (str.length() <= 1)
                        str = "0" + str;
                    sb.append(" " + str);
                } else {
                    String str1 = Integer.toHexString(data[i] & 0xFF);
                    if (str1.length() <= 1)
                        str1 = "0" + str1;
                    sb.append(" " + str1);
                    String str2 = Integer.toHexString(data[i + 1] & 0xFF);
                    if (str2.length() <= 1)
                        str2 = "0" + str2;
                    sb.append(str2);
                    i++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sb.toString().toUpperCase();
    }
    
    public static boolean isIncludeChineseChar(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isLetter(c) && Character.getType(c) == 5) {
                return true;
            }
        }
        return false;
    }
    
     /**
     * 记录异常日志
     * @param exeption
     */
    public static void writeExceptionLog(Exception exeption)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String fileName = "log\\exception_" + format.format(new Date()) + ".log";
        File testFile = new File(fileName);
        if(testFile!=null&&testFile.length() > 500*1024*1024) { //异常日志达到500M则不再记录
            return;
        }
        writeExceptionLog(fileName, exeption);
    }
    /**
     * 记录异常日志
     * @param exeption
     */
    private static void writeExceptionLog(String fileName, Exception exeption) {
        if (fileName == null || fileName == "") {
            return;
        }
        try {
            int i = fileName.lastIndexOf("\\");
            if (i >= 0) {
                String dir = fileName.substring(0, i);
                File file = new File(dir);
                if (!file.exists()) {
                    file.mkdirs();
                }
            }
            File exceptionFile = new File(fileName);
            if(exceptionFile!=null&&exceptionFile.length() > 500*1024*1024) { //异常日志达到500M则不再记录
                return;
            }
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
            java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
            out.print(format.format(new Date()) + ",");
            exeption.printStackTrace(out);
            out.close();
        } catch (IOException ex) {

        }
    }
    
    public void deleteLog() {
        if (deleteLogTimer != null) {
            try {
                deleteLogTimer.cancel();
            } catch (Exception e) {
                
            }
            deleteLogTimer = null;
        }
        deleteLogTimer = new Timer();
        deleteLogTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    deleteBeforeOneMonthLog();
                } catch (Exception e) {
                    CommUtils.writeExceptionLog(e);
                }
            }
        }, 60 * 1000, 24 * 60 * 60 * 1000);
    }
    
    private void deleteBeforeOneMonthLog() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        calendar.setTime(new Date());
        
        calendar.add(Calendar.DAY_OF_MONTH,-10);
        Date deleteDate = calendar.getTime();
        
        File file = new File("log");
        if (!file.exists()) {
            return;
        }
        File[] files = file.listFiles();
        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                try {
                    String fileName = files[i].getName();
                    int lastLineIndex = fileName.lastIndexOf("_");
                    int lastIndex = fileName.lastIndexOf(".");
                    
                    if (lastIndex - lastLineIndex == 9) { //假如在 "_" 和 "." 之间有9位，则为日志文件
                        
                        String time = fileName.substring(lastLineIndex + 1, lastIndex);
                        
                        Date date = sdf.parse(time);
                        
                        if (date.before(deleteDate)) {
                            deleteLog(fileName);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    CommUtils.writeExceptionLog(e);
                }
            }
        }
    }
    
    /**
     * 删除日志
     * 
     * @param fileName
     *            文件名
     */
    private void deleteLog(String fileName) {
        try {
            File file = new File("log\\" + fileName);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception ex) {
            CommUtils.writeExceptionLog(ex);
        }
    }
    
    public static void main(String[] args) {
//        CommLog.deleteBeforeOneMonthLog();
//        CommLog.writeOverTimeDataLog("天安直放站","111","255","13824396986");
//        CommLog.writeSerialPortLog("天安直放站");
//        CommLog.writeOverTimeDataLog("上沙直放站","222","0","13824396986");
//        CommLog.writeOverTimeDataLog("下沙直放站","333","255","13824396986");
        /*for(int i=0;i<1000000;i++) {
            CommLog.writeExceptionLog(new Exception());
            CommLog.writeTestLog("nihao");
        }*/
        String str = "58 F1 00 00 08 04 00 06 00 03 00 05 00 0B 68";
        byte[] data = CommUtils.HexStringTobytes(str.replace(" ", ""));
        for(int i=0;i<data.length;i++){
            System.out.println(data[i]);
        }
    }
}
