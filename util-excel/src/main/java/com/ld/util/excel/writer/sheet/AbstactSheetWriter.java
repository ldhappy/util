package com.ld.util.excel.writer.sheet;

import com.ld.util.excel.exception.ExcelException;
import com.ld.util.excel.message.ExcelMessageSource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName AbstactSheetWriter
 * @Description sheet输出抽象类
 * @Author 梁聃
 * @Date 2021/9/22 15:02
 */
@Slf4j
@Getter
@ToString
@EqualsAndHashCode
public abstract class AbstactSheetWriter implements ISheetWriter {
    /**
     * 工作表名称
     */
    protected String sheetName;

    public AbstactSheetWriter(String sheetName) {
        if(StringUtils.isBlank(sheetName)){
            throw ExcelException.messageException(ExcelMessageSource.WRITE_SHEET_NAME_EMPTY);
        }
        this.sheetName = sheetName;
    }

    /**
     * 解决导出需要合并多行的需求
     * 样例：
     * ExportColumnHeader<UnitTransDetailVo,ExcelWriter.Content> transTime = new ExportColumnHeader<>("换届时间","E2",1,2,unitTransDetailVo -> {
     *             ExcelWriter.Content content = new ExcelWriter.Content();
     *             if(CollectionUtils.isNotEmpty(unitTransDetailVo.getHistoryTrans())){
     *                 unitTransDetailVo.getHistoryTrans().stream().forEach(transDetail -> {
     *                     if(Objects.nonNull(transDetail.getTransTime())){
     *                         content.getColumnValues().add(transDetail.getTransTime().toString());
     *                     }else {
     *                         content.getColumnValues().add("");
     *                     }
     *                     content.getCoverageRows().add(1);
     *
     *                 });
     *             }
     *             return content;
     *         });
     */
    @Data
    public static class Content{
        //列内容
        private List<String> columnValues = new ArrayList<>();
        //占行数
        private List<Integer> coverageRows = new ArrayList<>();

    }
}
