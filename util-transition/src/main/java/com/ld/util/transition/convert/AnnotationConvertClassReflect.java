package com.ld.util.transition.convert;

import com.ld.util.transition.annotation.Conversion;
import com.ld.util.transition.annotation.Name;
import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 存储AnnotationConvert解析过的类型的反射结果
 * 梁聃 2019/1/9 16:02
 */
public class AnnotationConvertClassReflect{
    //字段及其转换规则
    private List<FieldConvert> fieldConvertList;
    public AnnotationConvertClassReflect(Class clazz) {
        this(clazz,Object.class);
    }
    /**
     *
     * @param clazz
     * @param ignoreClazz 不需要解析的父类
     */
    public AnnotationConvertClassReflect(Class clazz,Class ignoreClazz) {
        fieldConvertList = new ArrayList<>();
        List<Class<?>> classList = ClassUtils.getAllSuperclasses(clazz);
        //忽略不解析的父类属性
        if(ignoreClazz != null){
            List<Class<?>> ignoreclassList = ClassUtils.getAllSuperclasses(ignoreClazz);
            for(Class c:ignoreclassList){
                classList.remove(c);
            }
            classList.remove(ignoreClazz);
        }
        classList.add(clazz);
        //查找所有父类的所有字段，解析
        for(Class c:classList) {
            for (Field field : c.getDeclaredFields()) {
                FieldConvert fieldConvert = new FieldConvert();
                field.setAccessible(true);
                fieldConvert.setField(field);
                //查找到注解了名称的属性
                Name nameAnnotation = field.getAnnotation(Name.class);
                String name;
                if(nameAnnotation != null && !nameAnnotation.sourceName().equals("")){
                    name = nameAnnotation.sourceName();
                }else{
                    name = field.getName();
                }
                fieldConvert.setSourceName(name);
                String errorTipName;
                if(nameAnnotation != null && !nameAnnotation.errorTipName().equals("")){
                    errorTipName = nameAnnotation.errorTipName();
                }else{
                    errorTipName = name;
                }
                fieldConvert.setErrorTipName(errorTipName);
                //查找到注解了规则的属性
                Conversion conversion = field.getAnnotation(Conversion.class);
                fieldConvert.setConversion(conversion);
                fieldConvertList.add(fieldConvert);
            }
        }
    }

    public List<FieldConvert> getFieldConvertList() {
        return fieldConvertList;
    }
}
