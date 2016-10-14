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
import com.johnny.kdsclient.activity.TopicDetailActivity;
import com.johnny.kdsclient.bean.Topic;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Johnny on 2016/10/5.
 */

public class TopicRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private Context context;
    private List<Topic> datas;
    private LayoutInflater layoutInflater;
    private FooterViewHolder footerViewHolder;

    public TopicRecycleAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.datas = new ArrayList<>();
    }

    public List<Topic> getDatas() {
        return datas;
    }

    public void setDatas(List<Topic> datas) {
        this.datas = datas;
    }

    public void setFooterViewType(boolean isEnd){
        if(isEnd){
            footerViewHolder.progressBar.setVisibility(View.GONE);
            footerViewHolder.textView.setVisibility(View.VISIBLE);
        }else{
            footerViewHolder.progressBar.setVisibility(View.VISIBLE);
            footerViewHolder.textView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            View view = layoutInflater.inflate(R.layout.item_foot_loading, parent, false);
            footerViewHolder = new FooterViewHolder(view);
            return footerViewHolder;
        } else {
            View view = layoutInflater.inflate(R.layout.item_topic, parent, false);
            TopicRecycleHolder topicRecycleHolder = new TopicRecycleHolder(view);
            return topicRecycleHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TopicRecycleHolder) {
            TopicRecycleHolder topicRecycleHolder = (TopicRecycleHolder) holder;
            final Topic topic = datas.get(position);
            if(!"None".equals(topic.getImgPreview())){
                Glide.with(context).load(topic.getImgPreview()).into(topicRecycleHolder.ivPic);
            }else{
                topicRecycleHolder.ivPic.setImageResource(R.mipmap.no_image);
            }
            topicRecycleHolder.tvTitle.setText(topic.getTitle());
            topicRecycleHolder.tvDatetime.setText(topic.getTopicTime());
            topicRecycleHolder.tvClickNum.setText(String.valueOf(topic.getClickNum()));
            topicRecycleHolder.tvReplyNum.setText(String.valueOf(topic.getReplyNum()));
            topicRecycleHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("topic",topic);
                    intent.setClass(context, TopicDetailActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return datas.size() + 1;
    }

    public class TopicRecycleHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.id_cardview)
        CardView cardView;
        @BindView(R.id.iv_pic)
        ImageView ivPic;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_datetime)
        TextView tvDatetime;
        @BindView(R.id.tv_click_num)
        TextView tvClickNum;
        @BindView(R.id.tv_reply_num)
        TextView tvReplyNum;

        public TopicRecycleHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
