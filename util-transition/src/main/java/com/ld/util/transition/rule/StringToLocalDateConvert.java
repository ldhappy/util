package com.ld.util.transition.rule;

import com.ld.util.transition.exception.ConvertException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 字符串转换为Date
 * 支持格式"yyyy/MM/dd HH:mm:ss"
 * "yyyy年MM月dd日"
 */
public class StringToLocalDateConvert implements IConvertRule {
    private static List<DateTimeFormatter> formatList = new ArrayList<>();
    static {
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
                LocalDate date = LocalDate.parse(s, format);
                return date;
            } catch (Exception e) {
                //格式不能解析
            }
        }
        throw new ConvertException("提供格式无法支持当前字符串“"+s+"”转换为是日期");
    }
}
