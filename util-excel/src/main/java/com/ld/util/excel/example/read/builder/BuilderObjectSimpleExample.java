package com.ld.util.excel.example.read.builder;

import com.ld.util.excel.core.ColumnHeader;
import com.ld.util.excel.core.ExportColumnHeader;
import com.ld.util.excel.reader.ExcelReader;
import com.ld.util.excel.reader.ReadResult;
import com.ld.util.excel.reader.input.FileSourceInput;
import com.ld.util.excel.reader.rule.ReadRule;
import com.ld.util.excel.writer.DefaultExcelWriter;
import com.ld.util.excel.writer.output.FileOutputStreamROP;
import com.ld.util.excel.writer.sheet.StandardSheetWriter;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @ClassName BuilderObjectSimpleExample
 * @Description 使用构造函数创建excel导入任务，简单样例（单行表头）
 * @Author 梁聃
 * @Date 2021/9/25 10:04
 */
@Slf4j
public class BuilderObjectSimpleExample {
    public static void main(String[] args) {
        ReadRule<People> readRule = ReadRule.<People>builder()
                .ruleColumnHeader(ColumnHeader.columnHeaderBuilder().columnName("姓名").coordinate("A1").build())
                .ruleColumnHeader(ColumnHeader.columnHeaderBuilder().columnName("年龄").coordinate("B1").build())
                .ruleColumnHeader(ColumnHeader.columnHeaderBuilder().columnName("性别").coordinate("C1").build())
                .targetClass(People.class)
                .build();
        ExcelReader<People> excelReader = new ExcelReader<>(new FileSourceInput(),readRule);
        ReadResult<People> readResult = excelReader.read("d:/BuilderObjectSimpleExample_1634087556390.xlsss");
        log.info(readResult.toString());
    }
}
