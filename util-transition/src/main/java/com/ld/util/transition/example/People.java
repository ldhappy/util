package com.ld.util.transition.example;

import com.ld.util.transition.annotation.Conversion;
import com.ld.util.transition.annotation.Name;
import com.ld.util.transition.annotation.Regulation;
import com.ld.util.transition.rule.StringToIntegerConvert;

/**
 * 解析文件对应对象的校验和转换的对象配置样例
 * 梁聃 2019/1/9 10:40
 */
public class People{
    /**
     * 名字转换注解
     * sourceName 配对excel的列号，如A,B,C,D
     * errorTipName 配对列错误提示信息的提示列名
     * 名字转换注解可不写，此时三个值均为属性名
     */
    @Name(sourceName = "B")
    private String name;
    @Name(sourceName = "C",errorTipName = "年龄")
    @Conversion(convertRuleClass = StringToIntegerConvert.class)
    /**
     * 校验规则注解
     * rule 简单的校验正则
     * ruleClass 复杂的校验规则可单独编写规则配置类，可参见NoRule，需实现Rule接口 优先于rule
     * errorInfo 对应正则的错误信息
     * required 是否必填字段
     * 校验规则注解可不写，此时不进行校验
     */
    @Regulation(rule = "^[0-9]{1,2}$",errorInfo = "请输入0~99之间的正整数")
    private Integer age;
    @Name(sourceName = "D",errorTipName = "性别")
    /**
     * 转换规则注解
     * convertRuleClass 转换规则类，可参见SexConvert，需实现ConvertRule接口
     * 校验规则注解可不写，此时不进行校验
     */
    @Conversion(convertRuleClass = SexConvert.class)
    @Regulation(rule = "^男|女$",errorInfo = "请输入男或女",required = true)
    private Integer sex;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "People{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                '}';
    }
}
