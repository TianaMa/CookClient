package com.demo.cook.ui.classes;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.demo.baselib.adapter.CmnRcvAdapter;
import com.demo.baselib.design.BaseFragment;
import com.demo.cook.R;
import com.demo.cook.base.http.QiNiuUtil;
import com.demo.cook.databinding.FragmentClassesBinding;
import com.demo.cook.databinding.ItemLayoutBannerClassesBinding;
import com.demo.cook.databinding.ItemLayoutRecipeMenuBinding;
import com.demo.cook.ui.classes.model.data.RecipeSort;
import com.demo.cook.ui.classes.sort.RecipeListActivity;
import com.demo.cook.ui.product.fragment.ProductListFragment;
import com.demo.cook.ui.recipe.fragment.RecipeListFragment;
import com.demo.cook.ui.recipe.model.data.request.QueryRecipeParams;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.to.aboomy.pager2banner.IndicatorView;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ClassesFragment extends BaseFragment<FragmentClassesBinding, ClassesViewModel> {

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_classes;
    }

    @Override
    protected ClassesViewModel getViewModel() {
        return new ViewModelProvider(this).get(ClassesViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //Banner
        mDataBinding.bannerClasses.setIndicator(
                new IndicatorView(getContext()).setIndicatorColor(Color.WHITE).setIndicatorSelectorColor(Color.YELLOW)
        ).setAdapter(
                new CmnRcvAdapter<Integer>(R.layout.item_layout_banner_classes, new ArrayList<Integer>() {{
                    add(R.drawable.image_banner_1);add(R.drawable.image_banner_2);
                    add(R.drawable.image_banner_3);add(R.drawable.image_banner_4);
                    add(R.drawable.image_banner_5);
                }}) {
                    @Override
                    public void convert(CmnViewHolder holder, Integer integer, int position) {
                        ItemLayoutBannerClassesBinding binding = DataBindingUtil.bind(holder.itemView);
                        Glide.with(getContext())
                                .load(integer)
                                .apply(RequestOptions.bitmapTransform(new RoundedCorners(12)).override(300, 300))
                                .into(binding.ivBannerClasses);
                    }
                }
        );

        //中间菜单
        try {
            List<RecipeSort> recipeSortList = new Gson().fromJson(
                    new InputStreamReader(getContext().getAssets().open("recipeSortMenu.json"), "UTF-8"),
                    new TypeToken<List<RecipeSort>>() {
                    }.getType()
            );

            mDataBinding.rcvClassesSort.setAdapter(new CmnRcvAdapter<RecipeSort>(R.layout.item_layout_recipe_menu, recipeSortList) {
                @Override
                public void convert(CmnViewHolder holder, RecipeSort recipeSort, int position) {
                    ItemLayoutRecipeMenuBinding menuBinding = DataBindingUtil.bind(holder.itemView);
                    menuBinding.setSort(recipeSort);
                    holder.itemView.setOnClickListener(v -> {
                        RecipeListActivity.actionStart(v.getContext(),recipeSort.getSortId(),recipeSort.getSortName());
                    });
                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }

        //下面列表
        MutableLiveData<QueryRecipeParams> recipeParamsData = new MutableLiveData(new QueryRecipeParams(QueryRecipeParams.Order.praise));
        getChildFragmentManager().beginTransaction()
                .replace(R.id.flClassesRecommend, RecipeListFragment.newInstance(false,recipeParamsData))
                .commit();

    }
}