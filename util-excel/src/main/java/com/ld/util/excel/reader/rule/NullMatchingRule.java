package com.ld.util.excel.reader.rule;

import com.ld.util.excel.core.ColumnHeader;
import com.ld.util.excel.exception.ExcelException;

import java.util.List;

/**
 * @ClassName NullMatchingRule
 * @Description 不进行列头匹配校验的规则
 * @Author 梁聃
 * @Date 2021/10/8 15:15
 */
public class NullMatchingRule implements MatchingRule{

    /**
     * 检查导入列头是否符合规则
     *
     * @param ruleColumnHeaderList 导入规则列头
     * @param columnList           当前文件列头
     * @throws ExcelException
     */
    @Override
    public void checkColumns(List<ColumnHeader> ruleColumnHeaderList, List<ColumnHeader> columnList) throws ExcelException {
    }


}
