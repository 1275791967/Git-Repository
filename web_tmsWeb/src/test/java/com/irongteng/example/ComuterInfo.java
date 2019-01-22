package com.irongteng.example;
import org.hyperic.jni.ArchLoader;
import org.hyperic.sigar.CpuPerc; 
import org.hyperic.sigar.Sigar; 
import org.hyperic.sigar.SigarException; 
import org.hyperic.sigar.cmd.Shell; 
import org.hyperic.sigar.cmd.SigarCommandBase; 


public class ComuterInfo extends SigarCommandBase { 

    public boolean displayTimes = true; 

    public ComuterInfo(Shell shell) { 
        super(shell); 
    } 

    public ComuterInfo() { 
        super(); 
    } 

    @Override
    public String getUsageShort() { 
        return "Display cpu information"; 
    } 

    private void output(CpuPerc cpu) { 
        println("User Time....." + CpuPerc.format(cpu.getUser())); 
        println("Sys Time......" + CpuPerc.format(cpu.getSys())); 
        println("Idle Time....." + CpuPerc.format(cpu.getIdle())); 
        println("Wait Time....." + CpuPerc.format(cpu.getWait())); 
        println("Nice Time....." + CpuPerc.format(cpu.getNice())); 
        println("Combined......" + CpuPerc.format(cpu.getCombined())); 
        println("Irq Time......" + CpuPerc.format(cpu.getIrq())); 
        if (ArchLoader.IS_LINUX) { 
            println("SoftIrq Time.." + CpuPerc.format(cpu.getSoftIrq())); 
            println("Stolen Time...." + CpuPerc.format(cpu.getStolen())); 
        } 
        println(""); 
    } 

    @Override
    public void output(String[] args) throws SigarException { 
        StringBuffer sb = new StringBuffer();
        org.hyperic.sigar.CpuInfo[] infos = this.sigar.getCpuInfoList(); 
        
        CpuPerc[] cpus = this.sigar.getCpuPercList(); 

        org.hyperic.sigar.CpuInfo info = infos[0]; 
        long cacheSize = info.getCacheSize(); 
        println("Vendor........." + info.getVendor()); 
        println("Model.........." + info.getModel()); 
        println("Mhz............" + info.getMhz()); 
        println("Total CPUs....." + info.getTotalCores()); 
        if ((info.getTotalCores() != info.getTotalSockets()) || (info.getCoresPerSocket() > info.getTotalCores())) { 
            println("Physical CPUs.." + info.getTotalSockets()); 
            println("Cores per CPU.." + info.getCoresPerSocket()); 
        } 
        if (cacheSize != Sigar.FIELD_NOTIMPL) { 
            println("Cache size...." + cacheSize); 
        } 
        println(""); 
        if (!this.displayTimes) { 
            return; 
        } 
        /*
        for (int i = 0; i 0) 
            sb.append("第一个网卡号="+sigar.getNetInterfaceConfig(interfaces[0]).getHwaddr()); 
        */
        org.hyperic.sigar.FileSystem[] filesystems = sigar.getFileSystemList(); 
        if(filesystems!=null || filesystems.length>0) 
            sb.append("\n"+"硬盘第一个分区的卷标="+filesystems[1].getDevName()); 
       
        System.out.println(sb.toString()); 
    } 
    public static void main(String[] args) {
        
    }
}