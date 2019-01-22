package com.irongteng.persistence.mapper;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.irongteng.persistence.LoadInfoVO;
import com.irongteng.persistence.beans.LoadInfo;
import com.irongteng.service.LoadInfoBean;
import com.irongteng.service.LoadInfoService;

import dwz.framework.junit.BaseJunitCase;
import dwz.framework.sys.exception.ServiceException;
@Rollback(false)
public class LoadInfoTest extends BaseJunitCase{
    @Autowired
    private  LoadInfoService loadInfoService;
    
    @Test
    public void testAdd() {
        LoadInfoBean bean = new LoadInfoBean();
        bean.setCh1Counts(11);
        bean.setCh1NormalCounts(22);
        bean.setCh1WaitTranslates(33);
        bean.setCh2Counts(44);
        bean.setCh2NormalCounts(55);
        bean.setCh2WaitTranslates(66);
        bean.setDeviceID(9);
        bean.setCh3Counts(99);
        bean.setCh3NormalCounts(1);
        bean.setCh3WaitTranslates(6);
        bean.setCh4Counts(5);
        bean.setCh4NormalCounts(7);
        bean.setCh4WaitTranslates(3);
        bean.setCh5Counts(2);
        bean.setCh5NormalCounts(5);
        bean.setCh5WaitTranslates(3);
        bean.setAlarmStatus(2);
        bean.setRecordTime(new Date());
        bean.setRemark("天气晴朗");
        try {
            System.out.println(loadInfoService.add(bean));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
    
    @Test 
    public void update() {
        LoadInfoBean beans = new LoadInfoBean();
        LoadInfo bean = beans.getLoadInfo();
        bean.setID(105);
        bean.setRemark("笑笑");
        bean.setDeviceID(11);
        
        try {
            loadInfoService.update(beans);  
        } catch (ServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @Test
    public void testGet() {
        int id = 101;
        LoadInfoBean db = loadInfoService.get(id);
        System.out.println(db.toString());
    }
    
    @Test
    public void testSearch() {   
        LoadInfoVO vo = new LoadInfoVO();
        List<LoadInfoBean> beans = loadInfoService.search(vo);
        for (LoadInfoBean bean: beans) {
            System.out.println(bean.toString());
        }
    }
    
    @Test
    public void testSearchNum() {
        LoadInfoVO vo = new LoadInfoVO();        
        Integer num = loadInfoService.searchNum(vo);
        System.out.println("num:" + num);
    }
    
    
    @Test
    public void testDelete() {
        int id = 1;
        loadInfoService.delete(id);
    }

    @Test
    public void testSearchBy() {
        LoadInfoVO vo = new LoadInfoVO();
        List<LoadInfoBean> beans = loadInfoService.searchBy(vo);
        for (LoadInfoBean bean: beans) {
            System.out.println(bean.toString());
        }
    }
    
    @Test
    public void testQueryaUnicomTranslateRatio() {
        LoadInfoBean bean = loadInfoService.channelOneTranslateRatio();
        System.out.println(bean);
    }
    
    @Test
    public void testQueryaCmccTranslateRatio() {
        LoadInfoBean bean = loadInfoService.channelTwoTranslateRatio();
        System.out.println(bean);
    }
    
    @Test
    public void testQueryaCdmaTranslateRatio() {
        LoadInfoBean bean = loadInfoService.channeThreeTranslateRatio();
        System.out.println(bean);
    }
    
    @Test 
    public void findAllTest(){
      List<LoadInfoBean> findAll = loadInfoService.findAll();
      System.out.println("customer findAll == "+findAll);

    }
}
