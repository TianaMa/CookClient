package com.demo.cook.ui.publish.recipe.model.data;

import androidx.databinding.BaseObservable;

import com.demo.cook.base.local.Storage;

import java.util.ArrayList;

public class Recipe extends BaseObservable {


    private String recipeId;

    private String issuer = Storage.getUser().getUsername();

    private String recipeName;

    private String cover;

    private String recipeDescribe;

    private String tips;

    private ArrayList<RecipeMaterial> recipeMaterialList;

    private ArrayList<RecipeStep> recipeStepList;

    public ArrayList<RecipeMaterial> getRecipeMaterialList() {
        return recipeMaterialList;
    }

    public void setRecipeMaterialList(ArrayList<RecipeMaterial> recipeMaterialList) {
        this.recipeMaterialList = recipeMaterialList;
    }

    public ArrayList<RecipeStep> getRecipeStepList() {
        return recipeStepList;
    }

    public void setRecipeStepList(ArrayList<RecipeStep> recipeStepList) {
        this.recipeStepList = recipeStepList;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId == null ? null : recipeId.trim();
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer == null ? null : issuer.trim();
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName == null ? null : recipeName.trim();
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover == null ? null : cover.trim();
        notifyChange();
    }

    public String getRecipeDescribe() {
        return recipeDescribe;
    }

    public void setRecipeDescribe(String recipeDescribe) {
        this.recipeDescribe = recipeDescribe == null ? null : recipeDescribe.trim();
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips == null ? null : tips.trim();
    }
}