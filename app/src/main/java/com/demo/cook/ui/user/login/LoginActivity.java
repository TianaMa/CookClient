package com.demo.cook.ui.user.login;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.demo.baselib.design.BaseActivity;
import com.demo.cook.base.local.Storage;
import com.demo.cook.ui.MainActivity;
import com.demo.cook.R;
import com.demo.cook.databinding.ActivityLoginBinding;
import com.demo.cook.ui.user.model.data.User;
import com.demo.cook.ui.user.register.RegisterActivity;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class LoginActivity extends BaseActivity<ActivityLoginBinding,LoginViewModel> {

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginViewModel getViewModel() {
        return new ViewModelProvider(this).get(LoginViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDataBinding.setLoginViewModel(mViewModel);
        mDataBinding.tvLoginRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            LoginActivity.this.finish();
        });

        mDataBinding.tvLoginVisitor.setOnClickListener(v->{
            User user= new User();
            user.setRegisterDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            user.setNickname("Visitor_"+((Math.random())+"").substring(2,8));
            user.setHeadImg("image/head/user_head_"+new Random().nextInt(34)+".jpg");
            Log.e("Visitor","user="+new Gson().toJson(user));
            Storage.setUser(user);
            startActivity(new Intent(LoginActivity.this,MainActivity.class));

        });

        mViewModel.uiChange.loginSuccess.observe(this, aBoolean -> startActivity(new Intent(LoginActivity.this, MainActivity.class)));

    }
}