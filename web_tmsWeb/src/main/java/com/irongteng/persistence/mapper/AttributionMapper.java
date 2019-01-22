package com.irongteng.persistence.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.irongteng.persistence.BaseConditionVO;
import com.irongteng.persistence.beans.Attribution;

import dwz.dal.BaseMapper;

@Repository
public interface AttributionMapper extends BaseMapper<Attribution, Integer> {

    Integer isExistPhoneNO(@Param("id") Integer id, @Param("phoneNO") Integer phoneNO);

    void addBatch(List<Attribution> models);

    void updateBatchSelective(List<Attribution> models);

    /**
     * 根据phoneNO手机号码段来删除记录
     * 
     * @param phoneNO
     */
    void deleteByPhoneNO(@Param("phoneNO") Integer phoneNO);

    /**
     * 通过id列表批量删除
     * 
     * @param phoneNOList
     */
    void deleteBatch(List<Integer> ids);

    /**
     * 通过号码段列表删除号码段
     * 
     * @param phoneNOList
     */
    void deleteBatchByPhoneNO(List<Integer> phoneNoList);

    // 查询
    List<Attribution> findPageBreakByCondition(BaseConditionVO vo, RowBounds rb);

    Integer findNumberByCondition(BaseConditionVO vo);

    List<Integer> queryAllPhoneNO();

    /**
     * 通过号码查询号码归属地对象
     * 
     * @param phoneNO
     * @return
     */
    Attribution queryByPhoneNO(@Param("phoneNO") Integer phoneNO);

    List<String> findProvinces();

    List<String> findCitys(String province);

    List<Attribution> findCondition(Attribution model);

    List<String> queryAreaByCellNo(@Param("cellNo") Integer cellNo);
}
