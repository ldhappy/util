package com.ld.util.excel.writer.output;

import org.apache.poi.ss.usermodel.Workbook;

/**
 * @ClassName IResultOutPut
 * @Description excel导出文件输出方式接口
 * @Author 梁聃
 * @Date 2021/9/22 16:33
 */
public interface IResultOutPut<R> {
    /**
     * 输出导出结果
     * @param fileNamePre
     * @param workbook
     * @return
     */
    R outPut(String fileNamePre, Workbook workbook) throws Exception ;
}
