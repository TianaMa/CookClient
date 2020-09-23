package com.demo.cook.ui.me;

import androidx.lifecycle.MutableLiveData;

import com.demo.basecode.ui.ToastyUtils;
import com.demo.baselib.design.BaseViewModel;

import com.demo.cook.R;
import com.demo.cook.base.http.HttpCallback;
import com.demo.cook.base.http.HttpConfig;
import com.demo.cook.base.local.Storage;
import com.demo.cook.ui.user.model.HttpUserApi;
import com.demo.cook.ui.user.model.data.User;

public class MeViewModel extends BaseViewModel {

    HttpUserApi userApi = HttpConfig.getHttpServe(HttpUserApi.class);

    public MutableLiveData<User> user = new MutableLiveData<>(Storage.getUser());

    void updateHeadImage(String path){
        user.getValue().setHeadImg(path);
        userApi.updateUserInfo(user.getValue()).enqueue(new HttpCallback<User>() {
            @Override
            public void onSuccess(User data) {
                user.postValue(data);
                Storage.setUser(data);
                ToastyUtils.show(R.string.text_update_success);
            }
        });

    }

}