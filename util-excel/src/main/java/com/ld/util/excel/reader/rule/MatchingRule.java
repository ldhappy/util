package com.ld.util.excel.reader.rule;

import com.ld.util.excel.core.ColumnHeader;
import com.ld.util.excel.exception.ExcelException;

import java.util.List;

/**
 * @ClassName MatchingRule
 * @Description 列头比对的规则
 * @Author 梁聃
 * @Date 2021/10/7 17:02
 */
public interface MatchingRule {
    /**
     * 检查导入列头是否符合规则
     * @param ruleColumnHeaderList 导入规则列头
     * @param columnList 当前文件列头
     * @throws ExcelException 比对失败时会抛出异常
     */
    void checkColumns(List<ColumnHeader> ruleColumnHeaderList,List<ColumnHeader> columnList) throws ExcelException;
}
