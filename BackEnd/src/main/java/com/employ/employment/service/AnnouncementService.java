package com.employ.employment.service;

import com.employ.employment.entity.AjaxJson;
import com.employ.employment.entity.Announcement;
import com.employ.employment.entity.SP;
import com.employ.employment.entity.SoMap;
import com.employ.employment.mapper.AnnouncementMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Zenglr
 * @program: employment
 * @packagename: com.employ.employment.service
 * @Description 公告表操作
 * @create 2021-05-12-6:45 下午
 */
@Service
@Slf4j
public class AnnouncementService {

    private final AnnouncementMapper announcementMapper;


    @Autowired
    public AnnouncementService(AnnouncementMapper announcementMapper) {
        this.announcementMapper = announcementMapper;
    }




    /**
     * 新增公告信息
     * @param announcement
     * @return
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public AjaxJson add(Announcement announcement) {
        log.info("AnnouncementService start add announcement=====");
        int line = announcementMapper.add(announcement);
        //获得主键
        long aid = SP.publicMapper.getPrimarykey();
        log.info("aid:{}", aid);

        Announcement a = announcementMapper.getById(aid);
        return AjaxJson.getSuccessData(a);
    }

    @Transactional(rollbackFor = Exception.class)
    public AjaxJson delete(Integer id) {
        announcementMapper.delete(id);
        return AjaxJson.getSuccess();
    }

    /**
     * 修改公告信息
     * @param announcement
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AjaxJson update(Announcement announcement) {
        log.info("AnnouncementService start update announcement=====");
        announcementMapper.update(announcement);

        Announcement a = announcementMapper.getById(announcement.getAnnounceId());
        return AjaxJson.getSuccessData(a);
    }

}
