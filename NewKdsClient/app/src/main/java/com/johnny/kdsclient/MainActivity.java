package com.johnny.kdsclient;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.johnny.kdsclient.activity.DraftActivity;
import com.johnny.kdsclient.activity.LoginActivity;
import com.johnny.kdsclient.activity.WriteTopicActivity;
import com.johnny.kdsclient.adapter.TabViewPagerAdapter;
import com.johnny.kdsclient.bean.TopicListTypeEnum;
import com.johnny.kdsclient.bean.UserInfo;
import com.johnny.kdsclient.fragment.ImageFragment;
import com.johnny.kdsclient.fragment.TopicFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    private String[] mTitles;
    private List<Fragment> fragmentList;
    private int currentPageIndex;

    @Override
    protected int layout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initDate() {
        mTitles = getResources().getStringArray(R.array.tab_titles);
        fragmentList = new ArrayList<Fragment>();
        TopicFragment topicFragment = new TopicFragment();
        ImageFragment imageFragment = new ImageFragment();
        fragmentList.add(topicFragment);
        fragmentList.add(imageFragment);
    }

    @Override
    protected void initView() {
        setBackEnable(false);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        TabViewPagerAdapter tabViewPagerAdapter = new TabViewPagerAdapter(getSupportFragmentManager(),
                mTitles, fragmentList);
        viewPager.setAdapter(tabViewPagerAdapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(this);

        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);

        ViewGroup view = (ViewGroup) navigationView.getHeaderView(0);
        CircleImageView circleImageView = (CircleImageView) view.findViewById(R.id.iv_avatar);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        navigationView.setNavigationItemSelectedListener(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null == UserData.getInstance().getUserInfo()) {
                    Snackbar.make(view, "发帖请先登录账户", Snackbar.LENGTH_LONG)
                            .setAction("登录", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                            }).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, WriteTopicActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_normal) {
            toolbar.setTitle("主页");
            MessageEvent messageEvent = new MessageEvent();
            messageEvent.setTypeEnum(TopicListTypeEnum.Normal);
            messageEvent.setNeedRefreshPage(currentPageIndex);
            EventBus.getDefault().post(messageEvent);
        } else if (id == R.id.nav_daily_hot) {
            toolbar.setTitle("今日热帖");
            MessageEvent messageEvent = new MessageEvent();
            messageEvent.setTypeEnum(TopicListTypeEnum.Daliy);
            messageEvent.setNeedRefreshPage(currentPageIndex);
            EventBus.getDefault().post(messageEvent);
        } else if (id == R.id.nav_week_hot) {
            toolbar.setTitle("本周热帖");
            MessageEvent messageEvent = new MessageEvent();
            messageEvent.setTypeEnum(TopicListTypeEnum.Week);
            messageEvent.setNeedRefreshPage(currentPageIndex);
            EventBus.getDefault().post(messageEvent);
        } else if (id == R.id.nav_moonth_hot) {
            toolbar.setTitle("本月热帖");
            MessageEvent messageEvent = new MessageEvent();
            messageEvent.setTypeEnum(TopicListTypeEnum.Month);
            messageEvent.setNeedRefreshPage(currentPageIndex);
            EventBus.getDefault().post(messageEvent);
        } else if (id == R.id.nav_draft) {
            if (null == UserData.getInstance().getUserInfo()) {
                Toast.makeText(MainActivity.this, "登录后，可查看草稿箱", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(this, DraftActivity.class);
                startActivity(intent);
            }
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return false;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentPageIndex = position;
        EventBus.getDefault().post(new MessageEvent());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (UserData.getInstance().getUserInfo() != null) {
            UserInfo userInfo = UserData.getInstance().getUserInfo();
            ViewGroup view = (ViewGroup) navigationView.getHeaderView(0);
            CircleImageView circleImageView = (CircleImageView) view.findViewById(R.id.iv_avatar);
            TextView tvUserName = (TextView) view.findViewById(R.id.tv_username);
            TextView tvScore = (TextView) view.findViewById(R.id.tv_score);
            Glide.with(this).load(userInfo.getImg_pic_id()).into(circleImageView);
            tvUserName.setText(userInfo.getUserName());
            tvScore.setText("HP:" + userInfo.getHp() + " PP:" + userInfo.getPp());
        }
    }
}
