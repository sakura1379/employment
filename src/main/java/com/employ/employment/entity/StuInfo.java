package com.employ.employment.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * Model: stu_info -- 学生信息表
 * @author Zenglr 
 */
@Data
@Accessors(chain = true)
public class StuInfo implements Serializable {

	// ---------- 模块常量 ----------
	/**
	 * 序列化版本id 
	 */
	private static final long serialVersionUID = 1L;	
	/**
	 * 此模块对应的表名 
	 */
	public static final String TABLE_NAME = "stu_info";	
	/**
	 * 此模块对应的权限码 
	 */
	public static final String PERMISSION_CODE = "stu-info";	


	// ---------- 表中字段 ----------
	/**
	 * 学生id 
	 */
	public Integer stuNum;	

	/**
	 * 学生姓名 
	 */
	public String stuName;	

	/**
	 * 学生头像 
	 */
	public String avatar;	

	/**
	 * 学生学校 
	 */
	public String stuGraUniversity;	

	/**
	 * 学生专业 
	 */
	public String stuMajor;	

	/**
	 * 学生学历 (1=本科, 2=研究生) 
	 */
	public Integer stuEducation;	

	/**
	 * 学生学历 (1=实习, 2=校招, 3=实习和校招) 
	 */
	public Integer stJodKind;	

	/**
	 * 学生毕业年份 
	 */
	public Date stuGraduateTime;	

	/**
	 * 学生邮箱 
	 */
	public String stuEmail;	

	/**
	 * 学生电话号码 
	 */
	public String stuTelephone;	

	/**
	 * 期望城市 
	 */
	public String dreamAddress;	

	/**
	 * 期望职位类别 
	 */
	public String dreamPosition;	

	/**
	 * 简历信息 
	 */
	public String resume;	





	


}
