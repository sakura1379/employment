package com.employ.employment.service;

import cn.dev33.satoken.stp.StpUtil;
import com.employ.employment.dao.SeminarRedisDao;
import com.employ.employment.entity.*;
import com.employ.employment.mapper.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Zenglr
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

    private final UserInfoMapper userInfoMapper;

    private final EpAdminPasswordService epAdminPasswordService;

    private final CompanyInfoMapper companyInfoMapper;

    private final CompUserMapper compUserMapper;

    private final SeminarRedisDao seminarRedisDao;

    private final SeminarInfoMapper seminarInfoMapper;

    @Autowired
    public CompService(UserInfoMapper userInfoMapper, EpAdminPasswordService epAdminPasswordService,
                       CompanyInfoMapper companyInfoMapper, CompUserMapper compUserMapper,
                       SeminarRedisDao seminarRedisDao, SeminarInfoMapper seminarInfoMapper) {
        this.userInfoMapper = userInfoMapper;
        this.epAdminPasswordService = epAdminPasswordService;
        this.companyInfoMapper = companyInfoMapper;
        this.compUserMapper = compUserMapper;
        this.seminarRedisDao = seminarRedisDao;
        this.seminarInfoMapper = seminarInfoMapper;
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

    public AjaxJson deleteSeminarInfo(long id, long seminarId){
        log.info("start updateSeminarInfo======");
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
        long pageCount = seminarRedisDao.getPageNumberByCompId(comp_id);

        //到mysql中查询
        if (!seminarIdList.isEmpty()){
            log.info("select seminarInfos from mysql");
            List<SeminarInfo> seminarInfos = seminarInfoMapper.selectAllSeminarBySeminarIds(seminarIdList);
            return AjaxJson.getPageData(pageCount, seminarInfos, page, seminarPageRecord);
        } else {
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

        return AjaxJson.getSuccessData(seminarInfo);
    }

}
