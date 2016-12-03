package com.johnny.kdsclient.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.johnny.kdsclient.BaseActivity;
import com.johnny.kdsclient.R;
import com.johnny.kdsclient.SettingShared;
import com.johnny.kdsclient.UserData;
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
    SwitchCompat switchNightMode;
    @BindView(R.id.switch_auto_clear_cache)
    SwitchCompat switchAutoClearCache;
    @BindView(R.id.id_toolbar)
    Toolbar toolbar;

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
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        boolean isDarkTheme = SettingShared.isEnableDarkTheme(this);
        if (isDarkTheme) {
            switchNightMode.setChecked(true);
        }
    }

    @OnClick({R.id.btn_night_mode, R.id.btn_switch_theme, R.id.btn_auto_clear_cache, R.id.btn_clear_cache
            , R.id.btn_feedback, R.id.btn_rating, R.id.tv_logout})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_night_mode:
                switchNightMode.toggle();
                SettingShared.setEnableDarkTheme(this, switchNightMode.isChecked());
                ThemeUtils.notifyThemeApply(this, false);
                break;
            case R.id.btn_switch_theme:
                Toast.makeText(SettingActivity.this, "该功能还未做完", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_auto_clear_cache:
                switchAutoClearCache.toggle();
                break;
            case R.id.btn_clear_cache:
                Toast.makeText(SettingActivity.this, "该功能还未做完", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_feedback:
                Toast.makeText(SettingActivity.this, "该功能还未做完", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_rating:
                try {
                    Uri uri = Uri.parse("market://details?id="
                    );//需要评分的APP包名
                    Intent intent5 = new Intent(Intent.ACTION_VIEW, uri);
                    intent5.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent5);
                } catch (Exception e) {
                    Toast.makeText(SettingActivity.this, "该功能还未做完", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_logout:
                UserData.getInstance().setUserInfo(null);
                Toast.makeText(SettingActivity.this, "已成功退出账号", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
