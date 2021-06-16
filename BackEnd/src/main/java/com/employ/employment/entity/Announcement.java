package com.employ.employment.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Model: Announcement --
 * @author Zenglr 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Announcement implements Serializable {

	// ---------- 模块常量 ----------
	/**
	 * 序列化版本id 
	 */
	private static final long serialVersionUID = 1L;


	// ---------- 表中字段 ----------
	/**
	 * 公告编号
	 */
	public Long announceId;

	/**
	 * 公告标题
	 */
	public String announceTitle;

	/**
	 * 公告内容
	 */
	public String announceContent;

	/**
	 * 公告类型
	 */
	public Integer announceType;

	/**
	 * 发布时间
	 */
	@ApiModelProperty(hidden = true)
	public Date announceTime;

	/**
	 * 管理员编号
	 */
	public Long adminId;




	


}
