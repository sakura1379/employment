package com.employ.employment.service;

import com.employ.employment.dao.SeminarRedisDao;
import com.employ.employment.entity.AjaxJson;
import com.employ.employment.entity.SeminarInfo;
import com.employ.employment.entity.SoMap;
import com.employ.employment.mapper.CompUserMapper;
import com.employ.employment.mapper.CompanyInfoMapper;
import com.employ.employment.mapper.SeminarInfoMapper;
import com.employ.employment.mapper.UserInfoMapper;
import com.employ.employment.util.JedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Zenglr
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

    private final SeminarRedisDao seminarRedisDao;

    private final SeminarInfoMapper seminarInfoMapper;

    private final JedisUtil jedisUtil;

    @Autowired
    public SearchService(SeminarRedisDao seminarRedisDao, SeminarInfoMapper seminarInfoMapper,
                         JedisUtil jedisUtil) {
        this.seminarRedisDao = seminarRedisDao;
        this.seminarInfoMapper = seminarInfoMapper;
        this.jedisUtil = jedisUtil;
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
                return AjaxJson.getPageData(pageCount, seminarInfos, page, seminarPageRecord);
            }else {
                return AjaxJson.getError("未查询到对应的宣讲会信息，请检查检索词或页码");
            }
        }else {
            SoMap so = SoMap.getSoMap();
            List<SeminarInfo> seminarInfos = seminarInfoMapper.getList(so.startPage());
            return AjaxJson.getPageData(so.getDataCount(), seminarInfos, page, seminarPageRecord);
        }

    }
}
