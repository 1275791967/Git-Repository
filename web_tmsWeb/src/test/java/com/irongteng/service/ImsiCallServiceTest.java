package com.irongteng.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.irongteng.persistence.BaseConditionVO;

import dwz.common.util.JdbcUtils;
import dwz.common.util.excel.ExcelBundle;
import dwz.common.util.time.DateUtils;
import dwz.framework.junit.BaseJunitCase;
import dwz.framework.sys.exception.ServiceException;

public class ImsiCallServiceTest extends BaseJunitCase {
    
    @Autowired
    private ImsiPhoneService service;
    
    @Test
    public void testQueryByID() {
        try {
            Integer id = 1;
            ImsiPhoneBean bean = service.get(id);
            System.out.println(bean.toString());
        } catch(Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }  
    }
    @Test
    public void testCountAll() {
        try {
            Integer bean = service.countAll();
            System.out.println(bean);
        } catch(Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }  
    }
    
    @Test
    public void testQueryByPhone() {
        try {
            String phoneNO = "13480760840";
            ImsiPhoneBean bean = service.queryByPhone(phoneNO);
            System.out.println(bean.toString());
        } catch(Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }  
    }
    
    @Test
    public void testQueryByImsi() {
        try {
            String imsi = "460018087949962";
            ImsiPhoneBean bean = service.queryByImsi(imsi);
            System.out.println(bean.toString());
        } catch(Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }  
    }
    
    @Test
    public void testAdd() {
        long crt = System.currentTimeMillis();
        try {
            ImsiPhoneBean bean = new ImsiPhoneBean();
            bean.setImsi("0376");
            bean.setPhone("12345678");
            bean.setProvince("河南");
            bean.setCity("信阳");
            
            bean.setRemark("测试用例");
            
            service.add(bean);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis() - crt);
    }
    
    @Test
    public void testAddOrUpdate() {
        ImsiPhoneBean bean = new ImsiPhoneBean();
        bean.setImsi("0376");
        bean.setPhone("123456789");
        //bean.setProvince("河南");
        //bean.setCity("信阳");
        
        bean.setRemark("测试用例");
        
        service.addOrUpdate(bean);
    }
    
    @Test
    public void testAddBatch() {
        long crt = System.currentTimeMillis();
        
        try {
            List<ImsiPhoneBean> callAttrList = new ArrayList<ImsiPhoneBean>();
            for (int i = 0; i < 200; i++) {
                ImsiPhoneBean bean = new ImsiPhoneBean();
                bean.setPhone("" + (long)12345678+i);
                bean.setProvince("河南");
                bean.setCity("信阳");
                bean.setImsi("" + (0376+i));
                bean.setRemark("测试用例"+i);
                callAttrList.add(bean);
            }
            
            service.addBatch(callAttrList);
        } catch (ServiceException e) {
            e.printStackTrace();
        } finally{
            
        }
        
        System.out.println("callAttr运行时间：" + (System.currentTimeMillis() - crt));
    }
    
    @Test
    public void testUpdate() {
        try {  
            ImsiPhoneBean bean = new ImsiPhoneBean();
            bean.setId(2);
            bean.setImsi("" + 0276L);
            bean.setPhone("" + 12345578l);
            bean.setProvince("河南");
            bean.setCity("信阳");
            bean.setRemark("测试用例");
            service.update(bean);
        } catch (ServiceException e) {
            e.printStackTrace();
        } finally {  
            
        }
    }
    
    @Test
    public void testUpdateBatchSelective() {
        long crt = System.currentTimeMillis();
        
        try {    
            
            List<ImsiPhoneBean> beans = new ArrayList<ImsiPhoneBean>();
            ImsiPhoneBean bean = new ImsiPhoneBean();
            bean.setId(1);
            bean.setImsi("" + 23760);
            bean.setPhone("" + 123456781L);
            bean.setProvince("河南");
            bean.setCity("信阳");
            bean.setRemark("测试用例批量修改1");
            beans.add(bean);
            
            ImsiPhoneBean bean2 = new ImsiPhoneBean();
            bean2.setId(2);
            bean2.setImsi("" + 23761);
            bean2.setPhone("" + 123456782l);
            bean2.setProvince("河南");
            bean2.setCity("信阳");
            bean2.setRemark("测试用例批量修改2");
            beans.add(bean2);
           
            service.updateBatchSelective(beans);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        
        System.out.println("callAttr运行时间：" + (System.currentTimeMillis() - crt));
    }
    
    @Test
    public void testDelete() {
        Integer id = 55;
        service.delete(id);
    }
    
    @Test
    public void testDeleteByPhone() {
        service.deleteByPhone("" + 123456l);
    }
    
    @Test
    public void testDeleteBatch() {
        long crt = System.currentTimeMillis();
        
        try {
            List<Integer> list = new ArrayList<Integer>();
            for (int i = 100; i < 200; i++) {
                list.add(269730+i);
            }
            service.deleteBatch(list);
                
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        
        System.out.println("callAttr运行时间：" + (System.currentTimeMillis() - crt));
    }
    
    @Test
    public void testDeleteBatchByPhone() {
        long crt = System.currentTimeMillis();
        
        try {
            List<String> phoneNoLi = new ArrayList<String>();
            for (int i = 50; i < 100; i++) {
                phoneNoLi.add("" + (12345678+i));
            }
            service.deleteBatchByPhone(phoneNoLi);
            
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        
        System.out.println("callAttr运行时间：" + (System.currentTimeMillis() - crt));
    }
    
    @Test
    public void testGet() {
        Integer id = 1;
        ImsiPhoneBean bean = service.get(id);
        System.out.println(bean.toString());
    }
    
    @Test
    public void testSearch() {
        BaseConditionVO vo = new BaseConditionVO();
        vo.setKeywords(JdbcUtils.filterLikeSql("123456"));
        List<ImsiPhoneBean> beans = service.search(vo);
        
        for(ImsiPhoneBean bean: beans) {
            System.out.println(bean);
        }
        
        System.out.println("size:" + beans.size());
    }
    
    @Test
    public void testSearchNum() {
        BaseConditionVO vo = new BaseConditionVO();
        vo.setKeywords(JdbcUtils.filterLikeSql("123456"));
        Integer num = service.searchNum(vo);
        System.out.println("num:" + num);
    }
    
    @Test
    public void testGetLastRecordTime() {
        Date date = service.getLastRecordTime();
        
        System.out.println("Date:" + DateUtils.formatStandardDate(date));
    }
    
    @Test
    public void testExportExcel() {
        BaseConditionVO vo = new BaseConditionVO();
        Integer totalCount = service.searchNum(vo);
        vo.setPageSize(totalCount);
        List<ImsiPhoneBean> beans = service.search(vo);
        String[] cs = new String[5];
        List<String[]> strings = new ArrayList<String[]>();
        for (ImsiPhoneBean bean: beans) {
            cs[0] = String.valueOf(bean.getImsi());
            cs[1] = String.valueOf(bean.getPhone());
            cs[2] = bean.getRemark();
            
            strings.add(Arrays.copyOf(cs,cs.length));
        }
        
        OutputStream output = null;
        try {
            output = new FileOutputStream("d:/test3.xlsx");
            new ExcelBundle().writeExcel(strings, output);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}