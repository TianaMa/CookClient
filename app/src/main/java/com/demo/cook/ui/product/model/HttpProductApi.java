package com.demo.cook.ui.product.model;

import com.demo.cook.base.http.PageInfo;
import com.demo.cook.base.http.RtnResult;
import com.demo.cook.ui.product.model.data.ProductDetails;
import com.demo.cook.ui.product.model.data.Product;
import com.demo.cook.ui.product.model.data.request.QueryProductParams;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface HttpProductApi {


    @POST("product/publish")
    Call<RtnResult> publish(
            @Body Product recipe
    );


    @POST("product/updateMyRecipe")
    Call<RtnResult> updateMyProduct(
            @Body Product recipe
    );


    @POST("product/queryProductList")
    Call<RtnResult<PageInfo<ProductDetails>>> queryProductList(
            @Body QueryProductParams order
    );


}
