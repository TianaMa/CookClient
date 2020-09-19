package com.demo.cook.ui.home.friends;

import androidx.lifecycle.ViewModelProvider;

import com.demo.baselib.design.BaseFragment;
import com.demo.cook.R;
import com.demo.cook.databinding.FragmentFriendsBinding;

public class FriendsFragment extends BaseFragment<FragmentFriendsBinding,FriendsViewModel> {

    public static FriendsFragment newInstance() {
        return new FriendsFragment();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_friends;
    }

    @Override
    protected FriendsViewModel getViewModel() {
        return new ViewModelProvider(this).get(FriendsViewModel.class);
    }


}