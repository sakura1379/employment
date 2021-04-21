package com.employ.employment.mapper;

import com.employ.employment.entity.ApplyInfo;
import com.employ.employment.entity.SoMap;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Mapper: apply_info -- 职位申请表
 * @author Zenglr 
 */

@Mapper
@Repository
public interface ApplyInfoMapper {

	/**
	 * 增  
	 * @param a 实体对象 
	 * @return 受影响行数 
	 */
	int add(ApplyInfo a);

	/**
	 * 删  
	 * @param id 要删除的数据id  
	 * @return 受影响行数 
	 */
	int delete(Integer stuNum);	 

	/** 
	 * 改  
	 * @param a 实体对象 
	 * @return 受影响行数 
	 */
	int update(ApplyInfo a);

	/** 
	 * 查 - 根据id  
	 * @param id 要查询的数据id 
	 * @return 实体对象 
	 */
	ApplyInfo getById(Integer stuNum);	 

	/**
	 * 查集合 - 根据条件（参数为空时代表忽略指定条件）
	 * @param so 参数集合 
	 * @return 数据列表 
	 */
	List<ApplyInfo> getList(SoMap so);


}
