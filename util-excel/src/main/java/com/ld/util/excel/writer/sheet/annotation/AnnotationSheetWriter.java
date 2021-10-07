package com.ld.util.excel.writer.sheet.annotation;

import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import com.ld.util.excel.core.ExportColumnHeader;
import com.ld.util.excel.writer.sheet.ISheetWriter;
import com.ld.util.excel.writer.sheet.StandardSheetWriter;
import com.ld.util.transition.convert.AnnotationConvertClassReflect;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * @ClassName AnnotationSheetWriter
 * @Description T中包含了打印sheet需要的全部注解，转换为标准打印对象去输出结果
 * @Author 梁聃
 * @Date 2021/9/22 15:09
 */
@Slf4j
@Getter
public abstract class AnnotationSheetWriter<T> implements ISheetWriter {
    private static ConcurrentMap<String, AnnotationFieldColumnHeaderClassReflect<?>> classReflectMap = new ConcurrentHashMap<>();
    /**
     * 导出内容
     */
    private StandardSheetWriter<T> standardSheetWriter;

    private Class<T> resultClass;

    /**
     * @param contentList 导出内容
     */
    public AnnotationSheetWriter(List<T> contentList) {
        Type superclass = getClass().getGenericSuperclass();
        Type runtimeType = ((ParameterizedType) superclass).getActualTypeArguments()[0];
        this.resultClass = (Class<T>) TypeToken.of(runtimeType).getRawType();
        AnnotationFieldColumnHeaderClassReflect classReflect = getClassReflect();
        standardSheetWriter = StandardSheetWriter.<T>builder()
                .needDefaultCoordinate(true)
                .sheetName(classReflect.getSheetName())
                .columnHeaderList(annotationColumnHeaderList(classReflect))
                .contentList(contentList).build();
    }

    public List<ExportColumnHeader<T, ?>> annotationColumnHeaderList(AnnotationFieldColumnHeaderClassReflect classReflect){
        List<ExportColumnHeader<T, ?>> columnHeaderList = Lists.newArrayList();
        List<FieldColumnHeader> fieldColumnHeaderList = classReflect.getFieldColumnHeaderList();
        fieldColumnHeaderList.forEach(fieldColumnHeader->
                        columnHeaderList.add(
                                ExportColumnHeader.<T,Object>exportColumnHeaderBuilder()
                                        .columnName(fieldColumnHeader.getName())
                                        .columnFunction(object-> {
                                            try {
                                                return fieldColumnHeader.getField().get(object);
                                            } catch (IllegalAccessException e) {
                                                e.printStackTrace();
                                                return "单元格内容异常无法输出";
                                            }
                                        })
                                        .build())
                );
        return columnHeaderList;
    }

    /**
     * 向workbook对象中写入导出内容
     *
     * @param workbook
     */
    @Override
    public void write(Workbook workbook) {
        standardSheetWriter.write(workbook);
    }

    private AnnotationFieldColumnHeaderClassReflect getClassReflect() {
        AnnotationFieldColumnHeaderClassReflect classReflect = classReflectMap.get(resultClass.getName());
        if(classReflect == null) {
            classReflectMap.putIfAbsent(resultClass.getName(),new AnnotationFieldColumnHeaderClassReflect(resultClass));
            classReflect=classReflectMap.get(resultClass.getName());
        }
        return classReflect;
    }
}
