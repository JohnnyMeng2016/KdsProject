package com.johnny.kdsclient.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.mancj.materialsearchbar.MaterialSearchBar;

/**
 * 项目名称：NewKdsClient
 * 类描述：查询条
 * 创建人：孟忠明
 * 创建时间：2016/11/22
 */
public class KdsSearchBar extends MaterialSearchBar {
    private OnItemDeleteListener onItemDeleteListener;

    public KdsSearchBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnItemDeleteListener(OnItemDeleteListener onItemDeleteListener) {
        this.onItemDeleteListener = onItemDeleteListener;
    }

    @Override
    public void OnItemDeleteListener(int position, View v) {
        super.OnItemDeleteListener(position, v);
        if (null != onItemDeleteListener) {
            onItemDeleteListener.onItemDelete(position);
        }
    }

    public interface OnItemDeleteListener {
        void onItemDelete(int position);
    }
}


