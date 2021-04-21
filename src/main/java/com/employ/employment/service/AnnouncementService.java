package com.employ.employment.service;

import com.employ.employment.entity.Announcement;
import com.employ.employment.entity.SoMap;
import com.employ.employment.mapper.AnnouncementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service: announcement -- 
 * @author Zenglr 
 */
@Service
public class AnnouncementService {

	/** 底层 Mapper 对象 */
	@Autowired
	AnnouncementMapper announcementMapper;

	/** 增 */
	public int add(Announcement a){
		return announcementMapper.add(a);
	}

	/** 删 */
	public int delete(Integer announceId){
		return announcementMapper.delete(announceId);
	}

	/** 改 */
	public int update(Announcement a){
		return announcementMapper.update(a);
	}

	/** 查
	 * @param announceId*/
	public Announcement getById(long announceId){
		return announcementMapper.getById((int) announceId);
	}

	/** 查集合 - 根据条件（参数为空时代表忽略指定条件） */
	public List<Announcement> getList(SoMap so) {
		return announcementMapper.getList(so);	
	}
	

}
