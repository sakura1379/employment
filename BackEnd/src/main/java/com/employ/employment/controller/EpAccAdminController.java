package com.employ.employment.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.employ.employment.entity.SoMap;
import com.employ.employment.entity.UserInfo;
import com.employ.employment.entity.AjaxJson;
import com.employ.employment.service.EpAccAdminService;
import com.employ.employment.service.EpRolePermissionService;
import com.employ.employment.util.UserInfoUtil;
import com.employ.employment.util.utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * admin账号相关的接口
 * @author Zenglr
 *
 */
@RestController
@RequestMapping("/AccAdmin/")
@Api
@Slf4j
@CrossOrigin
public class EpAccAdminController {

	private final EpAccAdminService epAccAdminService;

	private final EpRolePermissionService epRolePermissionService;

	@Autowired
	public EpAccAdminController(EpAccAdminService epAccAdminService, EpRolePermissionService epRolePermissionService) {
		this.epAccAdminService = epAccAdminService;
		this.epRolePermissionService = epRolePermissionService;
	}


	/** 账号、密码登录  */
	@PostMapping("doLogin")
	@ApiOperation("账号密码登录")
	AjaxJson doLogin(String key, String password) {
		log.info("Start login=============");
		log.info("Receive key: {}, password: {}", key, password);
		// 1、验证参数
		if(utils.isOneNull(key, password)) {
			return AjaxJson.getError("请提供key与password参数");
		}
		return epAccAdminService.doLogin(key, password);
	}


	/** 退出登录  */
	@GetMapping("doExit")
	@ApiOperation("退出登录")
	AjaxJson doExit() {
		log.info("Start ExitLogin=======");
		StpUtil.logout();
		return AjaxJson.getSuccess();
	}


	/** 管理员登录后台时需要返回的信息 */
	@GetMapping("firstOpenAdmin")
	@ApiOperation("管理员登录后台时需要返回的信息")
	AjaxJson fristOpenAdmin(HttpServletRequest request) {
		log.info("Start getFirstOpenAdmin=========");
		// 当前user
		UserInfo user = UserInfoUtil.getCurrAdmin();

		// 组织参数 (admin信息，权限信息，配置信息)
		SoMap map = new SoMap();
		map.set("user", UserInfoUtil.getCurrAdmin());
		map.set("per_list", epRolePermissionService.getPcodeByRid2(user.getRoleId()));
		return AjaxJson.getSuccessData(map);
	}





}
