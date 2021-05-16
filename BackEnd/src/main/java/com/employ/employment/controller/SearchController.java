package com.employ.employment.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.employ.employment.entity.AjaxJson;
import com.employ.employment.mapper.CompUserMapper;
import com.employ.employment.mapper.CompanyInfoMapper;
import com.employ.employment.mapper.SeminarInfoMapper;
import com.employ.employment.service.CompService;
import com.employ.employment.service.SearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Zenglr
 * @program: employment
 * @packagename: com.employ.employment.controller
 * @Description 首页查找信息接口，包括查找职位信息、宣讲会信息、公告信息等
 * @create 2021-05-16-8:46 下午
 */
@RestController
@RequestMapping("/search/")
@Api
@Slf4j
public class SearchController {

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
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
}
