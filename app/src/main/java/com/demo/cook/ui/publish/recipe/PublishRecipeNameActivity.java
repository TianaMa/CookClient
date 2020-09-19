package com.demo.cook.ui.publish.recipe;


import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.demo.baselib.design.BaseActivity;
import com.demo.cook.R;
import com.demo.cook.databinding.ActivityPublishRecipeNameBinding;

public class PublishRecipeNameActivity extends BaseActivity<ActivityPublishRecipeNameBinding,PublishRecipeNameViewModel> {

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_publish_recipe_name;
    }

    @Override
    protected PublishRecipeNameViewModel getViewModel() {
        return new ViewModelProvider(this).get(PublishRecipeNameViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDataBinding.setMViewModel(mViewModel);
        mDataBinding.ivPublishTypeClose.setOnClickListener(v -> onBackPressed());

        mDataBinding.tvPublishRecipeNameNext.setOnClickListener(v -> {
            PublishRecipeActivity.actionCreate(this,mViewModel.recipeName);
            this.finish();
        });

    }
}