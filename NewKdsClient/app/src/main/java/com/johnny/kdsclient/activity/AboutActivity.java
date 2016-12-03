package com.johnny.kdsclient.activity;

import android.webkit.WebView;

import com.johnny.kdsclient.BaseActivity;
import com.johnny.kdsclient.R;

import butterknife.BindView;

/**
 * 项目名称：NewKdsClient
 * 类描述：关于界面
 * 创建人：孟忠明
 * 创建时间：2016/11/25
 */
public class AboutActivity extends BaseActivity {
    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected int layout() {
        return R.layout.activity_about;
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void initView() {
        webView.loadUrl(" file:///android_asset/README.html ");
    }
}
