package com.ld.util.transition.example;

import com.ld.util.transition.parse.MapParse;
import lombok.extern.slf4j.Slf4j;

import java.beans.IntrospectionException;
import java.util.HashMap;
import java.util.Map;

/**
 * map解析样例
 * 梁聃 2018/3/13 13:55
 */
@Slf4j
public class MapParseExample {
    public static void main(String[] args) throws IntrospectionException {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("A","No.1");
        map.put("B","梁聃");
        map.put("C",28);
        map.put("D","女1");
        Student student = new Student();
        MapParse<Student> mp = new MapParse<>(map,student);
        mp.setMustValidate(true);
        mp.parse();
        log.debug(student.toString());
    }


}




