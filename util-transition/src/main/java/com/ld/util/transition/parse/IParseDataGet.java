package com.ld.util.transition.parse;


/**
 * 解析类的数据访问接口
 * 梁聃 2018/3/13 20:27
 */
public interface IParseDataGet<S,R> {
    /**
     * 获取结果对象
     * @return
     */
    R getResult();
    /**
     * 获取资源对象
     * @return
     */
    S getSource();
    /**
     * 按字段名获取结果对象字段内容
     * @param fieldName 字段名
     * @return
     */
    Object getResultField(String fieldName);
    /**
     * 按字段名获取资源对象字段内容
     * @param fieldName 字段名
     * @return
     */
    Object getSourceField(String fieldName);
}
