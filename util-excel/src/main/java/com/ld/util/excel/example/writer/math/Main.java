package com.ld.util.excel.example.writer.math;

import com.ld.util.excel.core.ExportColumnHeader;
import com.ld.util.excel.writer.DefaultExcelWriter;
import com.ld.util.excel.writer.output.FileOutputStreamROP;
import com.ld.util.excel.writer.sheet.ISheetWriter;
import com.ld.util.excel.writer.sheet.StandardSheetWriter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * @ClassName MathematicalProblem
 * @Description 数学题，加减法
 * @Author 梁聃
 * @Date 2021/9/22 17:13
 */
@Slf4j
public class Main {


    public static void main(String[] args) {
        //二元运算竖式,加减法
        log.debug("文件地址："+operation(OperationConfig.builder()
                    .sheet(20)
                    .column(10)
                    .row(14)
                    .minNum(10)
                    .maxNum(89)
                    .fileNamePre("二元运算竖式100以内加减法")
                    .op(Op.ADD)
                    .op(Op.SUB)
                    .createFunction(operationConfig -> new BinaryOperation(operationConfig)).build(),
                Main::vertical));
        //二元运算横式,加减法
        log.debug("文件地址："+operation(OperationConfig.builder()
                .sheet(1)
                .column(5)
                .row(399)
                .minNum(10)
                .maxNum(89)
                .fileNamePre("二元运算横式100以内加减法")
                .op(Op.ADD)
                .op(Op.SUB)
                .createFunction(operationConfig -> new BinaryOperation(operationConfig)).build(),
                Main::horizontal));
    }

    private static Collection<? extends ExportColumnHeader<MathematicalProblemRow, ?>>  vertical(OperationConfig operationConfig){
        List<ExportColumnHeader<MathematicalProblemRow, ?>> columnHeaderList = new ArrayList<>(operationConfig.getColumn());
        IntStream.range(0, operationConfig.getColumn()).forEach(i -> columnHeaderList.add(
                ExportColumnHeader.<MathematicalProblemRow, String>exportColumnHeaderBuilder()
                        .columnName(i+"")
                        .coverageRow(0)
                        .columnFunction(mathematicalProblemRow -> mathematicalProblemRow.operationList.get(i).verticalString())
                        .build()));
        return columnHeaderList;
    }

    private static Collection<? extends ExportColumnHeader<MathematicalProblemRow, ?>> horizontal(OperationConfig operationConfig) {
        List<ExportColumnHeader<MathematicalProblemRow, ?>> columnHeaderList = new ArrayList<>(operationConfig.getColumn());
        IntStream.range(0, operationConfig.getColumn()).forEach(i -> columnHeaderList.add(
                ExportColumnHeader.<MathematicalProblemRow, String>exportColumnHeaderBuilder()
                        .columnName(operationConfig.getCreateFunction().apply(operationConfig).horizontalString())
                        .columnFunction(mathematicalProblemRow -> mathematicalProblemRow.operationList.get(i).horizontalString())
                        .build()));
        return columnHeaderList;
    }

    private static String operation(OperationConfig operationConfig, Function<OperationConfig,Collection<? extends ExportColumnHeader<MathematicalProblemRow, ?>>> function) {
        DefaultExcelWriter<String> writer = DefaultExcelWriter.<String>builder()
                .fileNamePre(operationConfig.getFileNamePre())
                .rowAccessWindowSize(100)
                .sheetWriterList(createSheetList(operationConfig,function))
                .build();
        return writer.write(new FileOutputStreamROP("d://"));
    }

    public static Collection<? extends ISheetWriter> createSheetList(OperationConfig operationConfig, Function<OperationConfig, Collection<? extends ExportColumnHeader<MathematicalProblemRow, ?>>> function) {
        List<ISheetWriter> sheetList = new ArrayList<>(operationConfig.getSheet());
        IntStream.range(0, operationConfig.getSheet()).forEach(i ->
                sheetList.add(
                        StandardSheetWriter.<MathematicalProblemRow>builder()
                                .sheetName(i + "")
                                .needDefaultCoordinate(true)
                                .columnHeaderList(function.apply(operationConfig))
                                .contentList(createRowList(operationConfig))
                                .build()));
        return sheetList;
    }

    private static Collection<? extends MathematicalProblemRow> createRowList(OperationConfig operationConfig) {
        List<MathematicalProblemRow> rowList = new ArrayList<>(operationConfig.getRow());
        IntStream.range(0, operationConfig.getRow()).forEach(i -> rowList.add(new MathematicalProblemRow(operationConfig)));
        return rowList;
    }




    /**
     * 每行的题目
     */
    public static class MathematicalProblemRow {
        public List<AbstractOperation> operationList;


        public MathematicalProblemRow(OperationConfig operationConfig) {
            operationList = new ArrayList<>(operationConfig.getColumn());
            IntStream.range(0, operationConfig.getColumn()).forEach(i -> operationList.add(operationConfig.getCreateFunction().apply(operationConfig)));
        }
    }

}
