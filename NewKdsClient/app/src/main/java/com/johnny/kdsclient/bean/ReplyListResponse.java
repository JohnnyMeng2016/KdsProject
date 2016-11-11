package com.johnny.kdsclient.bean;

import java.util.List;

/**
 * 项目名称：KdsClient
 * 类描述：回复列表Response
 * 创建人：孟忠明
 * 创建时间：2016/10/10
 */
public class ReplyListResponse {
    private int ol;
    private int pageSize;
    private int totalPage;
    private int nowPage;
    private int firstPage;
    private int lastPage;
    private int replyNum;
    private int prevPage;
    private int nextPage;
    private String topFloorUserId;
    private String areaId;
    private String bbsId;
    private String topicId;
    private String title;
    private String postTime;
    private String nickName;
    private int hitNum;
    private int favouriteNum;
    private List<Reply> replys;

    public int getOl() {
        return ol;
    }

    public void setOl(int ol) {
        this.ol = ol;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getNowPage() {
        return nowPage;
    }

    public void setNowPage(int nowPage) {
        this.nowPage = nowPage;
    }

    public int getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(int firstPage) {
        this.firstPage = firstPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public int getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(int replyNum) {
        this.replyNum = replyNum;
    }

    public int getPrevPage() {
        return prevPage;
    }

    public void setPrevPage(int prevPage) {
        this.prevPage = prevPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public String getTopFloorUserId() {
        return topFloorUserId;
    }

    public void setTopFloorUserId(String topFloorUserId) {
        this.topFloorUserId = topFloorUserId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getBbsId() {
        return bbsId;
    }

    public void setBbsId(String bbsId) {
        this.bbsId = bbsId;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getHitNum() {
        return hitNum;
    }

    public void setHitNum(int hitNum) {
        this.hitNum = hitNum;
    }

    public int getFavouriteNum() {
        return favouriteNum;
    }

    public void setFavouriteNum(int favouriteNum) {
        this.favouriteNum = favouriteNum;
    }

    public List<Reply> getReplys() {
        return replys;
    }

    public void setReplys(List<Reply> replys) {
        this.replys = replys;
    }
}
