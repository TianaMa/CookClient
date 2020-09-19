package com.demo.baselib.design;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import com.demo.baselib.design.BaseViewModel;

import org.greenrobot.eventbus.EventBus;

public abstract class BaseFragment<V extends ViewDataBinding,VM extends BaseViewModel> extends Fragment {

    protected boolean registerEventBus =false;

    protected V mDataBinding;

    protected VM mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mDataBinding = DataBindingUtil.inflate(inflater, getLayoutRes(),container,false);
        mDataBinding.setLifecycleOwner(this);
        mViewModel = getViewModel();
        initUIObservable();
        return mDataBinding.getRoot();
    }


    @Override
    public void onStart() {
        super.onStart();
        registerBus();
    }

    protected abstract int getLayoutRes();

    protected abstract VM getViewModel();

    private void registerBus(){
        if (registerEventBus && !EventBus.getDefault().isRegistered(this)) {
            Log.e("BaseFragment","registerBus===");
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (registerEventBus && EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    private void initUIObservable(){
        mViewModel.uiChangeObservable.showLoading.observe(this.getViewLifecycleOwner(), s -> {
            if (TextUtils.isEmpty(s)){
                closeLoading();
            }else {
                showLoading(s);
            }
        });
    }

    private Dialog loading;

    public void showLoading(@StringRes int msgId){
        showLoading(this.getString(msgId));
    }

    public void showLoading(String msg) {
        closeLoading();
        GradientDrawable drawable = new GradientDrawable();//描述布局背景shape 圆角和背景色
        drawable.setColor(Color.parseColor("#CC444444"));
        drawable.setCornerRadius(50);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        LinearLayout layout = new LinearLayout(getContext());//生成布局
        layout.setLayoutParams(layoutParams);//设置宽高
        layout.setGravity(Gravity.CENTER);
        layout.setBackground(drawable);//背景
        layout.setPadding(40, 50, 40, 50);//padding
        layout.setOrientation(LinearLayout.VERTICAL);//方向

        ProgressBar progressBar = new ProgressBar(getContext());
        layout.addView(progressBar);

        TextView textView = new TextView(getContext());
        textView.setLayoutParams(layoutParams);
        textView.setTextColor(Color.parseColor("#FFFFFF"));
        textView.setText(msg);
        layout.addView(textView);

        loading = new Dialog(getContext(), android.R.style.Theme_Material_Dialog_NoActionBar);//R.style.progress_dialog
        loading.setContentView(layout);
        loading.setCancelable(false);
        loading.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        loading.getWindow().setDimAmount(0f);//去掉背景遮蔽蒙版
        loading.show();
    }

    public void closeLoading() {
        if (loading != null && loading.isShowing()) {
            loading.dismiss();
            loading = null;
        }
    }
}
