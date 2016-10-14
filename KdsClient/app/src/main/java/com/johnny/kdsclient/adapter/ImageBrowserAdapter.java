package com.johnny.kdsclient.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 项目名称：KdsClient
 * 类描述：
 * 创建人：孟忠明
 * 创建时间：2016/10/14
 */
public class ImageBrowserAdapter extends PagerAdapter {

    private Context context;
    private List<String> imgs;
    private LayoutInflater inflater;

    public ImageBrowserAdapter(Context context, List<String> imgs) {
        this.context = context;
        this.imgs = imgs;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return imgs.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

    }
}
