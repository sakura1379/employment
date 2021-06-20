package com.employ.employment.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.employ.employment.entity.AjaxJson;
import com.employ.employment.entity.JobInfo;
import com.employ.employment.entity.SoMap;
import com.employ.employment.entity.StuFavor;
import com.employ.employment.mapper.JobInfoMapper;
import com.employ.employment.mapper.StuFavorMapper;
import com.employ.employment.mapper.StuMesMapper;
import com.employ.employment.service.StuFavorService;
import com.employ.employment.service.StuMesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favor/")
@Api
@Slf4j
@CrossOrigin
public class StuFavorController {
    private final StuFavorService stuFavorService;

    private final StuFavorMapper stuFavorMapper;

    private final JobInfoMapper jobInfoMapper;

    @Autowired
    public StuFavorController(StuFavorService stuFavorService, StuFavorMapper stuFavorMapper, JobInfoMapper jobInfoMapper) {
        this.stuFavorService = stuFavorService;
        this.stuFavorMapper = stuFavorMapper;
        this.jobInfoMapper = jobInfoMapper;
    }

    /** 增 */
    @PostMapping("add")
    @ApiOperation("新建收藏项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jobId", value = "职位信息编号", required = true)
    })
    public AjaxJson add(long jobId){
        log.info("Start addFavorInfo========");
        StpUtil.checkPermission("favor_info");
        long id = StpUtil.getLoginIdAsLong();
        long compId=stuFavorMapper.getCompId(jobId);
        int line=stuFavorMapper.add(jobId,compId,id);
        return AjaxJson.getSuccessData(line);
    }

    /** 删 */
    @PostMapping("delete")
    @ApiOperation("根据收藏编号删除收藏项")
    public AjaxJson delete(long favorNum){
        log.info("Start deleteFavorInfo========"+favorNum);
        int line=stuFavorMapper.delete(favorNum);
        return AjaxJson.getByLine(line);
    }

    /** 改 */
    @PostMapping("update")
    @ApiOperation("根据收藏编号修改收藏信息[不需要]")
    public AjaxJson update(StuFavor s){
        log.info("Start updateFavorInfo========");
//        StpUtil.checkPermission("favor_info");
        long id = StpUtil.getLoginIdAsLong();
        log.info("current user id:{}",id);
        s.setStuNum(id);
        int line = stuFavorMapper.update(s);
        return AjaxJson.getByLine(line);
    }

    /** 查 - 根据信件id */
    @GetMapping("getById")
    @ApiOperation("根据收藏编号查看职位信息")
    public AjaxJson getCurrent(long id){
        log.info("Start getCurrentFavorInfo========");
        StpUtil.checkPermission("favor_info");
//        long id = StpUtil.getLoginIdAsLong();
        log.info("Current favor id:{}",id);
        StuFavor s = stuFavorMapper.getById(id);
        JobInfo jobInfo = jobInfoMapper.getById(s.jobId);
        return AjaxJson.getSuccessData(jobInfo);
    }

    /** 查 - 集合  */
    @GetMapping("getList")
    @ApiOperation("查当前学生用户所有收藏信息")
    AjaxJson getList(){
        log.info("Start getFavorList========");
        StpUtil.checkPermission("favor_info");
        long id = StpUtil.getLoginIdAsLong();
        log.info("Current user id:{}",id);
        List<Long> jobIds = stuFavorMapper.getJobList(id);
        List<String> jobIds1 = new ArrayList<String>();
        for(Long x: jobIds){
            String temp = ""+x;
            jobIds1.add(temp);
        }
        List<JobInfo> list = jobInfoMapper.selectAllJobByJobIds(jobIds1);
        return AjaxJson.getSuccessData(list);
    }
}
