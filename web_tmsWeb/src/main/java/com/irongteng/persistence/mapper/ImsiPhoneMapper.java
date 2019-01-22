package com.irongteng.persistence.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.irongteng.persistence.BaseConditionVO;
import com.irongteng.persistence.beans.ImsiPhone;
import com.irongteng.service.ImsiPhoneBean;

import dwz.dal.BaseMapper;

@Repository
public interface ImsiPhoneMapper extends BaseMapper<ImsiPhone, Integer> {
    
    Integer isExistPhone(@Param("id") Integer id, @Param("phoneNO")String phone);
    
    Integer isExistImsi(@Param("id") Integer id, @Param("imsi")String phone);
    
    void addBatch(List<ImsiPhone> models);
    
    void addOrUpdate(ImsiPhone model);

    void updateBatchSelective(List<ImsiPhone> models);
    /**
     * 根据phoneNO手机号码段来删除记录
     * @param phoneNO
     */
    void deleteByPhone(@Param("phoneNO")String phone);
    /**
     * 通过id列表批量删除
     * @param ids
     */
    void deleteBatch(List<Integer> ids);

    /**
     * 通过号码段列表删除号码段
     * @param phoneNOList
     */
    void deleteBatchByPhone(List<String> phoneList);
    
    // 查询
    List<ImsiPhone> findPageBreakByCondition(BaseConditionVO vo, RowBounds rb);
    
    Integer findNumberByCondition(BaseConditionVO vo);
    
    Integer searchStatusNum (ImsiPhoneBean bean);
    
    List<String> queryAllPhone();

    List<String> queryAllImsi();
    /**
     * 通过号码查询号码归属地对象
     * @param phoneNO
     * @return
     */
    ImsiPhone queryByPhone(@Param("phone")String phone);

    ImsiPhone queryByImsi(@Param("imsi")String imsi);
    
    Date getLastRecordTime();
}
