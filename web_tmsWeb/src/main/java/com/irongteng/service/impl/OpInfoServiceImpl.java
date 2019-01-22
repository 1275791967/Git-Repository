package com.irongteng.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irongteng.persistence.OpInfoVO;
import com.irongteng.persistence.beans.OpInfo;
import com.irongteng.persistence.mapper.OpInfoServiceMapper;
import com.irongteng.service.OpInfoBean;
import com.irongteng.service.OpInfoService;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;

@Transactional(rollbackFor = Exception.class)
@Service(OpInfoService.SERVICE_NAME)
@Scope("prototype")
public class OpInfoServiceImpl  extends AbstractBusinessObjectServiceMgr implements OpInfoService{
    
    @Autowired
    private OpInfoServiceMapper opInfoServiceMapper;
    
    @Override
    public List<OpInfoBean> search(OpInfoVO vo) {
        List<OpInfoBean> beans = new ArrayList<>();
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<OpInfo> al = opInfoServiceMapper.findPageBreakByCondition(vo, rb);
        al.stream().forEach((imsiCall) -> {
            beans.add(new OpInfoBean(imsiCall));
        });
        return beans;
    }
    
    
    @Override
    public Integer searchNum(OpInfoVO vo) {
        Integer count = opInfoServiceMapper.findNumberByCondition(vo);
        return count;
    }
   
    @Override
    public OpInfoBean get(Integer id) {
        OpInfo model = opInfoServiceMapper.load(id);
        if (model == null) return null;
        return new OpInfoBean(model);
    }
    
    @Override
    public Integer add(OpInfoBean bean) throws ServiceException{
        if (!isUniqueOpNo(bean)) {
            throw new ServiceException(getMessage("运营商编号已存在，请核对后提交"));
        }
        OpInfo model = bean.getOpInfo();
        bean.setCreateTime(new Date());
        bean.setUpdateTime(new Date());
        opInfoServiceMapper.insert(model);
        return model.getId();
    }
    
    private boolean isUniqueOpNo(OpInfoBean bean) {
        Integer id = bean.getId() != null ? bean.getId() : 0;
        return opInfoServiceMapper.isUniqueOpNo(id, bean.getOpNo()) < 1;
    }

    
    @Override
    public void update(OpInfoBean bean) throws ServiceException{
        if (!isUniqueOpNo(bean)) {
            throw new ServiceException(getMessage("运营商编号已存在，请核对后提交"));
        }
        bean.setCreateTime(new Date());
        bean.setUpdateTime(new Date());    
        OpInfo model = bean.getOpInfo();  
        opInfoServiceMapper.update(model);
    }
    
    
    @Override
    public void delete(Integer id) {
        opInfoServiceMapper.delete(id);
    }
    
    @Override
    public OpInfoBean queryByName(String opName){
        OpInfo model = opInfoServiceMapper.queryByName(opName);
        if (model!=null) {
            return new OpInfoBean(model);
        }
        return null;
    }


    @Override
    public OpInfoBean queryByNo(Integer opNo) {
        OpInfo model = opInfoServiceMapper.queryByNo(opNo);
        if (model!=null) {
            return new OpInfoBean(model);
        }
        return null;
    }
}
