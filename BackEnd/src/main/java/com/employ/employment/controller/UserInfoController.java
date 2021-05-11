package com.employ.employment.controller;

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
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller -- 系统管理员表
 */
@RestController
@RequestMapping("/user/")
@Api
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
	@ApiOperation("增加管理员信息")
	@ApiImplicitParam(dataTypeClass = UserInfo.class)
	AjaxJson add(UserInfo user){
		StpUtil.checkPermission(AuthConst.ADMIN_LIST);
		long id = userInfoService.add(user);
		return AjaxJson.getSuccessData(id);
	}

	/** 删 */
	@DeleteMapping("delete")
	@ApiOperation("删除管理员信息")
	AjaxJson delete(long id){
		StpUtil.checkPermission(AuthConst.ADMIN_LIST);
		// 不能自己删除自己
		if(StpUtil.getLoginIdAsLong() == id) {
			return AjaxJson.getError("不能自己删除自己");
		}
		int line = userInfoMapper.delete(id);
		return AjaxJson.getByLine(line);
	}

	/** 删 - 根据id列表 */
	@RequestMapping("deleteByIds")
	@ApiOperation("根据id列表删除管理员信息")
	AjaxJson deleteByIds(){
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
	@RequestMapping("update")
	@ApiOperation("改")
	AjaxJson update(UserInfo obj){
		StpUtil.checkPermission(AuthConst.ADMIN_LIST);
		UserInfoUtil.checkName(obj.getId(), obj.getName());
		int line = userInfoMapper.update(obj);
		return AjaxJson.getByLine(line);
	}

	/** 查  */
	@RequestMapping("getById")
	@ApiOperation("根据id查一个管理员信息")
	AjaxJson getById(long id){
		StpUtil.checkPermission(AuthConst.ADMIN_LIST);
		Object data = userInfoMapper.getById(id);
		return AjaxJson.getSuccessData(data);
	}

	/** 查 - 集合 */
	@RequestMapping("getList")
	@ApiOperation("查管理员信息列表")
	AjaxJson getList(){
		StpUtil.checkPermission(AuthConst.ADMIN_LIST);
		SoMap so = SoMap.getRequestSoMap();
		List<UserInfo> list = userInfoMapper.getList(so.startPage());
		return AjaxJson.getPageData(so.getDataCount(), list);
	}

	/** 改密码 */
	@RequestMapping("updatePassword")
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
	public AjaxJson updateStatus(long adminId, int status) {
		StpUtil.checkPermission(AuthConst.R1);

		// 验证对方是否为超管(保护超管不受摧残)
		if(StpUtil.hasPermission(adminId, AuthConst.R1)){
			return AjaxJson.getError("抱歉，对方角色为系统超级管理员，您暂无权限操作");
		}

		// 修改状态
		SP.publicMapper.updateColumnById("sp_admin", "status", status, adminId);
		// 如果是禁用，就停掉其秘钥有效果性，使其账号的登录状态立即无效
		if(status == 2) {
			StpUtil.logoutByLoginId(adminId);
		}
		return AjaxJson.getSuccess();
	}

	/** 改角色  */
	@RequestMapping("updateRole")
	AjaxJson updateRole(long id, String roleId){
		StpUtil.checkPermission(AuthConst.R1);
		int line = SP.publicMapper.updateColumnById("sp_admin", "role_id", roleId, id);
		return AjaxJson.getByLine(line);
	}

	/** 返回当前admin信息  */
	@RequestMapping("getByCurr")
	AjaxJson getByCurr() {
		UserInfo admin = UserInfoUtil.getCurrAdmin();
		return AjaxJson.getSuccessData(admin);
	}

	/** 当前admin修改信息 */
	@RequestMapping("updateInfo")
	AjaxJson updateInfo(UserInfo obj){
		obj.setId(StpUtil.getLoginIdAsLong());
		UserInfoUtil.checkName(obj.getId(), obj.getName());
		int line = userInfoMapper.update(obj);
		return AjaxJson.getByLine(line);
	}










}
