package com.demo.cook.ui.classes.sort;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.demo.baselib.adapter.MyPagerAdapter;
import com.demo.cook.R;
import com.demo.cook.databinding.ActivityRecipeListBinding;
import com.demo.cook.ui.recipe.fragment.RecipeListFragment;
import com.demo.cook.ui.recipe.model.data.request.QueryRecipeParams;

public class RecipeListActivity extends AppCompatActivity {

    private static final String EXTRA_SORT_ID = "sortId";
    private static final String EXTRA_SORT_NAME = "sortName";


    public static void actionStart(Context context,String sortId, String sortName){
        Intent intent = new Intent(context,RecipeListActivity.class);
        intent.putExtra(EXTRA_SORT_ID,sortId);
        intent.putExtra(EXTRA_SORT_NAME,sortName);
        context.startActivity(intent);

    }


    ActivityRecipeListBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding= DataBindingUtil.setContentView(this,R.layout.activity_recipe_list);

        mBinding.ivRecipeListClose.setOnClickListener(v -> super.onBackPressed());
        mBinding.tvRecipeListTitle.setText(getIntent().getStringExtra(EXTRA_SORT_NAME));

        String sortId = getIntent().getStringExtra(EXTRA_SORT_ID);
        final MyPagerAdapter adapter=new MyPagerAdapter(getSupportFragmentManager());
        adapter.add(getString(R.string.text_order_praise), RecipeListFragment.newInstance().setParams(new MutableLiveData(new QueryRecipeParams(sortId,QueryRecipeParams.Order.praise))));
        adapter.add(getString(R.string.text_order_collect), RecipeListFragment.newInstance().setParams(new MutableLiveData(new QueryRecipeParams(sortId,QueryRecipeParams.Order.collect))));
        adapter.add(getString(R.string.text_order_new), RecipeListFragment.newInstance().setParams(new MutableLiveData(new QueryRecipeParams(sortId,QueryRecipeParams.Order.time))));

        mBinding.vpRecipe.setAdapter(adapter);
        mBinding.tabRecipe.setupWithViewPager(mBinding.vpRecipe);

    }


}