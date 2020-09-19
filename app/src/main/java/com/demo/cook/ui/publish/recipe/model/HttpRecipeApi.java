package com.demo.cook.ui.publish.recipe.model;

import com.demo.cook.base.http.RtnResult;
import com.demo.cook.ui.publish.recipe.model.data.MyPublishRecipe;
import com.demo.cook.ui.publish.recipe.model.data.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface HttpRecipeApi {

    @POST("recipe/publish")
    Call<RtnResult> publish(
            @Body Recipe recipe
    );


    @GET("recipe/queryMyPublish")
    Call<RtnResult<List<MyPublishRecipe>>> queryMyPublish(
            @Query("username") String username
    );

    @GET("recipe/queryRecipeDetails")
    Call<RtnResult<Recipe>> queryRecipeDetails(
            @Query("recipeId") String recipeId
    );

    @POST("recipe/updateMyRecipe")
    Call<RtnResult> updateMyRecipe(
            @Body Recipe recipe
    );

}
