package com.irongteng.persistence.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.irongteng.persistence.beans.DeviceOp;

import dwz.dal.BaseMapper;
@Repository
public interface DeviceOpMapper extends BaseMapper<DeviceOp, Integer> {
        
    List<DeviceOp> queryAll();
    
    DeviceOp queryByDeviceID(@Param("deviceID")Integer deviceID);
    
    void deleteByDeviceID(@Param("deviceID")Integer deviceID);
    
    List<DeviceOp> queryByOp(@Param("op")Integer op);

}