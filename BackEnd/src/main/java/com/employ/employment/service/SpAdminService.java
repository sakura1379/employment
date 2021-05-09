package com.employ.employment.service;

import cn.dev33.satoken.stp.StpUtil;
import com.employ.employment.entity.SP;
import com.employ.employment.entity.SpAdmin;
import com.employ.employment.mapper.SpAdminMapper;
import com.employ.employment.util.SpAdminUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service: 用户表
 *
 */
@Service
public class SpAdminService {


	private final SpAdminMapper spAdminMapper;

	private final SpAdminPasswordService spAdminPasswordService;

	@Autowired
	public SpAdminService(SpAdminMapper spAdminMapper, SpAdminPasswordService spAdminPasswordService) {
		this.spAdminMapper = spAdminMapper;
		this.spAdminPasswordService = spAdminPasswordService;
	}



	/**
	 * 管理员添加一个管理员
	 * @param admin
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)
	public long add(SpAdmin admin) {
		// 检查姓名是否合法
		SpAdminUtil.checkAdmin(admin);

		// 创建人，为当前账号
		admin.setCreateByAid(StpUtil.getLoginIdAsLong());
		// 开始添加
		spAdminMapper.add(admin);
		// 获取主键
		long id = SP.publicMapper.getPrimarykey();
		// 更改密码（md5与明文）
		spAdminPasswordService.updatePassword(id, admin.getPassword2());

		// 返回主键
		return id;
	}




}
