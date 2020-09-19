package com.demo.cook.ui.publish.product;

import androidx.lifecycle.MutableLiveData;

import com.demo.baselib.design.BaseViewModel;
import com.demo.cook.ui.publish.product.model.data.Product;

public class PublishProductViewModel extends BaseViewModel {

    public MutableLiveData<Product> productData = new MutableLiveData(new Product());

}
