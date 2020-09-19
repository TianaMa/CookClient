package com.demo.cook.ui.like;

import androidx.lifecycle.ViewModelProvider;

import com.demo.baselib.design.BaseFragment;
import com.demo.cook.R;
import com.demo.cook.databinding.FragmentLikeBinding;

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
}