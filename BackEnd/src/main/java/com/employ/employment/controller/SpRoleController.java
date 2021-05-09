package com.employ.employment.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.employ.employment.config.token.AuthConst;
import com.employ.employment.entity.SP;
import com.employ.employment.entity.SoMap;
import com.employ.employment.entity.SpRole;
import com.employ.employment.mapper.SpRoleMapper;
import com.employ.employment.entity.AjaxError;
import com.employ.employment.entity.AjaxJson;
import com.employ.employment.service.SpCfgService;
import com.employ.employment.util.SpRoleUtil;
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
public class SpRoleController {

	private final SpRoleMapper spRoleMapper;

	@Autowired
	public SpRoleController(SpRoleMapper spRoleMapper) {
		this.spRoleMapper = spRoleMapper;
	}


	/** 增 */
	@RequestMapping("add")
	@Transactional(rollbackFor = Exception.class)
	AjaxJson add(SpRole s, HttpServletRequest request){
		StpUtil.checkPermission(AuthConst.ROLE_LIST);
		// 检验
		if(spRoleMapper.getById(s.getId()) != null) {
			return AjaxJson.getError("此id已存在，请更换");
		}
		SpRoleUtil.checkRoleThrow(s);
		int line = spRoleMapper.add(s);
		AjaxError.throwByLine(line, "添加失败");
		// 返回这个对象
		long id = s.getId();
		if(id == 0) {
			id = SP.publicMapper.getPrimarykey();
		}
		return AjaxJson.getSuccessData(spRoleMapper.getById(id));
	}

	/** 删 */
	@RequestMapping("delete")
	AjaxJson delete(long id, HttpServletRequest request){
		StpUtil.checkPermission(AuthConst.R1);
		StpUtil.checkPermission(AuthConst.ROLE_LIST);
		int line = spRoleMapper.delete(id);
		return AjaxJson.getByLine(line);
	}

	/** 改 */
	@RequestMapping("update")
	AjaxJson update(SpRole s){
		StpUtil.checkPermission(AuthConst.R1);
		StpUtil.checkPermission(AuthConst.ROLE_LIST);
		SpRoleUtil.checkRoleThrow(s);
		int line = spRoleMapper.update(s);
		return AjaxJson.getByLine(line);
	}

	/** 查 */
	@RequestMapping("getById")
	AjaxJson getById(long id){
		StpUtil.checkPermission(AuthConst.R99);
		SpRole s = spRoleMapper.getById(id);
		return AjaxJson.getSuccessData(s);
	}

	/** 查 - 集合  */
	@RequestMapping("getList")
	AjaxJson getList(){
		StpUtil.checkPermission(AuthConst.R99);
		SoMap so = SoMap.getRequestSoMap();
		List<SpRole> list = spRoleMapper.getList(so);
		return AjaxJson.getSuccessData(list);
	}





}
