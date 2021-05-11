package com.employ.employment.service;

import com.employ.employment.mapper.EpRolePermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色权限中间表
 * @author Zenglr
 *
 */
@Service
public class EpRolePermissionService {

	private final EpRolePermissionMapper epRolePermissionMapper;

	@Autowired
	public EpRolePermissionService(EpRolePermissionMapper epRolePermissionMapper) {
		this.epRolePermissionMapper = epRolePermissionMapper;
	}


	/**
	 * 获取指定角色的所有权限码 【增加缓存】
	 */
    @Cacheable(value="api_pcode_list", key="#roleId")
    public List<String> getPcodeByRid(long roleId){
    	return epRolePermissionMapper.getPcodeByRoleId(roleId);
    }

	/**
	 * 获取指定角色的所有权限码 (Object类型)  【增加缓存】
	 */
    @Cacheable(value="api_pcode_list2", key="#roleId")
    public List<Object> getPcodeByRid2(long roleId){
		List<String> codeList = epRolePermissionMapper.getPcodeByRoleId(roleId);
		return codeList.stream().map(String::valueOf).collect(Collectors.toList());
    }

    /**
     * [T] 修改角色的一组权限关系	【清除缓存 】
     */
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value= {"api_pcode_list", "api_pcode_list2"}, key="#roleId")
    public int updateRoleMenu(long roleId, String[] pcodeArray){

    	// 万一为空
    	if(pcodeArray == null){
    		pcodeArray = new String[0];
    	}

    	// 先删
    	epRolePermissionMapper.deleteByRoleId(roleId);

    	// 再添加
    	for(String pcode : pcodeArray){
    		epRolePermissionMapper.add(roleId, pcode);
        }

    	// 返回
        return pcodeArray.length;
    }



}
