package com.demo.cook.ui.recipe;

import androidx.lifecycle.MutableLiveData;

import com.demo.baselib.design.BaseViewModel;
import com.demo.cook.ui.recipe.model.data.RecipeDetails;

public class RecipeDetailsViewModel extends BaseViewModel {

    public MutableLiveData<RecipeDetails> recipe = new MutableLiveData();



}
