package com.irongteng.example;
import org.hyperic.sigar.Cpu;  
import org.hyperic.sigar.CpuPerc;  
import org.hyperic.sigar.Mem;  
import org.hyperic.sigar.Sigar;  
import org.hyperic.sigar.Swap;
  
public class SigarServlet{  
  
    public static void main(String[] args) throws Exception {  
          
        try {  
            Sigar sigar = new Sigar();  
            CpuPerc perc = sigar.getCpuPerc();
            
            Mem mem = sigar.getMem();  
            Swap swap = sigar.getSwap();  
              
            int cpuUsage = (int)perc.getCombined();  
            int memFree = (int)(mem.getFree()/(1024l*1024l));  
            int memTotal = (int)(mem.getTotal()/(1024l*1024l));  
              
            int pageFileFree = (int)(swap.getPageOut()/(1024l*1024l));  
            int pageFileTotal = (int)(swap.getPageIn()/(1024l*1024l));  
            int swapFree = (int)(swap.getFree()/(1024l*1024l));  
            int swapTotal = (int)(swap.getTotal()/(1024l*1024l));  

  
            Cpu cpu = sigar.getCpu();  
            
            System.out.println("CPU Usage："+perc.getCombined());  
            System.out.println("<br/>");  
            System.out.println("Memory Free："+memFree);  
            System.out.println("<br/>");  
            System.out.println("Memory Total："+memTotal);  
            System.out.println("<br/>");  
            System.out.println("Swap Free："+swapFree);  
            System.out.println("<br/>");  
            System.out.println("Swap Total："+swapTotal);  
            System.out.println("<br/>");  
            System.out.println("Page Free："+pageFileFree);  
            System.out.println("<br/>");  
            System.out.println("Page Total："+pageFileTotal);  
  
        } catch (Exception e) {  
              
        }  
    }  
  
}  