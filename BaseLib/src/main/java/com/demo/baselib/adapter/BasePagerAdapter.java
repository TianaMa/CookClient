package com.demo.baselib.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * viewPager-tabLayout CmnRcvAdapter
 * */

public class BasePagerAdapter extends FragmentStateAdapter {

    private List<Fragment> mFragments = new ArrayList<>();
    private List<CharSequence> titles = new ArrayList<>();
    private List<Integer> iconList= new ArrayList<>();

    public BasePagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public BasePagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }



    public void add(String title,Fragment fragment){
        titles.add(title);
        mFragments.add(fragment);
    }

    public void add(String title,int icon,Fragment fragment){
        iconList.add(icon);
        add(title,fragment);
    }

    public CharSequence getTitle(int position) {
        return titles.get(position);
    }

    private int getIcon(int position){
        return iconList.get(position);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getItemCount() {
        return mFragments.size();
    }
}
