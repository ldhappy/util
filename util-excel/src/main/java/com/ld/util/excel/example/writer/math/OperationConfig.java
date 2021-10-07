package com.ld.util.excel.example.writer.math;

import lombok.*;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

/**
 * @ClassName OperationConfig
 * @Description 数学题打印配置
 * @Author 梁聃
 * @Date 2021/9/23 16:43
 */
@Getter
@ToString
@EqualsAndHashCode
public class OperationConfig {

    public static final Random RANDOM = new Random();

    //工作空间数量
    private int sheet;
    //列数
    private int column;
    //行数
    private int row;
    //最大值
    private int maxNum;
    //最小值
    private int minNum;
    //文件名称
    private String fileNamePre;
    //允许的运算符
    private List<Op> opList;
    //创建方法
    private Function<OperationConfig, AbstractOperation> createFunction;

    @Builder
    public OperationConfig(int sheet,
                           int column,
                           int row,
                           int maxNum,
                           int minNum,
                           String fileNamePre,
                           @Singular("op") List<Op> opList,
                           Function<OperationConfig, AbstractOperation> createFunction) {
        this.sheet = sheet;
        this.column = column;
        this.row = row;
        this.maxNum = maxNum;
        this.minNum = minNum;
        this.fileNamePre = fileNamePre;
        this.opList = opList;
        this.createFunction = createFunction;
    }

    public Op randomOp() {
        return opList.get(RANDOM.nextInt(opList.size()));
    }
}
