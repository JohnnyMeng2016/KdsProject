package com.johnny.kdsclient;

import android.app.Application;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.johnny.kdsclient.api.ApiHelper;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

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

        SpeechUtility.createUtility(MyApplication.this, SpeechConstant.APPID + "=584e62dd");

        UMShareAPI.get(this);
    }

    {
        PlatformConfig.setWeixin("wxe8df81660baab538", "904474ed99e7e2ef0fe66df07f6240d8");
        PlatformConfig.setSinaWeibo("1842962300", "3a6ace234d0570d0c7330c99f9352ea4");
        PlatformConfig.setQQZone("1105862742", "jrQlnH6TJk5nxa5M");
        Config.REDIRECT_URL = "http://sns.whalecloud.com/sina2/callback";
    }

}
