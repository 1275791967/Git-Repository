package com.irongteng.persistence.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.irongteng.persistence.CustomerAccountVO;
import com.irongteng.persistence.beans.CustomerAccount;

import dwz.dal.BaseMapper;
@Repository
public interface CustomerAccountMapper extends BaseMapper<CustomerAccount, Integer>{
    
 void addBatch(List<CustomerAccount> models);
    
    void addOrUpdate(CustomerAccount model);

    void updateBatchSelective(List<CustomerAccount> models);
    /**
     * 根据phone手机号码段来删除记录
     * @param phone
     */
    void deleteByPhone(@Param("Phone")String Phone);
    
    /**
     * 通过id列表批量删除
     * @param ids
     */
    void deleteBatch(List<Integer> ids);

    /**
     * 通过号码段列表删除号码段
     * @param phoneNOList
     */
    void deleteBatchByPhone(List<String> contactList);
    
    // 查询
    List<CustomerAccount> findPageBreakByCondition(CustomerAccountVO vo, RowBounds rb);
    
    List<CustomerAccount> parameter(CustomerAccountVO vo, RowBounds rb);
    
    Integer findNumberByCondition(CustomerAccountVO vo);
    
    List<String> queryAllPhone();

    List<String> queryAllImsi();
    /**
     * 通过号码查询号码归属地对象
     * @param phone
     * @return
     */
    CustomerAccount queryByPhone(@Param("Phone")String Phone);
    
    CustomerAccount queryByName(@Param("name")String name);
    
    CustomerAccount queryByDeviceIDAndName(@Param("deviceID")Integer deviceID, @Param("name")String name);

    CustomerAccount queryByDeviceID(@Param("deviceID")Integer deviceID);

   // Customer queryByImsi(@Param("imsi")String imsi);
    
    Date getLastRecordTime();

    Integer isUniqueName(@Param("id")Integer id, @Param("name") String name);
    
    void updateStatus(@Param("id") int id, @Param("status") String status);


}
