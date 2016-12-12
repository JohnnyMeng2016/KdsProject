package com.johnny.kdsclient.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.johnny.kdsclient.BaseActivity;
import com.johnny.kdsclient.R;
import com.johnny.kdsclient.adapter.UserTopicRecycleAdapter;
import com.johnny.kdsclient.api.ApiHelper;
import com.johnny.kdsclient.api.SimpleResponseListener;
import com.johnny.kdsclient.bean.Reply;
import com.johnny.kdsclient.bean.Topic;
import com.johnny.kdsclient.bean.UserDetailResponse;
import com.johnny.kdsclient.bean.UserInfo;
import com.johnny.kdsclient.bean.UserTopic;
import com.johnny.kdsclient.bean.UserTopicResponse;
import com.johnny.kdsclient.utils.ThemeUtils;
import com.johnny.kdsclient.widget.AppBarStateChangeListener;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 项目名称：KdsClient
 * 类描述：用户详情界面
 * 创建人：孟忠明
 * 创建时间：2016/10/27
 */
public class UserDetailActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    public static final String EXTRA_REPLY = "reply";
    public static final String NAME_IMG_AVATAR = "imgAvatar";

    @BindView(R.id.appbar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.img_avatar)
    CircleImageView imgAvatar;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_signature)
    TextView tvSignature;
    @BindView(R.id.tv_register_time)
    TextView tvRegisterTime;
    @BindView((R.id.tv_score))
    TextView tvScore;
    @BindView(R.id.progress_wheel)
    ProgressWheel progressWheel;
    @BindView(R.id.id_swiperefreshlayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.id_recyclerview)
    RecyclerView recyclerView;

    private UserTopicRecycleAdapter topicAdapter;
    private Reply reply;
    private int loadedPage;
    private int lastVisibleItem;
    private boolean isBottom;

    @Override
    protected void configTheme() {
        ThemeUtils.configThemeBeforeOnCreate(this, R.style.BaseAppTheme_NoActionBar, R.style.BaseAppThemeDark_NoActionBar);
    }

    @Override
    protected int layout() {
        return R.layout.activity_user;
    }

    @Override
    protected void initDate() {
        ViewCompat.setTransitionName(imgAvatar, NAME_IMG_AVATAR);

        Intent intent = getIntent();
        reply = (Reply) intent.getSerializableExtra(EXTRA_REPLY);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                loadedPage = 1;
                loadDate(1);
            }
        });
    }

    @Override
    protected void initView() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.finishAfterTransition(UserDetailActivity.this);
            }
        });
        Glide.with(this).load(reply.getUserdata().getPhoto()).placeholder(R.mipmap.default_avater)
                .dontAnimate().into(imgAvatar);
        if ("男".equals(reply.getUserdata().getSex())) {
            ivSex.setVisibility(View.VISIBLE);
            ivSex.setImageResource(R.mipmap.ic_profile_male);
        } else if ("女".equals(reply.getUserdata().getSex())) {
            ivSex.setVisibility(View.VISIBLE);
            ivSex.setImageResource(R.mipmap.ic_profile_female);
        }
        tvUsername.setText(reply.getNickName());
        tvRegisterTime.setText("注册时间:" + reply.getUserdata().getRegister());
        tvScore.setText("HP:" + reply.getUserdata().getHp() + " PP:" + reply.getUserdata().getPp());

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(this);
        topicAdapter = new UserTopicRecycleAdapter(this);
        final RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(topicAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == topicAdapter.getItemCount() && !isBottom) {
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

        loadUserInfo(reply.getUserId());
    }

    @Override
    public void onRefresh() {
        loadedPage = 1;
        loadDate(1);
    }

    private void loadDate(int page) {
        ApiHelper.getInstance().getUserTopicList(reply.getUserId(), page,
                new SimpleResponseListener<UserTopicResponse>() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        swipeRefreshLayout.setRefreshing(false);
                        progressWheel.stopSpinning();
                    }

                    @Override
                    public void onResponse(UserTopicResponse response) {
                        swipeRefreshLayout.setRefreshing(false);
                        progressWheel.stopSpinning();

                        List<UserTopic> topicList = response.getThread();
                        if (topicList == null || topicList.size() < 40) {
                            isBottom = true;
                        }
                        if (loadedPage > 1) {
                            topicAdapter.getDatas().addAll(topicList);
                        } else {
                            topicAdapter.setDatas(topicList);
                        }
                        topicAdapter.notifyDataSetChanged();

                        if (isBottom) {
                            topicAdapter.setFooterViewType(true);
                            isBottom = true;
                        } else {
                            topicAdapter.setFooterViewType(false);
                        }

                    }
                });
    }

    private void loadUserInfo(String userId) {
        ApiHelper.getInstance().getUserInfo(userId, new SimpleResponseListener<UserInfo>() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

            @Override
            public void onResponse(UserInfo response) {
                tvUsername.setText(response.getNickName() + "(" + response.getUserName() + ")");
                tvSignature.setText(response.getUnderWrite());
            }
        });
    }
}
