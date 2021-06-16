package com.employ.employment.mapper;

import com.employ.employment.entity.Announcement;
import com.employ.employment.entity.SoMap;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Mapper: announcement -- 公告信息表
 * @author Liana
 */

@Mapper
@Repository
public interface AnnouncementMapper {

	/**
	 * 增  
	 * @param a 实体对象
	 * @return 受影响行数 
	 */
	int add(Announcement a);

	/**
	 * 删  
	 * @param id 要删除的数据id
	 * @return 受影响行数 
	 */
	int delete(long id);

	/** 
	 * 改  
	 * @param a 实体对象
	 * @return 受影响行数 
	 */
	int update(Announcement a);

	/** 
	 * 查 - 根据id  
	 * @param id 要查询的数据id
	 * @return 实体对象 
	 */
	Announcement getById(long id);

	/**
	 * 查集合 - 根据条件（参数为空时代表忽略指定条件）
	 * @param a 参数集合
	 * @return 数据列表 
	 */
	List<Announcement> getList(SoMap a);


}
