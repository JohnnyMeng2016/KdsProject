package com.johnny.kdsclient.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.johnny.kdsclient.R;
import com.johnny.kdsclient.adapter.TopicRecycleAdapter;
import com.johnny.kdsclient.model.Topic;
import com.johnny.kdsclient.utils.ApiHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Johnny on 2016/10/3.
 */

public class TopicFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.id_swiperefreshlayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.id_recyclerview)
    RecyclerView recyclerview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topic, null);
        ButterKnife.bind(this,view);

        swipeRefreshLayout.setOnRefreshListener(this);

        List<Topic> topicList = new ArrayList<Topic>();
        for(int i=0;i<10;i++){
            Topic topic = new Topic();
            topic.setClickNum(i);
            topic.setReplyNum(4);
            topicList.add(topic);
        }
        TopicRecycleAdapter topicRecycleAdapter = new TopicRecycleAdapter(getActivity());
        topicRecycleAdapter.setDatas(topicList);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter(topicRecycleAdapter);

        return view;
    }

    @Override
    public void onRefresh() {
        ApiHelper.getInstance().getTopicList(1,null);
    }
}
