package com.ld.util.excel.reader.rule;

import com.google.common.collect.Lists;
import com.ld.util.excel.core.ColumnHeader;
import com.ld.util.excel.exception.ExcelException;
import com.ld.util.excel.message.ExcelMessageSource;

import java.util.List;

/**
 * @ClassName CompleteMatchingRule
 * @Description 列头需要完全匹配的规则
 * @Author 梁聃
 * @Date 2021/10/7 16:32
 */
public class CompleteMatchingRule implements MatchingRule{

    /**
     * 检查导入列头是否符合规则
     *
     * @param ruleColumnHeaderList 导入规则列头
     * @param columnList           当前文件列头
     * @throws ExcelException
     */
    @Override
    public void checkColumns(List<ColumnHeader> ruleColumnHeaderList, List<ColumnHeader> columnList) throws ExcelException {
        if(ruleColumnHeaderList == null){
            throw ExcelException.messageException(ExcelMessageSource.READ_MATCHING_RULE_RULE_EMPTY);
        }
        if(columnList == null){
            throw ExcelException.messageException(ExcelMessageSource.READ_MATCHING_RULE_HEADER_EMPTY);
        }
        Integer count = ruleColumnHeaderList.size();
        if(count != columnList.size()){
            throw ExcelException.messageException(ExcelMessageSource.READ_MATCHING_RULE_HEADER_SIZE_ERROR,columnList.size(),count);
        }
        for(int i=0;i<count;i++){
            ColumnHeader ruleColumnHeader = ruleColumnHeaderList.get(i);
            ColumnHeader columnHeader = columnList.get(i);
            if(ruleColumnHeader.compareTo(columnHeader) != 0){
                throw ExcelException.messageException(ExcelMessageSource.READ_MATCHING_RULE_UNMATCH_COORDINATE,ruleColumnHeader.getCoordinateColumn()+1,ruleColumnHeader.getColumnName());
            }
            if(!ruleColumnHeader.equalsValue(columnHeader)){
                throw ExcelException.messageException(ExcelMessageSource.READ_MATCHING_RULE_UNMATCH_NAME,ruleColumnHeader.getCoordinateColumn()+1,ruleColumnHeader.getColumnName(),columnHeader.getColumnName());
            }
        }
    }

    public static void main(String[] args) {
        CompleteMatchingRule completeMatchingRule = new CompleteMatchingRule();
        completeMatchingRule.checkColumns(Lists.newArrayList(
                ColumnHeader.columnHeaderBuilder().columnName("姓名").coordinate("A1").build(),
                ColumnHeader.columnHeaderBuilder().columnName("性别").coordinate("B1").build(),
                ColumnHeader.columnHeaderBuilder().columnName("年龄").coordinate("C1").build()
        ),Lists.newArrayList(
                ColumnHeader.columnHeaderBuilder().columnName("姓名").coordinate("A1").build(),
                ColumnHeader.columnHeaderBuilder().columnName("性别").coordinate("B1").build(),
                ColumnHeader.columnHeaderBuilder().columnName("年龄").coordinate("C1").build()
        ));
    }
}
