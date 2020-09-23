package com.demo.cook.ui.publish.product.model;

import com.demo.cook.base.http.RtnResult;
import com.demo.cook.ui.publish.product.model.data.Product;

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

    @POST("product/updateMyRecipe")
    Call<RtnResult> updateMyProduct(
            @Body Product recipe
    );

    @GET("product/queryRecipeDetails")
    Call<RtnResult<Product>> queryProductDetails(
            @Query("recipeId") String recipeId
    );



}
