package com.employ.employment.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * Model: company_info -- 企业信息表
 * @author Zenglr 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyInfo implements Serializable {

	// ---------- 模块常量 ----------
	/**
	 * 序列化版本id 
	 */
	private static final long serialVersionUID = 1L;



	// ---------- 表中字段 ----------
	/**
	 * 公司编号 
	 */
	public Long compId;

	/**
	 * 企业名称 
	 */
	public String compName;	

	/**
	 * 企业所在行业 
	 */
	public String compIndustry;	

	/**
	 * 企业规模 (1=100人以下, 2=999人以下, 3=9999人以下, 4=9999人以上)
	 */
	public Integer compSize;

	/**
	 * 企业地址 
	 */
	public String compAddress;	

	/**
	 * 企业官网链接 
	 */
	public String complink;	

	/**
	 * 统一社会信用代码 
	 */
	public String creditcode;	

	/**
	 * 企业成立日期 
	 */
	public Date compEsDate;	

	/**
	 * 企业介绍 
	 */
	public String compIntro;	

	/**
	 * 审核状态 (1=未审核, 2=审核通过, 3=审核不通过) 
	 */
	public Integer approveStatus;





	


}
