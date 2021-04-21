package com.employ.employment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.employ.employment.entity.testUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Zenglr
 * @program: employment
 * @packagename: com.employ.employment.mapper
 * @Description
 * @create 2021-04-14-6:59 下午
 */
@Mapper
public interface UserMapper extends BaseMapper<testUser> {

    List<testUser> searchAll();
}
