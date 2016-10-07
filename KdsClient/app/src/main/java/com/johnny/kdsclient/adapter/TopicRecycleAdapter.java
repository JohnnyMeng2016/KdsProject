package com.johnny.kdsclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.johnny.kdsclient.R;
import com.johnny.kdsclient.model.Topic;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Johnny on 2016/10/5.
 */

public class TopicRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public Context context;
    public List<Topic> datas;
    public LayoutInflater layoutInflater;

    public TopicRecycleAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.datas = new ArrayList<>();
    }

    public void setDatas(List<Topic> datas) {
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_topic, parent, false);
        TopicRecycleHolder topicRecycleHolder = new TopicRecycleHolder(view);
        return topicRecycleHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class TopicRecycleHolder extends RecyclerView.ViewHolder{

        public TopicRecycleHolder(View itemView) {
            super(itemView);
        }
    }
}
