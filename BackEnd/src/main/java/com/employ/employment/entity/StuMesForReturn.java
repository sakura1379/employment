package com.employ.employment.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 *学生信件信息（用于list返回）
 */

@Data
@NoArgsConstructor
public class StuMesForReturn {
    /**
     * 信件标题
     */
    public String title;

    /**
     * 信件id
     */
    public long infoId;

    /**
     * 信件类型
     */
    public long infoType;

    public StuMesForReturn(String title,long infoId,long infoType){
        this.title=title;
        this.infoId=infoId;
        this.infoType=infoType;
    }
}
