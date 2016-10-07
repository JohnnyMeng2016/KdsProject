package com.johnny.kdsclient;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by Johnny on 2016/10/3.
 */

public class BaseActivity extends AppCompatActivity {

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
     * @return
     */
    protected int layout(){return 0;}

    /**
     * after setContextView() and before initView in onCreate()
     */
    protected void initDate(){}

    /**
     * after initDate() in onCreate()
     */
    protected void initView(){}
}
