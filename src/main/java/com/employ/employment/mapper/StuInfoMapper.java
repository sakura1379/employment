package com.employ.employment.mapper;

import com.employ.employment.entity.SoMap;
import com.employ.employment.entity.StuInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Mapper: stu_info -- 学生信息表
 * @author Zenglr 
 */

@Mapper
@Repository
public interface StuInfoMapper {

	/**
	 * 增  
	 * @param s 实体对象 
	 * @return 受影响行数 
	 */
	int add(StuInfo s);

	/**
	 * 删  
	 * @param id 要删除的数据id  
	 * @return 受影响行数 
	 */
	int delete(Integer stuNum);	 

	/** 
	 * 改  
	 * @param s 实体对象 
	 * @return 受影响行数 
	 */
	int update(StuInfo s);

	/** 
	 * 查 - 根据id  
	 * @param id 要查询的数据id 
	 * @return 实体对象 
	 */
	StuInfo getById(Integer stuNum);	 

	/**
	 * 查集合 - 根据条件（参数为空时代表忽略指定条件）
	 * @param so 参数集合 
	 * @return 数据列表 
	 */
	List<StuInfo> getList(SoMap so);


}
