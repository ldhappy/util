package com.ld.util.transition.example;

import com.ld.util.transition.annotation.Name;
import com.ld.util.transition.annotation.Regulation;

import java.util.Date;

/**
 * 解析文件对应对象的校验和转换的对象配置样例，还有部分规则可看People
 * 梁聃 2019/1/9 10:40
 */
public class Student extends People {
    /**
     * 名字转换注解
     * sourceName 配对excel的列号，如A,B,C,D
     * resultName 配对当前熟悉的名称
     * errorTipName 配对列错误提示信息的提示列名
     * 名字转换注解可不写，此时三个值均为属性名
     */
    @Name(sourceName = "A",resultName = "No",errorTipName="学号")
    /**
     * 校验规则注解
     * rule 简单的校验正则
     * ruleClass 复杂的校验规则可单独编写规则配置类，可参加NoRule，需实现Rule接口 优先于rule
     * errorInfo 对应正则的错误信息
     * required 是否必填字段
     * 校验规则注解可不写，此时不进行校验
     */
    @Regulation(ruleClass = NoRule.class)
    private String No;
    @Name(sourceName = "E",resultName = "birthday",errorTipName="出生日期")
    //@Conversion(convertRuleClass = StringToDateConvert.class)
    private Date birthday;

    public String getNo() {
        return No;
    }

    public void setNo(String no) {
        No = no;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "Student{" +
                "No='" + No + '\'' +
                ", birthday=" + birthday +
                "} " + super.toString();
    }
}
