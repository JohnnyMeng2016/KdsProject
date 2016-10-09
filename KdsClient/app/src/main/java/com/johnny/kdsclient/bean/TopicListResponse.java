package com.johnny.kdsclient.bean;

import java.util.List;

/**
 * 项目名称：KdsClient
 * 类描述：帖子列表Response
 * 创建人：孟忠明
 * 创建时间：2016/10/9
 */
public class TopicListResponse {
    private int statusCode;
    private String message;
    private List<Topic> topicList;

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

    public List<Topic> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<Topic> topicList) {
        this.topicList = topicList;
    }
}
