package com.employ.employment.util;

import cn.dev33.satoken.stp.StpUtil;
import com.employ.employment.entity.AjaxError;
import com.employ.employment.entity.UserInfo;
import com.employ.employment.mapper.UserInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * admin工具类
 * @author Zenglr
 *
 */
@Component
@Slf4j
public class UserInfoUtil {


	static UserInfoMapper userInfoMapper;
	@Autowired
	public void setSpAdminMapper(UserInfoMapper userInfoMapper) {
		this.userInfoMapper = userInfoMapper;
	}


	/**
	 * 当前admin
	 * @return
	 */
	public static UserInfo getCurrAdmin() {
		long loginId = StpUtil.getLoginIdAsLong();
		return userInfoMapper.getById(loginId);
	}

	/**
	 * 检查指定姓名是否合法 ,如果不合法，则抛出异常
	 * @param id
	 * @param name
	 * @return
	 */
	public static boolean checkName(long id, String name) {
		log.info("Receive id:{}, name:{}", id, name);
		if(utils.isNull(name)) {
			throw AjaxError.get("账号名称不能为空");
		}
		if(utils.isNumber(name)) {
			throw AjaxError.get("账号名称不能为纯数字");
		}
//		if(name.startsWith("a")) {
//			throw AjaxException.get("账号名称不能以字母a开头");
//		}
		// 如果能查出来数据，而且不是本人，则代表与已有数据重复
		UserInfo u2 = userInfoMapper.getByName(name);
		if(u2 != null && u2.getId() != id) {
			throw AjaxError.get("账号名称已有账号使用，请更换");
		}
		return true;
	}

	/**
	 * 检查整个用户是否合格
	 * @param u
	 * @return
	 */
	public static boolean checkAdmin(UserInfo u) {
		// 检查姓名
//		checkName(u.getId(), u.getName());
		nameIsOk(u.getName());
		// 检查密码
		if(u.getPassword2().length() < 4) {
			throw new AjaxError("密码不得低于4位");
		}
		return true;
	}



	/**
	 * 指定的name是否可用
	 * @param name
	 * @return
	 */
	public static boolean nameIsOk(String name) {
		UserInfo u2 = userInfoMapper.getByName(name);
		if(u2 == null) {
			return true;
		}
		return false;
	}










}
