package com.employ.employment.config;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.hutool.crypto.SecureUtil;
import com.employ.employment.entity.MyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 有关当前项目的一些全局工具方法封装
 *
 */
@Component
public class SystemObject {

	// ===================================== 一些二次封装的方法 ===================================================

	/** 返回md5加密后的密码，根据当前配置的salt
	 *   格式为： md5(salt + userid + password)
	 */
	public static String getPasswordMd5(long userId, String password) {
		return SecureUtil.md5("whatclickme" + userId + password).toUpperCase();
	}

	/** 返回md5加密后的密码，根据当前配置的salt
	 *  格式为： md5(salt + 0 + password)
	 */
	public static String getPasswordMd5(String password) {
		return getPasswordMd5(0, password);
	}

	public static String getPasswordRsa(String password) throws Exception {
		return SaSecureUtil.rsaEncryptByPublic(SaSecureUtil.rsaGenerateKeyPair().get("public"),password);
	}

	// ===================================== yml自定义配置信息 ===================================================

	public static MyConfig config;
	@Autowired
	void setMyConfig(MyConfig config) {
		SystemObject.config = config;
	}


}
