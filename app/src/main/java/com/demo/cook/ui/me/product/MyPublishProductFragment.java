package com.demo.cook.ui.me.product;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

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
import com.demo.cook.base.http.QiNiuUtil;
import com.demo.cook.databinding.FragmentMyPublishProductBinding;
import com.demo.cook.databinding.ItemLayoutProductImageBinding;
import com.demo.cook.ui.publish.product.PublishProductActivity;
import com.demo.cook.ui.publish.product.model.data.ProductDetails;
import com.demo.cook.utils.LoginVerifyUtils;
import com.demo.cook.utils.upload.UpLoadUtils;
import com.google.gson.Gson;


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

        CmnRcvAdapter<ProductDetails> adapter = new CmnRcvAdapter<ProductDetails>(
                this,R.layout.item_layout_product_image,mViewModel.myPublishProductListData
        ) {
            @Override
            public void convert(CmnViewHolder holder, ProductDetails myPublishProduct, int position) {
                ItemLayoutProductImageBinding imageBinding = DataBindingUtil.bind(holder.itemView);
                imageBinding.setImagePath(myPublishProduct.getImages().split(",")[0]);
                holder.itemView.setOnClickListener(v -> {

                });
            }
        };

        //空数据视图逻辑
        View emptyView = LayoutInflater.from(getContext()).inflate(R.layout.empty_layout_my_publish,null);
        ((TextView)emptyView.findViewById(R.id.tvEmptyMyPublishToast)).setText(R.string.text_my_publish_product_empty_toast);
        ((TextView)emptyView.findViewById(R.id.tvEmptyMyPublishGo)).setText(R.string.text_my_publish_product_go);
        emptyView.findViewById(R.id.tvEmptyMyPublishGo).setOnClickListener(v -> {

            LoginVerifyUtils.verifyAccount(() -> {
                UpLoadUtils.upLoadMultiImage(getActivity(), QiNiuUtil.Prefix.IMAGE_RECIPE_PRODUCT,9, pathList -> {
                    Log.e("upLoadMultiImage",new Gson().toJson(pathList));
                    PublishProductActivity.actionCreate(getContext(),pathList);
                });
            });
        });
        adapter.setEmptyView(emptyView);

        mDataBinding.rcvMyPublishProduct.setAdapter(adapter);

        mViewModel.queryMyProductList();
    }


}