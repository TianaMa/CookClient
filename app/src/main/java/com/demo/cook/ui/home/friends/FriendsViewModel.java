package com.demo.cook.ui.home.friends;


import androidx.lifecycle.MutableLiveData;

import com.demo.basecode.ui.ToastyUtils;
import com.demo.baselib.design.BaseViewModel;
import com.demo.cook.R;
import com.demo.cook.base.http.HttpCallback;
import com.demo.cook.base.http.HttpConfig;
import com.demo.cook.base.http.PageInfo;
import com.demo.cook.base.local.Storage;
import com.demo.cook.ui.interaction.subscribe.HttpSubscribeApi;
import com.demo.cook.ui.user.model.HttpUserApi;
import com.demo.cook.ui.user.model.data.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class FriendsViewModel extends BaseViewModel {

    HttpUserApi httpUserApi = HttpConfig.getHttpServe(HttpUserApi.class);
    HttpSubscribeApi subscribeApi = HttpConfig.getHttpServe(HttpSubscribeApi.class);

    MutableLiveData<List<UserInfo>> recommendFriendsData = new MutableLiveData();
    MutableLiveData<PageInfo<UserInfo>> pageInfoData = new MutableLiveData(new PageInfo());

    MutableLiveData<List<UserInfo>> mySubscribeFriendsData = new MutableLiveData(new ArrayList());

    void getRecommendFriends(){
        httpUserApi.getRecommendFriends(Storage.getUserInfo().getUsername()).enqueue(new HttpCallback<List<UserInfo>>() {
            @Override
            public void onSuccess(List<UserInfo> data) {
                recommendFriendsData.postValue(data);
            }
        });
    }

    void getMySubscribeFriends(){
        httpUserApi.getMySubscribeFriends(
                Storage.getUserInfo().getUsername(),
                pageInfoData.getValue().getNextPage(), pageInfoData.getValue().getPageSize()
        ).enqueue(new HttpCallback<PageInfo<UserInfo>>() {
            @Override
            public void onSuccess(PageInfo<UserInfo> data) {

                List<UserInfo> listData = mySubscribeFriendsData.getValue();
                if(data.getPageNum()==1){
                    listData.clear();
                }
                listData.addAll(data.getList());
                mySubscribeFriendsData.postValue(listData);
                pageInfoData.postValue(data);

            }

            @Override
            public void finallyCall() {
                super.finallyCall();
                pageInfoData.postValue(pageInfoData.getValue());
            }
        });
    }

    void addSubscribe(String targetUser){
        subscribeApi.addSubscribe(Storage.getUserInfo().getUsername(),targetUser).enqueue(new HttpCallback(){
            @Override
            public void onSuccess(Object data) {
                ToastyUtils.show(R.string.text_success);
                getRecommendFriends();
                pageInfoData.getValue().setNextPage(1);
                getMySubscribeFriends();
            }
        });
    }

    void cancelSubscribe(String targetUser){
        subscribeApi.cancelSubscribe(Storage.getUserInfo().getUsername(),targetUser).enqueue(new HttpCallback(){
            @Override
            public void onSuccess(Object data) {
                getRecommendFriends();
                pageInfoData.getValue().setNextPage(1);
                getMySubscribeFriends();
            }
        });
    }


}