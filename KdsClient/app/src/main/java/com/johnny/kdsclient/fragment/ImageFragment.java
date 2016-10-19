package com.johnny.kdsclient.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.johnny.kdsclient.MessageEvent;
import com.johnny.kdsclient.R;
import com.johnny.kdsclient.adapter.ImageRecycleAdapter;
import com.johnny.kdsclient.api.ApiHelper;
import com.johnny.kdsclient.api.SimpleResponseListener;
import com.johnny.kdsclient.bean.Topic;
import com.johnny.kdsclient.bean.TopicListResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Johnny on 2016/10/3.
 */

public class ImageFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.id_swiperefreshlayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.id_recyclerview)
    RecyclerView recyclerview;

    private ImageRecycleAdapter imageRecycleAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private boolean isFirstShow = true;
    private int lastVisibleItem;
    private int loadedPage;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, null);
        ButterKnife.bind(this, view);

        swipeRefreshLayout.setOnRefreshListener(this);

        imageRecycleAdapter = new ImageRecycleAdapter(getActivity());
        layoutManager = new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter(imageRecycleAdapter);
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == imageRecycleAdapter.getItemCount()) {
                    loadedPage += 1;
                    loadDate(loadedPage);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                int[] lastPositions = staggeredGridLayoutManager.findLastVisibleItemPositions(new int[staggeredGridLayoutManager.getSpanCount()]);
                int size = lastPositions.length;
                int maxPosition = Integer.MIN_VALUE;
                for (int i = 0; i < size; i++) {
                    maxPosition = Math.max(maxPosition, lastPositions[i]);
                }
                lastVisibleItem = maxPosition;
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (isFirstShow) {
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(true);
                    loadedPage = 1;
                    loadDate(1);
                }
            });
            isFirstShow = false;
        }
    }

    ;

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
                //剔除掉无图的帖子
                Iterator<Topic> iterator = topicList.iterator();
                while (iterator.hasNext()) {
                    if ("None".equals(iterator.next().getImgPreview()))
                        iterator.remove();
                }
                if (loadedPage > 1) {
                    //两次刷新的内容会有部分重复，剔除
                    List<Topic> loadedTopicList = imageRecycleAdapter.getDatas();
                    Iterator<Topic> topicIterator = topicList.iterator();
                    while (topicIterator.hasNext()) {
                        for (Topic loadedTopic : loadedTopicList) {
                            if (loadedTopic.getTopicLink().equals(topicIterator.next().getTopicLink())) {
                                topicIterator.remove();
                            }
                        }
                    }
                    imageRecycleAdapter.getDatas().addAll(topicList);
                } else {
                    imageRecycleAdapter.setDatas(topicList);
                }
                imageRecycleAdapter.notifyDataSetChanged();

                if (topicList.size() == 0) {
                    imageRecycleAdapter.setFooterViewType(true);
                } else {
                    imageRecycleAdapter.setFooterViewType(false);
                }
            }
        });
    }
}
