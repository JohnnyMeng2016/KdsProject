package com.johnny.kdsclient.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.johnny.kdsclient.R;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 项目名称：KdsClient
 * 类描述：为列表界面提供下拉加载布局
 * 创建人：孟忠明
 * 创建时间：2016/10/9
 */
public class FooterViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.id_circleProgressBar)
    CircleProgressBar progressBar;
    @BindView(R.id.id_textview)
    TextView textView;

    public FooterViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
