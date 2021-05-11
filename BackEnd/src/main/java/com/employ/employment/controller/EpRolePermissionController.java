package com.employ.employment.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.employ.employment.config.token.AuthConst;
import com.employ.employment.entity.AjaxJson;
import com.employ.employment.service.EpRolePermissionService;
import com.employ.employment.util.EpRoleUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller: 角色与权限的中间表
 * @author Zenglr
 *
 */
@RestController
@RequestMapping("/EpRolePermission/")
@Api
public class EpRolePermissionController {

	private final EpRolePermissionService epRolePermissionService;

	@Autowired
	public EpRolePermissionController(EpRolePermissionService epRolePermissionService) {
		this.epRolePermissionService = epRolePermissionService;
	}

	/**
	 * 拉取权限id列表  根据指定roleId
	 * @param roleId
	 * @return
	 */
	@RequestMapping("getPcodeByRid")
    public AjaxJson getPcodeByRid(@RequestParam(defaultValue="0") long roleId){
		// 鉴权
		StpUtil.checkPermission(AuthConst.R1);
		StpUtil.checkPermission(AuthConst.ROLE_LIST);
		// 防止拉出全部
		if(roleId == 0){
			return AjaxJson.getError("roleId不能为null或0");
		}
		return AjaxJson.getSuccessData(epRolePermissionService.getPcodeByRid2(roleId));
	}


	/** 拉取菜单id列表  根据当前用户roleId  */
	@RequestMapping("getPcodeByCurrRid")
	public AjaxJson getPcodeByCurrRid(){
		long roleId = EpRoleUtil.getCurrRoleId();
		List<Object> list = epRolePermissionService.getPcodeByRid2(roleId);
		return AjaxJson.getSuccessData(list);
	}


	/**
	 * 修改指定角色的拥有的权限
	 * @param roleId 角色id
	 * @param code 拥有的权限码集合
	 * @return
	 */
	@RequestMapping("updatePcodeByRid")
	public AjaxJson updatePcodeByRid(long roleId, String[] code){
		StpUtil.checkPermission(AuthConst.R1);
		StpUtil.checkPermission(AuthConst.ROLE_LIST);
		return AjaxJson.getSuccessData(epRolePermissionService.updateRoleMenu(roleId, code));
	}










}
