package com.employ.employment.service;

import com.employ.employment.dao.JobRedisDao;
import com.employ.employment.dao.SeminarRedisDao;
import com.employ.employment.entity.*;
import com.employ.employment.mapper.*;
import com.employ.employment.util.JedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Zenglr, clh
 * @program: employment
 * @packagename: com.employ.employment.service
 * @Description 检索服务
 * @create 2021-05-16-6:02 下午
 */
@Service
@Slf4j
public class SearchService {

    //分页的每一页的结果数
    static int seminarPageRecord = 10;

    //
    static int jobPageRecord = 10;
    //

    private final SeminarRedisDao seminarRedisDao;

    private final SeminarInfoMapper seminarInfoMapper;

    private final JedisUtil jedisUtil;
    //
    private final JobRedisDao jobredisDao;

    private final JobInfoMapper jobInfoMapper;

    private final AnnouncementMapper announcementMapper;
    //
    @Autowired
    public SearchService(SeminarRedisDao seminarRedisDao, SeminarInfoMapper seminarInfoMapper,
                         JedisUtil jedisUtil, JobRedisDao jobredisDao,
                         JobInfoMapper jobInfoMapper, AnnouncementMapper announcementMapper) {
        this.seminarRedisDao = seminarRedisDao;
        this.seminarInfoMapper = seminarInfoMapper;
        this.jedisUtil = jedisUtil;
        this.jobredisDao = jobredisDao;
        this.jobInfoMapper = jobInfoMapper;
        this.announcementMapper = announcementMapper;
    }

    /**
     * 根据检索式获得所有的宣讲会信息
     * @param query
     * @param page
     * @return
     */
    public AjaxJson getSeminarList(String query, int page, int sortType){
        log.info("receive query:{}, page:{}",query,page);
        if (!query.trim().isEmpty()){
            //到redis中模糊查询到对应的SeminarList
            List<String> seminarIdList = seminarRedisDao.fuzzySearchSeminarIdList(query);

            //分页并获取页码总数
            int length = seminarIdList.size();
            int start = (page - 1) * seminarPageRecord;
            int end = start + seminarPageRecord;
            end = Math.min(end, length);
            List<String> res = seminarIdList.subList(start, end);
            long pageCount = jedisUtil.getPageNumber(length, seminarPageRecord);
            log.info("start:{}, end:{}, res:{}, pageCount:{}", start, end, res.toString(), pageCount);

            if(!res.isEmpty()){
                //根据SeminarIdList到mysql中查询到【已通过审核】的宣讲会信息
                List<SeminarInfo> seminarInfos = seminarInfoMapper.selectSeminarBySeminarIds(res, sortType);
                return AjaxJson.getPageData((long)seminarInfos.size(), seminarInfos, page, seminarPageRecord);
            }else {
                return AjaxJson.getError("未查询到对应的宣讲会信息，请检查检索词或页码");
            }
        }else {
            SoMap so = SoMap.getSoMap();
            List<SeminarInfo> seminarInfos = seminarInfoMapper.getList(so.startPage());
            log.info(seminarInfos.toString());
            return AjaxJson.getPageData(so.getDataCount(), seminarInfos, page, seminarPageRecord);
        }

    }

    /**
     * 根据检索式获得所有的职位信息
     * 排序类型【1为按时间排，2为按匹配度排】
     * @param query
     * @param page
     * @return
     */
    public AjaxJson getJobList(String query, int page, int sortType){
        log.info("receive query:{}, page:{}",query,page);
        if (!query.trim().isEmpty()){
            //到redis中模糊查询到对应的SeminarList
            List<String> jobIdList = jobredisDao.fuzzySearchJobIdList(query);

            //分页并获取页码总数
            int length = jobIdList.size();
            int start = (page - 1) * jobPageRecord;
            int end = start + jobPageRecord;
            end = Math.min(end, length);
            List<String> res = jobIdList.subList(start, end);
            long pageCount = jedisUtil.getPageNumber(length, jobPageRecord);
            log.info("start:{}, end:{}, res:{}, pageCount:{}", start, end, res.toString(), pageCount);

            if(!res.isEmpty()){
                //根据jobIdList到mysql中查询到【已通过审核】的职位信息
                List<JobInfo> jobInfos = jobInfoMapper.selectJobByJobIds(res, sortType);
                log.info(jobInfos.toString());
                return AjaxJson.getPageData((long)jobInfos.size(), jobInfos, page, jobPageRecord);
            }else {
                return AjaxJson.getError("未查询到对应的职位信息，请检查检索词或页码");
            }
        }else {
            SoMap so = SoMap.getSoMap();
            List<JobInfo> jobInfos = jobInfoMapper.getList(so.startPage());
            log.info(jobInfos.toString());
            return AjaxJson.getPageData(so.getDataCount(), jobInfos, page, jobPageRecord);
        }

    }


    /**
     * 根据公告类型查询公告信息
     *
     * @param announceType
     * @param page
     * @return
     */
    public AjaxJson getAnnouncementList(int announceType, int page) {
        SoMap so = SoMap.getSoMap();
        so.set("announceType", announceType);
        so.set("pageNo",page);
        List<Announcement> announcementList = announcementMapper.getList(so.startPage());
        log.info(announcementList.toString());
        return AjaxJson.getPageData(so.getDataCount(), announcementList, page, seminarPageRecord);
    }

    /**
     * 查询所有公告信息
     * @param page
     * @return
     */
    public AjaxJson getAllAnnouncement(int page) {
        SoMap so=new SoMap();
        so.set("pageNo",page);
        List<Announcement> announcementList = announcementMapper.getList(so.startPage());
        log.info(announcementList.toString());
        return AjaxJson.getPageData(so.getDataCount(), announcementList, page, 10);
    }

    /**
     * 根据检索词和公告类型查找公告
     * @param page
     * @param announceType
     * @param query
     * @return
     */
    public AjaxJson getAllAnnouncementByQuery(int page, int announceType, String query){
        SoMap so=new SoMap();
        so.set("pageNo",page);
        so.set("announceType", announceType);
        so.set("announceTitle", query);
        List<Announcement> announcementList = announcementMapper.getList(so.startPage());
        log.info(announcementList.toString());
        return AjaxJson.getPageData(so.getDataCount(), announcementList, page, 10);
    }

}
