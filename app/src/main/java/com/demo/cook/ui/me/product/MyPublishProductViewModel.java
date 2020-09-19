package com.demo.cook.ui.me.product;

import androidx.lifecycle.MutableLiveData;
import com.demo.baselib.design.BaseViewModel;

import com.demo.cook.ui.publish.product.model.data.MyPublishProduct;

import java.util.ArrayList;
import java.util.List;

public class MyPublishProductViewModel extends BaseViewModel {

    public MutableLiveData<List<MyPublishProduct>> myPublishProductListData = new MutableLiveData<>(new ArrayList<>());
}