package com.demo.cook.ui.like;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.demo.baselib.adapter.MyPagerAdapter;
import com.demo.baselib.design.BaseFragment;
import com.demo.cook.R;
import com.demo.cook.base.local.Storage;
import com.demo.cook.databinding.FragmentLikeBinding;
import com.demo.cook.ui.me.product.MyPublishProductFragment;
import com.demo.cook.ui.me.recipe.MyPublishRecipeFragment;
import com.demo.cook.ui.product.fragment.ProductListFragment;
import com.demo.cook.ui.product.model.data.request.QueryProductParams;
import com.demo.cook.ui.recipe.fragment.RecipeListFragment;
import com.demo.cook.ui.recipe.model.data.request.QueryRecipeParams;

/**
 */
public class LikeFragment extends BaseFragment<FragmentLikeBinding,LikeViewModel> {


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_like;
    }

    @Override
    protected LikeViewModel getViewModel() {
        return new ViewModelProvider(this).get(LikeViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final MyPagerAdapter adapter=new MyPagerAdapter(getChildFragmentManager());
        
        QueryRecipeParams recipeParams = new QueryRecipeParams();
        recipeParams.setCollector(Storage.getUserInfo().getUsername());
        MutableLiveData recipeParamsData = new MutableLiveData(recipeParams);
        adapter.add(getString(R.string.text_recipe), RecipeListFragment.newInstance().setParams(recipeParamsData));
        
        QueryProductParams productParams = new QueryProductParams();
        productParams.setCollector(Storage.getUserInfo().getUsername());
        MutableLiveData productParamsData = new MutableLiveData(productParams);
        adapter.add(getString(R.string.text_product), ProductListFragment.newInstance().setParams(productParamsData));

        mDataBinding.vpLike.setAdapter(adapter);
        mDataBinding.tabLike.setupWithViewPager(mDataBinding.vpLike);

        mDataBinding.tvLikeSearch.setOnClickListener(v -> {
            String searchText=mDataBinding.etLikeSearchText.getText().toString();
            if(mDataBinding.tabLike.getSelectedTabPosition()==0){
                recipeParams.setSearchText(searchText);
                recipeParamsData.postValue(recipeParams);
            }else {
                productParams.setSearchText(searchText);
                productParamsData.postValue(productParams);
            }
        });

    }
}