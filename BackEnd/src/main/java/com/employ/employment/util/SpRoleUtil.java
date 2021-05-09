package com.employ.employment.util;

import com.employ.employment.entity.AjaxError;
import com.employ.employment.entity.AjaxJson;
import com.employ.employment.entity.SpRole;
import com.employ.employment.mapper.SpRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 工具类：SysRole模块
 * @author Zenglr
 *
 */
@Component
public class SpRoleUtil {

	/** 底层Mapper依赖 */
	static SpRoleMapper spRoleMapper;
	@Autowired
	public void setspRoleMapper(SpRoleMapper spRoleMapper) {
		SpRoleUtil.spRoleMapper = spRoleMapper;
	}

	/**
	 * 获取当前会话的roleId
	 */
	public static long getCurrRoleId() {
		return SpAdminUtil.getCurrAdmin().getRoleId();
	}

	/**
	 * 验证一个SysRole对象 是否符合标准
	 * @param s
	 * @return
	 */
	static AjaxJson checkRole(SpRole s) {

		// 1、名称相关
		if(utils.isNull(s.getName())) {
			return AjaxJson.getError("昵称不能为空");
		}
		// 2、如果该名称已存在，并且不是当前角色
		SpRole s2 = spRoleMapper.getByRoleName(s.getName());
		if(s2 != null && s2.getId() != s.getId()) {
			return AjaxJson.getError("昵称与已有角色重复，请更换");
		}

		// 重重检验，最终合格
		return AjaxJson.getSuccess();
	}

	/**
	 * 验证一个Role是否符合标准, 不符合则抛出异常
	 * @param s
	 */
	public static void checkRoleThrow(SpRole s) {
		AjaxJson aj = checkRole(s);
		if(aj.getCode() != AjaxJson.CODE_SUCCESS){
			throw AjaxError.get(aj.getMsg());
		}
	}


}
