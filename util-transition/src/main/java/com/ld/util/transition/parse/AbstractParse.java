package com.ld.util.transition.parse;

import com.ld.util.transition.exception.ParseException;
import com.ld.util.transition.message.TransitionMessageSource;
import org.springframework.context.support.MessageSourceAccessor;

/**
 * 解析顶层虚类
 * 梁聃 2018/3/13 13:45
 * @param <S> 待解析资源
 * @param <R> 解析后结果
 */

public abstract class AbstractParse<S,R> implements IParse ,IParseData<S,R>{
    private S source;
    private R result;

    public AbstractParse(S source, R result) {
        this.source = source;
        this.result = result;
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
            ParseException.messageException(TransitionMessageSource.SOURCE_NULL);
        }
    }



    protected void resultIsNotNull(){
        if(result == null){
            ParseException.messageException(TransitionMessageSource.RESULT_NULL);
        }
    }

    public S getSource() {
        return source;
    }

    public R getResult() {
        return result;
    }

}
