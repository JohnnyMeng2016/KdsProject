package com.johnny.kdsclient.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 项目名称：KdsClient
 * 类描述：回复Bean
 * 创建人：孟忠明
 * 创建时间：2016/10/10
 */
public class Reply implements Serializable {
    private String msgId;
    private String bbsId;
    private String userId;
    private String userName;
    private String nickName;
    private String postTime;
    private int propFlag;
    private String messageShow;
    private String message;
    private String userSign;
    private int floor;
    private UserInfo Club;
    private UserInfo userdata;
    private List<Quote> quote_list;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getBbsId() {
        return bbsId;
    }

    public void setBbsId(String bbsId) {
        this.bbsId = bbsId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public int getPropFlag() {
        return propFlag;
    }

    public void setPropFlag(int propFlag) {
        this.propFlag = propFlag;
    }

    public String getMessageShow() {
        return messageShow;
    }

    public void setMessageShow(String messageShow) {
        this.messageShow = messageShow;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserSign() {
        return userSign;
    }

    public void setUserSign(String userSign) {
        this.userSign = userSign;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public List<Quote> getQuote_list() {
        return quote_list;
    }

    public void setQuote_list(List<Quote> quote_list) {
        this.quote_list = quote_list;
    }

    public UserInfo getClub() {
        return Club;
    }

    public void setClub(UserInfo club) {
        Club = club;
    }

    public UserInfo getUserdata() {
        userdata.setRegister(Club.getRegister());
        userdata.setUserCertStat(Club.getUserCertStat());
        return userdata;
    }

    public void setUserdata(UserInfo userdata) {
        this.userdata = userdata;
    }
}
