package com.employ.employment.mapper;

import com.employ.employment.entity.SoMap;
import com.employ.employment.entity.ApplyInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 *
 * @ProjectName: employment
 * @Description: [职位申请表]
 * @Author: [Le]
 **/

@Mapper
@Repository
public interface ApplyInfoMapper{

    /**
     * 增
     * @param a 实体对象
     * @return 受影响行数
     */
    int add(ApplyInfo a);

    /**
     * 删
     * @param jobId 根据jobId删除
     * @return 受影响行数
     */
    int deleteByJobId(long jobId);

    /**
     * 删
     * @param stuNum 根据stuNum删除
     * @return 受影响行数
     */
    int deleteByStuNum(long stuNum);

    /**
     * 删
     * @param stuNum jobId 根据stuNum和jobId删除
     * @return 受影响行数
     */
    int deleteByAppId(long stuNum, long jobId);
    /**
     * 改
     * @param a 实体对象
     * @return 受影响行数
     */
    int update(ApplyInfo a);

    /**
     * 查 - 根据id
     * @param stuNum 根据学生编号查
     * @return 实体对象
     */
    ApplyInfo getByStuNum(long stuNum);

    /**
     * 查 - 根据id
     * @param jobId 根据职位编号查
     * @return 实体对象
     */
    ApplyInfo getByJobId(long jobId);

    /**
     * 查 - 根据id
     * @param jobId stuNum根据职位编号查
     * @return 实体对象
     */
    ApplyInfo getByAppId(long stuNum, long jobId);

    /**
     * 查集合 - 根据条件（参数为空时代表忽略指定条件）
     * @param apl 参数集合
     * @return 数据列表
     */
    List<ApplyInfo> getList(SoMap apl);

    /**
     * 根据所有申请的jobid查集合
     * @param jobIds, stuNum
     * @return 数据列表
     */
    List<ApplyInfo> selectAllApplyByJobIds(@Param("jobIds") List<String> jobIds);
//    List<String> selectAllApplyByJobIds(@Param("jobIds") List<String> jobIds);

    /**
     * 根据所有申请的jobid, stuNum查集合
     * @param jobIds, stuNum
     * @return 数据列表
     */
    List<ApplyInfo> selectAllApplyByAppIds(@Param("jobIds") List<String> jobIds, @Param("stuNum") long stuNum);
}
