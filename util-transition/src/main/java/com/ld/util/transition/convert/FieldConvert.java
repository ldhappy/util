package com.ld.util.transition.convert;


import com.ld.util.transition.annotation.Conversion;

import java.lang.reflect.Field;

/**
 * 存储字段，转换等信息，减少反复解析次数
 * 梁聃 2019/1/9 15:56
 */
public class FieldConvert {
    //资源属性名称
    private String sourceName;
    //错误提示属性名称
    private String errorTipName;
    //字段属性
    private Field field;
    //字段上配置的转换规则
    private Conversion conversion;
    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getErrorTipName() {
        return errorTipName;
    }

    public void setErrorTipName(String errorTipName) {
        this.errorTipName = errorTipName;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Conversion getConversion() {
        return conversion;
    }

    public void setConversion(Conversion conversion) {
        this.conversion = conversion;
    }
}
