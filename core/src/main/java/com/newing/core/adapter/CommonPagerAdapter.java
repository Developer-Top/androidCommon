package com.newing.core.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：LiMing
 * @date ：2019-04-17
 * @desc ：
 */
public class CommonPagerAdapter extends FragmentPagerAdapter {

    private List<String>   title     = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    public CommonPagerAdapter(FragmentManager fm, List<String> title, List<Fragment> fragments) {
        super(fm);
        this.title=title;
        this.fragments=fragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }

    @Override
    public int getCount() {
        return title.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }


}
