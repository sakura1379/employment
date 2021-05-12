package com.employ.employment.service;

import cn.dev33.satoken.stp.StpUtil;
import com.employ.employment.entity.SP;
import com.employ.employment.entity.UserInfo;
import com.employ.employment.mapper.UserInfoMapper;
import com.employ.employment.util.UserInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service: 用户表
 *
 */
@Service
public class UserInfoService {


	private final UserInfoMapper userInfoMapper;

	private final EpAdminPasswordService epAdminPasswordService;

	@Autowired
	public UserInfoService(UserInfoMapper userInfoMapper, EpAdminPasswordService epAdminPasswordService) {
		this.userInfoMapper = userInfoMapper;
		this.epAdminPasswordService = epAdminPasswordService;
	}



	/**
	 * 添加一个用户
	 * @param user
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)
	public long add(UserInfo user) {
		// 检查姓名是否合法
		UserInfoUtil.checkAdmin(user);

		// 创建人，为当前账号
//		user.setCreateByAid(StpUtil.getLoginIdAsLong());
		// 开始添加
		userInfoMapper.add(user);
		// 获取主键
		long id = SP.publicMapper.getPrimarykey();
		// 更改密码（md5与明文）
		epAdminPasswordService.updatePassword(id, user.getPassword2());

		// 返回主键
		return id;
	}




}
