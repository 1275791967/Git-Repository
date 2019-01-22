package com.irongteng.persistence.mapper;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.irongteng.persistence.OpInfoVO;
import com.irongteng.service.OpInfoBean;
import com.irongteng.service.OpInfoService;

import dwz.framework.junit.BaseJunitCase;
@Rollback(false)
public class OpInfoTest extends BaseJunitCase{
    
    @Autowired
    private OpInfoService opInfoService;
    
    @Test
    public void testSearch() {
        OpInfoVO vo = new OpInfoVO();
        List<OpInfoBean> beans = opInfoService.search(vo);
        for (OpInfoBean bean: beans) {
            System.out.println(bean.toString());
        }
    }
    
    @Test
    public void testSearchNum() {
        OpInfoVO vo = new OpInfoVO();        
        Integer num = opInfoService.searchNum(vo);
        System.out.println("num:" + num);
    }
    
    @Test
    public void testGet() {
        int id = 3;
        OpInfoBean db = opInfoService.get(id);
        System.out.println(db.toString());
    }
    
    
    @Test 
    public void queryByNameTest(){
        OpInfoBean queryByName = opInfoService.queryByName("移动GSM");
        System.out.println("customer ID == "+ queryByName);
    }
    
    @Test 
    public void queryByNoTest(){
        int channelType = 14;
        OpInfoBean bean = opInfoService.queryByNo(channelType);
        System.out.println("customer ID == "+ bean == null ? "bean is null....":bean.toString());
    }
    
}

