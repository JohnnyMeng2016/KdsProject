package com.johnny.kdsclient.activity;

import android.view.View;
import android.webkit.WebView;
import android.support.v7.widget.Toolbar;

import com.johnny.kdsclient.BaseActivity;
import com.johnny.kdsclient.R;
import com.johnny.kdsclient.utils.ThemeUtils;

import butterknife.BindView;

/**
 * 项目名称：NewKdsClient
 * 类描述：关于界面
 * 创建人：孟忠明
 * 创建时间：2016/11/25
 */
public class AboutActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected void configTheme() {
        ThemeUtils.configThemeBeforeOnCreate(this, R.style.BaseAppTheme_NoActionBar, R.style.BaseAppThemeDark_NoActionBar);
    }

    @Override
    protected int layout() {
        return R.layout.activity_about;
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        webView.loadUrl("file:android_asset/README.html");
    }
}
