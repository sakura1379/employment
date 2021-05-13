package com.employ.employment.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.employ.employment.entity.AjaxJson;
import com.employ.employment.entity.SP;
import com.employ.employment.entity.StuInfo;
import com.employ.employment.entity.UserInfo;
import com.employ.employment.mapper.StuInfoMapper;
import com.employ.employment.mapper.UserInfoMapper;
import com.employ.employment.service.EpAdminPasswordService;
import com.employ.employment.service.StuService;
import com.employ.employment.service.UserInfoService;
import com.employ.employment.util.UploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Zenglr
 * @program: employment
 * @packagename: com.employ.employment.controller
 * @Description 学生信息表
 * @create 2021-05-12-6:48 下午
 */
@RestController
@RequestMapping("/stu/")
@Api
@Slf4j
public class StuInfoController {

    private final StuService stuService;

    private final StuInfoMapper stuInfoMapper;

    @Autowired
    public StuInfoController(StuService stuService,StuInfoMapper stuInfoMapper) {
        this.stuService = stuService;
        this.stuInfoMapper = stuInfoMapper;
    }

    /** 增 */
    @PostMapping("add")
    @ApiOperation("学生用户注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "用户名", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true),
            @ApiImplicitParam(name = "mail", value = "邮箱", required = true),
            @ApiImplicitParam(name = "stuName", value = "学生姓名", required = true),
            @ApiImplicitParam(name = "stuGraUniversity", value = "学生学校", required = true),
            @ApiImplicitParam(name = "stuMajor", value = "学生专业", required = true),
            @ApiImplicitParam(name = "stuEducation", value = "学生学历（1=本科,2=硕士研究生", required = true),
            @ApiImplicitParam(name = "stJodKind", value = "求职性质 (1=实习, 2=校招, 3=实习和校招)", required = true),
            @ApiImplicitParam(name = "stuGraduateTime", value = "学生毕业年份 [date]", required = true),
            @ApiImplicitParam(name = "stuTelephone", value = "学生电话号码", required = true),
            @ApiImplicitParam(name = "dreamAddress", value = "期望城市", required = true),
            @ApiImplicitParam(name = "dreamPosition", value = "期望职位类别", required = true)
    })
    public AjaxJson add(StuInfo s, UserInfo u){
        log.info("Start addStuInfo========");
        stuService.add(s, u);
        return AjaxJson.getSuccessData(s);
    }


    /** 改 */
    @PostMapping("update")
    @ApiOperation("学生用户修改个人基本信息")
    public AjaxJson update(StuInfo s){
        log.info("Start updateStuInfo========");
        long id = StpUtil.getLoginIdAsLong();
        int line = stuInfoMapper.update(s,id);
        return AjaxJson.getByLine(line);
    }

    /** 查 - 根据id */
    @GetMapping("getCurrent")
    @ApiOperation("学生用户查看个人信息")
    public AjaxJson getCurrent(){
        log.info("Start getCurrentStuInfo========");
        long id = StpUtil.getLoginIdAsLong();
        log.info("Current user id:{}",id);
        StuInfo s = stuInfoMapper.getById(id);
        return AjaxJson.getSuccessData(s);
    }

    @PostMapping("uploadResume")
    @ApiOperation("上传简历")
    public AjaxJson uploadResume(MultipartFile file){
        log.info("Start uploadResume========");
        log.info("Receive file:{}", file.toString());
        // 验证文件大小 -> 验证后缀 -> 保存到硬盘 -> 地址返回给前端
        UploadUtil.checkFileSize(file);
        UploadUtil.checkSubffix(file.getOriginalFilename(), UploadUtil.uploadConfig.fileSuffix);
        String httpUrl = UploadUtil.saveFile(file, UploadUtil.uploadConfig.fileFolder);
        long id = StpUtil.getLoginIdAsLong();
        log.info("Current user id:{}",id);
//        更改当前学生信息表中的简历信息
//        未完成==========================

        return AjaxJson.getSuccessData(httpUrl);
    }

}
