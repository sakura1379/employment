package com.employ.employment.controller;

import com.employ.employment.dao.TimeListMongoDao;
import com.employ.employment.entity.AjaxJson;
import com.employ.employment.entity.JobInfo;
import com.employ.employment.mapper.JobInfoMapper;
import com.employ.employment.service.SearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Zenglr, clh
 * @program: employment
 * @packagename: com.employ.employment.controller
 * @Description 首页查找信息接口，包括查找职位信息、宣讲会信息、公告信息等
 * @create 2021-05-16-8:46 下午
 */
@RestController
@RequestMapping("/search/")
@Api
@Slf4j
@CrossOrigin
public class SearchController {

    private final SearchService searchService;

    private final TimeListMongoDao timeListMongoDao;

    private final JobInfoMapper jobInfoMapper;

    @Autowired
    public SearchController(SearchService searchService, TimeListMongoDao timeListMongoDao,
                            JobInfoMapper jobInfoMapper) {
        this.searchService = searchService;
        this.timeListMongoDao = timeListMongoDao;
        this.jobInfoMapper = jobInfoMapper;

    }

    @GetMapping("getSeminarList")
    @ApiOperation("根据检索词查找所有的审核通过的宣讲会信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "query", value = "检索词 可加空格", required = true),
            @ApiImplicitParam(name = "page", value = "页码", required = true),
            @ApiImplicitParam(name = "sortType", value = "排序类型【1为按时间排，2为按匹配度排】", required = true)
    })
    public AjaxJson getSeminarList(String query, int page, int sortType){
        log.info("Start getSeminarList========");
        return searchService.getSeminarList(query, page, sortType);
    }

    @GetMapping("getJobList")
    @ApiOperation("根据检索词查找所有的审核通过的职位信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "query", value = "检索词 可加空格", required = true),
            @ApiImplicitParam(name = "page", value = "页码", required = true),
            @ApiImplicitParam(name = "sortType", value = "排序类型【1为按时间排，2为按匹配度排】", required = true)
    })
    public AjaxJson getJobList(String query, int page, int sortType){
        log.info("Start getJobList========");
        return searchService.getJobList(query, page, sortType);
    }

    @GetMapping("getAnnouncementList")
    @ApiOperation("根据公告类型搜索所有公告信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "announceType", value = "公告类型 (1=系统公告, 2=经验分享)", required = true),
            @ApiImplicitParam(name = "page", value = "页码", required = true)
    })
    public AjaxJson getAnnouncementList(Integer announceType, int page) {
        return searchService.getAnnouncementList(announceType, page);
    }

    @GetMapping("getTimeList")
    @ApiOperation("根据公司名称搜索时间轴信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "compName", value = "公司名称", required = true)
    })
    public AjaxJson getTimeLineList(String compName) {
        log.info("start getTimeLineList======");
        log.info("receive compName:{}", compName);
        return timeListMongoDao.getTimeList(compName);
    }


    /**
     * 查询所有公告信息
     */
    @GetMapping("getAllAnnouncement")
    @ApiOperation("分页查询所有公告")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true)
    })
    public AjaxJson getAllAnnouncement(int page) {
        log.info("start getAllAnnouncement=======");
        log.info("receive page:{}", page);
        return searchService.getAllAnnouncement(page);
    }


    /**
     * 根据检索词查询所有公告信息
     */
    @GetMapping("getAnnouncementByQuery")
    @ApiOperation("根据检索词和公告类型分页查询所有公告")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "query", value = "检索词", required = true),
            @ApiImplicitParam(name = "page", value = "页码", required = true),
            @ApiImplicitParam(name = "announceType", value = "公告类型 (1=系统公告, 2=经验分享)", required = true)
    })
    public AjaxJson getAnnouncementByQuery(int page, int announceType, String query) {
        log.info("start getAllAnnouncement=======");
        log.info("receive page:{}, announceType:{}, query:{}", page, announceType, query);
        return searchService.getAllAnnouncementByQuery(page, announceType, query);
    }

    /**
     * 根据JobId查询对应的岗位详情
     */
    @GetMapping("getJobInfobyJobId")
    @ApiOperation("根据JobId查询对应的岗位详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jobId", value = "职位编号", required = true)
    })
    public AjaxJson getJobInfobyJobId(long jobId) {
        log.info("start getJobInfobyJobId=======");
        log.info("receive jobId:{}", jobId);
        JobInfo j = jobInfoMapper.getById(jobId);
        return AjaxJson.getSuccessData(j);
    }
}
