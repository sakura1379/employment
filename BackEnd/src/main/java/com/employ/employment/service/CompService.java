package com.employ.employment.service;

import cn.dev33.satoken.stp.StpUtil;
import com.employ.employment.dao.SeminarRedisDao;
import com.employ.employment.dao.JobRedisDao;
import com.employ.employment.dao.ApplyRedisDao;
import com.employ.employment.entity.*;
import com.employ.employment.mapper.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Zenglr, clh
 * @program: employment
 * @packagename: com.employ.employment.service
 * @Description 企业服务
 * @create 2021-05-13-7:31 下午
 */
@Service
@Slf4j
public class CompService {

    //分页的每一页的结果数
    static int seminarPageRecord = 10;

    //
    static int jobPageRecord = 10;

    static int applyPageRecord = 10;
    //

    private final UserInfoMapper userInfoMapper;

    private final EpAdminPasswordService epAdminPasswordService;

    private final CompanyInfoMapper companyInfoMapper;

    private final CompUserMapper compUserMapper;

    private final SeminarRedisDao seminarRedisDao;

    private final SeminarInfoMapper seminarInfoMapper;
// 我加的
    private final JobInfoMapper jobInfoMapper;

    private final ApplyInfoMapper applyInfoMapper;

    private final JobRedisDao jobRedisDao;

    private final ApplyRedisDao applyRedisDao;
//
    @Autowired
    public CompService(UserInfoMapper userInfoMapper, EpAdminPasswordService epAdminPasswordService,
                       CompanyInfoMapper companyInfoMapper, CompUserMapper compUserMapper,
                       SeminarRedisDao seminarRedisDao, SeminarInfoMapper seminarInfoMapper,
                       JobInfoMapper jobInfoMapper, ApplyInfoMapper applyInfoMapper,
                       JobRedisDao jobRedisDao, ApplyRedisDao applyRedisDao) {
        this.userInfoMapper = userInfoMapper;
        this.epAdminPasswordService = epAdminPasswordService;
        this.companyInfoMapper = companyInfoMapper;
        this.compUserMapper = compUserMapper;
        this.seminarRedisDao = seminarRedisDao;
        this.seminarInfoMapper = seminarInfoMapper;
        //
        this.jobInfoMapper = jobInfoMapper;
        this.applyInfoMapper = applyInfoMapper;
        this.jobRedisDao = jobRedisDao;
        this.applyRedisDao = applyRedisDao;
        //
    }

    /**
     * 新增企业及其用户
     * @param c
     * @param u
     * TODO 企业信息验证
     * @return line 影响的行数
     */
    @Transactional(rollbackFor = Exception.class, propagation= Propagation.REQUIRED)
    public int firstAdd(CompanyInfo c, UserInfo u){
        int line = 0;
        log.info("set roleId:1212========");
        u.setRoleId(1212);
        line = userInfoMapper.add(u);
        // 获取主键
        long id = SP.publicMapper.getPrimarykey();
        log.info("add userInfo success, get id:{}", id);
        // 更改密码（md5与明文）
        epAdminPasswordService.updatePassword(id, u.getPassword2());
        line += companyInfoMapper.add(c);
        // 获取主键
        long compId = SP.publicMapper.getPrimarykey();
        log.info("add CompanyInfo success, get compId:{}",compId);
        // 插入企业用户表
        CompUser compUser = new CompUser();
        compUser.setHrId(id);
        compUser.setCompId(compId);
        log.info("insert into compUser:{}",compUser.toString());
        line += compUserMapper.add(compUser);
        return line;
    }

    /**
     * 添加企业普通hr用户（由企业超级管理员添加）
     * @param u
     * @return
     */
    @Transactional(rollbackFor = Exception.class, propagation= Propagation.REQUIRED)
    public int add(UserInfo u){
        int line = 0;
        log.info("set roleId:121========");
        u.setRoleId(121);
        line = userInfoMapper.add(u);
        // 获取主键
        long id = SP.publicMapper.getPrimarykey();
        log.info("add userInfo success, get id:{}", id);
        // 更改密码（md5与明文）
        epAdminPasswordService.updatePassword(id, u.getPassword2());

        //获取当前用户所在公司compId
        long currentId = StpUtil.getLoginIdAsLong();
        log.info("current user id:{}",currentId);
        CompUser compUser = compUserMapper.getById(currentId);
        long compId = compUser.getCompId();
        log.info("current user compId:{}",compId);

        //插入到compUser表中
        CompUser c = new CompUser();
        c.setHrId(id);
        c.setCompId(compId);
        log.info("insert into compUser:{}",c.toString());
        line += compUserMapper.add(c);

        return line;
    }

    /**
     * 更新宣讲会信息
     * @param s
     * @return
     */
    @Transactional(rollbackFor = Exception.class, propagation= Propagation.REQUIRED)
    public AjaxJson updateSeminarInfo(SeminarInfo s){
        log.info("start updateSeminarInfo======");
        log.info("receive seminarInfo:{}", s.toString());
        int line = 0;
        // 每次修改都要把审核状态变为未审核-1
        s.setApproveStatus(1);

        //判断是否为该公司的宣讲会信息，否则不能修改
        long currentCompId = compUserMapper.getById(s.getHrId()).getCompId();
        SeminarInfo oldSeminar = seminarInfoMapper.getById(s.getSeminarId());
        long seminarCompId = compUserMapper.getById(oldSeminar.getHrId()).getCompId();
        if (currentCompId != seminarCompId){
            return AjaxJson.getError("不是本公司宣讲会信息，不能修改！！");
        }

        //判断标题是否为空，如果不为空则要修改redis索引
        if(s.getSeminarTitle() != null){
            log.info("update title index in redis");
            //redis删除之前索引，插入新的索引
            line += seminarRedisDao.updateTitleIndex(oldSeminar.getSeminarTitle(),
                                                        s.getSeminarTitle(), String.valueOf(s.getSeminarId()));
            log.info("redis line:{}", line);
        }

        //mysql中修改
        line += seminarInfoMapper.update(s);
        return AjaxJson.getByLine(line);

    }

    @Transactional(rollbackFor = Exception.class, propagation= Propagation.REQUIRED)
    public AjaxJson deleteSeminarInfo(long id, long seminarId){
        log.info("start deleteSeminarInfo======");
        log.info("receive id:{}, seminarId:{}", id, seminarId);
        int line = 0;

        //判断是否为该公司的宣讲会信息，否则不能修改
        long currentCompId = compUserMapper.getById(id).getCompId();
        SeminarInfo oldSeminar = seminarInfoMapper.getById(seminarId);
        long seminarCompId = compUserMapper.getById(oldSeminar.getHrId()).getCompId();
        if (currentCompId != seminarCompId){
            return AjaxJson.getError("不是本公司宣讲会信息，不能删除！！");
        }

        //删除redis中索引
        line += seminarRedisDao.deleteIndex(oldSeminar.getSeminarTitle(),
                String.valueOf(currentCompId), String.valueOf(seminarId));

        //删除mysql中数据
        line += seminarInfoMapper.delete(seminarId);
        return AjaxJson.getByLine(line);
    }

    /**
     * 获得当前企业所有的宣讲信息
     * 不需要筛选是否通过
     * @return
     */
    public AjaxJson getCurrentCompSeminarList(long id, int page){
        log.info("getCurrentCompSeminarList receive id:{}, page:{}",id,page);
        long comp_id = compUserMapper.getById(id).getCompId();
        log.info("Current comp_id:{}",comp_id);

        //根据页码获得当前公司下的所有SeminarId以及总共页码
        List<String> seminarIdList = seminarRedisDao.getSeminarByCompId(comp_id,page);
        long dataCount = seminarRedisDao.getPageNumberByCompId(comp_id);

        //到mysql中查询
        if (!seminarIdList.isEmpty()){
            log.info("select seminarInfos from mysql");
            List<SeminarInfo> seminarInfos = seminarInfoMapper.selectAllSeminarBySeminarIds(seminarIdList);
            log.info(AjaxJson.getPageData(dataCount, seminarInfos, page, seminarPageRecord).toString());
            return AjaxJson.getPageData(dataCount, seminarInfos, page, seminarPageRecord);
        } else {
            log.info("未查询到，请检查公司id或页码");
            return AjaxJson.getError("未查询到，请检查公司id或页码");
        }

    }

    /**
     * 增加宣讲会信息
     * @param s
     * @return
     */
    @Transactional(rollbackFor = Exception.class, propagation= Propagation.REQUIRED)
    public AjaxJson addSeminar(SeminarInfo s){
        //插入mysql
        int line = seminarInfoMapper.add(s);
        //获得主键
        long seminarId = SP.publicMapper.getPrimarykey();
        log.info("seminarId:{}", seminarId);

        //插入标题索引到redis
        long redisLine1 = seminarRedisDao.insertIndex(s.seminarTitle, String.valueOf(seminarId));

        //获得hrId对应的compId
        long compId = compUserMapper.getById(s.getHrId()).getCompId();
        log.info("compId:{}",compId);

        //插入compId索引到redis
        int redisLine2 = seminarRedisDao.insertCompIndex(String.valueOf(compId), String.valueOf(seminarId));

        SeminarInfo seminarInfo = seminarInfoMapper.getById(seminarId);
        log.info(AjaxJson.getSuccessData(seminarInfo).toString());
        return AjaxJson.getSuccessData(seminarInfo);
    }

//
    /**
     * 增加职位信息
     * @param j
     * @return
     */
    @Transactional(rollbackFor = Exception.class, propagation= Propagation.REQUIRED)
    public AjaxJson addJob(JobInfo j){

        //插入mysql
        int line = jobInfoMapper.add(j);
        //获得主键
        long jobId = SP.publicMapper.getPrimarykey();
        log.info("jobId:{}", jobId);

        //获得compId
        long compId = j.getCompId();
        //插入标题索引到redis
        long redisLine2 = jobRedisDao.insertIndex(j.getJobName(), String.valueOf(jobId));

//        //获得hrId对应的compId
        log.info("compId:{}",compId);
        String compName = companyInfoMapper.getById(compId).getCompName();
        j.setCompName(compName);
        //插入公司名称索引到redis
        long redisLine3 = jobRedisDao.insertIndex(j.getCompName(), String.valueOf(jobId));
//
        //插入compId索引到redis
        int redisLine1 = jobRedisDao.insertCompIndex(String.valueOf(compId), String.valueOf(jobId));

        JobInfo jobInfo = jobInfoMapper.getById(jobId);

        return AjaxJson.getSuccessData(jobInfo);
    }

    /**
     * 删除职位信息
     * @param id, jobId
     * @return
     */
    @Transactional(rollbackFor = Exception.class, propagation= Propagation.REQUIRED)
    public AjaxJson deleteJobInfo(long id, long jobId){
        log.info("start delete JobInfo======");
        log.info("receive hr id:{}, jobId:{}", id, jobId);
        int line = 0;

        //判断是否为该公司的职位信息，否则不能修改
        long currentCompId = compUserMapper.getById(id).getCompId();
        JobInfo oldJob = jobInfoMapper.getById(jobId);
        long jobCompId = oldJob.getCompId();
        if (currentCompId != jobCompId){
            return AjaxJson.getError("不是本公司职位信息，不能删除！！");
        }

        //删除redis中 公司名称—职位 职位名称—职位 公司id-职位索引
        line += jobRedisDao.deleteIndex(String.valueOf(currentCompId), String.valueOf(jobId),String.valueOf(oldJob.jobName),String.valueOf(oldJob.compName));

        //删除mysql中数据
        line += jobInfoMapper.delete(jobId);
        return AjaxJson.getByLine(line);
    }

    /**
     * 更新职位信息表信息
     * @param j
     * @return
     */
    @Transactional(rollbackFor = Exception.class, propagation= Propagation.REQUIRED)
    public AjaxJson updateJobInfo(JobInfo j){
        log.info("start update JobInfo======");
        log.info("receive jobInfo:{}", j.toString());
        int line = 0;
        int line2 = 0;
        // 每次修改都要把审核状态变为未审核 1
        // 审核状态 (1=未审核, 2=审核通过, 3=审核不通过)
        j.setApproveStatus(1);

        //判断是否为该公司的职位信息，否则不能修改
        //要改成的job的公司id
        long currentCompId = j.getCompId();
        //原来的job
        JobInfo oldJob = jobInfoMapper.getById(j.getJobId());
        //原来的job的公司id
        long jobCompId = oldJob.getCompId();
        if (currentCompId != jobCompId){
            return AjaxJson.getError("==不是本公司职位信息，不能修改==");
        }

        //判断标题是否为空，如果不为空则要修改redis索引
        if(j.getJobName()!= null){
            log.info("update title index in redis");
            //redis删除之前索引，插入新的索引
            line += jobRedisDao.updateTitleIndex(oldJob.getJobName(),
                    j.getJobName(), String.valueOf(j.getJobId()));
            log.info("redis line:{}", line);
        }

        //mysql中修改
        line += jobInfoMapper.update(j);
//        if(j.status==2)
//        {
//            //删除redis中索引
//            line2 += jobRedisDao.deleteIndex(String.valueOf(currentCompId), String.valueOf(j.jobId),String.valueOf(j.jobName),String.valueOf(j.compName));
//        }
        return AjaxJson.getByLine(line);

    }


    /**
     * 获得当前企业所有的职位信息
     * 不筛选是否通过
     * @return
     */
    public AjaxJson getCurrentCompJobList(long id, int page){
        log.info("getCurrentCompJobList receive comp_id:{}, page:{}",id,page);
        long comp_id = compUserMapper.getById(id).getCompId();
        log.info("Current comp_id:{}",comp_id);

        //根据页码获得当前公司下的所有jobId以及总共页码
        List<String> jobIdList= jobRedisDao.getJobByCompId(comp_id,page);
        long dataCount = jobRedisDao.getJobPageNumberByCompId(comp_id);

        //到mysql中查询
        if (!jobIdList.isEmpty()){
            log.info("select jobInfos from mysql");
            List<JobInfo> jobInfos = jobInfoMapper.selectAllJobByJobIds(jobIdList);
            log.info(AjaxJson.getPageData(dataCount, jobInfos, page, jobPageRecord).toString());
            return AjaxJson.getPageData(dataCount, jobInfos, page, jobPageRecord);
        } else {
            log.info("未查询到，请检查公司id或页码");
            return AjaxJson.getError("未查询到，请检查公司id或页码");
        }

    }

    /**
     * 获得当前企业所有的职位申请表信息
     * 不筛选是否通过
     * @return
     */
    public AjaxJson getCurrentCompAppList(long id, int page){
        log.info("getCurrentCompAppList receive comp_id:{}, page:{}",id,page);
        long comp_id = compUserMapper.getById(id).getCompId();
        log.info("Current comp_id:{}",comp_id);

        //根据页码获得当前公司下的所有申请jobId,stuNum以及总共页码
        List<String> appIdList = applyRedisDao.getApplyByCompId(comp_id,page);
        long dataCount = applyRedisDao.getApplyPageNumberByCompId(comp_id);

        //到mysql中查询
        if (!appIdList.isEmpty()){
            log.info("select applyInfos from mysql");
            List<ApplyInfo> applyInfos = applyInfoMapper.selectAllApplyByJobIds(appIdList);
            log.info(AjaxJson.getPageData(dataCount, applyInfos, page, applyPageRecord).toString());
            return AjaxJson.getPageData(dataCount, applyInfos, page, applyPageRecord);
        } else {
            log.info("未查询到，请检查公司id或页码");
            return AjaxJson.getError("未查询到，请检查公司id或页码");
        }

    }

    /**
     * 更新职位申请信息表信息
     * @param a
     * @return
     */
    @Transactional(rollbackFor = Exception.class, propagation= Propagation.REQUIRED)
    public AjaxJson updateApplyInfo(ApplyInfo a){
        log.info("start update ApplyInfo======");
        log.info("receive ApplyInfo:{}", a.toString());
        int line = 0;
//        // 每次修改都要把审核状态变为未审核 1
//        // 审核状态 (1=未审核, 2=审核通过, 3=审核不通过)
//        a.setApproveStatus(1);

        //判断是否为该公司的职位信息，否则不能修改
        //要改成的job的公司id
        long currentCompId = jobInfoMapper.getById(a.getJobId()).getCompId();
        //原来的job
        ApplyInfo oldApply = applyInfoMapper.getByAppId(a.getStuNum(),a.getJobId());
        //原来的job的公司id
        long jobCompId = jobInfoMapper.getById(oldApply.getJobId()).getCompId();
        if (currentCompId != jobCompId){
            return AjaxJson.getError("==不是本公司职位信息，不能修改==");
        }

//        //判断标题是否为空，如果不为空则要修改redis索引
//        if(s.getSeminarTitle() != null){
//            log.info("update title index in redis");
//            //redis删除之前索引，插入新的索引
//            line += seminarRedisDao.updateTitleIndex(oldSeminar.getSeminarTitle(),
//                    s.getSeminarTitle(), String.valueOf(s.getSeminarId()));
//            log.info("redis line:{}", line);
//        }

        //获取到原来的最新状态，将原来的最新状态改成现在的历史状态
        a.setApplyStatus(applyInfoMapper.getByAppId(a.getStuNum(),a.getJobId()).getNewApplyStatus());

        //mysql中修改
        line += applyInfoMapper.update(a);
        return AjaxJson.getByLine(line);

    }


}
