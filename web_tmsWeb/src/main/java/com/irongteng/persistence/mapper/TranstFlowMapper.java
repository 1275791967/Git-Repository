package com.irongteng.persistence.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.irongteng.persistence.TranstFlowVO;
import com.irongteng.persistence.beans.TranstFlow;

import dwz.dal.BaseMapper;
@Repository
public interface TranstFlowMapper extends BaseMapper<TranstFlow, Integer>{
    
    // 查询
    List<TranstFlow> findPageBreakByCondition(TranstFlowVO vo, RowBounds rb);
    
    Integer findNumberByCondition(TranstFlowVO vo);
  
    void deleteBatch(List<Integer> ids);
    
    public List<TranstFlow> getDevice(@Param("deviceID")Integer deviceID);

    List<TranstFlow> getCancelTranslateList(@Param("deviceID")Integer deviceID);
    
    List<TranstFlow> getLastByImsi(@Param("imsi")String imsi);
    
//    void updateTranslateStatusByDeviceID(@Param("deviceID")Integer deviceID);
    
    void updateTranslateStatusByIMSI(@Param("imsi")String imsi, @Param("recordTime")Date recordTime);
    
    List<TranstFlow> getCancelTranslateList1(@Param("deviceID")Integer deviceID);
    
    void updateTranslateStatusBatch(List<TranstFlow> list);
    
    //目前紧手机客户端第三方查询使用
    List<TranstFlow> queryLastByImsiOrPhone(@Param("text")String text);

}
