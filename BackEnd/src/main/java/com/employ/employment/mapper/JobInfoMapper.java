package com.employ.employment.mapper;

import com.employ.employment.entity.SeminarInfo;
import com.employ.employment.entity.SoMap;
import com.employ.employment.entity.JobInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 *
 * @ProjectName: employment
 * @Mapper: job_info
 * @Description: [职位信息表]
 * @Author: [clh]
 **/
@Mapper
@Repository
public interface JobInfoMapper {
    /**
     * 增
     * @param j 实体对象
     * @return 受影响行数
     */
    int add(JobInfo j);

    /**
     * 删
     * @param jobId 要删除的数据id
     * @return 受影响行数
     */
    int delete(long jobId);

    /**
     * 改
     * @param j 实体对象
     * @return 受影响行数
     */
    int update(JobInfo j);

    /**
     * 查 - 根据id
     * @param jobId 要查询的数据id
     * @return 实体对象
     */
    JobInfo getById(long jobId);

    /**
     * 查集合 - 根据条件（参数为空时代表忽略指定条件）
     * @param job 参数集合
     * @return 数据列表
     */
    List<JobInfo> getList(SoMap job);

    /**
     * 根据所有jobIds查集合
     * @param jobIds
     * @return 数据列表
     */
    List<JobInfo> selectAllJobByJobIds(@Param("jobIds") List<String> jobIds);

    /**
     * 根据所有jobIds查已通过审核的集合【approveStatus = 2】
     * @param jobIds
     * @return 数据列表
     */
    List<JobInfo> selectJobByJobIds(@Param("jobIds") List<String> jobIds, @Param("sortType") int sortType);

    /**
     * 获取所有未审核的职位信息
     * @return
     */
    List<JobInfo> selectReviewJobs(SoMap job);
}
