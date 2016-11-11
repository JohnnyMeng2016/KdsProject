package com.johnny.kdsclient;

import com.johnny.kdsclient.bean.UserInfo;

/**
 * 项目名称：KdsClient
 * 类描述：
 * 创建人：孟忠明
 * 创建时间：2016/10/28
 */
public class UserData {

    private static UserData userData;

    private String sid;
    private UserInfo userInfo;

    private UserData() {
    }

    public static UserData getInstance() {
        if (userData == null) {
            userData = new UserData();
        }
        return userData;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
