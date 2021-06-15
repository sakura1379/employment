package com.employ.employment.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
/**
 *学生信箱表
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StuMes implements Serializable {

    /**
     * 序列化版本id
     */
    private static final long serialVersionUID = 1L;

    /**
     * 信箱id
     */
    public Long mailNum;

    /**
     * 学生id
     */
    public Long stuNum;

    /**
     * 信息id
     */
    public Long infoId;

    /**
     * 信息类型（1=宣讲会信息，2=职位信息，3=公告信息）
     */
    public Long infoType;
}
