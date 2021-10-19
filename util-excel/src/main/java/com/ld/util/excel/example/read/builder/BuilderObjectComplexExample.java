package com.ld.util.excel.example.read.builder;

import com.google.common.collect.Lists;
import com.ld.util.excel.core.ColumnHeader;
import com.ld.util.excel.reader.DefaultExcelReader;
import com.ld.util.excel.reader.ReadResult;
import com.ld.util.excel.reader.input.FileSourceInput;
import com.ld.util.excel.reader.rule.ReadRule;
import com.ld.util.excel.writer.ExcelErrorOutPutWriter;
import com.ld.util.excel.writer.output.FileOutputStreamROP;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @ClassName BuilderObjectSimpleExample
 * @Description 使用构造函数创建excel导入任务，复杂样例，多行表头
 * @Author 梁聃
 * @Date 2021/9/25 10:04
 */
@Slf4j
public class BuilderObjectComplexExample {
    public static void main(String[] args) {
        List<ColumnHeader> ruleColumnHeaderList = Lists.newArrayList(
                ColumnHeader.columnHeaderBuilder().columnName("姓名").coordinate("A1").build(),
                ColumnHeader.columnHeaderBuilder().columnName("年龄").coordinate("B1").build(),
                ColumnHeader.columnHeaderBuilder().columnName("性别").coordinate("C1").build(),
                ColumnHeader.columnHeaderBuilder().columnName("合并单元格测试").coordinate("A2").coverageColumn(3).coverageRow(2).build()
        );
        ReadRule<People> readRule = ReadRule.<People>builder()
                .ruleColumnHeaderList(ruleColumnHeaderList)
                .targetClass(People.class)
                .build();
        DefaultExcelReader<People> excelReader = new DefaultExcelReader<>(new FileSourceInput(),readRule);
        String sourceKey = "d:\\BuilderObjectSimpleExample_1634196295539.xlsx";
        ReadResult<People> readResult = excelReader.read(sourceKey);
        //打印结果
        log.debug(readResult.toString());
        //将错误信息打印成excel
        ExcelErrorOutPutWriter<String> excelErrorOutPutWriter = ExcelErrorOutPutWriter.<String>builder()
                .readResult(readResult)
                .columnHeaderList(ruleColumnHeaderList)
                .build();
        log.debug("文件地址："+excelErrorOutPutWriter.write(new FileOutputStreamROP("d://")));
    }
}
