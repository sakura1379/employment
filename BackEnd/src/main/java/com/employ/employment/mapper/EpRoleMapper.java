package com.employ.employment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.employ.employment.entity.EpRole;
import com.employ.employment.entity.SoMap;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Mapper: 系统角色表
 * @author Zenglr
 */
@Mapper
@Repository
public interface EpRoleMapper extends BaseMapper<EpRole> {


	/**
	 * 增
	 * @param obj
	 * @return
	 */
	int add(EpRole obj);

	/**
	 * 删
	 * @param id
	 * @return
	 */
	int delete(long id);

	/**
	 * 改
	 * @param obj
	 * @return
	 */
	int update(EpRole obj);

	/**
	 *  查
	 * @param id
	 * @return
	 */
	EpRole getById(long id);

	/**
	 * 查
	 * @param soMap
	 * @return
	 */
	List<EpRole> getList(SoMap soMap);


	/**
	 * 查，根据角色名字
	 * @param name
	 * @return
	 */
	EpRole getByRoleName(String name);

}
