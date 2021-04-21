package com.employ.employment.service;

import com.employ.employment.entity.SoMap;
import com.employ.employment.entity.StuInfo;
import com.employ.employment.mapper.StuInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service: stu_info -- 学生信息表
 * @author Zenglr 
 */
@Service
public class StuInfoService {

	/** 底层 Mapper 对象 */
	@Autowired
	StuInfoMapper stuInfoMapper;

	/** 增 */
	public int add(StuInfo s){
		return stuInfoMapper.add(s);
	}

	/** 删 */
	public int delete(Integer stuNum){
		return stuInfoMapper.delete(stuNum);
	}

	/** 改 */
	public int update(StuInfo s){
		return stuInfoMapper.update(s);
	}

	/** 查
	 * @param stuNum*/
	public StuInfo getById(long stuNum){
		return stuInfoMapper.getById((int) stuNum);
	}

	/** 查集合 - 根据条件（参数为空时代表忽略指定条件） */
	public List<StuInfo> getList(SoMap so) {
		return stuInfoMapper.getList(so);	
	}
	

}
