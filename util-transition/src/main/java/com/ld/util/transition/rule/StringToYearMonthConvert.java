package com.ld.util.transition.rule;

import com.ld.util.transition.exception.ConvertException;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName StringToYearMonthConvert
 * @Description 字符串转换为年月
 * @Author 梁聃
 * @Date 2020/12/8 15:13
 */
public class StringToYearMonthConvert implements IConvertRule {
    private static List<DateTimeFormatter> formatList = new ArrayList<>();
    static {
        formatList.add(DateTimeFormatter.ofPattern("yyyy/MM"));
        formatList.add(DateTimeFormatter.ofPattern("yyyy年MM月"));
        formatList.add(DateTimeFormatter.ofPattern("yyyy-MM"));
        formatList.add(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        formatList.add(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
        formatList.add(DateTimeFormatter.ofPattern("yyyy年MM月dd日"));
        formatList.add(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        formatList.add(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    public Object convert(Object source) {
        String s = source.toString().trim();
        for(DateTimeFormatter format:formatList){
            try {
                YearMonth ym = YearMonth.parse(s, format);
                return ym;
            } catch (Exception e) {
                //格式不能解析
            }
        }
        throw new ConvertException("提供格式无法支持当前字符串“"+s+"”转换为年月信息");
    }
}
