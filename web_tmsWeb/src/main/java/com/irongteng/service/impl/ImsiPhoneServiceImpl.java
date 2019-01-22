package com.irongteng.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irongteng.persistence.BaseConditionVO;
import com.irongteng.persistence.beans.ImsiPhone;
import com.irongteng.persistence.mapper.ImsiPhoneMapper;
import com.irongteng.service.ImsiPhoneBean;
import com.irongteng.service.ImsiPhoneService;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;

@Transactional(rollbackFor = Exception.class)
@Service(ImsiPhoneService.SERVICE_NAME)
@Scope("prototype")
public class ImsiPhoneServiceImpl extends AbstractBusinessObjectServiceMgr
        implements ImsiPhoneService {
    
    @Autowired
    private ImsiPhoneMapper imsiCallMapper;
    
    /**
     * 判断IMSI是否存在
     * @param bean
     * @return
     */
    private boolean isUniqueImsi(ImsiPhoneBean bean) {
        Integer id = bean.getId() != null ? bean.getId() : 0;
        return imsiCallMapper.isExistImsi(id, bean.getImsi()) < 1;
    }
    /**
     * 判断手机号码是否存在
     * @param bean
     * @return
     */
    private boolean isUniquePhone(ImsiPhoneBean bean) {
        Integer id = bean.getId() != null ? bean.getId() : 0;
        return imsiCallMapper.isExistPhone(id, bean.getPhone()) < 1;
    }

    
    /**
     * 查询所有已经存在的号码段
     * @return
     */
    @Override
    public List<String> queryAllPhone() {
        return imsiCallMapper.queryAllPhone();
    }
    /**
     * 添加
     * @param bean
     * @return 
     * @throws dwz.framework.sys.exception.ServiceException 
     */
    @Override
    public Integer add(ImsiPhoneBean bean) throws ServiceException{
        if (!isUniquePhone(bean)) {
            throw new ServiceException(getMessage("手机号码已经存在，请核对后提交"));
        }
        if (!isUniqueImsi(bean)) {
            throw new ServiceException(getMessage("imsi号码已经存在，请核对后提交"));
        }
        bean.setRecordTime(new Date());
        
        ImsiPhone model = bean.getImsiPhone();
        imsiCallMapper.insert(model);
        return model.getId();
    }
    
    /**
     * 添加或者更新记录
     * @param bean
     */
    @Override
    public void addOrUpdate(ImsiPhoneBean bean){
        bean.setRecordTime(new Date());
        imsiCallMapper.addOrUpdate(bean.getImsiPhone());
    }
    /**
     * 批量添加
     * @param beans
     * @throws dwz.framework.sys.exception.ServiceException
     */
    @Override
    public void addBatch(List<ImsiPhoneBean> beans) throws ServiceException {
        
        List<ImsiPhone> models = new ArrayList<>();   //保存不存在的号码归属地信息，用于添加列表
        Date date = new Date();
        beans.stream().map((bean) -> {
            bean.setRecordTime(date);
            return bean;
        }).forEach((bean) -> {
            models.add(bean.getImsiPhone());
        });
        
        int index = 0;       //保存每次的索引
        int nextIndex;       //保存最终索引的下一次索引
        int batchCount = 50; //每批commit的个数
        
        while(index < models.size()) {
            nextIndex = index + batchCount;
            if (nextIndex >= models.size()) {
                imsiCallMapper.addBatch(models.subList(index, models.size()));
            } else {
                imsiCallMapper.addBatch(models.subList(index, index + batchCount));
            }
            index += batchCount;
        }
    }
    /**
     * 更新
     * @param bean
     * @throws dwz.framework.sys.exception.ServiceException
     */
    @Override
    public void update(ImsiPhoneBean bean) throws ServiceException{
        if (!isUniquePhone(bean)) {
            throw new ServiceException(getMessage("手机号码已经存在，请核对后提交"));
        }
        if (!isUniqueImsi(bean)) {
            throw new ServiceException(getMessage("imsi号码已经存在，请核对后提交"));
        }
        bean.setRecordTime(new Date());
        ImsiPhone model = bean.getImsiPhone();
        imsiCallMapper.update(model);
    }
    
    /**
     * 条件更新
     * @param bean
     * @throws dwz.framework.sys.exception.ServiceException
     */
    @Override
    public void updateSelective(ImsiPhoneBean bean)  throws ServiceException{
        if (!isUniquePhone(bean)) {
            throw new ServiceException(getMessage("手机号码已经存在，请核对后提交"));
        }
        if (!isUniqueImsi(bean)) {
            throw new ServiceException(getMessage("imsi号码已经存在，请核对后提交"));
        }
        bean.setRecordTime(new Date());
        
        ImsiPhone model = bean.getImsiPhone();
        imsiCallMapper.updateSelective(model);
    }
    
    /**
     * 按条件批量更新
     * @param beans
     * @throws dwz.framework.sys.exception.ServiceException
     */
    @Override
    public void updateBatchSelective(List<ImsiPhoneBean> beans) throws ServiceException {
        if (beans==null || beans.size()<=0) {
            throw new ServiceException("msg.list.notnull");
        }
        List<ImsiPhone> imsiCallList = new ArrayList<>();   //保存不存在的号码归属地信息，用于添加列表
        Date date = new Date();
        beans.stream().map((bean) -> {
            bean.setRecordTime(date);
            return bean;
        }).forEach((bean) -> {
            imsiCallList.add(bean.getImsiPhone());
        });
        
        int index = 0;       //保存每次的索引
        int nextIndex;       //保存最终索引的下一次索引
        int batchCount = 500;//每批commit的个数
        
        while(index < imsiCallList.size()) {
            nextIndex = index + batchCount;
            if (nextIndex >= imsiCallList.size()) {
                imsiCallMapper.updateBatchSelective(imsiCallList.subList(index, imsiCallList.size()));
            } else {
                imsiCallMapper.updateBatchSelective(imsiCallList.subList(index, index + batchCount));
            }
            index += batchCount;
        }
        
    }
    
    
    @Override
    public void delete(Integer id) {
        imsiCallMapper.delete(id);
    }

    @Override
    public void deleteByPhone(String phone) {
        imsiCallMapper.deleteByPhone(phone);
        
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
                imsiCallMapper.deleteBatch(imsiCallList.subList(index, imsiCallList.size()));
            } else {
                imsiCallMapper.deleteBatch(imsiCallList.subList(index, index + batchCount));
            }
            index += batchCount;
        }
    }
    
    @Override
    public void deleteBatchByPhone(List<String> phoneList) throws ServiceException {
        if (phoneList==null || phoneList.size()<=0) {
            throw new ServiceException("msg.list.notnull");
        }
        int index = 0;       //保存每次的索引
        int nextIndex;   //保存最终索引的下一次索引
        int batchCount = 5000;//每批commit的个数
        while(index < phoneList.size()) {
            nextIndex = index + batchCount;
            if (nextIndex >= phoneList.size()) {
                imsiCallMapper.deleteBatchByPhone(phoneList.subList(index, phoneList.size()));
            } else {
                imsiCallMapper.deleteBatchByPhone(phoneList.subList(index, index + batchCount));
            }
            index += batchCount;
        }
    }
    
//    @Override
//    public  Integer searchStatusNum(ImsiCallBean bean){
//      return  imsiCallMapper.searchStatusNum(bean);
//  }
    
    
    @Override
    public List<ImsiPhoneBean> search(BaseConditionVO vo) {
        List<ImsiPhoneBean> beans = new ArrayList<>();
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<ImsiPhone> al = imsiCallMapper.findPageBreakByCondition(vo, rb);
        al.stream().forEach((imsiCall) -> {
            beans.add(new ImsiPhoneBean(imsiCall));
        });
        return beans;
    }
    
    @Override
    public Integer searchNum(BaseConditionVO vo) {
        Integer count = imsiCallMapper.findNumberByCondition(vo);
        return count;
    }
    
    @Override
    public ImsiPhoneBean get(Integer id) {
        ImsiPhone model = imsiCallMapper.load(id);
        if (model == null) return null;
        return new ImsiPhoneBean(model);
    }
    
    
    /**
     * 通过手机号码查询对象
     * @param phone
     * @return 
     */
    @Override
    public ImsiPhoneBean queryByPhone(String phone) {
        ImsiPhone model = imsiCallMapper.queryByPhone(phone);
        if (model!=null) {
            return new ImsiPhoneBean(imsiCallMapper.queryByPhone(phone));
        }
        return null;
    }
    /**
     * 通过IMSI查询对象
     * @param imsi
     * @return 
     */
    @Override
    public ImsiPhoneBean queryByImsi(String imsi) {
        ImsiPhone model = imsiCallMapper.queryByImsi(imsi);
        if (model!=null) {
            return new ImsiPhoneBean(model);
        }
        return null;
    }
    
    @Override
    public List<ImsiPhoneBean> findAll() {
        List<ImsiPhoneBean> beans = new ArrayList<>();
        List<ImsiPhone> models = imsiCallMapper.findAll();
        models.stream().forEach((model) -> {
            beans.add(new ImsiPhoneBean(model));
        });
        return beans;
    }
    @Override
    public Integer countAll() {
        return imsiCallMapper.countAll();
    }

    @Override
    public List<Integer> findAllIds() {
        return imsiCallMapper.findAllIds();
    }
    
    @Override
    public Date getLastRecordTime() {
        return imsiCallMapper.getLastRecordTime();
    }

    @Override
    public void importBatch(List<ImsiPhoneBean> beans) throws ServiceException {
        
        List<String> allPhoneNO = this.queryAllPhone();
        
        List<ImsiPhoneBean> addBeans = new ArrayList<>();   //保存不存在的号码归属地信息，用于添加列表
        List<ImsiPhoneBean> updateBeans = new ArrayList<>();//保存已经存在的号码归属地信息，用于更新列表
        beans.stream().forEach((bean) -> {
           
            if (!allPhoneNO.contains(bean.getPhone())) { //不重复则添加,重复则修改
       
                addBeans.add(bean);
            } else {
            
                updateBeans.add(bean);
            }
        });
        
        if (addBeans.size()>0) {
            this.addBatch(addBeans);
        }
        
        if (updateBeans.size()>0) {
            this.updateBatchSelective(updateBeans);
        }
    }
    
    @Override
    public void importExcel(List<String[]> strings) throws ServiceException {
        try {
            List<ImsiPhoneBean> beans = new ArrayList<>();
            strings.stream().map((arr) -> {
                ImsiPhoneBean bean = new ImsiPhoneBean();
                bean.setImsi(arr[0]);
                bean.setPhone(arr[1]);
                return bean;                
            }).forEach((bean) -> {
               
                ImsiPhoneBean queryByImsi = queryByImsi(bean.getImsi());
                if(queryByImsi!=null){
//                    log.error("imsi重复,跳过本条数据-----------------"+bean.toString());
                    return;
                }
                beans.add(bean);
            });
            //批量导入
            beans.remove(0);
            this.importBatch(beans);
            
        } catch(ServiceException e) {
            throw new ServiceException(getMessage("msg.excel.import.failure"));
        }
    }
}
