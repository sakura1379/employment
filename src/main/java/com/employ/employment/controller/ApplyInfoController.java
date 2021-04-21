package com.employ.employment.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.employ.employment.entity.*;
import com.employ.employment.service.ApplyInfoService;
import com.employ.employment.util.StpUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Controller: apply_info -- 职位申请表
 * @author Zenglr 
 */
@RestController
@RequestMapping("/ApplyInfo/")
public class ApplyInfoController {

	/** 底层 Service 对象 */
	@Autowired
	ApplyInfoService applyInfoService;

	/** 增 */  
	@RequestMapping("add")
	@SaCheckPermission(ApplyInfo.PERMISSION_CODE)
	@Transactional(rollbackFor = Exception.class)
	public AjaxJson add(ApplyInfo a){
		applyInfoService.add(a);
		a = applyInfoService.getById(SP.publicMapper.getPrimarykey());
		return AjaxJson.getSuccessData(a);
	}

	/** 删 */  
	@RequestMapping("delete")
	@SaCheckPermission(ApplyInfo.PERMISSION_CODE)
	public AjaxJson delete(Integer id){
		int line = applyInfoService.delete(id);
		return AjaxJson.getByLine(line);
	}
	
	/** 删 - 根据id列表 */  
	@RequestMapping("deleteByIds")
	@SaCheckPermission(ApplyInfo.PERMISSION_CODE)
	public AjaxJson deleteByIds(){
		List<Long> ids = SoMap.getRequestSoMap().getListByComma("ids", long.class);
		int line = SP.publicMapper.deleteByIds(ApplyInfo.TABLE_NAME, ids);
		return AjaxJson.getByLine(line);
	}
	
	/** 改 */  
	@RequestMapping("update")
	@SaCheckPermission(ApplyInfo.PERMISSION_CODE)
	public AjaxJson update(ApplyInfo a){
		int line = applyInfoService.update(a);
		return AjaxJson.getByLine(line);
	}

	/** 查 - 根据id */  
	@RequestMapping("getById")
	public AjaxJson getById(Integer id){
		ApplyInfo a = applyInfoService.getById(id);
		return AjaxJson.getSuccessData(a);
	}

	/** 查集合 - 根据条件（参数为空时代表忽略指定条件） */  
	@RequestMapping("getList")
	public AjaxJson getList() { 
		SoMap so = SoMap.getRequestSoMap();
		List<ApplyInfo> list = applyInfoService.getList(so.startPage());
		return AjaxJson.getPageData(so.getDataCount(), list);
	}
	
	
	
	
	// ------------------------- 前端接口 -------------------------
	
	
	/** 改 - 不传不改 [G] */
	@RequestMapping("updateByNotNull")
	public AjaxJson updateByNotNull(Integer id){
		AjaxError.throwBy(true, "如需正常调用此接口，请删除此行代码");
		// 鉴别身份，是否为数据创建者 
		long userId = SP.publicMapper.getColumnByIdToLong(ApplyInfo.TABLE_NAME, "user_id", id);
		AjaxError.throwBy(userId != StpUserUtil.getLoginIdAsLong(), "此数据您无权限修改");
		// 开始修改 (请只保留需要修改的字段)
		SoMap so = SoMap.getRequestSoMap();
		so.clearNotIn("stuNum", "jobId", "internshipTime", "dutyTime", "applyStatus").clearNull().humpToLineCase();	
		int line = SP.publicMapper.updateBySoMapById(ApplyInfo.TABLE_NAME, so, id);
		return AjaxJson.getByLine(line);
	}
	
	
	
	
	
	

}
