package com.employ.employment.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Zenglr
 * @program: employment
 * @packagename: com.employ.employment.entity
 * @Description 测试MP
 * @create 2021-04-14-6:40 下午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class testUser implements Serializable {
    private String id;
    private String name;
}
