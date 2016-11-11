package com.johnny.kdsclient.bean;

import java.io.Serializable;

/**
 * 项目名称：KdsClient
 * 类描述：用户信息
 * 创建人：孟忠明
 * 创建时间：2016/10/27
 */
public class UserInfo  implements Serializable {

    private String userId;
    private String userName;
    private String nickName;
    private int hp;
    private int pp;
    private int pNum;
    private int rNum;
    private String sex;
    private String area;
    private String city;
    private String register;
    private String loginTime;
    private String loginIp;
    private String underWrite;
    private String email;
    private int fridendsNum;
    private int favouriteNum;
    private String mobile;
    private int userCertStat;
    private String img_pic_id;
    private String photo;
    private int listPrivateTopic;
    private int listPrivateReply;
    private String token;

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

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getPp() {
        return pp;
    }

    public void setPp(int pp) {
        this.pp = pp;
    }

    public int getpNum() {
        return pNum;
    }

    public void setpNum(int pNum) {
        this.pNum = pNum;
    }

    public int getrNum() {
        return rNum;
    }

    public void setrNum(int rNum) {
        this.rNum = rNum;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getUnderWrite() {
        return underWrite;
    }

    public void setUnderWrite(String underWrite) {
        this.underWrite = underWrite;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getFridendsNum() {
        return fridendsNum;
    }

    public void setFridendsNum(int fridendsNum) {
        this.fridendsNum = fridendsNum;
    }

    public int getFavouriteNum() {
        return favouriteNum;
    }

    public void setFavouriteNum(int favouriteNum) {
        this.favouriteNum = favouriteNum;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getUserCertStat() {
        return userCertStat;
    }

    public void setUserCertStat(int userCertStat) {
        this.userCertStat = userCertStat;
    }

    public String getImg_pic_id() {
        return img_pic_id;
    }

    public void setImg_pic_id(String img_pic_id) {
        this.img_pic_id = img_pic_id;
    }

    public int getListPrivateTopic() {
        return listPrivateTopic;
    }

    public void setListPrivateTopic(int listPrivateTopic) {
        this.listPrivateTopic = listPrivateTopic;
    }

    public int getListPrivateReply() {
        return listPrivateReply;
    }

    public void setListPrivateReply(int listPrivateReply) {
        this.listPrivateReply = listPrivateReply;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
