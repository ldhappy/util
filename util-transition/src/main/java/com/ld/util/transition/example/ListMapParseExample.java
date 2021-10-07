package com.ld.util.transition.example;

import com.ld.util.transition.parse.ListMapParse;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Listmap解析样例
 * 梁聃 2018/3/13 13:55
 */
public class ListMapParseExample {
    public static void main(String[] args) throws IntrospectionException {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("A","No.1");
        map.put("B","梁聃");
        map.put("C",28);
        map.put("D","女");
        Map<String,Object> map2 = new HashMap<String,Object>();
        map.put("A","No.12");
        map.put("B","梁聃2");
        map.put("C",29);
        map.put("D","男");
        List<Map<String,Object>> source = new ArrayList<Map<String, Object>>();
        source.add(map);
        source.add(map2);
        List<Student> result = new ArrayList<Student>();
        ListMapParse<Student> mp = new ListMapParse<Student>(source,result){};
        mp.parse();
            System.out.println("正确转换结果：");
            for(Student s:mp.getResult()){
                System.out.println(s);
            }
            System.out.println("错误列表：");
            for(Map s:mp.getErrorList()){
                System.out.println(s);
            }
    }


}




