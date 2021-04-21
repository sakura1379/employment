package com.employ.employment.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.employ.employment.entity.*;
import com.employ.employment.service.AnnouncementService;
import com.employ.employment.util.StpUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Controller: announcement -- 
 * @author Zenglr 
 */
@RestController
@RequestMapping("/Announcement/")
public class AnnouncementController {

	/** 底层 Service 对象 */
	@Autowired
	AnnouncementService announcementService;

	/** 增 */  
	@RequestMapping("add")
	@SaCheckPermission(Announcement.PERMISSION_CODE)
	@Transactional(rollbackFor = Exception.class)
	public AjaxJson add(Announcement a){
		announcementService.add(a);
		a = announcementService.getById(SP.publicMapper.getPrimarykey());
		return AjaxJson.getSuccessData(a);
	}

	/** 删 */  
	@RequestMapping("delete")
	@SaCheckPermission(Announcement.PERMISSION_CODE)
	public AjaxJson delete(Integer id){
		int line = announcementService.delete(id);
		return AjaxJson.getByLine(line);
	}
	
	/** 删 - 根据id列表 */  
	@RequestMapping("deleteByIds")
	@SaCheckPermission(Announcement.PERMISSION_CODE)
	public AjaxJson deleteByIds(){
		List<Long> ids = SoMap.getRequestSoMap().getListByComma("ids", long.class);
		int line = SP.publicMapper.deleteByIds(Announcement.TABLE_NAME, ids);
		return AjaxJson.getByLine(line);
	}
	
	/** 改 */  
	@RequestMapping("update")
	@SaCheckPermission(Announcement.PERMISSION_CODE)
	public AjaxJson update(Announcement a){
		int line = announcementService.update(a);
		return AjaxJson.getByLine(line);
	}

	/** 查 - 根据id */  
	@RequestMapping("getById")
	public AjaxJson getById(Integer id){
		Announcement a = announcementService.getById(id);
		return AjaxJson.getSuccessData(a);
	}

	/** 查集合 - 根据条件（参数为空时代表忽略指定条件） */  
	@RequestMapping("getList")
	public AjaxJson getList() { 
		SoMap so = SoMap.getRequestSoMap();
		List<Announcement> list = announcementService.getList(so.startPage());
		return AjaxJson.getPageData(so.getDataCount(), list);
	}
	
	
	
	
	// ------------------------- 前端接口 -------------------------
	
	
	/** 改 - 不传不改 [G] */
	@RequestMapping("updateByNotNull")
	public AjaxJson updateByNotNull(Integer id){
		AjaxError.throwBy(true, "如需正常调用此接口，请删除此行代码");
		// 鉴别身份，是否为数据创建者 
		long userId = SP.publicMapper.getColumnByIdToLong(Announcement.TABLE_NAME, "user_id", id);
		AjaxError.throwBy(userId != StpUserUtil.getLoginIdAsLong(), "此数据您无权限修改");
		// 开始修改 (请只保留需要修改的字段)
		SoMap so = SoMap.getRequestSoMap();
		so.clearNotIn("announceId", "announceTitle", "announceContent", "announceType", "announceTime", "adminId").clearNull().humpToLineCase();	
		int line = SP.publicMapper.updateBySoMapById(Announcement.TABLE_NAME, so, id);
		return AjaxJson.getByLine(line);
	}
	
	
	
	
	
	

}
