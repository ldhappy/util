package com.ld.util.excel.example.math;

/**
 * @ClassName AbstractOperation
 * @Description 算数抽象
 * @Author 梁聃
 * @Date 2021/9/23 17:17
 */
public abstract class AbstractOperation {
    //生成题目限制
    protected OperationConfig operationConfig;

    public AbstractOperation(OperationConfig operationConfig) {
        this.operationConfig = operationConfig;
    }

    /**
     * 根据限制生成随机数
     * @return
     */
    protected Integer randomNum() {
        return operationConfig.getMinNum() + Math.max(OperationConfig.RANDOM.nextInt(operationConfig.getMaxNum()),1);
    }

    /**
     * 操作数前方补空格
     * @param length
     * @return
     */
    protected String space(int length) {
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i<length;i++){
            sb.append(" ");
        }
        return sb.toString();
    }

    /**
     * 每个数字中间加上空格
     * @param num
     * @return
     */
    protected String StringNum(Integer num) {
        String numStr = num.toString();
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i<numStr.length();i++){
            sb.append(" ");
            sb.append(numStr.charAt(i));
        }
        return sb.toString();
    }

    /**
     * 横式打印
     * @return
     */
    public abstract String horizontalString();

    /**
     * 竖式打印
     * @return
     */
    public abstract String verticalString();
}
