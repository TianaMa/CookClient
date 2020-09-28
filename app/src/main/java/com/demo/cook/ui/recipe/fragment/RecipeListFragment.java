package com.demo.cook.ui.recipe.fragment;

import android.os.Bundle;
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
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

public class RecipeListFragment extends BaseFragment<FragmentRecipeListBinding,RecipeListViewModel> {

    private static MutableLiveData<QueryRecipeParams> recipeParamsData;
    private static boolean canRefresh = true;

    public static RecipeListFragment newInstance(boolean enableRefresh,MutableLiveData<QueryRecipeParams> recipeParams) {
        recipeParamsData=recipeParams;
        canRefresh = enableRefresh;
        return new RecipeListFragment();
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
                mViewModel.getRecipeList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mViewModel.recipeParams.setPageNum(1);
                mViewModel.getRecipeList();
            }
        });

        mViewModel.finishAndHaveMore.observe(getViewLifecycleOwner(), aBoolean -> {
            mDataBinding.rflRecipeList.setNoMoreData(aBoolean);
            mDataBinding.rflRecipeList.closeHeaderOrFooter();
        });

        recipeParamsData.observe(getViewLifecycleOwner(), productParams -> {
            mViewModel.recipeParams=productParams;
            mViewModel.getRecipeList();
        });
    }

}