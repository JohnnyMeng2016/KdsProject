package com.johnny.kdsclient.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.johnny.kdsclient.BaseActivity;
import com.johnny.kdsclient.R;
import com.johnny.kdsclient.SettingShared;
import com.johnny.kdsclient.UserData;
import com.johnny.kdsclient.api.ApiHelper;
import com.johnny.kdsclient.api.SimpleResponseListener;
import com.johnny.kdsclient.bean.LoginResponse;
import com.johnny.kdsclient.utils.StringUtils;
import com.johnny.kdsclient.utils.ThemeUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项目名称：KdsClient
 * 类描述：登录界面
 * 创建人：孟忠明
 * 创建时间：2016/10/24
 */
public class LoginActivity extends BaseActivity {
    @BindView(R.id.id_toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_username)
    EditText etUserName;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.cb_autoLogin)
    CheckBox cbAutoLogin;
    @BindView(R.id.bt_login)
    Button btLogin;

    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;

    @Override
    protected void configTheme() {
        ThemeUtils.configThemeBeforeOnCreate(this, R.style.BaseAppTheme_NoActionBar, R.style.BaseAppThemeDark_NoActionBar);
    }

    @Override
    protected int layout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initDate() {
        toolbar.setTitle(getString(R.string.welcome));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initView() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("登录中...");

        sharedPreferences = this.getSharedPreferences("setting", this.MODE_PRIVATE);
    }

    @OnClick(R.id.bt_login)
    protected void onBtnLoginClick(final View view) {
        final String userName = etUserName.getText().toString();
        final String password = etPassword.getText().toString();
        if (null != userName && !"".equals(userName) && null != password && !"".equals(password)) {
            progressDialog.show();

            ApiHelper.getInstance().login(userName, password, new SimpleResponseListener<LoginResponse>() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Snackbar.make(view, getString(R.string.username_and_password_error), Snackbar.LENGTH_LONG).show();
                }

                @Override
                public void onResponse(LoginResponse response) {
                    progressDialog.dismiss();
                    if (response.getStatus().equals("Succes")) {
                        SettingShared.saveUserName(LoginActivity.this, userName);
                        SettingShared.savePassword(LoginActivity.this, password);
                        SettingShared.setEnableAutoLogin(LoginActivity.this, cbAutoLogin.isChecked());

                        Toast.makeText(LoginActivity.this, getString(R.string.login_success), Toast.LENGTH_SHORT).show();
                        UserData.getInstance().setUserInfo(response.getUserInfo());
                        finish();
                    } else {
                        Snackbar.make(view, getString(R.string.username_and_password_error), Snackbar.LENGTH_LONG).show();
                    }
                }
            });
        } else {
            Snackbar.make(view, getString(R.string.username_and_password_input), Snackbar.LENGTH_LONG).show();
        }
    }
}
