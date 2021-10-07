package com.ld.util.transition.parse;

/**
 * 解析类的结果数据设置接口
 * 梁聃 2018/3/13 20:27
 */
public interface IParseDataSet {
    /**
     * 按字段名设置结果对象字段内容
     * @param fieldName 字段名
     * @param value
     * @return
     */
    void setResultField(String fieldName, Object value);
}
