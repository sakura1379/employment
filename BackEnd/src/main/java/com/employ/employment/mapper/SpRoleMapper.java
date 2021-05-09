package com.employ.employment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.employ.employment.entity.SoMap;
import com.employ.employment.entity.SpRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Mapper: 系统角色表
 * @author Zenglr
 */
@Mapper
@Component
public interface SpRoleMapper extends BaseMapper<SpRole> {


	/**
	 * 增
	 * @param obj
	 * @return
	 */
	int add(SpRole obj);

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
	int update(SpRole obj);

	/**
	 *  查
	 * @param id
	 * @return
	 */
	SpRole getById(long id);

	/**
	 * 查
	 * @param soMap
	 * @return
	 */
	List<SpRole> getList(SoMap soMap);


	/**
	 * 查，根据角色名字
	 * @param name
	 * @return
	 */
	SpRole getByRoleName(String name);

}
