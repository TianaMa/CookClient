package com.demo.cook.ui.shop.details;

import androidx.lifecycle.MutableLiveData;

import com.demo.basecode.ui.ToastyUtils;
import com.demo.baselib.design.BaseViewModel;
import com.demo.cook.R;
import com.demo.cook.base.event.BusEvent;
import com.demo.cook.base.http.HttpCallback;
import com.demo.cook.base.http.HttpConfig;
import com.demo.cook.base.local.Storage;
import com.demo.cook.ui.shop.model.HttpGoodsApi;
import com.demo.cook.ui.shop.model.data.Goods;

import org.greenrobot.eventbus.EventBus;

public class GoodsDetailsViewModel extends BaseViewModel {

    private HttpGoodsApi goodsApi = HttpConfig.getHttpServe(HttpGoodsApi.class);

    public MutableLiveData<Goods> goodsData= new MutableLiveData<>();

    public void queryCount(){
        goodsApi.queryCount(Storage.getUserInfo().getUsername()).enqueue(new HttpCallback<Integer>() {
            @Override
            public void onSuccess(Integer data) {
                EventBus.getDefault().post(new BusEvent.ShoppingCartCount(data));
            }
        });
    }


    void queryGoodsDetails(String goodsId){
        showLoading(R.string.text_loading);
        goodsApi.queryGoodsDetails(goodsId).enqueue(new HttpCallback<Goods>() {
            @Override
            public void onSuccess(Goods data) {
                goodsData.setValue(data);
            }

            @Override
            public void finallyCall() {
                super.finallyCall();
                closeLoading();
            }
        });
    }

    void addShoppingCart(){
        showLoading(R.string.text_loading);
        goodsApi.addShoppingCart(Storage.getUserInfo().getUsername(),goodsData.getValue().getGoodsId()).enqueue(new HttpCallback<Integer>() {
            @Override
            public void onSuccess(Integer data) {
                EventBus.getDefault().post(new BusEvent.ShoppingCartCount(data));
            }

            @Override
            public void finallyCall() {
                super.finallyCall();
                closeLoading();
            }
        });
    }
}
