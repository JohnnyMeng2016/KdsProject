package com.johnny.kdsclient.bean;

import java.util.List;

/**
 * 项目名称：NewKdsClient
 * 类描述：帖子搜索返回结果
 * 创建人：孟忠明
 * 创建时间：2016/11/22
 */
public class SearchTopicResponse {
    private int totalPage;
    private String errMessage;
    private List<Topic> data;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public List<Topic> getData() {
        return data;
    }

    public void setData(List<Topic> data) {
        this.data = data;
    }
}
