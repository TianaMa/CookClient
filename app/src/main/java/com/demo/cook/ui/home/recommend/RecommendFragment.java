package com.demo.cook.ui.home.recommend;

import androidx.lifecycle.ViewModelProvider;

import com.demo.baselib.design.BaseFragment;
import com.demo.cook.R;
import com.demo.cook.databinding.FragmentRecommendBinding;

public class RecommendFragment extends BaseFragment<FragmentRecommendBinding,RecommendViewModel> {

    public static RecommendFragment newInstance() {
        return new RecommendFragment();
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_recommend;
    }

    @Override
    protected RecommendViewModel getViewModel() {
        return new ViewModelProvider(this).get(RecommendViewModel.class);
    }


}