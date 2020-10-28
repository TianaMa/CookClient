package com.demo.cook.ui.shop.details;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.demo.baselib.design.BaseActivity;
import com.demo.cook.R;
import com.demo.cook.base.event.BusEvent;
import com.demo.cook.databinding.ActivityGoodsDetailsBinding;
import com.demo.cook.ui.shop.cart.ShoppingCartActivity;
import com.demo.cook.utils.LoginVerifyUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
        registerEventBus = true;
        super.onCreate(savedInstanceState);

        mDataBinding.setViewModel(mViewModel);
        mViewModel.queryGoodsDetails(getIntent().getStringExtra("goodsId"));

        mDataBinding.btGoodsAddCart.setOnClickListener(v -> {
            LoginVerifyUtils.verifyAccount(() -> {
                mViewModel.addShoppingCart();
            });
        });

        mViewModel.queryCount();

        mDataBinding.btGoodsBuy.setOnClickListener(v -> {

        });

        //跳转购物车
        mDataBinding.ivShoppingCart.setOnClickListener(v -> LoginVerifyUtils.verifyAccount(()->{
            startActivity(new Intent(v.getContext(), ShoppingCartActivity.class));
            GoodsDetailsActivity.this.finish();
        }));

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMsg(BusEvent.ShoppingCartCount msg) {
        mDataBinding.tvShoppingCartCount.setVisibility(msg.getCount()==0? View.GONE:View.VISIBLE);
        mDataBinding.tvShoppingCartCount.setText(msg.getCount()+"");
    }
}