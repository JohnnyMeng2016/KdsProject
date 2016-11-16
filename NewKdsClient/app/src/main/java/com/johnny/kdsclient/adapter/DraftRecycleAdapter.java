package com.johnny.kdsclient.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.johnny.kdsclient.R;
import com.johnny.kdsclient.activity.WriteTopicActivity;
import com.johnny.kdsclient.bean.DraftTopic;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 项目名称：NewKdsClient
 * 类描述：草稿列表
 * 创建人：孟忠明
 * 创建时间：2016/11/15
 */
public class DraftRecycleAdapter extends RecyclerView.Adapter<DraftRecycleAdapter.DraftRecycleHolder> {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<DraftTopic> datas;

    public DraftRecycleAdapter(Context context, List<DraftTopic> datas) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.datas = datas;
    }

    @Override
    public DraftRecycleAdapter.DraftRecycleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_draft, parent, false);
        DraftRecycleAdapter.DraftRecycleHolder replyRecycleHolder = new DraftRecycleAdapter.DraftRecycleHolder(view);
        return replyRecycleHolder;
    }

    @Override
    public void onBindViewHolder(DraftRecycleAdapter.DraftRecycleHolder holder, int position) {
        final DraftTopic draftTopic = datas.get(position);
        if (null != draftTopic.getImgUris() && draftTopic.getImgUris().size() > 0) {
            holder.imageView.setVisibility(View.VISIBLE);
            Glide.with(context).load(draftTopic.getImgUris().get(0)).into(holder.imageView);
        } else {
            holder.imageView.setVisibility(View.GONE);
        }
        holder.tvTitle.setText(draftTopic.getTitle());
        holder.tvContent.setText(draftTopic.getContent());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WriteTopicActivity.class);
                intent.putExtra("Draft", draftTopic);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class DraftRecycleHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.id_cardview)
        CardView cardView;
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