package com.irongteng.persistence.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.irongteng.persistence.BaseConditionVO;
import com.irongteng.persistence.beans.ImsiPhone;
import com.irongteng.service.ImsiPhoneBean;
import com.irongteng.service.ImsiPhoneService;

import dwz.framework.junit.BaseJunitCase;
import dwz.framework.spring.SpringContextHolder;
import dwz.framework.sys.exception.ServiceException;
@Rollback(false)
public class ImsiCallTest  extends BaseJunitCase{
    private  ImsiPhoneService msiCallService = SpringContextHolder.getBean(ImsiPhoneService.SERVICE_NAME);

    
    @Test 
    public void addTest() {
        ImsiPhoneBean imsiCallBean = new ImsiPhoneBean();
        
        ImsiPhone imsiCall = imsiCallBean.getImsiPhone();
        imsiCall.setAddress("FsSDAdffF");
        imsiCall.setCity("rDDffd");
        imsiCall.setName("好HAOHff");
        imsiCall.setImei("12144131");
        imsiCall.setOwnID("1644");
        imsiCall.setPhone("4514454");
        imsiCall.setRemark("j份饭");
        imsiCall.setImsi("317448");
        imsiCall.setImsi_MSISDN("刚刚好e");
        imsiCall.setProvince("9155");
        imsiCall.setRemark("ZAgI");
        imsiCall.setRecordTime(new Date());
        try {
            msiCallService.add(imsiCallBean);
        } catch (ServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    @Test
    public void testSearch() {
        BaseConditionVO vo = new BaseConditionVO();
        ImsiPhoneBean imsiPhoneBean = msiCallService.get(1436);
        System.out.println(imsiPhoneBean.toString());
        List<ImsiPhoneBean> beans = msiCallService.search(vo);
        System.out.println("size:" + beans.size());
        beans.forEach(bean->System.out.println(bean));
    }
    @Test 
    public void addBatch() {
        ImsiPhoneBean imsiCallBean = new ImsiPhoneBean();
        
        ImsiPhone imsiCall = imsiCallBean.getImsiPhone();
        imsiCall.setCity(null);
        imsiCall.setImsi("4600130");
        imsiCall.setPhone("17603");
        imsiCall.setRecordTime(null);
        imsiCall.setImei("12132");
        imsiCall.setMac("12435");
        imsiCall.setImsi_MSISDN("1234");
        
        ImsiPhone imsiCall1 = imsiCallBean.getImsiPhone();
        imsiCall1.setCity(null);
        imsiCall1.setImsi("4600130001433");
        imsiCall1.setPhone("17603006424");
        imsiCall1.setRecordTime(null);
        imsiCall1.setImei("12132242");
        imsiCall1.setMac("12435442");
        imsiCall1.setImsi_MSISDN("12345642");
        ArrayList<ImsiPhoneBean> arrayList = new ArrayList<>();
        arrayList.add(imsiCallBean);
        try {
            msiCallService.addBatch(arrayList);
        } catch (ServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @Test 
    public void addOrUpdate() {
        ImsiPhoneBean imsiCallBean = new ImsiPhoneBean();
        ImsiPhone imsiCall = imsiCallBean.getImsiPhone();
        imsiCall.setCity(null);
        imsiCall.setImsi("460453");
        imsiCall.setPhone("176");
        imsiCall.setRecordTime(null);
        imsiCall.setImei("123333333");
        imsiCall.setMac("13332222");
        imsiCall.setImsi_MSISDN("12421");
        
        msiCallService.addOrUpdate(imsiCallBean);
    }
    @Test 
    public void update() {
        ImsiPhoneBean imsiCallBean = new ImsiPhoneBean();
        
        ImsiPhone imsiCall = imsiCallBean.getImsiPhone();
        imsiCall.setId(4575);
        imsiCall.setImsi("46001212221");
        imsiCall.setPhone("132432331234");
        imsiCall.setImei("1234567333");
        imsiCall.setMac("3245343244441");
        imsiCall.setImsi_MSISDN("6666");
        try {
            msiCallService.update(imsiCallBean);  
        } catch (ServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @Test 
    public void updateSelective() {
        ImsiPhoneBean imsiCallBean = new ImsiPhoneBean();
        
        ImsiPhone imsiCall = imsiCallBean.getImsiPhone();
        imsiCall.setId(4575);
        imsiCall.setImsi("11123");
        imsiCall.setPhone("12345678908");
        imsiCall.setImei("234543");
        imsiCall.setMac("243313");
        imsiCall.setImsi_MSISDN("77777");
        try {
            msiCallService.updateSelective(imsiCallBean);
        } catch (ServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @Test 
    public void updateBatchSelective() {
        ImsiPhoneBean imsiCallBean = new ImsiPhoneBean();
        
        ImsiPhone imsiCall = imsiCallBean.getImsiPhone();
        imsiCall.setId(433);
        imsiCall.setImsi("11113221");
        imsiCall.setPhone("4444444");
        imsiCall.setImei("55552315");
        imsiCall.setMac("666131666");
        imsiCall.setImsi_MSISDN("77771317");
        ImsiPhoneBean imsiCall11= new ImsiPhoneBean();
        
        ImsiPhone imsiCall1 = imsiCallBean.getImsiPhone();
        imsiCall1.setId(435);
        imsiCall1.setImsi("46003121441");
        imsiCall1.setPhone("176036633331122");
        imsiCall1.setRecordTime(null);
        imsiCall1.setImei("1213113274");
        imsiCall1.setMac("124354131535");
        imsiCall1.setImsi_MSISDN("12345641133");
        ArrayList<ImsiPhoneBean> arrayList = new ArrayList<>();
        arrayList.add(imsiCallBean);
        arrayList.add(imsiCall11);
        
        try {
            msiCallService.updateBatchSelective(arrayList);
        } catch (ServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
