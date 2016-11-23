package com.johnny.kdsclient.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.johnny.kdsclient.R;
import com.johnny.kdsclient.activity.TopicDetailActivity;
import com.johnny.kdsclient.bean.Topic;
import com.johnny.kdsclient.bean.UserTopic;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 项目名称：KdsClient
 * 类描述：
 * 创建人：孟忠明
 * 创建时间：2016/10/27
 */
public class UserTopicRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private Context context;
    private List<UserTopic> datas;
    private LayoutInflater layoutInflater;
    private FooterViewHolder footerViewHolder;

    public UserTopicRecycleAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.datas = new ArrayList<>();
    }

    public List<UserTopic> getDatas() {
        return datas;
    }

    public void setDatas(List<UserTopic> datas) {
        this.datas = datas;
    }

    public void setFooterViewType(boolean isEnd) {
        if (isEnd) {
            footerViewHolder.progressBar.setVisibility(View.GONE);
            footerViewHolder.textView.setVisibility(View.VISIBLE);
        } else {
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
            UserTopicRecycleHolder topicRecycleHolder =
                    new UserTopicRecycleHolder(view);
            return topicRecycleHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UserTopicRecycleHolder) {
            UserTopicRecycleAdapter.UserTopicRecycleHolder topicRecycleHolder =
                    (UserTopicRecycleAdapter.UserTopicRecycleHolder) holder;
            final UserTopic userTopic = datas.get(position);
            if (null != userTopic.getAttachment() && !userTopic.getAttachment().contains("kdsLogo1.png")) {
                Glide.with(context).load(userTopic.getAttachment()).into(topicRecycleHolder.ivPic);
            } else {
                topicRecycleHolder.ivPic.setImageResource(R.mipmap.no_image);
            }
            topicRecycleHolder.tvTitle.setText(userTopic.getTitle());
            topicRecycleHolder.tvDatetime.setText(userTopic.getPostTime());
            topicRecycleHolder.tvClickNum.setText(String.valueOf(userTopic.getHitNum()));
            topicRecycleHolder.tvReplyNum.setText(String.valueOf(userTopic.getReplyNum()));
            topicRecycleHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    Topic topic = new Topic();
                    topic.setTitle(userTopic.getTitle());
                    topic.setBbsId(userTopic.getBbsId());
                    topic.setCreateTime(userTopic.getCreateTime());
                    topic.setPreview(userTopic.getAttachment());
                    topic.setNickName(userTopic.getPostNickName());
                    topic.setView(Integer.parseInt(userTopic.getHitNum()));
                    topic.setReply(Integer.parseInt(userTopic.getReplyNum()));
                    topic.setReply(Integer.parseInt(userTopic.getReplyNum()));
                    intent.putExtra("topic", topic);
                    intent.setClass(context, TopicDetailActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (null != datas) {
            return datas.size() + 1;
        } else {
            return 1;
        }
    }


    public class UserTopicRecycleHolder extends RecyclerView.ViewHolder {
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

        public UserTopicRecycleHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
