package com.employ.employment.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * 账号相关
 * @author Zenglr
 *
 */
@Mapper
@Repository
public interface EpAccAdminMapper {

	/**
	 * 指定id的账号成功登录一次
	 * @param id
	 * @param loginIp
	 * @return
	 */
	int successLogin(@Param("id") long id, @Param("loginIp") String loginIp);

}
