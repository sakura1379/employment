package com.employ.employment.config.token;

import cn.dev33.satoken.stp.StpInterface;
import com.employ.employment.mapper.EpRoleMapper;
import com.employ.employment.mapper.UserInfoMapper;
import com.employ.employment.service.EpRolePermissionService;
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


	private final EpRolePermissionService epRolePermissionService;

	private final UserInfoMapper userInfoMapper;

	@Autowired
	public StpInterfaceImpl(EpRolePermissionService epRolePermissionService, UserInfoMapper userInfoMapper) {
		this.epRolePermissionService = epRolePermissionService;
		this.userInfoMapper = userInfoMapper;
	}


	/** 返回一个账号所拥有的权限码集合  */
	@Override
	public List<String> getPermissionList(Object loginId, String loginKey) {
		if(loginKey.equals("login")) {
			long roleId = userInfoMapper.getById(Long.valueOf(loginId.toString())).getRoleId();
			return epRolePermissionService.getPcodeByRid(roleId);
		}
		return null;
	}

	/** 返回一个账号所拥有的角色标识集合  */
	@Override
	public List<String> getRoleList(Object loginId, String loginKey) {
		return null;
	}

}
