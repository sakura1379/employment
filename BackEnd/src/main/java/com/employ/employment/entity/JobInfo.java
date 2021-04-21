package com.employ.employment.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * Model: job_info -- 职位信息表
 * @author Zenglr 
 */
@Data
@Accessors(chain = true)
public class JobInfo implements Serializable {

	// ---------- 模块常量 ----------
	/**
	 * 序列化版本id 
	 */
	private static final long serialVersionUID = 1L;	
	/**
	 * 此模块对应的表名 
	 */
	public static final String TABLE_NAME = "job_info";	
	/**
	 * 此模块对应的权限码 
	 */
	public static final String PERMISSION_CODE = "job-info";	


	// ---------- 表中字段 ----------
	/**
	 * 职位信息编号 
	 */
	public Integer jobId;	

	/**
	 * 公司编号 
	 */
	public String compId;	

	/**
	 * 职位名称 
	 */
	public String jobName;	

	/**
	 * 岗位类别 
	 */
	public String jobType;	

	/**
	 * 招聘性质 (1=实习, 2=校招, 3=实习和校招) 
	 */
	public Integer jobKind;	

	/**
	 * 招聘状态 (1=招聘中, 2=已结束) 
	 */
	public Integer status;	

	/**
	 * 发布日期 
	 */
	public Date relDate;	

	/**
	 * 工作地点 
	 */
	public String jobAddress;	

	/**
	 * 职位描述 
	 */
	public String jobCon;	

	/**
	 * 截止日期 
	 */
	public Date jobDeadline;	

	/**
	 * 已投递人数 
	 */
	public Integer deliverNum;	

	/**
	 * 薪资 
	 */
	public String salary;	

	/**
	 * 审核状态 (1=未审核, 2=审核通过, 3=审核不通过) 
	 */
	public Integer approveStatus;	





	


}
