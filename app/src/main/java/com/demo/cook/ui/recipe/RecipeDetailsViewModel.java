package com.demo.cook.ui.recipe;

import android.view.View;
import android.widget.CheckBox;

import androidx.lifecycle.MutableLiveData;

import com.demo.basecode.ui.ToastyUtils;
import com.demo.baselib.design.BaseViewModel;
import com.demo.cook.R;
import com.demo.cook.base.http.HttpCallback;
import com.demo.cook.base.http.HttpConfig;
import com.demo.cook.base.local.Storage;
import com.demo.cook.ui.interaction.collect.HttpCollectApi;
import com.demo.cook.ui.interaction.praise.HttpPraiseApi;
import com.demo.cook.ui.interaction.subscribe.HttpSubscribeApi;
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
        recipeApi.queryRecipeDetails(recipeId,Storage.getUserInfo().getUsername()).enqueue(new HttpCallback<RecipeDetails>() {
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



    HttpPraiseApi praiseApi = HttpConfig.getHttpServe(HttpPraiseApi.class);
    public void clickPraise(View view){
        if (((CheckBox) view).isChecked()){
            praiseApi.addPraise(Storage.getUserInfo().getUsername(), recipe.getValue().getRecipeId()).enqueue(new HttpCallback(){
                @Override
                public void onSuccess(Object data) {
                    recipe.getValue().setCountPraise(recipe.getValue().getCountPraise()+1);
                    recipe.getValue().setPraised(true);
                }
            });

        }else {
            praiseApi.cancelPraise(Storage.getUserInfo().getUsername(), recipe.getValue().getRecipeId()).enqueue(new HttpCallback(){
                @Override
                public void onSuccess(Object data) {
                    recipe.getValue().setCountPraise(recipe.getValue().getCountPraise()-1);
                    recipe.getValue().setPraised(false);
                }
            });
        }


    }

    HttpCollectApi collectApi = HttpConfig.getHttpServe(HttpCollectApi.class);
    public void clickCollect(View view){
        if (((CheckBox) view).isChecked()){
            collectApi.addCollect(Storage.getUserInfo().getUsername(), recipe.getValue().getRecipeId()).enqueue(new HttpCallback(){
                @Override
                public void onSuccess(Object data) {
                    recipe.getValue().setCountCollect(recipe.getValue().getCountCollect()+1);
                    recipe.getValue().setCollected(true);
                }
            });
        }else {
            collectApi.cancelCollect(Storage.getUserInfo().getUsername(), recipe.getValue().getRecipeId()).enqueue(new HttpCallback(){
                @Override
                public void onSuccess(Object data) {
                    recipe.getValue().setCountCollect(recipe.getValue().getCountCollect()-1);
                    recipe.getValue().setCollected(false);
                }
            });
        }
    }

    HttpSubscribeApi subscribeApi = HttpConfig.getHttpServe(HttpSubscribeApi.class);
    public void clickSubscribe(View view){
        if (((CheckBox) view).isChecked()){
            subscribeApi.addSubscribe(Storage.getUserInfo().getUsername(),recipe.getValue().getIssuer()).enqueue(new HttpCallback(){
                @Override
                public void onSuccess(Object data) {
                    recipe.getValue().setSubscribe(true);
                }
            });
        }else {
            subscribeApi.cancelSubscribe(Storage.getUserInfo().getUsername(),recipe.getValue().getIssuer()).enqueue(new HttpCallback(){
                @Override
                public void onSuccess(Object data) {
                    recipe.getValue().setSubscribe(false);
                }
            });
        }

    }

}
