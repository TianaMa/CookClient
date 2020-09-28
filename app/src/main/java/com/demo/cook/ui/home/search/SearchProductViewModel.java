package com.demo.cook.ui.home.search;

import androidx.lifecycle.MutableLiveData;

import com.demo.baselib.design.BaseViewModel;
import com.demo.cook.base.http.HttpCallback;
import com.demo.cook.base.http.HttpConfig;
import com.demo.cook.ui.product.model.HttpProductTagApi;
import com.demo.cook.ui.product.model.data.ProductTag;
import com.demo.cook.ui.product.model.data.request.QueryProductParams;

import java.util.List;

public class SearchProductViewModel extends BaseViewModel {

    public String searchText = "";

    MutableLiveData<QueryProductParams> productParamsData= new MutableLiveData(new QueryProductParams());

    public void searchProduct(){

        QueryProductParams params = productParamsData.getValue();
        params.setSearchText(searchText);
        productParamsData.postValue(params);

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
