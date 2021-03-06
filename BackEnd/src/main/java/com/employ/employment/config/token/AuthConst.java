package com.employ.employment.config.token;

/**
 * 权限码常量  
 * @author kong
 *
 */
public final class AuthConst {

	/**
	 *  私有构造方法 
	 */
	private AuthConst() {
	}
	
	
	// --------------- 代表身份的权限 --------------- 
	
	public static final String R1 = "1"; 			 // 角色_id_超级管理员 最高权限，超管身份的代表
	public static final String HR = "2";		  // 普通管理员
	public static final String R11 = "11"; 		 //学生账号
	public static final String R99 = "99";		  // 进入后台权限，没有此权限无法进入后台管理
	public static final String R121 = "121";		  // 企业hr
	public static final String R1212 = "1212";		  // 企业超级管理员
	public static final String R111 = "111";		  // 测试角色
	
	
	// --------------- 所有权限码 --------------- 

	public static final String AUTH = "auth";		   // 权限管理
	public static final String ROLE_LIST = "role-list";		    // 权限管理 - 角色管理
	public static final String MENU_LIST = "menu-list";		   // 权限管理 - 菜单列表
	public static final String ADMIN_LIST = "admin-list";		   // 权限管理 - 管理员列表
	public static final String ADMIN_ADD = "admin-add";		   // 权限管理 - 管理员添加

	public static final String CONSOLE = "console";		   // 监控中心
	public static final String SQL_CONSOLE = "sql-console";		      // 监控中心 - SQL监控
	public static final String REDIS_CONSOLE = "redis-console";		   // 监控中心 - Redis 控制台
	public static final String APILOG_LIST = "apilog-list";		   // 监控中心 - API 请求日志

	public static final String SP_CFG = "sp-cfg";		  	 // 系统配置
	public static final String SP_CFG_APP = "sp-cfg-app";		  	 // 系统配置 - 系统对公配置
	public static final String SP_CFG_SERVER = "sp-cfg-server";		   // 系统配置 - 服务器私有配置

	public static final String STU_INFO = "stu-info";    //学生表
	public static final String COMP_INFO = "company-info";  //企业信息表
	public static final String COMP_USER = "comp-user";   //企业用户表

//	public static final String RESUME = "resume";  //简历管理
//	public static final String ANNOUNCEMENT = "announcement";  //公告管理
//	public static final String HR_AUTH = "hr-auth";  //公司hr权限管理
//	public static final String JOB_INFO = "job-info";  //职位信息管理
//	public static final String COMP_INFO = "comp-info";  //公司信息修改

}
