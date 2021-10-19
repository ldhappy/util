package com.ld.util.excel.example.read.annotation.simple;

import com.ld.util.excel.reader.annotation.ReadRule;
import com.ld.util.excel.reader.annotation.ReadRuleColumnHeader;
import com.ld.util.transition.annotation.Conversion;
import com.ld.util.transition.annotation.Name;
import com.ld.util.transition.annotation.Regulation;
import com.ld.util.transition.example.SexConvert;
import com.ld.util.transition.rule.StringToIntegerConvert;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName People
 * @Description 导出对象样例
 * @Author 梁聃
 * @Date 2021/9/25 10:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ReadRuleColumnHeader(columnName = "姓名",coordinate = "A1")
@ReadRuleColumnHeader(columnName = "年龄",coordinate = "B1")
@ReadRuleColumnHeader(columnName = "性别",coordinate = "C1")
public class People {
    @Name(sourceName = "A",errorTipName = "姓名")
    private String name;
    @Name(sourceName = "B",errorTipName = "年龄")
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
    @Name(sourceName = "C",errorTipName = "性别")
    /**
     * 转换规则注解
     * convertRuleClass 转换规则类，可参见SexConvert，需实现ConvertRule接口
     * 校验规则注解可不写，此时不进行校验
     */
    @Conversion(convertRuleClass = SexConvert.class)
    private Integer sex;
}
