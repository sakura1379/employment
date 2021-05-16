package com.employ.employment.mapper;

import com.employ.employment.entity.CompUser;
import com.employ.employment.entity.SoMap;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Mapper: comp_user -- 
 * @author Zenglr 
 */

@Mapper
@Repository
public interface CompUserMapper {

	/**
	 * 增  
	 * @param c 实体对象 
	 * @return 受影响行数 
	 */
	int add(CompUser c);

	/**
	 * 删  
	 * @param hrId 要删除的数据id
	 * @return 受影响行数 
	 */
	int delete(Long hrId);

//	/**
//	 * 改
//	 * @param c 实体对象
//	 * @return 受影响行数
//	 */
//	int update(CompUser c);

	/** 
	 * 查 - 根据id  
	 * @param hrId 要查询的数据id
	 * @return 实体对象 
	 */
	CompUser getById(Long hrId);

	/**
	 * 查集合 - 根据条件（参数为空时代表忽略指定条件）
	 * @param so 参数集合 
	 * @return 数据列表 
	 */
	List<CompUser> getList(SoMap so);


}
