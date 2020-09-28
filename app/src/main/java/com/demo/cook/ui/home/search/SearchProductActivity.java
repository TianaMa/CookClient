package com.demo.cook.ui.home.search;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.demo.baselib.adapter.CmnRcvAdapter;
import com.demo.baselib.design.BaseActivity;
import com.demo.cook.R;
import com.demo.cook.databinding.ActivitySearchProductBinding;
import com.demo.cook.databinding.ItemLayoutProductTagSelectBinding;
import com.demo.cook.ui.product.fragment.ProductListFragment;
import com.demo.cook.ui.product.model.data.ProductTag;
import com.demo.cook.ui.product.model.data.request.QueryProductParams;

public class SearchProductActivity extends BaseActivity<ActivitySearchProductBinding,SearchProductViewModel> {



    @Override
    protected int getLayoutRes() {
        return R.layout.activity_search_product;
    }

    @Override
    protected SearchProductViewModel getViewModel() {
        return new ViewModelProvider(this).get(SearchProductViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding.setMViewModel(mViewModel);


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.flSearchProduct, ProductListFragment.newInstance().setParams(mViewModel.productParamsData))
                .commit();

        mDataBinding.ivSearchBack.setOnClickListener(v -> super.onBackPressed());

        mDataBinding.rcvSearchProductTags.setAdapter(new CmnRcvAdapter<ProductTag>(this,R.layout.item_layout_product_tag_select,mViewModel.productTagsData) {
            @Override
            public void convert(CmnViewHolder holder, ProductTag tag, int position) {
                ItemLayoutProductTagSelectBinding tagBinding = DataBindingUtil.bind(holder.itemView);
                tagBinding.setTag(tag);
                holder.itemView.setOnClickListener(v -> {
                    if(tag.isSelected()){
                        tag.setSelected(false);
                        mViewModel.productParamsData.getValue().setTagId(null);
                    }else {
                        for (ProductTag tagItem:mViewModel.productTagsData.getValue()){
                            tagItem.setSelected(false);
                        }
                        tag.setSelected(true);

                        QueryProductParams params = mViewModel.productParamsData.getValue();
                        params.setTagId(tag.getTagId());
                        mViewModel.productParamsData.postValue(params);

                    }
                });
            }
        });

        mViewModel.getProductTagList();


    }
}