package com.demo.cook.ui.user.register;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import com.demo.baselib.design.BaseViewModel;

import com.demo.basecode.ui.ToastyUtils;
import com.demo.cook.R;
import com.demo.cook.base.http.HttpCallback;
import com.demo.cook.base.http.HttpConfig;
import com.demo.cook.base.local.Storage;
import com.demo.cook.ui.user.model.HttpUserApi;
import com.demo.cook.ui.user.model.data.Register;
import com.demo.cook.ui.user.model.data.UserInfo;

import java.util.Random;

public class RegisterViewModel extends BaseViewModel {

    private HttpUserApi userApi = HttpConfig.getHttpServe(HttpUserApi.class);

    public String username;
    public String password;
    public String passwordAgain;


    public void onRegisterClick() {

        if(TextUtils.isEmpty(username)){
            ToastyUtils.show(R.string.text_register_hint_account);
            return;
        }
        if(TextUtils.isEmpty(password)){
            ToastyUtils.show(R.string.text_register_hint_password);
            return;
        }

        if(!password.equals(passwordAgain)){
            ToastyUtils.show(R.string.text_register_password_un_same);
            return;
        }

        Register user = new Register();
        user.setUsername(username);
        user.setPassword(password);
        user.setHeadImg("image/head/user_head_"+new Random().nextInt(34)+".jpg");
        user.setNickname("Visitor_"+username);

        showLoading(R.string.text_loading_register);
        userApi.register(user).enqueue(new HttpCallback<UserInfo>() {
            @Override
            public void onSuccess(UserInfo data) {
                ToastyUtils.showInfo(R.string.text_success);
                Storage.setUserInfo(data);
                uiChange.registerSuccess.postValue(true);
            }

            @Override
            public void finallyCall() {
                super.finallyCall();
                closeLoading();
            }
        });
    }


    public ObservableUIChange uiChange =new ObservableUIChange();

    class ObservableUIChange{
        MutableLiveData<Boolean> registerSuccess=new MutableLiveData<>();
    }



}
