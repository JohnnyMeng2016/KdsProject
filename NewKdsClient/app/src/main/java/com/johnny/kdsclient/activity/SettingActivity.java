package com.johnny.kdsclient.activity;

import android.support.v7.widget.SwitchCompat;
import android.view.View;

import com.johnny.kdsclient.BaseActivity;
import com.johnny.kdsclient.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项目名称：NewKdsClient
 * 类描述：设置界面
 * 创建人：孟忠明
 * 创建时间：2016/11/25
 */
public class SettingActivity extends BaseActivity {
    @BindView(R.id.switch_night_mode)
    SwitchCompat switchCompat;

    @Override
    protected int layout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initDate() {
    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.ll_night_mode, R.id.switch_theme})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_night_mode:
                switchCompat.toggle();
                break;
            case R.id.switch_theme:
                break;
        }
    }
}
