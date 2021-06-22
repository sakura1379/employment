package com.employ.employment.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.employ.employment.config.token.AuthConst;
import com.employ.employment.entity.SP;
import com.employ.employment.entity.UserInfo;
import com.employ.employment.mapper.UserInfoMapper;
import com.employ.employment.service.EpAdminPasswordService;
import com.employ.employment.entity.AjaxJson;
import com.employ.employment.entity.SoMap;
import com.employ.employment.service.UserInfoService;
import com.employ.employment.util.UserInfoUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller -- 用户表
 */
@RestController
@RequestMapping("/user/")
@Api
@Slf4j
@CrossOrigin
public class UserInfoController {

	private final UserInfoMapper userInfoMapper;

	private final UserInfoService userInfoService;

	private final EpAdminPasswordService epAdminPasswordService;

	@Autowired
	public UserInfoController(UserInfoMapper userInfoMapper, UserInfoService userInfoService, EpAdminPasswordService epAdminPasswordService) {
		this.userInfoMapper = userInfoMapper;
		this.userInfoService = userInfoService;
		this.epAdminPasswordService = epAdminPasswordService;
	}


	/** 增  */
	@PutMapping("add")
	@ApiOperation("就业中心管理员注册")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "name", value = "用户名", required = true),
			@ApiImplicitParam(name = "password", value = "密码", required = true),
			@ApiImplicitParam(name = "mail", value = "邮箱", required = true)
	})
	AjaxJson add(UserInfo user){
		log.info("Start addUser========");
		log.info("Receive userInfo:{}", user.toString());
		user.setRoleId(1);
		long id = userInfoService.add(user);
		return AjaxJson.getSuccessData(id);
	}

	/** 删 */
	@DeleteMapping("delete")
	@ApiOperation("删除用户信息")
	AjaxJson delete(long id){
		log.info("Start deleteUser========");
		log.info("Receive id:{}", id);
		StpUtil.checkPermission(AuthConst.ADMIN_LIST);
		// 不能自己删除自己
		if(StpUtil.getLoginIdAsLong() == id) {
			return AjaxJson.getError("不能自己删除自己");
		}
		int line = userInfoMapper.delete(id);
		return AjaxJson.getByLine(line);
	}

	/** 删 - 根据id列表 */
	@DeleteMapping("deleteByIds")
	@ApiOperation("根据id列表删除用户信息")
	AjaxJson deleteByIds(){
		log.info("Start deleteUserInfoByIdList========");
		// 鉴权
		StpUtil.checkPermission(AuthConst.ADMIN_LIST);
		// 不能自己删除自己
		List<Long> ids = SoMap.getRequestSoMap().getListByComma("ids", long.class);
		if(ids.contains(StpUtil.getLoginIdAsLong())) {
			return AjaxJson.getError("不能自己删除自己");
		}
		// 开始删除
		int line = SP.publicMapper.deleteByIds("sp_admin", ids);
		return AjaxJson.getByLine(line);
	}

	/** 改  -  name */
	@PostMapping("update")
	@ApiOperation("改用户名字")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "用户id", required = true),
			@ApiImplicitParam(name = "name", value = "新用户名", required = true)
	})
	AjaxJson update(UserInfo obj){
		log.info("Start updateUser========");
		log.info("Receive userInfo:{}", obj.toString());
		StpUtil.checkPermission(AuthConst.ADMIN_LIST);
		UserInfoUtil.checkName(obj.getId(), obj.getName());
		int line = userInfoMapper.update(obj);
		return AjaxJson.getByLine(line);
	}

	/** 查  */
	@GetMapping("getById")
	@ApiOperation("根据id查一个用户信息")
	AjaxJson getById(long id){
		log.info("Start getUserInfoById========");
		log.info("Receive id:{}", id);
		StpUtil.checkPermission(AuthConst.ADMIN_LIST);
		Object data = userInfoMapper.getById(id);
		return AjaxJson.getSuccessData(data);
	}

	/** 查 - 集合 */
	@GetMapping("getList")
	@ApiOperation("查用户信息列表")
	AjaxJson getList(int page){
		log.info("Start getUserInfoList========");
		StpUtil.checkPermission(AuthConst.ADMIN_LIST);
		SoMap so = SoMap.getRequestSoMap();
		List<UserInfo> list = userInfoMapper.getList(so.startPage());
		return AjaxJson.getPageData(so.getDataCount(), list, page, 10);
	}

	/** 改密码 */
	@PostMapping("updatePassword")
	@ApiOperation("改密码")
	AjaxJson updatePassword(long id, String password){
		StpUtil.checkPermission(AuthConst.ADMIN_LIST);
		int line = epAdminPasswordService.updatePassword(id, password);
		return AjaxJson.getByLine(line);
	}

	/** 改头像  */
//	@RequestMapping("updateAvatar")
//	@ApiOperation("改头像")
//	AjaxJson updateAvatar(long id, String avatar){
//		StpUtil.checkPermission(AuthConst.ADMIN_LIST);
//		int line = SP.publicMapper.updateColumnById("sp_admin", "avatar", avatar, id);
//		return AjaxJson.getByLine(line);
//	}

	/** 改状态  */
	@RequestMapping("updateStatus")
	@ApiOperation("改状态")
	public AjaxJson updateStatus(long id, int status) {
		log.info("Start updateUserStatus========");
		log.info("Receive id:{}, status:{}", id, status);
		StpUtil.checkPermission(AuthConst.R1);

		// 验证对方是否为超管(保护超管不受摧残)
		if(StpUtil.hasPermission(id, AuthConst.R1)){
			return AjaxJson.getError("抱歉，对方角色为系统超级管理员，您暂无权限操作");
		}

		// 修改状态
		SP.publicMapper.updateColumnById("user_info", "status", status, id);
		// 如果是禁用，就停掉其秘钥有效果性，使其账号的登录状态立即无效
		if(status == 2) {
			StpUtil.logoutByLoginId(id);
		}
		return AjaxJson.getSuccess();
	}

	/** 改角色  */
	@PostMapping("updateRole")
	@ApiOperation("根据id改角色")
	AjaxJson updateRole(long id, String roleId){
		log.info("Start updateUserRole========");
		log.info("Receive id:{}, roleId:{}", id, roleId);
		StpUtil.checkPermission(AuthConst.R1);
		int line = SP.publicMapper.updateColumnById("user_info", "role_id", roleId, id);
		return AjaxJson.getByLine(line);
	}

	/** 返回当前admin信息  */
	@GetMapping("getByCurr")
	@ApiOperation("返回当前用户信息")
	AjaxJson getByCurr() {
		log.info("Start getCurrentUserInfo========");
		UserInfo admin = UserInfoUtil.getCurrAdmin();
		return AjaxJson.getSuccessData(admin);
	}

	/** 当前admin修改信息 */
	@PostMapping("updateInfo")
	@ApiOperation("当前用户修改名字")
	@ApiImplicitParam(name = "name", value = "新用户名", required = true)
	AjaxJson updateInfo(UserInfo obj){
		log.info("Start updateUserInfo========");
		log.info("Receive userInfo:{}", obj.toString());
		obj.setId(StpUtil.getLoginIdAsLong());
		UserInfoUtil.checkName(obj.getId(), obj.getName());
		int line = userInfoMapper.update(obj);
		return AjaxJson.getByLine(line);
	}


	/** 删 */
	@DeleteMapping("deleteCurrent")
	@ApiOperation("当前用户注销")
	@SaCheckLogin
	public AjaxJson delete(){
		log.info("Start deleteUserInfo========");
		long id = StpUtil.getLoginIdAsLong();
		log.info("Current user id:{}",id);
        int line = userInfoService.deleteCurrent(id);
		log.info("Start ExitLogin=======");
		StpUtil.logout();
        return AjaxJson.getByLine(line);
	}







}
