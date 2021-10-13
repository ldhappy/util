package com.ld.util.excel.reader.content;


import com.ld.util.excel.core.ColumnHeader;
import com.ld.util.excel.exception.ExcelException;
import com.ld.util.excel.message.ExcelMessageSource;
import com.ld.util.excel.reader.ReadResult;
import com.ld.util.excel.reader.rule.ReadRule;
import com.ld.util.transition.exception.ParseException;
import com.ld.util.transition.parse.MapParse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFComment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 默认统一内容读取器
 * 可直接全量读取excel或按照读取规则限制的最大行数读取
 * currentRow，key:excel的列号字符串，如A,B,C,D
 * 解析的目标对象配置方式见example.Student
 * 要求解析目标对象的@Name注解的sourceName = "A"与Map<String, String> currentRow对象的key对应
 * 梁聃 2019/1/6 17:09
 */
@Slf4j
public class DefaultRowContentReader<T> implements RowContentReader<T> {

    /**
     * excel的解析规则
     */
    protected ReadRule<T> readRule;
    /**
     * excel的解析结果
     */
    protected ReadResult<T> readResult;
    /**
     * 列名List
     */
    protected List<ColumnHeader> columnHeaderList = new ArrayList<>();
    /**
     * 当前索引行号
     */
    protected int currentIndex = 0;
    /**
     * 当前行信息
     * key:excel的列号字符串，如A,B,C,D
     * value:单元格实际内容
     */
    protected Map<String, String> currentRow;

    /**
     * 行解析开始时调用
     * @param index 当前索引号
     */
    @Override
    public void startRow(int index) {
        if(index == readRule.getColumnContentBeginRowIndex()){
            //满足条件表示正文开始，先检查列头是否符合条件
            //1.校验列头名称信息
            readRule.checkColumns(columnHeaderList);
        }
        currentIndex = index;
        if(index >= readRule.getColumnContentBeginRowIndex()){
            //校验不可超过最大行数
            if(readRule.getMaxRows() != null && readRule.getMaxRows() < (index-readRule.getColumnContentBeginRowIndex()+1)){
                throw ExcelException.messageException(ExcelMessageSource.READ_OVERFLOW_MAX_ROWS,readRule.getMaxRows());
            }
            currentRow = new HashMap<>();
        }
    }

    /**
     * 行解析结束时调用
     * @param index 当前索引号
     */
    @Override
    public void endRow(int index) {
        if(index >= readRule.getColumnContentBeginRowIndex()){
            if(currentRow.size() > 0){
                //2.内容行开始，将内容转换为目标对象
                Class clazz = readRule.getTargetClass();
                MapParse<T> mp = null;
                try {
                    T object = (T)clazz.newInstance();
                    mp = new MapParse<T>(currentRow,object);
                    //设置是否必须要校验，false时不校验，直接转换
                    mp.setMustValidate(true);
                    //转换
                    mp.parse();
                    //SuccessRows存储转换后的对象
                    readResult.getSuccessRows().put(object,index);
                    //SuccessRowsOriginal存储excel的原内容，在不能通过内容校验时使用
                    readResult.getSuccessRowsOriginal().put(index,currentRow);
                } catch (ParseException e) {
                    log.warn(index+"行内容转换失败，原因：",e);
                    readResult.putFaultRow(index,e.getMessage(),currentRow);
                } catch (InstantiationException e) {
                    log.warn(index+"行内容转换失败，原因：",e);
                    readResult.putFaultRow(index,e.getMessage(),currentRow);
                } catch (IllegalAccessException e) {
                    log.warn(index+"行内容转换失败，原因：",e);
                    readResult.putFaultRow(index,e.getMessage(),currentRow);
                }
                readResult.setTotalRowsSize(readResult.getTotalRowsSize()+1);
            }
        }

    }
    /**
     * 每个单元格内容解析时调用
     * @param coordinate 单元格坐标
     * @param value 单元格内容
     * @param xssfComment
     */
    @Override
    public void cell(String coordinate, String value, XSSFComment xssfComment) {
        //数据不为空时处理数据内容
        if(StringUtils.isNotBlank(value)){
            if(readRule.getColumnHeaderBeginRowIndex() <= currentIndex && currentIndex < readRule.getColumnContentBeginRowIndex()){
                //列头
                columnHeaderList.add(ColumnHeader.columnHeaderBuilder().columnName(value).coordinate(coordinate).build());
            }else if(currentIndex >= readRule.getColumnContentBeginRowIndex()){
                //正式行信息
                coordinate=coordinate.substring(0,coordinate.lastIndexOf((currentIndex+1)+""));
                currentRow.put(coordinate,value);
            }
        }
    }

    @Override
    public void headerFooter(String s, boolean b, String s1) {
    }

    public ReadRule<T> getReadRule() {
        return readRule;
    }

    @Override
    public void setReadRule(ReadRule<T> readRule) {
        this.readRule = readRule;
    }

    @Override
    public ReadResult<T> getReadResult() {
        return readResult;
    }

    public void setReadResult(ReadResult<T> readResult) {
        this.readResult = readResult;
    }
}
