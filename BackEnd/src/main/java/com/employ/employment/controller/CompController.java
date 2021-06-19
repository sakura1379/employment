package com.employ.employment.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import com.employ.employment.config.token.AuthConst;
import com.employ.employment.entity.*;
import com.employ.employment.mapper.*;
import com.employ.employment.service.CompService;
import com.employ.employment.service.StuService;
import com.sun.xml.internal.bind.v2.TODO;
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
 * @author Zenglr, clh
 * @program: employment
 * @packagename: com.employ.employment.controller
 * @Description 企业用户、企业信息接口
 * @create 2021-05-13-7:26 下午
 */
@RestController
@RequestMapping("/comp/")
@Api
@Slf4j
@CrossOrigin
public class CompController {

    private final CompService compService;

    private final CompUserMapper compUserMapper;

    private final CompanyInfoMapper companyInfoMapper;

    private final SeminarInfoMapper seminarInfoMapper;
    //
    private final JobInfoMapper jobInfoMapper;

    private final ApplyInfoMapper applyInfoMapper;

    private final StuInfoMapper stuInfoMapper;
    //

    @Autowired
    public CompController(CompService compService, CompUserMapper compUserMapper,
                          CompanyInfoMapper companyInfoMapper, SeminarInfoMapper seminarInfoMapper,
                          JobInfoMapper jobInfoMapper, ApplyInfoMapper applyInfoMapper,
                          StuInfoMapper stuInfoMapper) {
        this.compService = compService;
        this.compUserMapper = compUserMapper;
        this.companyInfoMapper = companyInfoMapper;
        this.seminarInfoMapper = seminarInfoMapper;
        this.jobInfoMapper = jobInfoMapper;
        this.applyInfoMapper = applyInfoMapper;
        this.stuInfoMapper = stuInfoMapper;
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
            @ApiImplicitParam(name = "compSize", value = "企业规模", required = true, allowableValues = "1,2,3,4"),
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
    public AjaxJson addSeminar(SeminarInfo s){
        log.info("Start addSeminarInfo========");
        log.info("Receive seminarInfo:{}",s);
        StpUtil.checkPermission("seminar_info");
        long id = StpUtil.getLoginIdAsLong();
        log.info("Current user id:{}",id);
        s.setHrId(id);
        return compService.addSeminar(s);
    }

    /** 改 */
    @PostMapping("updateSeminar")
    @ApiOperation("修改宣讲会信息,修改标题或内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seminarId", value = "宣讲会信息编号", required = true),
            @ApiImplicitParam(name = "seminarTitle", value = "宣讲会标题"),
            @ApiImplicitParam(name = "seminarContent", value = "宣讲会内容")
    })
    public AjaxJson updateSeminar(SeminarInfo s){
        log.info("Start updateSeminarInfo========");
        log.info("Receive seminarInfo:{}",s);
        StpUtil.checkPermission("seminar_info");
        long id = StpUtil.getLoginIdAsLong();
        log.info("Current user id:{}",id);
        //每次修改都要将发布人更改为现在用户
        s.setHrId(id);
        return compService.updateSeminarInfo(s);
    }

    @DeleteMapping("deleteSeminar")
    @ApiOperation("删除宣讲会信息")
    public AjaxJson deleteSeminar(long seminarId){
        log.info("Start deleteSeminarInfo========");
        log.info("Receive SeminarId:{}",seminarId);
        StpUtil.checkPermission("seminar_info");
        long id = StpUtil.getLoginIdAsLong();
        return compService.deleteSeminarInfo(id, seminarId);
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

    /**
     * 增 职位信息
     * */
    @PostMapping("addJob")
    @ApiOperation("录入职位信息")
    @ApiImplicitParams({
//            @ApiImplicitParam(name = "compId", value = "公司编号", required = true),
            @ApiImplicitParam(name = "jobName", value = "职位名称", required = true),
            @ApiImplicitParam(name = "jobType", value = "岗位类别", required = true),
            @ApiImplicitParam(name = "jobKind", value = "招聘性质", required = true, allowableValues = "1,2,3"),
            @ApiImplicitParam(name = "jobAddress", value = "工作地点", required = true),
            @ApiImplicitParam(name = "jobCon", value = "职位描述", required = true),
            @ApiImplicitParam(name = "jobDeadline", value = "投递截止日期", required = true),
            @ApiImplicitParam(name = "salary", value = "薪资", required = true)
    })
    public AjaxJson addJob(JobInfo j){
        log.info("Start add jobInfo========");
        log.info("Receive jobInfo:{}",j);
        StpUtil.checkPermission("job_info");
        long hrid = StpUtil.getLoginIdAsLong();
        log.info("Current user id:{}",hrid);
        long comp_id = compUserMapper.getById(hrid).getCompId();
        log.info("Current company id:{}",comp_id);
        j.setCompId(comp_id);
        j.setStatus(1);
        j.setApproveStatus(1);
        return compService.addJob(j);
    }

    /**
     * 改 职位信息
     **/
    @PostMapping("updateJobInfo")
    @ApiOperation("修改职位信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jobId", value = "职位信息编号", required = true),
            @ApiImplicitParam(name = "jobName", value = "职位名称", required = true),
            @ApiImplicitParam(name = "jobType", value = "岗位类别", required = true),
            @ApiImplicitParam(name = "jobKind", value = "招聘性质", required = true, allowableValues = "1,2,3"),
            @ApiImplicitParam(name = "jobAddress", value = "工作地点", required = true),
            @ApiImplicitParam(name = "jobCon", value = "职位描述", required = true),
            @ApiImplicitParam(name = "jobDeadline", value = "投递截止日期", required = true),
            @ApiImplicitParam(name = "salary", value = "薪资", required = true)
    })
    public AjaxJson updateJobInfo(JobInfo j){
        log.info("Start updateJobInfo========");
        log.info("Receive jobInfo:{}",j);
        StpUtil.checkPermission("job_info");
        long hrid = StpUtil.getLoginIdAsLong();
        log.info("Current user id:{}",hrid);
        long comp_id = compUserMapper.getById(hrid).getCompId();
        log.info("Current company id:{}",comp_id);
        j.setCompId(comp_id);
        return compService.updateJobInfo(j);
    }

    /**
     * 删除职位信息
     * 其实没有删除 只是把status 改成2
     * */
    @DeleteMapping("deleteJobInfo")
    @ApiOperation("删除职位信息")
    public AjaxJson deleteJobInfo(long jobId){
        log.info("Start deleteJobInfo========");
        log.info("Receive JobId:{}",jobId);
        StpUtil.checkPermission("job_info");
        long id = StpUtil.getLoginIdAsLong();
//        return compService.deleteJobInfo(id, jobId); 原本的
        JobInfo  j = jobInfoMapper.getById(jobId);
        j.setStatus(2);
        return compService.updateJobInfo(j);
    }

    /**
     * 查找该公司下所有职位信息
     */
    @GetMapping("getCompJobList")
    @ApiOperation("查找该公司下所有的职位信息")
    public AjaxJson getCompJobList(int page){
        log.info("Start getCompJobList========");
        StpUtil.checkPermission("job_info");
        long id = StpUtil.getLoginIdAsLong();
        log.info("Current user id:{}",id);
        return compService.getCurrentCompJobList(id,page);
    }

    /**
     * 查找该公司下所有职位申请信息
     */
    @GetMapping("getCompApplyList")
    @ApiOperation("查找该公司下所有职位申请信息")
    public AjaxJson getCompApplyList(int page){
        log.info("Start getCompApplyList========");
        StpUtil.checkPermission("job_info");
        long id = StpUtil.getLoginIdAsLong();
        log.info("Current user id:{}",id);
        return compService.getCurrentCompAppList(id,page);
    }

    /**
     * 改 职位申请信息
     **/
    @PostMapping("updateApplyInfo")
    @ApiOperation("修改历史申请状态，最近申请状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "stuNum", value = "学生编号", required = true),
            @ApiImplicitParam(name = "jobId", value = "职位信息编号", required = true),
            @ApiImplicitParam(name = "ApplyStatus", value = "历史申请状态", required = true, allowableValues = "1,2,3,4,5,6,7,8"),
            @ApiImplicitParam(name = "newApplyStatus", value = "最近申请状态", required = true, allowableValues = "1,2,3,4,5,6,7,8")
    })
    public AjaxJson updateApplyInfo(ApplyInfo a){
        log.info("Start updateApplyInfo========");
        log.info("Receive applyInfo:{}",a);
        StpUtil.checkPermission("apply_info");
        long id = StpUtil.getLoginIdAsLong();
        log.info("Current user id:{}",id);

//        //每次修改都要将发布人更改为现在用户
//        a.setCompId(id);
        return compService.updateApplyInfo(a);
    }

    /** 查 - 根据id */
    @GetMapping("getStuApplyInfo")
    @ApiOperation("企业用户查看学生信息")
    public AjaxJson getStuApplyInfo(long stuNum){
        log.info("Start getStuApplyInfo========");
//        StpUtil.checkPermission("stu_info");
//        long id = StpUtil.getLoginIdAsLong();
//        log.info("Current user id:{}",id);
        StuInfo s = stuInfoMapper.getById(stuNum);
        return AjaxJson.getSuccessData(s.resume);
    }
}
