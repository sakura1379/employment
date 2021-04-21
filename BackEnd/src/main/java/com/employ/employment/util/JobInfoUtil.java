package com.employ.employment.util;

import com.employ.employment.entity.AjaxError;
import com.employ.employment.entity.JobInfo;
import com.employ.employment.mapper.JobInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 工具类：job_info -- 职位信息表
 * @author Zenglr 
 *
 */
@Component
public class JobInfoUtil {

	
	/** 底层 Mapper 对象 */
	public static JobInfoMapper jobInfoMapper;
	@Autowired
	private void setJobInfoMapper(JobInfoMapper jobInfoMapper) {
		JobInfoUtil.jobInfoMapper = jobInfoMapper;
	}
	
	
	/** 
	 * 将一个 JobInfo 对象进行进行数据完整性校验 (方便add/update等接口数据校验) [G] 
	 */
	static void check(JobInfo j) {
		AjaxError.throwByIsNull(j.jobId, "[职位信息编号] 不能为空");		// 验证: 职位信息编号
		AjaxError.throwByIsNull(j.compId, "[公司编号] 不能为空");		// 验证: 公司编号 
		AjaxError.throwByIsNull(j.jobName, "[职位名称] 不能为空");		// 验证: 职位名称 
		AjaxError.throwByIsNull(j.jobType, "[岗位类别] 不能为空");		// 验证: 岗位类别 
		AjaxError.throwByIsNull(j.jobKind, "[招聘性质] 不能为空");		// 验证: 招聘性质 (1=实习, 2=校招, 3=实习和校招) 
		AjaxError.throwByIsNull(j.status, "[招聘状态] 不能为空");		// 验证: 招聘状态 (1=招聘中, 2=已结束) 
		AjaxError.throwByIsNull(j.relDate, "[发布日期] 不能为空");		// 验证: 发布日期 
		AjaxError.throwByIsNull(j.jobAddress, "[工作地点] 不能为空");		// 验证: 工作地点 
		AjaxError.throwByIsNull(j.jobCon, "[职位描述] 不能为空");		// 验证: 职位描述 
		AjaxError.throwByIsNull(j.jobDeadline, "[截止日期] 不能为空");		// 验证: 截止日期 
		AjaxError.throwByIsNull(j.deliverNum, "[已投递人数] 不能为空");		// 验证: 已投递人数 
		AjaxError.throwByIsNull(j.salary, "[薪资] 不能为空");		// 验证: 薪资 
		AjaxError.throwByIsNull(j.approveStatus, "[审核状态] 不能为空");		// 验证: 审核状态 (1=未审核, 2=审核通过, 3=审核不通过) 
	}

	/** 
	 * 获取一个JobInfo (方便复制代码用) [G] 
	 */ 
	static JobInfo getJobInfo() {
		JobInfo j = new JobInfo();	// 声明对象 
		j.jobId = 0;		// 职位信息编号 
		j.compId = "";		// 公司编号 
		j.jobName = "";		// 职位名称 
		j.jobType = "";		// 岗位类别 
		j.jobKind = 0;		// 招聘性质 (1=实习, 2=校招, 3=实习和校招) 
		j.status = 0;		// 招聘状态 (1=招聘中, 2=已结束) 
		j.relDate = new Date();		// 发布日期 
		j.jobAddress = "";		// 工作地点 
		j.jobCon = "";		// 职位描述 
		j.jobDeadline = new Date();		// 截止日期 
		j.deliverNum = 0;		// 已投递人数 
		j.salary = "";		// 薪资 
		j.approveStatus = 0;		// 审核状态 (1=未审核, 2=审核通过, 3=审核不通过) 
		return j;
	}
	
	
	
	
	
}
