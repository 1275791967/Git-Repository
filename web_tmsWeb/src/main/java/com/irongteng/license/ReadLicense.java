package com.irongteng.license;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.Scanner;

import com.irongteng.conf.DirConfig;
import dwz.common.util.StringUtils;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReadLicense {

    private static boolean isVerify = false;
    private static String licenseFileName;
    private static final int NUMBER = 5;
    private static License licenseInfo = null;
    private static final Logger LOGGER = LoggerFactory.getLogger(ReadLicense.class);

    public static void loadLicense() {

        try {
            licenseFileName = URLDecoder.decode((new DirConfig().getHomeDir() + "/license/license.dat"), "UTF-8");
            if (new File(licenseFileName).exists()) {
                licenseInfo = unSerialize(licenseFileName);
            } else {
                licenseInfo = null; //重置fileInfo
                URL url = ReadLicense.class.getResource("/license/license.dat");
                if (url != null) {
                    licenseFileName = url.toURI().getPath();
                    licenseInfo = unSerialize(licenseFileName);
                } else {
                    throw new Exception("没有授权文件");
                }
            }
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }
    }

     /**
      * 读取C盘卷的序列号
      * 
      * @return 
      */
    private String readDiskSerial() {
        Process process;
        String serial = null;
        try {
            //process = Runtime.getRuntime().exec(new String[]{"wmic", "diskdrive", "get", "SerialNumber"});
            process = Runtime.getRuntime().exec("wmic volume where DriveLetter='c:' get SerialNumber");
            process.getOutputStream().close();
            //异常处理
            try (Scanner sc = new Scanner(process.getErrorStream())) {
                StringBuilder sb = new StringBuilder();
                while (sc.hasNextLine()) {
                    String tmp = sc.nextLine();
                    if (!"".equals(tmp)) sb.append(tmp).append(System.getProperty("line.separator"));
                }
                if (sb.length()>0) throw new IOException(sb.toString());
            }
            //信息处理
            try (Scanner sc = new Scanner(process.getInputStream())) {
                sc.next();  //跳过第一行
                if (sc.hasNext()) {
                    String volume = sc.next().trim();
                    if (!"".equals(volume) && StringUtils.isNumeric(volume)) serial = new StringBuffer(StringUtils.long2FixedHex(Long.parseLong(volume), 8).toUpperCase()).insert(4, "-").toString();
                }
            } //跳过第一行
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return serial;
    }

    /** 
     * 读取操作系统的产品编号ID
     * 
     * @return 
     */
    private String readProductID() {
        Process process;
        String serial = null;
        try {
            process = Runtime.getRuntime().exec(new String[]{"wmic", "OS", "get", "serialNumber"});
            process.getOutputStream().close();
            //异常处理
            try (Scanner sc = new Scanner(process.getErrorStream())) {
                StringBuilder sb = new StringBuilder();
                while (sc.hasNextLine()) {
                    String tmp = sc.nextLine();
                    if (!"".equals(tmp)) sb.append(tmp).append(System.getProperty("line.separator"));
                }
                if (sb.length()>0) throw new IOException(sb.toString());
            }
            //信息处理
            try (Scanner sc = new Scanner(process.getInputStream())) {
                sc.next();   //跳过第一行属性参数
                if (sc.hasNext()) {
                    String tmp = sc.next().trim();
                    if (!"".equals(serial)) serial = tmp.trim().toUpperCase();
                }
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return serial;
    }

    /**
     * 按照"XX-XX-XX-XX-XX-XX"格式，获取本机MAC地址
     *
     * @return
     * @throws Exception
     */
    private String readMacAddress() {
        try {
            String allMac = "";
            String mac;

            Process process = Runtime.getRuntime().exec("wmic nic where \"MACADDRESS is not null AND NOT PNPDeviceID LIKE 'ROOT%' AND PhysicalAdapter='true' AND NOT PNPDeviceID LIKE 'SWD%'\" get MACAddress");
            process.getOutputStream().close();
            //异常处理
            try (Scanner sc = new Scanner(process.getErrorStream())) {
                StringBuilder sb = new StringBuilder();
                while (sc.hasNextLine()) {
                    String tmp = sc.nextLine();
                    if (!"".equals(tmp)) sb.append(tmp).append(System.getProperty("line.separator"));
                }
                if (sb.length()>0) throw new IOException(sb.toString());
            }
            //信息处理
            try (Scanner sc = new Scanner(process.getInputStream())) {
                sc.next();  //跳过第一行属性参数
                while (sc.hasNext()) {
                    mac = sc.next().trim().toUpperCase().replaceAll(":", "-");
                    if (!"".equals(mac)) allMac += mac + "=";
                }
            }
            if (!"".equals(allMac)) return allMac.substring(0, allMac.lastIndexOf("="));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    /**
     * 判断是否是合法的MAC地址
     *
     * @param macAddress
     * @return
     * @throws Exception
     */
    /**
     * 判断是否是合法的MAC地址
     *
     * @param macAddress
     * @return
     * @throws Exception
     */
    private static boolean isValidMacAddress(String devMac, String licenseMac) {
        if (devMac==null || licenseMac==null) return false;
        List<String> strings = new ArrayList<>();
        if (devMac.contains("=")) {
            strings.addAll(Arrays.asList(devMac.split("=")));
        } else {
            strings.add(devMac);
        }
        for (String mac: strings) {
            if (mac != null && !"".equals(mac)) {
                mac = mac.trim().toUpperCase();
                if (licenseMac.toUpperCase().contains(mac)) return true;
            }
        }
        return false;
    }

    /**
     * 直接从主机读取硬件信息
     *
     * @return
     * @throws Exception
     */
    static License readLicenceInfo() throws Exception {
        ReadLicense info = new ReadLicense();
        License license = new License();
        String ds = info.readDiskSerial();
        String pid = info.readProductID();
        String mac = info.readMacAddress();
        
        if (ds != null) license.setDiskSerial(ds);
        if (pid != null) license.setProductID(pid);
        if (mac != null) license.setMacAddress(mac);
        return license;
    }

    // 把主机的硬件信息序列化到文件中去
    private static void serialize(License licence, String fileName) {
        try {
            serialize(licence, new FileOutputStream(fileName));
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage());
        }
    }

    // 把主机的硬件信息序列化到文件中去
    private static void serialize(License licence, OutputStream outStream) {
        try (ObjectOutputStream oos = new ObjectOutputStream(outStream)) {
            oos.writeObject(licence);
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    // 从序列化文件中读取主机硬件信息
    private static License unSerialize(String fileName) throws Exception {
        return unSerialize(new FileInputStream(fileName));
    }

    // 从序列化文件中读取主机硬件信息
    private static License unSerialize(InputStream inputStream) throws Exception {
        License licence = null;
        try (ObjectInputStream ois = new ObjectInputStream(inputStream)) {
            licence = (License) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.error(e.getMessage());
        }
        return licence;
    }

    public static int getNumber() {
        return NUMBER;
    }

    // 验证文件中的硬件信息是否和主机一致
    public static boolean verify() {
        try {
            loadLicense();
            //如果没有授权文件存在，验证没有授权
            if (licenseInfo == null) {
                isVerify = false;
            } else {
                //如果开关关闭，则默认检测日期是否过期
                if (!licenseInfo.isSwitch()) {
                    if (!licenseInfo.isExpriySwitch()) {
                        isVerify = true;
                    } else if (!licenseInfo.isOverTime()) {
                        isVerify = true;
                    }
                } else {
                    License info = readLicenceInfo();
                    String productID = info.getProductID();
                    String diskSerial = info.getDiskSerial();
                    String macAddress = info.getMacAddress(); 
                    
                    if (productID!=null && diskSerial!=null && macAddress!=null 
                            && productID.equalsIgnoreCase(licenseInfo.getProductID())
                            && diskSerial.equalsIgnoreCase(licenseInfo.getDiskSerial())
                            && isValidMacAddress(macAddress, licenseInfo.getMacAddress())) {
                        if (!licenseInfo.isExpriySwitch()) {
                            isVerify = true;
                        } else if (!licenseInfo.isOverTime()) {
                            isVerify = true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

        return isVerify;
    }

    public static boolean isVerify() {
        return isVerify;
    }

    /**
     * 判断是否过期
     *
     * @return
     */
    public static boolean isOverTime() {
        if (licenseInfo == null) {
            return true;
        }
        if (licenseInfo.isOverTime()) {
            if (licenseInfo.getOvertimeTag() == false) {
                licenseInfo.setOvertimeTag(true);
                serialize(licenseInfo, licenseFileName);
            }
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        ReadLicense rl = new ReadLicense();
        try {
            //String licenseName = URLDecoder.decode((new DirConfig().getHomeDir() + "/license/license.dat"), "UTF-8");
            //System.out.println(licenseName + " exist:" + new File(licenseName).exists());
            
            System.out.println(rl.readDiskSerial());
            System.out.println(rl.readMacAddress());
            System.out.println(rl.readProductID());
            System.out.println(ReadLicense.verify());
            //System.out.println(ReadLicense.isValidMacAddress("44-8A-5B-E5-07-BC="));

            //System.out.println(rl.readDiskSerial());
            //System.out.println(rl.readLicenceInfo());
            /*
            byte[] bytes = null;  
            try {  
                License license = rl.readLicenceInfo();
                // object to bytearray  
                ByteArrayOutputStream bo = new ByteArrayOutputStream();  
                ObjectOutputStream oo = new ObjectOutputStream(bo);  
                oo.writeObject(license);  

                bytes = bo.toByteArray();  

                bo.close();  
                oo.close();  
                System.out.println(StringUtils.bytes2HexString(bytes));

            } catch (Exception e) {  
                System.out.println("translation" + e.getMessage());  
                e.printStackTrace();  
            }  
            try {  
                ReadLicense.loadLicense();
                ByteArrayOutputStream bo = new ByteArrayOutputStream();  
                ObjectOutputStream oo = new ObjectOutputStream(bo);  
                oo.writeObject(fileInfo);  

                bytes = bo.toByteArray();  

                bo.close();  
                oo.close();  
                System.out.println(StringUtils.bytes2HexString(bytes));
            } catch (Exception e) {  
                System.out.println("translation" + e.getMessage());  
                e.printStackTrace();  
            }  
            System.out.println(ReadLicense.isValidMacAddress("00-50-56-C0-00-01=00-50-56-C0-00-08=54-EE-75-2F-E4-B0=30-10-B3-D2-61-57"));
             */
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
}
