package com.employ.employment.service;

import com.employ.employment.entity.JobInfo;
import com.employ.employment.entity.SP;
import com.employ.employment.entity.UserInfo;
import com.employ.employment.mapper.JobInfoMapper;
import com.employ.employment.mapper.StuInfoMapper;
import com.employ.employment.mapper.UserInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * All rights Reserved, Designed By www.super-master.cn
 *
 * @ProjectName: employment
 * @Package: com.employ.employment.service
 * @ClassName: JobInfoService
 * @Description: [职位信息表]
 * @Author: [Le]
 * @Date: 2021/6/10 21:52
 **/

@Service
@Slf4j
public class JobInfoService {

    private final JobInfoMapper jobInfoMapper;

    @Autowired
    public JobInfoService(JobInfoMapper jobInfoMapper) {
        this.jobInfoMapper = jobInfoMapper;
    }

//    /**
//     * 添加一个职位
//     * @param job
//     * @return
//     */
//    @Transactional(rollbackFor = Exception.class, propagation= Propagation.REQUIRED)
//    public int add(JobInfo j) {
//        int line = 0;
//        log.info("set roleId:121========");
//        u.setRoleId(121);
//        line = userInfoMapper.add(u);
//        // 获取主键
//        long id = SP.publicMapper.getPrimarykey();
//        log.info("add userInfo success, get id:{}", id);
//    }
}
