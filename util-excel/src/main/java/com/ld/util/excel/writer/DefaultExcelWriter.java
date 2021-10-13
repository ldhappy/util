package com.ld.util.excel.writer;

import com.ld.util.excel.exception.ExcelException;
import com.ld.util.excel.message.ExcelMessageSource;
import com.ld.util.excel.writer.output.IResultOutPut;
import com.ld.util.excel.writer.sheet.ISheetWriter;
import lombok.Builder;
import lombok.Singular;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

/**
 * @ClassName StandardSheetExcelWriter
 * @Description 默认的写excel工具，支持多个sheet输出
 * @Author 梁聃
 * @Date 2021/9/22 9:46
 */
@Slf4j
public class DefaultExcelWriter<R> extends AbstractExcelWriter<R>{
    /**
     * 需要书写到工作表的内容
     */
    private List<ISheetWriter> sheetWriterList;

    @Builder
    public DefaultExcelWriter(String fileNamePre, IResultOutPut resultOutPut, Integer rowAccessWindowSize,@Singular("sheetWriter") List<ISheetWriter> sheetWriterList) {
        super(fileNamePre, resultOutPut, rowAccessWindowSize);
        if(CollectionUtils.isEmpty(sheetWriterList)){
            throw ExcelException.messageException(ExcelMessageSource.WRITE_SHEET_EMPTY);
        }
        this.sheetWriterList = sheetWriterList;
    }

    /**
     * 在扩展类中写入每个工作表的内容
     *
     * @param workbook
     */
    @Override
    protected void writeSheetContent(Workbook workbook) {
        sheetWriterList.forEach(sheetWriter -> sheetWriter.write(workbook));
    }
}
