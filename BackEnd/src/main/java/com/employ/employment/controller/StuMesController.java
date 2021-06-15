package com.employ.employment.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.employ.employment.config.token.AuthConst;
import com.employ.employment.entity.*;
import com.employ.employment.mapper.*;
import com.employ.employment.service.StuMesService;
import com.employ.employment.service.StuService;
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

@RestController
@RequestMapping("/mail/")
@Api
@Slf4j
public class StuMesController {

    private final StuMesService stuMesService;

    private final StuMesMapper stuMesMapper;

    private final SeminarInfoMapper seminarInfoMapper;

    private final AnnouncementMapper announcementMapper;

    private final JobInfoMapper jobInfoMapper;

    @Autowired
    public StuMesController(StuMesService stuMesService, StuMesMapper stuMesMapper, SeminarInfoMapper seminarInfoMapper, AnnouncementMapper announcementMapper, JobInfoMapper jobInfoMapper) {
        this.stuMesService = stuMesService;
        this.stuMesMapper = stuMesMapper;
        this.seminarInfoMapper = seminarInfoMapper;
        this.announcementMapper = announcementMapper;
        this.jobInfoMapper= jobInfoMapper;
    }

    /** 增 */
    @PostMapping("add")
    @ApiOperation("新建信件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mailNum", value = "信箱编号", required = false),
            @ApiImplicitParam(name = "stuNum", value = "学生编号", required = false),
            @ApiImplicitParam(name = "infoId", value = "信息编号", required = true),
            @ApiImplicitParam(name = "infoType", value = "信息类型（1=宣讲会信息，2=职位信息，3=公告信息）", required = true, allowableValues = "1,2,3"),
    })
    public AjaxJson add(StuMes s){
        if(s.infoType==3){
            log.info("Start dropTempTable========");
            stuMesMapper.dropTempTable("tem");
            log.info("Start createTempTable========");
            stuMesMapper.createTempTable("tem");
            log.info("Start updateTempTable1========");
            stuMesMapper.updateTempTable1("tem");
            log.info("Start updateTempTable2========");
            stuMesMapper.updateTempTable2("tem",s);
            log.info("Start updateTable========");
            int line=stuMesMapper.updateTable("tem");
            log.info("Start dropTempTable========");
            stuMesMapper.dropTempTable("tem");
            return AjaxJson.getSuccessData(line);
        }
        else if (s.infoType==1||s.infoType==2){
            log.info("Start dropTempTable========");
            stuMesMapper.dropTempTable("tem");
            log.info("Start createTempTable========");
            stuMesMapper.createTempTable("tem");
            long id = StpUtil.getLoginIdAsLong();
            log.info("current user id:{}",id);
            log.info("Start updateTempTable3========");
            stuMesMapper.updateTempTable3("tem",id);
            log.info("Start updateTempTable2========");
            stuMesMapper.updateTempTable2("tem",s);
            log.info("Start updateTable========");
            int line=stuMesMapper.updateTable("tem");
            log.info("Start dropTempTable========");
            stuMesMapper.dropTempTable("tem");
            return AjaxJson.getSuccessData(line);
        }
        return AjaxJson.getError();
    }

//    public AjaxJson add(StuMes s){
//        log.info("Start dropTempTable========");
//        stuMesMapper.dropTempTable("temp");
//        log.info("Start createTempTable========");
//        stuMesMapper.createTempTable("temp");
//        long id = StpUtil.getLoginIdAsLong();
//        log.info("current user id:{}",id);
//        log.info("Start updateTempTable3========");
//        stuMesMapper.updateTempTable3("temp",id);
//        log.info("Start updateTempTable2========");
//        stuMesMapper.updateTempTable2("temp",s);
//        log.info("Start updateTable========");
//        int line=stuMesMapper.updateTable("temp");
//        log.info("Start dropTempTable========");
//        stuMesMapper.dropTempTable("temp");
//        return AjaxJson.getSuccessData(line);
//    }

    /** 删 - 删除某一特定邮箱中的特定信息 */
    @PostMapping("deleteOne")
    @ApiOperation("根据信箱、信件编号删除特定信件")
    public AjaxJson delete(long mailNum,long infoId){
        log.info("Start deleteMailInfo========"+mailNum+","+infoId);
        int line=stuMesMapper.deleteOne(mailNum, infoId);
        return AjaxJson.getByLine(line);
    }

    /** 删 - 删除所有邮箱中的相关信息 */
    @PostMapping("deleteAll")
    @ApiOperation("根据信息编号删除所有相关信件")
    public AjaxJson delete(long infoId){
        log.info("Start deleteMailInfo========"+infoId);
        int line=stuMesMapper.deleteAll(infoId);
        return AjaxJson.getByLine(line);
    }

    /** 改 */
    @PostMapping("update")
    @ApiOperation("修改信箱信息")
    public AjaxJson update(StuMes s){
        log.info("Start updateMailInfo========");
        StpUtil.checkPermission("mail_info");
        long id = StpUtil.getLoginIdAsLong();
        log.info("current user id:{}",id);
        s.setStuNum(id);
        int line = stuMesMapper.update(s);
        return AjaxJson.getByLine(line);
    }

    /** 查 - 根据信件id
    @GetMapping("getById")
    @ApiOperation("根据信件编号查看信件")
    public AjaxJson getCurrent(long id){
        log.info("Start getCurrentMailInfo========");
        StpUtil.checkPermission("mail_info");
//        long id = StpUtil.getLoginIdAsLong();
        log.info("Current mail id:{}",id);
        StuMes s = stuMesMapper.getById(id);
        return AjaxJson.getSuccessData(s);
    } */


    /** 查 - 集合  */
    @GetMapping("getList")
    @ApiOperation("查当前学生用户所有信件")
    AjaxJson getList(){
        log.info("Start getMailList========");
        long stuNum = StpUtil.getLoginIdAsLong();
        StpUtil.checkPermission("mail_info");
        List<StuMes> list = stuMesMapper.getList(stuNum);
        return AjaxJson.getSuccessData(list);
    }

    /** 查 - 根据信件id查标题 */
    @GetMapping("getTitle")
    @ApiOperation("根据信件编号查看信件标题")
    public AjaxJson getTitle(long infoId,long infoType){
        log.info("Start getCurrentMailTitle========");
        StpUtil.checkPermission("mail_info");
        if(infoType==1){
            SeminarInfo seminarInfo = seminarInfoMapper.getById(infoId);
            return AjaxJson.getSuccessData(seminarInfo.seminarTitle);
        }
        if(infoType==2){
            JobInfo jobInfo = jobInfoMapper.getById(infoId);
            return AjaxJson.getSuccessData(jobInfo.jobName);
        }
        if(infoType==3){
            Announcement announcement = announcementMapper.getById((int) infoId);
            return AjaxJson.getSuccessData(announcement.announceTitle);
        }
        return AjaxJson.getError();
    }

    /** 查 - 根据信件id查内容 */
    @GetMapping("getContent")
    @ApiOperation("根据信件编号、类型查看信件内容")
    public AjaxJson getContent(long infoId,long infoType){
        log.info("Start getCurrentMailContent========");
        StpUtil.checkPermission("mail_info");
        if(infoType==1){
            SeminarInfo seminarInfo = seminarInfoMapper.getById(infoId);
            return AjaxJson.getSuccessData(seminarInfo.seminarContent);
        }
        if(infoType==2){
            JobInfo jobInfo = jobInfoMapper.getById(infoId);
            return AjaxJson.getSuccessData(jobInfo.jobCon);
        }
        if(infoType==3){
            Announcement announcement = announcementMapper.getById((int) infoId);
            return AjaxJson.getSuccessData(announcement.announceContent);
        }
        return AjaxJson.getError();
    }
}
