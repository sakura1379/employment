package com.employ.employment.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.employ.employment.entity.*;
import com.employ.employment.mapper.*;
import com.employ.employment.service.AnnouncementService;
import com.employ.employment.service.CompService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Zenglr
 * @program: employment
 * @packagename: com.employ.employment.controller
 * @Description 管理员接口，包括:管理员发布公告、审核招聘信息、审核宣讲会信息
 * @create 2021-05-15-5:30 下午
 */
@RestController
@RequestMapping("/admin/")
@Api
@Slf4j
public class AdminController {

    private final SeminarInfoMapper seminarInfoMapper;

    private final JobInfoMapper jobInfoMapper;

    private final AnnouncementService announcementService;

    private final AnnouncementMapper announcementMapper;

    @Autowired
    public AdminController(SeminarInfoMapper seminarInfoMapper, JobInfoMapper jobInfoMapper,
                           AnnouncementService announcementService, AnnouncementMapper announcementMapper) {
        this.seminarInfoMapper = seminarInfoMapper;
        this.jobInfoMapper = jobInfoMapper;
        this.announcementService = announcementService;
        this.announcementMapper = announcementMapper;
    }


    @PostMapping("reviewSeminar")
    @ApiOperation("管理员审核宣讲会信息，1为未审核，2为通过，3为不通过")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seminarId", value = "宣讲会信息编号", required = true),
            @ApiImplicitParam(name = "approveStatus", value = "审核状态", allowableValues = "2,3", required = true)
    })
    public AjaxJson reviewSeminar(SeminarInfo seminarInfo){
        log.info("Start reviewSeminarInfo========");
        log.info("Receive seminarInfo:{}",seminarInfo.toString());
        StpUtil.checkPermission("review");
        long id = StpUtil.getLoginIdAsLong();
        log.info("Current user id:{}",id);
        int line = seminarInfoMapper.update(seminarInfo);
        return AjaxJson.getByLine(line);
    }

    @GetMapping("getAllSeminar")
    @ApiOperation("管理员查看所有宣讲会信息")
    public AjaxJson getAllSeminar(int page){
        log.info("Start getAllSeminarInfo========");
        StpUtil.checkPermission("review");
        long id = StpUtil.getLoginIdAsLong();
        log.info("Current user id:{}",id);
        SoMap so = SoMap.getRequestSoMap();
        List<SeminarInfo> seminarInfos = seminarInfoMapper.getList(so.startPage());
        return AjaxJson.getPageData(so.getDataCount(), seminarInfos, page, 10);
    }

    @PostMapping("reviewJobInfo")
    @ApiOperation("管理员审核职位信息，1为未审核，2为通过，3为不通过")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jobId", value = "职位信息编号", required = true),
            @ApiImplicitParam(name = "approveStatus", value = "审核状态", allowableValues = "2,3", required = true)
    })
    public AjaxJson reviewJobInfo(JobInfo jobInfo){
        log.info("Start reviewJobInfo========");
        log.info("Receive jobInfo:{}",jobInfo.toString());
        StpUtil.checkPermission("review");
        long id = StpUtil.getLoginIdAsLong();
        log.info("Current user id:{}",id);
        int line = jobInfoMapper.update(jobInfo);
        return AjaxJson.getByLine(line);
    }


    /**
     * 增
     */
    @PostMapping("addAnnouncement")
    @ApiOperation("新增公告")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "announceTitle", value = "公告标题", required = true),
            @ApiImplicitParam(name = "announceContent", value = "公告内容", required = true),
            @ApiImplicitParam(name = "announceType", value = "公告类型 (1=系统公告, 2=经验分享)", required = true)
    })
    public AjaxJson addAnnouncement(Announcement announcement) {
        log.info("Start addAnnouncement========");
        log.info("Receive announcement:{}",announcement.toString());
        StpUtil.checkPermission("announcement");
        long id = StpUtil.getLoginIdAsLong();
        log.info("Current user id:{}",id);
        announcement.setAdminId(id);
        return announcementService.add(announcement);
    }


    /**
     * 改
     */
    @PostMapping("updateAnnouncement")
    @ApiOperation("修改公告信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "announceId", value = "公告编号", required = true)
    })
    public AjaxJson updateAnnouncement(Announcement announcement) {
        log.info("Start updateAnnouncement========");
        log.info("Receive announcement:{}",announcement.toString());
        StpUtil.checkPermission("announcement");
        long id = StpUtil.getLoginIdAsLong();
        log.info("Current user id:{}",id);
        announcement.setAdminId(id);
        return announcementService.update(announcement);
    }

    /**
     * 查公告信息 - 根据id
     */
    @GetMapping("getAnnouncementById")
    @ApiOperation("根据id查询公告信息")
    public AjaxJson getAnnouncementById(long id) {
        log.info("Start getAnnouncementById========");
        log.info("Receive id:{}",id);
        Announcement a = announcementMapper.getById(id);
        return AjaxJson.getSuccessData(a);
    }

}
