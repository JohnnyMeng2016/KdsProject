package com.johnny.kdsclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.johnny.kdsclient.R;
import com.johnny.kdsclient.bean.SendTopicRequest;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 项目名称：NewKdsClient
 * 类描述：草稿列表
 * 创建人：孟忠明
 * 创建时间：2016/11/15
 */
public class DraftRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<SendTopicRequest> datas;

    public DraftRecycleAdapter(Context context, List<SendTopicRequest> datas) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_draft, parent, false);
        DraftRecycleAdapter.DraftRecycleHolder replyRecycleHolder = new DraftRecycleAdapter.DraftRecycleHolder(view);
        return replyRecycleHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class DraftRecycleHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_pic)
        ImageView imageView;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_datetime)
        TextView tvDatetime;

        public DraftRecycleHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}