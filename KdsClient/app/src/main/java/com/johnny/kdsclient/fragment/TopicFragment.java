package com.johnny.kdsclient.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.johnny.kdsclient.R;
import com.johnny.kdsclient.adapter.TopicRecycleAdapter;
import com.johnny.kdsclient.model.Topic;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Johnny on 2016/10/3.
 */

public class TopicFragment extends Fragment {
    @BindView(R.id.id_swiperefreshlayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.id_recyclerview)
    RecyclerView recyclerview;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topic, null);
        ButterKnife.bind(this,view);

        List<Topic> topicList = new ArrayList<Topic>();
        for(int i=0;i<10;i++){
            Topic topic = new Topic();
            topic.setClickNum(i);
            topicList.add(topic);
        }
        TopicRecycleAdapter topicRecycleAdapter = new TopicRecycleAdapter(getActivity());
        topicRecycleAdapter.setDatas(topicList);
        recyclerview.setAdapter(topicRecycleAdapter);

        return view;
    }
}
