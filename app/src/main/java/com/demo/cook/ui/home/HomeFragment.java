package com.demo.cook.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.demo.baselib.adapter.MyPagerAdapter;
import com.demo.baselib.design.BaseFragment;
import com.demo.cook.R;
import com.demo.cook.databinding.FragmentHomeBinding;
import com.demo.cook.ui.home.friends.FriendsFragment;
import com.demo.cook.ui.product.fragment.ProductListFragment;
import com.demo.cook.ui.home.search.SearchProductActivity;
import com.demo.cook.ui.product.model.data.request.QueryProductParams;
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

        mDataBinding.tvHomeSearch.setOnClickListener(v ->{
            Intent intent = new Intent(getContext(), SearchProductActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);

        } );

        MutableLiveData<QueryProductParams> productParamsData= new MutableLiveData(new QueryProductParams(QueryProductParams.Order.praise));
        final MyPagerAdapter adapter=new MyPagerAdapter(getChildFragmentManager());
        adapter.add(getString(R.string.text_home_friends), FriendsFragment.newInstance());
        adapter.add(getString(R.string.text_home_recommend), ProductListFragment.newInstance(productParamsData));

        mDataBinding.vpHome.setAdapter(adapter);
        mDataBinding.tabHome.setupWithViewPager(mDataBinding.vpHome);
    }
}