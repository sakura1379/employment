package com.employ.employment.config.token;

import cn.dev33.satoken.stp.StpInterface;
import com.employ.employment.mapper.SpAdminMapper;
import com.employ.employment.service.SpRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自定义权限验证接口扩展
 * @author Zenglr
 *
 */
@Component
public class StpInterfaceImpl implements StpInterface {


	@Autowired
	SpAdminMapper spAdminMapper;

	@Autowired
	SpRolePermissionService spRolePermissionService;


	/** 返回一个账号所拥有的权限码集合  */
	@Override
	public List<String> getPermissionList(Object loginId, String loginKey) {
		if(loginKey.equals("login")) {
			long roleId = spAdminMapper.getById(Long.valueOf(loginId.toString())).getRoleId();
			return spRolePermissionService.getPcodeByRid(roleId);
		}
		return null;
	}

	/** 返回一个账号所拥有的角色标识集合  */
	@Override
	public List<String> getRoleList(Object loginId, String loginKey) {
		return null;
	}

}
