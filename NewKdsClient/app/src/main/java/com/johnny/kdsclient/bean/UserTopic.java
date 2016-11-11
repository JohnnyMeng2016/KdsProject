package com.johnny.kdsclient.bean;

import java.io.Serializable;

/**
 * 项目名称：NewKdsClient
 * 类描述：帖子（用户帖子，和首页帖子格式不一样）
 * 创建人：孟忠明
 * 创建时间：2016/11/9
 */
public class UserTopic implements Serializable {
    private String bbsId;
    private String areaId;
    private String topicId;
    private String caption;
    private String postUserId;
    private String postNickName;
    private String replyUserId;
    private String replyNickName;
    private String createTime;
    private String postTime;
    private String replyTime;
    private String orderByTag;
    private String editTime;
    private String hitNum;
    private String replyNum;
    private String scoreNum;
    private String ppNum;
    private String msgLength;
    private String favoriteNum;
    private String title;
    private String description;
    private String tag;
    private String attachmentFlags;
    private String attachment;

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

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getPostUserId() {
        return postUserId;
    }

    public void setPostUserId(String postUserId) {
        this.postUserId = postUserId;
    }

    public String getPostNickName() {
        return postNickName;
    }

    public void setPostNickName(String postNickName) {
        this.postNickName = postNickName;
    }

    public String getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(String replyUserId) {
        this.replyUserId = replyUserId;
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

    public String getOrderByTag() {
        return orderByTag;
    }

    public void setOrderByTag(String orderByTag) {
        this.orderByTag = orderByTag;
    }

    public String getEditTime() {
        return editTime;
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }

    public String getHitNum() {
        return hitNum;
    }

    public void setHitNum(String hitNum) {
        this.hitNum = hitNum;
    }

    public String getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(String replyNum) {
        this.replyNum = replyNum;
    }

    public String getScoreNum() {
        return scoreNum;
    }

    public void setScoreNum(String scoreNum) {
        this.scoreNum = scoreNum;
    }

    public String getPpNum() {
        return ppNum;
    }

    public void setPpNum(String ppNum) {
        this.ppNum = ppNum;
    }

    public String getMsgLength() {
        return msgLength;
    }

    public void setMsgLength(String msgLength) {
        this.msgLength = msgLength;
    }

    public String getFavoriteNum() {
        return favoriteNum;
    }

    public void setFavoriteNum(String favoriteNum) {
        this.favoriteNum = favoriteNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getAttachmentFlags() {
        return attachmentFlags;
    }

    public void setAttachmentFlags(String attachmentFlags) {
        this.attachmentFlags = attachmentFlags;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
}
