package com.demo.cook.ui.home.recommend;

import androidx.lifecycle.MutableLiveData;

import com.demo.baselib.design.BaseViewModel;
import com.demo.cook.base.http.HttpCallback;
import com.demo.cook.base.http.HttpConfig;
import com.demo.cook.base.http.PageInfo;
import com.demo.cook.base.local.Storage;
import com.demo.cook.ui.collect.HttpCollectApi;
import com.demo.cook.ui.praise.HttpPraiseApi;
import com.demo.cook.ui.publish.product.model.HttpProductApi;
import com.demo.cook.ui.publish.product.model.data.ProductDetails;

import java.util.ArrayList;
import java.util.List;

public class RecommendViewModel extends BaseViewModel {

    HttpProductApi productDetailsApi = HttpConfig.getHttpServe(HttpProductApi.class);
    HttpPraiseApi praiseApi = HttpConfig.getHttpServe(HttpPraiseApi.class);
    HttpCollectApi collectApi = HttpConfig.getHttpServe(HttpCollectApi.class);


    MutableLiveData<PageInfo<ProductDetails>> pageInfoData = new MutableLiveData<>(new PageInfo<>());

    MutableLiveData<List<ProductDetails>> productListData = new MutableLiveData(new ArrayList());


    void getProductDetails(){

        productDetailsApi.queryProductDetails(pageInfoData.getValue().getPageNum(),pageInfoData.getValue().getPageSize(), Storage.getUserInfo().getUsername(),"")
                .enqueue(new HttpCallback<PageInfo<ProductDetails>>() {
                    @Override
                    public void onSuccess(PageInfo<ProductDetails> data) {
                        List<ProductDetails> listData = productListData.getValue();
                        if(pageInfoData.getValue().getPageNum()==1){
                            listData.clear();
                        }
                        listData.addAll(data.getList());
                        productListData.postValue(listData);
                        pageInfoData.postValue(data);
                    }

                    @Override
                    public void finallyCall() {
                        super.finallyCall();
                        pageInfoData.postValue(pageInfoData.getValue());
                    }
                });

    }

    void addPraise(ProductDetails productDetails){
        praiseApi.addPraise(Storage.getUserInfo().getUsername(),productDetails.getProductId()).enqueue(new HttpCallback(){
            @Override
            public void onSuccess(Object data) {
                productDetails.setCountPraise(productDetails.getCountPraise()+1);
                productDetails.setPraised(true);
            }
        });
    }

    void cancelPraise(ProductDetails productDetails){
        praiseApi.cancelPraise(Storage.getUserInfo().getUsername(),productDetails.getProductId()).enqueue(new HttpCallback(){
            @Override
            public void onSuccess(Object data) {
                productDetails.setCountPraise(productDetails.getCountPraise()-1);
                productDetails.setPraised(false);
            }
        });
    }

    void addCollect(ProductDetails productDetails){
        collectApi.addCollect(Storage.getUserInfo().getUsername(),productDetails.getProductId()).enqueue(new HttpCallback(){
            @Override
            public void onSuccess(Object data) {
                productDetails.setCountCollect(productDetails.getCountCollect()+1);
                productDetails.setCollected(true);
            }
        });
    }

    void cancelCollect(ProductDetails productDetails){
        collectApi.cancelCollect(Storage.getUserInfo().getUsername(),productDetails.getProductId()).enqueue(new HttpCallback(){
            @Override
            public void onSuccess(Object data) {
                productDetails.setCountCollect(productDetails.getCountCollect()-1);
                productDetails.setCollected(false);
            }
        });
    }
}