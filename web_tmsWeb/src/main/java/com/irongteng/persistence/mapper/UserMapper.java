package com.irongteng.persistence.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.irongteng.persistence.BaseConditionVO;
import com.irongteng.persistence.beans.User;

import dwz.dal.BaseMapper;

@Repository
public interface UserMapper extends BaseMapper<User, Integer> {
    
    User findByUsername(String username);
    
    Integer isUniqueUsername(@Param("id") Integer id, @Param("username") String username);
    
    void updateStatus(@Param("id") int id, @Param("status") String userStatus,
            @Param("updateDate") Date updateDate);
    
    // 查询
    List<User> findPageBreakByCondition(BaseConditionVO vo, RowBounds rb);
    
    Integer findNumberByCondition(BaseConditionVO vo);
}
