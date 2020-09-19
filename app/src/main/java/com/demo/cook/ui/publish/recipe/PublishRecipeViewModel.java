package com.demo.cook.ui.publish.recipe;


import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import com.demo.baselib.design.BaseViewModel;

import com.demo.basecode.ui.ToastyUtils;
import com.demo.baselib.base.BaseContext;
import com.demo.cook.R;
import com.demo.cook.base.http.HttpCallback;
import com.demo.cook.base.http.HttpConfig;
import com.demo.cook.base.http.RtnResult;
import com.demo.cook.ui.publish.recipe.model.HttpRecipeApi;
import com.demo.cook.ui.publish.recipe.model.data.Recipe;
import com.demo.cook.ui.publish.recipe.model.data.RecipeMaterial;
import com.demo.cook.ui.publish.recipe.model.data.RecipeStep;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;


public class PublishRecipeViewModel extends BaseViewModel {

    HttpRecipeApi httpRecipeApi = HttpConfig.getHttpServe(HttpRecipeApi.class);


    public MutableLiveData<Recipe> recipe=new MutableLiveData<>(new Recipe());

    public MutableLiveData<List<RecipeMaterial>> recipeMaterialListData = new MutableLiveData();

    public MutableLiveData<List<RecipeStep>> recipeStepListData=new MutableLiveData();

    public void getRecipeDetails(String recipeId){

        showLoading(R.string.text_loading);
        httpRecipeApi.queryRecipeDetails(recipeId).enqueue(new HttpCallback<Recipe>() {
            @Override
            public void onSuccess(Recipe data) {
                recipe.postValue(data);
                recipeMaterialListData.postValue(data.getRecipeMaterialList());
                recipeStepListData.postValue(data.getRecipeStepList());
            }

            @Override
            public void finallyCall() {
                closeLoading();
            }
        });
    }

    public void initData(){

        recipe.getValue().setRecipeMaterialList(new ArrayList<>());
        recipe.getValue().getRecipeMaterialList().add(new RecipeMaterial());
        recipe.getValue().getRecipeMaterialList().add(new RecipeMaterial());
        recipeMaterialListData.postValue(recipe.getValue().getRecipeMaterialList());

        recipe.getValue().setRecipeStepList(new ArrayList<>());
        recipe.getValue().getRecipeStepList().add(new RecipeStep());
        recipe.getValue().getRecipeStepList().add(new RecipeStep());
        recipeStepListData.postValue(recipe.getValue().getRecipeStepList());

    }



    public void addMaterial(){
        recipe.getValue().getRecipeMaterialList().add(new RecipeMaterial());
        recipeMaterialListData.postValue(recipe.getValue().getRecipeMaterialList());
    }


    public void addStep(){
        recipe.getValue().getRecipeStepList().add(new RecipeStep());
        recipeStepListData.postValue(recipe.getValue().getRecipeStepList());
    }



    public MutableLiveData<Boolean> canEditMaterial = new MutableLiveData(false);
    public MutableLiveData<Boolean> canEditStep = new MutableLiveData(false);

    public void editMaterial(){
        canEditMaterial.postValue(!canEditMaterial.getValue());
    }

    public void editStep(){
        canEditStep.postValue(!canEditStep.getValue());
    }

    public void publish(){
        //封面不能为空
        if(TextUtils.isEmpty(recipe.getValue().getCover())){
            ToastyUtils.show(R.string.text_publish_recipe_cant_empty_cover);return;
        }
        //食材不能为空
        if(recipe.getValue().getRecipeMaterialList().size()<1){
            ToastyUtils.show(R.string.text_publish_recipe_cant_empty_material);return;
        }
        //食材名和用量不能为空

        for (RecipeMaterial material:recipe.getValue().getRecipeMaterialList()){
            if(TextUtils.isEmpty(material.getMaterialName())){
                ToastyUtils.show(String.format(
                        BaseContext.getInstance().getString(R.string.text_publish_recipe_cant_empty_material_name),
                        material.getOrderIndex()+""
                ));return;
            }
            if(TextUtils.isEmpty(material.getDosage())){
                ToastyUtils.show(String.format(
                        BaseContext.getInstance().getString(R.string.text_publish_recipe_cant_empty_material_dosage),
                        material.getOrderIndex()+""
                ));return;
            }
        }

        //步骤不能为空
        if(recipe.getValue().getRecipeStepList().size()<1){
            ToastyUtils.show(R.string.text_publish_recipe_cant_empty_material);return;
        }

        //步骤内容不能为空
        for (RecipeStep step: recipe.getValue().getRecipeStepList()){
            if(TextUtils.isEmpty(step.getStepContent())){
                ToastyUtils.show(String.format(
                        BaseContext.getInstance().getString(R.string.text_publish_recipe_cant_empty_step_content),
                        step.getOrderIndex()+""
                ));return;
            }
        }


        Call<RtnResult> publishCall;
        if (TextUtils.isEmpty(recipe.getValue().getRecipeId())){
            publishCall = httpRecipeApi.publish(recipe.getValue());
        }else {
            publishCall = httpRecipeApi.updateMyRecipe(recipe.getValue());
        }

        showLoading(R.string.text_publishing);
        publishCall.enqueue(new HttpCallback(){
            @Override
            public void onSuccess(Object data) {
                ToastyUtils.show(R.string.text_success);
                uiChange.publishSuccess.postValue(recipe.getValue().getRecipeId());
            }

            @Override
            public void finallyCall() {
                closeLoading();
            }
        });

    }

    public ObservableUIChange uiChange =new ObservableUIChange();

    class ObservableUIChange{
        MutableLiveData<String> publishSuccess=new MutableLiveData<>();

    }


}
