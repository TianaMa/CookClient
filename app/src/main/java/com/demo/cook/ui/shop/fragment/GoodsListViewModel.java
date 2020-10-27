package com.demo.cook.ui.shop.fragment;


import androidx.lifecycle.MutableLiveData;

import com.demo.baselib.design.BaseViewModel;
import com.demo.cook.base.http.HttpCallback;
import com.demo.cook.base.http.HttpConfig;
import com.demo.cook.base.http.PageInfo;
import com.demo.cook.ui.product.model.data.ProductDetails;
import com.demo.cook.ui.shop.model.HttpGoodsApi;
import com.demo.cook.ui.shop.model.data.Goods;
import com.demo.cook.ui.shop.model.data.request.QueryGoodsParams;

import java.util.ArrayList;
import java.util.List;

public class GoodsListViewModel extends BaseViewModel {
    HttpGoodsApi goodsApi = HttpConfig.getHttpServe(HttpGoodsApi.class);

    MutableLiveData<Boolean> finishAndHaveMore = new MutableLiveData<>();

    MutableLiveData<List<Goods>> goodsListData = new MutableLiveData();

    void getGoodsList(QueryGoodsParams params){

        goodsApi.queryGoodsList(params).enqueue(new HttpCallback<PageInfo<Goods>>(){

            @Override
            public void onSuccess(PageInfo<Goods> data) {
                List<Goods> listData = goodsListData.getValue();
                listData=listData==null?new ArrayList():listData;
                if(data.getPageNum()==1){
                    listData.clear();
                }
                listData.addAll(data.getList());
                goodsListData.setValue(listData);
                params.setPageNum(data.getNextPage());
                finishAndHaveMore.setValue(data.isIsLastPage());
            }

            @Override
            public void finallyCall() {
                super.finallyCall();
                finishAndHaveMore.setValue(false);
            }

        });
    }

}