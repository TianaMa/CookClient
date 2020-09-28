package com.demo.cook.ui.recipe.model;

import com.demo.cook.base.http.PageInfo;
import com.demo.cook.base.http.RtnResult;
import com.demo.cook.ui.recipe.model.data.RecipeBrief;
import com.demo.cook.ui.recipe.model.data.Recipe;
import com.demo.cook.ui.recipe.model.data.RecipeDetails;
import com.demo.cook.ui.recipe.model.data.request.QueryRecipeParams;

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


    @POST("recipe/updateMyRecipe")
    Call<RtnResult> updateMyRecipe(
            @Body Recipe recipe
    );

    @POST("recipe/queryRecipeList")
    Call<RtnResult<PageInfo<RecipeBrief>>> queryRecipeList(
            @Body QueryRecipeParams params
    );

    @GET("recipe/queryRecipeDetails")
    Call<RtnResult<RecipeDetails>> queryRecipeDetails(
            @Query("recipeId") String recipeId
    );

}
