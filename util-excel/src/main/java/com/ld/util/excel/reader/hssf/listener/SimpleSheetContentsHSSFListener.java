package com.ld.util.excel.reader.hssf.listener;

import com.ld.util.excel.reader.content.RowContentReader;
import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.record.BOFRecord;
import org.apache.poi.hssf.record.EOFRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.RowRecord;

/**
 * 简单的Sheet内容读取器，仅读取第一个sheet的内容
 * 适配器，实际调用RowContentReader<T>解析
 * 梁聃 2019/1/7 15:27
 */
public class SimpleSheetContentsHSSFListener extends AbstractSheetContentsHSSFListener implements HSSFListener {
    /**
     * 记录当前excel的最大行数
     */
    private int maxRows = 0;
    /**
     * 记录当前excel的最大行数
     */
    private int firstRowColumns = 0;
    /**
     * 是否需要继续解析的标志
     */
    private boolean analysisFlag = true;
    /**
     * 开始解析sheet标志
     */
    private boolean sheetStartFlag = false;

    public SimpleSheetContentsHSSFListener(RowContentReader rowContentReader) {
        super(rowContentReader);
    }

    @Override
    public void processRecord(Record record) {
        if (!analysisFlag){
            return;
        }
        //调用父类处理默认的记录处理
        processRecordDefault( record);
        switch (record.getSid()) {
            case BOFRecord.sid:
                //Beginning Of File Record
                BOFRecord br = (BOFRecord) record;
                if (br.getType() == BOFRecord.TYPE_WORKSHEET){
                    //此处可判断sheet解析开始
                    sheetStartFlag = true;
                }
                break;
            case EOFRecord.sid:
                //End Of File record
                EOFRecord eofRecord = (EOFRecord) record;
                if(sheetStartFlag){
                    //此处可判断sheet解析结束，后续停止解析
                    analysisFlag = false;
                }
                break;
            case RowRecord.sid:
                //行记录
                rowHandle((RowRecord)record);
                break;
            default:
                break;
        }
    }

    public int getMaxRows() {
        return maxRows;
    }

    public int getFirstRowColumns() {
        return firstRowColumns;
    }

    /**
     * RowRecord记录的处理
     * 记录excel的最大行数
     * 记录第一行列头包含列数
     * @param record
     */
    private void rowHandle(RowRecord record) {
        if(maxRows == 0){
            firstRowColumns = record.getLastCol();
        }
        maxRows++;
    }




}
