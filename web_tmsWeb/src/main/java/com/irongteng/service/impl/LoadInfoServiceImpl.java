package com.irongteng.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irongteng.persistence.LoadInfoVO;
import com.irongteng.persistence.beans.Device;
import com.irongteng.persistence.beans.LoadInfo;
import com.irongteng.persistence.mapper.DeviceMapper;
import com.irongteng.persistence.mapper.LoadInfoMapper;
import com.irongteng.service.LoadInfoBean;
import com.irongteng.service.LoadInfoService;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;

@Transactional(rollbackFor = Exception.class)
@Service(LoadInfoService.SERVICE_NAME)
@Scope("prototype")
public class LoadInfoServiceImpl extends AbstractBusinessObjectServiceMgr
implements LoadInfoService{
    @Autowired
    private LoadInfoMapper loadInfoMapper;
    
    @Autowired
    private DeviceMapper deviceMapper;
  
    @Override
    public LoadInfoBean get(Integer id){
        LoadInfo user = loadInfoMapper.load(id);
        if(user == null) return null;
        return new LoadInfoBean(user);
    }
    
    @Override
    public Integer add(LoadInfoBean bean) throws ServiceException {
        if (!isUniqueID(bean)) {
            throw new ServiceException(getMessage("设备ID已存在，请核对后提交"));
        }
        LoadInfo model =bean.getLoadInfo();
        Device queryByName = deviceMapper.load(model.getDeviceID());
        bean.setDeviceID(queryByName.getId());

//        Device queryByName = deviceMapper.queryByName(model.getDeviceName());
//        bean.setDeviceID(queryByName.getId());

        loadInfoMapper.insert(model);
        return model.getId();
    }
    
    private boolean isUniqueID(LoadInfoBean bean) {
        Integer id = bean.getId() != null ? bean.getId() : 0;
        return loadInfoMapper.isUniqueID(id, bean.getDeviceID()) < 1;
    }
    
    @Override
    public void update(LoadInfoBean bean) throws ServiceException{
        if (!isUniqueID(bean)) {
            throw new ServiceException(getMessage("设备ID已存在，请核对后提交"));
        }
        LoadInfo model =bean.getLoadInfo();
        loadInfoMapper.updateSelective(model);
    }
    
    @Override
    public void delete(Integer id){
        loadInfoMapper.delete(id);
    }
    
    @Override
    public List<LoadInfoBean> search(LoadInfoVO vo) {
        List<LoadInfoBean> beans = new ArrayList<>();
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<LoadInfo> models = loadInfoMapper.findPageBreakByCondition(vo, rb);
        models.stream().forEach((model) -> {
            beans.add(new LoadInfoBean(model));
        });
        return beans;
    }
    
    @Override
    public Integer searchNum(LoadInfoVO vo) {
        return loadInfoMapper.findNumberByCondition(vo);
    }
    
    @Override
    public LoadInfoBean queryByDeviceID(Integer deviceID){
        LoadInfo info = loadInfoMapper.queryByDeviceID(deviceID);
        if(info == null) return null;
        return new LoadInfoBean(info);
    }
    
    
    @Override
    public LoadInfoBean channelOneTranslateRatio(){
        List<LoadInfo> infos = loadInfoMapper.channelOneTranslateRatio();
        if(infos == null) return null;
        if(infos.size() == 0) return null;
        LoadInfo info = infos.get(0);
        return new LoadInfoBean(info);
                
        /*List<LoadInfoBean> beans = new ArrayList<>();
        for(LoadInfo info : infos){
            beans.add(new LoadInfoBean(info));
        }
        return beans;*/
    }
    
    @Override
    public LoadInfoBean channelTwoTranslateRatio(){
        List<LoadInfo> infos = loadInfoMapper.channelTwoTranslateRatio();
        if(infos == null) return null;
        if(infos.size() == 0) return null;
        LoadInfo info = infos.get(0);
        return new LoadInfoBean(info);
    }
    
    @Override
    public LoadInfoBean channeThreeTranslateRatio(){
        List<LoadInfo> infos = loadInfoMapper.channeThreeTranslateRatio();
        if(infos == null) return null;
        if(infos.size() == 0) return null;
        LoadInfo info = infos.get(0);
        return new LoadInfoBean(info);
    }
    
    @Override
    public LoadInfoBean channeFourTranslateRatio(){
        List<LoadInfo> infos = loadInfoMapper.channeFourTranslateRatio();
        if(infos == null) return null;
        if(infos.size() == 0) return null;
        LoadInfo info = infos.get(0);
        return new LoadInfoBean(info);
    }
    
    
    @Override
    public LoadInfoBean channeFiveTranslateRatio(){
        List<LoadInfo> infos = loadInfoMapper.channeFiveTranslateRatio();
        if(infos == null) return null;
        if(infos.size() == 0) return null;
        LoadInfo info = infos.get(0);
        return new LoadInfoBean(info);
    }
    


//    @Override
//    public List<Map> searchBy(LoadInfoVO vo) {
//        List<LoadInfoBean> beans = new ArrayList<>();
//        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
//            Thread thread = new Thread(){
//               public void run(){
//                   Boolean isflg=true;
//                   while(isflg){
//                     List<Map> models = loadInfoMapper.findPage(vo, rb);
//                     try {
//                        Thread.sleep(10);
//                    } catch (InterruptedException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//      
//                 }
//               }
//            
//            };
//            thread.start();
//            return null;
//    }
//    
    @Override
    public List<LoadInfoBean> searchBy(LoadInfoVO vo) {
        List<LoadInfoBean> beans = new ArrayList<>();
        List<LoadInfo>  bean=  loadInfoMapper.findPage(vo);
        bean.stream().forEach((model)->beans.add(new LoadInfoBean(model)));
        return beans; 
    }

    @Override
    public List<LoadInfoBean> findAll() {
        List<LoadInfoBean> beans = new ArrayList<>();
        List<LoadInfo> models = loadInfoMapper.findAll();
        models.stream().forEach((model) -> {
            beans.add(new LoadInfoBean(model));
        });
        return beans;
    }  
    
//    
//    @Override
//    public List<LoadInfoBean> searchByDevice (LoadInfoVO vo) {
//        List<LoadInfoBean> beans = new ArrayList<>();
//        List<LoadInfo>  bean=  loadInfoMapper.getDevice(vo);
//        bean.stream().forEach((model)->beans.add(new LoadInfoBean(model)));
//        return beans; 
//    }
//
//    

}

