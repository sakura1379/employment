package com.employ.employment.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Zenglr
 * @program: employment
 * @packagename: com.employ.employment.entity
 * @Description 宣讲会信息
 * @create 2021-05-15-5:45 下午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeminarInfo {

    /**
     * 序列化版本id
     */
    private static final long serialVersionUID = 1L;

    // ---------- 表中字段 ----------
    /**
     * 宣讲会信息编号
     */
    public Long seminarId;

    /**
     * 宣讲会标题
     */
    public String seminarTitle;

    /**
     * 宣讲会内容链接
     */
    public String seminarContent;

    /**
     * 宣讲会时间
     */
    public String seminarTime;

    /**
     * 宣讲会地点
     */
    public String seminarAddress;

    /**
     * hr编号
     */
    public Long hrId;

    /**
     * 审核状态 (1=未审核, 2=审核通过, 3=审核不通过)
     */
    public Integer approveStatus;

    /**
     * 发布时间
     */
    public Date releaseTime;

}
