package com.employ.employment.mapper;


import com.employ.employment.entity.SoMap;
import com.employ.employment.entity.StuFavor;
import com.employ.employment.entity.StuMes;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
/**
 * favor_Info
 */


public interface StuFavorMapper {

      /**
     * 增
     * @param jobId
     * @param compId
     * @param stuNum
     * @return 受影响行数
     */
    int add(@Param("jobId")long jobId,@Param("compId")long compId,@Param("stuNum")long stuNum);
    
    /**
     * 删
     * @param favorNum 要删除的数据id
     * @return 受影响行数
     */
    int delete(long favorNum);

    /**
     * 改
     * @param s 实体对象
     * @return 受影响行数
     */
    int update(StuFavor s);

    /**
     * 查 - 根据id
     * @param favorNum 要查询的收藏id
     * @return 实体对象
     */
    StuFavor getById(Long favorNum);

    /**
     * 查集合 - 根据条件（参数为空时代表忽略指定条件）
     * @param stuNum 要查询的学生id
     * @return 数据列表
     */
    List<StuFavor> getList(long stuNum);
    
     /**
     * 查企业编号 -
     * @param jobId 要查询的职位id
     * @return 企业编号
     */
    long getCompId(@Param("jobId")long jobId);
}
