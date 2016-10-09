package com.johnny.kdsclient.api;

import com.android.volley.Response;

/**
 * 项目名称：KdsClient
 * 类描述：简易协议返回事件
 * 创建人：孟忠明
 * 创建时间：2016/10/8
 */
public interface SimpleResponseListener<T> extends Response.ErrorListener,Response.Listener<T>{

}
