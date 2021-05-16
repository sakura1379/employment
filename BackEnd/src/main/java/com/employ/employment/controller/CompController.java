package com.employ.employment.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import com.employ.employment.config.token.AuthConst;
import com.employ.employment.entity.*;
import com.employ.employment.mapper.CompUserMapper;
import com.employ.employment.mapper.CompanyInfoMapper;
import com.employ.employment.mapper.SeminarInfoMapper;
import com.employ.employment.mapper.StuInfoMapper;
import com.employ.employment.service.CompService;
import com.employ.employment.service.StuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Zenglr
 * @program: employment
 * @packagename: com.employ.employment.controller
 * @Description 企业用户、企业信息接口
 * @create 2021-05-13-7:26 下午
 */
@RestController
@RequestMapping("/comp/")
@Api
@Slf4j
public class CompController {

    private final CompService compService;

    private final CompUserMapper compUserMapper;

    private final CompanyInfoMapper companyInfoMapper;

    private final SeminarInfoMapper seminarInfoMapper;

    @Autowired
    public CompController(CompService compService, CompUserMapper compUserMapper, CompanyInfoMapper companyInfoMapper, SeminarInfoMapper seminarInfoMapper) {
        this.compService = compService;
        this.compUserMapper = compUserMapper;
        this.companyInfoMapper = companyInfoMapper;
        this.seminarInfoMapper = seminarInfoMapper;
    }

    /** 增 */
    @PostMapping("firstAdd")
    @ApiOperation("企业新用户注册，录入企业信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "用户名", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true),
            @ApiImplicitParam(name = "mail", value = "邮箱", required = true),
            @ApiImplicitParam(name = "compName", value = "企业名称", required = true),
            @ApiImplicitParam(name = "compIndustry", value = "企业所在行业", required = true),
            @ApiImplicitParam(name = "compSize", value = "企业规模", required = true),
            @ApiImplicitParam(name = "compAddress", value = "企业地址", required = true),
            @ApiImplicitParam(name = "complink", value = "企业官网链接"),
            @ApiImplicitParam(name = "creditcode", value = "统一社会信用代码", required = true),
            @ApiImplicitParam(name = "compEsDate", value = "企业成立日期", required = true),
            @ApiImplicitParam(name = "compIntro", value = "企业介绍", required = true)
    })
    public AjaxJson firstAdd(CompanyInfo c, UserInfo u){
        log.info("Start addNewCompanyInfo========");
        log.info("Receive companyInfo:{}, userInfo:{}",c,u);
        compService.firstAdd(c,u);
        return AjaxJson.getSuccessData(c);
    }

    /** 增 */
    @PostMapping("add")
    @ApiOperation("企业hr注册，由企业超级管理员录入信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "用户名", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true),
            @ApiImplicitParam(name = "mail", value = "邮箱", required = true)
                })
    public AjaxJson add(UserInfo u){
        log.info("Start addNewCompanyHRInfo========");
        log.info("Receive userInfo:{}",u);
        StpUtil.checkPermission("comp_user");
        compService.add(u);
        return AjaxJson.getSuccessData(u);
    }


    /** 改 */
    @PostMapping("update")
    @ApiOperation("企业信息修改，企业超级管理员权限")
    public AjaxJson update(CompanyInfo c){
        log.info("Start updateCompInfo========");
        log.info("Receive companyInfo:{}",c);
        StpUtil.checkPermission("company_info_update");
        long id = StpUtil.getLoginIdAsLong();
        log.info("Current user id:{}",id);
        long comp_id = compUserMapper.getById(id).getCompId();
        log.info("Current comp_id:{}",comp_id);
        c.setCompId(comp_id);
        int line = companyInfoMapper.update(c);
        return AjaxJson.getByLine(line);
    }

    /** 查 - 根据id */
    @GetMapping("getCurrent")
    @ApiOperation("企业用户查看当前企业信息")
    public AjaxJson getCurrent(){
        log.info("Start getCurrentCompInfo========");
        StpUtil.checkPermission("company_info");
        long id = StpUtil.getLoginIdAsLong();
        log.info("Current user id:{}",id);
        long comp_id = compUserMapper.getById(id).getCompId();
        log.info("Current comp_id:{}",comp_id);
        CompanyInfo c = companyInfoMapper.getById(comp_id);
        return AjaxJson.getSuccessData(c);
    }

    /** 增 */
    @PostMapping("addSeminar")
    @ApiOperation("添加宣讲会信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seminarTitle", value = "宣讲会标题", required = true),
            @ApiImplicitParam(name = "seminarContent", value = "宣讲会内容", required = true)
    })
    @Transactional(rollbackFor = Exception.class)
    public AjaxJson addSeminar(SeminarInfo s){
        log.info("Start addSeminarInfo========");
        log.info("Receive seminarInfo:{}",s);
        StpUtil.checkPermission("seminar_info");
        long id = StpUtil.getLoginIdAsLong();
        log.info("Current user id:{}",id);
        s.setHrId(id);
        int line = seminarInfoMapper.add(s);
        s = seminarInfoMapper.getById(SP.publicMapper.getPrimarykey());
        return AjaxJson.getSuccessData(s);
    }

    @DeleteMapping("deleteSeminar")
    @ApiOperation("删除宣讲会信息")
    public AjaxJson deleteSeminar(long seminarId){
        log.info("Start deleteSeminarInfo========");
        log.info("Receive SeminarId:{}",seminarId);
        int line = seminarInfoMapper.delete(seminarId);
        return AjaxJson.getByLine(line);
    }

    @GetMapping("getSeminarList")
    @ApiOperation("查找该公司下所有的宣讲会信息")
    public AjaxJson getSeminarList(int page){
        log.info("Start getSeminarList========");
        StpUtil.checkPermission("seminar_info");
        long id = StpUtil.getLoginIdAsLong();
        log.info("Current user id:{}",id);
        return compService.getCurrentCompSeminarList(id,page);
    }

}
