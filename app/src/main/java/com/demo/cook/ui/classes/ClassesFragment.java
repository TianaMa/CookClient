package com.demo.cook.ui.classes;

import androidx.lifecycle.ViewModelProvider;

import com.demo.baselib.design.BaseFragment;
import com.demo.cook.R;
import com.demo.cook.databinding.FragmentClassesBinding;

public class ClassesFragment extends BaseFragment<FragmentClassesBinding,ClassesViewModel> {

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_classes;
    }

    @Override
    protected ClassesViewModel getViewModel() {
        return new ViewModelProvider(this).get(ClassesViewModel.class);
    }
}