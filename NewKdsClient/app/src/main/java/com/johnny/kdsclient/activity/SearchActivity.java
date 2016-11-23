package com.johnny.kdsclient.activity;

import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.johnny.kdsclient.BaseActivity;
import com.johnny.kdsclient.R;
import com.johnny.kdsclient.adapter.TopicRecycleAdapter;
import com.johnny.kdsclient.api.ApiHelper;
import com.johnny.kdsclient.api.SimpleResponseListener;
import com.johnny.kdsclient.bean.SearchTopicResponse;
import com.johnny.kdsclient.bean.Topic;
import com.johnny.kdsclient.widget.KdsSearchBar;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 项目名称：NewKdsClient
 * 类描述：查询界面
 * 创建人：孟忠明
 * 创建时间：2016/11/21
 */
public class SearchActivity extends BaseActivity {
    @BindView(R.id.searchBar)
    KdsSearchBar searchBar;
    @BindView(R.id.id_swiperefreshlayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.id_recyclerview)
    RecyclerView recyclerView;

    private TopicRecycleAdapter topicRecycleAdapter;
    private SharedPreferences sharedPreferences;
    private String lastSearches;
    private List<String> lastSearcheList;
    private int lastVisibleItem;
    private int loadedPage;
    private String loadedKeyword;
    private boolean isEnd = true;

    @Override
    protected int layout() {
        return R.layout.activity_search;
    }

    @Override
    protected void initDate() {
        sharedPreferences = this.getSharedPreferences("setting", this.MODE_WORLD_WRITEABLE);
        lastSearcheList = new ArrayList<>();
        lastSearches = sharedPreferences.getString("searchHistory", "");
        String[] lastSearcheArr = lastSearches.split(";");
        for (String lastSearch : lastSearcheArr) {
            lastSearcheList.add(lastSearch);
        }
    }

    @Override
    protected void initView() {
        swipeRefreshLayout.setEnabled(false);

        topicRecycleAdapter = new TopicRecycleAdapter(this);
        final RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(topicRecycleAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && !isEnd
                        && lastVisibleItem + 1 == topicRecycleAdapter.getItemCount()) {
                    loadedPage += 1;
                    loadDate();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            }
        });

        if (lastSearcheList.size() > 0) {
            searchBar.setLastSuggestions(lastSearcheList);
        }
        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean b) {
            }

            @Override
            public void onSearchConfirmed(CharSequence charSequence) {
                if (!charSequence.toString().equals(loadedKeyword)) {
                    loadedKeyword = charSequence.toString();
                    loadedPage = 1;
                    isEnd = false;
                    loadDate();
                    lastSearcheList.add(loadedKeyword);
                    lastSearches = "";
                    for (String lastSearch : lastSearcheList) {
                        lastSearches += lastSearch;
                        lastSearches += ";";
                    }
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("searchHistory", lastSearches);
                    editor.commit();
                }
            }

            @Override
            public void onButtonClicked(int i) {
            }
        });
        searchBar.setOnItemDeleteListener(new KdsSearchBar.OnItemDeleteListener() {
            @Override
            public void onItemDelete(int position) {
                lastSearches = "";
                for (String lastSearch : lastSearcheList) {
                    lastSearches += lastSearch;
                    lastSearches += ";";
                }
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("searchHistory", lastSearches);
                editor.commit();
            }
        });
    }

    private void loadDate() {
        swipeRefreshLayout.setRefreshing(true);
        ApiHelper.getInstance().searchTopic(loadedKeyword, loadedPage,
                new SimpleResponseListener<SearchTopicResponse>() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(SearchActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(SearchTopicResponse response) {
                        swipeRefreshLayout.setRefreshing(false);
                        List<Topic> topicList = response.getData();
                        if (topicList.size() > 0) {
                            if (loadedPage > 1) {
                                topicRecycleAdapter.getDatas().addAll(topicList);
                            } else {
                                topicRecycleAdapter.setDatas(topicList);
                            }
                        }
                        if (topicList.size() < 40) {
                            isEnd = true;
                        }
                        topicRecycleAdapter.notifyDataSetChanged();
                    }
                });
    }
}
