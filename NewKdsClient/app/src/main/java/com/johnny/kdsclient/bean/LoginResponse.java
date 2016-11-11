package com.johnny.kdsclient.bean;

/**
 * 项目名称：KdsClient
 * 类描述：登录返回
 * 创建人：孟忠明
 * 创建时间：2016/10/28
 */
public class LoginResponse {

    private String status;//返回标识
    private UserInfo userInfo;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
