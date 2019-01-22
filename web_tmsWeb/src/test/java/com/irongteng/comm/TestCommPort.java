package com.irongteng.comm;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.irongteng.conf.ModemInfo;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

/**
 * @author lvlei
 * 
 */
public class TestCommPort {
    private static CommPortIdentifier portId;
    private static Enumeration<CommPortIdentifier> portList;
    private static int bauds[] = { 9600, 19200, 57600, 115200 };

    /** 初始化串口信息*/
    @SuppressWarnings("unchecked")
    public void initCommPorts() throws InterruptedException {
        
        ExecutorService executor = null;
        try {
            portList = CommPortIdentifier.getPortIdentifiers();

            List<ModemInfo> modems = new ArrayList<ModemInfo>();
            ModemInfo modem = new ModemInfo("COM3", "siemens,115200,16385132,324235423,+8613800755500,true,10,false,7890");
            modem.setPortName("COM3");
            modem.setBaudRate(115200);
            modems.add(modem);
            
            ModemInfo modem1 = new ModemInfo("COM4", "siemens,115200,16385132,324235423,+8613800755500,true,10,false,7890");
            modem1.setPortName("COM4");
            modem1.setBaudRate(115200);
            modems.add(modem1);
            
            int threadNum = modems.size();
            // 初始化countDown
            final CountDownLatch threadSignal = new CountDownLatch(threadNum);
            // 创建固定长度的线程池
            executor = Executors.newFixedThreadPool(threadNum);
            
            for (int i = 0; i < threadNum; i++) { // 开threadNum个线程
                //Runnable task = new CommPortTestThread(threadSignal, modems.get(i));
                final ModemInfo mInfo = modems.get(i);
                // 执行
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(Thread.currentThread().getName() + "开始...");
                        // do shomething
                        System.out.println("开始了线程：：：：" + threadSignal.getCount());
                        // 线程结束时计数器减1
                        boolean state = isActiveCommPort(mInfo.getPortName(), mInfo.getBaudRate());
                        System.out.println(state);
                        threadSignal.countDown();
                        System.out.println(Thread.currentThread().getName() + "结束. 还有" + threadSignal.getCount() + " 个线程");
                    }
                });
            }
            
            threadSignal.await(45, TimeUnit.SECONDS); // 等待所有子线程执行完
            // do work
            System.out.println(Thread.currentThread().getName() + "+++++++结束.");
        } catch(Exception e) {
            System.out.println("执行异常:" + e.getMessage());
        } finally {
            if (executor != null) executor.shutdown();
        }
    }
    
    /**
     * 检测串口是否能够工作，如果能够工作，把该串口添加到串口池当中去
     * 
     * @author OMC
     *
     */
    protected class CommPortTestThread implements Runnable {
        private CountDownLatch threadsSignal;
        private ModemInfo modemInfo;

        public CommPortTestThread(CountDownLatch threadsSignal, ModemInfo modemInfo) {
            this.threadsSignal = threadsSignal;
            this.modemInfo = modemInfo;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "开始...");
            // do shomething
            System.out.println("开始了线程：：：：" + threadsSignal.getCount());
            // 线程结束时计数器减1
            boolean state = isActiveCommPort(modemInfo.getPortName(), modemInfo.getBaudRate());
            
            System.out.println(state);
            
            threadsSignal.countDown();
            System.out.println(Thread.currentThread().getName() + "结束. 还有" + threadsSignal.getCount() + " 个线程");
        }
    }
    
    /**
     * 
     * @param commPortName
     *            串口号
     * @param bound
     *            波特率
     * @return
     */
    public boolean isActiveCommPort(String commPortName, int bound) {
        
        System.out.println("GSM Modem 串行端口[" + commPortName + "]连接测试开始...");
        
        while (portList.hasMoreElements()) {
            
            portId = portList.nextElement();
            
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL
                    && commPortName.equalsIgnoreCase(portId.getName())) {
                System.out.println("找到串口: " + portId.getName());
                
                SerialPort serialPort = null;
                InputStream inStream = null ;
                OutputStream outStream = null;
                
                try {
                    int c;
                    StringBuffer response = new StringBuffer();
                    serialPort = (SerialPort) portId.open("SMSLibCommTester", 2000);
                    serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN);
                    serialPort.setSerialPortParams(bound, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
                            SerialPort.PARITY_NONE);
                    
                    inStream = serialPort.getInputStream();
                    outStream = serialPort.getOutputStream();
                    
                    serialPort.enableReceiveTimeout(1000);
                    c = inStream.read();
                    while (c != -1) {
                        c = inStream.read();
                    }
                    outStream.write('A');
                    outStream.write('T');
                    outStream.write('\r');
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }
                    
                    c = inStream.read();
                    while (c != -1) {
                        response.append((char) c);
                        c = inStream.read();
                    }

                    if (response.indexOf("OK") >= 0) {
                        System.out.print("  正在检测设备:");
                        try {
                            outStream.write('A');
                            outStream.write('T');
                            outStream.write('+');
                            outStream.write('C');
                            outStream.write('G');
                            outStream.write('M');
                            outStream.write('M');
                            outStream.write('\r');
                            response = new StringBuffer();
                            c = inStream.read();
                            while (c != -1) {
                                response.append((char) c);
                                c = inStream.read();
                            }
                            System.out.println("  发现设备: " + response.toString().replaceAll("(\\s+OK\\s+)|[\n\r]", ""));
                            return true;
                        } catch (Exception e) {
                            System.out.println("  检测设备失败,获取设备信息异常：" + e.getMessage());
                        }
                    } else {
                        System.out.println("  检测设备失败，沒有接收到响应结果!");
                    }

                } catch (Exception e) {
                    System.out.println("  检测设备失败，发生异常：" + e.getMessage());
                } finally {
                    try {
                        if (inStream != null ) inStream.close();
                        if (outStream != null ) outStream.close();
                        if (serialPort != null) serialPort.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return false;
    }
    
    public static void main(String[] args) {
        TestCommPort test = new TestCommPort();
        try {
            test.initCommPorts();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}