package com.irongteng.persistence.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.irongteng.persistence.DeviceVO;
import com.irongteng.persistence.beans.Device;

import dwz.dal.BaseMapper;
@Repository
public interface DeviceMapper extends BaseMapper<Device, Integer> {
        
    void addBatch(List<Device> models);

    void updateBatchSelective(List<Device> models);
    
    /**
     * 通过id列表批量删除
     * @param ids
     */
    void deleteBatch(List<Integer> ids);

    Integer isUniqueName(@Param("id") Integer id, @Param("deviceName") String deviceName);
    // 查询
    List<Device> findPageBreakByCondition(DeviceVO vo, RowBounds rb);
    
    List<Device> parameter(DeviceVO vo, RowBounds rb);
    
    Integer findNumberByCondition(DeviceVO vo);

    List<String> queryAllImsi();
  
    List<Device> findByName(@Param("customerName")String customerName);

    List<Device> getByIP(@Param("deviceIP")String deviceName);
    
    List<Device> queryByName(@Param("deviceName")String deviceName);
    
    List<Device> queryByFeatureCode(@Param("featureCode")String featureCode);

   // Customer queryByImsi(@Param("imsi")String imsi);
    
    Date getLastRecordTime();

    List<String> inquiry(DeviceVO vo);

    List<Device> findCommonByCondition(DeviceVO vo);

    void updateBatch(List<Device> models);
    void addOrUpdate(Device model);

    Integer isUniqueFeatureCode(@Param("id") Integer id,  @Param("featureCode") String featureCode);




}