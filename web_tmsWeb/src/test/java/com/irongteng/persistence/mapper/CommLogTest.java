package com.irongteng.persistence.mapper;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.irongteng.persistence.CommLogVO;
import com.irongteng.persistence.beans.CommLog;
import com.irongteng.service.CommLogBean;
import com.irongteng.service.CommLogService;

import dwz.framework.junit.BaseJunitCase;
import dwz.framework.sys.exception.ServiceException;
@Rollback(false)
public class CommLogTest  extends BaseJunitCase{
    
    @Autowired
    private CommLogService service;
    
    @Test
    public void testAdd() {
        CommLogBean bean = new CommLogBean();
        bean.setDeviceID(5);
        bean.setDeviceName("Test手机2");
        bean.setLogTypeID(2);
        bean.setContent("dsad");
        bean.setRecordTime(new Date());
        bean.setRemark("nihaoa");
        try {
            System.out.println(service.add(bean));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
    
    @Test 
    public void update() {
        CommLogBean bean = new CommLogBean();
        CommLog s = bean.getCommLog();
        s.setContent("ssssssddddd");
        s.setDeviceID(2);
        
        try {
            service.update(bean);  
        } catch (ServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @Test
    public void testGet() {
        int id = 3;
        CommLogBean db = service.get(id);
        System.out.println(db.toString());
    }
                                 
    
    @Test
    public void testDelete() {
        int id = 1;
        service.delete(id);
    }
    
    @Test
    public void testSearch() {
        CommLogVO vo = new CommLogVO();
        List<CommLogBean> beans = service.search(vo);
        for (CommLogBean bean: beans) {
            System.out.println(bean.toString());
        }
    }
    
    @Test
    public void testSearchNum() {
        CommLogVO vo = new CommLogVO();        
        Integer num = service.searchNum(vo);
        System.out.println("num:" + num);
    }
    
    
    @Test 
    public void findAllTest(){
      List<CommLogBean> findAll = service.findAll();
      System.out.println("customer findAll == "+findAll);

    }
}
