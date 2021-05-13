package com.employ.employment.service;

import com.employ.employment.entity.SP;
import com.employ.employment.entity.StuInfo;
import com.employ.employment.entity.UserInfo;
import com.employ.employment.mapper.StuInfoMapper;
import com.employ.employment.mapper.UserInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Zenglr
 * @program: employment
 * @packagename: com.employ.employment.service
 * @Description 学生表操作
 * @create 2021-05-12-6:45 下午
 */
@Service
@Slf4j
public class StuService {

    private final UserInfoMapper userInfoMapper;

    private final StuInfoMapper stuInfoMapper;

    private final EpAdminPasswordService epAdminPasswordService;

    @Autowired
    public StuService(UserInfoMapper userInfoMapper, StuInfoMapper stuInfoMapper, EpAdminPasswordService epAdminPasswordService) {
        this.userInfoMapper = userInfoMapper;
        this.stuInfoMapper = stuInfoMapper;
        this.epAdminPasswordService = epAdminPasswordService;
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
}
