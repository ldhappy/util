package com.ld.util.excel.example.writer.math;

public enum Op {
    ADD(0,"+","加法"),

    SUB(1,"-","减法"),

    MULT(2,"*","乘法");

    private int id;
    private String code;
    private String codeZh;

    Op(int id, String code, String codeZh) {
        this.id = id;
        this.code = code;
        this.codeZh = codeZh;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getCodeZh() {
        return codeZh;
    }
}
