package com.irongteng.thirdinterface;

import com.irongteng.control.CommonParameterControl;
import com.irongteng.control.DeviceCache;
import com.irongteng.service.CustomerAccountBean;
import com.irongteng.service.CustomerAccountService;
import com.irongteng.service.TranstFlowBean;
import com.irongteng.service.TranstFlowService;

import dwz.framework.spring.SpringContextHolder;
import dwz.framework.sys.exception.ServiceException;

/**
 * 账户计费
 * @author xing
 *
 */
public class AccountBilling {
    
    //提交查询数据成功后，扣除一条查询次数
    public static void deduckFromCache(String resq){
        HttpQueryInfo queryInfo = HttpCanstants.resqQueueMap.get(resq);
        if(queryInfo != null && queryInfo.session != null){
            DeviceCache dCache = new CommonParameterControl().getDeviceCacheByIoSession(queryInfo.session);
            if(dCache != null){
                dCache.thridBalance--;
            }
        }
    }
    
    //获取查询结果后，数据库扣除一条查询次数（帐号的查询次数以数据库的为准）
    public static void deduckFromDB(String resq){
        TranstFlowService flowService = SpringContextHolder.getBean(TranstFlowService.SERVICE_NAME);
        if(resq.startsWith("86")){
            resq = resq.substring(2);
        }
        TranstFlowBean flowBean = flowService.queryLastByImsiOrPhone(resq);
        if(flowBean != null){
            int deviceID = flowBean.getFromDeviceID();
            HttpQueryInfo queryInfo = HttpCanstants.resqQueueMap.get(resq);
            DeviceCache dCache = null;
            if(queryInfo != null && queryInfo.session != null){
                dCache = new CommonParameterControl().getDeviceCacheByIoSession(queryInfo.session);
            }
            System.out.println("deviceID == "+deviceID);
            CustomerAccountService accountService = SpringContextHolder.getBean(CustomerAccountService.SERVICE_NAME);
            if(dCache == null) return;
            CustomerAccountBean accountBean = accountService.queryByDeviceIDAndName(deviceID, dCache.accountName);
            if(accountBean != null){
                int thirdBalance = accountBean.getThirdBalance();
                accountBean.setThirdBalance(--thirdBalance);
                try {
                    System.out.println("第三方查询剩余次数是："+accountBean.getThirdBalance());
                    accountService.update(accountBean);
                } catch (ServiceException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

}
