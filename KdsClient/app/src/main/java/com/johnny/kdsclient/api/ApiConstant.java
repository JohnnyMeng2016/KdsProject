package com.johnny.kdsclient.api;

/**
 * 项目名称：KdsClient
 * 类描述：
 * 创建人：孟忠明
 * 创建时间：2016/10/8
 */
public class ApiConstant {

    private static final String API_HOST = "http://10.141.10.144:8000/";

    public static final String GET_TOPIC_LIST = API_HOST + "getTopicList";

    public static final String GET_REPLY_LIST = API_HOST + "getTopicDetail";

    private ApiConstant() {

    }
}
