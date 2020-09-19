package com.demo.baselib.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * viewPager-tabLayout CmnRcvAdapter
 * */

public class MyPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments = new ArrayList<>();
    private List<CharSequence> titles = new ArrayList<>();
    private List<Integer> iconList= new ArrayList<>();

    public MyPagerAdapter(FragmentManager fm) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    public void add(String title,Fragment fragment){
        titles.add(title);
        mFragments.add(fragment);
    }

    public void add(String title,int icon,Fragment fragment){
        iconList.add(icon);
        add(title,fragment);
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public Fragment getItem(int arg0) {
        return mFragments.get(arg0);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

}
