package com.ld.util.excel.reader.hssf;

import com.ld.util.excel.reader.IInputStreamReader;
import com.ld.util.excel.reader.content.RowContentReader;
import com.ld.util.excel.reader.hssf.listener.FormatTrackingHSSFListener;
import com.ld.util.excel.reader.hssf.listener.SimpleSheetContentsHSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.eventusermodel.MissingRecordAwareHSSFListener;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.*;
import java.util.Objects;

/**
 * 读取xls文件
 * 梁聃 2019/1/6 11:19
 */
public class XlsReader implements IInputStreamReader {

    public static final String FILE_POSTFIX = ".xls";

    /**
     * 是否支持指定后缀文件
     *
     * @param filePostfix 文件后缀
     * @return
     */
    @Override
    public boolean support(String filePostfix) {
        return Objects.equals(FILE_POSTFIX,filePostfix);
    }

    /**
     * 读取内容
     *
     * @param inputStream
     * @param rowContentReader
     */
    @Override
    public <T> void read(InputStream inputStream, RowContentReader<T> rowContentReader) throws Exception {
        HSSFEventFactory factory = new HSSFEventFactory();
        HSSFRequest request = new HSSFRequest();
        //注册Record处理监听器
        SimpleSheetContentsHSSFListener sheetContentsHSSFListener = new SimpleSheetContentsHSSFListener(rowContentReader);
        MissingRecordAwareHSSFListener listener = new MissingRecordAwareHSSFListener(
                sheetContentsHSSFListener);
        FormatTrackingHSSFListener formatListener = new FormatTrackingHSSFListener(listener);
        sheetContentsHSSFListener.setFormatListener(formatListener);
        request.addListenerForAllRecords(formatListener);
        factory.processWorkbookEvents(request, new POIFSFileSystem(inputStream));
    }
}
