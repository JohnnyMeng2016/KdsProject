package com.johnny.kdsclient.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.johnny.kdsclient.BaseActivity;
import com.johnny.kdsclient.MyDbHelper;
import com.johnny.kdsclient.R;
import com.johnny.kdsclient.UserData;
import com.johnny.kdsclient.adapter.ReplyRecycleAdapter;
import com.johnny.kdsclient.api.ApiHelper;
import com.johnny.kdsclient.api.SimpleResponseListener;
import com.johnny.kdsclient.bean.CommonResponse;
import com.johnny.kdsclient.bean.Reply;
import com.johnny.kdsclient.bean.ReplyListResponse;
import com.johnny.kdsclient.bean.Topic;
import com.johnny.kdsclient.utils.CommonUtils;
import com.johnny.kdsclient.utils.ThemeUtils;
import com.johnny.kdsclient.widget.BottomViewBehavior;

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
    @BindView(R.id.et_message)
    EditText etMessage;

    private ReplyRecycleAdapter replyRecycleAdapter;
    private ProgressDialog progressDialog;

    private Topic topic;
    private int loadedPage;
    private int lastVisibleItem;
    private boolean isBottom;
    private int isOnlyLandLord;
    private String userId;
    MyDbHelper myDbHelper;

    @Override
    protected void configTheme() {
        ThemeUtils.configThemeBeforeOnCreate(this, R.style.BaseAppTheme_NoActionBar, R.style.BaseAppThemeDark_NoActionBar);
    }

    @Override
    protected int layout() {
        return R.layout.activity_topic_detail;
    }

    @Override
    protected void initDate() {
        topic = (Topic) getIntent().getSerializableExtra("topic");
        myDbHelper = new MyDbHelper(this);
    }

    @Override
    protected void initView() {
        toolbar.setTitle("共" + topic.getReply() + "个回复");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        progressDialog = new ProgressDialog(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_orange_light);
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
    public void onResume() {
        if (null != UserData.getInstance().getUserInfo()) {//已登录
            userId = UserData.getInstance().getUserInfo().getUserId();
            llBottom.setVisibility(View.VISIBLE);
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) llBottom.getLayoutParams();
            layoutParams.setBehavior(new BottomViewBehavior(this));
        } else {
            llBottom.setVisibility(View.GONE);
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) llBottom.getLayoutParams();
            layoutParams.setBehavior(null);
        }
        etMessage.setText("");
        super.onResume();
    }

    /**
     * 重新加载页面数据
     */
    private void reloadData() {
        swipeRefreshLayout.setRefreshing(true);
        loadedPage = 1;
        isBottom = false;
        loadDate(1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.reply_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_only_lord) {
            if (isOnlyLandLord == 1) {
                isOnlyLandLord = 0;
                reloadData();
            } else {
                isOnlyLandLord = 1;
                reloadData();
            }
            return true;
        } else if (id == R.id.action_login) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_share) {
            return true;
        } else if (id == R.id.action_collect) {
            myDbHelper.saveCollect(topic);
            Toast.makeText(TopicDetailActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
                        if (replyList == null || replyList.size() < 10) {//通常API一次会返回20条，但可能由于删帖导致返回少于20
                            isBottom = true;
                        }
                        if (loadedPage > 1) {
                            //这边API很坑，只看楼主页码会循环加载的。
                            if (isOnlyLandLord == 1 &&
                                    replyList.get(0).getPostTime().equals(replyRecycleAdapter.getDatas().get(0).getPostTime())) {
                                replyList.clear();
                                isBottom = true;
                            }
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

    @OnClick({R.id.ib_ComplexReply, R.id.ib_send})
    public void onAction(View view) {
        switch (view.getId()) {
            case R.id.ib_ComplexReply:
                Intent intent = new Intent(this, ReplyTopicActivity.class);
                intent.putExtra("topic", topic);
                startActivity(intent);
                break;
            case R.id.ib_send:
                String message = etMessage.getText().toString();
                if ("".equals(message)) {
                    Toast.makeText(this, "写点内容吧", Toast.LENGTH_SHORT).show();
                    return;
                }
                message = message.replace("\n", "<br/>");
                progressDialog.setMessage("发送中...");
                progressDialog.show();
                ApiHelper.getInstance().replyTopic(message, topic.getBbsId(), userId, new SimpleResponseListener<CommonResponse>() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(TopicDetailActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(CommonResponse response) {
                        progressDialog.dismiss();
                        if (response.getFlag() == 1) {
                            Toast.makeText(TopicDetailActivity.this, "回复成功", Toast.LENGTH_SHORT).show();
                            etMessage.setText("");
                            CommonUtils.closeKeybord(etMessage, TopicDetailActivity.this);
                        } else {
                            Toast.makeText(TopicDetailActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        }
    }

}
