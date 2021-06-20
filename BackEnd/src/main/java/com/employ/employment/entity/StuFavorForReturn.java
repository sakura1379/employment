package com.employ.employment.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StuFavorForReturn {
    public JobInfo jobInfo;

    public String compName;

    public long favorNum;

    public StuFavorForReturn(JobInfo jobInfo,String compName,long favorNum){
        this.jobInfo=jobInfo;
        this.compName=compName;
        this.favorNum=favorNum;
    }
}
