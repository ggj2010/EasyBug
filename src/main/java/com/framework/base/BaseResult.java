package com.framework.base;

/**
 * User: ½ª·¨ÓÐ
 * Date: 11-5-2
 * Time: ÏÂÎç8:41
 */
public class BaseResult {
    private int result;
    private String errorInfo;
    private Object data;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
    
    
}
