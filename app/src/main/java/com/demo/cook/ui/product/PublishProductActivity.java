package com.demo.cook.ui.product;


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
import com.demo.cook.databinding.ItemLayoutProductTagBinding;
import com.demo.cook.databinding.ItemLayoutPublishProductImageBinding;
import com.demo.cook.ui.product.model.data.ProductTag;
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

        mDataBinding.rcvProductImageList.setAdapter(new CmnRcvAdapter<String>(this,R.layout.item_layout_publish_product_image,mViewModel.productImageListData) {
            @Override
            public void convert(CmnViewHolder holder, String productImage, int position) {
                ItemLayoutPublishProductImageBinding productImageBinding = DataBindingUtil.bind(holder.itemView);
                productImageBinding.setProductImage(productImage);
                productImageBinding.ivDeleteImage.setOnClickListener(v -> {
                    List<String> imageList = mViewModel.productImageListData.getValue();
                    imageList.remove(position);
                    mViewModel.productImageListData.postValue(imageList);
                });
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
                            List<String> productImageList = mViewModel.productImageListData.getValue();
                            for (String path:pathList){
                                productImageList.add(path);
                            }
                            mViewModel.productImageListData.postValue(productImageList);
                        }
                )
        );

        mViewModel.getProductTagList();

        mDataBinding.rcvProductTags.setAdapter(new CmnRcvAdapter<ProductTag>(this,R.layout.item_layout_product_tag,mViewModel.productTagsData) {
            @Override
            public void convert(CmnViewHolder holder, ProductTag tag, int position) {
                ItemLayoutProductTagBinding tagBinding = DataBindingUtil.bind(holder.itemView);
                tagBinding.setTag(tag);
                holder.itemView.setOnClickListener(v -> {
                    if(tag.isSelected()){
                        tag.setSelected(false);
                        mDataBinding.tvProductTagName.setText(null);
                        mViewModel.productData.getValue().setTagId(null);
                    }else {
                        for (ProductTag tagItem:mViewModel.productTagsData.getValue()){
                            tagItem.setSelected(false);
                        }
                        tag.setSelected(true);
                        mDataBinding.tvProductTagName.setText(tag.getTagName());
                        mViewModel.productData.getValue().setTagId(tag.getTagId());
                    }
                });
            }
        });



    }
}