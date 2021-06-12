package com.employ.employment.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @ProjectName: employment
 * @Package: com.employ.employment.entity
 * @ClassName: JobInfo
 * @Description: [职位信息表]
 * @Author: [clh]
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobInfo implements Serializable {

    /**
     * 序列化版本id
     */
    private static final long serialVersionUID = 1L;

    /**
     * 职位id
     */
    public Long jobId;

    /**
     * 公司编号
     */
    public Long compId;

    /**
     * 职位名称
     */
    public String jobName;

    /**
     * 岗位类别
     */
    public String jobType;

    /**
     * 招聘性质 (1=实习, 2=校招, 3=实习和校招)
     */
    public Integer jobKind;

    /**
     * 招聘性质 (1=招聘中，2=已结束)
     */
    public Integer status;

    /**
     * 发布日期
     */
    public Date relDate;

    /**
     * 工作地点
     */
    public String jobAddress;

    /**
     * 职位描述
     */
    public String jobCon;

    /**
     * 截止日期
     */
    public Date jobDeadline;

    /**
     * 已投递人数
     */
    public Integer deliverNum;

    /**
     * 薪资
     */
    public String salary;

    /**
     * 审核状态(1=未审核 2=审核通过 3=审核不通过)
     */
    public Integer approveStatus;
}
