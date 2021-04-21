package com.employ.employment.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.employ.employment.entity.*;
import com.employ.employment.service.CompanyInfoService;
import com.employ.employment.util.StpUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Controller: company_info -- 企业信息表
 * @author Zenglr 
 */
@RestController
@RequestMapping("/CompanyInfo/")
public class CompanyInfoController {

	/** 底层 Service 对象 */
	@Autowired
	CompanyInfoService companyInfoService;

	/** 增 */  
	@RequestMapping("add")
	@SaCheckPermission(CompanyInfo.PERMISSION_CODE)
	@Transactional(rollbackFor = Exception.class)
	public AjaxJson add(CompanyInfo c){
		companyInfoService.add(c);
		c = companyInfoService.getById(SP.publicMapper.getPrimarykey());
		return AjaxJson.getSuccessData(c);
	}

	/** 删 */  
	@RequestMapping("delete")
	@SaCheckPermission(CompanyInfo.PERMISSION_CODE)
	public AjaxJson delete(Integer id){
		int line = companyInfoService.delete(id);
		return AjaxJson.getByLine(line);
	}
	
	/** 删 - 根据id列表 */  
	@RequestMapping("deleteByIds")
	@SaCheckPermission(CompanyInfo.PERMISSION_CODE)
	public AjaxJson deleteByIds(){
		List<Long> ids = SoMap.getRequestSoMap().getListByComma("ids", long.class);
		int line = SP.publicMapper.deleteByIds(CompanyInfo.TABLE_NAME, ids);
		return AjaxJson.getByLine(line);
	}
	
	/** 改 */  
	@RequestMapping("update")
	@SaCheckPermission(CompanyInfo.PERMISSION_CODE)
	public AjaxJson update(CompanyInfo c){
		int line = companyInfoService.update(c);
		return AjaxJson.getByLine(line);
	}

	/** 查 - 根据id */  
	@RequestMapping("getById")
	public AjaxJson getById(Integer id){
		CompanyInfo c = companyInfoService.getById(id);
		return AjaxJson.getSuccessData(c);
	}

	/** 查集合 - 根据条件（参数为空时代表忽略指定条件） */  
	@RequestMapping("getList")
	public AjaxJson getList() { 
		SoMap so = SoMap.getRequestSoMap();
		List<CompanyInfo> list = companyInfoService.getList(so.startPage());
		return AjaxJson.getPageData(so.getDataCount(), list);
	}
	
	
	
	
	// ------------------------- 前端接口 -------------------------
	
	
	/** 改 - 不传不改 [G] */
	@RequestMapping("updateByNotNull")
	public AjaxJson updateByNotNull(Integer id){
		AjaxError.throwBy(true, "如需正常调用此接口，请删除此行代码");
		// 鉴别身份，是否为数据创建者 
		long userId = SP.publicMapper.getColumnByIdToLong(CompanyInfo.TABLE_NAME, "user_id", id);
		AjaxError.throwBy(userId != StpUserUtil.getLoginIdAsLong(), "此数据您无权限修改");
		// 开始修改 (请只保留需要修改的字段)
		SoMap so = SoMap.getRequestSoMap();
		so.clearNotIn("compId", "compName", "compIndustry", "compSize", "compAddress", "complink", "creditcode", "compEsDate", "compIntro", "approveStatus").clearNull().humpToLineCase();	
		int line = SP.publicMapper.updateBySoMapById(CompanyInfo.TABLE_NAME, so, id);
		return AjaxJson.getByLine(line);
	}
	
	
	
	
	
	

}
