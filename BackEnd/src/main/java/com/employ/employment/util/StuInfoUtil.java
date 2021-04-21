package com.employ.employment.util;

import com.employ.employment.entity.AjaxError;
import com.employ.employment.entity.StuInfo;
import com.employ.employment.mapper.StuInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 工具类：stu_info -- 学生信息表
 * @author Zenglr 
 *
 */
@Component
public class StuInfoUtil {

	
	/** 底层 Mapper 对象 */
	public static StuInfoMapper stuInfoMapper;
	@Autowired
	private void setStuInfoMapper(StuInfoMapper stuInfoMapper) {
		StuInfoUtil.stuInfoMapper = stuInfoMapper;
	}
	
	
	/** 
	 * 将一个 StuInfo 对象进行进行数据完整性校验 (方便add/update等接口数据校验) [G] 
	 */
	static void check(StuInfo s) {
		AjaxError.throwByIsNull(s.stuNum, "[学生id] 不能为空");		// 验证: 学生id
		AjaxError.throwByIsNull(s.stuName, "[学生姓名] 不能为空");		// 验证: 学生姓名 
		AjaxError.throwByIsNull(s.avatar, "[学生头像] 不能为空");		// 验证: 学生头像 
		AjaxError.throwByIsNull(s.stuGraUniversity, "[学生学校] 不能为空");		// 验证: 学生学校 
		AjaxError.throwByIsNull(s.stuMajor, "[学生专业] 不能为空");		// 验证: 学生专业 
		AjaxError.throwByIsNull(s.stuEducation, "[学生学历] 不能为空");		// 验证: 学生学历 (1=本科, 2=研究生) 
		AjaxError.throwByIsNull(s.stJodKind, "[学生学历] 不能为空");		// 验证: 学生学历 (1=实习, 2=校招, 3=实习和校招) 
		AjaxError.throwByIsNull(s.stuGraduateTime, "[学生毕业年份] 不能为空");		// 验证: 学生毕业年份 
		AjaxError.throwByIsNull(s.stuEmail, "[学生邮箱] 不能为空");		// 验证: 学生邮箱 
		AjaxError.throwByIsNull(s.stuTelephone, "[学生电话号码] 不能为空");		// 验证: 学生电话号码 
		AjaxError.throwByIsNull(s.dreamAddress, "[期望城市] 不能为空");		// 验证: 期望城市 
		AjaxError.throwByIsNull(s.dreamPosition, "[期望职位类别] 不能为空");		// 验证: 期望职位类别 
		AjaxError.throwByIsNull(s.resume, "[简历信息] 不能为空");		// 验证: 简历信息 
	}

	/** 
	 * 获取一个StuInfo (方便复制代码用) [G] 
	 */ 
	static StuInfo getStuInfo() {
		StuInfo s = new StuInfo();	// 声明对象 
		s.stuNum = 0;		// 学生id 
		s.stuName = "";		// 学生姓名 
		s.avatar = "";		// 学生头像 
		s.stuGraUniversity = "";		// 学生学校 
		s.stuMajor = "";		// 学生专业 
		s.stuEducation = 0;		// 学生学历 (1=本科, 2=研究生) 
		s.stJodKind = 0;		// 学生学历 (1=实习, 2=校招, 3=实习和校招) 
		s.stuGraduateTime = new Date();		// 学生毕业年份 
		s.stuEmail = "";		// 学生邮箱 
		s.stuTelephone = "";		// 学生电话号码 
		s.dreamAddress = "";		// 期望城市 
		s.dreamPosition = "";		// 期望职位类别 
		s.resume = "";		// 简历信息 
		return s;
	}
	
	
	
	
	
}
