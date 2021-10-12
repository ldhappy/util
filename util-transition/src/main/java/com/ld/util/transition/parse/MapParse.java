package com.ld.util.transition.parse;

import com.ld.util.transition.convert.AnnotationConvert;
import com.ld.util.transition.convert.IConvert;
import com.ld.util.transition.exception.ParseException;
import com.ld.util.transition.message.TransitionMessageSource;
import com.ld.util.transition.validation.AnnotationValidate;
import com.ld.util.transition.validation.IValidate;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 入参类型为map，解析结果为对象
 * 梁聃 2018/3/13 14:41
 *
 * @param <R> 解析后结果
 */
@Slf4j
public class MapParse<R> extends AbstractParse<Map, R> {
    /**
     * 内容验证接口，默认实现注解验证
     */
    private IValidate validate = new AnnotationValidate<Map, R>(this);
    /**
     * 内容转换接口，默认实现注解转换
     */
    private IConvert convert = new AnnotationConvert<Map, R>(this);

    /**
     * 必须验证的标志
     */
    private boolean mustValidate = false;

    public MapParse(Map source, R result) {
        super(source, result);
    }

    public MapParse(Map source, R result, IValidate validate, IConvert convert) {
        this(source, result);
        this.validate = validate;
        this.convert = convert;
    }

    public void parse() {
        log.trace("parse-begin>>>param:" + "");
        //校验
        validate();
        //转换
        convert();
        log.trace("parse-end<<<return:");
    }

    private void convert(){
        if (convert != null) {
            convert.convert();
        } else {
            throw ParseException.messageException(TransitionMessageSource.MUST_CONVERT);
        }
    }

    private void validate(){
        sourceIsNotNull();
        resultIsNotNull();
        if (validate != null) {
            validate.validate();
        } else if (mustValidate) {
            throw ParseException.messageException(TransitionMessageSource.MUST_VALIDATE);
        }
    }

    public IValidate getValidate() {
        return validate;
    }

    public void setValidate(IValidate validate) {
        this.validate = validate;
    }

    public IConvert getConvert() {
        return convert;
    }

    public void setConvert(IConvert convert) {
        this.convert = convert;
    }

    public boolean isMustValidate() {
        return mustValidate;
    }

    public void setMustValidate(boolean mustValidate) {
        this.mustValidate = mustValidate;
    }

    /**
     * 按字段名获取结果对象字段内容
     *
     * @param fieldName 字段名
     * @return
     */
    public Object getResultField(String fieldName) {
        try {
            Field field = getResult().getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException e) {
            log.error("getResultField方法错误：" + fieldName + "读取错误，原因：" + e.getMessage());
        }
        return null;
    }

    /**
     * 按字段名获取资源对象字段内容
     *
     * @param fieldName 字段名
     * @return
     */
    public Object getSourceField(String fieldName) {
        Map source = getSource();
        return source.get(fieldName);
    }

    /**
     * 按字段名设置结果对象字段内容
     *
     * @param fieldName 字段名
     * @param value
     * @return
     */
    public void setResultField(String fieldName, Object value) {
        try {
            Field field = getResult().getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(getResult(), value);
        } catch (NoSuchFieldException e) {
            log.error("setResultField方法错误：" + fieldName + "设置错误，原因：" + e.getMessage());
        } catch (IllegalAccessException e) {
            log.error("setResultField方法错误：" + fieldName + "设置错误，原因：" + e.getMessage());
        }
    }
}
