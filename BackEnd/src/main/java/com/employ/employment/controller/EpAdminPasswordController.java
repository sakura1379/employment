package com.employ.employment.controller;

import com.employ.employment.config.SystemObject;
import com.employ.employment.entity.UserInfo;
import com.employ.employment.service.EpAdminPasswordService;
import com.employ.employment.entity.AjaxJson;
import com.employ.employment.util.UserInfoUtil;
import com.employ.employment.util.utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * admin表 密码相关
 *
 * @author Zenglr
 */
@RestController
@RequestMapping("/UserPassword/")
@Api
@Slf4j
public class EpAdminPasswordController {

	private final EpAdminPasswordService epAdminPasswordService;

	@Autowired
	public EpAdminPasswordController(EpAdminPasswordService epAdminPasswordService) {
		this.epAdminPasswordService = epAdminPasswordService;
	}

	/** 指定用户修改自己密码 */
	@PostMapping("update")
	@ApiOperation("指定用户修改自己密码")
	AjaxJson updatePassword(String oldPwd, String newPwd) {
		log.info("Start updating password=========");
		log.info("Receive oldPwd:{}, newPwd:{}", oldPwd, newPwd);
		// 1、转md5
		UserInfo u = UserInfoUtil.getCurrAdmin();
		String oldPwdMd5 = SystemObject.getPasswordMd5(u.getId(), oldPwd);

		// 2、验证
		if(utils.isNull(u.getPassword2()) && utils.isNull(oldPwd)) {
			// 如果没有旧密码，则不用取验证
		} else {
			if(!oldPwdMd5.equals(u.getPassword2())) {
				return AjaxJson.getError("旧密码输入错误");
			}
		}

		// 3、开始改
		int line = epAdminPasswordService.updatePassword(u.getId(), newPwd);
		return AjaxJson.getByLine(line);
	}




}
