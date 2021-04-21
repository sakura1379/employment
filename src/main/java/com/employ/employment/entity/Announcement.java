package com.employ.employment.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * Model: announcement -- 
 * @author Zenglr 
 */
@Data
@Accessors(chain = true)
public class Announcement implements Serializable {

	// ---------- 模块常量 ----------
	/**
	 * 序列化版本id 
	 */
	private static final long serialVersionUID = 1L;	
	/**
	 * 此模块对应的表名 
	 */
	public static final String TABLE_NAME = "announcement";	
	/**
	 * 此模块对应的权限码 
	 */
	public static final String PERMISSION_CODE = "announcement";	


	// ---------- 表中字段 ----------
	/**
	 * 公告编号 
	 */
	public Integer announceId;	

	/**
	 * 公告标题 
	 */
	public String announceTitle;	

	/**
	 * 公告内容 
	 */
	public String announceContent;	

	/**
	 * 公告类型 (1=系统公告, 2=经验分享, 3=宣讲会信息) 
	 */
	public Integer announceType;	

	/**
	 * 发布时间 
	 */
	public Date announceTime;	

	/**
	 * 管理员编号 
	 */
	public Integer adminId;	





	


}
