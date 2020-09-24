package com.demo.cook.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.demo.baselib.adapter.MyPagerAdapter;
import com.demo.baselib.design.BaseFragment;
import com.demo.cook.R;
import com.demo.cook.databinding.FragmentHomeBinding;
import com.demo.cook.ui.home.friends.FriendsFragment;
import com.demo.cook.ui.home.recommend.RecommendFragment;
import com.demo.cook.ui.me.MeFragment;
import com.demo.cook.ui.me.product.MyPublishProductFragment;
import com.demo.cook.ui.me.recipe.MyPublishRecipeFragment;
import com.demo.cook.ui.publish.type.PublishTypeActivity;
import com.demo.cook.utils.LoginVerifyUtils;

public class HomeFragment extends BaseFragment<FragmentHomeBinding,HomeViewModel> {

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    protected HomeViewModel getViewModel() {
        return new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDataBinding.ivHomePublish.setOnClickListener(v -> {
            LoginVerifyUtils.verifyAccount(() -> startActivity(new Intent(getContext(), PublishTypeActivity.class)));
        });

        final MyPagerAdapter adapter=new MyPagerAdapter(getChildFragmentManager());
        adapter.add(getString(R.string.text_home_friends), FriendsFragment.newInstance());
        adapter.add(getString(R.string.text_home_recommend), RecommendFragment.newInstance());

        mDataBinding.vpHome.setAdapter(adapter);
        mDataBinding.tabHome.setupWithViewPager(mDataBinding.vpHome);
    }
}