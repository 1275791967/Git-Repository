package com.irongteng.test;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.junit.Test;

public class MailManagerTest{
    private void printCpuPerc(CpuPerc cpu) {
        System.out.println("User :" + CpuPerc.format(cpu.getUser()));// 用户使用率
        System.out.println("Sys :" + CpuPerc.format(cpu.getSys()));// 系统使用率
        System.out.println("Wait :" + CpuPerc.format(cpu.getWait()));// 当前等待率
        System.out.println("Nice :" + CpuPerc.format(cpu.getNice()));//
        System.out.println("Idle :" + CpuPerc.format(cpu.getIdle()));// 当前空闲率
        System.out.println("Total :" + CpuPerc.format(cpu.getCombined()));// 总的使用率
    }
    
    @Test
    public void testCpuPerc() {
        Sigar sigar = new Sigar();
        // 方式一，主要是针对一块CPU的情况
        CpuPerc cpu;
        try {
            cpu = sigar.getCpuPerc();
            printCpuPerc(cpu);
        } catch (SigarException e) {
            e.printStackTrace();
        }
    } 
    
    @Test
    public void testFileSystemInfo() throws Exception {
        Sigar sigar = new Sigar();
        FileSystem fslist[] = sigar.getFileSystemList();
        String dir = System.getProperty("user.home");// 当前用户文件夹路径
        System.out.println("user.home:" + dir);
        for (int i = 0; i < fslist.length; i++) {
            System.out.println("/n~~~~~~~~~~" + i + "~~~~~~~~~~");
            FileSystem fs = fslist[i];
            // 分区的盘符名称
            System.out.println("fs.getDevName() = " + fs.getDevName());
            // 分区的盘符名称
            System.out.println("fs.getDirName() = " + fs.getDirName());
            System.out.println("fs.getFlags() = " + fs.getFlags());//
            // 文件系统类型，比如 FAT32、NTFS
            System.out.println("fs.getSysTypeName() = " + fs.getSysTypeName());
            // 文件系统类型名，比如本地硬盘、光驱、网络文件系统等
            System.out.println("fs.getTypeName() = " + fs.getTypeName());
            // 文件系统类型
            System.out.println("fs.getType() = " + fs.getType());

            FileSystemUsage usage = null;
            try {
                usage = sigar.getFileSystemUsage(fs.getDirName());
            } catch (SigarException e) {
                if (fs.getType() == 2)
                    throw e;
                continue;
            }
            switch (fs.getType()) {
            case 0: // TYPE_UNKNOWN ：未知
                break;
            case 1: // TYPE_NONE
                break;
            case 2: // TYPE_LOCAL_DISK : 本地硬盘
                // 文件系统总大小
                System.out.println(" Total = " + usage.getTotal() + "KB");
                // 文件系统剩余大小
                System.out.println(" Free = " + usage.getFree() + "KB");
                // 文件系统可用大小
                System.out.println(" Avail = " + usage.getAvail() + "KB");
                // 文件系统已经使用量
                System.out.println(" Used = " + usage.getUsed() + "KB");
                double usePercent = usage.getUsePercent() * 100D;
                // 文件系统资源的利用率
                System.out.println(" Usage = " + usePercent + "%");
                break;
            case 3:// TYPE_NETWORK ：网络
                break;
            case 4:// TYPE_RAM_DISK ：闪存
                break;
            case 5:// TYPE_CDROM ：光驱
                break;
            case 6:// TYPE_SWAP ：页面交换
                break;
            }
            System.out.println(" DiskReads = " + usage.getDiskReads());
            System.out.println(" DiskWrites = " + usage.getDiskWrites());
        }
        return;
    }
    @Test
    public void testGetOSInfo() {
          OperatingSystem os = OperatingSystem.getInstance();
          // 操作系统内核类型如： 386、486、586等x86
          System.out.println("OS.getArch() = " + os.getArch());
          System.out.println("OS.getCpuEndian() = " + os.getCpuEndian());//
          System.out.println("OS.getDataModel() = " + os.getDataModel());//
          // 系统描述
          System.out.println("OS.getDescription() = " + os.getDescription());
          System.out.println("OS.getMachine() = " + os.getMachine());//
          // 操作系统类型
          System.out.println("OS.getName() = " + os.getName());
          System.out.println("OS.getPatchLevel() = " + os.getPatchLevel());//
          // 操作系统的卖主
          System.out.println("OS.getVendor() = " + os.getVendor());
          // 卖主名称
          System.out.println("OS.getVendorCodeName() = " + os.getVendorCodeName());
          // 操作系统名称
          System.out.println("OS.getVendorName() = " + os.getVendorName());
          // 操作系统卖主类型
          System.out.println("OS.getVendorVersion() = " + os.getVendorVersion());
          // 操作系统的版本号
          System.out.println("OS.getVersion() = " + os.getVersion());
          
    }
}
