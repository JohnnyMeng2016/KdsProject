package com.johnny.kdsclient.api;


import android.util.Base64;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.johnny.kdsclient.BuildConfig;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 项目名称：KdsClient
 * 类描述：简单请求类
 * 创建人：孟忠明
 * 创建时间：2016/10/8
 */
public class SimpleRequest extends Request<String> {
    private SimpleResponseListener listener;
    private HashMap<String, String> paramMap;

    public SimpleRequest(String url, SimpleResponseListener listener) {
        super(Method.GET, url, listener);
        this.listener = listener;
    }

    public SimpleRequest(String url, HashMap<String, String> paramMap, SimpleResponseListener listener) {
        super(Method.POST, url, listener);
        this.paramMap = paramMap;
        this.listener = listener;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(String response) {
        if (listener != null) {
            if (BuildConfig.ENCRYPT) {
                byte[] asBytes = Base64.decode(response, Base64.DEFAULT);
                try {
                    response = new String(asBytes, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    listener.onErrorResponse(new VolleyError("转码错误"));
                }
            }
            listener.onResponse(response);
        }
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return paramMap;
    }
}
