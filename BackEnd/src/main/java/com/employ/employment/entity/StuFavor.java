package com.employ.employment.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StuFavor implements Serializable {

    /**
     * 序列化版本id
     */
    private static final long serialVersionUID = 1L;

    /**
     * 收藏编号
     */
    public Long favorNum;

    /**
     * 学生编号
     */
    public Long stuNum;

    /**
     * 企业编号
     */
    public Long compId;

    /**
     * 职业信息编号
     */
    public Long jobId;
}
