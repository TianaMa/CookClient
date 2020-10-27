package com.demo.cook.ui.shop;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.View;

import com.demo.baselib.adapter.MyPagerAdapter;
import com.demo.baselib.design.BaseFragment;
import com.demo.cook.R;
import com.demo.cook.base.event.BusEvent;
import com.demo.cook.databinding.FragmentShopBinding;
import com.demo.cook.ui.shop.cart.ShoppingCartActivity;
import com.demo.cook.ui.shop.fragment.GoodsListFragment;
import com.demo.cook.ui.shop.model.data.GoodsSort;
import com.demo.cook.ui.shop.model.data.request.QueryGoodsParams;
import com.demo.cook.ui.shop.search.SearchGoodsActivity;
import com.demo.cook.utils.LoginVerifyUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ShopFragment extends BaseFragment<FragmentShopBinding,ShopViewModel> {

    public static ShopFragment newInstance() {
        return new ShopFragment();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_shop;
    }

    @Override
    protected ShopViewModel getViewModel() {
        return new ViewModelProvider(this).get(ShopViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        registerEventBus= true;

        mViewModel.queryCount();

        mDataBinding.etLikeSearchText.setOnClickListener(v -> startActivity(new Intent(v.getContext(), SearchGoodsActivity.class)));
        //中间菜单
        try {
            List<GoodsSort> goodsSortList = new Gson().fromJson(
                    new InputStreamReader(getContext().getAssets().open("goodsSortMenu.json"), "UTF-8"),
                    new TypeToken<List<GoodsSort>>() {}.getType()
            );

            final MyPagerAdapter adapter=new MyPagerAdapter(getChildFragmentManager());

            for (GoodsSort goodsSort:goodsSortList){
                adapter.add(goodsSort.getSortName(), GoodsListFragment.newInstance().setParams(new MutableLiveData(new QueryGoodsParams(goodsSort.getSortId()))));
            }

            mDataBinding.vpShop.setAdapter(adapter);
            mDataBinding.tabShop.setupWithViewPager(mDataBinding.vpShop);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //跳转购物车
        mDataBinding.ivShoppingCart.setOnClickListener(v -> LoginVerifyUtils.verifyAccount(()->startActivity(new Intent(v.getContext(), ShoppingCartActivity.class))));

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMsg(BusEvent.ShoppingCartCount msg) {
        mDataBinding.tvShoppingCartCount.setVisibility(msg.getCount()==0?View.GONE:View.VISIBLE);
        mDataBinding.tvShoppingCartCount.setText(msg.getCount()+"");
    }


}