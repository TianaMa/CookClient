package com.demo.cook.ui.product.data;

import com.demo.cook.base.http.PageInfo;
import com.demo.cook.base.http.RtnResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HttpProductDetailsApi {

    @GET("product/queryProductList")
    Call<RtnResult<PageInfo<ProductDetails>>> queryProductDetails(

            @Query("pageNum") int pageNum,
            @Query("pageSize") int pageSize,
            @Query("username") String username,
            @Query("order") String order
    );



}
