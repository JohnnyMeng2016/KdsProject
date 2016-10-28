package com.johnny.kdsclient.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.johnny.kdsclient.R;
import com.johnny.kdsclient.activity.TopicDetailActivity;
import com.johnny.kdsclient.bean.Topic;

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
    private List<Topic> datas;
    private LayoutInflater layoutInflater;
    private FooterViewHolder footerViewHolder;

    public UserTopicRecycleAdapter(Context context) {
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
            View view = layoutInflater.inflate(R.layout.item_user_topic, parent, false);
            UserTopicRecycleAdapter.UserTopicRecycleHolder topicRecycleHolder =
                    new UserTopicRecycleAdapter.UserTopicRecycleHolder(view);
            return topicRecycleHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UserTopicRecycleAdapter.UserTopicRecycleHolder) {
            UserTopicRecycleAdapter.UserTopicRecycleHolder topicRecycleHolder =
                    (UserTopicRecycleAdapter.UserTopicRecycleHolder) holder;
            final Topic topic = datas.get(position);
            topicRecycleHolder.tvTitle.setText(Html.fromHtml(topic.getTitle()));
            String content = "";
            if(topic.getContent().length()>20){
                content = topic.getContent().substring(0,19)+"...";
            }
            topicRecycleHolder.tvContent.setText(Html.fromHtml(content));
            topicRecycleHolder.tvDatetime.setText(topic.getTopicTime());
            topicRecycleHolder.tvReplyNum.setText(String.valueOf(topic.getReplyNum()));
            topicRecycleHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    topic.setPageCount(100);
                    intent.putExtra("topic", topic);
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


    public class UserTopicRecycleHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.id_cardview)
        CardView cardView;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_datetime)
        TextView tvDatetime;
        @BindView(R.id.tv_reply_num)
        TextView tvReplyNum;

        public UserTopicRecycleHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
