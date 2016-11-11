package com.johnny.kdsclient.bean;

import java.util.List;

/**
 * 项目名称：KdsClient
 * 类描述：回复内容解析容器
 * 创建人：孟忠明
 * 创建时间：2016/10/13
 */
public class ContentParsedBean {

    private String refrence;
    private String content;
    private List<String> refrenceImgs;
    private List<String> contentImgs;

    public String getRefrence() {
        return refrence;
    }

    public void setRefrence(String refrence) {
        this.refrence = refrence;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getRefrenceImgs() {
        return refrenceImgs;
    }

    public void setRefrenceImgs(List<String> refrenceImgs) {
        this.refrenceImgs = refrenceImgs;
    }

    public List<String> getContentImgs() {
        return contentImgs;
    }

    public void setContentImgs(List<String> contentImgs) {
        this.contentImgs = contentImgs;
    }
}
