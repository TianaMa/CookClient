package com.demo.cook.ui.shop.details;

import android.content.Context;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.demo.basecode.widget.BaseBottomDialog;
import com.demo.cook.R;
import com.demo.cook.databinding.ViewDialogBuyGoodsBinding;
import com.demo.cook.ui.shop.model.data.ShoppingCartDetails;

public class BuyGoodsDialog extends BaseBottomDialog {

    FinishedCallback finishedCallback;
    ShoppingCartDetails goods;

    public BuyGoodsDialog(Context context, ShoppingCartDetails goods,FinishedCallback finishedCallback) {
        super(context);
        this.goods= goods;
        this.finishedCallback = finishedCallback;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.view_dialog_buy_goods;
    }


    ViewDialogBuyGoodsBinding mDataBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding= DataBindingUtil.bind(childView);
        mDataBinding.setShoppingCart(goods);

        mDataBinding.ivBuyGoodsClose.setOnClickListener(v -> dismiss());
        mDataBinding.tvBuyGoodsReduce.setOnClickListener(v -> goods.setBuyCount(goods.getBuyCount()-1));
        mDataBinding.tvBuyGoodsAdd.setOnClickListener(v -> goods.setBuyCount(goods.getBuyCount()+1));
        mDataBinding.btBuyGoodsConfirm.setOnClickListener(v -> {
            if (finishedCallback != null) {
                finishedCallback.selected(goods);
            }
        });
    }

    interface FinishedCallback{

        void selected(ShoppingCartDetails goods);
    }
}
