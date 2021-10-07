package com.ld.util.excel.example.builder;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ClassName People
 * @Description 导出对象样例
 * @Author 梁聃
 * @Date 2021/9/25 10:23
 */
@Data
@AllArgsConstructor
public class People {
    //姓名
    private String name;
    //年龄
    private Integer age;
    //性别
    private Integer sex;
}
