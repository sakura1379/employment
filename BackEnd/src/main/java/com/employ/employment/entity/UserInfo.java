package com.employ.employment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Model: 用户表
 * @author Zenglr
 */
@Data
public class UserInfo implements Serializable  {

	private static final long serialVersionUID = 1L;


	/** id，--主键、自增 */
	public Long id;

	/** admin名称 */
	public String name;

	/** 头像地址 */
//	public String avatar;

	/** 密码 */
	public String password;

	/** 明文密码 */
//	public String pw;

	/** 手机号 */
//	public String phone;

	/** 邮箱 */
	public String mail;

	/** 所属角色id */
	public Integer roleId;

	/** 账号状态(1=正常, 2=禁用) */
	public Integer status;

//	/** 创建自哪个管理员 */
//	public Long createByAid;

	/** 创建时间 */
	public Date createTime;

	/** 上次登陆时间 */
	public Date loginTime;

	/** 上次登陆IP */
	public String loginIp;

	/** 登陆次数 */
	public Integer loginCount;


	// -------- 额外字段

	/** 所属角色名称   */
	private String roleName;


	/** 防止密码被传递到前台  */
    public String getPassword(){
    	return "********";
    }
    /** 获取真实密码   */
    @JsonIgnore()
    public String getPassword2(){
    	return this.password;
    }

}
