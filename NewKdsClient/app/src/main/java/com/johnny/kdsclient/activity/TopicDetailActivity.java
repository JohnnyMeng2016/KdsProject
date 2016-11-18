package com.johnny.kdsclient.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.johnny.kdsclient.BaseActivity;
import com.johnny.kdsclient.R;
import com.johnny.kdsclient.adapter.ReplyRecycleAdapter;
import com.johnny.kdsclient.api.ApiHelper;
import com.johnny.kdsclient.api.SimpleResponseListener;
import com.johnny.kdsclient.bean.Reply;
import com.johnny.kdsclient.bean.ReplyListResponse;
import com.johnny.kdsclient.bean.Topic;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 项目名称：KdsClient
 * 类描述：帖子内容界面
 * 创建人：孟忠明
 * 创建时间：2016/10/10
 */
public class TopicDetailActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.id_toolbar)
    Toolbar toolbar;
    @BindView(R.id.id_swiperefreshlayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.id_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.ib_ComplexReply)
    ImageButton complexReplyButton;

    private ReplyRecycleAdapter replyRecycleAdapter;

    private Topic topic;
    private int loadedPage;
    private int lastVisibleItem;
    private boolean isBottom;
    private int isOnlyLandLord;

    @Override
    protected int layout() {
        return R.layout.activity_topic_detail;
    }

    @Override
    protected void initDate() {
        topic = (Topic) getIntent().getSerializableExtra("topic");
    }

    @Override
    protected void initView() {
        toolbar.setTitle("共" + topic.getReply() + "个回复");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().hide();

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                loadedPage = 1;
                loadDate(1);
            }
        }, 200);

        replyRecycleAdapter = new ReplyRecycleAdapter(this, topic);
        final RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(replyRecycleAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == replyRecycleAdapter.getItemCount() && !isBottom) {
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
    }

    @Override
    public void onRefresh() {
        loadedPage = 1;
        loadDate(1);
    }

    private void loadDate(int page) {
        String bbsId = topic.getBbsId();
        ApiHelper.getInstance().getReplyList(bbsId, page, isOnlyLandLord,
                new SimpleResponseListener<ReplyListResponse>() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onResponse(ReplyListResponse response) {
                        swipeRefreshLayout.setRefreshing(false);
                        List<Reply> replyList = response.getReplys();
                        if (replyList == null || replyList.size() == 0) {
                            isBottom = true;
                        }
                        if (loadedPage > 1) {
                            replyRecycleAdapter.getDatas().addAll(replyList);
                        } else {
                            replyRecycleAdapter.setDatas(replyList);
                        }
                        replyRecycleAdapter.notifyDataSetChanged();

                        if (replyRecycleAdapter.getDatas().size() >= topic.getReply() || isBottom) {
                            replyRecycleAdapter.setFooterViewType(true);
                            isBottom = true;
                        } else {
                            replyRecycleAdapter.setFooterViewType(false);
                        }
                    }
                });
    }

    @OnClick(R.id.ib_ComplexReply)
    public void onAction(View view) {
        switch (view.getId()) {
            case R.id.ib_ComplexReply:
                Intent intent = new Intent(this, ReplyTopicActivity.class);
                startActivity(intent);
                break;
        }
    }

}
