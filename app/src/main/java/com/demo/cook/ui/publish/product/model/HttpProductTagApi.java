package com.demo.cook.ui.publish.product.model;

import com.demo.cook.base.http.RtnResult;
import com.demo.cook.ui.publish.product.model.data.ProductTag;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface HttpProductTagApi {

    @GET("productTag/queryList")
    Call<RtnResult<List<ProductTag>>> getTags();
}
