package com.ld.util.excel.example.math;

import java.util.Random;

/**
 * @ClassName BinaryOperation
 * @Description 数学题：二元运算
 * @Author 梁聃
 * @Date 2021/9/23 16:40
 */
public class BinaryOperation extends AbstractOperation{

    private static final Random random = new Random();



    //操作符号
    public Op op;
    //第一个操作数
    public Integer num1;
    public String num1Str;
    //第二个操作数
    public Integer num2;
    public String num2Str;

    public BinaryOperation(OperationConfig operationConfig) {
        super(operationConfig);
        op = operationConfig.randomOp();

        num1 = randomNum();
        num2 = op == Op.SUB ? random.nextInt(num1-operationConfig.getMinNum())+operationConfig.getMinNum() : randomNum();

        num1Str = StringNum(num1);
        num2Str = StringNum(num2);

        if(num1Str.length() > num2Str.length()){
            //如果数字1比数字2长
            //数字2补空格
            num2Str = space(num1Str.length()-num2Str.length())+num2Str;
        }else if(num1Str.length() < num2Str.length()){
            //如果数字2比数字1长
            //数字1补空格
            num1Str = space(num2Str.length()-num1Str.length())+num1Str;
        }
    }


    /**
     * 横式打印
     * @return
     */
    @Override
    public String horizontalString() {
        return num1Str+" "+op.getCode()+num2Str+" =    ";
    }

    /**
     * 竖式打印
     *
     * @return
     */
    @Override
    public String verticalString() {
        return "  "+num1Str+"\r\n "+op.getCode()+num2Str+"\r\n————\r\n";
    }


}
