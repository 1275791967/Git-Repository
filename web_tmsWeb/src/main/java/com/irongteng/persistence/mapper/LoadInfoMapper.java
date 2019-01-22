package com.irongteng.persistence.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.irongteng.persistence.LoadInfoVO;
import com.irongteng.persistence.beans.LoadInfo;
import com.irongteng.persistence.beans.TranstFlow;
import dwz.dal.BaseMapper;
@Repository 
public interface LoadInfoMapper extends BaseMapper<LoadInfo, Integer>{
    
    // 查询
    List<LoadInfo> findPageBreakByCondition(LoadInfoVO vo, RowBounds rb);
    
    Integer findNumberByCondition(LoadInfoVO vo);

    List<LoadInfo> findPage(LoadInfoVO vo);
    
    List<LoadInfo> getDevice(LoadInfoVO vo);
    //通过deviceID 获取对应的数据
    TranstFlow analyzeTranslateResult(@Param("deviceID")Integer deviceID);
    
    LoadInfo queryByDeviceID(@Param("deviceID")int deviceID);
    
    List<LoadInfo> channelOneTranslateRatio();
    
    List<LoadInfo> channelTwoTranslateRatio();
    
    List<LoadInfo> channeThreeTranslateRatio();
    
    List<LoadInfo> channeFourTranslateRatio();
    
    List<LoadInfo> channeFiveTranslateRatio();

    Integer isUniqueID(@Param("id")Integer id, @Param("deviceID") Integer deviceID);
 
}
