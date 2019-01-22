package com.irongteng.persistence.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.irongteng.persistence.beans.DeviceRand;
import dwz.dal.BaseMapper;
@Repository
public interface DeviceRandMapper extends BaseMapper<DeviceRand, Integer> {
        
    List<DeviceRand> queryAll();
    
    DeviceRand queryByDeviceID(@Param("deviceID")Integer deviceID);
    
    void deleteByDeviceID(@Param("deviceID")Integer deviceID);
    
//    List<DeviceRand> queryByRandDeviceID(@Param("deviceID")Integer deviceID, @Param("rand")Integer rand);
    
    List<DeviceRand> getRandToDevice();
    
    List<DeviceRand> queryServiceRand(@Param("randV")Integer randV);
    
}