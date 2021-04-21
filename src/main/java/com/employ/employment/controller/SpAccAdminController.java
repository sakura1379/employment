package com.employ.employment.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.employ.employment.entity.SoMap;
import com.employ.employment.entity.SpAdmin;
import com.employ.employment.service.SpAccAdminService;
import com.employ.employment.service.SpRolePermissionService;
import com.employ.employment.entity.AjaxJson;
import com.employ.employment.util.SpAdminUtil;
import com.employ.employment.util.SpCfgUtil;
import com.employ.employment.util.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * admin账号相关的接口
 * @author kong
 *
 */
@RestController
@RequestMapping("/AccAdmin/")
public class SpAccAdminController {


	@Autowired
	SpAccAdminService spAccAdminService;

	@Autowired
	SpRolePermissionService spRolePermissionService;


	/** 账号、密码登录  */
	@RequestMapping("doLogin")
	AjaxJson doLogin(String key, String password) {
		// 1、验证参数
		if(utils.isOneNull(key, password)) {
			return AjaxJson.getError("请提供key与password参数");
		}
		return spAccAdminService.doLogin(key, password);
	}


	/** 退出登录  */
	@RequestMapping("doExit")
	AjaxJson doExit() {
		StpUtil.logout();
		return AjaxJson.getSuccess();
	}


	/** 管理员登录后台时需要返回的信息 */
	@RequestMapping("fristOpenAdmin")
	AjaxJson fristOpenAdmin(HttpServletRequest request) {
		// 当前admin
		SpAdmin admin = SpAdminUtil.getCurrAdmin();

		// 组织参数 (admin信息，权限信息，配置信息)
		SoMap map = new SoMap();
		map.set("admin", SpAdminUtil.getCurrAdmin());
		map.set("per_list", spRolePermissionService.getPcodeByRid2(admin.getRoleId()));
		map.set("app_cfg", SpCfgUtil.getAppCfg());
		return AjaxJson.getSuccessData(map);
	}





}
