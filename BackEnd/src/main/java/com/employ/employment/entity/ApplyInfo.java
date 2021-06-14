package com.employ.employment.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *
 * @ProjectName: employment
 * @Package: com.employ.employment.entity
 * @ClassName: ApplyInfo
 * @Description: [职位申请表]
 * @Author: [Le]
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplyInfo implements Serializable {
    /**
     * 学生id
     */
    public Long stuNum;

    /**
     * 学生姓名
     */
    public String stuName;

    /**
     * 职位名称
     */
    public String jobName;

    /**
     * 职位id
     */
    public Long jobId;

    /**
     * 一周可实习时间(1=一天 2=两天 3=三天 4=四天 5=五天 6=六天)
     */
    public Integer internshipTime;

    /**
     * 最快到岗时间(1=一周内 2=两周内 3=一个月内 4=三个月内)
     */
    public Integer dutyTime;

    /**
     * 历史申请状态
     * (1=简历待筛选 2=未通过 3=一面 4=二面 5=HR面 6=录用评估中 7=录用意向 8=已录用)
     */
    public Integer ApplyStatus;

    /**
     * 最近申请状态
     * (1=简历待筛选 2=未通过 3=一面 4=二面 5=HR面 6=录用评估中 7=录用意向 8=已录用)
     */
    public Integer newApplyStatus;
}
