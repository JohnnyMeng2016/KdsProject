package com.johnny.kdsclient.bean;

import java.io.Serializable;

/**
 * 项目名称：NewKdsClient
 * 类描述：回复中的引用
 * 创建人：孟忠明
 * 创建时间：2016/11/9
 */
public class Quote  implements Serializable {
    private String messageId;
    private String message;
    private String userName;
    private String nickName;
    private String postTime;
    private int floor;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }
}
