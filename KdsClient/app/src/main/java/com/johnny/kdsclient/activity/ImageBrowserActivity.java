package com.johnny.kdsclient.activity;

import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.johnny.kdsclient.BaseActivity;
import com.johnny.kdsclient.R;

import butterknife.BindView;

/**
 * 项目名称：KdsClient
 * 类描述：
 * 创建人：孟忠明
 * 创建时间：2016/10/14
 */
public class ImageBrowserActivity extends BaseActivity {
    @BindView(R.id.vp_image_brower)
    ViewPager imageViewPager;
    @BindView(R.id.tv_image_index)
    TextView tvImageIndex;

    @Override
    protected int layout() {
        return R.layout.activity_image_brower;
    }

    @Override
    protected void initDate() {

    }

    @Override
    protected void initView() {

    }
}
