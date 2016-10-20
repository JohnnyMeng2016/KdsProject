package com.johnny.kdsclient.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.android.volley.VolleyError;
import com.johnny.kdsclient.R;
import com.johnny.kdsclient.adapter.TopicRecycleAdapter;
import com.johnny.kdsclient.api.SimpleResponseListener;
import com.johnny.kdsclient.bean.Topic;
import com.johnny.kdsclient.api.ApiHelper;
import com.johnny.kdsclient.bean.TopicListResponse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;

/**
 * Created by Johnny on 2016/10/3.
 */

public class TopicFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.id_swiperefreshlayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.id_recyclerview)
    RecyclerView recyclerview;

    private TopicRecycleAdapter topicRecycleAdapter;

    private int lastVisibleItem;
    private int loadedPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topic, null);
        ButterKnife.bind(this, view);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                loadedPage = 1;
                loadDate(1);
            }
        });

        topicRecycleAdapter = new TopicRecycleAdapter(getActivity());
        final RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter(topicRecycleAdapter);
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == topicRecycleAdapter.getItemCount()) {
                    loadedPage += 1;
                    loadDate(loadedPage);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            }
        });

        return view;
    }

    @Override
    public void onRefresh() {
        loadedPage = 1;
        loadDate(1);
    }

    private void loadDate(int page) {
        ApiHelper.getInstance().getTopicList(page, new SimpleResponseListener<TopicListResponse>() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponse(TopicListResponse response) {
                swipeRefreshLayout.setRefreshing(false);
                List<Topic> topicList = response.getTopicList();
                if (loadedPage > 1) {
                    //两次刷新的内容会有部分重复，剔除
                    List<Topic> loadedTopicList = topicRecycleAdapter.getDatas();
                    Iterator<Topic> iterator = topicList.iterator();
                    while (iterator.hasNext()) {
                        Topic topic = iterator.next();
                        for (Topic loadedTopic : loadedTopicList) {
                            if (loadedTopic.getTitle().equals(topic.getTitle())) {
                                iterator.remove();
                            }
                        }
                    }
                    topicRecycleAdapter.getDatas().addAll(topicList);
                } else {
                    topicRecycleAdapter.setDatas(topicList);
                }
                topicRecycleAdapter.notifyDataSetChanged();

                if (topicList.size() == 0) {
                    topicRecycleAdapter.setFooterViewType(true);
                } else {
                    topicRecycleAdapter.setFooterViewType(false);
                }
            }
        });
    }
}
