package com.demo.cook.ui.shop.cart;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.demo.baselib.adapter.CmnRcvAdapter;
import com.demo.baselib.design.BaseActivity;
import com.demo.cook.R;
import com.demo.cook.databinding.ActivityShoppingCartBinding;
import com.demo.cook.databinding.ItemLayoutShoppingCartBinding;
import com.demo.cook.ui.shop.model.data.ShoppingCartDetails;
import com.demo.cook.utils.LoginVerifyUtils;

public class ShoppingCartActivity extends BaseActivity<ActivityShoppingCartBinding,ShoppingCartViewModel> {

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_shopping_cart;
    }

    @Override
    protected ShoppingCartViewModel getViewModel() {
        return new ViewModelProvider(this).get(ShoppingCartViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDataBinding.setMViewModel(mViewModel);

        mDataBinding.ivShoppingCartBack.setOnClickListener(v -> onBackPressed());

        CmnRcvAdapter<ShoppingCartDetails> adapter = new CmnRcvAdapter<ShoppingCartDetails>(
                this,R.layout.item_layout_shopping_cart,mViewModel.shoppingCartListData
        ) {
            @Override
            public void convert(CmnViewHolder holder, ShoppingCartDetails shoppingCartDetails, int position) {

                ItemLayoutShoppingCartBinding mBinding= DataBindingUtil.bind(holder.itemView);
                mBinding.setShoppingCart(shoppingCartDetails);
                mBinding.cbShoppingCart.setOnClickListener(v -> {
                    shoppingCartDetails.setCheck(!shoppingCartDetails.isCheck());
                    mViewModel.totalPrice();
                    mViewModel.checkSelectAll();
                });
            }
        };
        View emptyView = LayoutInflater.from(this).inflate(R.layout.empty_layout_shopping_cart,null);
        adapter.setEmptyView(emptyView);
        mDataBinding.rcvShoppingCart.setAdapter(adapter);

        LoginVerifyUtils.verifyAccount(() -> mViewModel.getMyCart());

        mDataBinding.cbShoppingCartSelectAll.setOnClickListener(v -> {
            mViewModel.selectAllData.setValue(!mViewModel.selectAllData.getValue());
            for (ShoppingCartDetails cartDetails:mViewModel.shoppingCartListData.getValue()){
                cartDetails.setCheck(mViewModel.selectAllData.getValue());
            }
            mViewModel.totalPrice();
        });

        mViewModel.priceTotalData.observe(this, s -> mDataBinding.tvShoppingCartTotalPrice.setText(getString(R.string.text_total)+s));

    }
}