package com.demo.cook.ui.shop.settle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.demo.baselib.adapter.CmnRcvAdapter;
import com.demo.baselib.design.BaseActivity;
import com.demo.cook.R;
import com.demo.cook.databinding.ActivitySettleBinding;
import com.demo.cook.databinding.ItemLayoutGoodsSettleBinding;
import com.demo.cook.ui.shop.model.data.ShoppingCartDetails;

import java.util.ArrayList;
import java.util.List;

public class SettleActivity extends BaseActivity<ActivitySettleBinding,SettleViewModel> {

    public static void actionStart(Context context, String total, ArrayList<ShoppingCartDetails> goods){
        Intent intent = new Intent(context,SettleActivity.class);
        intent.putExtra("total",total);
        intent.putExtra("goodsList",goods);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_settle;
    }

    @Override
    protected SettleViewModel getViewModel() {
        return new ViewModelProvider(this).get(SettleViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDataBinding.setViewModel(mViewModel);

        mDataBinding.tvSettleTotalPrice.setText(getString(R.string.text_settle_total)+getIntent().getStringExtra("total"));

        mDataBinding.rcvSettle.setAdapter(new CmnRcvAdapter<ShoppingCartDetails>(R.layout.item_layout_goods_settle, (List<ShoppingCartDetails>) getIntent().getSerializableExtra("goodsList")) {
            @Override
            public void convert(CmnViewHolder holder, ShoppingCartDetails goods, int position) {
                ItemLayoutGoodsSettleBinding mBinding = DataBindingUtil.bind(holder.itemView);
                mBinding.setShoppingCart(goods);
            }
        });
    }
}