package com.irongteng.persistence.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.irongteng.persistence.beans.LoadOP;
import dwz.dal.BaseMapper;
@Repository 
public interface LoadOPMapper extends BaseMapper<LoadOP, Integer>{
    
    List<LoadOP> getRatio(@Param("opNo")Integer opNo);
    
    List<LoadOP> getCDMARatio(@Param("opNo")Integer opNo, @Param("rand")Integer rand);
    
    LoadOP get(@Param("deviceID")Integer deviceID, @Param("opNo")Integer opNo);
    
    List<LoadOP> queryAll();
    
    Integer getCountByOP( @Param("opNo")Integer opNo);
    
    List<LoadOP> queryByDevice( @Param("deviceID")Integer deviceID);
    
    void updateRitio(LoadOP model);
    
    void clear();
    
    void deleteByDeviceID(@Param("deviceID")Integer deviceID);
    
    List<LoadOP> queryByOp( @Param("opNo")Integer opNo);
    
}
