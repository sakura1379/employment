package com.employ.employment.service;

import cn.dev33.satoken.stp.StpUtil;
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
 * @Description 学生表操作
 * @create 2021-05-12-6:45 下午
 */
@Service
@Slf4j
public class StuService {
    //
    static int applyPageRecord = 10;
    //
    private final UserInfoMapper userInfoMapper;

    private final StuInfoMapper stuInfoMapper;

    private final EpAdminPasswordService epAdminPasswordService;
    //
    private final ApplyInfoMapper applyInfoMapper;

    private final ApplyRedisDao applyRedisDao;

    private final CompanyInfoMapper companyInfoMapper;

    private final JobInfoMapper jobInfoMapper;
    //
    @Autowired
    public StuService(UserInfoMapper userInfoMapper, StuInfoMapper stuInfoMapper,
                      EpAdminPasswordService epAdminPasswordService,
                      ApplyInfoMapper applyInfoMapper, ApplyRedisDao applyRedisDao,
                      CompanyInfoMapper companyInfoMapper, JobInfoMapper jobInfoMapper) {
        this.userInfoMapper = userInfoMapper;
        this.stuInfoMapper = stuInfoMapper;
        this.epAdminPasswordService = epAdminPasswordService;

        //
        this.applyInfoMapper = applyInfoMapper;
        this.applyRedisDao = applyRedisDao;
        this.companyInfoMapper = companyInfoMapper;
        this.jobInfoMapper = jobInfoMapper;
        //
    }

    @Transactional(rollbackFor = Exception.class, propagation= Propagation.REQUIRED)
    public int add(StuInfo s, UserInfo u){
        log.info("set roleId:11========");
        u.setRoleId(11);
        userInfoMapper.add(u);
        // 获取主键
        long id = SP.publicMapper.getPrimarykey();
        log.info("add userInfo success, get id:{}", id);
        // 更改密码（md5与明文）
        epAdminPasswordService.updatePassword(id, u.getPassword2());
        s.setStuNum(id);
        log.info("start insert stuInfo:{}",s);
        return stuInfoMapper.add(s);
    }

    /**
     * 删除申请的职位信息
     * @param stuNum, jobId
     * @return
     */
    @Transactional(rollbackFor = Exception.class, propagation= Propagation.REQUIRED)
    public AjaxJson deleteApplyInfo(long stuNum, long jobId){
        log.info("start delete ApplyInfo======");
        log.info("receive id:{}, jobId:{}", stuNum, jobId);
        int line = 0;

        //判断是否为该同学的职位申请，否则不能修改
        long id = StpUtil.getLoginIdAsLong();
        long currentStuNum = stuInfoMapper.getById(id).getStuNum();
        log.info("stuNum:{}", currentStuNum);
        ApplyInfo oldApply = applyInfoMapper.getByAppId(stuNum, jobId);
        long oldStuNum = stuNum;
        if (currentStuNum != oldStuNum){
            return AjaxJson.getError("不是本人的职位申请信息，不能删除！！");
        }

        //获取对应公司compId
        long compId = jobInfoMapper.getById(jobId).compId;
        //删除redis中索引
        line += applyRedisDao.deleteIndex(String.valueOf(compId), String.valueOf(stuNum), String.valueOf(jobId));

        //删除mysql中数据
        //这里有误！要根据jobId和stuNum两个一起删除
        line += applyInfoMapper.deleteByAppId(stuNum,jobId);
        return AjaxJson.getByLine(line);
    }

    /**
     * 获得当前学生所有的职位申请表信息
     * 不筛选是否通过
     * @return
     */
    public AjaxJson getCurrentStuAppList(long stuNum, int page){
        log.info("getCurrentStuAppList receive stuNum:{}, page:{}",stuNum,page);
//        long stu_id = compUserMapper.getById(stuNum).getCompId();
        log.info("Current stu_id:{}",stuNum);

        //根据页码获得当前学生下的所有申请jobId,stuNum以及总共页码
        List<String> appIdList = applyRedisDao.getApplyByStuNum(stuNum,page);
        long pageCount = applyRedisDao.getApplyPageNumberByStuNum(stuNum);

        //到mysql中查询
        if (!appIdList.isEmpty()){
            log.info("select applyInfos from mysql");
            List<ApplyInfo> applyInfos = applyInfoMapper.selectAllApplyByAppIds(appIdList, stuNum);
            return AjaxJson.getPageData(pageCount, applyInfos, page, applyPageRecord);
        } else {
            return AjaxJson.getError("未查询到，请检查公司id或页码");
        }

    }
    /**
     * 增加职位申请信息
     * @param a
     * @return
     */
    @Transactional(rollbackFor = Exception.class, propagation= Propagation.REQUIRED)
    public AjaxJson addApplyInfo(ApplyInfo a){
        log.info("start addApplyInfo======");

        //获得主键
        long id = StpUtil.getLoginIdAsLong();
        long currentStuNum = stuInfoMapper.getById(id).getStuNum();
        log.info("stuNum:{}", currentStuNum);

        //判断是否为该学生的信息，否则不能修改
        long newStuNum = a.getStuNum();
        if (currentStuNum != newStuNum){
            return AjaxJson.getError("不是本人的申请信息，不能申请！");
        }
        //插入stuNum到redis
        long redisLine1 = applyRedisDao.insertStuIndex(String.valueOf(a.stuNum), String.valueOf(a.jobId));

        //获得jobId对应的compId
        long compId = jobInfoMapper.getById(a.jobId).getCompId();
        log.info("compId:{}",compId);
//
        //插入compId索引到redis
        int redisLine2 = applyRedisDao.insertCompIndex(String.valueOf(compId), String.valueOf(a.jobId));

        //插入mysql
        int line = applyInfoMapper.add(a);
        ApplyInfo applyInfo = applyInfoMapper.getByAppId(a.getStuNum(),a.getJobId());

        return AjaxJson.getSuccessData(applyInfo);
    }
}
