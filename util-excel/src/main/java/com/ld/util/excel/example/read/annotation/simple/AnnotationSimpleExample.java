package com.ld.util.excel.example.read.annotation.simple;

import com.ld.util.excel.reader.ExcelReader;
import com.ld.util.excel.reader.ReadResult;
import com.ld.util.excel.reader.input.FileSourceInput;
import com.ld.util.excel.writer.ExcelErrorOutPutWriter;
import com.ld.util.excel.writer.output.FileOutputStreamROP;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName AnnotationSimpleExample
 * @Description 使用注解创建excel导入任务，简单样例（单行表头）
 * @Author 梁聃
 * @Date 2021/10/18 14:25
 */
@Slf4j
public class AnnotationSimpleExample {
    public static void main(String[] args) {
        ExcelReader<People> excelReader = new ExcelReader<>(new FileSourceInput(),People.class);
        String sourceKey = "d:\\BuilderObjectSimpleExample_1634550467404.xlsx";
        ReadResult<People> readResult = excelReader.read(sourceKey);
        //打印结果
        log.debug(readResult.toString());
        //将错误信息打印成excel
        ExcelErrorOutPutWriter<String> excelErrorOutPutWriter = ExcelErrorOutPutWriter.<String>builder()
                .readResult(readResult)
                .build();
        log.debug("文件地址："+excelErrorOutPutWriter.write(new FileOutputStreamROP("d://")));
    }
}
