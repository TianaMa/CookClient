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
import com.demo.cook.ui.user.model.data.UserInfo;

public class MyInfoViewModel extends BaseViewModel {
    HttpUserApi userApi = HttpConfig.getHttpServe(HttpUserApi.class);
    public MutableLiveData<User> user = new MutableLiveData<>(Storage.getUserInfo());
    public MutableLiveData<Boolean> editAble=new MutableLiveData<>(false);

    public void editOrSave(){
        if(editAble.getValue()){
            userApi.updateUserInfo(user.getValue()).enqueue(new HttpCallback<UserInfo>() {
                @Override
                public void onSuccess(UserInfo data) {
                    user.postValue(data);
                    Storage.setUserInfo(data);
                    ToastyUtils.show(R.string.text_update_success);
                    editAble.postValue(false);
                }
            });
        }else {
            editAble.postValue(true);
        }
    }




}
