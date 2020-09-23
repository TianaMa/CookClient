package com.demo.cook.ui.me.recipe;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.demo.baselib.adapter.CmnRcvAdapter;
import com.demo.baselib.design.BaseFragment;
import com.demo.cook.R;
import com.demo.cook.base.event.BusEvent;
import com.demo.cook.databinding.FragmentMyPublishRecipeBinding;
import com.demo.cook.databinding.ItemLayoutMyPublishRecipeBinding;
import com.demo.cook.ui.publish.recipe.PublishRecipeActivity;
import com.demo.cook.ui.publish.recipe.PublishRecipeNameActivity;
import com.demo.cook.ui.publish.recipe.model.data.MyPublishRecipe;
import com.demo.cook.utils.LoginVerifyUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MyPublishRecipeFragment extends BaseFragment<FragmentMyPublishRecipeBinding,MyPublishRecipeViewModel> {


    public static MyPublishRecipeFragment newInstance() {
        return new MyPublishRecipeFragment();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_my_publish_recipe;
    }

    @Override
    protected MyPublishRecipeViewModel getViewModel() {
        return new ViewModelProvider(this).get(MyPublishRecipeViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerEventBus=true;
        CmnRcvAdapter<MyPublishRecipe> adapter= new CmnRcvAdapter<MyPublishRecipe>(
                this, R.layout.item_layout_my_publish_recipe,mViewModel.myPublishRecipeListData
        ) {
            @Override
            public void convert(CmnViewHolder holder, MyPublishRecipe myPublishRecipe, int position) {
                ItemLayoutMyPublishRecipeBinding myPublishRecipeBinding = DataBindingUtil.bind(holder.itemView);
                myPublishRecipeBinding.setRecipe(myPublishRecipe);
                myPublishRecipeBinding.ivEditRecipe.setOnClickListener(v -> {
                    PublishRecipeActivity.actionEdit(v.getContext(),myPublishRecipe.getRecipeId());
                });
            }
        };
        //空数据视图逻辑
        View emptyView = LayoutInflater.from(getContext()).inflate(R.layout.empty_layout_my_publish,null);
        ((TextView)emptyView.findViewById(R.id.tvEmptyMyPublishToast)).setText(R.string.text_publish_slogan);
        ((TextView)emptyView.findViewById(R.id.tvEmptyMyPublishGo)).setText(R.string.text_my_publish_recipe_go);
        emptyView.findViewById(R.id.tvEmptyMyPublishGo).setOnClickListener(v -> {
            LoginVerifyUtils.jumpNeedAccount(()->startActivity(new Intent(getContext(), PublishRecipeNameActivity.class)));
        });
        adapter.setEmptyView(emptyView);

        mDataBinding.rcvMyPublishRecipe.setAdapter(adapter);

        mViewModel.queryMuPublishRecipe();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMsg(BusEvent.PublishRecipeSuccess msg) {

        Log.e("MyPublishRecipe","onEventMsg===");
        mViewModel.queryMuPublishRecipe();
    }

}