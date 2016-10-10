package com.johnny.kdsclient.bean;

import java.util.List;

/**
 * 项目名称：KdsClient
 * 类描述：回复列表Response
 * 创建人：孟忠明
 * 创建时间：2016/10/10
 */
public class ReplyListResponse {
    private int statusCode;
    private String message;
    private List<Reply> replyList;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Reply> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<Reply> replyList) {
        this.replyList = replyList;
    }
}
