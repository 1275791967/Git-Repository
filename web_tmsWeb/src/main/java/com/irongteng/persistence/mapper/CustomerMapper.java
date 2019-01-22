package com.irongteng.persistence.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.irongteng.persistence.CustomerVO;
import com.irongteng.persistence.beans.Customer;

import dwz.dal.BaseMapper;

@Repository
public interface CustomerMapper extends BaseMapper<Customer, Integer> {
  void addBatch(List<Customer> models);
    
    void addOrUpdate(Customer model);

    void updateBatchSelective(List<Customer> models);
    /**
     * 根据contact手机号码段来删除记录
     * @param contact
     */
    void deleteByPhone(@Param("contact")String contact);
    
    /**
     * 通过id列表批量删除
     * @param ids
     */
    void deleteBatch(List<Integer> ids);

    /**
     * 通过号码段列表删除号码段
     * @param contact
     */
    void deleteBatchByPhone(List<String> contactList);
    
    // 查询
    List<Customer> findPageBreakByCondition(CustomerVO vo, RowBounds rb);
    
    List<Customer> parameter(CustomerVO vo, RowBounds rb);
    
    Integer findNumberByCondition(CustomerVO vo);
    
    List<String> queryAllPhone();

    List<String> queryAllImsi();
    /**
     * 通过号码查询号码归属地对象
     * @param contact
     * @return
     */
    Customer queryByPhone(@Param("contact")String contact);
    
    Customer queryByName(@Param("customerName")String customerName);

   // Customer queryByImsi(@Param("imsi")String imsi);
    
    Date getLastRecordTime();

}
