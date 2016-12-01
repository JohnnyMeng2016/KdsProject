package com.johnny.kdsclient.activity;

import android.support.v7.widget.SwitchCompat;
import android.view.View;

import com.johnny.kdsclient.BaseActivity;
import com.johnny.kdsclient.R;
import com.johnny.kdsclient.SettingShared;
import com.johnny.kdsclient.utils.ThemeUtils;

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
    protected void configTheme() {
        ThemeUtils.configThemeBeforeOnCreate(this, R.style.BaseAppTheme_NoActionBar, R.style.BaseAppThemeDark_NoActionBar);
    }

    @Override
    protected int layout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initDate() {
    }

    @Override
    protected void initView() {
        boolean isDarkTheme = SettingShared.isEnableDarkTheme(this);
        if (isDarkTheme) {
            switchCompat.setChecked(true);
        }
    }

    @OnClick({R.id.ll_night_mode, R.id.switch_theme})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_night_mode:
                switchCompat.toggle();
                SettingShared.setEnableDarkTheme(this, switchCompat.isChecked());
                ThemeUtils.notifyThemeApply(this, false);
                break;
            case R.id.switch_theme:
                break;
        }
    }
}
