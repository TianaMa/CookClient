package com.demo.cook.ui.publish.type;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.demo.baselib.design.BaseActivity;
import com.demo.cook.R;
import com.demo.cook.databinding.ActivityPublishTypeBinding;
import com.demo.cook.ui.publish.recipe.PublishRecipeNameActivity;

public class PublishTypeActivity extends BaseActivity<ActivityPublishTypeBinding, PublishTypeViewModel> {


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_publish_type;
    }

    @Override
    protected PublishTypeViewModel getViewModel() {
        return new ViewModelProvider(this).get(PublishTypeViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding.setMViewModel(mViewModel);

        mDataBinding.ivPublishTypeClose.setOnClickListener(v -> onBackPressed());
        mDataBinding.publishTypeRecipe.setOnClickListener(v ->{
            startActivity(new Intent(this, PublishRecipeNameActivity.class));
            this.finish();
        });

        mDataBinding.publishTypeImage.setOnClickListener(v ->{
            startActivity(new Intent(this, PublishRecipeNameActivity.class));
            this.finish();
        });

        mDataBinding.publishTypeVideo.setOnClickListener(v ->{



            startActivity(new Intent(this, PublishRecipeNameActivity.class));
            this.finish();
        });


    }
}