package com.employ.employment.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Mapper: 角色权限中间表
 * @author kong
 */
@Mapper
public interface SpRolePermissionMapper {


	/**
	 * 增
	 * @param roleId
	 * @param pcode
	 * @return
	 */
	int add(@Param("roleId") long roleId, @Param("pcode") String pcode);


	/**
	 * 删除指定角色的所有权限
	 * @param roleId
	 * @return
	 */
	int deleteByRoleId(long roleId);


	/**
	 * 指定roleId的所有权限码
	 * @param roleId
	 * @return
	 */
	List<String> getPcodeByRoleId(long roleId);



}
