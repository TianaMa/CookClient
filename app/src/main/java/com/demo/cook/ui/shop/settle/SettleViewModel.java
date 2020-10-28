package com.demo.cook.ui.shop.settle;

import androidx.lifecycle.MutableLiveData;

import com.demo.baselib.design.BaseViewModel;
import com.demo.cook.base.local.Storage;
import com.demo.cook.ui.user.model.data.UserInfo;

public class SettleViewModel extends BaseViewModel {

    public MutableLiveData<Integer> payWayData = new MutableLiveData<>(1);

    public MutableLiveData<UserInfo> userInfoData = new MutableLiveData<>(Storage.getUserInfo());

    public void changePayWay(int payWay){
        payWayData.setValue(payWay);
    }


}
