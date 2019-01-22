package com.irongteng.persistence.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.irongteng.persistence.BaseConditionVO;
import com.irongteng.persistence.beans.UserLogger;


import dwz.dal.BaseMapper;

@Repository
public interface UserLoggerMapper extends BaseMapper<UserLogger, Integer>{

    UserLogger findByLoginHostName(String loginHostName);
    
    Integer isUniqueUserLoginHostName(@Param("id") Integer id, @Param("loginHostName") String LoginHostName);
    
    void updateLoginStatus(UserLogger model);
    
    // 查询
    List<UserLogger> findPageBreakByCondition(BaseConditionVO vo, RowBounds rb);
    
    Integer findNumberByCondition(BaseConditionVO vo);
}
