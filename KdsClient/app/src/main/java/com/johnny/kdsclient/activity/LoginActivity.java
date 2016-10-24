package com.johnny.kdsclient.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.johnny.kdsclient.BaseActivity;
import com.johnny.kdsclient.R;

import butterknife.BindView;

/**
 * 项目名称：KdsClient
 * 类描述：
 * 创建人：孟忠明
 * 创建时间：2016/10/24
 */
public class LoginActivity extends BaseActivity{
    @BindView(R.id.et_username)
    EditText etUserName;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.bt_login)
    Button btLogin;

    @Override
    protected int layout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void initView() {
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
