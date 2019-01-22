package com.irongteng.control;

import java.util.List;

import com.irongteng.service.DeviceBean;
import com.irongteng.service.DeviceService;
import com.irongteng.service.TranstFlowBean;

import dwz.framework.spring.SpringContextHolder;

public class DealTranslateCancelThread extends Thread{
    private int index = 0;
    private int count = 0;
    private List<TranstFlowBean> beans = null;
    private DeviceService deviceService = null;
    
    
    public DealTranslateCancelThread(List<TranstFlowBean> beans){
        this.count = beans.size();
        this.beans = beans;
        this.deviceService = SpringContextHolder.getBean(DeviceService.SERVICE_NAME); 
    }
    
    @Override
    public void run() {
        // TODO Auto-generated method stub
        super.run();
        System.out.println(String.format("index is (%d) ..... count is(%d)", index, count));
        while(index < count){
            System.out.println(index);
            TranstFlowBean bean = beans.get(index++);
            DeviceBean dBean = deviceService.get(bean.getToDeviceID());
            System.out.println(dBean.toString());
        }
        
    }

}
