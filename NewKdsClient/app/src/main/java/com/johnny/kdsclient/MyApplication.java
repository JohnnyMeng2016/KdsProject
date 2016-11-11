package com.johnny.kdsclient;

import android.app.Application;

import com.johnny.kdsclient.api.ApiHelper;

/**
 * 项目名称：KdsClient
 * 类描述：容器
 * 创建人：孟忠明
 * 创建时间：2016/10/8
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        //初始化API
        ApiHelper.getInstance().init(getApplicationContext());

    }

}
