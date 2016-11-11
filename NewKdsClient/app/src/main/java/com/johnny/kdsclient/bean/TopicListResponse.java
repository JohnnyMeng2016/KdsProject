package com.johnny.kdsclient.bean;

import java.util.List;

/**
 * 项目名称：KdsClient
 * 类描述：帖子列表Response
 * 创建人：孟忠明
 * 创建时间：2016/10/9
 */
public class TopicListResponse {
    private String caption;
    private int countPage;
    private String imageServer;
    private String kdsTime;
    private String kdsVersion;
    private String errMessage;
    private List<Topic> data;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getCountPage() {
        return countPage;
    }

    public void setCountPage(int countPage) {
        this.countPage = countPage;
    }

    public String getImageServer() {
        return imageServer;
    }

    public void setImageServer(String imageServer) {
        this.imageServer = imageServer;
    }

    public String getKdsTime() {
        return kdsTime;
    }

    public void setKdsTime(String kdsTime) {
        this.kdsTime = kdsTime;
    }

    public String getKdsVersion() {
        return kdsVersion;
    }

    public void setKdsVersion(String kdsVersion) {
        this.kdsVersion = kdsVersion;
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
