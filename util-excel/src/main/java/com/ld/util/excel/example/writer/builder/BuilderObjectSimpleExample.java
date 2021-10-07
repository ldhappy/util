package com.ld.util.excel.example.writer.builder;

import com.ld.util.excel.core.ExportColumnHeader;
import com.ld.util.excel.writer.DefaultExcelWriter;
import com.ld.util.excel.writer.output.FileOutputStreamROP;
import com.ld.util.excel.writer.sheet.StandardSheetWriter;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @ClassName BuilderObjectSimpleExample
 * @Description 使用构造函数创建excel输出任务，简单样例（单行表头）
 * @Author 梁聃
 * @Date 2021/9/25 10:04
 */
@Slf4j
public class BuilderObjectSimpleExample {
    public static void main(String[] args) {
        DefaultExcelWriter<String> writer = DefaultExcelWriter.<String>builder()
                //文件前缀
                .fileNamePre("BuilderObjectSimpleExample")
                //本地文件地址前缀
                .resultOutPut(new FileOutputStreamROP("d://"))
                //单个工作空间需要打印的信息配置
                .sheetWriter(StandardSheetWriter.<People>builder()
                        //工作空间名
                        .sheetName("导出信息")
                        //是否需要设置默认的列序号，默认不需要
                        .needDefaultCoordinate(true)
                        //每个需要导出的列---开始
                        .columnHeader(ExportColumnHeader.<People,String>exportColumnHeaderBuilder()
                                .columnName("姓名")
                                .columnFunction(People::getName)
                                .build())
                        .columnHeader(ExportColumnHeader.<People,Integer>exportColumnHeaderBuilder()
                                .columnName("年龄")
                                .columnFunction(People::getAge)
                                .build())
                        .columnHeader(ExportColumnHeader.<People,String>exportColumnHeaderBuilder()
                                .columnName("性别")
                                .columnFunction(people -> Objects.isNull(people.getSex())?"未知":(Objects.equals(0,people.getSex())?"男":"女"))
                                .build())
                        //每个需要导出的列---结束
                        //每一行需要导出的内容---开始
                        .content(new People("测试1",28,null))
                        .content(new People("测试2",30,0))
                        .content(new People("测试3",27,1))
                        //每一行需要导出的内容---结束
                        .build())
                .build();
        log.debug("文件地址："+writer.write());
    }
}
