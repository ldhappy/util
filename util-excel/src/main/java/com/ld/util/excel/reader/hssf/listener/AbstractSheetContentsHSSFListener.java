package com.ld.util.excel.reader.hssf.listener;

import com.ld.util.excel.exception.ExcelException;
import com.ld.util.excel.message.ExcelMessageSource;
import com.ld.util.excel.reader.content.RowContentReader;
import com.ld.util.excel.util.ExcelUtil;
import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.eventusermodel.dummyrecord.LastCellOfRowDummyRecord;
import org.apache.poi.hssf.record.*;

/**
 * Sheet内容读取器
 * 适配器，实际调用RowContentDefaultReader<T>解析
 */
public abstract class AbstractSheetContentsHSSFListener implements HSSFListener {
    /**
     * 默认的索引起始值
     */
    protected static final int DEFAULT_INDEX = -1;
    /**
     * 统一xls，xlsx文件内容读取的接口,提供默认，可扩展
     */
    protected RowContentReader rowContentReader;
    /**
     * 所有单元字符串记录
     */
    protected SSTRecord sstRecord;

    /**
     * 当前行索引
     */
    protected int currentRowIndex = DEFAULT_INDEX;
    /**
     * 当前列索引
     */
    protected int currentColumnIndex = DEFAULT_INDEX;

    private FormatTrackingHSSFListener formatListener;
    public AbstractSheetContentsHSSFListener(RowContentReader rowContentReader) {
        this.rowContentReader = rowContentReader;
    }

    /**
     * 提供默认的记录处理方式
     * @param record
     */
    protected void processRecordDefault(Record record) {
        //单元记录
        if(record instanceof CellRecord){
            processCellRecordDefault((CellRecord)record);
        }
        //行结束记录
        if(record instanceof LastCellOfRowDummyRecord){
            endRow((LastCellOfRowDummyRecord)record);
        }
        //字符串记录
        if(record.getSid() == SSTRecord.sid){
            sstRecord = (SSTRecord) record;
        }
    }
    /**
     * 提供默认的单元格处理
     * @param record
     */
    private void processCellRecordDefault(CellRecord record) {
        cellHandlePre((CellRecord)record);
        switch (record.getSid()){
            case LabelSSTRecord.sid:
                //字符串标签记录
                labelSSTHandle((LabelSSTRecord)record);
                break;
            case BoolErrRecord.sid:
                boolErrHandle((BoolErrRecord)record);
                break;
            case FormulaRecord.sid:
                formulaHandle((FormulaRecord)record);
                break;
            case NumberRecord.sid:
                numberHandle((NumberRecord)record);
                break;
            default:break;
        }
    }




    /**
     * CellRecord记录的处理
     * 调用RowContentReader的cell方法，处理单元格内容
     * @param cellValue
     */
    private void cellHandle(String cellValue) {
        //此处s字段，将列号+行号合成如A1的字符串，适配xlsx的格式
        rowContentReader.cell(ExcelUtil.excelColIndexToStr(currentColumnIndex)+(currentRowIndex+1),cellValue,null);
    }
    /**
     * LabelSSTRecord记录的处理
     * 字符串标签记录
     * @param record
     */
    private void labelSSTHandle(LabelSSTRecord record) {
        if (sstRecord == null) {
            ExcelException.messageException(ExcelMessageSource.ANALYSIS_ERROR_XLS_NO_SST_RECORD);
        } else {
            cellHandle(sstRecord.getString(record.getSSTIndex()).getString());
        }
    }

    /**
     * BoolErrRecord记录的处理
     * boolean类型数据
     * @param record
     */
    private void boolErrHandle(BoolErrRecord record) {
        cellHandle(Boolean.toString(record.getBooleanValue()));
    }

    /**
     * FormulaRecord记录的处理
     * 公式类型数据
     * @param record
     */
    private void formulaHandle(FormulaRecord record) {
        cellHandle(formatListener.formatNumberDateCell(record));
    }

    /**
     * NumberRecord记录的处理
     * 数字类型数据
     * @param record
     */
    private void numberHandle(NumberRecord record) {
        cellHandle(formatListener.formatNumberDateCell(record));
    }


    /**
     * CellRecord记录的处理（开始）
     * 开始新行时调用RowContentReader的startRow方法，设置当前列号
     * @param record
     */
    private void cellHandlePre(CellRecord record) {
        if(currentRowIndex != record.getRow()){
            //开始新行
            currentRowIndex = record.getRow();
            rowContentReader.startRow(currentRowIndex);
        }
        currentColumnIndex = record.getColumn();
    }

    /**
     * 行结束时调用rowContentReader的endRow方法，重置当前列
     * @param record
     */
    private void endRow(LastCellOfRowDummyRecord record) {
        if(currentRowIndex != record.getRow()){
            //没有执行cellHandlePre的空行不需要执行rowContentReader.endRow(currentRowIndex);方法
            return;
        }
        //行结束时调用
        rowContentReader.endRow(currentRowIndex);
        //行结束，重置当前列
        currentColumnIndex = DEFAULT_INDEX;
    }

    public void setFormatListener(FormatTrackingHSSFListener formatListener) {
        this.formatListener = formatListener;
    }


}
