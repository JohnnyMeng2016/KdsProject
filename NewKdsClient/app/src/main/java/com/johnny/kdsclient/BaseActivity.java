package com.johnny.kdsclient;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import com.github.anzewei.parallaxbacklayout.ParallaxActivityBase;

import butterknife.ButterKnife;

/**
 * Created by Johnny on 2016/10/3.
 */

public abstract class BaseActivity extends ParallaxActivityBase {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout());
        ButterKnife.bind(this);
        initDate();
        initView();
    }

    /**
     * setContentView()
     *
     * @return
     */
    protected abstract int layout();

    /**
     * after setContextView() and before initView in onCreate()
     */
    protected abstract void initDate();

    /**
     * after initDate() in onCreate()
     */
    protected abstract void initView();
}
