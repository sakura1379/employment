//package com.employ.employment.controller;
//
//import cn.dev33.satoken.stp.StpUtil;
//import com.employ.employment.config.token.AuthConst;
//import com.employ.employment.service.SpAdminPasswordService;
//import com.employ.employment.service.SpCfgService;
//import com.employ.employment.entity.AjaxJson;
//import io.swagger.annotations.Api;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * 系统配置相关
// * @author Zenglr
// *
// */
//@RestController
//@RequestMapping("/SpCfg/")
//@Api
//public class SpCfgController {
//
//	private final SpCfgService sysCfgService;
//
//	@Autowired
//	public SpCfgController(SpCfgService sysCfgService) {
//		this.sysCfgService = sysCfgService;
//	}
//
//
//	/** 返回指定【cfgName】配置信息 */
//	@GetMapping("getCfg")
//	public AjaxJson getCfg(String cfgName){
//		StpUtil.checkPermission(AuthConst.SP_CFG);
//		return AjaxJson.getSuccessData(sysCfgService.getCfgValue(cfgName));
//	}
//
//	/** 修改指定【cfgName】配置信息  */
//	@PostMapping("updateCfg")
//	public AjaxJson updateCfg(String cfgName, String cfgValue){
//		StpUtil.checkPermission(AuthConst.SP_CFG);
//		int a=sysCfgService.updateCfgValue(cfgName, cfgValue);
//		return AjaxJson.getByLine(a);
//	}
//
//
//	/** 返回应用配置信息 （对公开放的） */
//	@GetMapping("appCfg")
//	public AjaxJson appCfg(){
//		return AjaxJson.getSuccessData(sysCfgService.getCfgValue("app_cfg"));
//	}
//
//
//
//
//
//
//}
