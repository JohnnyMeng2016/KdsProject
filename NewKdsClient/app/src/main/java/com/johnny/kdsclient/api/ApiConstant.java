package com.johnny.kdsclient.api;

/**
 * 项目名称：KdsClient
 * 类描述：
 * 创建人：孟忠明
 * 创建时间：2016/10/8
 */
public class ApiConstant {

    private static final String API_HOST = "http://app2.api.kdslife.com";

    //获取个人信息
    public static final String GET_USER_INFO = API_HOST + "/index.php/profile/";

    //用户登录
    public static final String LOGIN = API_HOST + "/index.php/login";

    //用户关注列表
    public static final String GET_FRIEND_LIST = API_HOST + "/index.php/friend";

    //常规帖子列表
    public static final String GET_TOPIC_LIST_NORMAL = API_HOST + "/index.php/forum/";

    //本日热帖
    public static final String GET_TOPIC_LIST_DAILY = API_HOST + "/index.php/forum/hotDailyForum";

    //本周热帖
    public static final String GET_TOPIC_LIST_WEEK = API_HOST + "/index.php/forum/hotforum/";

    //本月热帖
    public static final String GET_TOPIC_LIST_MONTH = API_HOST + "/index.php/forum/hotMonthlyForum";

    //获取帖子回复列表
    public static final String GET_REPLY_LIST = API_HOST + "/index.php/thread/";

    //获取用户帖子列表
    public static final String GET_USER_TOPIC_LIST = API_HOST + "/index.php/mysend/index";

    //回复帖子
    public static final String REPLY_TOPIC = API_HOST + "/index.php/postreply/replyPosts_android";

    //发帖
    public static final String SEND_TOPIC = API_HOST + "/index.php/sendtopic/";

    //上传图片
    public static final String UPLOAD_PICTURE = API_HOST + "/index.php/recommend/kdsUploadPicture";

    //搜索帖子
    public static final String SEARCH_TOPIC = API_HOST + "/index.php/search";

    private ApiConstant() {

    }
}
