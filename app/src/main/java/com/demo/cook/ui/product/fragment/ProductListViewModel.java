package com.demo.cook.ui.product.fragment;

import androidx.lifecycle.MutableLiveData;

import com.demo.baselib.design.BaseViewModel;
import com.demo.cook.base.http.HttpCallback;
import com.demo.cook.base.http.HttpConfig;
import com.demo.cook.base.http.PageInfo;
import com.demo.cook.base.local.Storage;
import com.demo.cook.ui.interaction.collect.HttpCollectApi;
import com.demo.cook.ui.interaction.praise.HttpPraiseApi;
import com.demo.cook.ui.product.model.HttpProductApi;
import com.demo.cook.ui.product.model.data.ProductDetails;
import com.demo.cook.ui.product.model.data.request.QueryProductParams;

import java.util.ArrayList;
import java.util.List;

public class ProductListViewModel extends BaseViewModel {

    HttpProductApi productDetailsApi = HttpConfig.getHttpServe(HttpProductApi.class);
    HttpPraiseApi praiseApi = HttpConfig.getHttpServe(HttpPraiseApi.class);
    HttpCollectApi collectApi = HttpConfig.getHttpServe(HttpCollectApi.class);

    MutableLiveData<Boolean> finishAndHaveMore = new MutableLiveData<>();

    MutableLiveData<List<ProductDetails>> productListData = new MutableLiveData();


    void getProductList(QueryProductParams productParams){

        productDetailsApi.queryProductList(productParams)
                .enqueue(new HttpCallback<PageInfo<ProductDetails>>() {
                    @Override
                    public void onSuccess(PageInfo<ProductDetails> data) {
                        List<ProductDetails> listData = productListData.getValue();
                        listData=listData==null?new ArrayList():listData;
                        if(data.getPageNum()==1){
                            listData.clear();
                        }
                        listData.addAll(data.getList());
                        productListData.setValue(listData);
                        productParams.setPageSize(data.getNextPage());
                        finishAndHaveMore.setValue(data.isIsLastPage());
                    }

                    @Override
                    public void finallyCall() {
                        super.finallyCall();
                        finishAndHaveMore.setValue(true);
                    }
                });

    }

    void clickPraise(ProductDetails productDetails){
        if(!productDetails.isPraised()){
            praiseApi.addPraise(Storage.getUserInfo().getUsername(),productDetails.getProductId()).enqueue(new HttpCallback(){
                @Override
                public void onSuccess(Object data) {
                    productDetails.setCountPraise(productDetails.getCountPraise()+1);
                    productDetails.setPraised(true);
                }
            });
        }else {
            praiseApi.cancelPraise(Storage.getUserInfo().getUsername(),productDetails.getProductId()).enqueue(new HttpCallback(){
                @Override
                public void onSuccess(Object data) {
                    productDetails.setCountPraise(productDetails.getCountPraise()-1);
                    productDetails.setPraised(false);
                }
            });
        }
    }

    void clickCollect(ProductDetails productDetails){
        if(!productDetails.isCollected()){
            collectApi.addCollect(Storage.getUserInfo().getUsername(),productDetails.getProductId()).enqueue(new HttpCallback(){
                @Override
                public void onSuccess(Object data) {
                    productDetails.setCountCollect(productDetails.getCountCollect()+1);
                    productDetails.setCollected(true);
                }
            });
        }else {
            collectApi.cancelCollect(Storage.getUserInfo().getUsername(),productDetails.getProductId()).enqueue(new HttpCallback(){
                @Override
                public void onSuccess(Object data) {
                    productDetails.setCountCollect(productDetails.getCountCollect()-1);
                    productDetails.setCollected(false);
                }
            });
        }
    }
}