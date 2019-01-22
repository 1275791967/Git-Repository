package com.irongteng.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.irongteng.persistence.BaseConditionVO;

import dwz.common.util.JdbcUtils;
import dwz.common.util.excel.ExcelBundle;
import dwz.framework.junit.BaseJunitCase;
import dwz.framework.sys.exception.ServiceException;

public class CallAttributionServiceTest extends BaseJunitCase {
    
    @Autowired
    private AttributionService service;
    
    @Test
    public void testQueryByID() {
        try {
            Integer id = 1;
            AttributionBean bean = service.get(id);
            System.out.println("id:" + bean.getId() + " phoneNO:" + bean.getPhoneNO() 
            + " province:" + bean.getProvince() + " city" + bean.getCity() + " " + bean.getCellNO());
        } catch(Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }  
    }
    
    @Test
    public void testQueryByPhoneNO() {
        try {
            Integer phoneNO = 1348076;
            AttributionBean bean = service.queryByPhoneNO(phoneNO);
            System.out.println("id:" + bean.getId() + " phoneNO:" + bean.getPhoneNO() 
                + " province:" + bean.getProvince() +  " city" + bean.getCity() + " " + bean.getCellNO());
        } catch(Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }  
    }
    
    @Test
    public void testAdd() {
        long crt = System.currentTimeMillis();
        try {
            AttributionBean bean = new AttributionBean();
            bean.setPhoneNO(12345678);
            bean.setProvince("河南");
            bean.setCity("信阳");
            bean.setCellNO(0376);
            bean.setRemark("测试用例");
            
            service.add(bean);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis() - crt);
    }
    
    @Test
    public void testAddBatch() {
        long crt = System.currentTimeMillis();
        
        try {
            List<AttributionBean> beans = new ArrayList<AttributionBean>();
            for (int i = 0; i < 200; i++) {
                AttributionBean bean = new AttributionBean();
                bean.setPhoneNO(12345678+i);
                bean.setProvince("河南");
                bean.setCity("信阳");
                bean.setCellNO(0376);
                bean.setRemark("测试用例"+i);
                beans.add(bean);
            }
            
            service.addBatch(beans);
        } catch (ServiceException e) {
            e.printStackTrace();
        } finally{
            
        }
        
        System.out.println("callAttr运行时间：" + (System.currentTimeMillis() - crt));
    }
    
    @Test
    public void testUpdate() {
        try {  
            AttributionBean bean = new AttributionBean();
            bean.setId(269730);
            bean.setPhoneNO(12345578);
            bean.setProvince("河南");
            bean.setCity("信阳");
            bean.setCellNO(0376);
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
            
            List<AttributionBean> beans = new ArrayList<AttributionBean>();
            AttributionBean bean = new AttributionBean();
            bean.setId(269730);
            bean.setPhoneNO(123456781);
            bean.setProvince("河南");
            bean.setCity("信阳");
            bean.setCellNO(0376);
            bean.setRemark("测试用例批量修改1");
            beans.add(bean);
            
            AttributionBean bean2 = new AttributionBean();
            bean2.setId(269731);
            bean2.setPhoneNO(123456782);
            bean2.setProvince("河南");
            bean2.setCity("信阳");
            bean2.setCellNO(0376);
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
        Integer id = 269781;
        service.delete(id);
    }
    
    @Test
    public void testDeleteByPhoneNO() {
        
        service.deleteByPhoneNO(123456);
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
    public void testDeleteBatchByPhoneNO() {
        long crt = System.currentTimeMillis();
        
        try {
            List<Integer> list = new ArrayList<Integer>();
            for (int i = 50; i < 100; i++) {
                list.add(12345678+i);
            }
            service.deleteBatchByPhoneNO(list);
            
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        
        System.out.println("callAttr运行时间：" + (System.currentTimeMillis() - crt));
    }
    
    @Test
    public void testGet() {
        Integer id = 13;
        AttributionBean bean = service.get(id);
         System.out.println("id:" + bean.getId() + " phoneNO:" + bean.getPhoneNO() + " province:" + bean.getProvince() + 
                 " city" + bean.getCity() + " " + bean.getCellNO());
    }
    
    @Test
    public void testFindAll() {
        List<AttributionBean> beans = service.findAll();
        
        for(AttributionBean bean: beans) {
             System.out.println("id:" + bean.getId() + " phoneNO:" + bean.getPhoneNO() + " province:" + bean.getProvince() + 
                        " city" + bean.getCity() + " " + bean.getCellNO());
        }
        
        System.out.println("size:" + beans.size());
    }
    
    @Test
    public void testSearch() {
        BaseConditionVO vo = new BaseConditionVO();
        vo.setKeywords(JdbcUtils.filterLikeSql("123456"));
        List<AttributionBean> beans = service.search(vo);
        
        for(AttributionBean bean: beans) {
             System.out.println("id:" + bean.getId() + " phoneNO:" + bean.getPhoneNO() + " province:" + bean.getProvince() + " city" + bean.getCity() + " " + bean.getCellNO());
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
    public void testSearch_test() {
        BaseConditionVO vo = new BaseConditionVO();
        Integer totalCount = service.searchNum(vo);
        vo.setPageSize(totalCount);
        List<AttributionBean> beans = service.search(vo);
        String[] cs = new String[5];
        List<String[]> strings = new ArrayList<String[]>();
        for (AttributionBean bean: beans) {
            cs[0] = String.valueOf(bean.getPhoneNO());
            cs[1] = bean.getProvince();
            cs[2] = bean.getCity();
            cs[3] = String.valueOf(bean.getCellNO());
            cs[4] = bean.getRemark();
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
    
    @Test
    public void testSearchProvinces() {
        List<String> beans = service.searchProvinces();
        
        for(String bean: beans) {
             System.out.println("province:" + bean);
        }
        
        System.out.println("size:" + beans.size());
    }
    


    @Test
    public void testSearchCityes() {
        List<String> beans = service.searchCitys("山东");
        
        for(String bean: beans) {
             System.out.println("city:" + bean);
        }
        
        System.out.println("size:" + beans.size());
    }
    

    @Test
    public void testSearchCondition() {
        AttributionBean vo = new AttributionBean();
        vo.setProvince("广东");
        //vo.setCity("深圳");
        List<AttributionBean> beans = service.searchCondition(vo);
        
        for(AttributionBean bean: beans) {
             System.out.println("id:" + bean.getId() + " phoneNO:" + bean.getPhoneNO() + " province:" + bean.getProvince() + " city" + bean.getCity() + " " + bean.getCellNO());
        }
        
        System.out.println("size:" + beans.size());
    }
}