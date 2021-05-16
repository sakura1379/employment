package com.employ.employment.mapper;

import com.employ.employment.entity.SeminarInfo;
import com.employ.employment.entity.SoMap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Mapper: seminar_info -- 
 * @author Zenglr 
 */

@Mapper
@Repository
public interface SeminarInfoMapper {

	/**
	 * 增  
	 * @param s 实体对象 
	 * @return 受影响行数 
	 */
	int add(SeminarInfo s);

	/**
	 * 删  
	 * @param seminarId 要删除的数据id
	 * @return 受影响行数 
	 */
	int delete(Long seminarId);

	/** 
	 * 改  
	 * @param s 实体对象 
	 * @return 受影响行数 
	 */
	int update(SeminarInfo s);

	/** 
	 * 查 - 根据id  
	 * @param seminarId 要查询的数据id
	 * @return 实体对象 
	 */
	SeminarInfo getById(Long seminarId);

	/**
	 * 查集合 - 根据条件（参数为空时代表忽略指定条件）
	 * @param so 参数集合 
	 * @return 数据列表 
	 */
	List<SeminarInfo> getList(SoMap so);


	/**
	 * 根据所有SeminarIds查集合
	 * @param seminarIds
	 * @return 数据列表
	 */
	List<SeminarInfo> selectAllSeminarBySeminarIds(@Param("seminarIds") List<String> seminarIds);


	/**
	 * 根据所有SeminarIds查已通过审核的集合【approveStatus = 2】
	 * @param seminarIds
	 * @return 数据列表
	 */
	List<SeminarInfo> selectSeminarBySeminarIds(@Param("seminarIds") List<String> seminarIds, @Param("sortType") int sortType);
}
