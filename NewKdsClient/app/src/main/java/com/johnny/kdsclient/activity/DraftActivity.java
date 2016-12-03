package com.johnny.kdsclient.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.johnny.kdsclient.BaseActivity;
import com.johnny.kdsclient.MyDbHelper;
import com.johnny.kdsclient.R;
import com.johnny.kdsclient.adapter.DraftRecycleAdapter;
import com.johnny.kdsclient.bean.DraftTopic;
import com.johnny.kdsclient.utils.ThemeUtils;

import java.util.List;

import butterknife.BindView;

/**
 * 项目名称：NewKdsClient
 * 类描述：草稿列表界面
 * 创建人：孟忠明
 * 创建时间：2016/11/15
 */
public class DraftActivity extends BaseActivity {
    @BindView(R.id.id_toolbar)
    Toolbar toolbar;
    @BindView(R.id.id_recyclerview)
    RecyclerView recyclerView;

    MyDbHelper myDbHelper;
    List<DraftTopic> draftTopicList;
    DraftRecycleAdapter draftRecycleAdapter;

    @Override
    protected void configTheme() {
        ThemeUtils.configThemeBeforeOnCreate(this, R.style.BaseAppTheme_NoActionBar, R.style.BaseAppThemeDark_NoActionBar);
    }

    @Override
    protected int layout() {
        return R.layout.activity_draft;
    }

    @Override
    protected void initDate() {
        myDbHelper = new MyDbHelper(this);
        draftTopicList = myDbHelper.searchDraftList();
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

        draftRecycleAdapter = new DraftRecycleAdapter(this, draftTopicList);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(draftRecycleAdapter);

    }
}
