package com.employ.employment.service;

import com.employ.employment.entity.CompanyInfo;
import com.employ.employment.entity.SoMap;
import com.employ.employment.mapper.CompanyInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service: company_info -- 企业信息表
 * @author Zenglr 
 */
@Service
public class CompanyInfoService {

	/** 底层 Mapper 对象 */
	@Autowired
	CompanyInfoMapper companyInfoMapper;

	/** 增 */
	public int add(CompanyInfo c){
		return companyInfoMapper.add(c);
	}

	/** 删 */
	public int delete(Integer compId){
		return companyInfoMapper.delete(compId);
	}

	/** 改 */
	public int update(CompanyInfo c){
		return companyInfoMapper.update(c);
	}

	/** 查
	 * @param compId*/
	public CompanyInfo getById(long compId){
		return companyInfoMapper.getById((int) compId);
	}

	/** 查集合 - 根据条件（参数为空时代表忽略指定条件） */
	public List<CompanyInfo> getList(SoMap so) {
		return companyInfoMapper.getList(so);	
	}
	

}
