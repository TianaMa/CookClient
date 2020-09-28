package com.demo.cook.ui.product;

import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.demo.basecode.ui.ToastyUtils;
import com.demo.baselib.design.BaseViewModel;
import com.demo.cook.R;
import com.demo.cook.base.event.BusEvent;
import com.demo.cook.base.http.HttpCallback;
import com.demo.cook.base.http.HttpConfig;
import com.demo.cook.ui.product.model.HttpProductApi;
import com.demo.cook.ui.product.model.HttpProductTagApi;
import com.demo.cook.ui.product.model.data.Product;
import com.demo.cook.ui.product.model.data.ProductTag;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class PublishProductViewModel extends BaseViewModel {

    HttpProductApi productApi = HttpConfig.getHttpServe(HttpProductApi.class);

    public MutableLiveData<Product> productData = new MutableLiveData(new Product());

    public MutableLiveData<List<String>> productImageListData = new MutableLiveData();


    public void getProductDetails(String productId){


    }

    public void initProduct(ArrayList<String> productImagePathList){
        productImageListData.postValue(productImagePathList);
    }


    public void publish(){

        if(TextUtils.isEmpty(productData.getValue().getTitle())){
            ToastyUtils.show(R.string.text_product_title_cant_empty);return;
        }

        if(TextUtils.isEmpty(productData.getValue().getContent())){
            ToastyUtils.show(R.string.text_product_content_cant_empty);return;
        }

        StringBuffer imagesStr=new StringBuffer();
        for (String productImagePath:productImageListData.getValue() ){
            imagesStr.append(",");
            imagesStr.append(productImagePath);
        }
        productData.getValue().setImages(imagesStr.toString().substring(1));
        Log.e("product==",new Gson().toJson(productData.getValue()));

        showLoading(R.string.text_publishing);
        productApi.publish(productData.getValue()).enqueue(new HttpCallback() {
            @Override
            public void onSuccess(Object data) {
                closeLoading();
                ToastyUtils.show(R.string.text_success);
                EventBus.getDefault().post(new BusEvent.PublishProductSuccess());
                finishActivity();
            }

            @Override
            public void finallyCall() {
                closeLoading();
            }
        });

    }



    HttpProductTagApi productTagApi = HttpConfig.getHttpServe(HttpProductTagApi.class);

    MutableLiveData<List<ProductTag>> productTagsData = new MutableLiveData();

    void getProductTagList(){
        productTagApi.getTags().enqueue(new HttpCallback<List<ProductTag>>() {
            @Override
            public void onSuccess(List<ProductTag> data) {
                productTagsData.setValue(data);
            }
        });
    }






}
