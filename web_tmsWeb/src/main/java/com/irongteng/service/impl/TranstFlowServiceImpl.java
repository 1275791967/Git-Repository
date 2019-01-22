package com.irongteng.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irongteng.persistence.TranstFlowVO;
import com.irongteng.persistence.beans.TranstFlow;
import com.irongteng.persistence.mapper.DeviceMapper;
import com.irongteng.persistence.mapper.LoadInfoMapper;
import com.irongteng.persistence.mapper.TranstFlowMapper;
import com.irongteng.service.TranstFlowBean;
import com.irongteng.service.TranstFlowService;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;
@Transactional(rollbackFor = Exception.class)
@Service(TranstFlowService.SERVICE_NAME)
@Scope("prototype")
public class TranstFlowServiceImpl extends AbstractBusinessObjectServiceMgr
implements TranstFlowService{
    
    @Autowired
    private TranstFlowMapper transtFlowMapper;
    @Autowired
    private LoadInfoMapper loadInfoMapper;
    @Autowired
    private DeviceMapper deviceMapper;
    
    @Override
    public TranstFlowBean get(Integer id){
        TranstFlow user = transtFlowMapper.load(id);
        if(user == null) return null;
        return new TranstFlowBean(user);
    }
    
    
    
    @Override
    public Integer add(TranstFlowBean bean) throws ServiceException {
        TranstFlow model =bean.getTranstFlow();
//        Device toName = deviceMapper.queryByName(bean.getToDeviceName());
//        Device queryByName = deviceMapper.queryByName(bean.getFromDeviceName());
//        bean.setToDeviceID(toName.getId());
//        bean.setFromDeviceID(queryByName.getId());
        model.setRecordTime(new Date());
        transtFlowMapper.insert(model);
        return model.getId();
    }
    
    
    
    @Override
    public void update(TranstFlowBean bean) throws ServiceException{
        TranstFlow model =bean.getTranstFlow();
        transtFlowMapper.updateSelective(model);
    }
    
    @Override
    public void delete(Integer id){
        transtFlowMapper.delete(id);
    }
    
    @Override
    public List<TranstFlowBean> search(TranstFlowVO vo) {
        List<TranstFlowBean> beans = new ArrayList<>();
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<TranstFlow> models = transtFlowMapper.findPageBreakByCondition(vo, rb);
        models.stream().forEach((model) -> {
            beans.add(new TranstFlowBean(model));
        });
        return beans;
    }
    
    @Override
    public Integer searchNum(TranstFlowVO vo) {
        return transtFlowMapper.findNumberByCondition(vo);
    }
    @Override
   public  TranstFlowBean  analyzeTranslateResult(Integer deviceID){
            TranstFlow analyzeTranslateResult = loadInfoMapper.analyzeTranslateResult(deviceID);
            return new TranstFlowBean (analyzeTranslateResult);
    };
    
    
    @Override
    public List<TranstFlowBean> getDevice(Integer deviceID) {
        List<TranstFlowBean> beans = new ArrayList<>();
        List<TranstFlow>  bean= transtFlowMapper.getDevice(deviceID);
        bean.stream().forEach((model)->beans.add(new TranstFlowBean(model)));
        return beans;
       
    }
    @Override
    public List<TranstFlowBean> getCancelTranslateList(Integer deviceID) {
        List<TranstFlow> datas = transtFlowMapper.getCancelTranslateList(deviceID);
        List<TranstFlowBean> beans = new ArrayList<>();
        for(TranstFlow data : datas){
            beans.add(new TranstFlowBean(data));
        }
        return beans;
    }

    
    @Override
    public TranstFlowBean getLastByImsi(String imsi){
        List<TranstFlow> flows = transtFlowMapper.getLastByImsi(imsi);
        if(flows == null) return null;
        if(flows.size() > 0){
            return new TranstFlowBean(flows.get(0));
        }
        return null;
    }
    
    @Override
    public void deleteBatch(List<Integer> imsiCallList) throws ServiceException{
        if (imsiCallList==null || imsiCallList.size()<=0) {
            throw new ServiceException("msg.list.notnull");
        }
        int index = 0;   //保存每次的索引
        int nextIndex;   //保存最终索引的下一次索引
        int batchCount = 5000;//每批commit的个数
        
        while(index < imsiCallList.size()) {
            nextIndex = index + batchCount;
            if (nextIndex >= imsiCallList.size()) {
                transtFlowMapper.deleteBatch(imsiCallList.subList(index, imsiCallList.size()));
            } else {
                transtFlowMapper.deleteBatch(imsiCallList.subList(index, index + batchCount));
            }
            index += batchCount;
        }
    }



    @Override
    public void updateTranslateStatusByDeviceID(Integer deviceID) {
        List<TranstFlow> list = transtFlowMapper.getCancelTranslateList1(deviceID);
        Date nowTime = new Date();
        for(TranstFlow flow : list){
            flow.setRecordTime(nowTime);
        }
        transtFlowMapper.updateTranslateStatusBatch(list);
        /*for(TranstFlow model : list){
            transtFlowMapper.update(model);
        }
        transtFlowMapper.updateTranslateStatusByDeviceID(deviceID);*/
    }
    
    @Override
    public void updateTranslateStatusByIMSI(String imsi) {
        Date nowTime = new Date();
        transtFlowMapper.updateTranslateStatusByIMSI(imsi, nowTime);
    }

    @Override
    public List<TranstFlowBean> getCancelTranslateList1(Integer deviceID) {
        List<TranstFlow> datas = transtFlowMapper.getCancelTranslateList1(deviceID);
        List<TranstFlowBean> beans = new ArrayList<>();
        for(TranstFlow data : datas){
            beans.add(new TranstFlowBean(data));
        }
        return beans;
    }
    
    //目前仅手机客户端第三方查询使用
    @Override
    public TranstFlowBean queryLastByImsiOrPhone(String text) {
        List<TranstFlow> datas = transtFlowMapper.queryLastByImsiOrPhone(text);
        if(datas != null && datas.size() > 0){
            TranstFlowBean bean = new TranstFlowBean(datas.get(0));
            return bean;
        }
        return null;
        
    }

    
    @Override
    public List<TranstFlowBean> findAll() {
        List<TranstFlowBean> beans = new ArrayList<>();
        List<TranstFlow> models = transtFlowMapper.findAll();
        models.stream().forEach((model) -> {
            beans.add(new TranstFlowBean(model));
        });
        return beans;
    }  

}
