package com.employ.employment.mapper;

import com.employ.employment.entity.CompanyInfo;
import com.employ.employment.entity.SoMap;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Mapper: company_info -- 企业信息表
 * @author Zenglr 
 */

@Mapper
@Repository
public interface CompanyInfoMapper {

	/**
	 * 增  
	 * @param c 实体对象 
	 * @return 受影响行数 
	 */
	int add(CompanyInfo c);

	/**
	 * 删  
	 * @param id 要删除的数据id  
	 * @return 受影响行数 
	 */
	int delete(Integer compId);	 

	/** 
	 * 改  
	 * @param c 实体对象 
	 * @return 受影响行数 
	 */
	int update(CompanyInfo c);

	/** 
	 * 查 - 根据id  
	 * @param id 要查询的数据id 
	 * @return 实体对象 
	 */
	CompanyInfo getById(Integer compId);	 

	/**
	 * 查集合 - 根据条件（参数为空时代表忽略指定条件）
	 * @param so 参数集合 
	 * @return 数据列表 
	 */
	List<CompanyInfo> getList(SoMap so);


}
