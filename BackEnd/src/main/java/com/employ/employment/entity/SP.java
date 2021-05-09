package com.employ.employment.entity;

import com.employ.employment.mapper.PublicMapper;
import com.employ.employment.service.PublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 公共Mapper 与 公共Service
 * @author Zenglr
 *
 */
@Component
public class SP {


	/**
	 * 公共Mapper
	 */
	public static PublicMapper publicMapper;
	/**
	 * 公共Service
	 */
	public static PublicService publicService;

	// 注入
	@Autowired
	public void setBean(
			PublicMapper publicMapper,
			PublicService publicService
			) {
		SP.publicMapper = publicMapper;
		SP.publicService = publicService;
	}




}
