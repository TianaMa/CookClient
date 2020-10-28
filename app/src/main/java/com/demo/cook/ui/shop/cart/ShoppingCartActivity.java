package com.demo.cook.ui.shop.cart;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.demo.basecode.ui.ToastyUtils;
import com.demo.baselib.adapter.CmnRcvAdapter;
import com.demo.baselib.design.BaseActivity;
import com.demo.cook.R;
import com.demo.cook.databinding.ActivityShoppingCartBinding;
import com.demo.cook.databinding.ItemLayoutShoppingCartBinding;
import com.demo.cook.ui.shop.model.data.ShoppingCartDetails;
import com.demo.cook.ui.shop.settle.SettleActivity;
import com.demo.cook.utils.view.DialogUtils;

import java.util.ArrayList;

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
                mBinding.setMViewModel(mViewModel);
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

        mViewModel.getMyCart();

        mDataBinding.btBuyOrDelete.setOnClickListener(v -> {
            if(mViewModel.editAble.getValue()){
                DialogUtils.getInstance().showConfirmDialog(
                        v.getContext(),
                        getString(R.string.text_dialog_title),
                        getString(R.string.text_dialog_delete_goods),
                        (dialog, which) -> mViewModel.deleteGoods()
                );
            }else {
                ArrayList<ShoppingCartDetails> list=new ArrayList<>();
                for (ShoppingCartDetails goods:mViewModel.shoppingCartListData.getValue()){
                    if (goods.isCheck()){
                        list.add(goods);
                    }
                }
                SettleActivity.actionStart(v.getContext(),mViewModel.priceTotalData.getValue(),list);
            }
        });

        mViewModel.priceTotalData.observe(this, s -> mDataBinding.tvShoppingCartTotalPrice.setText(getString(R.string.text_total)+s));

    }
}