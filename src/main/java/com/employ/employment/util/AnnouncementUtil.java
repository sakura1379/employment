package com.employ.employment.util;

import com.employ.employment.entity.AjaxError;
import com.employ.employment.entity.Announcement;
import com.employ.employment.mapper.AnnouncementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 工具类：announcement -- 
 * @author Zenglr 
 *
 */
@Component
public class AnnouncementUtil {

	
	/** 底层 Mapper 对象 */
	public static AnnouncementMapper announcementMapper;
	@Autowired
	private void setAnnouncementMapper(AnnouncementMapper announcementMapper) {
		AnnouncementUtil.announcementMapper = announcementMapper;
	}
	
	
	/** 
	 * 将一个 Announcement 对象进行进行数据完整性校验 (方便add/update等接口数据校验) [G] 
	 */
	static void check(Announcement a) {
		AjaxError.throwByIsNull(a.announceId, "[公告编号] 不能为空");		// 验证: 公告编号
		AjaxError.throwByIsNull(a.announceTitle, "[公告标题] 不能为空");		// 验证: 公告标题 
		AjaxError.throwByIsNull(a.announceContent, "[公告内容] 不能为空");		// 验证: 公告内容 
		AjaxError.throwByIsNull(a.announceType, "[公告类型] 不能为空");		// 验证: 公告类型 (1=系统公告, 2=经验分享, 3=宣讲会信息) 
		AjaxError.throwByIsNull(a.announceTime, "[发布时间] 不能为空");		// 验证: 发布时间 
		AjaxError.throwByIsNull(a.adminId, "[管理员编号] 不能为空");		// 验证: 管理员编号 
	}

	/** 
	 * 获取一个Announcement (方便复制代码用) [G] 
	 */ 
	static Announcement getAnnouncement() {
		Announcement a = new Announcement();	// 声明对象 
		a.announceId = 0;		// 公告编号 
		a.announceTitle = "";		// 公告标题 
		a.announceContent = "";		// 公告内容 
		a.announceType = 0;		// 公告类型 (1=系统公告, 2=经验分享, 3=宣讲会信息) 
		a.announceTime = new Date();		// 发布时间 
		a.adminId = 0;		// 管理员编号 
		return a;
	}
	
	
	
	
	
}
