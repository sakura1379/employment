package com.employ.employment.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Model: apply_info -- 职位申请表
 * @author Zenglr 
 */
@Data
@Accessors(chain = true)
public class ApplyInfo implements Serializable {

	// ---------- 模块常量 ----------
	/**
	 * 序列化版本id 
	 */
	private static final long serialVersionUID = 1L;	
	/**
	 * 此模块对应的表名 
	 */
	public static final String TABLE_NAME = "apply_info";	
	/**
	 * 此模块对应的权限码 
	 */
	public static final String PERMISSION_CODE = "apply-info";	


	// ---------- 表中字段 ----------
	/**
	 * 学生编号 
	 */
	public Integer stuNum;	

	/**
	 * 职位信息编号 
	 */
	public Integer jobId;	

	/**
	 * 一周可实习时间 (1=一天, 2=两天, 3=三天, 4=四天, 5=五天, 6=六天) 
	 */
	public Integer internshipTime;	

	/**
	 * 最快到岗时间 (1=一周内, 2=两周内, 3=一个月内, 4=三个月内) 
	 */
	public Integer dutyTime;	

	/**
	 * 申请状态 (1=简历待筛选, 2=简历未通过, 3=一面, 4=二面, 5=HR面, 6=HR面, 7=录用评估中, 8=录用意向, 9=已录用) 
	 */
	public Integer applyStatus;	





	


}
