package com.ld.util.excel.example.annotation;

import com.ld.util.excel.writer.sheet.annotation.ColumnHeaderRule;
import com.ld.util.excel.writer.sheet.annotation.SheetRule;
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
@SheetRule(name = "People")
public class People {
    //姓名
    @ColumnHeaderRule(name = "姓名",order = 1)
    private String name;
    //年龄
    @ColumnHeaderRule(name = "年龄",order = 3)
    private Integer age;
    //性别
    @ColumnHeaderRule(name = "性别",order = 2)
    private Integer sex;
}
