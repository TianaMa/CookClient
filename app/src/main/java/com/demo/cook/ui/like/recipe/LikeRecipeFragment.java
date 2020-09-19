package com.demo.cook.ui.like.recipe;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.baselib.design.BaseFragment;
import com.demo.cook.R;
import com.demo.cook.databinding.FragmentLikeRecipeBinding;

public class LikeRecipeFragment extends BaseFragment<FragmentLikeRecipeBinding,LikeRecipeViewModel> {


    public static LikeRecipeFragment newInstance() {
        return new LikeRecipeFragment();
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_like_recipe;
    }

    @Override
    protected LikeRecipeViewModel getViewModel() {
        return new ViewModelProvider(this).get(LikeRecipeViewModel.class);
    }


}