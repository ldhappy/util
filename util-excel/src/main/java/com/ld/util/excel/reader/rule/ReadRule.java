package com.ld.util.excel.reader.rule;

import com.google.common.collect.Lists;
import com.ld.util.excel.core.ColumnHeader;
import com.ld.util.excel.exception.ExcelException;
import com.ld.util.excel.message.ExcelMessageSource;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @ClassName ReadRule
 * @Description excel读取规则
 * @Author 梁聃
 * @Date 2021/10/7 15:07
 */
@Getter
@ToString
public class ReadRule<T> {
    /**
     * 规则列名List
     */
    protected List<ColumnHeader> ruleColumnHeaderList;
    /**
     * 列头开始行号,为空会根据规则列名出现的首行号为默认值
     */
    protected Integer columnHeaderBeginRowIndex;
    /**
     * 列头结束行号，为空会根据规则列名出现的（最大行号+占行数）为默认值
     */
    protected Integer columnContentBeginRowIndex;
    /**
     * 可解析的最大行数
     */
    protected Integer maxRows;
    /**
     * 列头核验的匹配规则,建议使用MatchingRule中的常量规则
     */
    protected MatchingRule matchingRule;
    /**content
     * 解析目标对象类型，解析的目标对象配置方式见example.Student
     */
    protected Class<T> targetClass;


    @Builder
    public ReadRule(@Singular("ruleColumnHeader") List<ColumnHeader> ruleColumnHeaderList, Integer columnHeaderBeginRowIndex, Integer columnContentBeginRowIndex, Integer maxRows, MatchingRule matchingRule,Class<T> targetClass) {
        if(Objects.isNull(targetClass)){
            throw ExcelException.messageException(ExcelMessageSource.READ_READ_RULE_TARGET_CLASS_EMPTY);
        }
        this.targetClass = targetClass;
        if(CollectionUtils.isEmpty(ruleColumnHeaderList)){
            throw ExcelException.messageException(ExcelMessageSource.READ_READ_RULE_HEADER_EMPTY);
        }
        //规则列名List规则列名进行一次排序保证规则是有正确顺序的
        this.ruleColumnHeaderList = ruleColumnHeaderList.stream().sorted().collect(Collectors.toList());

        this.columnHeaderBeginRowIndex = Objects.isNull(columnHeaderBeginRowIndex)?
                ruleColumnHeaderList.stream().mapToInt(columnHeader -> columnHeader.getCoordinateRow()).min().getAsInt() :
                columnHeaderBeginRowIndex;
        this.columnContentBeginRowIndex = Objects.isNull(columnContentBeginRowIndex)?
                ruleColumnHeaderList.stream().mapToInt(columnHeader -> columnHeader.getCoordinateRow()+columnHeader.getCoverageRow()).max().getAsInt():
                columnContentBeginRowIndex;
        this.maxRows = maxRows;
        this.matchingRule = Objects.isNull(matchingRule)?
                //不进行列头匹配校验的规则-----默认规则
                new CompleteMatchingRule():
                matchingRule;
    }


    /**
     * 查验列头是否符合规则
     * @param columnList
     * @return
     */
    public void checkColumns(List<ColumnHeader> columnList){
        matchingRule.checkColumns(ruleColumnHeaderList,columnList);
    }
}
