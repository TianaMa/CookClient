package com.demo.cook.ui.recipe;

import androidx.lifecycle.MutableLiveData;

import com.demo.baselib.design.BaseViewModel;
import com.demo.cook.R;
import com.demo.cook.base.http.HttpCallback;
import com.demo.cook.base.http.HttpConfig;
import com.demo.cook.base.local.Storage;
import com.demo.cook.ui.recipe.model.HttpRecipeApi;
import com.demo.cook.ui.recipe.model.data.RecipeDetails;
import com.demo.cook.ui.recipe.model.data.RecipeMaterial;
import com.demo.cook.ui.recipe.model.data.RecipeStep;
import com.demo.cook.ui.user.model.data.User;

import java.util.List;

public class RecipeDetailsViewModel extends BaseViewModel {

    public User user = Storage.getUserInfo();

    HttpRecipeApi recipeApi = HttpConfig.getHttpServe(HttpRecipeApi.class);

    public MutableLiveData<RecipeDetails> recipe = new MutableLiveData();

    public MutableLiveData<List<RecipeMaterial>> recipeMaterialListData = new MutableLiveData();

    public MutableLiveData<List<RecipeStep>> recipeStepListData=new MutableLiveData();


    void getRecipeDetails(String recipeId){
        showLoading(R.string.text_loading);
        recipeApi.queryRecipeDetails(recipeId).enqueue(new HttpCallback<RecipeDetails>() {
            @Override
            public void onSuccess(RecipeDetails data) {
                closeLoading();
                recipe.postValue(data);
                recipeMaterialListData.postValue(data.getRecipeMaterialList());
                recipeStepListData.postValue(data.getRecipeStepList());
            }

            @Override
            public void finallyCall() {
                super.finallyCall();
                closeLoading();
            }
        });

    }



}
