package com.irongteng.persistence.mapper;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.irongteng.persistence.BaseConditionVO;
import com.irongteng.persistence.beans.LogType;

import dwz.dal.BaseMapper;

@Repository
public interface LogTypeMapper extends BaseMapper<LogType, Integer>{

    // 查询
    List<LogType> findPageBreakByCondition(BaseConditionVO vo, RowBounds rb);
    
    Integer findNumberByCondition(BaseConditionVO vo);


}
