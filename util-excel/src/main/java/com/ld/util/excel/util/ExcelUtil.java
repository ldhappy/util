package com.ld.util.excel.util;

/**
 * excel通用的相关工具
 * 梁聃 2019/1/9 10:54
 */
public class ExcelUtil {
    /**
     * Excel column index begin 1
     * excel数字转列号字符串
     * @param columnIndex
     * @return
     */
    public static String excelColIndexToStr(int columnIndex) {
        columnIndex++;
        if (columnIndex <= 0) {
            return null;
        }
        String columnStr = "";
        columnIndex--;
        do {
            if (columnStr.length() > 0) {
                columnIndex--;
            }
            columnStr = ((char) (columnIndex % 26 + (int) 'A')) + columnStr;
            columnIndex = (int) ((columnIndex - columnIndex % 26) / 26);
        } while (columnIndex > 0);
        return columnStr;
    }

    /**
     * Excel column index begin 1
     * excel列号字符串转数字
     * @param colStr
     * @return
     */
    public static int excelColStrToNum(String colStr) {
        int num = 0;
        int result = 0;
        int length = colStr.length();
        for (int i = 0; i < length; i++) {
            char ch = colStr.charAt(length - i - 1);
            num = (int) (ch - 'A' + 1);
            num *= Math.pow(26, i);
            result += num;
        }
        return result-1;
    }

}
