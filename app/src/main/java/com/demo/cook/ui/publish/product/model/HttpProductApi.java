package com.demo.cook.ui.publish.product.model;

import com.demo.cook.base.http.PageInfo;
import com.demo.cook.base.http.RtnResult;
import com.demo.cook.ui.publish.product.model.data.ProductDetails;
import com.demo.cook.ui.publish.product.model.data.Product;
import com.demo.cook.ui.publish.product.model.data.ProductTag;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface HttpProductApi {


    @POST("product/publish")
    Call<RtnResult> publish(
            @Body Product recipe
    );

    @GET("product/queryMyPublish")
    Call<RtnResult<PageInfo<ProductDetails>>> queryMyPublish(
            @Query("username") String username
    );

    @POST("product/updateMyRecipe")
    Call<RtnResult> updateMyProduct(
            @Body Product recipe
    );

    @GET("product/queryRecipeDetails")
    Call<RtnResult<Product>> queryProductDetails(
            @Query("recipeId") String recipeId
    );


    @GET("product/queryProductList")
    Call<RtnResult<PageInfo<ProductDetails>>> queryProductDetails(

            @Query("pageNum") int pageNum,
            @Query("pageSize") int pageSize,
            @Query("username") String username,
            @Query("order") String order
    );


}
