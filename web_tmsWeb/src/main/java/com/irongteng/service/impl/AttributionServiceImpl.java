package com.irongteng.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irongteng.persistence.BaseConditionVO;
import com.irongteng.persistence.beans.Attribution;
import com.irongteng.persistence.mapper.AttributionMapper;
import com.irongteng.service.AttributionService;
import com.irongteng.service.AttributionBean;

import dwz.common.util.StringUtils;
import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;

@Transactional(rollbackFor = Exception.class)
@Service(AttributionService.SERVICE_NAME)
@Scope("prototype")
public class AttributionServiceImpl extends AbstractBusinessObjectServiceMgr
        implements AttributionService {
    
    @Autowired
    private AttributionMapper attributionMapper;

    private boolean isUniquePhoneNO(AttributionBean bean) {
        Integer id = bean.getId() != null ? bean.getId() : 0;
        return attributionMapper.isExistPhoneNO(id, bean.getPhoneNO()) < 1;
    }
    
    @Override
    public Integer add(AttributionBean bean) throws ServiceException{
        if (!isUniquePhoneNO(bean)) {
            throw new ServiceException(getMessage("msg.phoneno.unique"));
        }
        Attribution callAttr = bean.getCallAttribution();
        attributionMapper.insert(callAttr);
        return callAttr.getId();
    }
    
    @Override
    public void importBatch(List<AttributionBean> beans) throws ServiceException {
        
        List<Integer> allPhoneNO = this.queryAllPhoneNO();
        
        List<AttributionBean> addList = new ArrayList<>();   //保存不存在的号码归属地信息，用于添加列表
        List<AttributionBean> updateList = new ArrayList<>();//保存已经存在的号码归属地信息，用于更新列表
        beans.stream().forEach((bean) -> {
            if (!allPhoneNO.contains(bean.getPhoneNO())) { //如果不重复
                addList.add(bean);
            } else {
                updateList.add(bean);
            }
        });
        
        if (addList.size()>0) {
            this.addBatch(addList);
        }
        
        if (updateList.size()>0) {
            this.updateBatchSelective(updateList);
        }
    }
    
    @Override
    public void importExcel(List<String[]> strings) throws ServiceException {
        try {
            List<AttributionBean> beans = new ArrayList<>();

            for (int i=1; i<strings.size(); i++) {
                String[] arr = strings.get(i);
				
                try {
                    AttributionBean bean = new AttributionBean();
                    String phone = arr[0];
                    if(!StringUtils.isNumeric(phone)) {
                        continue;
                    }
                    bean.setPhoneNO(Integer.parseInt(arr[0]));
                    bean.setProvince(arr[1]);
                    bean.setCity(arr[2]);
                    
                    String cell = arr[3];
                    if(StringUtils.isNumeric(cell)) {
                        bean.setCellNO(Integer.parseInt(cell));
                    } else {
                        bean.setCellNO(0);
                    }
                    bean.setRemark(arr[4]);
                    
                    beans.add(bean);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
            //批量导入
            this.importBatch(beans);
            
        } catch(ServiceException e) {
            throw new ServiceException(getMessage("msg.excel.import.failure"));
        }
    }
    
    /**
     * 查询所有已经存在的号码段
     * @return
     */
    @Override
    public List<Integer> queryAllPhoneNO() {
        return attributionMapper.queryAllPhoneNO();
    }

    /**
     * 批量添加
     * @param beans
     * @throws dwz.framework.sys.exception.ServiceException
     */
    @Override
    public void addBatch(List<AttributionBean> beans) throws ServiceException {
        
        List<Attribution> models = new ArrayList<>();   //保存不存在的号码归属地信息，用于添加列表
        beans.stream().forEach((bean) -> {
            models.add(bean.getCallAttribution());
        });
        
        int index = 0;       //保存每次的索引
        int nextIndex;       //保存最终索引的下一次索引
        int batchCount = 50; //每批commit的个数
        
        while(index < models.size()) {
            nextIndex = index + batchCount;
            if (nextIndex >= models.size()) {
                attributionMapper.addBatch(models.subList(index, models.size()));
            } else {
                attributionMapper.addBatch(models.subList(index, index + batchCount));
            }
            index += batchCount;
        }
    }
    
    @Override
    public void update(AttributionBean bean) throws ServiceException{
        if (!isUniquePhoneNO(bean)) {
            throw new ServiceException(getMessage("msg.phoneno.unique"));
        }
        Attribution model = bean.getCallAttribution();
        attributionMapper.update(model);
    }
    
    @Override
    public void updateSelective(AttributionBean bean)  throws ServiceException{
        if (!isUniquePhoneNO(bean)) {
            throw new ServiceException(getMessage("msg.phoneno.unique"));
        }
        Attribution callAttr = bean.getCallAttribution();
        attributionMapper.updateSelective(callAttr);
    }
    
    /**
     * 批量更新
     * @param beans
     * @throws dwz.framework.sys.exception.ServiceException
     */
    @Override
    public void updateBatchSelective(List<AttributionBean> beans) throws ServiceException {
        if (beans==null || beans.size()<=0) {
            throw new ServiceException("msg.list.notnull");
        }
        List<Attribution> models = new ArrayList<>();   //保存不存在的号码归属地信息，用于添加列表
        beans.stream().forEach((bean) -> {
            models.add(bean.getCallAttribution());
        });
        
        int index = 0;       //保存每次的索引
        int nextIndex;       //保存最终索引的下一次索引
        int batchCount = 500;//每批commit的个数
        
        while(index < models.size()) {
            nextIndex = index + batchCount;
            if (nextIndex >= models.size()) {
                attributionMapper.updateBatchSelective(models.subList(index, models.size()));
            } else {
                attributionMapper.updateBatchSelective(models.subList(index, index + batchCount));
            }
            index += batchCount;
        }
        
    }
    
    @Override
    public void delete(Integer id) {
        attributionMapper.delete(id);
    }

    @Override
    public void deleteByPhoneNO(Integer phoneNO) {
        attributionMapper.deleteByPhoneNO(phoneNO);
        
    }
    
    @Override
    public void deleteBatch(List<Integer> ids) throws ServiceException{
        if (ids==null || ids.size()<=0) {
            throw new ServiceException("msg.list.notnull");
        }
        int index = 0;        //保存每次的索引
        int nextIndex;        //保存最终索引的下一次索引
        int batchCount = 5000;//每批commit的个数
        
        while(index < ids.size()) {
            nextIndex = index + batchCount;
            if (nextIndex >= ids.size()) {
                attributionMapper.deleteBatch(ids.subList(index, ids.size()));
            } else {
                attributionMapper.deleteBatch(ids.subList(index, index + batchCount));
            }
            index += batchCount;
        }
    }
    
    @Override
    public void deleteBatchByPhoneNO(List<Integer> phoneList) throws ServiceException {
        if (phoneList==null || phoneList.size()<=0) {
            throw new ServiceException("msg.list.notnull");
        }
        
        int index = 0;        //保存每次的索引
        int nextIndex;        //保存最终索引的下一次索引
        int batchCount = 5000;//每批commit的个数
        
        while(index < phoneList.size()) {
            nextIndex = index + batchCount;
            if (nextIndex >= phoneList.size()) {
                attributionMapper.deleteBatchByPhoneNO(phoneList.subList(index, phoneList.size()));
            } else {
                attributionMapper.deleteBatchByPhoneNO(phoneList.subList(index, index + batchCount));
            }
            index += batchCount;
        }
        
    }
    
    @Override
    public List<AttributionBean> search(BaseConditionVO vo) {
        List<AttributionBean> beans = new ArrayList<>();
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<Attribution> models = attributionMapper.findPageBreakByCondition(vo, rb);
        models.stream().forEach((model) -> {
            beans.add(new AttributionBean(model));
        });
        return beans;
    }
    
    @Override
    public Integer searchNum(BaseConditionVO vo) {
        return attributionMapper.findNumberByCondition(vo);
    }
    
    @Override
    public AttributionBean get(Integer id) {
        Attribution model = attributionMapper.load(id);
        if (model == null) return null;
        
        return new AttributionBean(model);
    }

    @Override
    public AttributionBean queryByPhoneNO(Integer phoneNO) {
        Attribution model = attributionMapper.queryByPhoneNO(phoneNO);
        if (model != null) {
            return new AttributionBean(attributionMapper.queryByPhoneNO(phoneNO));
        }
        return null;
    }
    
    @Override
    public List<AttributionBean> findAll() {
        List<AttributionBean> beans = new ArrayList<>();
        List<Attribution> models = attributionMapper.findAll();
        models.stream().forEach((model) -> {
            beans.add(new AttributionBean(model));
        });
        return beans;
    }
    
    @Override
    public Integer countAll() {
        return attributionMapper.countAll();
    }

    @Override
    public List<Integer> findAllIds() {
        return attributionMapper.findAllIds();
    }

    @Override
    public List<String> searchProvinces() {
        return attributionMapper.findProvinces();
    }

    @Override
    public List<String> searchCitys(String province) {
        return attributionMapper.findCitys(province);
    }
    
    @Override
    public List<AttributionBean> searchCondition(AttributionBean bean) {
        List<AttributionBean> beans = new ArrayList<>();
        List<Attribution> models = attributionMapper.findCondition(bean.getCallAttribution());
        
        models.stream().forEach((model) -> {
            beans.add(new AttributionBean(model));
        });
        models.clear();
        return beans;
    }

    @Override
    public String queryAreaByCellNo(Integer cellNo) {
        List<String> areaList = attributionMapper.queryAreaByCellNo(cellNo);
        if(areaList != null && areaList.size() > 0) return areaList.get(0);
        return null;
    }
}
