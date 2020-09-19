package com.demo.cook.ui.publish.product;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.demo.baselib.design.BaseActivity;
import com.demo.cook.R;
import com.demo.cook.databinding.ActivityPublishProductBinding;
import com.demo.cook.ui.publish.recipe.PublishRecipeActivity;

import java.util.ArrayList;

public class PublishProductActivity extends BaseActivity<ActivityPublishProductBinding,PublishProductViewModel> {


    private static final String PRODUCT_ID = "productId";

    private static final String PRODUCT_IMAGE_LIST = "productImageList";



    public static void actionCreate(Context context, ArrayList<String> productImageList){
        Intent intent = new Intent(context, PublishRecipeActivity.class);
        intent.putStringArrayListExtra(PRODUCT_IMAGE_LIST,productImageList);
        context.startActivity(intent);
    }

    public static void actionEdit(Context context,String productId){
        Intent intent = new Intent(context,PublishRecipeActivity.class);
        intent.putExtra(PRODUCT_ID,productId);
        context.startActivity(intent);
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_publish_product;
    }

    @Override
    protected PublishProductViewModel getViewModel() {
        return new ViewModelProvider(this).get(PublishProductViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding.setMViewModel(mViewModel);
    }
}