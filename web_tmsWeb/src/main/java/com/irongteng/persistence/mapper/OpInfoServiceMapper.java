package com.irongteng.persistence.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.irongteng.persistence.OpInfoVO;
import com.irongteng.persistence.beans.OpInfo;

import dwz.dal.BaseMapper;

@Repository
public interface OpInfoServiceMapper extends BaseMapper<OpInfo, Integer>{
    
    OpInfo queryByName(@Param("opName")String opName);
    
    OpInfo queryByNo(@Param("opNo")Integer opNo);
    
    Integer findNumberByCondition(OpInfoVO vo);
    
    List<OpInfo> findPageBreakByCondition(OpInfoVO vo, RowBounds rb);

    @Override
    OpInfo load(Integer id);

    Integer isUniqueOpNo(@Param("id")Integer id, @Param("opNo")String opNo);

}
