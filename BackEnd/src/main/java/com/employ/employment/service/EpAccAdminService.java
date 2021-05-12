package com.employ.employment.service;


import cn.dev33.satoken.spring.SpringMVCUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.employ.employment.config.SystemObject;
import com.employ.employment.entity.SP;
import com.employ.employment.entity.SoMap;
import com.employ.employment.entity.UserInfo;
import com.employ.employment.mapper.EpAccAdminMapper;
import com.employ.employment.entity.AjaxJson;
import com.employ.employment.mapper.UserInfoMapper;
import com.employ.employment.util.WebUtil;
import com.employ.employment.util.utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;

/**
 * service：admin账号相关
 * @author Zenglr
 *
 */
@Service
@Slf4j
public class EpAccAdminService {

	private final UserInfoMapper userInfoMapper;

	private final EpAccAdminMapper epAccAdminMapper;

	private final EpRolePermissionService epRolePermissionService;

	@Autowired
	public EpAccAdminService(UserInfoMapper userInfoMapper, EpAccAdminMapper epAccAdminMapper, EpRolePermissionService epRolePermissionService) {
		this.userInfoMapper = userInfoMapper;
		this.epAccAdminMapper = epAccAdminMapper;
		this.epRolePermissionService = epRolePermissionService;
	}


	/**
	  * 登录
	 * @param key 账号
	 * @param password 密码
	 * @return
	 */
	public AjaxJson doLogin(String key, String password) {

		// 0、判断 way (1=ID, 2=昵称，3=邮箱  )
    	int way = 2;
    	if(utils.isNumber(key) == true){
    		way = 1;
//    		if(key.length() == 11){
//    			way = 3;
//    		}
    	}
		if(utils.isEmail(key) == true){
			way = 3;
		}

		// 2、获取admin
        UserInfo user = null;
        if(way == 1) {
			user = userInfoMapper.getById(Long.parseLong(key));
        }
        if(way == 2) {
			user = userInfoMapper.getByName(key);
        }
        if(way == 3) {
			user = userInfoMapper.getByEmail(key);
        }


        // 3、开始验证
        if(user == null){
        	return AjaxJson.getError("无此账号");
        }
        if(utils.isNull(user.getPassword2())) {
        	return AjaxJson.getError("此账号尚未设置密码，无法登陆");
        }
        String md5Password = SystemObject.getPasswordMd5(user.getId(), password);
        if(user.getPassword2().equals(md5Password) == false){
        	return AjaxJson.getError("密码错误");
        }

        // 4、是否禁用
        if(user.getStatus() == 2) {
        	return AjaxJson.getError("此账号已被禁用，如有疑问，请联系管理员");
        }

        // =========== 至此, 已登录成功 ============
        successLogin(user);
        StpUtil.setLoginId(user.getId());
        log.info("loginId{} 登录成功==========",user.getId());

        // 组织返回参数
		SoMap map = new SoMap();
		map.put("user", user);
		map.put("per_list", epRolePermissionService.getPcodeByRid2(user.getRoleId()));
		map.put("tokenInfo", StpUtil.getTokenInfo());
		AjaxJson res = AjaxJson.getSuccessData(map);
		log.info(res.toString());
		return res;
	}


	/**
	 * 指定id的账号成功登录一次 （修改最后登录时间等数据 ）
	 * @param s
	 * @return
	 */
	public int successLogin(UserInfo s){
		String loginIp = WebUtil.getIP(SpringMVCUtil.getRequest());
		log.info("loginIp{}登录一次==========",loginIp);
		int line = epAccAdminMapper.successLogin(s.getId(), loginIp);
		if(line > 0) {
	        s.setLoginIp(loginIp);
	        s.setLoginTime(new Date());
	        s.setLoginCount(s.getLoginCount() + 1);
		}
        return line;
	}

	/**
	 * 修改手机号
	 * @param adminId
	 * @param newPhone
	 * 不用了
	 * @return
	 */
//	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRED)
//	public AjaxJson updatePhone(long adminId, String newPhone) {
//		// 修改admin手机号
//		int line = SP.publicMapper.updateColumnById("sys_admin", "phone", newPhone, adminId);
//		return AjaxJson.getByLine(line);
//	}




}
