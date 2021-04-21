package com.employ.employment.service;

import com.employ.employment.entity.ApplyInfo;
import com.employ.employment.entity.SoMap;
import com.employ.employment.mapper.ApplyInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service: apply_info -- 职位申请表
 * @author Zenglr 
 */
@Service
public class ApplyInfoService {

	/** 底层 Mapper 对象 */
	@Autowired
	ApplyInfoMapper applyInfoMapper;

	/** 增 */
	public int add(ApplyInfo a){
		return applyInfoMapper.add(a);
	}

	/** 删 */
	public int delete(Integer stuNum){
		return applyInfoMapper.delete(stuNum);
	}

	/** 改 */
	public int update(ApplyInfo a){
		return applyInfoMapper.update(a);
	}

	/** 查
	 * @param stuNum*/
	public ApplyInfo getById(long stuNum){
		return applyInfoMapper.getById((int) stuNum);
	}

	/** 查集合 - 根据条件（参数为空时代表忽略指定条件） */
	public List<ApplyInfo> getList(SoMap so) {
		return applyInfoMapper.getList(so);	
	}
	

}
