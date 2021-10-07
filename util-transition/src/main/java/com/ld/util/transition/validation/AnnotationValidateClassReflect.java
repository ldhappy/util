package com.ld.util.transition.validation;

import com.ld.util.transition.annotation.Name;
import com.ld.util.transition.annotation.Regulation;
import org.apache.commons.lang3.ClassUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 存储AnnotationValidate解析过的类型的反射结果
 * 梁聃 2018/3/27 11:34
 */
public class AnnotationValidateClassReflect {
    //key:sourceName,value:对应规则
    private Map<String, Regulation> regulationMap;
    private Map<String,String> errorTipNameMap;
    public AnnotationValidateClassReflect(Class clazz){
        this(clazz,Object.class);
    }

    /**
     *
     * @param clazz
     * @param ignoreClazz 不需要解析的父类
     */
    public AnnotationValidateClassReflect(Class clazz,Class ignoreClazz) {
        regulationMap = new HashMap<>();
        errorTipNameMap = new HashMap<>();
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
                //查找到注解了名称的属性
                Name nameAnnotation = field.getAnnotation(Name.class);
                String name;
                if(nameAnnotation != null && !nameAnnotation.sourceName().equals("")){
                    name = nameAnnotation.sourceName();
                }else{
                    name = field.getName();
                }
                //查找到注解了规则的属性
                Regulation regulation = field.getAnnotation(Regulation.class);
                regulationMap.put(name,regulation);
                if(nameAnnotation != null && !nameAnnotation.errorTipName().equals("")){
                    errorTipNameMap.put(name,nameAnnotation.errorTipName());
                }

            }
        }
    }

    public Map<String, Regulation> getRegulationMap() {
        return regulationMap;
    }

    public Map<String, String> getErrorTipNameMap() {
        return errorTipNameMap;
    }
}
