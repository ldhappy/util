package com.ld.util.excel.reader.rule;

import com.google.common.collect.Lists;
import com.ld.util.excel.core.ColumnHeader;
import com.ld.util.excel.exception.ExcelException;
import com.ld.util.excel.message.ExcelMessageSource;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName FuzzyMatchingRule
 * @Description 列头可以模糊匹配的规则
 * @Author 梁聃
 * @Date 2021/10/7 17:58
 */
public class FuzzyMatchingRule implements MatchingRule{

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
        //j:导入列头的索引
        int j = 0;
        //匹配成功的列数量
        int matchCount = 0;
        //i:规则列头的索引
        for(int i=0;i<count;i++){
            ColumnHeader ruleColumnHeader = ruleColumnHeaderList.get(i);
            while (j < columnList.size()){
                ColumnHeader columnHeader = columnList.get(j);
                j++;
                if(ruleColumnHeader.compareTo(columnHeader) > 0){
                    //继续寻找下一列看是否匹配
                    continue;
                }else if(ruleColumnHeader.compareTo(columnHeader) < 0){
                    throw ExcelException.messageException(ExcelMessageSource.READ_MATCHING_RULE_UNMATCH_COORDINATE,ruleColumnHeader.getCoordinateColumn()+1,ruleColumnHeader.getColumnName());
                } else{
                    if(ruleColumnHeader.equalsValue(columnHeader)){
                        //匹配到列，结束
                        matchCount++;
                        break;
                    } else {
                        throw ExcelException.messageException(ExcelMessageSource.READ_MATCHING_RULE_UNMATCH_NAME,ruleColumnHeader.getCoordinateColumn()+1,ruleColumnHeader.getColumnName(),columnHeader.getColumnName());
                    }
                }
            }
        }
        if(count>matchCount){
            throw ExcelException.messageException(ExcelMessageSource.READ_MATCHING_RULE_HEADER_SIZE_ERROR_FOR_FUZZY,count,matchCount);
        }
    }

    public static void main(String[] args) {
        FuzzyMatchingRule fuzzyMatchingRule = new FuzzyMatchingRule();
        fuzzyMatchingRule.checkColumns(Lists.newArrayList(
                ColumnHeader.columnHeaderBuilder().columnName("年龄").coordinate("C1").build(),
                ColumnHeader.columnHeaderBuilder().columnName("姓名").coordinate("B1").build()
        ).stream().sorted().collect(Collectors.toList()), Lists.newArrayList(
                ColumnHeader.columnHeaderBuilder().columnName("姓名").coordinate("A1").build(),
                ColumnHeader.columnHeaderBuilder().columnName("性别").coordinate("B1").build(),
                ColumnHeader.columnHeaderBuilder().columnName("年龄").coordinate("D1").build()
        ));
    }
}
