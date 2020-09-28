package com.demo.cook.ui.recipe.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.demo.baselib.adapter.CmnRcvAdapter;
import com.demo.baselib.design.BaseFragment;
import com.demo.cook.R;
import com.demo.cook.databinding.FragmentRecipeListBinding;
import com.demo.cook.databinding.ItemLayoutRecipeBinding;
import com.demo.cook.ui.recipe.RecipeDetailsActivity;
import com.demo.cook.ui.recipe.model.data.RecipeBrief;
import com.demo.cook.ui.recipe.model.data.request.QueryRecipeParams;
import com.google.gson.Gson;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

public class RecipeListFragment extends BaseFragment<FragmentRecipeListBinding,RecipeListViewModel> {

    private MutableLiveData<QueryRecipeParams> recipeParamsData;
    private boolean canRefresh = true;

    public static RecipeListFragment newInstance() {
        return new RecipeListFragment();
    }

    public RecipeListFragment setParams(MutableLiveData<QueryRecipeParams> recipeParamsData){
        this.recipeParamsData  = recipeParamsData;
        return this;
    }

    public RecipeListFragment setCanRefresh(boolean canRefresh){
        this.canRefresh = canRefresh;
        return this;
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_recipe_list;
    }

    @Override
    protected RecipeListViewModel getViewModel() {
        return new ViewModelProvider(this).get(RecipeListViewModel.class);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CmnRcvAdapter<RecipeBrief> adapter= new CmnRcvAdapter<RecipeBrief>(
                this, R.layout.item_layout_recipe,mViewModel.recipeListData
        ) {
            @Override
            public void convert(CmnViewHolder holder, RecipeBrief recipeBrief, int position) {
                ItemLayoutRecipeBinding myPublishRecipeBinding = DataBindingUtil.bind(holder.itemView);
                myPublishRecipeBinding.setRecipe(recipeBrief);
                holder.itemView.setOnClickListener(v -> RecipeDetailsActivity.actionStart(v.getContext(),recipeBrief.getRecipeId()));
            }
        };
        //空数据视图逻辑
        View emptyView = LayoutInflater.from(getContext()).inflate(R.layout.empty_layout_no_data,null);

        adapter.setEmptyView(emptyView);

        mDataBinding.rcvRecipeList.setAdapter(adapter);

        mDataBinding.rflRecipeList.setEnableRefresh(canRefresh);
        mDataBinding.rflRecipeList.setEnableLoadMore(canRefresh);
        mDataBinding.rflRecipeList.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mViewModel.getRecipeList(recipeParamsData.getValue());
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                recipeParamsData.getValue().setPageNum(1);
                mViewModel.getRecipeList(recipeParamsData.getValue());
            }
        });

        mViewModel.finishAndHaveMore.observe(getViewLifecycleOwner(), aBoolean -> {
            mDataBinding.rflRecipeList.setNoMoreData(aBoolean);
            mDataBinding.rflRecipeList.closeHeaderOrFooter();
        });

        recipeParamsData.observe(getViewLifecycleOwner(), productParams -> {
            mViewModel.getRecipeList(recipeParamsData.getValue());
        });
    }

}