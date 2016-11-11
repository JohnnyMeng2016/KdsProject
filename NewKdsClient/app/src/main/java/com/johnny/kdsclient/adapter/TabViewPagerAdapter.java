package com.johnny.kdsclient.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by Johnny on 2016/10/3.
 */

public class TabViewPagerAdapter extends FragmentStatePagerAdapter {

    private String[] titles;
    private List<Fragment> fragmentList;

    public TabViewPagerAdapter(FragmentManager fm, String[] titles, List<Fragment> fragmentList) {
        super(fm);
        this.titles = titles;
        this.fragmentList = fragmentList;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return titles.length;
    }
}
