package com.employ.employment.service;

import com.employ.employment.entity.SP;
import com.employ.employment.entity.StuInfo;
import com.employ.employment.entity.UserInfo;
import com.employ.employment.mapper.StuInfoMapper;
import com.employ.employment.mapper.StuMesMapper;
import com.employ.employment.mapper.UserInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class StuMesService {

    @Autowired
    public StuMesService(){

    }

}
