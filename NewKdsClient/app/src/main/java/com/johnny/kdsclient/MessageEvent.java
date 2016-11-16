package com.johnny.kdsclient;

import com.johnny.kdsclient.bean.TopicListTypeEnum;

/**
 * 项目名称：KdsClient
 * 类描述：
 * 创建人：孟忠明
 * 创建时间：2016/10/19
 */
public class MessageEvent {
    private TopicListTypeEnum typeEnum;
    private int needRefreshPage;

    public TopicListTypeEnum getTypeEnum() {
        return typeEnum;
    }

    public void setTypeEnum(TopicListTypeEnum typeEnum) {
        this.typeEnum = typeEnum;
    }

    public int getNeedRefreshPage() {
        return needRefreshPage;
    }

    public void setNeedRefreshPage(int needRefreshPage) {
        this.needRefreshPage = needRefreshPage;
    }
}
