package com.test.demo.utils;


/**
 * 
* @ClassName: BaseAO 
* @Description: 应用对象，在web层与service层之间抽象的复用对象模型，
* 极为贴近展示层，复用度不高
* @author xiaotian.zhang 
* @date 2017年11月8日 下午3:14:15 
*
 */
public class BaseAO<T> {
    public static int BASE_RESULT_STATUS_ERROR = 0;
    public static int BASE_RESULT_STATUS_SUCCESS = 1;

    private String msg;
    private int result;
    private long systemTime;
    private T info;

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public long getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(long systemTime) {
        this.systemTime = systemTime;
    }

}
