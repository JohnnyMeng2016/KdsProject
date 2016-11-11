package com.johnny.kdsclient.bean;

import java.util.List;

/**
 * 项目名称：NewKdsClient
 * 类描述：用户帖子列表响应
 * 创建人：孟忠明
 * 创建时间：2016/11/10
 */
public class UserTopicResponse {
    private List<UserTopic> thread;

    public List<UserTopic> getThread() {
        return thread;
    }

    public void setThread(List<UserTopic> thread) {
        this.thread = thread;
    }
}
