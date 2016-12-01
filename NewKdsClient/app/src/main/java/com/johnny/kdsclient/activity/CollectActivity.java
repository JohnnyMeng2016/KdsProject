package com.johnny.kdsclient.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.johnny.kdsclient.BaseActivity;
import com.johnny.kdsclient.MyDbHelper;
import com.johnny.kdsclient.R;
import com.johnny.kdsclient.adapter.DraftRecycleAdapter;
import com.johnny.kdsclient.adapter.TopicRecycleAdapter;
import com.johnny.kdsclient.bean.Topic;
import com.johnny.kdsclient.utils.ThemeUtils;

import java.util.List;

import butterknife.BindView;

/**
 * 项目名称：NewKdsClient
 * 类描述：收藏界面
 * 创建人：孟忠明
 * 创建时间：2016/11/21
 */
public class CollectActivity extends BaseActivity {
    @BindView(R.id.id_toolbar)
    Toolbar toolbar;
    @BindView(R.id.id_recyclerview)
    RecyclerView recyclerView;

    private TopicRecycleAdapter topicRecycleAdapter;

    MyDbHelper myDbHelper;
    List<Topic> collectTopicList;

    @Override
    protected void configTheme() {
        ThemeUtils.configThemeBeforeOnCreate(this, R.style.BaseAppTheme_NoActionBar, R.style.BaseAppThemeDark_NoActionBar);
    }

    @Override
    protected int layout() {
        return R.layout.activity_collect;
    }

    @Override
    protected void initDate() {
        myDbHelper = new MyDbHelper(this);
        collectTopicList = myDbHelper.searchCollectList();
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        topicRecycleAdapter = new TopicRecycleAdapter(this);
        topicRecycleAdapter.setDatas(collectTopicList);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(topicRecycleAdapter);
        topicRecycleAdapter.notifyDataSetChanged();
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean isEnd = true;
                topicRecycleAdapter.setFooterViewType(isEnd);
            }
        }, 200);
    }
}
