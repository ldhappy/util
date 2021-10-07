package com.ld.util.excel.writer;

import com.ld.util.excel.writer.output.IResultOutPut;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.Objects;

/**
 * @ClassName AbstractExcelWriter
 * @Description 写excel工具,抽象类
 * @Author 梁聃
 * @Date 2021/9/22 14:13
 */
@Slf4j
public abstract class AbstractExcelWriter<R> {
    public static final String XLSX_EXT = ".xlsx";
    /**
     * 导出文件名前缀
     */
    private String fileNamePre;

    /**
     * excel导出文件输出方式
     */
    private IResultOutPut<R> resultOutPut;

    /**
     * 分批次导出文件行数
     */
    private Integer rowAccessWindowSize;

    public AbstractExcelWriter(String fileNamePre, IResultOutPut<R> resultOutPut, Integer rowAccessWindowSize) {
        this.fileNamePre = fileNamePre;
        this.resultOutPut = resultOutPut;
        this.rowAccessWindowSize = Objects.isNull(rowAccessWindowSize)?1000:rowAccessWindowSize;
    }

    public R write(){
        //采用性能更高的SXSSFWorkbook方式
        SXSSFWorkbook workbook = new SXSSFWorkbook(new XSSFWorkbook(), rowAccessWindowSize);
        //在扩展类中写入每个工作表的内容
        writeSheetContent(workbook);
        return resultOutPut.outPut(fileNamePre,workbook);
    }

    /**
     * 在扩展类中写入每个工作表的内容
     * @param workbook
     */
    protected abstract void writeSheetContent(Workbook workbook);

    public String getFileNamePre() {
        return fileNamePre;
    }
}
