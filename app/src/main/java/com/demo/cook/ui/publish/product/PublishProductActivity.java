package com.demo.cook.ui.publish.product;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.demo.baselib.adapter.CmnRcvAdapter;
import com.demo.baselib.design.BaseActivity;
import com.demo.cook.R;
import com.demo.cook.base.http.QiNiuUtil;
import com.demo.cook.databinding.ActivityPublishProductBinding;
import com.demo.cook.databinding.ItemLayoutPublishProductImageBinding;
import com.demo.cook.ui.publish.product.model.data.ProductImage;
import com.demo.cook.utils.upload.UpLoadUtils;

import java.util.ArrayList;
import java.util.List;

public class PublishProductActivity extends BaseActivity<ActivityPublishProductBinding,PublishProductViewModel> {


    private static final String PRODUCT_ID = "productId";

    private static final String PRODUCT_IMAGE_PATH_LIST = "productImagePathList";



    public static void actionCreate(Context context, ArrayList<String> productImagePathList){
        Intent intent = new Intent(context, PublishProductActivity.class);
        intent.putStringArrayListExtra(PRODUCT_IMAGE_PATH_LIST,productImagePathList);
        context.startActivity(intent);
    }

    public static void actionEdit(Context context,String productId){
        Intent intent = new Intent(context,PublishProductActivity.class);
        intent.putExtra(PRODUCT_ID,productId);
        context.startActivity(intent);
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_publish_product;
    }

    @Override
    protected PublishProductViewModel getViewModel() {
        return new ViewModelProvider(this).get(PublishProductViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding.setMViewModel(mViewModel);

        String productId = getIntent().getStringExtra(PRODUCT_ID);

        if (TextUtils.isEmpty(productId)){
            ArrayList<String>  productImagePathList = getIntent().getStringArrayListExtra(PRODUCT_IMAGE_PATH_LIST);
            mViewModel.initProduct(productImagePathList);
        }else {
            mViewModel.getProductDetails(productId);
        }

        mDataBinding.ivPublishProductClose.setOnClickListener(v -> super.onBackPressed());

        mDataBinding.rcvProductImageList.setAdapter(new CmnRcvAdapter<ProductImage>(this,R.layout.item_layout_publish_product_image,mViewModel.productImageListData) {
            @Override
            public void convert(CmnViewHolder holder, ProductImage productImage, int position) {
                ItemLayoutPublishProductImageBinding productImageBinding = DataBindingUtil.bind(holder.itemView);
                productImageBinding.setProductImage(productImage);
                productImage.setOrderIndex(position+1);

            }
        });

        mViewModel.productImageListData.observe(this, productImages -> {
            mDataBinding.ivAddProductImage.setVisibility(productImages.size()>8? View.GONE:View.VISIBLE);
        });

        mDataBinding.ivAddProductImage.setOnClickListener(v -> 
                UpLoadUtils.upLoadMultiImage(this, 
                        QiNiuUtil.Prefix.IMAGE_RECIPE_PRODUCT,
                        9 - mViewModel.productImageListData.getValue().size(), 
                        pathList -> {
                            List<ProductImage> productImageList = mViewModel.productImageListData.getValue();
                            for (String path:pathList){
                                ProductImage productImage = new ProductImage(path);
                                productImageList.add(productImage);
                                mViewModel.productData.getValue().getProductImageList().add(productImage);
                            }
                            mViewModel.productImageListData.postValue(productImageList);
                        }
                )
        );




    }
}