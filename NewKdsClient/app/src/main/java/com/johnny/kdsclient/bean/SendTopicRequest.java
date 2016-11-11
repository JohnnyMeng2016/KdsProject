package com.johnny.kdsclient.bean;

/**
 * 项目名称：NewKdsClient
 * 类描述：发帖请求
 * 创建人：孟忠明
 * 创建时间：2016/11/11
 */
public class SendTopicRequest {
    private String htmlcode;
    private String description;
    private String title;
    private String areaId;
    private String sortId;
    private String topicId;
    private String postUserId;

    public SendTopicRequest() {
        this.areaId = "1";
        this.sortId = "0";
        this.topicId = "15";
    }

    public String getHtmlcode() {
        return htmlcode;
    }

    public void setHtmlcode(String htmlcode) {
        this.htmlcode = htmlcode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getSortId() {
        return sortId;
    }

    public void setSortId(String sortId) {
        this.sortId = sortId;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getPostUserId() {
        return postUserId;
    }

    public void setPostUserId(String postUserId) {
        this.postUserId = postUserId;
    }
}
