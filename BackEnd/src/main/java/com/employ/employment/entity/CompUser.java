package com.employ.employment.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Model: comp_user -- 
 * @author Zenglr 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompUser implements Serializable {

	// ---------- 模块常量 ----------
	/**
	 * 序列化版本id 
	 */
	private static final long serialVersionUID = 1L;


	// ---------- 表中字段 ----------
	/**
	 * hr编号 
	 */
	public Long hrId;

	/**
	 * 企业编号 
	 */
	public Long compId;





	


}
