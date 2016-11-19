package com.johnny.kdsclient.bean;


/**
 * 项目名称：KdsClient
 * 类描述：通用返回
 * 创建人：孟忠明
 * 创建时间：2016/10/13
 */
public class CommonResponse {
    private int flag;
    private String errMessage;
    private String message;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
