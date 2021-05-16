package com.employ.employment.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Zenglr
 * @program: employment
 * @packagename: com.employ.employment.entity
 * @Description 学生信息
 * @create 2021-05-12-8:32 下午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StuInfo implements Serializable {

    /**
     * 序列化版本id
     */
    private static final long serialVersionUID = 1L;

    /**
     * 学生id
     */
    public Long stuNum;

    /**
     * 学生姓名
     */
    public String stuName;

    /**
     * 学生学校
     */
    public String stuGraUniversity;

    /**
     * 学生专业
     */
    public String stuMajor;

    /**
     * 学生学历 (1=本科, 2=研究生)
     */
    public Integer stuEducation;

    /**
     * 求职性质 (1=实习, 2=校招, 3=实习和校招)
     */
    public Integer stJodKind;

    /**
     * 学生毕业年份
     */
    public Date stuGraduateTime;

    /**
     * 学生电话号码
     */
    public String stuTelephone;

    /**
     * 期望城市
     */
    public String dreamAddress;

    /**
     * 期望职位类别
     */
    public String dreamPosition;

    /**
     * 简历信息
     */
    public String resume;
}
