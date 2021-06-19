package com.employ.employment.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.employ.employment.config.token.AuthConst;
import com.employ.employment.entity.*;
import com.employ.employment.mapper.EpRoleMapper;
import com.employ.employment.util.EpRoleUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Controller: 系统角色表
 * @author Zenglr
 */
@RestController
@RequestMapping("/role/")
@Api
@Slf4j
@CrossOrigin
public class EpRoleController {

	private final EpRoleMapper epRoleMapper;

	@Autowired
	public EpRoleController(EpRoleMapper epRoleMapper) {
		this.epRoleMapper = epRoleMapper;
	}


	/** 增 */
	@PutMapping("add")
	@Transactional(rollbackFor = Exception.class)
	@ApiOperation("增加角色")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "roleId", required = true),
			@ApiImplicitParam(name = "name", value = "角色名称", required = true),
			@ApiImplicitParam(name = "info", value = "角色详细描述", required = true)
	})
	AjaxJson add(EpRole s, HttpServletRequest request){
		log.info("Start addRole========");
		log.info("Receive EpRole:{}", s.toString());
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
	@DeleteMapping("delete")
	@ApiOperation("删除角色")
	AjaxJson delete(long id, HttpServletRequest request){
		log.info("Start deleteRole========");
		log.info("Receive id:{}", id);
		StpUtil.checkPermission(AuthConst.R1);
		StpUtil.checkPermission(AuthConst.ROLE_LIST);
		int line = epRoleMapper.delete(id);
		return AjaxJson.getByLine(line);
	}

	/** 改 */
	@PostMapping("update")
	@ApiOperation("修改角色")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "roleId", required = true),
			@ApiImplicitParam(name = "name", value = "角色名称", required = true),
			@ApiImplicitParam(name = "info", value = "角色详细描述", required = true)
	})
	AjaxJson update(EpRole s){
		log.info("Start updateRole========");
		log.info("Receive EpRole:{}", s.toString());
		StpUtil.checkPermission(AuthConst.R1);
		StpUtil.checkPermission(AuthConst.ROLE_LIST);
		EpRoleUtil.checkRoleThrow(s);
		int line = epRoleMapper.update(s);
		return AjaxJson.getByLine(line);
	}

	/** 查 */
	@GetMapping("getById")
	@ApiOperation("根据id查角色")
	AjaxJson getById(long id){
		log.info("Start getRoleById========");
		log.info("Receive id:{}", id);
		StpUtil.checkPermission(AuthConst.R99);
		EpRole s = epRoleMapper.getById(id);
		return AjaxJson.getSuccessData(s);
	}

	/** 查 - 集合  */
	@GetMapping("getList")
	@ApiOperation("查所有的角色列表")
	AjaxJson getList(){
		log.info("Start getRoleList========");
		StpUtil.checkPermission(AuthConst.R99);
		SoMap so = SoMap.getRequestSoMap();
		List<EpRole> list = epRoleMapper.getList(so);
		return AjaxJson.getSuccessData(list);
	}





}
