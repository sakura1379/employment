package com.employ.employment.util;

import com.employ.employment.entity.AjaxError;
import com.employ.employment.entity.ApplyInfo;
import com.employ.employment.mapper.ApplyInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 工具类：apply_info -- 职位申请表
 * @author Zenglr 
 *
 */
@Component
public class ApplyInfoUtil {

	
	/** 底层 Mapper 对象 */
	public static ApplyInfoMapper applyInfoMapper;
	@Autowired
	private void setApplyInfoMapper(ApplyInfoMapper applyInfoMapper) {
		ApplyInfoUtil.applyInfoMapper = applyInfoMapper;
	}
	
	
	/** 
	 * 将一个 ApplyInfo 对象进行进行数据完整性校验 (方便add/update等接口数据校验) [G] 
	 */
	static void check(ApplyInfo a) {
		AjaxError.throwByIsNull(a.stuNum, "[学生编号] 不能为空");		// 验证: 学生编号
		AjaxError.throwByIsNull(a.jobId, "[职位信息编号] 不能为空");		// 验证: 职位信息编号 
		AjaxError.throwByIsNull(a.internshipTime, "[一周可实习时间] 不能为空");		// 验证: 一周可实习时间 (1=一天, 2=两天, 3=三天, 4=四天, 5=五天, 6=六天) 
		AjaxError.throwByIsNull(a.dutyTime, "[最快到岗时间] 不能为空");		// 验证: 最快到岗时间 (1=一周内, 2=两周内, 3=一个月内, 4=三个月内) 
		AjaxError.throwByIsNull(a.applyStatus, "[申请状态] 不能为空");		// 验证: 申请状态 (1=简历待筛选, 2=简历未通过, 3=一面, 4=二面, 5=HR面, 6=HR面, 7=录用评估中, 8=录用意向, 9=已录用) 
	}

	/** 
	 * 获取一个ApplyInfo (方便复制代码用) [G] 
	 */ 
	static ApplyInfo getApplyInfo() {
		ApplyInfo a = new ApplyInfo();	// 声明对象 
		a.stuNum = 0;		// 学生编号 
		a.jobId = 0;		// 职位信息编号 
		a.internshipTime = 0;		// 一周可实习时间 (1=一天, 2=两天, 3=三天, 4=四天, 5=五天, 6=六天) 
		a.dutyTime = 0;		// 最快到岗时间 (1=一周内, 2=两周内, 3=一个月内, 4=三个月内) 
		a.applyStatus = 0;		// 申请状态 (1=简历待筛选, 2=简历未通过, 3=一面, 4=二面, 5=HR面, 6=HR面, 7=录用评估中, 8=录用意向, 9=已录用) 
		return a;
	}
	
	
	
	
	
}
