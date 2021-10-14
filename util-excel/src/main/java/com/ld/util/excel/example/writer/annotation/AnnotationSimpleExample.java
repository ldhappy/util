package com.ld.util.excel.example.writer.annotation;

import com.google.common.collect.Lists;
import com.ld.util.excel.writer.DefaultExcelWriter;
import com.ld.util.excel.writer.output.FileOutputStreamROP;
import com.ld.util.excel.writer.sheet.annotation.AnnotationSheetWriter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @ClassName AnnotationSimpleExample
 * @Description 使用构注解对象创建打印需求，打印对象样例
 * @Author 梁聃
 * @Date 2021/9/25 11:50
 */
@Slf4j
public class AnnotationSimpleExample {
    public static void main(String[] args) {
        List<People> peopleList = Lists.newArrayList(new People("测试1",28,null),
                new People("测试2",30,0),
                new People("测试3",27,1));
        DefaultExcelWriter<String> writer = DefaultExcelWriter.<String>builder()
                //文件前缀
                .fileNamePre("AnnotationExample")
                //单个工作空间需要打印的信息配置
                .sheetWriter(new AnnotationSheetWriter<People>(peopleList) {})
                .build();
        log.debug("文件地址："+writer.write(new FileOutputStreamROP("d://")));
    }
}
