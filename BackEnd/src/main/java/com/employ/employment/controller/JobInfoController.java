package com.employ.employment.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.employ.employment.entity.AjaxJson;
import com.employ.employment.entity.JobInfo;
import com.employ.employment.entity.SeminarInfo;
import com.employ.employment.mapper.CompUserMapper;
import com.employ.employment.mapper.CompanyInfoMapper;
import com.employ.employment.mapper.JobInfoMapper;
import com.employ.employment.service.CompService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @ProjectName: employment
 * @Package: com.employ.employment.controller
 * @ClassName: JobInfoController
 * @Description: [职位信息表]
 * @Author: [clh]
 * @Date: 2021/6/11 15:06
 **/

@RestController
@RequestMapping("/job/")
@Api
@Slf4j
@CrossOrigin
public class JobInfoController {
    private final CompService compService;

    private final CompUserMapper compUserMapper;

    private final CompanyInfoMapper companyInfoMapper;

    private final JobInfoMapper jobInfoMapper;

    @Autowired
    public JobInfoController(CompService compService, CompUserMapper compUserMapper,
                          CompanyInfoMapper companyInfoMapper, JobInfoMapper jobInfoMapper) {
        this.compService = compService;
        this.compUserMapper = compUserMapper;
        this.companyInfoMapper = companyInfoMapper;
        this.jobInfoMapper = jobInfoMapper;
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
        long id = StpUtil.getLoginIdAsLong();
        long compId = compUserMapper.getById(id).getCompId();
        log.info("Current user id:{}",id);
        j.setCompId(compId);
        j.setStatus(1);
        j.setDeliverNum(0);
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
        long id = StpUtil.getLoginIdAsLong();
        log.info("Current user id:{}",id);
        long compId = compUserMapper.getById(id).getCompId();
        //每次修改都要将发布人更改为现在用户
        j.setCompId(compId);
        return compService.updateJobInfo(j);
    }

    /**
     * 删除职位信息
     * */
    @DeleteMapping("deleteJobInfo")
    @ApiOperation("删除职位信息")
    public AjaxJson deleteJobInfo(long jobId){
        log.info("Start deleteJobInfo========");
        log.info("Receive JobId:{}",jobId);
        StpUtil.checkPermission("job_info");
        long id = StpUtil.getLoginIdAsLong();
        return compService.deleteJobInfo(id, jobId);
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

    //感觉要在SearchController中加入查找职位信息

}
