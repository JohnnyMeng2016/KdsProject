package com.johnny.kdsclient.activity;

import android.content.Intent;
import android.os.Handler;

import com.android.volley.VolleyError;
import com.johnny.kdsclient.BaseActivity;
import com.johnny.kdsclient.MainActivity;
import com.johnny.kdsclient.R;
import com.johnny.kdsclient.SettingShared;
import com.johnny.kdsclient.UserData;
import com.johnny.kdsclient.api.ApiHelper;
import com.johnny.kdsclient.api.SimpleResponseListener;
import com.johnny.kdsclient.bean.LoginResponse;

/**
 * 项目名称：NewKdsClient
 * 类描述：
 * 创建人：孟忠明
 * 创建时间：2016/12/1
 */
public class WelcomeActivity extends BaseActivity {
    @Override
    protected int layout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initDate() {
        boolean isAutoLogin = SettingShared.isEnableAutoLogin(this);
        String userName = SettingShared.getLoginName(this);
        String password = SettingShared.getLoginPassword(this);
        if (isAutoLogin) {
            ApiHelper.getInstance().login(userName, password, new SimpleResponseListener<LoginResponse>() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }

                @Override
                public void onResponse(LoginResponse response) {
                    if (response.getStatus().equals("Succes")) {
                        UserData.getInstance().setUserInfo(response.getUserInfo());
                    }
                }
            });
        }


        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                , 2000);
    }

    @Override
    protected void initView() {

    }

}
