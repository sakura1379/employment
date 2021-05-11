//package com.employ.employment.service;
//
//import com.employ.employment.entity.SP;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.stereotype.Service;
//
///**
// * 配置类：sp_cfg
// * @author Zenglr
// *
// */
//@Service
//public class SpCfgService {
//
//
//	/** 获得cfg_value 根据cfgName */
//	@Cacheable(value="sp_cfg_", key="#cfgName")
//	public String getCfgValue(String cfgName){
//		return SP.publicMapper.getColumnByWhere("sp_cfg", "cfg_value", "cfg_name", cfgName);
//	}
//
//
//	/** 修改cfg_value 根据cfgName */
//	@CacheEvict(value="sp_cfg_", key="#cfgName")
//	public int updateCfgValue(String cfgName, String cfgValue){
//		return SP.publicMapper.updateColumnBy("sp_cfg", "cfg_value", cfgValue, "cfg_name", cfgName);
//	}
//
//
//
//
//
//
//
//
//}
