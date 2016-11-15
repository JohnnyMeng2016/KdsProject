package com.johnny.kdsclient.bean;

import java.util.List;

/**
 * 项目名称：NewKdsClient
 * 类描述：发帖草稿
 * 创建人：孟忠明
 * 创建时间：2016/11/15
 */
public class DraftTopic {
    private int id;
    private String title;
    private String content;
    private String createTime;
    private String modifyTime;
    private List<String> imgUris;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public List<String> getImgUris() {
        return imgUris;
    }

    public void setImgUris(List<String> imgUris) {
        this.imgUris = imgUris;
    }
}
