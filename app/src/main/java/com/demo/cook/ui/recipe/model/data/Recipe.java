package com.demo.cook.ui.recipe.model.data;


import java.util.ArrayList;

//发布和修改的参数
public class Recipe extends RecipeBase {


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

}