package com.johnny.kdsclient.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.johnny.kdsclient.bean.LoginResponse;
import com.johnny.kdsclient.bean.ReplyListResponse;
import com.johnny.kdsclient.bean.TopicListResponse;
import com.johnny.kdsclient.bean.UserDetailResponse;

import java.util.HashMap;

/**
 * 项目名称：KdsClient
 * 类描述：API操作类
 * 创建人：孟忠明
 * 创建时间：2016/10/8
 */
public class ApiHelper {

    private static final String TAG = ApiHelper.class.getSimpleName();

    private static ApiHelper apiHelper;

    private RequestQueue queue;

    private Gson gson;

    private ApiHelper() {
    }

    public static ApiHelper getInstance() {
        if (apiHelper == null) {
            apiHelper = new ApiHelper();
        }
        return apiHelper;
    }

    public void init(Context context) {
        queue = Volley.newRequestQueue(context);
        gson = new Gson();
    }

    public RequestQueue getHttpQueue() {
        return queue;
    }

    /**
     * 获取帖子列表
     *
     * @param page     给定页码
     * @param listener
     */
    public void getTopicList(int page, final SimpleResponseListener<TopicListResponse> listener) {
        HashMap<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("page", String.valueOf(page));
        Request request = new SimplePostRequest(ApiConstant.GET_TOPIC_LIST, paramMap, new SimpleResponseListener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG,response);
                try {
                    TopicListResponse topicListResponse = gson.fromJson(response, TopicListResponse.class);
                    listener.onResponse(topicListResponse);
                } catch (Exception e) {
                    VolleyError volleyError = new VolleyError();
                    volleyError.setStackTrace(e.getStackTrace());
                    listener.onErrorResponse(volleyError);
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onErrorResponse(error);
            }
        });
        getHttpQueue().add(request);
        Log.d(TAG,request.toString());
        Log.d(TAG,paramMap.toString());
    }

    /**
     * 获取帖子回复列表
     *
     * @param topicUrl
     * @param page
     * @param pageCount
     * @param replyNum
     * @param listener
     */
    public void getReplyList(String topicUrl, int page, int pageCount, int replyNum,
                             final SimpleResponseListener<ReplyListResponse> listener) {
        HashMap<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("topicUrl", topicUrl);
        paramMap.put("page", String.valueOf(page));
        paramMap.put("pageCount", String.valueOf(pageCount));
        paramMap.put("replyNum", String.valueOf(replyNum));
        Request request = new SimplePostRequest(ApiConstant.GET_REPLY_LIST, paramMap, new SimpleResponseListener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG,response);
                try {
                    ReplyListResponse replyListResponse = gson.fromJson(response, ReplyListResponse.class);
                    listener.onResponse(replyListResponse);
                } catch (Exception e) {
                    VolleyError volleyError = new VolleyError();
                    volleyError.setStackTrace(e.getStackTrace());
                    listener.onErrorResponse(volleyError);
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onErrorResponse(error);
            }
        });
        getHttpQueue().add(request);
        Log.d(TAG,request.toString());
        Log.d(TAG,paramMap.toString());
    }

    /**
     * 获取用户数据
     *
     * @param userUrl
     * @param listener
     */
    public void getUserDetail(String userUrl, final SimpleResponseListener listener) {
        HashMap<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("userUrl", userUrl);
        Request request = new SimplePostRequest(ApiConstant.GET_USER_DETAIL, paramMap, new SimpleResponseListener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG,response);
                try {
                    UserDetailResponse userDetailResponse = gson.fromJson(response, UserDetailResponse.class);
                    listener.onResponse(userDetailResponse);
                } catch (Exception e) {
                    VolleyError volleyError = new VolleyError();
                    volleyError.setStackTrace(e.getStackTrace());
                    listener.onErrorResponse(volleyError);
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onErrorResponse(error);
            }
        });
        getHttpQueue().add(request);
        Log.d(TAG,request.toString());
        Log.d(TAG,paramMap.toString());
    }

    /**
     * 用户登录
     *
     * @param userName
     * @param password
     * @param listener
     */
    public void login(String userName, String password, final SimpleResponseListener<LoginResponse> listener) {
        HashMap<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("username", userName);
        paramMap.put("password", password);
        Request request = new SimplePostRequest(ApiConstant.LOGIN, paramMap, new SimpleResponseListener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG,response);
                try {
                    LoginResponse loginResponse = gson.fromJson(response, LoginResponse.class);
                    listener.onResponse(loginResponse);
                } catch (Exception e) {
                    VolleyError volleyError = new VolleyError();
                    volleyError.setStackTrace(e.getStackTrace());
                    listener.onErrorResponse(volleyError);
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onErrorResponse(error);
            }
        });
        getHttpQueue().add(request);
        Log.d(TAG,request.toString());
        Log.d(TAG,paramMap.toString());
    }

}
