package com.irongteng.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irongteng.persistence.DeviceVO;
import com.irongteng.persistence.beans.Device;
import com.irongteng.persistence.mapper.DeviceMapper;
import com.irongteng.service.CustomerBean;
import com.irongteng.service.CustomerService;
import com.irongteng.service.DeviceBean;
import com.irongteng.service.DeviceService;

import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;

@Transactional(rollbackFor = Exception.class)
@Service(DeviceService.SERVICE_NAME)
@Scope("prototype")
public class DeviceServiceImpl extends AbstractBusinessObjectServiceMgr implements DeviceService {
    @Autowired
    private DeviceMapper deviceMapper;
    @Autowired
    private CustomerService customerMapper;

    /**
     * 添加
     * 
     * @param bean
     * @return
     * @throws dwz.framework.sys.exception.ServiceException
     */
    @Override
    public Integer add(DeviceBean bean) throws ServiceException {
        if (!isUniqueName(bean)) {
            throw new ServiceException(getMessage("设备名字已存在，请核对后提交"));
        }
        if (!isUniqueFeatureCode(bean)) {
            throw new ServiceException(getMessage("特征码已经存在，请核对后提交"));
        }
        bean.setCreateTime(new Date());
        CustomerBean queryByName = customerMapper.queryByName(bean.getCustomerName());
        bean.setCustomerID(queryByName.getId());
        // bean.setCustomerID(customerMapper.queryByName(bean.getCustomerName()).getId());
        Device model = bean.getDevice();
        deviceMapper.insert(model);
        return model.getId();
    }
    

    private boolean isUniqueFeatureCode(DeviceBean bean) {
        Integer id = bean.getId() != null ? bean.getId() : 0;
        return deviceMapper.isUniqueFeatureCode(id, bean.getFeatureCode()) < 1;
    }


    @Override
    public void addBatch(List<DeviceBean> beans) throws ServiceException {
        List<Device> models = new ArrayList<>();
        Date date = new Date();
        beans.stream().map((bean) -> {
            bean.setCreateTime(date);
            return bean;
        }).forEach((bean) -> {
            models.add(bean.getDevice());
        });

        int index = 0; // 保存每次的索引
        int nextIndex; // 保存最终索引的下一次索引
        int batchCount = 50; // 每批commit的个数

        while (index < models.size()) {
            nextIndex = index + batchCount;
            if (nextIndex >= models.size()) {
                deviceMapper.addBatch(models.subList(index, models.size()));
            } else {
                deviceMapper.addBatch(models.subList(index, index + batchCount));
            }
            index += batchCount;
        }
    }

    /**
     * 更新
     * 
     * @param bean
     * @throws dwz.framework.sys.exception.ServiceException
     */
    @Override
    public void update(DeviceBean bean) throws ServiceException {
        if (!isUniqueName(bean)) {
            throw new ServiceException(getMessage("设备名字已存在，请核对后提交"));
        }
        if (!isUniqueFeatureCode(bean)) {
            throw new ServiceException(getMessage("特征码已经存在，请核对后提交"));
        }
        Device balancedetail = bean.getDevice();
        bean.setCreateTime(new Date());
        deviceMapper.update(balancedetail);
    }

    /**
     * 条件更新
     * 
     * @param bean
     * @throws dwz.framework.sys.exception.ServiceException
     */
    @Override
    public void updateSelective(DeviceBean bean) throws ServiceException {
        if (!isUniqueName(bean)) {
            throw new ServiceException(getMessage("设备名字已存在，请核对后提交"));
        }
        if (!isUniqueFeatureCode(bean)) {
            throw new ServiceException(getMessage("特征码已经存在，请核对后提交"));
        }
        bean.setCreateTime(new Date());
        Device model = bean.getDevice();
        deviceMapper.updateSelective(model);
    }

    /**
     * 按条件批量更新
     * 
     * @param beans
     * @throws dwz.framework.sys.exception.ServiceException
     */
    @Override
    public void updateBatchSelective(List<DeviceBean> beans) throws ServiceException {
        if (beans == null || beans.size() <= 0) {
            throw new ServiceException("msg.list.notnull");
        }
        List<Device> device = new ArrayList<>();
        Date date = new Date();
        beans.stream().map((bean) -> {
            bean.setCreateTime(date);
            return bean;
        }).forEach((bean) -> {
            device.add(bean.getDevice());
        });

        int index = 0; // 保存每次的索引
        int nextIndex; // 保存最终索引的下一次索引
        int batchCount = 500;// 每批commit的个数

        while (index < device.size()) {
            nextIndex = index + batchCount;
            if (nextIndex >= device.size()) {
                deviceMapper.updateBatchSelective(device.subList(index, device.size()));
            } else {
                deviceMapper.updateBatchSelective(device.subList(index, index + batchCount));
            }
            index += batchCount;
        }

    }

    @Override
    public void delete(Integer id) {
        deviceMapper.delete(id);
    }

    @Override
    public void deleteBatch(List<Integer> imsiCallList) throws ServiceException {
        if (imsiCallList == null || imsiCallList.size() <= 0) {
            throw new ServiceException("msg.list.notnull");
        }
        int index = 0; // 保存每次的索引
        int nextIndex; // 保存最终索引的下一次索引
        int batchCount = 5000;// 每批commit的个数

        while (index < imsiCallList.size()) {
            nextIndex = index + batchCount;
            if (nextIndex >= imsiCallList.size()) {
                deviceMapper.deleteBatch(imsiCallList.subList(index, imsiCallList.size()));
            } else {
                deviceMapper.deleteBatch(imsiCallList.subList(index, index + batchCount));
            }
            index += batchCount;
        }
    }

    @Override
    public List<DeviceBean> findAll() {
        List<DeviceBean> beans = new ArrayList<>();
        List<Device> models = deviceMapper.findAll();
        models.stream().forEach((model) -> {
            beans.add(new DeviceBean(model));
        });
        return beans;
    }

    @Override
    public List<DeviceBean> search(DeviceVO vo) {
        List<DeviceBean> beans = new ArrayList<>();
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<Device> al = deviceMapper.findPageBreakByCondition(vo, rb);
        al.stream().forEach((imsiCall) -> {
            beans.add(new DeviceBean(imsiCall));
        });
        return beans;
    }

    @Override
    public Integer searchNum(DeviceVO vo) {
        Integer count = deviceMapper.findNumberByCondition(vo);
        return count;
    }

    @Override
    public Integer countAll() {
        return deviceMapper.countAll();
    }

    @Override
    public List<Integer> findAllIds() {
        return deviceMapper.findAllIds();
    }

    @Override
    public DeviceBean get(DeviceBean deviceBean) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DeviceBean> queryAll() {
        DeviceVO vo = new DeviceVO();
        vo.setStatus("1"); // 查询已配置设备
        return this.searchCommonByCondition(vo);
    }

    private List<DeviceBean> searchCommonByCondition(DeviceVO vo) {
        List<DeviceBean> beans = new ArrayList<>();
        List<Device> models = deviceMapper.findCommonByCondition(vo);
        models.stream().forEach((model) -> beans.add(new DeviceBean(model)));
        return beans;
    }

    @Override
    public List<DeviceBean> searchAll() {
        List<DeviceBean> beans = new ArrayList<>();
        List<Device> models = deviceMapper.findAll();
        models.stream().forEach((model) -> beans.add(new DeviceBean(model)));
        return beans;
    }

    @Override
    public Integer searchAllNum() {
        return deviceMapper.countAll();
    }

    @Override
    public void importExcel(List<String[]> strings) throws ServiceException {
        try {
            List<DeviceBean> beans = new ArrayList<>();
            for (int i = 1; i < strings.size(); i++) {
                String[] arr = strings.get(i);
                DeviceBean bean = new DeviceBean();
                bean.setDeviceName(arr[0]);
                bean.setCustomerID(customerMapper.queryByName(arr[1]).getId());
                bean.setDeviceType(Integer.valueOf(arr[2]));
                bean.setFeatureCode(arr[3]);
                bean.setAddress(arr[4]);
                bean.setLongitude(arr[5]);
                bean.setLatitude(arr[6]);
                bean.setRemark(arr[7]);
                bean.setCustomerID(Integer.valueOf(arr[8]));
                beans.add(bean);
            }
            // 批量导入
            this.importBatch(beans);
        } catch (ServiceException e) {
            throw new ServiceException(getMessage("msg.excel.import.failure"));
        }
    }

    public void importBatch(List<DeviceBean> beans) throws ServiceException {

        List<DeviceBean> addBeans = new ArrayList<>(); // 保存不存在的设备信息，用于添加列表
        List<DeviceBean> updateBeans = new ArrayList<>();// 保存已经存在的设备信息，用于更新列表
        beans.stream().forEach((bean) -> {
            if (isUniqueName(bean)) {
                addBeans.add(bean);
            } else {
                DeviceBean rbean = this.queryByName(bean.getDeviceName());
                if (rbean != null) {
                    bean.setId(rbean.getId());
                }
                updateBeans.add(bean);
            }
        });

        if (addBeans.size() > 0) {
            this.addBatch(addBeans);
        }

        if (updateBeans.size() > 0) {
            this.updateBatch(updateBeans);
        }
    }

    /**
     * 批量更新
     * 
     * @param beans
     * @throws dwz.framework.sys.exception.ServiceException
     */
    @Override
    public void updateBatch(List<DeviceBean> beans) throws ServiceException {
        if (beans == null || beans.size() <= 0) {
            throw new ServiceException("msg.list.notnull");
        }
        List<Device> models = new ArrayList<>(); // 保存不存在的号码归属地信息，用于添加列表
        Date date = new Date();
        beans.stream().map((bean) -> {
            bean.setCreateTime(date);
            return bean;
        }).forEach((bean) -> {
            models.add(bean.getDevice());
        });

        int index = 0; // 保存每次的索引
        int nextIndex; // 保存最终索引的下一次索引
        int batchCount = 500;// 每批commit的个数

        while (index < models.size()) {
            nextIndex = index + batchCount;
            if (nextIndex >= models.size()) {
                deviceMapper.updateBatch(models.subList(index, models.size()));
            } else {
                deviceMapper.updateBatch(models.subList(index, index + batchCount));
            }
            index += batchCount;
        }
    }

    private boolean isUniqueName(DeviceBean bean) {
        Integer id = bean.getId() != null ? bean.getId() : 0;
        return deviceMapper.isUniqueName(id, bean.getDeviceName()) < 1;
    }

    @Override
    public DeviceBean get(Integer id) {
        Device model = deviceMapper.load(id);
        if (model == null)
            return null;
        return new DeviceBean(model);
    }

//     public DeviceBean get(Integer id) {
//     Device model = deviceMapper.load(id);
//     if (model == null) return null;
//     Map<Integer, String> deviceType = CommonConst.getDeviceType();
//     Set<Integer> keySet = deviceType.keySet();
//     keySet.forEach(bean->{
//     if(model.getDeviceType()==bean)
//     model.setDeviceType(deviceType.get(bean));
//    
//     });
//     }
    @Override
    public DeviceBean findByName(String customerName) {
        List<Device> devices = deviceMapper.findByName(customerName);
        if (devices != null && devices.size() > 0)
            return new DeviceBean(devices.get(0));
        return null;
    }

    @Override
    public DeviceBean queryByName(String deviceName) {
        List<Device> devices = deviceMapper.queryByName(deviceName);
        if (devices != null && devices.size() > 0)
            return new DeviceBean(devices.get(0));
        return null;

    }

    @Override
    public void addOrUpdate(DeviceBean bean) {
        deviceMapper.addOrUpdate(bean.getDevice());
    }

    @Override
    public DeviceBean getByIP(String deviceIP) {
        // TODO Auto-generated method stub
        List<Device> devices = deviceMapper.getByIP(deviceIP);
        if (devices != null && devices.size() > 0)
            return new DeviceBean(devices.get(0));
        return null;
    }

    @Override
    public DeviceBean queryByFeatureCode(String featureCode) {
        // TODO Auto-generated method stub
        List<Device> devices = deviceMapper.queryByFeatureCode(featureCode);
        if (devices != null && devices.size() > 0)
            return new DeviceBean(devices.get(0));
        return null;
    }

    @Override
    public List<DeviceBean> parameter(DeviceVO vo) {
        List<DeviceBean> beans = new ArrayList<>();
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<Device> al = deviceMapper.parameter(vo, rb);
        al.stream().forEach((imsiCall) -> {
            beans.add(new DeviceBean(imsiCall));
        });
        return beans;
    }

}
