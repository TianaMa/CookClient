package com.demo.cook.ui.shop.details;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.demo.baselib.design.BaseActivity;
import com.demo.cook.R;
import com.demo.cook.databinding.ActivityGoodsDetailsBinding;
import com.demo.cook.utils.LoginVerifyUtils;

public class GoodsDetailsActivity extends BaseActivity<ActivityGoodsDetailsBinding,GoodsDetailsViewModel> {


    public static void actionStart(Context context, String goodsId){
        Intent intent = new Intent(context,GoodsDetailsActivity.class);
        intent.putExtra("goodsId",goodsId);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_goods_details;
    }

    @Override
    protected GoodsDetailsViewModel getViewModel() {
        return new ViewModelProvider(this).get(GoodsDetailsViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDataBinding.setViewModel(mViewModel);
        mViewModel.queryGoodsDetails(getIntent().getStringExtra("goodsId"));

        mDataBinding.btGoodsAddCart.setOnClickListener(v -> {
            LoginVerifyUtils.verifyAccount(() -> {
                mViewModel.addShoppingCart();
            });
        });

        mDataBinding.btGoodsBuy.setOnClickListener(v -> {

        });

    }
}