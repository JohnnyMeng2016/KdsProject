package com.johnny.kdsclient.bean;

import java.io.Serializable;

/**
 * 项目名称：KdsClient
 * 类描述：帖子
 * 创建人：孟忠明
 * 创建时间：2016/10/13
 */

public class Topic implements Serializable {
    private String bbsId;
    private String areaId;
    private String title;
    private String userId;
    private String nickName;
    private String replyNickName;
    private String createTime;
    private String postTime;
    private String replyTime;
    private String postTS;
    private String replyTS;
    private int view;
    private int reply;
    private String currentUserId;
    private String userLog;
    private String description;
    private String preview;
    private String location;

    public String getBbsId() {
        return bbsId;
    }

    public void setBbsId(String bbsId) {
        this.bbsId = bbsId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getReplyNickName() {
        return replyNickName;
    }

    public void setReplyNickName(String replyNickName) {
        this.replyNickName = replyNickName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(String replyTime) {
        this.replyTime = replyTime;
    }

    public String getPostTS() {
        return postTS;
    }

    public void setPostTS(String postTS) {
        this.postTS = postTS;
    }

    public String getReplyTS() {
        return replyTS;
    }

    public void setReplyTS(String replyTS) {
        this.replyTS = replyTS;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public int getReply() {
        return reply;
    }

    public void setReply(int reply) {
        this.reply = reply;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public String getUserLog() {
        return userLog;
    }

    public void setUserLog(String userLog) {
        this.userLog = userLog;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
