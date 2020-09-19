package com.demo.cook.ui.like.product;

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
import com.demo.cook.databinding.FragmentLikeProductBinding;

public class LikeProductFragment extends BaseFragment<FragmentLikeProductBinding,LikeProductViewModel> {

    public static LikeProductFragment newInstance() {
        return new LikeProductFragment();
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_like_product;
    }

    @Override
    protected LikeProductViewModel getViewModel() {
        return new ViewModelProvider(this).get(LikeProductViewModel.class);
    }


}