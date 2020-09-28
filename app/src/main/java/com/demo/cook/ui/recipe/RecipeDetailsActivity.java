package com.demo.cook.ui.recipe;


import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.demo.baselib.design.BaseActivity;
import com.demo.cook.R;
import com.demo.cook.databinding.ActivityRecipeDetailsBinding;

public class RecipeDetailsActivity extends BaseActivity<ActivityRecipeDetailsBinding,RecipeDetailsViewModel> {



    @Override
    protected int getLayoutRes() {
        return R.layout.activity_recipe_details;
    }

    @Override
    protected RecipeDetailsViewModel getViewModel() {
        return new ViewModelProvider(this).get(RecipeDetailsViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}