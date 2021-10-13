package com.ld.util.excel.reader.formatter;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.util.LocaleUtil;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 修改excel的格式化方式
 * 日期格式强制转换为"yyyy/MM/dd HH:mm:ss"
 */
public class ExcelDataFormatter extends DataFormatter {
    public ExcelDataFormatter(Locale locale) {
        super(locale);
    }

    public ExcelDataFormatter() {
        this(LocaleUtil.getUserLocale());
    }
    @Override
    public String formatRawCellContents(double value, int formatIndex, String formatString) {
        Format numberFormat;
        if (DateUtil.isADateFormat(formatIndex, formatString)) {
            if (DateUtil.isValidExcelDate(value)) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date d = DateUtil.getJavaDate(value, false);
                return format.format(d);
            }
        }
        return this.formatRawCellContents(value, formatIndex, formatString, false);
    }
}
