package com.demo.cook.ui.publish.type;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.demo.baselib.design.BaseActivity;
import com.demo.cook.R;
import com.demo.cook.base.http.QiNiuUtil;
import com.demo.cook.databinding.ActivityPublishTypeBinding;
import com.demo.cook.ui.product.PublishProductActivity;
import com.demo.cook.ui.recipe.publish.PublishRecipeNameActivity;
import com.demo.cook.utils.upload.UpLoadUtils;
import com.google.gson.Gson;


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
        mDataBinding.publishTypeRecipe.setOnClickListener(v -> {
            startActivity(new Intent(this, PublishRecipeNameActivity.class));
            this.finish();
        });

        mDataBinding.publishTypeImage.setOnClickListener(v -> UpLoadUtils.upLoadMultiImage(this, QiNiuUtil.Prefix.IMAGE_RECIPE_PRODUCT,9, pathList -> {
            Log.e("upLoadMultiImage", new Gson().toJson(pathList));
            PublishProductActivity.actionCreate(PublishTypeActivity.this, pathList);
            PublishTypeActivity.this.finish();
        }));

//        mDataBinding.publishTypeVideo.setOnClickListener(v -> {
//            startActivity(new Intent(this, PublishRecipeNameActivity.class));
//            this.finish();
//        });


    }
}