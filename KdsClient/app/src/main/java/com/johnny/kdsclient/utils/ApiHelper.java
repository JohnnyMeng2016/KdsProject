package com.johnny.kdsclient.utils;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.johnny.kdsclient.model.Topic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名称：KdsClient
 * 类描述：
 * 创建人：孟忠明
 * 创建时间：2016/10/8
 */
public class ApiHelper {

    private static ApiHelper apiHelper;

    private RequestQueue queue;

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
    }

    public RequestQueue getHttpQueue() {
        return queue;
    }

    public void getTopicList(int page, SimpleResponseListener listener) {
        HashMap<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("page", String.valueOf(page));
        Request request = new SimplePostRequest(ApiConstant.GET_TOPIC_LIST, paramMap, new SimpleResponseListener(){
            @Override
            public void onResponse(String response) {
                System.out.println(response);
            }
        });
        getHttpQueue().add(request);
    }

}
