package com.demo.cook.ui.classes.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.demo.cook.R;
import com.demo.cook.base.local.Storage;
import com.demo.cook.databinding.ActivitySearchRecipeBinding;
import com.demo.cook.ui.recipe.fragment.RecipeListFragment;
import com.demo.cook.ui.recipe.model.data.request.QueryRecipeParams;

public class SearchRecipeActivity extends AppCompatActivity {


    public static void actionStart(Context context,boolean searchOwn){
        Intent intent = new Intent(context,SearchRecipeActivity.class);
        intent.putExtra("searchOwn",searchOwn);
        context.startActivity(intent);
    }


    ActivitySearchRecipeBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding= DataBindingUtil.setContentView(this,R.layout.activity_search_recipe);

        boolean searchOwn = getIntent().getBooleanExtra("searchOwn",false);

        MutableLiveData<QueryRecipeParams> paramsData = new MutableLiveData(searchOwn?new QueryRecipeParams(Storage.getUserInfo().getUsername()):new QueryRecipeParams());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.flSearchRecipe, RecipeListFragment.newInstance().setParams(paramsData))
                .commit();

        mBinding.ivSearchBack.setOnClickListener(v -> super.onBackPressed());

        mBinding.tvSearchRecipe.setOnClickListener(v -> {
            QueryRecipeParams params = paramsData.getValue();
            params.setSearchText(mBinding.etSearchRecipe.getText().toString());
            paramsData.postValue(params);

        });

    }
}