package com.employ.employment.service;

import cn.dev33.satoken.stp.StpUtil;
import com.employ.employment.entity.SP;
import com.employ.employment.entity.UserInfo;
import com.employ.employment.mapper.StuInfoMapper;
import com.employ.employment.mapper.UserInfoMapper;
import com.employ.employment.util.UserInfoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service: 用户表
 *
 */
@Service
@Slf4j
public class UserInfoService {

	private final UserInfoMapper userInfoMapper;

	private final EpAdminPasswordService epAdminPasswordService;

	private final StuInfoMapper stuInfoMapper;

	@Autowired
	public UserInfoService(UserInfoMapper userInfoMapper, EpAdminPasswordService epAdminPasswordService, StuInfoMapper stuInfoMapper) {
		this.userInfoMapper = userInfoMapper;
		this.epAdminPasswordService = epAdminPasswordService;
		this.stuInfoMapper = stuInfoMapper;
	}



	/**
	 * 添加一个用户
	 * @param user
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)
	public long add(UserInfo user) {
		// 检查姓名是否合法
		log.info("check name is legal");
		UserInfoUtil.checkAdmin(user);

		// 创建人，为当前账号
//		user.setCreateByAid(StpUtil.getLoginIdAsLong());
		// 开始添加
		log.info("start add====");
		userInfoMapper.add(user);
		// 获取主键
		long id = SP.publicMapper.getPrimarykey();
		log.info("add success, id:{}",id);
		// 更改密码（md5与明文）
		epAdminPasswordService.updatePassword(id, user.getPassword2());
		log.info("insert password success");
		// 返回主键
		return id;
	}

	/**
	 * 当前用户注销，判断是哪类用户
	 * 学生用户注销则需删除学生信息和用户信息
	 * 企业用户注销则需要判断是否为公司最后一个用户，如果是的话公司信息也需要删除
	 * 管理员用户判断是否为超级管理员，超级管理员不可注销
	 * @param id
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)
	public int deleteCurrent(long id){
//		获得roleId，判断是哪类用户
		long roleId = userInfoMapper.getRoleIdById(id);
		log.info("current roleId:{}",roleId);
		int line = 0;
		if (roleId == 11){
//			学生用户，则删除当前id下的学生信息以及用户信息
			line = userInfoMapper.delete(id);
			line += stuInfoMapper.delete(id);
			log.info("delete stuInfo and userInfo, id:{}",id);
		}else if (roleId == 121 || roleId == 1212 ){
//			企业用户，则首先到企业用户表中获得到对应企业id

//			到企业用户表中查该企业的用户列表

//			如果只有一个用户，首先则删除该用户信息以及对应企业信息

//			如果不只有一个用户，则只删除该用户信息

		}else if (roleId == 2){
//			普通管理员用户，直接删除用户信息
			line = userInfoMapper.delete(id);
		}else {
//			超级管理员不可删除
			log.info("current user cannot delete");
		}
		return line;
	}


}
