package com.employ.employment.util;

import com.employ.employment.entity.AjaxError;
import com.employ.employment.entity.CompanyInfo;
import com.employ.employment.mapper.CompanyInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 工具类：company_info -- 企业信息表
 * @author Zenglr 
 *
 */
@Component
public class CompanyInfoUtil {

	
	/** 底层 Mapper 对象 */
	public static CompanyInfoMapper companyInfoMapper;
	@Autowired
	private void setCompanyInfoMapper(CompanyInfoMapper companyInfoMapper) {
		CompanyInfoUtil.companyInfoMapper = companyInfoMapper;
	}
	
	
	/** 
	 * 将一个 CompanyInfo 对象进行进行数据完整性校验 (方便add/update等接口数据校验) [G] 
	 */
	static void check(CompanyInfo c) {
		AjaxError.throwByIsNull(c.compId, "[公司编号] 不能为空");		// 验证: 公司编号
		AjaxError.throwByIsNull(c.compName, "[企业名称] 不能为空");		// 验证: 企业名称 
		AjaxError.throwByIsNull(c.compIndustry, "[企业所在行业] 不能为空");		// 验证: 企业所在行业 
		AjaxError.throwByIsNull(c.compSize, "[企业规模] 不能为空");		// 验证: 企业规模 
		AjaxError.throwByIsNull(c.compAddress, "[企业地址] 不能为空");		// 验证: 企业地址 
		AjaxError.throwByIsNull(c.complink, "[企业官网链接] 不能为空");		// 验证: 企业官网链接 
		AjaxError.throwByIsNull(c.creditcode, "[统一社会信用代码] 不能为空");		// 验证: 统一社会信用代码 
		AjaxError.throwByIsNull(c.compEsDate, "[企业成立日期] 不能为空");		// 验证: 企业成立日期 
		AjaxError.throwByIsNull(c.compIntro, "[企业介绍] 不能为空");		// 验证: 企业介绍 
		AjaxError.throwByIsNull(c.approveStatus, "[审核状态] 不能为空");		// 验证: 审核状态 (1=未审核, 2=审核通过, 3=审核不通过) 
	}

	/** 
	 * 获取一个CompanyInfo (方便复制代码用) [G] 
	 */ 
	static CompanyInfo getCompanyInfo() {
		CompanyInfo c = new CompanyInfo();	// 声明对象 
		c.compId = 0;		// 公司编号 
		c.compName = "";		// 企业名称 
		c.compIndustry = "";		// 企业所在行业 
		c.compSize = "";		// 企业规模 
		c.compAddress = "";		// 企业地址 
		c.complink = "";		// 企业官网链接 
		c.creditcode = "";		// 统一社会信用代码 
		c.compEsDate = new Date();		// 企业成立日期 
		c.compIntro = "";		// 企业介绍 
		c.approveStatus = "";		// 审核状态 (1=未审核, 2=审核通过, 3=审核不通过) 
		return c;
	}
	
	
	
	
	
}
