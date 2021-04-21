package com.employ.employment.mapper;

import com.employ.employment.entity.JobInfo;
import com.employ.employment.entity.SoMap;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Mapper: job_info -- 职位信息表
 * @author Zenglr 
 */

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
	 * @param id 要删除的数据id  
	 * @return 受影响行数 
	 */
	int delete(Integer jobId);	 

	/** 
	 * 改  
	 * @param j 实体对象 
	 * @return 受影响行数 
	 */
	int update(JobInfo j);

	/** 
	 * 查 - 根据id  
	 * @param id 要查询的数据id 
	 * @return 实体对象 
	 */
	JobInfo getById(Integer jobId);	 

	/**
	 * 查集合 - 根据条件（参数为空时代表忽略指定条件）
	 * @param so 参数集合 
	 * @return 数据列表 
	 */
	List<JobInfo> getList(SoMap so);


}
