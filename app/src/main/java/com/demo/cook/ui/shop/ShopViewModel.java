package com.demo.cook.ui.shop;


import com.demo.baselib.design.BaseViewModel;
import com.demo.cook.base.event.BusEvent;
import com.demo.cook.base.http.HttpCallback;
import com.demo.cook.base.http.HttpConfig;
import com.demo.cook.base.local.Storage;
import com.demo.cook.ui.shop.model.HttpGoodsApi;

import org.greenrobot.eventbus.EventBus;

public class ShopViewModel extends BaseViewModel {
    HttpGoodsApi goodsApi = HttpConfig.getHttpServe(HttpGoodsApi.class);

    public void queryCount(){
        goodsApi.queryCount(Storage.getUserInfo().getUsername()).enqueue(new HttpCallback<Integer>() {
            @Override
            public void onSuccess(Integer data) {
                EventBus.getDefault().post(new BusEvent.ShoppingCartCount(data));
            }
        });
    }


}