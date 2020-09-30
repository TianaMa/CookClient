package com.demo.cook.ui.home.friends;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.demo.baselib.adapter.CmnRcvAdapter;
import com.demo.baselib.design.BaseFragment;
import com.demo.cook.R;
import com.demo.cook.databinding.FragmentFriendsBinding;
import com.demo.cook.databinding.ItemLayoutFriendsBinding;
import com.demo.cook.databinding.ItemLayoutFriendsRecommendBinding;
import com.demo.cook.ui.user.model.data.UserInfo;
import com.demo.cook.utils.LoginVerifyUtils;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

public class FriendsFragment extends BaseFragment<FragmentFriendsBinding,FriendsViewModel> {

    public static FriendsFragment newInstance() {
        return new FriendsFragment();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_friends;
    }

    @Override
    protected FriendsViewModel getViewModel() {
        return new ViewModelProvider(this).get(FriendsViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //推荐好友
        mDataBinding.rcvFriendRecommend.setAdapter(new CmnRcvAdapter<UserInfo> (this,R.layout.item_layout_friends_recommend,getViewModel().recommendFriendsData){
            @Override
            public void convert(CmnViewHolder holder, UserInfo userInfo, int position) {
                ItemLayoutFriendsRecommendBinding recommendBinding = DataBindingUtil.bind(holder.itemView);
                recommendBinding.setFriends(userInfo);
                recommendBinding.btRecommendSubscribe.setOnClickListener(v -> LoginVerifyUtils.verifyAccount(() -> mViewModel.addSubscribe(userInfo.getUsername())));
            }
        });

        mViewModel.getRecommendFriends();

        mDataBinding.rcvFriendMySubscribe.setAdapter(new CmnRcvAdapter<UserInfo>(this,R.layout.item_layout_friends,mViewModel.mySubscribeFriendsData){
            @Override
            public void convert(CmnViewHolder holder, UserInfo userInfo, int position) {
                ItemLayoutFriendsBinding friendsBinding = DataBindingUtil.bind(holder.itemView);
                friendsBinding.setFriends(userInfo);
                friendsBinding.btRecommendSubscribe.setOnClickListener(v -> LoginVerifyUtils.verifyAccount(() ->mViewModel.cancelSubscribe(userInfo.getUsername())));
            }
        });



        mDataBinding.rflFriendMySubscribe.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mViewModel.getMySubscribeFriends();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mViewModel.pageInfoData.getValue().setNextPage(1);
                mViewModel.getMySubscribeFriends();
            }
        });

        mViewModel.pageInfoData.observe(getViewLifecycleOwner(), productDetailsPageInfo -> {
            mDataBinding.rflFriendMySubscribe.setNoMoreData(productDetailsPageInfo.isIsLastPage());
            mDataBinding.rflFriendMySubscribe.closeHeaderOrFooter();
        });

        mViewModel.getMySubscribeFriends();


    }
}