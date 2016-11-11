package com.johnny.kdsclient.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.johnny.kdsclient.R;
import com.johnny.kdsclient.utils.EmotionUtils;

import java.util.List;

public class EmotionGvAdapter extends BaseAdapter {

    private Context context;
    private List<Integer> emotionCodes;
    private int itemWidth;

    public EmotionGvAdapter(Context context, List<Integer> emotionCodes, int itemWidth) {
        this.context = context;
        this.emotionCodes = emotionCodes;
        this.itemWidth = itemWidth;
    }

    @Override
    public int getCount() {
        return emotionCodes.size();
    }

    @Override
    public Integer getItem(int position) {
        return emotionCodes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView iv = new ImageView(context);
        LayoutParams params = new LayoutParams(itemWidth, itemWidth);
        iv.setPadding(itemWidth / 8, itemWidth / 8, itemWidth / 8, itemWidth / 8);
        iv.setLayoutParams(params);
        iv.setBackgroundResource(R.drawable.bg_tran2lgray_selector);
        int emotionCode = emotionCodes.get(position);
        iv.setImageResource(EmotionUtils.getImgByName(emotionCode));

        return iv;
    }

}
