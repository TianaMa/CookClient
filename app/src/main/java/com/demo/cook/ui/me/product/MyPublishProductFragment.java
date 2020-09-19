package com.demo.cook.ui.me.product;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.demo.baselib.adapter.CmnRcvAdapter;
import com.demo.baselib.design.BaseFragment;
import com.demo.cook.R;
import com.demo.cook.databinding.FragmentMyPublishProductBinding;
import com.demo.cook.ui.publish.product.PublishProductActivity;
import com.demo.cook.ui.publish.product.model.data.MyPublishProduct;
import com.demo.cook.utils.RouteUtils;


public class MyPublishProductFragment extends BaseFragment<FragmentMyPublishProductBinding, MyPublishProductViewModel> {


    public static MyPublishProductFragment newInstance() {
        return new MyPublishProductFragment();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_my_publish_product;
    }

    @Override
    protected MyPublishProductViewModel getViewModel() {
        return new ViewModelProvider(this).get(MyPublishProductViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        CmnRcvAdapter<MyPublishProduct> adapter = new CmnRcvAdapter<MyPublishProduct>(
                this,R.layout.item_layout_my_publish_product,mViewModel.myPublishProductListData
        ) {
            @Override
            public void convert(CmnViewHolder holder, MyPublishProduct myPublishProduct, int position) {

            }
        };

        //空数据视图逻辑
        View emptyView = LayoutInflater.from(getContext()).inflate(R.layout.empty_layout_my_publish,null);
        ((TextView)emptyView.findViewById(R.id.tvEmptyMyPublishToast)).setText(R.string.text_my_publish_product_empty_toast);
        ((TextView)emptyView.findViewById(R.id.tvEmptyMyPublishGo)).setText(R.string.text_my_publish_product_go);
        emptyView.findViewById(R.id.tvEmptyMyPublishGo).setOnClickListener(v -> {

            RouteUtils.jumpNeedAccount(() -> {
                startActivity(new Intent(getContext(), PublishProductActivity.class));
            });
        });
        adapter.setEmptyView(emptyView);

        mDataBinding.rcvMyPublishProduct.setAdapter(adapter);
    }


}