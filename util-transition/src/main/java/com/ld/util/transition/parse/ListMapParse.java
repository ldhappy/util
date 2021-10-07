package com.ld.util.transition.parse;

import com.google.common.reflect.TypeToken;
import com.ld.util.transition.exception.ParseException;
import com.ld.util.transition.message.TransitionMessageSource;
import org.springframework.context.support.MessageSourceAccessor;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * List<Map>类型对象解析
 * @param <R>
 */
public abstract class ListMapParse<R> implements IParse {
    private List<Map<String,Object>> source;
    private List<R> result;
    private Class<R> resultClass;
    private List<Map<String,Object>> errorList = new ArrayList<Map<String, Object>>();
    private String errorInfoField = "listMapParseErrorInfo";

    /**
     * 错误日志消息
     */
    private MessageSourceAccessor messageSourceAccessor = TransitionMessageSource.getAccessor();

    /**
     * 解析成功标志
     */
    private boolean success = false;
    public ListMapParse(List<Map<String, Object>> source, List<R> result) {
        this.source = source;
        this.result = result;
        Type superclass = getClass().getGenericSuperclass();
        Type runtimeType = ((ParameterizedType) superclass).getActualTypeArguments()[0];
        this.resultClass = (Class<R>) TypeToken.of(runtimeType).getRawType();
        initValidate();
    }
    /**
     * 初始化入参有效性验证
     */
    protected void initValidate() {
        sourceIsNotNull();
        resultIsNotNull();
    }
    protected void sourceIsNotNull(){
        if(source == null){
            throw new ParseException(getErrorMessage(TransitionMessageSource.SOURCE_NULL));
        }
    }
    protected void resultIsNotNull(){
        if(result == null){
            throw new ParseException(getErrorMessage(TransitionMessageSource.RESULT_NULL));
        }
    }
    public void parse() {
        sourceIsNotNull();
        resultIsNotNull();
        for(Map<String,Object> map:source){
            try {
                R r = (R) resultClass.newInstance();
                MapParse<R> mp = new MapParse<>(map,r);
                mp.parse();
                if(mp.isSuccess()){
                    result.add(r);
                }else{
                    map.put(errorInfoField,getErrorMessage(TransitionMessageSource.PARSE_FAIL));
                    errorList.add(map);
                }
            }catch (IllegalAccessException e) {
                map.put(errorInfoField,getErrorMessage(TransitionMessageSource.CREATE_RESULT_EXCEPTION+e.getMessage()));
                errorList.add(map);
            } catch (InstantiationException e) {
                map.put(errorInfoField,getErrorMessage(TransitionMessageSource.CREATE_RESULT_EXCEPTION+e.getMessage()));
                errorList.add(map);
            }catch (Exception e) {
                map.put(errorInfoField,e.getMessage());
                errorList.add(map);
            }

        }
        success = true;
    }

    public void setErrorInfoField(String errorInfoField) {
        this.errorInfoField = errorInfoField;
    }

    public List<Map<String, Object>> getSource() {
        return source;
    }

    public List<R> getResult() {
        return result;
    }

    public Class getResultClass() {
        return resultClass;
    }

    public List<Map<String, Object>> getErrorList() {
        return errorList;
    }

    public String getErrorInfoField() {
        return errorInfoField;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setMessageSourceAccessor(MessageSourceAccessor messageSourceAccessor) {
        this.messageSourceAccessor = messageSourceAccessor;
    }

    /**
     * 根据code查询提示语
     * @param messageCode
     * @return
     */
    public String getErrorMessage(String messageCode) {
        return messageSourceAccessor.getMessage(messageCode);
    }
}
