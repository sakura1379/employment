package com.employ.employment.service;

import com.employ.employment.entity.JobInfo;
import com.employ.employment.entity.SoMap;
import com.employ.employment.mapper.JobInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service: job_info -- 职位信息表
 * @author Zenglr 
 */
@Service
public class JobInfoService {

	/** 底层 Mapper 对象 */
	@Autowired
	JobInfoMapper jobInfoMapper;

	/** 增 */
	public int add(JobInfo j){
		return jobInfoMapper.add(j);
	}

	/** 删 */
	public int delete(Integer jobId){
		return jobInfoMapper.delete(jobId);
	}

	/** 改 */
	public int update(JobInfo j){
		return jobInfoMapper.update(j);
	}

	/** 查
	 * @param jobId*/
	public JobInfo getById(long jobId){
		return jobInfoMapper.getById((int) jobId);
	}

	/** 查集合 - 根据条件（参数为空时代表忽略指定条件） */
	public List<JobInfo> getList(SoMap so) {
		return jobInfoMapper.getList(so);	
	}
	

}
