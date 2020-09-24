package com.demo.cook.ui.home.recommend;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.demo.baselib.adapter.CmnRcvAdapter;
import com.demo.baselib.design.BaseFragment;
import com.demo.cook.R;
import com.demo.cook.base.local.Storage;
import com.demo.cook.databinding.FragmentRecommendBinding;
import com.demo.cook.databinding.ItemLayoutProductBinding;
import com.demo.cook.databinding.ItemLayoutProductImageBinding;
import com.demo.cook.ui.publish.product.model.data.ProductDetails;
import com.demo.cook.utils.LoginVerifyUtils;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.Arrays;
import java.util.List;

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


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CmnRcvAdapter<ProductDetails> adapter = new CmnRcvAdapter<ProductDetails>(this,R.layout.item_layout_product,mViewModel.productListData) {
            @Override
            public void convert(CmnViewHolder holder, ProductDetails productDetails, int position) {
                ItemLayoutProductBinding productBinding = DataBindingUtil.bind(holder.itemView);
                productBinding.setProductDetail(productDetails);
                productBinding.setUser(Storage.getUserInfo());

                productBinding.tvPraise.setOnClickListener(v -> {
                    LoginVerifyUtils.verifyAccount(() -> {
                        if (((CheckBox) v).isChecked()){
                            mViewModel.addPraise(productDetails);
                        }else {
                            mViewModel.cancelPraise(productDetails);
                        }
                    });
                });

                productBinding.tvCollect.setOnClickListener(v -> {
                    LoginVerifyUtils.verifyAccount(() -> {
                        if (((CheckBox) v).isChecked()){
                            mViewModel.addCollect(productDetails);
                        }else {
                            mViewModel.cancelCollect(productDetails);
                        }
                    });
                });

                productBinding.tvCollect.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    Log.e("tvCollect","=="+isChecked);
                });

                List<String> imageList = Arrays.asList(productDetails.getImages().split(","));

                productBinding.rcyProductImageList.setAdapter(new CmnRcvAdapter<String>(R.layout.item_layout_product_image,imageList) {
                    @Override
                    public void convert(CmnViewHolder holder, String s, int position) {
                        ItemLayoutProductImageBinding imageBinding = DataBindingUtil.bind(holder.itemView);
                        imageBinding.setImagePath(s);
                    }
                });
            }
        };
        mDataBinding.rcvRecommend.setAdapter(adapter);

        mDataBinding.rflRecommend.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mViewModel.getProductDetails();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mViewModel.getProductDetails();
            }
        });

        mViewModel.pageInfoData.observe(getViewLifecycleOwner(), productDetailsPageInfo -> {
            mDataBinding.rflRecommend.setNoMoreData(productDetailsPageInfo.isIsLastPage());
            mDataBinding.rflRecommend.closeHeaderOrFooter();
        });

        mViewModel.getProductDetails();
    }

}