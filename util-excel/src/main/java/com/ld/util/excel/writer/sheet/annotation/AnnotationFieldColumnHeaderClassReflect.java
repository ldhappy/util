package com.ld.util.excel.writer.sheet.annotation;

import com.ld.util.excel.exception.ExcelException;
import com.ld.util.transition.annotation.Conversion;
import com.ld.util.transition.annotation.Name;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @ClassName AnnotationFieldColumnHeaderClassReflect
 * @Description 存储AnnotationSheetWriter解析过的类型的反射结果
 * @Author 梁聃
 * @Date 2021/9/30 17:00
 */
public class AnnotationFieldColumnHeaderClassReflect<T> {
    //工作表名称
    private String sheetName;
    //字段及其转换规则
    private List<FieldColumnHeader> fieldColumnHeaderList;
    /**
     *
     * @param clazz
     */
    public AnnotationFieldColumnHeaderClassReflect(Class<T> clazz) {
        List<FieldColumnHeader> list = new ArrayList<>();
        SheetRule sheetRule = clazz.getAnnotation(SheetRule.class);
        if(Objects.isNull(sheetRule) || StringUtils.isBlank(sheetRule.name())){
            throw new ExcelException("需要导出的数据类型："+clazz.getName()+"未设置工作表名称，无法完成导出工作");
        }
        sheetName = sheetRule.name();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            //查找到注解了名称的属性
            ColumnHeaderRule columnHeaderRule = field.getAnnotation(ColumnHeaderRule.class);
            if(Objects.nonNull(columnHeaderRule)){
                FieldColumnHeader fieldColumnHeader = new FieldColumnHeader();
                fieldColumnHeader.setField(field);
                fieldColumnHeader.setName(columnHeaderRule.name());
                fieldColumnHeader.setOrder(columnHeaderRule.order());
                list.add(fieldColumnHeader);
            }
        }
        if(list.size() == 0){
            throw new ExcelException("需要导出的数据类型："+clazz.getName()+"未设置列头信息，无法完成导出工作");
        }
        fieldColumnHeaderList = list.stream().sorted().collect(Collectors.toList());
    }

    public String getSheetName() {
        return sheetName;
    }

    public List<FieldColumnHeader> getFieldColumnHeaderList() {
        return fieldColumnHeaderList;
    }
}
