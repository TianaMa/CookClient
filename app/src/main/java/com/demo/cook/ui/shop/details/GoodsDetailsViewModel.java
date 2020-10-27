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

    void queryGoodsDetails(String goodsId){
        goodsApi.queryGoodsDetails(goodsId).enqueue(new HttpCallback<Goods>() {
            @Override
            public void onSuccess(Goods data) {
                goodsData.setValue(data);
            }
        });
    }

    void addShoppingCart(){
        goodsApi.addShoppingCart(Storage.getUserInfo().getUsername(),goodsData.getValue().getGoodsId()).enqueue(new HttpCallback<Integer>() {
            @Override
            public void onSuccess(Integer data) {
                EventBus.getDefault().post(new BusEvent.ShoppingCartCount(data));
                ToastyUtils.show(R.string.text_success);
            }
        });
    }
}
