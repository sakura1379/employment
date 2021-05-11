package com.employ.employment.mapper;

import com.employ.employment.entity.SoMap;
import com.employ.employment.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Mapper: 系统管理员表
 */
@Mapper
@Component
public interface UserInfoMapper {


	/**
	 * 增 #{name}, #{password}, #{roleId}
	 * @param obj
	 * @return
	 */
	int add(UserInfo obj);

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
	int update(UserInfo obj);

	/**
	 * 查
	 * @param id
	 * @return
	 */
	UserInfo getById(long id);

	/**
	 * 查
	 * @param so
	 * @return
	 */
	List<UserInfo> getList(SoMap so);

	/**
	 * 查询，根据name
	 * @param name
	 * @return
	 */
	UserInfo getByName(String name);

	/**
	 * 查询，根据 phone
	 * @param phone
	 * 没用了
	 * @return
	 */
//	SpAdmin getByPhone(String phone);

	/**
	 * 查询，根据 email
	 * @param mail
	 * @return
	 */
	UserInfo getByEmail(String mail);

}
