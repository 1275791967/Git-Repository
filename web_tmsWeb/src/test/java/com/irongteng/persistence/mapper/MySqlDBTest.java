package com.irongteng.persistence.mapper;

import org.springframework.test.annotation.Rollback;
import dwz.framework.junit.BaseJunitCase;
@Rollback(false)
public class MySqlDBTest extends BaseJunitCase{
    
    /*
    @Autowired
    private  LoadInfoService loadInfoService;
    
    @Test
    public void testAdd() {
        LoadInfoBean bean = new LoadInfoBean();
        bean.setCdmaChannels(1);
        bean.setCdmaNarmalChannels(2);
        bean.setCdmaWaitTranslates(3);
        bean.setCmccChannels(4);
        bean.setCmccNarmalChannels(6);
        bean.setCmccWaitTranslates(7);
        bean.setDeviceID(1);
        bean.setUnicomChannels(8);
        bean.setUnicomNarmalChannels(9);
        bean.setUnicomWaitTranslates(10);
        bean.setRecordTime(new Date());
        bean.setRemark("nihaoa");
        try {
            System.out.println(loadInfoService.add(bean));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
    
    @Test 
    public void update() {
        LoadInfoBean bean = new LoadInfoBean();
        
        LoadInfo beans = bean.getLoadInfo();
        beans.setCdmaChannels(2);
        beans.setCdmaNarmalChannels(2);
        beans.setCdmaWaitTranslates(3);
        beans.setCmccChannels(4);
        beans.setCmccNarmalChannels(6);
        beans.setCmccWaitTranslates(7);
        beans.setDeviceID(1);
        beans.setUnicomChannels(8);
        beans.setUnicomNarmalChannels(9);
        beans.setUnicomWaitTranslates(10);
        beans.setRecordTime(new Date());
        beans.setRemark("haode");
        
        try {
            loadInfoService.update(bean);  
        } catch (ServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @Test
    public void testGet() {
        int id = 1;
        LoadInfoBean db = loadInfoService.get(id);
        System.out.println(db.toString());
    }
    
    
    @Test
    public void testSearch() {
        
        LoadInfoVO vo = new LoadInfoVO();
        vo.setDeviceID(3);
        List<LoadInfoBean> beans = loadInfoService.search(vo);
        for (LoadInfoBean bean: beans) {
            System.out.println(bean.toString());
        }
    }
    
    @Test
    public void testDelete() {
        int id = 1;
        loadInfoService.delete(id);
    }
    */
    
    
    

}
