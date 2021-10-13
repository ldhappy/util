package com.ld.util.excel.reader.xssf;

import com.ld.util.excel.exception.ExcelException;
import com.ld.util.excel.reader.IInputStreamReader;
import com.ld.util.excel.reader.content.RowContentReader;
import com.ld.util.excel.reader.formatter.ExcelDataFormatter;
import com.ld.util.excel.reader.rule.ReadRule;
import org.apache.poi.ooxml.util.SAXHelper;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * @ClassName XlsxReader
 * @Description xlsx文件的读取器，只能读取首个工作空间
 * @Author 梁聃
 * @Date 2021/10/12 16:50
 */
public class XlsxReader implements IInputStreamReader {

    public static final String FILE_POSTFIX = ".xlsx";

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
    public <T> void read(InputStream inputStream, RowContentReader<T> rowContentReader) throws Exception{
        OPCPackage xlsxPackage = OPCPackage.open(inputStream);
        //读取列名（该excel的所有sheet的所有列头信息）
        ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(xlsxPackage);
        //仅读取第一个sheet的内容
        XSSFReader xssfReader = new XSSFReader(xlsxPackage);
        StylesTable styles = xssfReader.getStylesTable();
        InputStream stream = xssfReader.getSheetsData().next();
        DataFormatter formatter = new ExcelDataFormatter();
        InputSource sheetSource = new InputSource(stream);
        XMLReader sheetParser = SAXHelper.newXMLReader();
        SheetContentsXlsxReader sheetXlsxReader = new SheetContentsXlsxReader(rowContentReader);
        ContentHandler handler = new XSSFSheetXMLHandler(styles, null, strings, sheetXlsxReader, formatter, false);
        sheetParser.setContentHandler(handler);
        //2解析sheet的资源
        sheetParser.parse(sheetSource);
        stream.close();
    }
}
