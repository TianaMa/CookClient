package com.demo.cook.ui.recipe;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.demo.baselib.adapter.CmnRcvAdapter;
import com.demo.baselib.design.BaseActivity;
import com.demo.baselib.view.RcvDragVerticalCallback;
import com.demo.cook.R;
import com.demo.cook.base.http.QiNiuUtil;
import com.demo.cook.databinding.ActivityRecipeDetailsBinding;
import com.demo.cook.databinding.ItemLayoutRecipeDetailsMaterialBinding;
import com.demo.cook.databinding.ItemLayoutRecipeDetailsStepBinding;
import com.demo.cook.databinding.ItemLayoutRecipeMaterialBinding;
import com.demo.cook.databinding.ItemLayoutRecipeStepBinding;
import com.demo.cook.ui.recipe.model.data.RecipeDetails;
import com.demo.cook.ui.recipe.model.data.RecipeMaterial;
import com.demo.cook.ui.recipe.model.data.RecipeStep;
import com.demo.cook.ui.recipe.publish.PublishRecipeActivity;
import com.demo.cook.utils.upload.UpLoadUtils;

public class RecipeDetailsActivity extends BaseActivity<ActivityRecipeDetailsBinding,RecipeDetailsViewModel> {

    private static final String EXTRA_RECIPE_ID= "recipeId";

    public static void actionStart(Context context,String recipeId){
        Intent intent = new Intent(context, RecipeDetailsActivity.class);
        intent.putExtra(EXTRA_RECIPE_ID,recipeId);
        context.startActivity(intent);
    }


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

        mDataBinding.setMViewModel(mViewModel);

        String recipeId = getIntent().getStringExtra(EXTRA_RECIPE_ID);
        mViewModel.getRecipeDetails(recipeId);

        //食材adapter 及拖动
        mDataBinding.rcvPublishRecipeMaterial.setAdapter(new CmnRcvAdapter<RecipeMaterial>(
                this, R.layout.item_layout_recipe_details_material,
                mViewModel.recipeMaterialListData
        ) {
            @Override
            public void convert(CmnViewHolder holder, RecipeMaterial recipeMaterial, int position) {
                recipeMaterial.setOrderIndex(position+1);
                ItemLayoutRecipeDetailsMaterialBinding materialBinding = DataBindingUtil.bind(holder.itemView);
                materialBinding.setMaterial(recipeMaterial);
            }
        });

        //步骤adapter 及拖动
        mDataBinding.rcvPublishRecipeStep.setAdapter(new CmnRcvAdapter<RecipeStep>(
                this, R.layout.item_layout_recipe_details_step,
                mViewModel.recipeStepListData
        ) {
            @Override
            public void convert(CmnViewHolder holder, RecipeStep recipeStep, int position) {
                recipeStep.setOrderIndex(position+1);
                ItemLayoutRecipeDetailsStepBinding recipeStepBinding = DataBindingUtil.bind(holder.itemView);
                recipeStepBinding.setStep(recipeStep);
            }
        });


    }
}