package com.demo.cook.ui.publish.product;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.demo.basecode.ui.ToastyUtils;
import com.demo.baselib.design.BaseViewModel;
import com.demo.cook.R;
import com.demo.cook.base.http.HttpCallback;
import com.demo.cook.base.http.HttpConfig;
import com.demo.cook.ui.publish.product.model.HttpProductApi;
import com.demo.cook.ui.publish.product.model.data.Product;
import com.demo.cook.ui.publish.product.model.data.ProductImage;

import java.util.ArrayList;
import java.util.List;

public class PublishProductViewModel extends BaseViewModel {

    HttpProductApi productApi = HttpConfig.getHttpServe(HttpProductApi.class);

    public MutableLiveData<Product> productData = new MutableLiveData(new Product());

    public MutableLiveData<List<ProductImage>> productImageListData = new MutableLiveData();


    public void getProductDetails(String productId){


    }

    public void initProduct(ArrayList<String> productImagePathList){
        List<ProductImage> productImageList = new ArrayList<>();
        for (String productImagePath:productImagePathList ){
            productImageList.add(new ProductImage(productImagePath));
        }
        productData.getValue().setProductImageList(productImageList);
        productImageListData.postValue(productImageList);
    }


    public void publish(){

        if(TextUtils.isEmpty(productData.getValue().getTitle())){
            ToastyUtils.show(R.string.text_product_title_cant_empty);return;
        }

        if(TextUtils.isEmpty(productData.getValue().getContent())){
            ToastyUtils.show(R.string.text_product_content_cant_empty);return;
        }

        showLoading(R.string.text_publishing);
        productApi.publish(productData.getValue()).enqueue(new HttpCallback() {
            @Override
            public void onSuccess(Object data) {
                closeLoading();
                ToastyUtils.show(R.string.text_success);
                finishActivity();
            }

            @Override
            public void finallyCall() {
                closeLoading();
            }
        });


    }


}
