package com.irongteng.persistence.mapper;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.irongteng.persistence.CommLogVO;
import com.irongteng.persistence.beans.CommLog;

import dwz.dal.BaseMapper;
@Repository
public interface CommLogMapper extends BaseMapper<CommLog, Integer>{
   
    
    // 查询
    List<CommLog> findPageBreakByCondition(CommLogVO vo, RowBounds rb);
    
    Integer findNumberByCondition(CommLogVO vo);
    
    

}
