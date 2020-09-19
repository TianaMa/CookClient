package com.demo.cook.ui.user.register;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.demo.baselib.design.BaseActivity;
import com.demo.cook.R;
import com.demo.cook.databinding.ActivityRegisterBinding;
import com.demo.cook.ui.MainActivity;
import com.demo.cook.ui.user.login.LoginActivity;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding,RegisterViewModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDataBinding.setMViewModel(mViewModel);

        mDataBinding.ivRegisterClose.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            RegisterActivity.this.finish();
        });

        mViewModel.uiChange.registerSuccess.observe(this, aBoolean -> {
            startActivity(new Intent(this, MainActivity.class));
        });
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_register;
    }

    @Override
    protected RegisterViewModel getViewModel() {
        return new ViewModelProvider(this).get(RegisterViewModel.class);
    }
}