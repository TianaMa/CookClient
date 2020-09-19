package com.demo.cook.ui.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.demo.baselib.adapter.MyPagerAdapter;
import com.demo.baselib.design.BaseFragment;
import com.demo.cook.R;
import com.demo.cook.base.http.QiNiuUtil;
import com.demo.cook.base.local.Storage;
import com.demo.cook.databinding.FragmentMeBinding;
import com.demo.cook.ui.me.recipe.MyPublishRecipeFragment;
import com.demo.cook.ui.me.product.MyPublishProductFragment;
import com.demo.cook.ui.publish.type.PublishTypeActivity;
import com.demo.cook.ui.user.login.LoginActivity;
import com.demo.cook.utils.RouteUtils;
import com.demo.cook.utils.upload.UpLoadUtils;

public class MeFragment extends BaseFragment<FragmentMeBinding,MeViewModel> {

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_me;
    }

    @Override
    protected MeViewModel getViewModel() {
        return new ViewModelProvider(this).get(MeViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDataBinding.setMViewModel(mViewModel);
        mDataBinding.tvMinePublish.setOnClickListener(v -> {
            RouteUtils.jumpNeedAccount(() -> startActivity(new Intent(MeFragment.this.getContext(), PublishTypeActivity.class)));
        });

        mDataBinding.ivMeSetting.setOnClickListener(v -> {
            Storage.setUser(null);
            startActivity(new Intent(this.getContext(), LoginActivity.class));
        });

        final MyPagerAdapter adapter=new MyPagerAdapter(getChildFragmentManager());
        adapter.add(getString(R.string.text_recipe),MyPublishRecipeFragment.newInstance());
        adapter.add(getString(R.string.text_product), MyPublishProductFragment.newInstance());

        mDataBinding.vpMe.setAdapter(adapter);
        mDataBinding.tabMe.setupWithViewPager(mDataBinding.vpMe);

        mDataBinding.ivMeHead.setOnClickListener(
                v -> UpLoadUtils.upLoadSingleImage(
                        getActivity(),
                        QiNiuUtil.Prefix.IMAGE_HEAD,
                        path -> mViewModel.updateHeadImage(path)
                )
        );
    }


}