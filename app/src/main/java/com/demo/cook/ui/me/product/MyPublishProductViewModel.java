package com.demo.cook.ui.me.product;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import com.demo.baselib.design.BaseViewModel;

import com.demo.cook.base.http.HttpCallback;
import com.demo.cook.base.http.HttpConfig;
import com.demo.cook.base.http.PageInfo;
import com.demo.cook.base.local.Storage;
import com.demo.cook.ui.product.model.HttpProductApi;
import com.demo.cook.ui.product.model.data.ProductDetails;
import com.demo.cook.ui.product.model.data.request.QueryProductParams;

import java.util.ArrayList;
import java.util.List;

public class MyPublishProductViewModel extends BaseViewModel {
    HttpProductApi productApi = HttpConfig.getHttpServe(HttpProductApi.class);

    public MutableLiveData<List<ProductDetails>> myPublishProductListData = new MutableLiveData<>(new ArrayList<>());

    void queryMyProductList(){

        String username = Storage.getUserInfo().getUsername();
        if(!TextUtils.isEmpty(username)){
            QueryProductParams params = new QueryProductParams(username);
            params.setOrder(QueryProductParams.Order.time);
            productApi.queryProductList(params).enqueue(new HttpCallback<PageInfo<ProductDetails>>() {
                @Override
                public void onSuccess(PageInfo<ProductDetails> data) {
                    myPublishProductListData.setValue(data.getList());
                }
            });
        }

    }
}