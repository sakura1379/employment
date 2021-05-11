package com.employ.employment.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.employ.employment.config.token.AuthConst;
import com.employ.employment.entity.*;
import com.employ.employment.mapper.EpRoleMapper;
import com.employ.employment.util.EpRoleUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Controller: 系统角色表
 * @author Zenglr
 */
@RestController
@RequestMapping("/role/")
@Api
public class EpRoleController {

	private final EpRoleMapper epRoleMapper;

	@Autowired
	public EpRoleController(EpRoleMapper epRoleMapper) {
		this.epRoleMapper = epRoleMapper;
	}


	/** 增 */
	@RequestMapping("add")
	@Transactional(rollbackFor = Exception.class)
	AjaxJson add(EpRole s, HttpServletRequest request){
		StpUtil.checkPermission(AuthConst.ROLE_LIST);
		// 检验
		if(epRoleMapper.getById(s.getId()) != null) {
			return AjaxJson.getError("此id已存在，请更换");
		}
		EpRoleUtil.checkRoleThrow(s);
		int line = epRoleMapper.add(s);
		AjaxError.throwByLine(line, "添加失败");
		// 返回这个对象
		long id = s.getId();
		if(id == 0) {
			id = SP.publicMapper.getPrimarykey();
		}
		return AjaxJson.getSuccessData(epRoleMapper.getById(id));
	}

	/** 删 */
	@RequestMapping("delete")
	AjaxJson delete(long id, HttpServletRequest request){
		StpUtil.checkPermission(AuthConst.R1);
		StpUtil.checkPermission(AuthConst.ROLE_LIST);
		int line = epRoleMapper.delete(id);
		return AjaxJson.getByLine(line);
	}

	/** 改 */
	@RequestMapping("update")
	AjaxJson update(EpRole s){
		StpUtil.checkPermission(AuthConst.R1);
		StpUtil.checkPermission(AuthConst.ROLE_LIST);
		EpRoleUtil.checkRoleThrow(s);
		int line = epRoleMapper.update(s);
		return AjaxJson.getByLine(line);
	}

	/** 查 */
	@RequestMapping("getById")
	AjaxJson getById(long id){
		StpUtil.checkPermission(AuthConst.R99);
		EpRole s = epRoleMapper.getById(id);
		return AjaxJson.getSuccessData(s);
	}

	/** 查 - 集合  */
	@RequestMapping("getList")
	AjaxJson getList(){
		StpUtil.checkPermission(AuthConst.R99);
		SoMap so = SoMap.getRequestSoMap();
		List<EpRole> list = epRoleMapper.getList(so);
		return AjaxJson.getSuccessData(list);
	}





}
