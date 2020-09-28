package com.demo.cook.ui.product.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.demo.baselib.adapter.CmnRcvAdapter;
import com.demo.baselib.design.BaseFragment;
import com.demo.cook.R;
import com.demo.cook.base.local.Storage;
import com.demo.cook.databinding.FragmentProductListBinding;
import com.demo.cook.databinding.ItemLayoutProductBinding;
import com.demo.cook.databinding.ItemLayoutProductImageBinding;
import com.demo.cook.ui.product.model.data.ProductDetails;
import com.demo.cook.ui.product.model.data.request.QueryProductParams;
import com.demo.cook.utils.LoginVerifyUtils;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.Arrays;
import java.util.List;

public class ProductListFragment extends BaseFragment<FragmentProductListBinding, ProductListViewModel> {

    private ProductListFragment(){

    }

    private static MutableLiveData<QueryProductParams> productParamsData ;
    public static ProductListFragment newInstance(MutableLiveData<QueryProductParams> productParams) {
        productParamsData = productParams;
        return new ProductListFragment();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_product_list;
    }

    @Override
    protected ProductListViewModel getViewModel() {
        return new ViewModelProvider(this).get(ProductListViewModel.class);
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
        //空数据视图逻辑
        View emptyView = LayoutInflater.from(getContext()).inflate(R.layout.empty_layout_no_data,null);
        adapter.setEmptyView(emptyView);

        mDataBinding.rcvProductList.setAdapter(adapter);

        mDataBinding.rflProductList.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mViewModel.getProductList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mViewModel.productParams.setPageNum(1);
                mViewModel.getProductList();
            }
        });

        mViewModel.finishAndHaveMore.observe(getViewLifecycleOwner(), aBoolean -> {
            mDataBinding.rflProductList.setNoMoreData(aBoolean);
            mDataBinding.rflProductList.closeHeaderOrFooter();
        });

        productParamsData.observe(getViewLifecycleOwner(), productParams -> {
            mViewModel.productParams=productParams;
            mViewModel.getProductList();
        });

    }

}